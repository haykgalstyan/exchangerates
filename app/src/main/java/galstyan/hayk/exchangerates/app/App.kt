package galstyan.hayk.exchangerates.app

import android.app.Application
import galstyan.hayk.exchangerates.repository.BranchRepository
import galstyan.hayk.exchangerates.repository.BankRatesRepository
import galstyan.hayk.exchangerates.ui.AppViewModelFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


class App : Application() {

    private val jsonSerializer = Json(
        JsonConfiguration.Stable.copy(
            ignoreUnknownKeys = true,
            isLenient = true,
            serializeSpecialFloatingPointValues = true,
            useArrayPolymorphism = true
        )
    )

    private val appContainer = AppContainerImpl(
        repositories = mapOf(
            BankRatesRepository::class.java to BankRatesRemoteRepositoryImpl(jsonSerializer),
            BranchRepository::class.java to BranchRepositoryImpl()
        )
    )

    // these are framework specific not domain, only implementations depend on these
    val viewModelFactory = AppViewModelFactory(appContainer)
    val fragmentFactory = FragmentFactoryImpl(viewModelFactory)
}