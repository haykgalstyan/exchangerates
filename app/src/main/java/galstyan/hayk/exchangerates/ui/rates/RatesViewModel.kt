package galstyan.hayk.exchangerates.ui.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import galstyan.hayk.exchangerates.domain.AppContainer
import galstyan.hayk.exchangerates.domain.Bank
import galstyan.hayk.exchangerates.domain.Language
import galstyan.hayk.exchangerates.repository.BranchRepository
import galstyan.hayk.exchangerates.repository.BankRatesRepository
import galstyan.hayk.exchangerates.ui.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * In case other actions are needed for other types of payloads later
 */
const val PAYLOADS_RATE_TYPE = 1


/**
 * Since the UI is showing Banks grouped by currencies, we need a map of
 * currency -> Banks (Can retrieve rates from bank fast by currency key)
 * View model shall expose data convenient for the view, so the mapping is done here
 *
 * Backing fields are for encapsulating [MutableLiveData] or mutable variables
 */
class ViewModel(appContainer: AppContainer) : AppViewModel(appContainer) {
    private val repoBankRates = appContainer.getRepository(BankRatesRepository::class.java)

    // this should be stored somewhere global, but I have no more time, sorry
    private var language = Language.EN


    val currencyBanksMapObservable: LiveData<Map<String, List<Bank>>> get() = _currencyBanksMapObservable
    private val _currencyBanksMapObservable: MutableLiveData<Map<String, List<Bank>>> =
        MutableLiveData()

    /**
     * View could have notified adapter directly of rate type change,
     * But that would not work if rates were updated async here (for example from db)
     * So notifying from here will prevent future bugs
     */
    val onPayloadsChangedObservable: LiveData<Int> get() = _onPayloadsChangedObservable
    private val _onPayloadsChangedObservable: MutableLiveData<Int> = MutableLiveData()


    val currencyBanksMap get(): Map<String, List<Bank>> = _currencyBanksMap
    private lateinit var _currencyBanksMap: Map<String, List<Bank>>


    val isCash get(): Boolean = _isCash
    private var _isCash: Boolean = true


    fun loadBanks() {
        viewModelScope.launch(Dispatchers.IO) {
            val banks = repoBankRates.getBankRates(language)
            val currencies = banks.flatMap { it.rateInfo.currencyRates.keys }.distinct()
            _currencyBanksMap = currencies.associateWith { currency ->
                banks.filter { bank -> bank.rateInfo.currencyRates.contains(currency) }
            }
            _currencyBanksMapObservable.postValue(_currencyBanksMap)
        }
    }


    fun setLanguage(language: Language) {
        this.language = language
        loadBanks()
    }


    fun toggleIsCash() {
        _isCash = !isCash
        _currencyBanksMapObservable.value = _currencyBanksMap
        _onPayloadsChangedObservable.value =
            PAYLOADS_RATE_TYPE
    }
}