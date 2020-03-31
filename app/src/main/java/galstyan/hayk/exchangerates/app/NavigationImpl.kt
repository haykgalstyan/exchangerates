package galstyan.hayk.exchangerates.app

import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import galstyan.hayk.exchangerates.ui.navigation.Navigation
import galstyan.hayk.exchangerates.ui.navigation.OnNavigationListener


class NavigationImpl(
	private val fragmentManager: FragmentManager,
	private val containerViewId: Int,
	private val listener: OnNavigationListener?
) : Navigation {

	private val uiHandler: Handler = Handler()

	init {
		if (listener != null) fragmentManager.addOnBackStackChangedListener { notifyListener() }
	}


	override fun replace(fragment: Fragment) {
		fragmentManager
			.beginTransaction()
			.replace(containerViewId, fragment)
			.commit()

		uiHandler.post { notifyListener() }
	}


	override fun push(fragment: Fragment) {
		fragmentManager
			.beginTransaction()
			.addToBackStack(null)
			.replace(containerViewId, fragment)
			.commit()
	}


	override fun pop() = fragmentManager.popBackStack()


	override fun peek(): Fragment? {
		return fragmentManager.fragments.lastOrNull()
	}


	private fun notifyListener() {
		listener?.onNavigation()
	}
}