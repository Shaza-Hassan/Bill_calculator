package com.shaza.billcalculator.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class User(
        var id: Int,
        var name: String,
        var totalCost: Double = 0.0,
        var listOfItems: @RawValue MutableList<Item> = mutableListOf()
) : Parcelable
