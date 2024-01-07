package com.github.zavier.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.math.BigDecimal

val orderStorage = listOf(
    Order(
        "2020-04-06-01", listOf(
            OrderItem("Ham Sandwich", 2, BigDecimal("5.50")),
            OrderItem("Water", 2, BigDecimal("1.50")),
            OrderItem("Beer", 3, BigDecimal("2.30")),
            OrderItem("Cheesecake", 1, BigDecimal("3.75")),
        )
    ), Order(
        "2020-04-03-01", listOf(
            OrderItem("Cheeseburger", 1, BigDecimal("8.50")),
            OrderItem("Water", 2, BigDecimal("1.50")),
            OrderItem("Coke", 2, BigDecimal("1.76")),
            OrderItem("Ice Cream", 1, BigDecimal("2.35")),
        )
    )
)

@Serializable
data class Order(val number: String, val content: List<OrderItem>)

@Serializable
data class OrderItem(val item: String, val amount: Int, @Contextual val price: BigDecimal)