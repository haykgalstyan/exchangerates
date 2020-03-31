package galstyan.hayk.exchangerates.app

import android.app.Application
import galstyan.hayk.exchangerates.repository.BranchRepository
import galstyan.hayk.exchangerates.repository.RatesRepository
import galstyan.hayk.exchangerates.ui.AppViewModelFactory


class App : Application() {


    private val appContainer = AppContainerImpl(
        repositories = mapOf(
            RatesRepository::class.java to RatesMockRepositoryImpl(),
            BranchRepository::class.java to BranchLocalRepositoryImpl()
        )
    )


    // these are framework specific not domain, only implementations depend on these
    val viewModelFactory = AppViewModelFactory(appContainer)
    val fragmentFactory = FragmentFactoryImpl(viewModelFactory)
}