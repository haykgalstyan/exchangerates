package galstyan.hayk.exchangerates.repository

import galstyan.hayk.exchangerates.domain.Bank
import galstyan.hayk.exchangerates.domain.Language


abstract class BankRatesRepository : Repository {

    abstract suspend fun getBankRates(language: Language): List<Bank>
}