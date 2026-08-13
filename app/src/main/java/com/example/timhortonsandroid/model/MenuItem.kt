package com.example.timhortonsandroid.model

/**
 * Represents one product available in the Tim Hortons menu.
 *
 * @property id unique identifier for the menu item
 * @property name display name of the product
 * @property category category used to organize menu items
 * @property price product price
 * @property description short description shown to the user
 */
data class MenuItem(
    val id: Int,
    val name: String,
    val category: String,
    val price: Double,
    val description: String
)