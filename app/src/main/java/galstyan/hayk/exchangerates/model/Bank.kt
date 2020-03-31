package galstyan.hayk.exchangerates.model


data class Bank(
    val id: String,
    val title: String,
    val image: String,
    val rateInfo: RateInfo,
    val branches: List<Branch>?
    // lazy load branches from other api, copy object and add them
)

