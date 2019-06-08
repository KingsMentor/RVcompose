package xyz.belvi.rvcompose.factory

import xyz.belvi.rvcompose.factory.UIFieldsModel.LabelInfo
import xyz.belvi.rvcomposelibrary.models.Field
import xyz.belvi.rvcomposelibrary.models.rvField


object ProdFactory {
    fun dummyUI(): MutableList<Field> {
        return mutableListOf<Field>().apply {
            add(rvField<LabelInfo> {
                label = "Library Name"
                text = "Rv Compose"
            }
            )
        }

    }


}