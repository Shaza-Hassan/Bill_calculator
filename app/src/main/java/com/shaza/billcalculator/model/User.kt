package com.shaza.billcalculator.model

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Bill::class,
            parentColumns = arrayOf("billId"),
            childColumns = arrayOf("billId"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "userId") var id: Long? = null,
    @ColumnInfo(name = "userName") var name: String = "",
    @ColumnInfo(name = "totalCost") var totalCost: Double = 0.0,
    @ColumnInfo(name = "billId") var billId: Long = 0,
    @Ignore var listOfItems: @RawValue MutableList<Item> = mutableListOf()
) : Parcelable
