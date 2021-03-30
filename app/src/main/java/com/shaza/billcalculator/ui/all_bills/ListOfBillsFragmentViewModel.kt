package com.shaza.billcalculator.ui.all_bills

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.example.drdbasemodule.pagination.BillDataSource
import com.shaza.billcalculator.BillApplication
import com.shaza.billcalculator.model.Bill
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

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

    fun getBills() {

    }

    fun getAllBills(): Flowable<PagingData<Bill>> {

        return Pager(PagingConfig(pageSize = 10, prefetchDistance = 5, enablePlaceholders = true)) {
            BillDataSource(application.billRepo, 0, 5)
        }.flowable.cachedIn(viewModelScope)
//        val observable = application.billRepo.allBills.subscribeOn(Schedulers.io())
//            .doOnNext {
//                allBills.postValue(it)
//            }.doOnError {
//                    uiErrorLiveData.postValue(it.message)
//            }
//
//        compositeDisposable.add(observable.subscribe())
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