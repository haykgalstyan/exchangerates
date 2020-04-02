package galstyan.hayk.exchangerates.repository

import galstyan.hayk.exchangerates.domain.Branch
import galstyan.hayk.exchangerates.domain.Language


abstract class BranchRepository : Repository {

    abstract suspend fun getBranchesOf(bankId: String, language: Language): List<Branch>

}