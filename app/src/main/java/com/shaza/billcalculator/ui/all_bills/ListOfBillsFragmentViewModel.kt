package com.shaza.billcalculator.ui.all_bills

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shaza.billcalculator.BillApplication
import com.shaza.billcalculator.model.Bill
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ListOfBillsFragmentViewModel(private var application: BillApplication) :
    AndroidViewModel(application) {

    private val tag = ListOfBillsFragmentViewModel::class.java.simpleName
    val allBills = MutableLiveData<List<Bill>>()
    var compositeDisposable = CompositeDisposable()
    var uiErrorLiveData = MutableLiveData<String>()
    fun onCreate() {
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
    }

    fun getAllBills() {
        val observable = application.billRepo.allBills.subscribeOn(Schedulers.io())
            .doOnNext {
                allBills.postValue(it)
            }.doOnError {
                    uiErrorLiveData.postValue(it.message)
            }

        compositeDisposable.add(observable.subscribe())
    }


    fun onDetach() {
        compositeDisposable.dispose()
    }
}

class ListOfBillsViewModelFactory(private val application: BillApplication) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListOfBillsFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ListOfBillsFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}