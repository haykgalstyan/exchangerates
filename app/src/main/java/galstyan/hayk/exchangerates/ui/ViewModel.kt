package galstyan.hayk.exchangerates.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import galstyan.hayk.exchangerates.model.AppContainer
import galstyan.hayk.exchangerates.model.Bank
import galstyan.hayk.exchangerates.repository.BranchRepository
import galstyan.hayk.exchangerates.repository.BankRatesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ViewModel(appContainer: AppContainer) : AppViewModel(appContainer) {

    val bankRatesObservable: LiveData<List<Bank>> get() = _bankRatesObservable
    private val _bankRatesObservable: MutableLiveData<List<Bank>> = MutableLiveData()


    private val repoBankRates = appContainer.getRepository(BankRatesRepository::class.java)
    private val repoBranches = appContainer.getRepository(BranchRepository::class.java)

    val bankRatesList get() = _bankRatesList
    private lateinit var _bankRatesList: List<Bank>


    fun loadBanks() {
        viewModelScope.launch(Dispatchers.IO) {
            _bankRatesList = repoBankRates.getBankRates()
            _bankRatesObservable.postValue(bankRatesList)
        }
    }
}