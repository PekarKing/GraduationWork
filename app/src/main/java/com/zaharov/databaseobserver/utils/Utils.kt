package com.zaharov.databaseobserver.utils

import android.app.Application
import android.content.res.Resources
import androidx.annotation.StringRes
import kotlinx.coroutines.Job

object Utils {

    private var application: Application? = null

    fun init(application: Application) {
        this.application = application
    }

    /**
     * Данный метод позволяет отменить Job.
     * Используется для сокращения строчек кода в классах
     */
    fun Job.cancelIfCompleted() {
        if (!isCompleted) {
            cancel()
        }
    }

    /**
     * Метод превращает dp в px и возвращает целочисленное значение
     */
    fun Int.toPx(): Int {
        return (this * Resources.getSystem().displayMetrics.density).toInt()
    }

    /**
     * Возвращает форматированную строку
     */
    @JvmStatic
    fun getString(@StringRes id: Int, vararg parameters: Any): String {
        return application?.getString(id, *parameters)
            ?: throw IllegalStateException("Application context in Utils not initialized. Please call method init in your Application instance")
    }
}