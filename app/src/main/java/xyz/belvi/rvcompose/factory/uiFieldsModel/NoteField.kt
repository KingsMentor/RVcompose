package xyz.belvi.rvcompose.factory.uiFieldsModel

import android.view.View
import kotlinx.android.synthetic.main.item_input_note.view.*
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field

data class NoteField(
    override val layout: Int = R.layout.item_input_note,
    var hint: String="",
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
        itemView.item_note.setText(text)
        itemView.item_note.hint = hint


    }

    override fun hasValidData(): Boolean {
        return if (required) !text.isBlank() else true
    }
}