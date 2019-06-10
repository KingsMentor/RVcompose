package xyz.belvi.rvcompose.factory.uiFieldsModel

import android.view.View
import kotlinx.android.synthetic.main.item_btn_action.view.*
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field


data class ActionField(
    override val layout: Int = R.layout.item_btn_action,
    var text: String = ""
) :
    Field() {
    override fun getValue(): Any {
        return text
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

        itemView.btn_action_field.text = text
        itemView.btn_action_field.setOnClickListener {
            event(this)
        }

    }


}

