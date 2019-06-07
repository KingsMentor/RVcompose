package xyz.belvi.rvcomposelibrary.compose

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field

class RVcompose internal constructor(
    private val recyclerView: RecyclerView
) {

    private lateinit var clickEvent: (uiComposeAdapter: UIComposeAdapter,field: Field, position: Int) -> Unit
    private var items = mutableListOf<Field>()

    fun withLayoutManager(layoutManager: RecyclerView.LayoutManager): RVcompose {
        recyclerView.layoutManager = layoutManager
        return this
    }

    fun fieldClicked(event: (uiComposeAdapter: UIComposeAdapter, field: Field, position: Int) -> Unit): RVcompose {
        clickEvent = event
        return this
    }

    fun withFields(items: MutableList<Field>.() -> MutableList<Field>): RVcompose {
        this.items = items(mutableListOf())
        return this
    }


    internal fun toAttached(): RVcomposeImp {
        return RVcomposeImp(UIComposeAdapter(items) { uiComposeAdapter,field, position ->
            clickEvent(uiComposeAdapter,field, position)
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