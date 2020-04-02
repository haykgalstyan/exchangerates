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


/**
 * Since the UI is showing Banks grouped by currencies, we need a map of
 * currency -> Banks (Can retrieve rates from bank fast by currency key)
 * View model shall expose data convenient for the view, so the mapping is done here
 *
 * backing fields are for encapsulating [MutableLiveData] or mutable variables
 */

class ViewModel(appContainer: AppContainer) : AppViewModel(appContainer) {
    private val repoBankRates = appContainer.getRepository(BankRatesRepository::class.java)
    private val repoBranches = appContainer.getRepository(BranchRepository::class.java)

    val currencyBanksMapObservable: LiveData<Map<String, List<Bank>>> get() = _currencyBanksMapObservable
    private val _currencyBanksMapObservable: MutableLiveData<Map<String, List<Bank>>> =
        MutableLiveData()

    val currencyBanksMap get(): Map<String, List<Bank>> = _currencyBanksMap
    private lateinit var _currencyBanksMap: Map<String, List<Bank>>

    val currencies get(): List<String> = _currencies
    private lateinit var _currencies: List<String>


    fun loadBanks() {
        viewModelScope.launch(Dispatchers.IO) {
            val banks = repoBankRates.getBankRates()

            _currencies = banks.flatMap { it.rateInfo.currencyRates.keys }.distinct()
            _currencyBanksMap = _currencies.associateWith { currency ->
                banks.filter { bank -> bank.rateInfo.currencyRates.contains(currency) }
            }

            _currencyBanksMapObservable.postValue(_currencyBanksMap)
        }
    }
}