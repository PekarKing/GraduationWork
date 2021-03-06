package com.zaharov.databaseobserver.ui.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import moxy.MvpAppCompatActivity
import moxy.MvpView
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface BaseView : MvpView {

    fun updateDialogsTheme()

    fun showAlertDialog(
        title: String, message: String,
        posBtnTxt: String? = null, negBtnTxt: String? = null,
        posBtnAction: (() -> Unit)? = null, negBtnAction: (() -> Unit)? = null,
        cancellable: Boolean
    )
}

abstract class BaseActivity(@LayoutRes containerLayoutId: Int) :
    MvpAppCompatActivity(containerLayoutId), BaseView {

    override fun showAlertDialog(
        title: String, message: String,
        posBtnTxt: String?, negBtnTxt: String?,
        posBtnAction: (() -> Unit)?, negBtnAction: (() -> Unit)?,
        cancellable: Boolean
    ) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            if (posBtnTxt != null) setPositiveButton(posBtnTxt) { _, _ -> if (posBtnAction != null) posBtnAction() }
            if (negBtnTxt != null) setNegativeButton(negBtnTxt) { _, _ -> if (negBtnAction != null) negBtnAction() }
            setCancelable(cancellable)
        }.show()
    }

    override fun updateDialogsTheme() {}

    companion object {
        const val TAG = "BaseActivity"
    }
}