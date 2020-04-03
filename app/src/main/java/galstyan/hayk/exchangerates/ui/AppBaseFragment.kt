package galstyan.hayk.exchangerates.ui

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider


abstract class AppBaseFragment(
    protected val viewModelFactory: ViewModelProvider.Factory
) : Fragment() {

    protected fun getAppActivity(): AppActivity = activity as AppActivity
}



