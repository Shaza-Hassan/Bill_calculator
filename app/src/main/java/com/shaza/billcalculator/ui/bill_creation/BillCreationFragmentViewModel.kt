package com.shaza.billcalculator.ui.bill_creation

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shaza.billcalculator.BillApplication
import io.reactivex.disposables.CompositeDisposable

class BillCreationFragmentViewModel(private val application: BillApplication) :
    AndroidViewModel(application) {

    private val tag = BillCreationFragmentViewModel::class.java.simpleName
    var compositeDisposable = CompositeDisposable()

    fun onCreate() {
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
    }

    fun onDetach() {
        compositeDisposable.dispose()
    }
}

class BillCreationViewModelFactory(private val application: BillApplication) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BillCreationFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BillCreationFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}