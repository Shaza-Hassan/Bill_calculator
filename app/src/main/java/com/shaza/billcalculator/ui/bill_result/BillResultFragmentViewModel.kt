package com.shaza.billcalculator.ui.bill_result

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.shaza.billcalculator.helper.SingleLiveData
import com.shaza.billcalculator.model.User

class BillResultFragmentViewModel : ViewModel() {

    val userListLiveData = SingleLiveData<MutableList<User>>()

    fun getData(bundle: Bundle) {
        userListLiveData.value = bundle.getParcelableArray("users")?.toList() as MutableList<User>
    }

}