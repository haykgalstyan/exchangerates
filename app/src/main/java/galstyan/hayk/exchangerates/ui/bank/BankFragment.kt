package galstyan.hayk.exchangerates.ui.bank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import galstyan.hayk.exchangerates.R
import galstyan.hayk.exchangerates.ui.AppBaseFragment


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

        viewModel.loadBranches(id)
        viewModel.bankBranchesObservable.observe(viewLifecycleOwner, Observer {
            println(it)
        })
    }
}