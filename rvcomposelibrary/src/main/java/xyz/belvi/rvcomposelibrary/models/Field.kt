package xyz.belvi.rvcomposelibrary.models

import android.view.View
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter

abstract class Field(
    open val layout: Int = 0,
    var key: String = "",
    var errorMessage: String = "",
    var dataSource: Any = Any()
) {

    abstract fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    )


    abstract fun hasValidData(): Boolean

    abstract fun getValue(): Any

    open fun matchSearch(text: String): Boolean {
        return true
    }


}

