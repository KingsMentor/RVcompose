package xyz.belvi.rvcomposelibrary.models

abstract class UIField<T, V, D>(
    layout: Int,
    var required: Boolean = false,
    var dataSource: D? = null,
    var validation: ((T) -> Boolean)? = null
) : Field(layout) {


    abstract fun getValue(): V


}

fun <T> rvField(receiver: T, action: T.() -> Unit): T {
    receiver.action()
    return receiver
}



