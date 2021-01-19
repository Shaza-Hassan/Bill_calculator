package com.shaza.billcalculator.model

data class User(var id: Int, var name: String, var totalCost: Double = 0.0, var listOfItems: MutableList<Item> = mutableListOf())
