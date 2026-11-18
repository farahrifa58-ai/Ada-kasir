package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddShoppingCart
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.CartLine
import com.example.data.model.CheckoutTotals
import com.example.data.model.DiscountType
import com.example.data.model.PaymentMethod
import com.example.data.model.PosCalculator
import com.example.data.model.Product
import com.example.data.model.ProductCategory
import com.example.data.model.ProductUnit
import com.example.data.model.StoreSettings
import com.example.data.model.TaxType
import com.example.ui.components.ProductPhotoBox
import com.example.ui.components.SubScreenTopBar
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.LocalAppCurrency
import com.example.ui.theme.LocalAppStrings
import com.example.ui.theme.StatusError
import com.example.ui.theme.StatusWarning
import com.example.ui.theme.amountWithCurrency
import com.example.ui.viewmodel.CashierUiState
import com.example.util.PosFormatters
import kotlin.math.abs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashierScreen(
    products: List<Product>,
    categories: List<ProductCategory>,
    store: StoreSettings,
    cashierState: CashierUiState,
    totals: CheckoutTotals,
    onAddProduct: (Product, Double) -> Unit,
    onSetCartQuantity: (String, Double) -> Unit,
    onRemoveCartItem: (String) -> Unit,
    onCustomerNameChange: (String) -> Unit,
    onDiscountChange: (DiscountType, String) -> Unit,
    onTaxChange: (TaxType, String) -> Unit,
    onPaymentMethodChange: (PaymentMethod) -> Unit,
    onAmountPaidChange: (String) -> Unit,
    onFillExactCash: () -> Unit,
    onCompleteCheckout: () -> Unit,
    onClearCart: () -> Unit = {},
    onBackToDashboard: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val currency = LocalAppCurrency.current
    val totalCartCount = remember(cashierState.cart) {
        PosCalculator.totalCartItemCount(cashierState.cart)
    }
    val cartQtyByProductId = remember(cashierState.cart) {
        cashierState.cart.associate { it.product.id to it.quantity }
    }
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var decimalPickerProduct by remember { mutableStateOf<Product?>(null) }
    var showCartSheet by remember { mutableStateOf(false) }

    val filteredProducts = remember(products, searchQuery, selectedCategory) {
        products.filter { prod ->
            val matchesSearch = searchQuery.isBlank() ||
                prod.name.contains(searchQuery, ignoreCase = true) ||
                prod.sku.contains(searchQuery, ignoreCase = true)
            val matchesCat = selectedCategory == null || prod.category.equals(selectedCategory, ignoreCase = true)
            matchesSearch && matchesCat
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SubScreenTopBar(
            title = strings.menuCashier,
            onBackClick = onBackToDashboard
        )

        BoxWithConstraints(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            val isWideScreen = maxWidth >= 760.dp

            if (isWideScreen) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, end = 16.dp, top = 10.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ProductCatalogPane(
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it },
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onSelectCategory = { selectedCategory = it },
                        products = filteredProducts,
                        cartQtyByProductId = cartQtyByProductId,
                        isWideLayout = true,
                        onAddSingleOrPick = { prod ->
                            if (prod.supportsDecimal) {
                                decimalPickerProduct = prod
                            } else {
                                onAddProduct(prod, 1.0)
                            }
                        },
                        onIncrementLine = { prod, currentQty ->
                            if (prod.supportsDecimal) {
                                onSetCartQuantity(prod.id, currentQty + 0.25)
                            } else {
                                onAddProduct(prod, 1.0)
                            }
                        },
                        onDecrementLine = { prod, currentQty ->
                            val step = if (prod.supportsDecimal) 0.25 else 1.0
                            onSetCartQuantity(prod.id, currentQty - step)
                        },
                        onEditDecimalProduct = { prod -> decimalPickerProduct = prod },
                        modifier = Modifier
                            .weight(1.3f)
                            .fillMaxHeight()
                    )

                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)),
                        shadowElevation = 0.dp,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        CartAndCheckoutContent(
                            store = store,
                            cashierState = cashierState,
                            totalCartCount = totalCartCount,
                            totals = totals,
                            onSetCartQuantity = onSetCartQuantity,
                            onRemoveCartItem = onRemoveCartItem,
                            onClearCart = onClearCart,
                            onEditDecimalLine = { decimalPickerProduct = it },
                            onCustomerNameChange = onCustomerNameChange,
                            onDiscountChange = onDiscountChange,
                            onTaxChange = onTaxChange,
                            onPaymentMethodChange = onPaymentMethodChange,
                            onAmountPaidChange = onAmountPaidChange,
                            onFillExactCash = onFillExactCash,
                            onCompleteCheckout = onCompleteCheckout
                        )
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    ProductCatalogPane(
                        searchQuery = searchQuery,
                        onSearchChange = { searchQuery = it },
                        categories = categories,
                        selectedCategory = selectedCategory,
                        onSelectCategory = { selectedCategory = it },
                        products = filteredProducts,
                        cartQtyByProductId = cartQtyByProductId,
                        isWideLayout = false,
                        onAddSingleOrPick = { prod ->
                            if (prod.supportsDecimal) {
                                decimalPickerProduct = prod
                            } else {
                                onAddProduct(prod, 1.0)
                            }
                        },
                        onIncrementLine = { prod, currentQty ->
                            if (prod.supportsDecimal) {
                                onSetCartQuantity(prod.id, currentQty + 0.25)
                            } else {
                                onAddProduct(prod, 1.0)
                            }
                        },
                        onDecrementLine = { prod, currentQty ->
                            val step = if (prod.supportsDecimal) 0.25 else 1.0
                            onSetCartQuantity(prod.id, currentQty - step)
                        },
                        onEditDecimalProduct = { prod -> decimalPickerProduct = prod },
                        bottomContentPadding = if (cashierState.cart.isNotEmpty()) 104.dp else 24.dp,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    )

                    // Floating Blue Cart Summary Bar matching reference layout
                    if (cashierState.cart.isNotEmpty()) {
                        Surface(
                            onClick = { showCartSheet = true },
                            shape = RoundedCornerShape(20.dp),
                            color = BrandBlue,
                            shadowElevation = 4.dp,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .navigationBarsPadding()
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                                .fillMaxWidth()
                                .testTag("cashier_cart_bar")
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 13.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(Color.White)
                                            .testTag("cashier_cart_badge"),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "$totalCartCount",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = BrandBlue
                                        )
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
                                        Text(
                                            text = "$totalCartCount ${strings.productsInCartLabel}",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = Color.White.copy(alpha = 0.92f),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = PosFormatters.formatCurrency(totals.grandTotal, currency.code),
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.White
                                        )
                                    }
                                }

                                Surface(
                                    onClick = { showCartSheet = true },
                                    shape = RoundedCornerShape(14.dp),
                                    color = Color.White
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.ShoppingCart,
                                            contentDescription = strings.cartTitle,
                                            tint = BrandBlue,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Text(
                                            text = strings.cartTitle,
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = BrandBlue
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (showCartSheet) {
                    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
                    ModalBottomSheet(
                        onDismissRequest = { showCartSheet = false },
                        sheetState = sheetState,
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        CartAndCheckoutContent(
                            store = store,
                            cashierState = cashierState,
                            totalCartCount = totalCartCount,
                            totals = totals,
                            onSetCartQuantity = onSetCartQuantity,
                            onRemoveCartItem = onRemoveCartItem,
                            onClearCart = {
                                onClearCart()
                                showCartSheet = false
                            },
                            onEditDecimalLine = { decimalPickerProduct = it },
                            onCustomerNameChange = onCustomerNameChange,
                            onDiscountChange = onDiscountChange,
                            onTaxChange = onTaxChange,
                            onPaymentMethodChange = onPaymentMethodChange,
                            onAmountPaidChange = onAmountPaidChange,
                            onFillExactCash = onFillExactCash,
                            onCompleteCheckout = {
                                onCompleteCheckout()
                                showCartSheet = false
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .navigationBarsPadding()
                        )
                    }
                }
            }
        }
    }

    val pickerProd = decimalPickerProduct
    if (pickerProd != null) {
        DecimalQuantityDialog(
            product = pickerProd,
            initialQty = cartQtyByProductId[pickerProd.id]?.takeIf { it > 0.0 } ?: 1.0,
            onDismiss = { decimalPickerProduct = null },
            onConfirm = { qty ->
                val existing = cartQtyByProductId[pickerProd.id] ?: 0.0
                if (existing > 0.0) {
                    onSetCartQuantity(pickerProd.id, qty)
                } else {
                    onAddProduct(pickerProd, qty)
                }
                decimalPickerProduct = null
            }
        )
    }
}

