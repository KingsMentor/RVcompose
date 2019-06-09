package xyz.belvi.rvcomposelibrary

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.compose.RVcomposeHandler
import xyz.belvi.rvcomposelibrary.compose.RVcomposeImp
import xyz.belvi.rvcomposelibrary.models.Field

class RVcompose internal constructor(
    private val recyclerView: RecyclerView
) {

    private lateinit var clickEvent: (uiComposeAdapter: UIComposeAdapter, field: Field, position: Int) -> Unit
    var items = mutableListOf<Field>()

    fun withLayoutManager(layoutManager: RecyclerView.LayoutManager): RVcompose {
        recyclerView.layoutManager = layoutManager
        return this
    }

    fun fieldClicked(event: (uiComposeAdapter: UIComposeAdapter, field: Field, position: Int) -> Unit): RVcompose {
        clickEvent = event
        return this
    }

    inline fun <reified T : Field> withField(item: T.() -> Unit): RVcompose {
        val entry = T::class.java.newInstance()
        entry.item()
        this.items.add(entry)
        return this
    }


    internal fun toAttached(): RVcomposeImp {
        return RVcomposeImp(UIComposeAdapter(items) { uiComposeAdapter, field, position ->
            clickEvent(uiComposeAdapter, field, position)
        })
    }
}

fun RecyclerView.compose(block: RVcompose.() -> Unit): RVcomposeHandler {
    val setup = RVcompose(this).apply(block)
    if (layoutManager == null) {
        layoutManager = LinearLayoutManager(context)
    }

    return setup.toAttached().also {
        adapter = it.uiComposeAdapter
    }
}