package com.shaza.billcalculator.ui.bill_calculator

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shaza.billcalculator.model.User

class BillCalculatorFragmentViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    val addTaxes = MutableLiveData<Boolean>()
    val addServices = MutableLiveData<Boolean>()
    val addDelivery = MutableLiveData<Boolean>()
    val noOfPeople = MutableLiveData<Int>()

    val taxesValue = MutableLiveData<Double>()
    val servicesValue = MutableLiveData<Double>()
    val deliveryValue = MutableLiveData<Double>()

    val costForEveryPerson = MutableLiveData<Double>()

    val userListLiveData = MutableLiveData<MutableList<User>>()

    init {
        taxesValue.value = 0.0
        servicesValue.value = 0.0
        deliveryValue.value = 0.0
        noOfPeople.value = 1
    }

    fun getData(bundle: Bundle?) {
        addTaxes.value = bundle?.getBoolean("taxes")
        addServices.value = bundle?.getBoolean("services")
        addDelivery.value = bundle?.getBoolean("delivery")
        noOfPeople.value = bundle?.getInt("numberOfPeople")
        createUserList()
    }

    private fun createUserList() {
        val userList = mutableListOf<User>()
        for (i in 1..noOfPeople.value!!) {
            val user = User(id = 1, "User $i")
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

    fun calculateEachOneCost() {
        val sharedItem = (taxesValue.value!! + servicesValue.value!! + deliveryValue.value!!) / noOfPeople.value!!
        for (user in userListLiveData.value!!) {
            var totalCost = user.listOfItems.map { it.itemPrice }.sum()
            totalCost += sharedItem
            user.totalCost = totalCost
        }

        userListLiveData.value!!.forEach {
            Log.v("userTotalCost", it.totalCost.toString())
        }
    }
}