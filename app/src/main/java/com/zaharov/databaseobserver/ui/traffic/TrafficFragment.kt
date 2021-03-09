package com.zaharov.databaseobserver.ui.traffic

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.zaharov.databaseobserver.R
import com.zaharov.databaseobserver.ui.app.AppActivity
import com.zaharov.databaseobserver.ui.base.BaseFragment
import com.zaharov.databaseobserver.ui.base.BaseView
import com.zaharov.databaseobserver.utils.navigation.OnNavigateListener
import moxy.ktx.moxyPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface TrafficView : BaseView {
    fun onChangeSignal(signalType: Int)
}

class TrafficFragment : BaseFragment(R.layout.fragment_traffic), TrafficView {

    private val presenter: TrafficPresenter by moxyPresenter { TrafficPresenter() }

    private var onNavigateListener: OnNavigateListener? = null

    private lateinit var red: FrameLayout
    private lateinit var yellow: FrameLayout
    private lateinit var green: FrameLayout

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onNavigateListener = context as AppActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        red = view.findViewById(R.id.red)
        yellow = view.findViewById(R.id.yellow)
        green = view.findViewById(R.id.green)
    }

    override fun onResume() {
        super.onResume()
        presenter.startStateMachine()
    }

    override fun onChangeSignal(signalType: Int) {
        when (signalType) {
            1 -> {
                red.setBackgroundColor(resources.getColor(R.color.red, activity?.theme))
                yellow.setBackgroundColor(resources.getColor(R.color.yellow_dark, activity?.theme))
                green.setBackgroundColor(resources.getColor(R.color.green_dark, activity?.theme))
            }
            2 -> {
                red.setBackgroundColor(resources.getColor(R.color.red_dark, activity?.theme))
                yellow.setBackgroundColor(resources.getColor(R.color.yellow, activity?.theme))
                green.setBackgroundColor(resources.getColor(R.color.green_dark, activity?.theme))
            }
            3 -> {
                red.setBackgroundColor(resources.getColor(R.color.red_dark, activity?.theme))
                yellow.setBackgroundColor(resources.getColor(R.color.yellow_dark, activity?.theme))
                green.setBackgroundColor(resources.getColor(R.color.green, activity?.theme))
            }
        }
    }

    companion object {
        const val TAG = "TrafficFragment"

        fun newInstance(): TrafficFragment = TrafficFragment()
    }
}