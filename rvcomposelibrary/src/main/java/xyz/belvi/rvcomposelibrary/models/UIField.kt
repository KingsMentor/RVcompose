package xyz.belvi.rvcomposelibrary.models

abstract class UIField<T>(
    var required: Boolean = false,
    var validation: (() -> Boolean)? = null
) : Field()

inline fun <reified T : Field> rvField(action: T.() -> Unit): T {
    val entry = T::class.java.newInstance()
    entry.action()
    return entry

}



