package com.zaharov.databaseobserver.ui.app

import android.util.Log
import com.zaharov.databaseobserver.ui.base.BasePresenter

class AppPresenter : BasePresenter<AppView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d(TAG, "onFirstViewAttach")
        viewState.onFirstOpen()
    }

    companion object {
        private const val TAG = "AppPresenter"
    }
}