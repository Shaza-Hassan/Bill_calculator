package com.shaza.billcalculator.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shaza.billcalculator.model.Bill
import com.shaza.billcalculator.model.User

@Database(entities = [Bill::class, User::class], version = 1, exportSchema = true)
abstract class BillCalculatorDatabase : RoomDatabase() {
    abstract fun billDao(): BillDao

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var instance: BillCalculatorDatabase? = null

        fun getDatabase(context: Context): BillCalculatorDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    BillCalculatorDatabase::class.java,
                    "Bill_Database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}