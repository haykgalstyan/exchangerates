package galstyan.hayk.exchangerates.ui.bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import galstyan.hayk.exchangerates.R
import galstyan.hayk.exchangerates.ui.AppBaseFragment
import galstyan.hayk.exchangerates.ui.BoundViewHolder
import galstyan.hayk.exchangerates.ui.BoundViewHolderAdapter
import galstyan.hayk.exchangerates.ui.inflate
import kotlinx.android.synthetic.main.fragment_bank.*


private const val ARG_ID = "arg_id"


class BankFragment(
    viewModelFactory: ViewModelProvider.Factory
) : AppBaseFragment(viewModelFactory) {

    private val viewModel by viewModels<BankViewModel> { viewModelFactory }


    companion object {
        @JvmStatic
        fun newInstance(id: String, viewModelFactory: ViewModelProvider.Factory) =
            BankFragment(viewModelFactory).apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                }
            }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bank, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = arguments?.getString(ARG_ID) ?: throw IllegalArgumentException("bank id not found")

        val adapter = BranchAdapter()
        recycler.adapter = adapter

        viewModel.loadBranches(id)
        viewModel.bankBranchesObservable.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
    }


    /**
     * Since this data is not refreshed frequently not using diffing adapter
     */
    inner class BranchAdapter : BoundViewHolderAdapter() {

        override fun getItemCount(): Int = viewModel.branches.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoundViewHolder {
            return Holder(parent.inflate(R.layout.item_branch))
        }

        inner class Holder(itemView: View) : BoundViewHolder(itemView) {
            private val title: TextView by lazy { itemView.findViewById<TextView>(R.id.item_branch_title) }
            private val address: TextView by lazy { itemView.findViewById<TextView>(R.id.item_branch_adress) }
            private val contact: TextView by lazy { itemView.findViewById<TextView>(R.id.item_branch_contact) }

            override fun bind() {
                val branch = viewModel.branches[adapterPosition]
                val isHead: Boolean = adapterPosition == 0 && branch.isHead

                title.text = branch.title
                address.text = branch.address
                contact.text = branch.contact

                // Sorry :D I really have no more time to do something better ))
                itemView.setBackgroundColor(
                    if (isHead) 0xff222222.toInt()
                    else 0xff444444.toInt()
                )
            }
        }
    }
}