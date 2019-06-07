package xyz.belvi.rvcompose.Factory

import android.view.View
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field
import xyz.belvi.rvcomposelibrary.models.UIField

open class Samp : UIField<Samp, String, String>(R.layout.text) {

    override fun getValue(): String {
        return ""
    }

    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (uiComposeAdapter: UIComposeAdapter, field: Field, positon: Int) -> Unit
    ) {
        itemView.setOnClickListener {
            event(uiComposeAdapter, this, position)
        }
    }

    override fun hasValidData(): Boolean {
        return if (required) (validation?.invoke(this) ?: kotlin.run { false }) else false
    }

    override fun matchSearch(text: String): Boolean {
        return true
    }


}
