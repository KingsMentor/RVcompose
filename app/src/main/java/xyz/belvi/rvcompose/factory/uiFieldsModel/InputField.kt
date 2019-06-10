package xyz.belvi.rvcompose.factory.uiFieldsModel

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import kotlinx.android.synthetic.main.item_input.view.*
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field


data class InputField(
    override val layout: Int = R.layout.item_input,
    var hint: String = "",
    var text: String = ""
) :
    Field() {


    override fun getValue(): String {
        return text
    }

    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {
        itemView.item_field.setText(text)
        itemView.item_field.hint = hint
        itemView.item_field.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                text = s.toString()
                event(this@InputField)
            }
        })

    }

    override fun hasValidData(): Boolean {
        return validation?.invoke() ?: kotlin.run { if (required) !text.isBlank() else true }
    }

}
