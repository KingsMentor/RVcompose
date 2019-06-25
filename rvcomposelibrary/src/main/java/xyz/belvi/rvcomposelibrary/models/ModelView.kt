package xyz.belvi.rvcomposelibrary.models

import android.view.View
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter

abstract class Field(
    open val layout: Int ,
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

    /**
     * the value that this field should return when called in @UIComposeAdapter or in cases when RVcompose is used as form builder
     */

    abstract fun getValue(): Any

    /**
     * define criteria for including this field in a search result
     */
    open fun matchSearch(text: String): Boolean {
        return true
    }

    /**
     * perform a validation check on this field @Field
     */
    open fun hasValidData(): Boolean {
        return if (required) validation?.invoke() ?: kotlin.run { false } else true
    }


}

inline fun <reified T : Field> rvField(action: T.() -> Unit): T {
    val entry = T::class.java.newInstance()
    entry.action()
    return entry

}

fun MutableList<Field>.withFields(block: MutableList<Field>.() -> Unit): MutableList<Field> {
    this.addAll(mutableListOf<Field>().apply(block))
    return this
}
