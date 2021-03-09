package com.zaharov.databaseobserver.ui.checker

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.zaharov.databaseobserver.R
import com.zaharov.databaseobserver.ui.app.AppActivity
import com.zaharov.databaseobserver.ui.base.BaseFragment
import com.zaharov.databaseobserver.ui.base.BaseView
import com.zaharov.databaseobserver.utils.navigation.OnNavigateListener
import moxy.ktx.moxyPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface StatesView : BaseView {
    fun showState(stateName: String)
}

class StatesFragment : BaseFragment(R.layout.fragment_states), StatesView,
    CompoundButton.OnCheckedChangeListener {

    private val presenter: StatesPresenter by moxyPresenter { StatesPresenter() }

    private var onNavigateListener: OnNavigateListener? = null

    private lateinit var switchStateOne: SwitchCompat
    private lateinit var switchStateTwo: SwitchCompat
    private lateinit var switchStateThree: SwitchCompat
    private lateinit var tvState: TextView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNavigateListener = context as AppActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchStateOne = view.findViewById(R.id.switchStateOne)
        switchStateTwo = view.findViewById(R.id.switchStateTwo)
        switchStateThree = view.findViewById(R.id.switchStateThree)

        tvState = view.findViewById(R.id.tvState)

        switchStateOne.setOnCheckedChangeListener(this)
        switchStateTwo.setOnCheckedChangeListener(this)
        switchStateThree.setOnCheckedChangeListener(this)
    }

    override fun showState(stateName: String) {
        tvState.text = stateName
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        when (buttonView.id) {
            R.id.switchStateOne -> {
                presenter.oneState = isChecked
            }
            R.id.switchStateTwo -> {
                presenter.twoState = isChecked
            }
            R.id.switchStateThree -> {
                presenter.threeState = isChecked
            }
        }
    }

    companion object {

        const val TAG = "StatesFragment"
        fun newInstance(): StatesFragment = StatesFragment()
    }
}