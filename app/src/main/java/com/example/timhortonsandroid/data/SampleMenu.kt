package com.example.timhortonsandroid.data

import com.example.timhortonsandroid.model.MenuItem

/**
 * Provides sample Tim Hortons-style products used by the app.
 */
object SampleMenu {

    val items = listOf(
        MenuItem(
            id = 1,
            name = "Original Blend Coffee",
            category = "Coffee",
            price = 2.19,
            description = "Freshly brewed classic coffee."
        ),

        MenuItem(
            id = 2,
            name = "French Vanilla",
            category = "Hot Drinks",
            price = 2.69,
            description = "Sweet and creamy French vanilla."
        ),

        MenuItem(
            id = 3,
            name = "Iced Capp",
            category = "Cold Drinks",
            price = 3.49,
            description = "Creamy blended iced coffee."
        ),

        MenuItem(
            id = 4,
            name = "Boston Cream",
            category = "Donuts",
            price = 1.59,
            description = "Chocolate-topped donut with creamy filling."
        ),

        MenuItem(
            id = 5,
            name = "Everything Bagel",
            category = "Breakfast",
            price = 3.29,
            description = "Toasted everything bagel."
        ),

        MenuItem(
            id = 6,
            name = "Breakfast Sandwich",
            category = "Breakfast",
            price = 4.99,
            description = "Egg and cheese breakfast sandwich."
        )
    )
}