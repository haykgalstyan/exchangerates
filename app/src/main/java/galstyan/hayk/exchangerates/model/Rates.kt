package galstyan.hayk.exchangerates.model


data class RateInfo(
    val date: Long,
    val currencyRates: List<CurrencyRate>
)


data class CurrencyRate(
    val currency: String,
    val rateCash: Rate?,
    val rateNonCash: Rate?
)


data class Rate(val buy: Double, val sell: Double)