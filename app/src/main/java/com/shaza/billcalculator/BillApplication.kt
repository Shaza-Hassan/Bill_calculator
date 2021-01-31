package com.shaza.billcalculator

import android.app.Application
import com.shaza.billcalculator.repo.BillRepo
import com.shaza.billcalculator.repo.UserRepo
import com.shaza.billcalculator.room.BillCalculatorDatabase

class BillApplication : Application() {

    private val database by lazy { BillCalculatorDatabase.getDatabase(this) }
    val billRepo by lazy { BillRepo(database.billDao()) }
    val userRepo by lazy { UserRepo(database.userDao()) }
}