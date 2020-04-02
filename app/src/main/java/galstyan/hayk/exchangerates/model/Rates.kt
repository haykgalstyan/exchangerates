package galstyan.hayk.exchangerates.model


data class RateInfo(
    val date: Long,
    val currencyRates: Map<String, Rates>
)


data class Rates(
    val rateCash: Rate?,
    val rateNonCash: Rate?
)


data class Rate(val buy: Double, val sell: Double)