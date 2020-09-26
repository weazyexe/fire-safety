package dev.weazyexe.firesafety.utils.extensions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

fun <T> ViewModel.subscribe(
    observable: Observable<T>,
    onNext: (T) -> Unit,
    onError: (t: Throwable) -> Unit
): Disposable = observable
    .subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe(onNext, onError)

fun <T : ViewModel> ViewModelStoreOwner.useViewModel(owner: ViewModelStoreOwner, vmClass: Class<T>): T {
    return ViewModelProvider(owner, ViewModelProvider.NewInstanceFactory()).get(vmClass)
}

fun <T> MutableLiveData<T>.default(value: T): MutableLiveData<T> {
    postValue(value)
    return this
}