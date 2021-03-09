package com.zaharov.databaseobserver.ui.app

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.zaharov.databaseobserver.R
import com.zaharov.databaseobserver.ui.base.BaseActivity
import com.zaharov.databaseobserver.ui.base.BaseFragment
import com.zaharov.databaseobserver.ui.base.BaseView
import com.zaharov.databaseobserver.ui.main.MainFragment
import com.zaharov.databaseobserver.utils.navigation.OnNavigateListener
import com.zaharov.databaseobserver.utils.navigation.attachFragment
import com.zaharov.databaseobserver.utils.navigation.replaceFragment
import moxy.MvpAppCompatActivity
import moxy.MvpView
import moxy.ktx.moxyPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface AppView : BaseView {
    fun onFirstOpen()
}

class AppActivity : BaseActivity(R.layout.activity_container), AppView, OnNavigateListener {

    private val presenter: AppPresenter by moxyPresenter { AppPresenter() }

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)
    }

    /**
     * Данный метод срабатывает при firstAttach
     * Необходим для запуска стартового экрана приложения
     */
    override fun onFirstOpen() {
        Log.d(TAG, "onFirstOpen")
        onNavigate(
            MainFragment.newInstance(), MainFragment.TAG
        )
    }

    /**
     * Метод для навигации по фрагментам без чистки popBackStack,
     * в случае если фрагмент будет найден по тегу к нему применится [FragmentTransaction.attach],
     * в противном случае применится [FragmentTransaction.add]
     */
    override fun onNavigate(fragmentInstance: Fragment, tag: String?, isAddToBackStack: Boolean) {
        supportFragmentManager.attachFragment(R.id.container,
            fragmentInstance,
            tag,
            isAddToBackStack)
    }

    /**
     * Метод для навигации по фрагментам с чисткой popBackStack
     * и удалением предыдущих экземпляров фрагментов
     */
    override fun onNavigateExclusive(fragmentInstance: Fragment, tag: String?) {
        supportFragmentManager.replaceFragment(R.id.container, fragmentInstance, tag)
    }

    /**
     * Метод для выполнение шага назад
     * в основном используется при клике на крестик или стрелочку в toolbar
     */
    override fun onBackPressed() {
        currentFragment?.onBackPressedAction?.invoke() ?: super.onBackPressed()
    }

    companion object {
        private const val TAG = "AppActivity"
    }
}