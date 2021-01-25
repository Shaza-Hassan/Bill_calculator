package com.shaza.billcalculator.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = arrayOf("billId"), unique = true)])
data class Bill(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "billId") var billId: Long? = null,
    @ColumnInfo(name = "billName") var billName: String
)