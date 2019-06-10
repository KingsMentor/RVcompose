package xyz.belvi.rvcompose.factory

import xyz.belvi.rvcompose.factory.UIFieldsModel.InputField
import xyz.belvi.rvcompose.factory.UIFieldsModel.LabelInfo
import xyz.belvi.rvcomposelibrary.models.Field
import xyz.belvi.rvcomposelibrary.models.rvField
import xyz.belvi.rvcomposelibrary.models.withFields


object ProdFactory {
    fun dummyUI(): MutableList<Field> {
        return mutableListOf<Field>().withFields {

            this += rvField<LabelInfo> {
                label = "Library Name"
                text = "Rv Compose"
            }
            this += rvField<InputField> {

                hint = "Library Name 2"
                text = "Rv Compose"
            }
        }


    }


}