@Composable
private fun ProductCatalogPane(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    categories: List<ProductCategory>,
    selectedCategory: String?,
    onSelectCategory: (String?) -> Unit,
    products: List<Product>,
    cartQtyByProductId: Map<String, Double>,
    isWideLayout: Boolean,
    onAddSingleOrPick: (Product) -> Unit,
    onIncrementLine: (Product, Double) -> Unit,
    onDecrementLine: (Product, Double) -> Unit,
    onEditDecimalProduct: (Product) -> Unit,
    bottomContentPadding: Dp = 16.dp,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = {
                Text(
                    text = strings.searchProductHint,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = BrandBlue
                )
            },
            trailingIcon = if (searchQuery.isNotEmpty()) {
                {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(Icons.Rounded.Close, contentDescription = strings.close)
                    }
                }
            } else null,
            singleLine = true,
            shape = RoundedCornerShape(18.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = BrandBlue,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("cashier_search_input")
        )

        // Category pills (solid Blue active, clean outlined white inactive)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CashierCategoryChip(
                label = strings.all,
                selected = selectedCategory == null,
                onClick = { onSelectCategory(null) }
            )
            categories.forEach { cat ->
                CashierCategoryChip(
                    label = cat.name,
                    selected = selectedCategory == cat.name,
                    onClick = { onSelectCategory(cat.name) }
                )
            }
        }

        if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = strings.noProductsFound,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else if (isWideLayout) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 310.dp),
                contentPadding = PaddingValues(bottom = bottomContentPadding),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(products, key = { it.id }) { prod ->
                    val qty = cartQtyByProductId[prod.id] ?: 0.0
                    CashierProductRowCard(
                        product = prod,
                        inCartQty = qty,
                        onAddClick = { onAddSingleOrPick(prod) },
                        onIncrement = { onIncrementLine(prod, qty) },
                        onDecrement = { onDecrementLine(prod, qty) },
                        onTapQty = {
                            if (prod.supportsDecimal) onEditDecimalProduct(prod) else onIncrementLine(prod, qty)
                        }
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = bottomContentPadding),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(products, key = { it.id }) { prod ->
                    val qty = cartQtyByProductId[prod.id] ?: 0.0
                    CashierProductRowCard(
                        product = prod,
                        inCartQty = qty,
                        onAddClick = { onAddSingleOrPick(prod) },
                        onIncrement = { onIncrementLine(prod, qty) },
                        onDecrement = { onDecrementLine(prod, qty) },
                        onTapQty = {
                            if (prod.supportsDecimal) onEditDecimalProduct(prod) else onIncrementLine(prod, qty)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CashierCategoryChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        color = if (selected) BrandBlue else MaterialTheme.colorScheme.surface,
        border = if (selected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.55f)),
        shadowElevation = 0.dp
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 9.dp)
        )
    }
}

