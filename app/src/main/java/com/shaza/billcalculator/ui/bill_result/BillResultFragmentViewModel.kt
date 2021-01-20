package com.shaza.billcalculator.ui.bill_result

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shaza.billcalculator.model.User

class BillResultFragmentViewModel : ViewModel() {

    val userListLiveData = MutableLiveData<MutableList<User>>()

    fun getData(bundle: Bundle) {
        userListLiveData.value = bundle.getParcelableArray("users")?.toList() as MutableList<User>
    }

}