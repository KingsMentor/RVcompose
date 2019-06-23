package xyz.belvi.rvcompose.factory.uiFieldsModel

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_spinner_no_title.view.*
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field


data class SpinnerField(
    var items: MutableList<String> = mutableListOf(),
    var selectedPosition: Int = 0
) :
    Field(R.layout.item_spinner_no_title) {

    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {
        val spinnerAdapter =
            ArrayAdapter<String>(itemView.context, R.layout.item_spinner_drpdown, items)
        spinnerAdapter.setDropDownViewResource(R.layout.item_spinner_drpdown_up)
        itemView.form_spinner.adapter = spinnerAdapter
        var firstCall = true
        itemView.form_spinner.setSelection(selectedPosition)
        itemView.form_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (firstCall) {
                    firstCall = false
                    return
                }
                selectedPosition = position
                event(this@SpinnerField)


            }
        }
    }

    override fun hasValidData(): Boolean {
        return true
    }

    override fun getValue(): Any {
        return items[selectedPosition]
    }

}