/**
 * Clean horizontal product card matching reference layout with Blue accents
 * and ZERO dark press shadow / card-wide overlay when selected.
 */
@Composable
private fun CashierProductRowCard(
    product: Product,
    inCartQty: Double,
    onAddClick: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onTapQty: () -> Unit
) {
    val strings = LocalAppStrings.current
    val currency = LocalAppCurrency.current
    val isDark = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    val isInCart = inCartQty > 0.0001
    val enabled = !product.isOutOfStock

    val stepperBg = if (isDark) BrandBlue.copy(alpha = 0.22f) else BrandBlue.copy(alpha = 0.11f)
    val stockColor = when {
        product.isOutOfStock -> StatusError
        product.isLowStock -> StatusWarning
        else -> BrandBlue
    }
    val stockText = when {
        product.isOutOfStock -> strings.outOfStockBadge
        else -> "${strings.stockLabelPrefix} ${PosFormatters.formatQuantity(product.stock)}"
    }

    // Flat clean card with NO outer clickable (eliminates dark card press shadow)
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
        border = BorderStroke(
            width = 1.dp,
            color = if (isInCart) BrandBlue.copy(alpha = 0.45f) else MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.75f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("cashier_product_card_${product.id}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ProductPhotoBox(
                photoData = product.photoData,
                contentDescription = product.name,
                size = 66.dp,
                cornerRadius = 14.dp
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${product.category} • ${product.unit.lowercase()}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(SpanStyle(color = BrandBlue, fontWeight = FontWeight.ExtraBold)) {
                            append(PosFormatters.formatCurrency(product.sellPrice, currency.code))
                        }
                        withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Normal)) {
                            append("/${product.unit.lowercase()}")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = stockText,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = stockColor,
                    maxLines = 1
                )
            }

            // Dedicated right-side action control: "+ Tambah" / "+ Pilih kg" or soft-blue "− qty +" stepper
            if (!enabled) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = StatusError.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = strings.outOfStockBadge,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = StatusError,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp)
                    )
                }
            } else if (isInCart) {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = stepperBg,
                    modifier = Modifier.testTag("cashier_stepper_${product.id}")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                    ) {
                        IconButton(
                            onClick = onDecrement,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Remove,
                                contentDescription = "${strings.decreaseQtyLabel} ${product.name}",
                                tint = BrandBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = PosFormatters.formatQuantity(inCartQty),
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = BrandBlue,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(onClick = onTapQty)
                                .padding(horizontal = 6.dp, vertical = 6.dp)
                        )
                        IconButton(
                            onClick = onIncrement,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "${strings.increaseQtyLabel} ${product.name}",
                                tint = BrandBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            } else {
                Button(
                    onClick = onAddClick,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BrandBlue,
                        contentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 10.dp),
                    modifier = Modifier
                        .heightIn(min = 42.dp)
                        .testTag("cashier_add_${product.id}")
                ) {
                    Icon(
                        imageVector = Icons.Rounded.AddShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(17.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = if (product.supportsDecimal) {
                            "${strings.chooseUnitPrefix} ${product.unit.lowercase()}"
                        } else {
                            strings.addShortButton
                        },
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
private fun DecimalQuantityDialog(
    product: Product,
    initialQty: Double = 1.0,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit
) {
    val strings = LocalAppStrings.current
    val currency = LocalAppCurrency.current
    var qtyInput by remember(product.id) { mutableStateOf(initialQty.toString()) }
    val parsedQty = qtyInput.replace(",", ".").toDoubleOrNull() ?: 0.0
    val estimated = PosCalculator.roundCurrency(product.sellPrice * parsedQty.coerceAtLeast(0.0), currency.decimalPlaces)
    val unitLower = product.unit.lowercase()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .imePadding()
                .padding(horizontal = 24.dp)
                .widthIn(max = 440.dp)
                .testTag("decimal_quantity_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp, vertical = 22.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "${strings.quickUnitDialogTitle} ($unitLower)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold,
                        color = BrandBlue,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Text(
                    text = "${strings.quickSelectUnitPrefix} ${product.unit}:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                val presets = ProductUnit.decimalPresets
                val topRow = presets.take(4)
                val bottomRow = presets.drop(4)

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        topRow.forEach { preset ->
                            val isSelected = abs(parsedQty - preset) < 0.001
                            DecimalPresetChip(
                                label = "${preset} $unitLower",
                                selected = isSelected,
                                onClick = { qtyInput = preset.toString() },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    if (bottomRow.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            bottomRow.forEach { preset ->
                                val isSelected = abs(parsedQty - preset) < 0.001
                                DecimalPresetChip(
                                    label = "${preset} $unitLower",
                                    selected = isSelected,
                                    onClick = { qtyInput = preset.toString() },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                OutlinedTextField(
                    value = qtyInput,
                    onValueChange = { qtyInput = it },
                    label = { Text("${strings.manualWeightLabel} ($unitLower)") },
                    suffix = {
                        Text(
                            text = unitLower,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = BrandBlue,
                        focusedLabelColor = BrandBlue
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "${strings.estimatedPriceLabel}: ${PosFormatters.formatCurrency(estimated, currency.code)}",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = BrandBlue
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(
                            text = strings.cancel,
                            color = BrandBlue,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { if (parsedQty > 0.0) onConfirm(parsedQty) },
                        enabled = parsedQty > 0.0,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BrandBlue,
                            contentColor = Color.White
                        ),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = strings.addConfirmButton,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DecimalPresetChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        color = if (selected) BrandBlue else MaterialTheme.colorScheme.surface,
        border = if (selected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.65f)),
        shadowElevation = 0.dp,
        modifier = modifier.heightIn(min = 42.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = if (selected) Color.White else BrandBlue,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun CartAndCheckoutContent(
    store: StoreSettings,
    cashierState: CashierUiState,
    totalCartCount: Int,
    totals: CheckoutTotals,
    onSetCartQuantity: (String, Double) -> Unit,
    onRemoveCartItem: (String) -> Unit,
    onClearCart: () -> Unit,
    onEditDecimalLine: (Product) -> Unit,
    onCustomerNameChange: (String) -> Unit,
    onDiscountChange: (DiscountType, String) -> Unit,
    onTaxChange: (TaxType, String) -> Unit,
    onPaymentMethodChange: (PaymentMethod) -> Unit,
    onAmountPaidChange: (String) -> Unit,
    onFillExactCash: () -> Unit,
    onCompleteCheckout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val currency = LocalAppCurrency.current
    val blueChipColors = FilterChipDefaults.filterChipColors(
        selectedContainerColor = BrandBlue,
        selectedLabelColor = Color.White
    )

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header row: Cart title + total count pill + Clear button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ShoppingCart,
                    contentDescription = null,
                    tint = BrandBlue
                )
                Text(
                    text = strings.cartTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                if (totalCartCount > 0) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = BrandBlue.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "$totalCartCount ${strings.cartItemCountLabel}",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = BrandBlue,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp)
                        )
                    }
                }
            }
            if (cashierState.cart.isNotEmpty()) {
                TextButton(onClick = onClearCart) {
                    Icon(
                        imageVector = Icons.Rounded.DeleteOutline,
                        contentDescription = strings.clearCartButton,
                        tint = StatusError,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(strings.clearCartButton, color = StatusError)
                }
            }
        }

        if (cashierState.cart.isEmpty()) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = strings.emptyCartHint,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 22.dp)
                )
            }
        } else {
            // 1. Clean White Cart Line Items Section
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.75f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    cashierState.cart.forEachIndexed { idx, line ->
                        CartLineRow(
                            line = line,
                            onIncrement = {
                                val step = if (line.product.supportsDecimal) 0.25 else 1.0
                                onSetCartQuantity(line.product.id, line.quantity + step)
                            },
                            onDecrement = {
                                val step = if (line.product.supportsDecimal) 0.25 else 1.0
                                onSetCartQuantity(line.product.id, line.quantity - step)
                            },
                            onTapQty = {
                                if (line.product.supportsDecimal) onEditDecimalLine(line.product)
                            },
                            onRemove = { onRemoveCartItem(line.product.id) }
                        )
                        if (idx < cashierState.cart.lastIndex) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                        }
                    }
                }
            }

            // 2. Customer, Discount & PPN Options Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.75f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = cashierState.customerName,
                        onValueChange = onCustomerNameChange,
                        label = { Text(strings.customerNameLabel) },
                        placeholder = { Text(strings.customerNameHint) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = BrandBlue,
                            focusedLabelColor = BrandBlue
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(strings.discountLabel, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(
                            selected = cashierState.discountType == DiscountType.NONE,
                            onClick = { onDiscountChange(DiscountType.NONE, "") },
                            colors = blueChipColors,
                            label = { Text(strings.discountNone) }
                        )
                        FilterChip(
                            selected = cashierState.discountType == DiscountType.PERCENT,
                            onClick = { onDiscountChange(DiscountType.PERCENT, cashierState.discountInput) },
                            colors = blueChipColors,
                            label = { Text("%") }
                        )
                        FilterChip(
                            selected = cashierState.discountType == DiscountType.AMOUNT,
                            onClick = { onDiscountChange(DiscountType.AMOUNT, cashierState.discountInput) },
                            colors = blueChipColors,
                            label = { Text(currency.symbol) }
                        )
                    }
                    if (cashierState.discountType != DiscountType.NONE) {
                        OutlinedTextField(
                            value = cashierState.discountInput,
                            onValueChange = { onDiscountChange(cashierState.discountType, it) },
                            label = {
                                Text(
                                    if (cashierState.discountType == DiscountType.PERCENT) strings.discountPercent
                                    else strings.amountWithCurrency(strings.discountAmountWord, currency.symbol)
                                )
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = if (cashierState.discountType == DiscountType.PERCENT || currency.decimalPlaces > 0) {
                                    KeyboardType.Decimal
                                } else {
                                    KeyboardType.Number
                                }
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BrandBlue),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Text(strings.taxLabel, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(
                            selected = cashierState.taxType == TaxType.NONE,
                            onClick = { onTaxChange(TaxType.NONE, "") },
                            colors = blueChipColors,
                            label = { Text(strings.taxNone) }
                        )
                        FilterChip(
                            selected = cashierState.taxType == TaxType.AUTO,
                            onClick = { onTaxChange(TaxType.AUTO, "") },
                            colors = blueChipColors,
                            label = { Text("${strings.taxAuto} (${PosFormatters.formatQuantity(store.defaultTaxPercent)}%)") }
                        )
                        FilterChip(
                            selected = cashierState.taxType == TaxType.MANUAL,
                            onClick = { onTaxChange(TaxType.MANUAL, cashierState.manualTaxInput) },
                            colors = blueChipColors,
                            label = { Text(strings.amountWithCurrency(strings.taxManualWord, currency.symbol)) }
                        )
                    }
                    if (cashierState.taxType == TaxType.MANUAL) {
                        OutlinedTextField(
                            value = cashierState.manualTaxInput,
                            onValueChange = { onTaxChange(TaxType.MANUAL, it) },
                            label = { Text(strings.amountWithCurrency(strings.taxManualWord, currency.symbol)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = if (currency.decimalPlaces > 0) KeyboardType.Decimal else KeyboardType.Number
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BrandBlue),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // 3. Payment Method & Nominal Input Card
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.75f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(strings.paymentMethodLabel, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        listOf(
                            PaymentMethod.CASH to strings.methodCash,
                            PaymentMethod.BANK_TRANSFER to strings.methodBankTransfer,
                            PaymentMethod.QRIS to strings.methodQris,
                            PaymentMethod.OTHER to strings.methodOther
                        ).forEach { (method, label) ->
                            FilterChip(
                                selected = cashierState.paymentMethod == method,
                                onClick = { onPaymentMethodChange(method) },
                                colors = blueChipColors,
                                label = { Text(label) }
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = cashierState.amountPaidInput,
                            onValueChange = onAmountPaidChange,
                            label = { Text(strings.amountReceivedLabel) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = if (currency.decimalPlaces > 0) KeyboardType.Decimal else KeyboardType.Number
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = BrandBlue,
                                focusedLabelColor = BrandBlue
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("cashier_amount_paid_input")
                        )
                        FilledTonalButton(
                            onClick = onFillExactCash,
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = BrandBlue.copy(alpha = 0.12f),
                                contentColor = BrandBlue
                            ),
                            modifier = Modifier.height(54.dp)
                        ) {
                            Text(strings.exactCashButton, fontWeight = FontWeight.Bold)
                        }
                    }

                    if (cashierState.paymentMethod == PaymentMethod.CASH && totals.grandTotal > 0.0) {
                        val quickNominals = currency.quickCashNominals
                            .filter { it >= totals.grandTotal - 0.0001 }
                            .take(3)
                        if (quickNominals.isNotEmpty()) {
                            Row(
                                modifier = Modifier.horizontalScroll(rememberScrollState()),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                quickNominals.forEach { nominal ->
                                    FilledTonalButton(
                                        onClick = { onAmountPaidChange(PosFormatters.formatInputAmount(nominal, currency.code)) },
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                                        colors = ButtonDefaults.filledTonalButtonColors(
                                            containerColor = BrandBlue.copy(alpha = 0.10f),
                                            contentColor = BrandBlue
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Text(
                                            text = PosFormatters.formatCurrency(nominal, currency.code),
                                            style = MaterialTheme.typography.labelMedium,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 4. Totals Summary Card (Blue Theme)
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = BrandBlue.copy(alpha = 0.08f),
                border = BorderStroke(1.dp, BrandBlue.copy(alpha = 0.25f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = strings.billSummaryTitle,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = BrandBlue
                    )
                    SummaryLine(strings.subtotalLabel, PosFormatters.formatCurrency(totals.subtotal, currency.code))
                    if (totals.discountAmount > 0.0) {
                        SummaryLine(strings.discountLabel, "-${PosFormatters.formatCurrency(totals.discountAmount, currency.code)}")
                    }
                    if (totals.taxAmount > 0.0) {
                        SummaryLine(strings.taxLabel, PosFormatters.formatCurrency(totals.taxAmount, currency.code))
                    }
                    HorizontalDivider(
                        color = BrandBlue.copy(alpha = 0.18f),
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = strings.grandTotalLabel,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = PosFormatters.formatCurrency(totals.grandTotal, currency.code),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = BrandBlue
                        )
                    }
                    if (cashierState.paymentMethod == PaymentMethod.CASH) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(strings.changeLabel, style = MaterialTheme.typography.titleSmall)
                            Text(
                                text = PosFormatters.formatCurrency(totals.changeAmount, currency.code),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = BrandBlue
                            )
                        }
                    }
                }
            }

            Button(
                onClick = onCompleteCheckout,
                enabled = !cashierState.isSubmitting && cashierState.cart.isNotEmpty(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BrandBlue,
                    contentColor = Color.White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("cashier_complete_tx_button")
            ) {
                if (cashierState.isSubmitting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Icon(Icons.Rounded.CheckCircle, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = strings.completeTransactionButton,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(Modifier.height(6.dp))
        }
    }
}

@Composable
private fun CartLineRow(
    line: CartLine,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onTapQty: () -> Unit,
    onRemove: () -> Unit
) {
    val strings = LocalAppStrings.current
    val currency = LocalAppCurrency.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = line.product.name,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${PosFormatters.formatCurrency(line.product.sellPrice, currency.code)}/${line.product.unit.lowercase()} • ${PosFormatters.formatCurrency(line.subtotal, currency.code)}",
                style = MaterialTheme.typography.bodySmall,
                color = BrandBlue,
                fontWeight = FontWeight.Medium
            )
        }
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = BrandBlue.copy(alpha = 0.10f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecrement, modifier = Modifier.size(34.dp)) {
                    Icon(Icons.Rounded.Remove, contentDescription = strings.decreaseQtyLabel, tint = BrandBlue, modifier = Modifier.size(16.dp))
                }
                Text(
                    text = PosFormatters.formatQuantity(line.quantity, line.product.unit),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = BrandBlue,
                    modifier = Modifier
                        .clickable(enabled = line.product.supportsDecimal, onClick = onTapQty)
                        .padding(horizontal = 4.dp)
                )
                IconButton(onClick = onIncrement, modifier = Modifier.size(34.dp)) {
                    Icon(Icons.Rounded.Add, contentDescription = strings.increaseQtyLabel, tint = BrandBlue, modifier = Modifier.size(16.dp))
                }
            }
        }
        IconButton(onClick = onRemove, modifier = Modifier.size(34.dp)) {
            Icon(Icons.Rounded.DeleteOutline, contentDescription = strings.delete, tint = StatusError, modifier = Modifier.size(18.dp))
        }
    }
}

@Composable
private fun SummaryLine(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
    }
}
