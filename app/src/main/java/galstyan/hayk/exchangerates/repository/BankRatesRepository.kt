package galstyan.hayk.exchangerates.repository

import galstyan.hayk.exchangerates.model.Bank


abstract class BankRatesRepository : Repository {

    abstract suspend fun getBankRates(): List<Bank>
}