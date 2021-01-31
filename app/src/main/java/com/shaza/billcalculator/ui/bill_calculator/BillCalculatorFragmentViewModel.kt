package com.shaza.billcalculator.ui.bill_calculator

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shaza.billcalculator.BillApplication
import com.shaza.billcalculator.helper.SingleLiveData
import com.shaza.billcalculator.model.Bill
import com.shaza.billcalculator.model.User
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BillCalculatorFragmentViewModel(private val application: BillApplication) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    val tag = BillCalculatorFragmentViewModel::class.java.simpleName
    val addTaxes = SingleLiveData<Boolean>()
    val addServices = SingleLiveData<Boolean>()
    val addDelivery = SingleLiveData<Boolean>()
    private val noOfPeople = SingleLiveData<Int>()
    private var billId: Long = 0
    private val billName = SingleLiveData<String>()
    private val taxesValue = SingleLiveData<Double>()
    private val servicesValue = SingleLiveData<Double>()
    private val deliveryValue = SingleLiveData<Double>()
    val addedToDB = SingleLiveData<Boolean>()
    val costForEveryPerson = SingleLiveData<Double>()
    val userList = mutableListOf<User>()

    val userListLiveData = SingleLiveData<MutableList<User>>()

    var compositeDisposable = CompositeDisposable()
    var uiErrorLiveData = SingleLiveData<String>()

    init {
        taxesValue.value = 0.0
        servicesValue.value = 0.0
        deliveryValue.value = 0.0
        noOfPeople.value = 1
    }

    fun onCreate() {
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
    }


    fun getData(bundle: Bundle?) {
        addTaxes.value = bundle?.getBoolean("taxes")
        addServices.value = bundle?.getBoolean("services")
        addDelivery.value = bundle?.getBoolean("delivery")
        noOfPeople.value = bundle?.getInt("numberOfPeople")
        billName.value = bundle?.getString("name")
        createUserList()
    }

    private fun createUserList() {
        userList.clear()
        for (i in 1..noOfPeople.value!!) {
            val user = User(name = "User $i")
            userList.add(user)
        }
        userListLiveData.value = userList
    }

    fun updateTaxesValue(taxes: String) {
        taxesValue.value = taxes.toDoubleOrNull()
    }

    fun updateServicesValue(services: String) {
        servicesValue.value = services.toDoubleOrNull()
    }

    fun updateDeliveryValue(delivery: String) {
        deliveryValue.value = delivery.toDoubleOrNull()
    }

    fun createBillInDBAndCalculate() {
        createBillInDB()
    }


    private fun createBillInDB() {
        val bill = Bill(billName = billName.value!!)
        val observable = application.billRepo.insertNewBill(bill).subscribeOn(Schedulers.io())
                .doOnNext {
//                    billId = it
                    calculateCostForEveryPerson(it)
                }.doOnError {
                    uiErrorLiveData.postValue(it.message)
                }
        compositeDisposable.add(observable.subscribe())
    }

    private fun calculateCostForEveryPerson(billId: Long) {
        val sharedItem = (taxesValue.value!! + servicesValue.value!! + deliveryValue.value!!) / noOfPeople.value!!
        for (user in userList) {
            var totalCost = user.listOfItems.map { it.itemPrice }.sum()
            totalCost += sharedItem
            user.totalCost = totalCost
            user.billId = billId
        }
        addUsersInDB()
    }

    private fun addUsersInDB() {
        for (user in userList) {
            val observale = application.userRepo.insertNewBill(user).subscribeOn(Schedulers.io())
                    .doOnNext {
                        Log.v(tag, it.toString())
                    }.doOnError {
                        Log.e(tag, it.message.toString())
                    }
            compositeDisposable.add(observale.subscribe())
        }
        addedToDB.postValue(true)
    }

    fun onDetach() {
        compositeDisposable.dispose()
    }
}

class BillCalculatorViewModelFactory(private val application: BillApplication) :
        ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BillCalculatorFragmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BillCalculatorFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}