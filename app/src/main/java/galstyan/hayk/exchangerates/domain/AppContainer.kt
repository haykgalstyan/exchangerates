package galstyan.hayk.exchangerates.domain

import galstyan.hayk.exchangerates.repository.Repository


/**
 * Container for domain layer dependencies
 */
interface AppContainer {

    /**
     * Get a Repository marked by the [Repository] interface
     * Using [Class] so this can be used by java code if needed
     */
    fun <T : Repository> getRepository(repositoryClass: Class<T>): T

}