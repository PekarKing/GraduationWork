package com.zaharov.databaseobserver.ui.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import moxy.MvpAppCompatFragment

abstract class BaseFragment(@LayoutRes containerLayoutId: Int) :
    MvpAppCompatFragment(containerLayoutId) {

    private var baseActivity: BaseActivity? = null

    var onBackPressedAction: (() -> Unit)? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseActivity = context as? BaseActivity
    }

    fun showAlertDialog(
        title: String, message: String,
        posBtnTxt: String?, negBtnTxt: String?,
        posBtnAction: (() -> Unit)?, negBtnAction: (() -> Unit)?,
        cancellable: Boolean
    ) {
        baseActivity?.showAlertDialog(
            title, message,
            posBtnTxt, negBtnTxt,
            posBtnAction, negBtnAction,
            cancellable
        )
    }
}