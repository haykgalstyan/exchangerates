package galstyan.hayk.exchangerates.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import galstyan.hayk.exchangerates.R
import galstyan.hayk.exchangerates.model.Bank
import kotlinx.android.synthetic.main.fragment_rates.*


class RatesFragment(viewModelFactory: ViewModelProvider.Factory) :
    AppBaseFragment(viewModelFactory) {

    private val viewModel by viewModels<ViewModel> { viewModelFactory }

    private val imageLoader = ImageLoaderProvider().imageLoader


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_rates, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        refresh.setOnRefreshListener {
            viewModel.loadBanks()
            refresh.isRefreshing = false
        }

        val pageAdapter = PageAdapter(stringDiffer())
        pager.adapter = pageAdapter
        TabLayoutMediator(
            tabs, pager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = pageAdapter.currentList[position]
            }
        ).attach()

        viewModel.loadBanks()
        viewModel.currencyBanksMapObservable.observe(viewLifecycleOwner, Observer {
            pageAdapter.submitList(it.keys.toList())
        })
    }


    inner class PageAdapter(
        differ: DiffUtil.ItemCallback<String>
    ) : BoundViewHolderListAdapter<String>(differ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoundViewHolder {
            val holder = PageHolder(parent.inflate(R.layout.rate_list))
            holder.recycler.adapter = BankRateListAdapter(bankDiffer())
            return holder
        }

        inner class PageHolder(
            itemView: View
        ) : BoundViewHolder(itemView) {
            val recycler: RecyclerView by lazy { itemView.findViewById<RecyclerView>(R.id.recycler) }

            override fun bind() {
//                val currency = viewModel.currencies[adapterPosition]
                val currency = currentList[adapterPosition]
                val map = viewModel.currencyBanksMap

                @Suppress("UNCHECKED_CAST")
                val adapter = recycler.adapter as ListAdapter<Bank, *>
                recycler.tag = currency
                adapter.submitList(map[currency])
            }
        }
    }


    inner class BankRateListAdapter(
        differ: DiffUtil.ItemCallback<Bank>
    ) : BoundViewHolderListAdapter<Bank>(differ) {

        lateinit var recycler: RecyclerView

        override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
            recycler = recyclerView
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoundViewHolder {
            return BankRateHolder(parent.inflate(R.layout.item_rate))
        }

        inner class BankRateHolder(
            itemView: View
        ) : BoundViewHolder(itemView) {
            private val title: TextView by lazy { itemView.findViewById<TextView>(R.id.item_rate_text_title) }
            private val buy: TextView by lazy { itemView.findViewById<TextView>(R.id.item_rate_text_rate_buy) }
            private val sell: TextView by lazy { itemView.findViewById<TextView>(R.id.item_rate_text_rate_sell) }
            private val image: ImageView by lazy { itemView.findViewById<ImageView>(R.id.item_rate_image) }

            override fun bind() {
                val bank = currentList[adapterPosition]
                val currency = recycler.tag
                val rates = bank.rateInfo.currencyRates[currency]

                title.text = bank.title
                buy.text = rates?.rateCash?.buy.toString()
                sell.text = rates?.rateCash?.buy.toString()

                // todo: add cash no cash to item ui

                imageLoader.load(bank.image).into(image)
            }
        }
    }
}