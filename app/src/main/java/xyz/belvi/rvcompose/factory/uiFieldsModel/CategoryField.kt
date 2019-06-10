package xyz.belvi.rvcompose.factory.uiFieldsModel

import android.view.View
import kotlinx.android.synthetic.main.item_category.view.*
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field
import xyz.belvi.rvcomposelibrary.models.UIField


data class CategoryField(
    override val layout: Int = R.layout.item_category,
    var name: String = ""
) :
    UIField<CategoryField>() {

    override fun getValue(): String {
        return name
    }


    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {
        itemView.category_title.text = name
        itemView.setOnClickListener {
            event(this@CategoryField)
        }
    }

    override fun hasValidData(): Boolean {
        return true
    }

}
