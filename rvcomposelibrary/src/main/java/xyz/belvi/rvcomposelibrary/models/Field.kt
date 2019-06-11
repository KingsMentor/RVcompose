package xyz.belvi.rvcomposelibrary.models

import android.view.View
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter

abstract  class Field(
    open val layout: Int = 0,
    var required: Boolean = false,
    var key: String = "",
    var errorMessage: String = "",
    var dataSource: Any = Any(),
    var validation: (() -> Boolean)? = null
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

inline fun <reified T : Field> rvField(action: T.() -> Unit): T {
    val entry = T::class.java.newInstance()
    entry.action()
    return entry

}


fun MutableList<Field>.withFields(block: MutableList<Field>.() -> Unit): MutableList<Field> {
    this.addAll( mutableListOf<Field>().apply(block))
    return this
}
