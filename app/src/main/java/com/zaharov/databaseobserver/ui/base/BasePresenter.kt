package com.zaharov.databaseobserver.ui.base

import moxy.MvpPresenter

open class BasePresenter<View : BaseView> : MvpPresenter<View>() {

    companion object {
        private const val TAG = "BasePresenter"
    }
}