package com.shaza.billcalculator.repo

import com.shaza.billcalculator.model.Bill
import com.shaza.billcalculator.room.BillDao
import io.reactivex.Observable
import io.reactivex.Single

class BillRepo(private val billDao: BillDao) {

    companion object {
        @Volatile
        private var instanceBillRepo: BillRepo? = null

        fun getRepo(billDao: BillDao): BillRepo {
            return instanceBillRepo ?: synchronized(this) {
                val instance = BillRepo(billDao)
                instanceBillRepo = instance
                instance
            }
        }
    }

    val allBills: Observable<List<Bill>> = billDao.getAllBills().toObservable()

    fun insertNewBill(bill: Bill): Single<Long> {
        return billDao.insertNewBill(bill)
    }
}