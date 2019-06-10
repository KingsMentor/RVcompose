package xyz.belvi.rvcompose.factory.uiFieldsModel

import android.view.View
import kotlinx.android.synthetic.main.item_info_with_label.view.*
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field
import xyz.belvi.rvcomposelibrary.models.UIField


data class LabelInfo(
    override val layout: Int = R.layout.item_info_with_label,
    var label: String = "",
    var text: String = ""
) :
    UIField<LabelInfo>() {


    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {
        itemView.label_info_title.text = label
        itemView.label_info.text = text


    }

    override fun getValue(): String {
        return text
    }

    override fun hasValidData(): Boolean {
        return true
    }
}
