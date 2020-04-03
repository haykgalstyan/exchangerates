package galstyan.hayk.exchangerates.ui.bank

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import galstyan.hayk.exchangerates.domain.AppContainer
import galstyan.hayk.exchangerates.domain.Branch
import galstyan.hayk.exchangerates.domain.Language
import galstyan.hayk.exchangerates.repository.BranchRepository
import galstyan.hayk.exchangerates.ui.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BankViewModel(appContainer: AppContainer) : AppViewModel(appContainer) {
    private val repoBranches = appContainer.getRepository(BranchRepository::class.java)

    private var language = Language.EN


    val bankBranchesObservable: LiveData<List<Branch>> get() = _bankBranchesObservable
    private val _bankBranchesObservable: MutableLiveData<List<Branch>> =
        MutableLiveData()


    fun loadBranches(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val branches = repoBranches.getBranchesOf(id, language)

            _bankBranchesObservable.postValue(branches)
        }
    }

}