package galstyan.hayk.exchangerates.app

import galstyan.hayk.exchangerates.model.Bank
import galstyan.hayk.exchangerates.model.CurrencyRate
import galstyan.hayk.exchangerates.model.Rate
import galstyan.hayk.exchangerates.model.RateInfo
import galstyan.hayk.exchangerates.repository.BankRatesRepository
import kotlin.random.Random


class BankRatesMockRepositoryImpl : BankRatesRepository() {


    override suspend fun getBankRates(): List<Bank> {
        val r = Random(System.currentTimeMillis())
        var i = 0

        val list: MutableList<Bank> = mutableListOf()
        repeat(10) {
            i++
            list.add(
                Bank(
                    id = i.toString(),
                    title = "Bank $i",
                    image = "247e6cb5ede645579a2a6dba26dc6200.jpg",
                    branches = null,
                    rateInfo = RateInfo(
                        date = System.currentTimeMillis(),
                        currencyRates = listOf(
                            CurrencyRate(
                                currency = if (i % 2 == 2) "USD" else "AMD",
                                rateCash = Rate(
                                    buy = i * 10.toDouble(),
                                    sell = i * 20.toDouble()
                                ),
                                rateNonCash = Rate(
                                    buy = 500.toDouble(),
                                    sell = 700.toDouble()
                                )
                            )
                        ).shuffled()
                    )
                )
            )
        }
        return list.shuffled()
    }
}