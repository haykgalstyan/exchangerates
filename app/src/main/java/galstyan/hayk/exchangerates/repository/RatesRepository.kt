package galstyan.hayk.exchangerates.repository

import galstyan.hayk.exchangerates.model.Bank


abstract class RatesRepository : Repository {

    abstract suspend fun getRates(): List<Bank>
}