package xyz.belvi.rvcompose.factory

import xyz.belvi.rvcompose.factory.uiFieldsModel.CategoryField
import xyz.belvi.rvcompose.factory.uiFieldsModel.InputField
import xyz.belvi.rvcompose.factory.uiFieldsModel.InvoiceDateField
import xyz.belvi.rvcompose.factory.uiFieldsModel.LabelInfo
import xyz.belvi.rvcomposelibrary.models.Field
import xyz.belvi.rvcomposelibrary.models.rvField
import xyz.belvi.rvcomposelibrary.models.withFields
import java.util.*


object ProdFactory {
    fun dummyUI(): MutableList<Field> {
        return mutableListOf<Field>().withFields {

            this += rvField<InputField> {
                hint = "Customer Email"
                key = "email"
                validation = { android.util.Patterns.EMAIL_ADDRESS.matcher(this.text).matches() }
            }
            this += rvField<InvoiceDateField> {
                hint = "Invoice Date"
                date = Calendar.getInstance()

            }

        }


    }


}