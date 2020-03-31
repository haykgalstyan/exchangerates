package galstyan.hayk.exchangerates.model


data class Branch(
    val id: String,
    val title: String,
    val address: String,
    val location: Location,
    val contact: String
    // is main branch will probably decided outside
    // todo: work hours (if there is time)
)