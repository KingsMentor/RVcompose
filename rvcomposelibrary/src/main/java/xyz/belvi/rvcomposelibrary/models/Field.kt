package xyz.belvi.rvcomposelibrary.models

import android.view.View
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter

abstract class Field(
    open val layout: Int = 0,
    var key: String = "",
    var errorMessage: String = ""
) {

    abstract fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (uiComposeAdapter: UIComposeAdapter, field: Field, positon: Int) -> Unit
    )


    abstract fun hasValidData(): Boolean

    open fun matchSearch(text: String): Boolean {
        return true
    }
}

