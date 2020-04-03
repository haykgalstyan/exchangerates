package galstyan.hayk.exchangerates.repository

import galstyan.hayk.exchangerates.domain.Branch
import galstyan.hayk.exchangerates.domain.Language


abstract class BranchRepository : Repository {


    /**
     * Returns all the branches of the banks with id [bankId]
     * If there is a head branch, it will be the first element in the list
     */
    abstract suspend fun getBranchesOf(bankId: String, language: Language): List<Branch>

}