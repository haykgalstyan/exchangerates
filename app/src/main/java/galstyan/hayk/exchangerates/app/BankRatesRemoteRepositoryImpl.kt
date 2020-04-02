package galstyan.hayk.exchangerates.app

import android.annotation.SuppressLint
import android.net.Uri
import galstyan.hayk.exchangerates.domain.*
import galstyan.hayk.exchangerates.repository.BankRatesRepository
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.json.JSONObject
import java.net.URL


private const val API = "http://rate.am/ws/mobile/v2/rates.ashx"
private const val IMAGE_BASE = "https://rate.am/images/organization/logo/"

private const val KEY_CURRENCY_RATE = "list"
private const val KEY_RATE_CASH = "0"
private const val KEY_RATE_NO_CASH = "1"
private const val KEY_QUERY_LANG = "lang"


/**
 * rate.am API Rates implementation
 * Using mix of custom deserialization because of the horrible json... sorry :D
 */
class BankRatesRemoteRepositoryImpl(
    private val json: Json
) : BankRatesRepository() {

    @Serializable
    data class BankModel(val title: String, val date: Long, val logo: String)

    @Serializable
    data class RateModel(val buy: Double, val sell: Double)


    override suspend fun getBankRates(language: Language): List<Bank> {
        @SuppressLint("DefaultLocale") // seems like a lint bug
        val url = Uri.parse(API)
            .buildUpon()
            .appendQueryParameter(KEY_QUERY_LANG, language.name.toLowerCase())
            .toString()

        val response = URL(url).readText()
        return deserializeBank(JSONObject(response))
    }


    private fun deserializeBank(response: JSONObject): List<Bank> {
        val bankIds = response.keys()
        val result = mutableListOf<Bank>()
        bankIds.forEach { id ->
            val bankText = response.get(id).toString()
            val rateInfo = JSONObject(bankText).get(KEY_CURRENCY_RATE) as JSONObject
            val bankModel = json.parse(BankModel.serializer(), bankText)
            result.add(
                Bank(
                    id = id,
                    title = bankModel.title,
                    image = IMAGE_BASE + bankModel.logo,
                    branches = null,
                    rateInfo = RateInfo(
                        date = bankModel.date,
                        currencyRates = deserializeCurrencyRates(rateInfo)
                    )
                )
            )
        }
        return result
    }


    private fun deserializeCurrencyRates(rateInfo: JSONObject): Map<String, Rates> {
        val currencies = rateInfo.keys()
        val result = mutableMapOf<String, Rates>()
        currencies.forEach { currency ->
            val rate = rateInfo.get(currency) as JSONObject
            result[currency] = Rates(
                rateCash = deserializeRate(rate, KEY_RATE_CASH),
                rateNonCash = deserializeRate(rate, KEY_RATE_NO_CASH)
            )
        }
        return result
    }


    private fun deserializeRate(rate: JSONObject, key: String): Rate? =
        rate.optJSONObject(key)?.let {
            val model = json.parse(RateModel.serializer(), it.toString())
            Rate(buy = model.buy, sell = model.sell)
        }
}
