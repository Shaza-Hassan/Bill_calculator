package com.shaza.billcalculator.ui.bill_creation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

class BillCreationFragmentViewModel : ViewModel() {

    private val tag = BillCreationFragmentViewModel::class.java.simpleName
    val noOfPersonsOnBillLD = MutableLiveData<Int>()
    val billNameLD = MutableLiveData<String>()
    val taxesLD = MutableLiveData<Boolean>()
    val servicesLD = MutableLiveData<Boolean>()
    val deliveryLD = MutableLiveData<Boolean>()

    var compositeDisposable = CompositeDisposable()

    init {
        taxesLD.value = false
        servicesLD.value = false
        deliveryLD.value = false
    }

    fun updateBillName(billName: String) {
        billNameLD.value = billName
    }

    fun updateNoOfPerson(noOfPerson: Int?) {
        noOfPersonsOnBillLD.value = noOfPerson
    }

    fun updateTaxes(checkTaxes: Boolean) {
        taxesLD.value = checkTaxes
    }

    fun updateServices(checkServices: Boolean) {
        servicesLD.value = checkServices
    }

    fun updateDelivery(checkDelivery: Boolean) {
        deliveryLD.value = checkDelivery
    }

    fun checkEmptyOfNoOfPerson(): Boolean {
        return noOfPersonsOnBillLD.value == null
    }

    fun checkRightValueOfNoOfPerson(): Boolean {
        return noOfPersonsOnBillLD.value != null && noOfPersonsOnBillLD.value!! > 0
    }

    fun onCreate() {
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
    }

    fun onDetach() {
        compositeDisposable.dispose()
    }
}