package xyz.belvi.rvcompose.factory.uiFieldsModel

import android.view.View
import kotlinx.android.synthetic.main.item_invoice_receipt.view.*
import xyz.belvi.rvcompose.R
import xyz.belvi.rvcomposelibrary.adapter.UIComposeAdapter
import xyz.belvi.rvcomposelibrary.models.Field


data class InvoiceReceiptField(
    var totalDue: Double = 0.0
) :
    Field(R.layout.item_invoice_receipt) {
    override fun bind(
        itemView: View,
        uiComposeAdapter: UIComposeAdapter,
        position: Int,
        event: (field: Field) -> Unit
    ) {
        itemView.receipt_sub_total.text = String.format("%,.2f", totalDue)
    }
    override fun getValue(): Any {
        return ""
    }
    override fun hasValidData(): Boolean {
        return true
    }
}

