package galstyan.hayk.exchangerates.app

import android.app.Application
import galstyan.hayk.exchangerates.repository.BranchRepository
import galstyan.hayk.exchangerates.repository.RatesRepository
import galstyan.hayk.exchangerates.ui.AppViewModelFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


class App : Application() {

    /**
     * API
     *
     * Rates
     * http://rate.am/ws/mobile/v2/rates.ashx?lang=en
     * Logos
     * https://rate.am/images/organization/logo/
     *
     * Branches
     * http://rate.am/ws/mobile/v2/branches.ashx?id=5ee70183-87fe-4799-802e-ef7f5e7323db
     * Maybe put org id in gradle local props and mention that in readme file
     *
     *
     * 0 cash
     * 1 non cache
     */


    private val appContainer = AppContainerImpl(
        repositories = mapOf(
            RatesRepository::class.java to RatesMockRepositoryImpl(),
            BranchRepository::class.java to BranchLocalRepositoryImpl("")
        )
    )


    val json = Json(JsonConfiguration.Stable)


    // these are framework specific not domain, only implementations depend on these
    val viewModelFactory = AppViewModelFactory(appContainer)
    val fragmentFactory = FragmentFactoryImpl(viewModelFactory)
}