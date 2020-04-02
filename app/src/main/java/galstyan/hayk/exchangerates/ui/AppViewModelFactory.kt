package galstyan.hayk.exchangerates.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import galstyan.hayk.exchangerates.domain.AppContainer


class AppViewModelFactory(
	private val appContainer: AppContainer
) : ViewModelProvider.Factory {

	override fun <T : ViewModel?> create(modelClass: Class<T>): T {
		try {
			return modelClass.getConstructor(AppContainer::class.java).newInstance(appContainer)
		} catch (e: InstantiationException) {
			throw IllegalArgumentException(
				"${javaClass.simpleName} can only create instances of " +
						"${AppViewModel::class.simpleName} subclasses " +
						"${modelClass.simpleName} will not work"
			)
		}
	}

}