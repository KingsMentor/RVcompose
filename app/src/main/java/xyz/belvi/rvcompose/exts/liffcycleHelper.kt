package  xyz.belvi.rvcompose.exts

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*


inline fun <reified K : ViewModel> FragmentActivity.getViewModel(viewModelFactory: ViewModelProvider.Factory): K {
    return ViewModelProviders.of(this, viewModelFactory).get(K::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.withViewModel(
    viewModelFactory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = getViewModel<T>(viewModelFactory)
    vm.body()
    return vm
}

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) {
    liveData.observe(this, Observer(body))
}

