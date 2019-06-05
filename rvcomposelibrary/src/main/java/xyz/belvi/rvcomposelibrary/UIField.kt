package xyz.belvi.rvcomposelibrary

import android.view.View

abstract class UIField<T, V>(
    layout: Int,
    var required: Boolean = false,
    var dataSource: Any = Any(),
    var validation: (() -> Boolean)? = null
) : Field(layout) {


    fun required(required: Boolean): T {
        this.required = required
        return this as T
    }

    fun key(key: String): T {
        this.key = key
        return this as T
    }

    fun dataSource(dataSource: Any): T {
        this.dataSource = dataSource
        return this as T
    }


    fun errorMessage(message: String): T {
        this.errorMessage = message
        return this as T
    }

    fun validation(validation: (() -> Boolean)?): T {
        this.validation = validation
        return this as T
    }


    abstract fun getValue(): V


}