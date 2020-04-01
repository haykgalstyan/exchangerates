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
import galstyan.hayk.exchangerates.model.CurrencyRate
import kotlinx.android.synthetic.main.fragment_rates.*
import kotlin.random.Random


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

        val pageAdapter = PageAdapter(RateDiffer())
        pager.adapter = pageAdapter
        TabLayoutMediator(
            tabs, pager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = pageAdapter.currentList[position].currency
            }
        ).attach()

        viewModel.loadBanks()
        viewModel.bankRatesObservable.observe(viewLifecycleOwner, Observer { banks ->
            val currencyList = banks.flatMap {
                it.rateInfo.currencyRates.distinctBy { currencyRate -> currencyRate.currency }
            }
            pageAdapter.submitList(currencyList)
        })
    }


    inner class PageAdapter(
        differ: DiffUtil.ItemCallback<CurrencyRate>
    ) :
        BoundViewHolderListAdapter<CurrencyRate>(differ) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoundViewHolder {
            val holder = PageHolder(parent.inflate(R.layout.rate_list))
            holder.recycler.adapter = BankRateListAdapter(BankDiffer())
            return holder
        }
    }


    inner class PageHolder(
        itemView: View
    ) : BoundViewHolder(itemView) {
        val recycler: RecyclerView by lazy { itemView.findViewById<RecyclerView>(R.id.recycler) }

        override fun bind() {
            @Suppress("UNCHECKED_CAST")
            (recycler.adapter as ListAdapter<Bank, BoundViewHolder>).submitList(viewModel.bankRatesList)
        }
    }


    inner class BankRateListAdapter(
        differ: DiffUtil.ItemCallback<Bank>
    ) : BoundViewHolderListAdapter<Bank>(differ) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoundViewHolder {
            return BankRateHolder(parent.inflate(R.layout.item_rate))
        }
    }


    inner class BankRateHolder(
        itemView: View
    ) : BoundViewHolder(itemView) {
        val title: TextView by lazy { itemView.findViewById<TextView>(R.id.item_rate_text_title) }
        val buy: TextView by lazy { itemView.findViewById<TextView>(R.id.item_rate_text_rate_buy) }
        val sell: TextView by lazy { itemView.findViewById<TextView>(R.id.item_rate_text_rate_sell) }
        val image: ImageView by lazy { itemView.findViewById<ImageView>(R.id.item_rate_image) }

        override fun bind() {
            val bank = viewModel.bankRatesList[adapterPosition]
            title.text = bank.title
            buy.text = "${Random(adapterPosition).nextInt()}"
            sell.text = "${Random(adapterPosition).nextInt()}"
            imageLoader.load(bank.image).into(image)
        }
    }
}