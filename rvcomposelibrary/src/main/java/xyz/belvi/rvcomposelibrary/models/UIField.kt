package xyz.belvi.rvcomposelibrary.models

abstract class UIField<T, V, D>(
    var required: Boolean = false,
    var dataSource: D? = null,
    var validation: (() -> Boolean)? = null
) : Field() {


    abstract fun getValue(): V


}

inline fun <reified T : Field> rvField(action: T.() -> Unit): T {
    val entry = T::class.java.newInstance()
    entry.action()
    return entry

}



