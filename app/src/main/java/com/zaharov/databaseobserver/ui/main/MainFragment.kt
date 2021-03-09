package com.zaharov.databaseobserver.ui.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.zaharov.databaseobserver.R
import com.zaharov.databaseobserver.ui.app.AppActivity
import com.zaharov.databaseobserver.ui.base.BaseFragment
import com.zaharov.databaseobserver.ui.base.BaseView
import com.zaharov.databaseobserver.ui.checker.StatesFragment
import com.zaharov.databaseobserver.ui.traffic.TrafficFragment
import com.zaharov.databaseobserver.utils.navigation.OnNavigateListener
import moxy.ktx.moxyPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface MainView : BaseView {}

class MainFragment : BaseFragment(R.layout.fragment_main), MainView {

    private val presenter: MainPresenter by moxyPresenter { MainPresenter() }

    private var onNavigateListener: OnNavigateListener? = null

    private lateinit var btnOpenTraffic: Button
    private lateinit var btnOpenStates: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNavigateListener = context as AppActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnOpenTraffic = view.findViewById(R.id.btnOpenTraffic)
        btnOpenStates = view.findViewById(R.id.btnOpenStates)

        btnOpenTraffic.setOnClickListener {
            onNavigateListener?.onNavigate(
                TrafficFragment.newInstance(),
                TrafficFragment.TAG,
                isAddToBackStack = true
            )
        }

        btnOpenStates.setOnClickListener {
            onNavigateListener?.onNavigate(
                StatesFragment.newInstance(),
                StatesFragment.TAG,
                isAddToBackStack = true
            )
        }
    }

    companion object {
        const val TAG = "MainFragment"

        fun newInstance(): MainFragment = MainFragment()
    }
}