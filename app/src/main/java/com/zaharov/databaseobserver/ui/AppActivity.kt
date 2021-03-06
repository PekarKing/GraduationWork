package com.zaharov.databaseobserver.ui

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.widget.SwitchCompat
import com.zaharov.databaseobserver.R
import moxy.MvpAppCompatActivity
import moxy.MvpView
import moxy.ktx.moxyPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface AppView : MvpView {
    fun onChangeSignal(signalType: Int)
}

class AppActivity : MvpAppCompatActivity(), AppView {

    private val presenter: AppPresenter by moxyPresenter { AppPresenter() }

    private lateinit var switchTrafficLight: SwitchCompat
    private lateinit var red: FrameLayout
    private lateinit var yellow: FrameLayout
    private lateinit var green: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_container)

        switchTrafficLight = findViewById(R.id.switchTrafficLight)
        red = findViewById(R.id.red)
        yellow = findViewById(R.id.yellow)
        green = findViewById(R.id.green)

//        switchTrafficLight.setOnCheckedChangeListener { _, isChecked -> presenter.setIsOne(isChecked) }
    }

    override fun onResume() {
        super.onResume()
        presenter.a()
    }

    override fun onChangeSignal(signalType: Int) {
        when (signalType) {
            1 -> {
                red.setBackgroundColor(resources.getColor(R.color.red, theme))
                yellow.setBackgroundColor(resources.getColor(R.color.yellow_dark, theme))
                green.setBackgroundColor(resources.getColor(R.color.green_dark, theme))
            }
            2 -> {
                red.setBackgroundColor(resources.getColor(R.color.red_dark, theme))
                yellow.setBackgroundColor(resources.getColor(R.color.yellow, theme))
                green.setBackgroundColor(resources.getColor(R.color.green_dark, theme))
            }
            3 -> {
                red.setBackgroundColor(resources.getColor(R.color.red_dark, theme))
                yellow.setBackgroundColor(resources.getColor(R.color.yellow_dark, theme))
                green.setBackgroundColor(resources.getColor(R.color.green, theme))
            }
        }
    }

    companion object {
        private const val TAG = "AppActivity"
    }
}