package com.shaza.billcalculator.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shaza.billcalculator.model.User
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM USER WHERE billId=:billId ORDER BY userName ASC")
    fun selectUses(billId: Int): Single<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User): Single<Long>
}