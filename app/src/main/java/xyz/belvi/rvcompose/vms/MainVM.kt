package xyz.belvi.rvcompose.vms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import xyz.belvi.rvcompose.factory.ProdFactory
import xyz.belvi.rvcomposelibrary.models.Field

class MainVM : ViewModel() {
    val uiFields = MutableLiveData<MutableList<Field>>()

    fun productUiPage() {

        uiFields.postValue(ProdFactory.dummyUI())

    }

}