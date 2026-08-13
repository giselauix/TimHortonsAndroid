package com.example.timhortonsandroid.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.timhortonsandroid.data.OrderPreferences
import com.example.timhortonsandroid.data.SampleMenu
import com.example.timhortonsandroid.model.CoffeeOrder
import com.example.timhortonsandroid.model.MenuItem
import kotlinx.coroutines.launch

/**
 * Main screen of the Tim Hortons team coffee-run application.
 *
 * Provides menu selection, multiple team orders, reusable favorites,
 * persistent favorite storage, order totals, and the coffee-run timer.
 */
@Composable
fun HomeScreen() {

    var personName by remember {
        mutableStateOf("")
    }

    val selectedItems = remember {
        mutableStateListOf<MenuItem>()
    }

    val savedOrders = remember {
        mutableStateListOf<CoffeeOrder>()
    }

    val context = LocalContext.current

    val orderPreferences = remember {
        OrderPreferences(context)
    }

    val coroutineScope = rememberCoroutineScope()

    val persistentFavorites = remember {
        mutableStateListOf<String>()
    }

    // Observe favorites stored locally with DataStore.
    LaunchedEffect(Unit) {
        orderPreferences.favoriteOrders.collect { favorites ->
            persistentFavorites.clear()
            persistentFavorites.addAll(favorites)
        }
    }

    Scaffold { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // MARK: App Header

            item {
                AppHeader()
            }

            // MARK: Team Member

            item {

                OutlinedTextField(
                    value = personName,
                    onValueChange = {
                        personName = it
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text("Team member name")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Groups,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )
            }

            // MARK: Coffee Run Timer

            item {
                CoffeeRunTimer()
            }

            // MARK: Menu

            item {

                SectionHeader(
                    title = "Menu",
                    subtitle = "Select items for this team member"
                )
            }

            items(
                items = SampleMenu.items,
                key = { menuItem ->
                    menuItem.id
                }
            ) { menuItem ->

                val isSelected =
                    selectedItems.contains(menuItem)

                MenuItemCard(
                    menuItem = menuItem,
                    isSelected = isSelected,
                    onSelectionChanged = {

                        if (isSelected) {
                            selectedItems.remove(menuItem)
                        } else {
                            selectedItems.add(menuItem)
                        }
                    }
                )
            }

            // MARK: Current Order

            item {

                OrderSummaryCard(
                    personName = personName,
                    selectedItems = selectedItems,
                    onSaveOrder = {

                        if (
                            personName.isNotBlank() &&
                            selectedItems.isNotEmpty()
                        ) {

                            savedOrders.add(
                                CoffeeOrder(
                                    personName =
                                        personName.trim(),
                                    items =
                                        selectedItems.toList()
                                )
                            )

                            personName = ""
                            selectedItems.clear()
                        }
                    }
                )
            }

            // MARK: Persistent Favorites

            if (persistentFavorites.isNotEmpty()) {

                item {

                    PersistentFavoritesCard(
                        favorites = persistentFavorites
                    )
                }
            }

            // MARK: Complete Coffee Run

            if (savedOrders.isNotEmpty()) {

                item {

                    CoffeeRunSummaryCard(
                        orders = savedOrders
                    )
                }

                item {

                    SectionHeader(
                        title = "Saved Orders",
                        subtitle =
                            "Reuse or favorite a team member's order"
                    )
                }

                items(
                    items = savedOrders,
                    key = { order ->
                        "${order.personName}-${order.hashCode()}"
                    }
                ) { order ->

                    SavedOrderCard(
                        order = order,

                        onReuseOrder = {

                            personName =
                                order.personName

                            selectedItems.clear()

                            selectedItems.addAll(
                                order.items
                            )
                        },

                        onToggleFavorite = {

                            val index =
                                savedOrders.indexOf(order)

                            if (index >= 0) {

                                val updatedOrder =
                                    order.copy(
                                        isFavorite =
                                            !order.isFavorite
                                    )

                                savedOrders[index] =
                                    updatedOrder

                                coroutineScope.launch {

                                    val storageValue =
                                        updatedOrder
                                            .toStorageString()

                                    if (
                                        updatedOrder.isFavorite
                                    ) {

                                        orderPreferences
                                            .saveFavoriteOrder(
                                                storageValue
                                            )

                                    } else {

                                        orderPreferences
                                            .removeFavoriteOrder(
                                                storageValue
                                            )
                                    }
                                }
                            }
                        }
                    )
                }
            }

            item {
                Spacer(
                    modifier = Modifier.height(16.dp)
                )
            }
        }
    }
}

/**
 * Displays the primary application heading.
 */
