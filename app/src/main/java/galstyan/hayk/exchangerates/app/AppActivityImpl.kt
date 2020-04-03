package galstyan.hayk.exchangerates.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import galstyan.hayk.exchangerates.R
import galstyan.hayk.exchangerates.ui.AppActivity
import galstyan.hayk.exchangerates.ui.navigation.Navigation
import galstyan.hayk.exchangerates.ui.rates.RatesFragment


class AppActivityImpl : AppCompatActivity(), AppActivity {

    val app by lazy { (application as App?)!! }

    private lateinit var navigation: Navigation


    override fun getNavigation(): Navigation = navigation


    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = app.fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        if (savedInstanceState != null)
            return

        navigation = NavigationImpl(supportFragmentManager, R.id.fragment_container, null)
        navigation.replace(RatesFragment(app.viewModelFactory))
    }
}
