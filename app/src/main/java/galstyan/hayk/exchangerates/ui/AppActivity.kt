package galstyan.hayk.exchangerates.ui

import galstyan.hayk.exchangerates.ui.navigation.Navigation


/**
 * Interface for fragments to communicate or get UI dependencies with/from activity
 * without knowing too much about it
 */
interface AppActivity {

    fun getNavigation(): Navigation
}