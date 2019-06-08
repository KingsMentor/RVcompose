package xyz.belvi.rvcompose.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object MainVMFactory :
    ViewModelProvider.NewInstanceFactory() {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainVM() as T
    }
}