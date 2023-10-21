package com.example.vegitrace.model

data class Order(
    val orderId: String,
    val supplier: String,
    val vegetableType: String,
    val quantity: String,
    val price: String,
    val centre: String,
    val status: String,
    val farmer: String
) {
    constructor() : this("", "", "", "", "", "", "Pending", "")
}