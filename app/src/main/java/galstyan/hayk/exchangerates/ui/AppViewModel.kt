package galstyan.hayk.exchangerates.ui

import androidx.lifecycle.ViewModel
import galstyan.hayk.exchangerates.domain.AppContainer


abstract class AppViewModel(protected val appContainer: AppContainer) : ViewModel()