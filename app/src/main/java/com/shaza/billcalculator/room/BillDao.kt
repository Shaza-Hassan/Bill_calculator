package com.shaza.billcalculator.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shaza.billcalculator.model.Bill
import io.reactivex.Single

@Dao
interface BillDao {

    @Query("SELECT * FROM BILL ORDER BY billId ASC LIMIT :limit offset :offset")
    fun getAllBills(limit: Int, offset: Int): Single<List<Bill>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNewBill(bill: Bill): Single<Long>
}