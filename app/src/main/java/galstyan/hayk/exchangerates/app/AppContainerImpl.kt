package galstyan.hayk.exchangerates.app

import galstyan.hayk.exchangerates.domain.AppContainer
import galstyan.hayk.exchangerates.repository.Repository


class AppContainerImpl(
    private val repositories: Map<Class<out Repository>, Repository>
) : AppContainer {


    @Suppress("UNCHECKED_CAST")
    override fun <T : Repository> getRepository(repositoryClass: Class<T>): T =
        repositories[repositoryClass] as T

}