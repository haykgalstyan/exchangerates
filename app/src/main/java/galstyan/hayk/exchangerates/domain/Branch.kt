package galstyan.hayk.exchangerates.domain


data class Branch(
    val id: String,
    val title: String,
    val address: String,
    val location: Location,
    val contact: String,
    val isHead: Boolean
    // todo: work hours (if there is time)
)