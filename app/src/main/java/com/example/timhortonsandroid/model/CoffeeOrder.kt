package com.example.timhortonsandroid.model

/**
 * Represents a coffee-run order for one team member.
 *
 * The order can later be saved and reused when a person
 * frequently orders the same products.
 */
data class CoffeeOrder(
    val personName: String,
    val items: List<MenuItem>,
    val isFavorite: Boolean = false
) {

    /**
     * Calculates the total cost of all products in the order.
     */
    fun totalPrice(): Double {
        return items.sumOf { item ->
            item.price
        }
    }
}