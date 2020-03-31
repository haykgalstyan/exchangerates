package galstyan.hayk.exchangerates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import galstyan.hayk.exchangerates.R
import kotlinx.android.synthetic.main.fragment_rates.*


class RatesFragment(
    viewModelFactory: ViewModelProvider.Factory
) : AppBaseFragment(viewModelFactory) {


    val viewModel by viewModels<ViewModel> { viewModelFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rates, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        TabLayoutMediator(
            tabs, pager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->

            }
        ).attach()
    }
}