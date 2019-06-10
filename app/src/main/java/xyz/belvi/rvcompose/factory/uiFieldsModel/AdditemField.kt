package xyz.belvi.rvcompose.factory.uiFieldsModel

import android.view.View
import kotlinx.android.synthetic.main.item_invoice_add_new_item.view.*
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field


data class AdditemField(
    override val layout: Int = R.layout.item_invoice_add_new_item,
    var text: String = ""
) :
    Field() {
    override fun getValue(): Any {
        return "Add Item"
    }

    override fun hasValidData(): Boolean {
        return true
    }

    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {

        itemView.invoice_add_item_text.text = text
        itemView.setOnClickListener {
           event(this)
        }

    }


}

