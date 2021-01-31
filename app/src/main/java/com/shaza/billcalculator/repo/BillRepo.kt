package com.shaza.billcalculator.repo

import com.shaza.billcalculator.model.Bill
import com.shaza.billcalculator.room.BillDao
import io.reactivex.Observable

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

    fun insertNewBill(bill: Bill): Observable<Long> {
        return billDao.insertNewBill(bill).toObservable()
    }
}