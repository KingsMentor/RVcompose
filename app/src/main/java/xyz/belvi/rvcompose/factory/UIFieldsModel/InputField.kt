package xyz.belvi.rvcompose.factory.UIFieldsModel

import android.text.InputType
import android.view.View
import kotlinx.android.synthetic.main.item_input.view.*
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field
import xyz.belvi.rvcomposelibrary.models.UIField


data class InputField(
    override val layout: Int,
    var hint: String="",
    var text: String = ""
) :
    UIField<InputField>() {

    constructor() : this(R.layout.item_input)

    override fun getValue(): String {
        return text
    }

    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (uiComposeAdapter: UIComposeAdapter, field: Field, positon: Int) -> Unit
    ) {
        itemView.item_field.setText(text)
        itemView.item_field.hint = hint

    }

    override fun hasValidData(): Boolean {
        return validation?.invoke() ?: kotlin.run { if (required) !text.isBlank() else true }
    }

}
