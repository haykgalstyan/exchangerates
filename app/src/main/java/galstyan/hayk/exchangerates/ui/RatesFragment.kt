package galstyan.hayk.exchangerates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import galstyan.hayk.exchangerates.R
import galstyan.hayk.exchangerates.model.CurrencyRate
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


        pager.adapter = PageAdapter(object : DiffUtil.ItemCallback<List<CurrencyRate>>() {
            override fun areItemsTheSame(
                old: List<CurrencyRate>,
                new: List<CurrencyRate>
            ): Boolean {
                return false
            }

            override fun areContentsTheSame(
                old: List<CurrencyRate>,
                new: List<CurrencyRate>
            ): Boolean {
                return false
            }
        })
    }


    // todo: READ THIS https://developer.android.com/reference/android/support/v7/recyclerview/extensions/ListAdapter


    class PageHolder(itemView: View) : BoundViewHolder(itemView) {
        val recycler: RecyclerView by lazy { itemView.findViewById(R.id.recycler) }

        override fun bind() {
            (recycler.adapter as ListAdapter<*, *>).submitList(listOf())
        }
    }


    class PageAdapter(differ: DiffUtil.ItemCallback<List<CurrencyRate>>) :
        BoundViewHolderListAdapter<List<CurrencyRate>>(differ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoundViewHolder {
            return PageHolder(parent.inflate(R.layout.rate_list))
        }
    }

}