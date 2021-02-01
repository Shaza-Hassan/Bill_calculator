package com.shaza.billcalculator.ui.bill_result

import android.os.Bundle
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shaza.billcalculator.BillApplication
import com.shaza.billcalculator.helper.SingleLiveData
import com.shaza.billcalculator.model.User
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BillResultFragmentViewModel(private val application: BillApplication) : AndroidViewModel(application) {

    val userListLiveData = SingleLiveData<MutableList<User>>()

    val billIdLiveData = SingleLiveData<Int>()
    var compositeDisposable = CompositeDisposable()
    var uiErrorLiveData = MutableLiveData<String>()
    fun onCreate() {
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
    }

    fun getData(bundle: Bundle) {

        if (bundle.getParcelableArray("users") != null) {
            userListLiveData.value = bundle.getParcelableArray("users")?.toList() as MutableList<User>
        }
        if (bundle.getInt("billId") != null) {
            billIdLiveData.value = bundle.getInt("billId")
        }
    }

    fun getAllUsersForThisBill(billId: Int) {

        val observable = application.userRepo.getAllUsersForThisBill(billId).subscribeOn(Schedulers.io())
                .doOnNext {
                    userListLiveData.postValue(it as MutableList<User>?)
                }.doOnError {
                    uiErrorLiveData.postValue(it.message)
                }

        observable.subscribe()

    }

    fun onDetach() {
        compositeDisposable.dispose()
    }
}

class BillResultViewModelFactory(private val application: BillApplication) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BillResultFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BillResultFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}