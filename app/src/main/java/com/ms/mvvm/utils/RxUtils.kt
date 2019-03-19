package com.ms.mvvm.utils



import androidx.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.Observable.create

class RxUtils {

    companion object {

        @JvmStatic
        fun <T> toObservable(observableField: ObservableField<T>): Observable<T> {
            return create { emitter ->
                val callback = object : androidx.databinding.Observable.OnPropertyChangedCallback() {
                    override fun onPropertyChanged(
                        dataBindingObservable: androidx.databinding.Observable,
                        propertyId: Int
                    ) {
                        if (dataBindingObservable === observableField) {
                            emitter.onNext(observableField.get()!!)
                        }
                    }
                }
                observableField.addOnPropertyChangedCallback(callback);
                emitter.setCancellable {
                    observableField.removeOnPropertyChangedCallback(callback);
                }
            }
        }
    }
}