package xyz.belvi.rvcomposelibrary

import android.view.View

abstract class Field(
    val layout: Int,
    var key: String = "",
    var errorMessage: String = ""
) {


    abstract fun bind(itemView: View, factoryEventListener: (field: Field) -> Unit)

    abstract fun hasValidData(): Boolean

    abstract fun matchSearch(text: String): Boolean
}