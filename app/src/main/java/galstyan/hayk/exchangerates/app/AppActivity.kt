package galstyan.hayk.exchangerates.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import galstyan.hayk.exchangerates.R


class AppActivity : AppCompatActivity() {

    val app by lazy { (application as App?)!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = app.fragmentFactory
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        if (savedInstanceState != null)
            return

        val navigation = NavigationImpl(supportFragmentManager, R.id.fragment_container, null)
//        navigation.replace(Fragment(app.viewModelFactory))
    }
}