@Composable
fun AppHeader() {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(22.dp),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically,
                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Coffee,
                    contentDescription = null
                )

                Text(
                    text = "Tim Hortons Run",
                    style =
                        MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Text(
                text =
                    "One coffee run. Everyone's order organized.",
                style =
                    MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/**
 * Provides a consistent heading for each screen section.
 */
@Composable
fun SectionHeader(
    title: String,
    subtitle: String
) {

    Column(
        verticalArrangement =
            Arrangement.spacedBy(3.dp)
    ) {

        Text(
            text = title,
            style =
                MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = subtitle,
            style =
                MaterialTheme.typography.bodyMedium,
            color =
                MaterialTheme.colorScheme
                    .onSurfaceVariant
        )
    }
}

/**
 * Displays an individual selectable menu product.
 */
@Composable
fun MenuItemCard(
    menuItem: MenuItem,
    isSelected: Boolean,
    onSelectionChanged: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onSelectionChanged()
            },
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
                if (isSelected) {
                    6.dp
                } else {
                    2.dp
                }
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(17.dp),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement =
                    Arrangement.spacedBy(5.dp)
            ) {

                Text(
                    text = menuItem.name,
                    style =
                        MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = menuItem.category,
                    style =
                        MaterialTheme.typography.labelMedium,
                    color =
                        MaterialTheme.colorScheme.primary
                )

                Text(
                    text = menuItem.description,
                    style =
                        MaterialTheme.typography.bodyMedium
                )

                Text(
                    text =
                        "$%.2f".format(
                            menuItem.price
                        ),
                    style =
                        MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Checkbox(
                checked = isSelected,
                onCheckedChange = {
                    onSelectionChanged()
                }
            )
        }
    }
}

/**
 * Displays the order currently being created.
 */
@Composable
fun OrderSummaryCard(
    personName: String,
    selectedItems: List<MenuItem>,
    onSaveOrder: () -> Unit
) {

    val totalPrice =
        selectedItems.sumOf { item ->
            item.price
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically,
                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                Icon(
                    imageVector =
                        Icons.Default.CheckCircle,
                    contentDescription = null
                )

                Text(
                    text = "Current Order",
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider()

            Text(
                text =
                    "Items selected: ${selectedItems.size}"
            )

            Text(
                text =
                    "Total: $%.2f".format(
                        totalPrice
                    ),
                style =
                    MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onSaveOrder,
                modifier = Modifier.fillMaxWidth(),
                enabled =
                    personName.isNotBlank() &&
                            selectedItems.isNotEmpty()
            ) {

                Text("Save Team Order")
            }
        }
    }
}

/**
 * Displays a saved team-member order.
 */
@Composable
fun SavedOrderCard(
    order: CoffeeOrder,
    onReuseOrder: () -> Unit,
    onToggleFavorite: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment =
                    Alignment.CenterVertically,
                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(
                    text = order.personName,
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector =
                        if (order.isFavorite) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                    contentDescription =
                        if (order.isFavorite) {
                            "Favorite order"
                        } else {
                            "Not a favorite"
                        }
                )
            }

            HorizontalDivider()

            order.items.forEach { item ->

                Text(
                    text =
                        "• ${item.name} — $%.2f"
                            .format(item.price)
                )
            }

            Text(
                text =
                    "Order Total: $%.2f"
                        .format(
                            order.totalPrice()
                        ),
                style =
                    MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onReuseOrder,
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Refresh,
                    contentDescription = null
                )

                Text(
                    text = "  Reuse Order"
                )
            }

            OutlinedButton(
                onClick = onToggleFavorite,
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector =
                        if (order.isFavorite) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Outlined.FavoriteBorder
                        },
                    contentDescription = null
                )

                Text(
                    text =
                        if (order.isFavorite) {
                            "  Remove Favorite"
                        } else {
                            "  Save as Favorite"
                        }
                )
            }
        }
    }
}

/**
 * Displays totals for the complete team coffee run.
 */
@Composable
fun CoffeeRunSummaryCard(
    orders: List<CoffeeOrder>
) {

    val totalItems =
        orders.sumOf { order ->
            order.items.size
        }

    val totalPrice =
        orders.sumOf { order ->
            order.totalPrice()
        }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = "Coffee Run Summary",
                style =
                    MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            HorizontalDivider()

            Text(
                text =
                    "Team members: ${orders.size}"
            )

            Text(
                text =
                    "Total items: $totalItems"
            )

            Text(
                text =
                    "Coffee run total: $%.2f"
                        .format(totalPrice),
                style =
                    MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/**
 * Displays favorite orders retrieved from persistent storage.
 */
@Composable
fun PersistentFavoritesCard(
    favorites: List<String>
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement =
                Arrangement.spacedBy(10.dp)
        ) {

            Row(
                verticalAlignment =
                    Alignment.CenterVertically,
                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                Icon(
                    imageVector =
                        Icons.Default.Favorite,
                    contentDescription = null
                )

                Text(
                    text = "Saved Favorites",
                    style =
                        MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            HorizontalDivider()

            favorites.forEach { favorite ->

                val parts =
                    favorite.split("|")

                val name =
                    parts.getOrElse(0) {
                        "Saved Order"
                    }

                val products =
                    parts.getOrElse(1) {
                        ""
                    }

                Text(
                    text = name,
                    fontWeight = FontWeight.Bold
                )

                if (products.isNotBlank()) {

                    Text(
                        text = products.replace(
                            ",",
                            " • "
                        ),
                        style =
                            MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

/**
 * Converts an order into the text representation stored by DataStore.
 */
fun CoffeeOrder.toStorageString(): String {

    val itemNames =
        items.joinToString(",") { item ->
            item.name
        }

    return "$personName|$itemNames"
}