package galstyan.hayk.exchangerates.model


data class RateInfo(
    val date: Long,
    val rates: List<CurrencyRate>
)


data class CurrencyRate(
    val currency: String,
    val cash: Rate,
    val nonCash: Rate
)


data class Rate(val buy: Double, val sell: Double)