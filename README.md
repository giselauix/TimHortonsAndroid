# TimHortonsAndroid

## Android Coffee Run Ordering App

**Student:** Gisela Burgos
**Program:** Mobile & Web Development Using AI
**Institution:** triOS College
**Platform:** Android
**Language:** Kotlin
**UI Framework:** Jetpack Compose

---

## Project Overview

TimHortonsAndroid is an Android adaptation of a coffee-run ordering application originally designed for iOS.

The purpose of the app is to help a team organize daily Tim Hortons orders without writing each person's order on paper.

Users can enter a team member's name, select menu items, save multiple orders, reuse previous selections, mark favorite orders, track the total coffee run cost, and use a built-in countdown timer.

The application was developed using Kotlin, Android Studio, Jetpack Compose, Git, GitHub, and DataStore Preferences.

---

## Main Features

### Team Member Orders

Users can enter the name of a team member and create a personalized order.

### Tim Hortons-Style Menu

The application includes sample menu items such as:

* Original Blend Coffee
* French Vanilla
* Iced Capp
* Boston Cream
* Everything Bagel
* Breakfast Sandwich

Each item includes:

* Product name
* Category
* Description
* Price

### Product Selection

Menu items can be selected or deselected using interactive cards and checkboxes.

### Automatic Order Total

The application automatically calculates:

* Number of selected items
* Individual order total
* Complete coffee run total

### Multiple Orders

Multiple team-member orders can be saved during the same coffee run.

### Coffee Run Summary

The app displays:

* Number of team members
* Total number of items
* Combined coffee run price

### Reusable Orders

Previously created orders can be loaded back into the current order form using the **Reuse Order** option.

This is helpful when team members frequently order the same products.

### Favorite Orders

Orders can be marked as favorites.

Favorite selections are stored locally using Android DataStore.

### Persistent Storage

Favorite orders remain available after the app is closed and reopened.

This functionality is implemented with:

```text
Android DataStore Preferences
```

### Coffee Run Timer

A built-in countdown timer helps organize the coffee run.

The timer supports:

* Start
* Pause
* Reset

### Professional Jetpack Compose Interface

The interface uses:

* Material Design cards
* Rounded corners
* Icons
* Scrollable content
* Form controls
* Reusable composable functions
* Responsive layouts

---

## Technologies Used

* Kotlin
* Android Studio
* Jetpack Compose
* Material Design 3
* Android DataStore Preferences
* Coroutines
* Git
* GitHub

---

## Kotlin Concepts Demonstrated

This project demonstrates:

* Data classes
* Objects
* Lists
* Mutable state
* Functions
* Extension functions
* Lambdas
* Collections
* Higher-order functions
* String formatting
* Conditional logic
* Coroutines
* Local persistence
* Reusable code organization

---

## Project Structure

```text
TimHortonsAndroid
│
├── app
│   └── src
│       └── main
│           ├── java
│           │   └── com.example.timhortonsandroid
│           │       │
│           │       ├── data
│           │       │   ├── OrderPreferences.kt
│           │       │   └── SampleMenu.kt
│           │       │
│           │       ├── model
│           │       │   ├── CoffeeOrder.kt
│           │       │   └── MenuItem.kt
│           │       │
│           │       ├── ui
│           │       │   ├── CoffeeRunTimer.kt
│           │       │   ├── HomeScreen.kt
│           │       │   └── theme
│           │       │
│           │       └── MainActivity.kt
│           │
│           ├── res
│           └── AndroidManifest.xml
│
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

---

## Application Flow

```text
Launch App
    ↓
Enter Team Member Name
    ↓
Select Menu Items
    ↓
Review Current Order
    ↓
Save Order
    ↓
Add Additional Team Orders
    ↓
View Coffee Run Summary
    ↓
Reuse or Favorite Saved Orders
```

---

## Persistent Favorites

Favorite orders are stored using DataStore Preferences.

The app observes saved favorite data using a Kotlin Flow and updates the interface automatically when the stored values change.

This means favorite orders are preserved between app launches.

---

## Testing

The application was manually tested in an Android emulator.

The following functionality was verified:

* App launches correctly
* Menu items display correctly
* Product selection works
* Product deselection works
* Order totals calculate correctly
* Multiple orders can be saved
* Coffee run totals calculate correctly
* Orders can be reused
* Orders can be marked as favorites
* Favorites persist after closing the application
* Coffee Run Timer starts correctly
* Coffee Run Timer pauses correctly
* Coffee Run Timer resets correctly
* Scrolling works correctly
* No crashes occurred during normal testing

---

## How to Run

1. Clone or download the repository.
2. Open the project in Android Studio.
3. Allow Gradle synchronization to complete.
4. Select an Android emulator or physical Android device.
5. Click **Run**.
6. Enter a team member name.
7. Select menu products.
8. Save the order.
9. Create additional orders or reuse saved selections.

---

## Assignment Alignment

This Android version preserves the main goal of the original coffee-run assignment:

* Replace handwritten team orders with a mobile application
* Record individual selections
* Reuse frequently selected orders
* Implement core mobile UI functionality
* Add a creative coffee-run timer
* Maintain clear and appropriately commented code

---

## Future Improvements

Possible future enhancements include:

* Room database integration
* User accounts
* Product images
* Real Tim Hortons API integration
* Quantity selection
* Custom drink sizes
* Sugar and cream options
* Push notifications
* Cloud synchronization
* Order history
* Dark mode

---

## Author

**Amalia Bonilla**

Mobile & Web Development Using AI
triOS College

---

## Academic Purpose

This project was created for educational purposes to demonstrate Android application development with Kotlin and Jetpack Compose.

---

## License

This project is intended for educational and portfolio use.
