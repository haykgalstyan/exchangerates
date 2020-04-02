package galstyan.hayk.exchangerates.domain


data class RateInfo(
    val date: Long,
    val currencyRates: Map<String, Rates>
)


data class Rates(
    val rateCash: Rate?,
    val rateNonCash: Rate?
)


data class Rate(val buy: Double, val sell: Double)