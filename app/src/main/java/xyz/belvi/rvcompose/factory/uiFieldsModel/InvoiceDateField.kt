package xyz.belvi.rvcompose.factory.uiFieldsModel

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.view.View
import kotlinx.android.synthetic.main.item_invoice_date.view.*
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field
import xyz.belvi.rvcomposelibrary.models.UIField
import java.text.SimpleDateFormat
import java.util.*


data class InvoiceDateField(
    override val layout: Int = R.layout.item_invoice_date,
    var hint: String ="",
    var date: Calendar = Calendar.getInstance()
) :
    UIField<InvoiceDateField>() {
    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {
        itemView.invoice_date_hint.text = hint
        itemView.invoice_date_txt.text =
            ("${date.get(Calendar.DAY_OF_MONTH)}/${(date.get(Calendar.MONTH) + 1)}/${date.get(
                Calendar.YEAR
            )}")

        itemView.setOnClickListener {

            val picker = DatePickerDialog(
                it.context,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    val calendar = Calendar.getInstance()
                    calendar.apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    }
                    date = calendar
                    dataSource = calendar
                    itemView.invoice_date_txt.text =
                        ("${date.get(Calendar.DAY_OF_MONTH)}/${(date.get(Calendar.MONTH) + 1)}/${date.get(
                            Calendar.YEAR
                        )}")
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            picker.datePicker.minDate = Calendar.getInstance().timeInMillis
            picker.show()
        }
    }

    override fun hasValidData(): Boolean {
        return true
    }

    @SuppressLint("SimpleDateFormat")
    override fun getValue(): String {
        return SimpleDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'").format(Date(date.timeInMillis))
    }
}

