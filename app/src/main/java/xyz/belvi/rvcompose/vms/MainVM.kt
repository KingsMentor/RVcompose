package xyz.belvi.rvcompose.vms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.belvi.rvcompose.factory.ProdFactory
import xyz.belvi.rvcompose.factory.uiFieldsModel.InputField
import xyz.belvi.rvcomposelibrary.models.Field

open class MainVM : ViewModel() {
    val uiFields = MutableLiveData<MutableList<Field>>()

    fun productUiPage() {

        uiFields.postValue(ProdFactory.sampleUI())
        InputField()


    }


}

