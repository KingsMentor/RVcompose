package xyz.belvi.rvcompose.factory

import xyz.belvi.rvcompose.factory.uiFieldsModel.*
import xyz.belvi.rvcomposelibrary.models.Field
import xyz.belvi.rvcomposelibrary.models.rvField
import xyz.belvi.rvcomposelibrary.models.withFields
import java.util.*


object ProdFactory {
    fun sampleUI(): MutableList<Field> {
        return mutableListOf<Field>().withFields {

            this += rvField<InputField> {
                hint = "Customer Email"
                key = "email"
                required = true
                validation = { android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }
            }
            this += rvField<InvoiceDateField> {
                hint = "Invoice Date"
                date = Calendar.getInstance()

            }
            this += rvField<SpinnerField> {
                items = mutableListOf("Pay with Cash", "Pay with Card", "Pay some other time")
            }
            this += rvField<InvoiceDateField> {
                hint = "Due Date"
                date = Calendar.getInstance()

            }
            this += rvField<AdditemField> {
                text = "Add Item"
            }

            this += rvField<InvoiceReceiptField> {
                totalDue = 0.0
            }
            this += rvField<NoteField> {
               hint="additional information for customer"
            }
            this+= rvField<ActionField> {
                text = "Create Invoice"
            }

        }


    }


}