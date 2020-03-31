package galstyan.hayk.exchangerates.repository

import galstyan.hayk.exchangerates.model.Branch


abstract class BranchRepository : Repository {

    abstract suspend fun getBranchesOf(bankId: String): List<Branch>

}