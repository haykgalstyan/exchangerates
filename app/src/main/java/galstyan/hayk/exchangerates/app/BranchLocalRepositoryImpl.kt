package galstyan.hayk.exchangerates.app

import galstyan.hayk.exchangerates.domain.Branch
import galstyan.hayk.exchangerates.domain.Language
import galstyan.hayk.exchangerates.domain.Location
import galstyan.hayk.exchangerates.repository.BranchRepository


class BranchLocalRepositoryImpl : BranchRepository() {


    override suspend fun getBranchesOf(bankId: String, language: Language): List<Branch> {
        val list: MutableList<Branch> = mutableListOf()
        repeat(10) {
            list.add(
                Branch(
                    id = it.toString(),
                    title = "Branch $it",
                    address = "Some Address",
                    contact = "+123-456-789",
                    location = Location(
                        40.toDouble(), 40.toDouble()
                    )
                )
            )
        }
        return list
    }

}