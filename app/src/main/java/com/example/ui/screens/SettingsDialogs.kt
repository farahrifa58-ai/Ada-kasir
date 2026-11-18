package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.Badge
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Percent
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.model.AppAccessMode
import com.example.data.model.AppCurrencyCatalog
import com.example.data.model.AppLanguageCatalog
import com.example.data.model.DiscountType
import com.example.data.model.LocalizationRegion
import com.example.data.model.PaymentMethod
import com.example.data.model.PosCurrencyOption
import com.example.data.model.PosLanguageOption
import com.example.data.model.PosTransaction
import com.example.data.model.StaffRole
import com.example.data.model.StoreDirectoryEntry
import com.example.data.model.StoreSettings
import com.example.data.model.TaxType
import com.example.data.model.TransactionItem
import com.example.data.prefs.LocalAppPrefs
import com.example.ui.components.StoreBusinessIcons
import com.example.ui.components.StoreIconBgColors
import com.example.ui.components.StoreProfileAvatar
import com.example.ui.components.parseHexColor
import com.example.ui.components.storeBusinessIconVector
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.JetBrainsMonoFontFamily
import com.example.ui.theme.LocalAppCurrency
import com.example.ui.theme.LocalAppStrings
import com.example.ui.theme.StatusError
import com.example.ui.theme.StatusSuccess
import com.example.ui.theme.amountWithCurrency
import com.example.ui.theme.tutorialStepsList
import com.example.util.ImageUtils
import com.example.util.PosFormatters
import com.example.util.ReceiptHelper

@Composable
internal fun StoreProfileEditorDialog(
    initialStoreName: String,
    initialOwnerName: String,
    initialPhone: String,
    initialAddress: String,
    initialOperatingHours: String,
    initialShowOperatingHoursOnReceipt: Boolean,
    initialPhotoData: String,
    initialIconKey: String,
    initialIconBgHex: String,
    language: String,
    onDismiss: () -> Unit,
    onOpenPhotoPicker: () -> Unit,
    onClearPhoto: () -> Unit,
    onSave: (
        storeName: String,
        ownerName: String,
        phone: String,
        address: String,
        operatingHours: String,
        showOperatingHoursOnReceipt: Boolean,
        iconKey: String,
        iconBgHex: String
    ) -> Unit
) {
    val strings = LocalAppStrings.current
    var storeName by remember(initialStoreName) { mutableStateOf(initialStoreName) }
    var ownerName by remember(initialOwnerName) { mutableStateOf(initialOwnerName) }
    var phone by remember(initialPhone) { mutableStateOf(initialPhone) }
    var address by remember(initialAddress) { mutableStateOf(initialAddress) }
    var operatingHours by remember(initialOperatingHours) { mutableStateOf(initialOperatingHours) }
    var showOperatingHoursOnReceipt by remember(initialShowOperatingHoursOnReceipt) {
        mutableStateOf(initialShowOperatingHoursOnReceipt)
    }
    var selectedIconKey by remember(initialIconKey) {
        mutableStateOf(initialIconKey.ifBlank { "storefront" })
    }
    var selectedIconBgHex by remember(initialIconBgHex) {
        mutableStateOf(initialIconBgHex.ifBlank { "#1565C0" })
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .widthIn(max = 500.dp)
                .heightIn(max = 700.dp)
                .testTag("store_profile_dialog")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 10.dp, top = 12.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = strings.storeProfileSection,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Rounded.Close, contentDescription = strings.close)
                    }
                }
                HorizontalDivider()

                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Compact Store Photo / Business Icon Live Preview
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        StoreProfileAvatar(
                            photoData = initialPhotoData,
                            storeIconKey = selectedIconKey,
                            storeIconBgHex = selectedIconBgHex,
                            size = 72.dp,
                            onClick = onOpenPhotoPicker,
                            modifier = Modifier.testTag("settings_store_avatar")
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilledTonalButton(
                            onClick = onOpenPhotoPicker,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Rounded.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(6.dp))
                            Text(strings.changeStorePhotoButton, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                        if (initialPhotoData.isNotBlank()) {
                            OutlinedButton(
                                onClick = onClearPhoto,
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(strings.removeStorePhotoButton, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }
                    }

                    // Compact Small Business Icon Picker
                    Text(
                        text = strings.storeIconPickerLabel,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        StoreBusinessIcons.forEach { option ->
                            val isSelected = selectedIconKey == option.key
                            val activeBg = parseHexColor(selectedIconBgHex)
                            val labelText = option.label(strings)
                            Surface(
                                onClick = {
                                    selectedIconKey = option.key
                                    if (initialPhotoData.isNotBlank()) onClearPhoto()
                                },
                                shape = RoundedCornerShape(11.dp),
                                color = if (isSelected) activeBg else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier
                                    .widthIn(min = 52.dp)
                                    .border(
                                        width = if (isSelected) 1.5.dp else 1.dp,
                                        color = if (isSelected) activeBg else MaterialTheme.colorScheme.outline.copy(alpha = 0.22f),
                                        shape = RoundedCornerShape(11.dp)
                                    )
                                    .testTag("store_icon_${option.key}")
                            ) {
                                Column(
                                    modifier = Modifier.padding(horizontal = 7.dp, vertical = 6.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Icon(
                                        imageVector = option.icon,
                                        contentDescription = labelText,
                                        tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Text(
                                        text = labelText,
                                        fontSize = 10.sp,
                                        lineHeight = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }

                    // Compact Icon Background Color Picker
                    Text(
                        text = strings.storeIconColorLabel,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StoreIconBgColors.forEach { hex ->
                            val selected = selectedIconBgHex.equals(hex, ignoreCase = true)
                            val swatch = parseHexColor(hex)
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(swatch)
                                    .border(
                                        width = if (selected) 2.5.dp else 1.dp,
                                        color = if (selected) MaterialTheme.colorScheme.onSurface else Color.White.copy(alpha = 0.6f),
                                        shape = CircleShape
                                    )
                                    .clickable {
                                        selectedIconBgHex = hex
                                        if (initialPhotoData.isNotBlank()) onClearPhoto()
                                    }
                                    .testTag("store_color_${hex.removePrefix("#")}"),
                                contentAlignment = Alignment.Center
                            ) {
                                if (selected) {
                                    Icon(
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = hex,
                                        tint = Color.White,
                                        modifier = Modifier.size(15.dp)
                                    )
                                }
                            }
                        }
                    }

                    HorizontalDivider()

                    OutlinedTextField(
                        value = storeName,
                        onValueChange = { storeName = it },
                        label = { Text(strings.storeNameLabel) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = ownerName,
                        onValueChange = { ownerName = it },
                        label = { Text(strings.ownerNameLabel) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text(strings.phoneLabel) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text(strings.addressLabel) },
                        minLines = 2,
                        maxLines = 3,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Optional Operating Hours at the bottom of Store Profile + Active toggle for receipt
                    OutlinedTextField(
                        value = operatingHours,
                        onValueChange = { operatingHours = it.take(100) },
                        label = { Text(strings.storeOperatingHoursLabel) },
                        placeholder = { Text(strings.storeOperatingHoursHint) },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("store_operating_hours_input")
                    )
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { showOperatingHoursOnReceipt = !showOperatingHoursOnReceipt }
                            .testTag("switch_store_operating_hours")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = strings.showOperatingHoursOnReceiptLabel,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 10.dp)
                            )
                            Switch(
                                checked = showOperatingHoursOnReceipt,
                                onCheckedChange = { showOperatingHoursOnReceipt = it }
                            )
                        }
                    }
                }

                HorizontalDivider()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    Button(
                        onClick = {
                            onSave(
                                storeName,
                                ownerName,
                                phone,
                                address,
                                operatingHours,
                                showOperatingHoursOnReceipt,
                                selectedIconKey,
                                selectedIconBgHex
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("settings_save_profile_button")
                    ) {
                        Icon(Icons.Rounded.Save, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(strings.saveChanges)
                    }
                }
            }
        }
    }
}

@Composable
internal fun TaxDiscountSettingsDialog(
    store: StoreSettings,
    onDismiss: () -> Unit,
    onSave: (
        defaultTaxType: TaxType,
        defaultTaxPercent: Double,
        defaultManualTaxAmount: Double,
        defaultDiscountType: DiscountType,
        defaultDiscountValue: Double
    ) -> Unit
) {
    val strings = LocalAppStrings.current
    val currency = LocalAppCurrency.current
    var taxType by remember(store.defaultTaxType) { mutableStateOf(TaxType.fromCode(store.defaultTaxType)) }
    var autoTaxPercentInput by remember(store.defaultTaxPercent) {
        mutableStateOf(PosFormatters.formatQuantity(store.defaultTaxPercent))
    }
    var manualTaxInput by remember(store.defaultManualTaxAmount, currency.code) {
        mutableStateOf(
            if (store.defaultManualTaxAmount > 0.0) PosFormatters.formatInputAmount(store.defaultManualTaxAmount, currency.code) else ""
        )
    }
    var discountType by remember(store.defaultDiscountType) {
        mutableStateOf(DiscountType.fromCode(store.defaultDiscountType))
    }
    var discountValueInput by remember(store.defaultDiscountValue, currency.code, discountType) {
        mutableStateOf(
            when {
                store.defaultDiscountValue <= 0.0 -> ""
                discountType == DiscountType.AMOUNT -> PosFormatters.formatInputAmount(store.defaultDiscountValue, currency.code)
                else -> PosFormatters.formatQuantity(store.defaultDiscountValue)
            }
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .widthIn(max = 500.dp)
                .heightIn(max = 660.dp)
                .testTag("tax_discount_dialog")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 10.dp, top = 12.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Rounded.Percent, contentDescription = null, tint = BrandBlue)
                        Text(strings.taxDiscountTitle, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Rounded.Close, contentDescription = strings.close)
                    }
                }
                HorizontalDivider()

                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(strings.defaultTaxModeLabel, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = taxType == TaxType.NONE,
                            onClick = { taxType = TaxType.NONE },
                            label = { Text(strings.taxNone) }
                        )
                        FilterChip(
                            selected = taxType == TaxType.AUTO,
                            onClick = { taxType = TaxType.AUTO },
                            label = { Text(strings.taxAuto) }
                        )
                        FilterChip(
                            selected = taxType == TaxType.MANUAL,
                            onClick = { taxType = TaxType.MANUAL },
                            label = { Text(strings.taxManual) }
                        )
                    }

                    OutlinedTextField(
                        value = autoTaxPercentInput,
                        onValueChange = { autoTaxPercentInput = it },
                        label = { Text(strings.defaultTaxPercentLabel) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (taxType == TaxType.MANUAL) {
                        OutlinedTextField(
                            value = manualTaxInput,
                            onValueChange = { manualTaxInput = it },
                            label = { Text(strings.amountWithCurrency(strings.defaultManualTaxWord, currency.symbol)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = if (currency.decimalPlaces > 0) KeyboardType.Decimal else KeyboardType.Number
                            ),
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    HorizontalDivider()

                    Text(strings.defaultDiscountModeLabel, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Row(
                        modifier = Modifier.horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = discountType == DiscountType.NONE,
                            onClick = {
                                discountType = DiscountType.NONE
                                discountValueInput = ""
                            },
                            label = { Text(strings.discountNone) }
                        )
                        FilterChip(
                            selected = discountType == DiscountType.PERCENT,
                            onClick = { discountType = DiscountType.PERCENT },
                            label = { Text(strings.discountPercent) }
                        )
                        FilterChip(
                            selected = discountType == DiscountType.AMOUNT,
                            onClick = { discountType = DiscountType.AMOUNT },
                            label = { Text(strings.discountAmount) }
                        )
                    }

                    if (discountType != DiscountType.NONE) {
                        OutlinedTextField(
                            value = discountValueInput,
                            onValueChange = { discountValueInput = it },
                            label = {
                                Text(
                                    if (discountType == DiscountType.PERCENT) "${strings.defaultDiscountValueLabel} (%)"
                                    else strings.amountWithCurrency(strings.defaultDiscountValueLabel, currency.symbol)
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.fillMaxWidth()
                        )
                        val presets = if (discountType == DiscountType.PERCENT) {
                            listOf("5" to "5%", "10" to "10%", "15" to "15%", "20" to "20%")
                        } else {
                            currency.quickDiscountPresets
                        }
                        Row(
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            presets.forEach { (raw, label) ->
                                FilterChip(
                                    selected = discountValueInput == raw,
                                    onClick = { discountValueInput = raw },
                                    label = { Text(label) }
                                )
                            }
                        }
                    }
                }

                HorizontalDivider()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    Button(
                        onClick = {
                            val pct = autoTaxPercentInput.replace(",", ".").toDoubleOrNull()?.coerceIn(0.0, 100.0) ?: 11.0
                            val manualAmt = manualTaxInput.replace(",", ".").toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
                            val discVal = if (discountType == DiscountType.NONE) {
                                0.0
                            } else {
                                val parsed = discountValueInput.replace(",", ".").toDoubleOrNull()?.coerceAtLeast(0.0) ?: 0.0
                                if (discountType == DiscountType.PERCENT) parsed.coerceAtMost(100.0) else parsed
                            }
                            onSave(taxType, pct, manualAmt, discountType, discVal)
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("save_tax_discount_button")
                    ) {
                        Icon(Icons.Rounded.Save, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(strings.saveChanges)
                    }
                }
            }
        }
    }
}

@Composable
internal fun ReceiptCustomizationWithPrefixDialog(
    store: StoreSettings,
    onDismiss: () -> Unit,
    onSave: (
        showLogo: Boolean,
        showCashierName: Boolean,
        showStorePhone: Boolean,
        showStoreAddress: Boolean,
        showCustomerName: Boolean,
        showOperatingHours: Boolean,
        footerMessage: String,
        receiptNumberPrefix: String
    ) -> Unit
) {
    val strings = LocalAppStrings.current
    val currency = LocalAppCurrency.current
    var showLogo by remember(store.showReceiptLogo) { mutableStateOf(store.showReceiptLogo) }
    var showCashierName by remember(store.showReceiptCashierName) { mutableStateOf(store.showReceiptCashierName) }
    var showStorePhone by remember(store.showReceiptStorePhone) { mutableStateOf(store.showReceiptStorePhone) }
    var showStoreAddress by remember(store.showReceiptStoreAddress) { mutableStateOf(store.showReceiptStoreAddress) }
    var showCustomerName by remember(store.showReceiptCustomerName) { mutableStateOf(store.showReceiptCustomerName) }
    var showOperatingHours by remember(store.showReceiptOperatingHours) { mutableStateOf(store.showReceiptOperatingHours) }
    var footerMessage by remember(store.receiptFooterMessage) { mutableStateOf(store.receiptFooterMessage) }
    var prefixInput by remember(store.receiptNumberPrefix) {
        mutableStateOf(PosFormatters.sanitizeReceiptPrefix(store.receiptNumberPrefix))
    }

    val sanitizedPreviewPrefix = remember(prefixInput) {
        PosFormatters.sanitizeReceiptPrefix(prefixInput)
    }

    val previewStore = remember(
        store,
        strings,
        showLogo,
        showCashierName,
        showStorePhone,
        showStoreAddress,
        showCustomerName,
        showOperatingHours,
        footerMessage,
        sanitizedPreviewPrefix
    ) {
        store.copy(
            storeName = store.storeName.ifBlank { strings.defaultStoreName },
            ownerName = store.ownerName.ifBlank { strings.receiptCashierLabel },
            phone = store.phone.ifBlank { "0812-3456-7890" },
            address = store.address.ifBlank { strings.receiptDefaultAddress },
            operatingHours = store.operatingHours.ifBlank { strings.receiptDefaultHours },
            showReceiptLogo = showLogo,
            showReceiptCashierName = showCashierName,
            showReceiptStorePhone = showStorePhone,
            showReceiptStoreAddress = showStoreAddress,
            showReceiptCustomerName = showCustomerName,
            showReceiptOperatingHours = showOperatingHours,
            receiptFooterMessage = footerMessage,
            receiptNumberPrefix = sanitizedPreviewPrefix
        )
    }

    val sampleTx = remember(previewStore.ownerName, sanitizedPreviewPrefix, strings) {
        PosTransaction(
            id = "preview",
            transactionNumber = "$sanitizedPreviewPrefix-001",
            customerName = strings.receiptDefaultCustomer,
            cashierName = previewStore.ownerName.ifBlank { strings.receiptCashierLabel },
            items = listOf(
                TransactionItem(
                    productId = "p1",
                    productName = strings.receiptSampleItem1,
                    unit = "Pcs",
                    unitPrice = 75000.0,
                    subtotal = 75000.0,
                    quantity = 1.0
                ),
                TransactionItem(
                    productId = "p2",
                    productName = strings.receiptSampleItem2,
                    unit = "Pcs",
                    unitPrice = 4000.0,
                    subtotal = 8000.0,
                    quantity = 2.0
                )
            ),
            subtotal = 83000.0,
            totalAmount = 83000.0,
            paymentMethod = PaymentMethod.CASH.code,
            amountPaid = 100000.0,
            changeAmount = 17000.0,
            transactionMillis = System.currentTimeMillis()
        )
    }

    val previewText = remember(previewStore, sampleTx, strings, currency.code, store.language) {
        ReceiptHelper.buildPlainTextReceipt(
            transaction = sampleTx,
            store = previewStore,
            strings = strings,
            language = store.language,
            charsPerLine = 32
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .widthIn(max = 500.dp)
                .heightIn(max = 690.dp)
                .testTag("receipt_customization_dialog")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 10.dp, top = 12.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = strings.receiptCustomTitle,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Rounded.Close, contentDescription = strings.close)
                    }
                }
                HorizontalDivider()

                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 18.dp, vertical = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(strings.receiptPreviewLabel, style = MaterialTheme.typography.labelLarge)
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = Color(0xFFFAFAFA),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
                            .testTag("receipt_custom_preview")
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.width(IntrinsicSize.Max),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                if (showLogo) {
                                    val bmp = remember(store.photoData) { ImageUtils.decodeDataUriToBitmap(store.photoData) }
                                    if (bmp != null) {
                                        Image(
                                            bitmap = bmp.asImageBitmap(),
                                            contentDescription = previewStore.storeName,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(CircleShape)
                                        )
                                    } else if (store.storeIconKey.isNotBlank()) {
                                        Box(
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(CircleShape)
                                                .background(parseHexColor(store.storeIconBgHex)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = storeBusinessIconVector(store.storeIconKey),
                                                contentDescription = previewStore.storeName,
                                                tint = Color.White,
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }
                                    } else {
                                        Image(
                                            painter = painterResource(id = R.drawable.ada_kasir_logo),
                                            contentDescription = strings.appName,
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .size(44.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                        )
                                    }
                                }
                                Text(
                                    text = previewText,
                                    fontFamily = JetBrainsMonoFontFamily,
                                    fontSize = 10.sp,
                                    lineHeight = 14.sp,
                                    softWrap = false,
                                    color = Color(0xFF111827)
                                )
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp))

                    ReceiptToggleRowItem(
                        label = strings.receiptShowLogoLabel,
                        checked = showLogo,
                        onCheckedChange = { showLogo = it },
                        modifier = Modifier.testTag("switch_receipt_logo")
                    )
                    ReceiptToggleRowItem(
                        label = strings.receiptShowCashierLabel,
                        checked = showCashierName,
                        onCheckedChange = { showCashierName = it },
                        modifier = Modifier.testTag("switch_receipt_cashier")
                    )
                    ReceiptToggleRowItem(
                        label = strings.receiptShowPhoneLabel,
                        checked = showStorePhone,
                        onCheckedChange = { showStorePhone = it },
                        modifier = Modifier.testTag("switch_receipt_phone")
                    )
                    ReceiptToggleRowItem(
                        label = strings.receiptShowAddressLabel,
                        checked = showStoreAddress,
                        onCheckedChange = { showStoreAddress = it },
                        modifier = Modifier.testTag("switch_receipt_address")
                    )
                    ReceiptToggleRowItem(
                        label = strings.receiptShowCustomerLabel,
                        checked = showCustomerName,
                        onCheckedChange = { showCustomerName = it },
                        modifier = Modifier.testTag("switch_receipt_customer")
                    )
                    ReceiptToggleRowItem(
                        label = strings.receiptShowOperatingHoursLabel,
                        checked = showOperatingHours,
                        onCheckedChange = { showOperatingHours = it },
                        modifier = Modifier.testTag("switch_receipt_operating_hours")
                    )

                    OutlinedTextField(
                        value = prefixInput,
                        onValueChange = { raw ->
                            prefixInput = raw.uppercase().filter { it.isLetterOrDigit() || it == '-' }.take(10)
                        },
                        label = { Text(strings.receiptPrefixLabel) },
                        placeholder = { Text(strings.receiptPrefixHint) },
                        supportingText = { Text("${strings.receiptNumber}: $sanitizedPreviewPrefix-YYYYMMDD-0001") },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("receipt_prefix_input")
                    )

                    OutlinedTextField(
                        value = footerMessage,
                        onValueChange = { footerMessage = it.take(200) },
                        label = { Text(strings.receiptFooterMessageLabel) },
                        placeholder = { Text(strings.receiptFooterMessageHint) },
                        minLines = 2,
                        maxLines = 3,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("receipt_footer_input")
                    )
                }

                HorizontalDivider()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 14.dp)
                ) {
                    Button(
                        onClick = {
                            onSave(
                                showLogo,
                                showCashierName,
                                showStorePhone,
                                showStoreAddress,
                                showCustomerName,
                                showOperatingHours,
                                footerMessage,
                                sanitizedPreviewPrefix
                            )
                        },
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 50.dp)
                            .testTag("save_receipt_customization_button")
                    ) {
                        Icon(Icons.Rounded.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = strings.saveChanges,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReceiptToggleRowItem(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onCheckedChange(!checked) }
            .padding(horizontal = 4.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
        )
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
internal fun EnhancedUserManagementDialog(
    store: StoreSettings,
    localPrefs: LocalAppPrefs,
    userEmail: String,
    staffRole: StaffRole,
    authorizedMonitorStores: List<StoreDirectoryEntry>,
    isMonitorMode: Boolean,
    onDismiss: () -> Unit,
    onOpenInfo: () -> Unit,
    onSetAccessMode: (AppAccessMode) -> Unit,
    onSwitchStaffRole: (StaffRole, String) -> Boolean,
    onSavePermissionsAndPin: (
        ownerPin: String,
        allowProducts: Boolean,
        allowSalesReport: Boolean,
        allowExpenses: Boolean,
        allowHistory: Boolean,
        allowSettings: Boolean,
        allowCancelRefund: Boolean
    ) -> Unit,
    onAddMonitorEmail: (String) -> Unit,
    onRemoveMonitorEmail: (String) -> Unit,
    onConnectMonitorStore: (StoreDirectoryEntry) -> Unit,
    onDisconnectMonitorStore: () -> Unit
) {
    val strings = LocalAppStrings.current
    var emailInput by remember { mutableStateOf("") }
    val selectedMode = if (isMonitorMode || localPrefs.accessMode == AppAccessMode.MONITOR.code) {
        AppAccessMode.MONITOR
    } else {
        AppAccessMode.CASHIER
    }

    var pinDraft by remember(store.ownerPin) { mutableStateOf(store.ownerPin) }
    var showPinChars by remember { mutableStateOf(false) }
    var allowProducts by remember(store.cashierAllowProducts) { mutableStateOf(store.cashierAllowProducts) }
    var allowSalesReport by remember(store.cashierAllowSalesReport) { mutableStateOf(store.cashierAllowSalesReport) }
    var allowExpenses by remember(store.cashierAllowExpenses) { mutableStateOf(store.cashierAllowExpenses) }
    var allowHistory by remember(store.cashierAllowHistory) { mutableStateOf(store.cashierAllowHistory) }
    var allowSettings by remember(store.cashierAllowSettings) { mutableStateOf(store.cashierAllowSettings) }
    var allowCancelRefund by remember(store.cashierAllowCancelRefund) { mutableStateOf(store.cashierAllowCancelRefund) }

    var showOwnerPinUnlock by remember { mutableStateOf(false) }
    var unlockPinInput by remember { mutableStateOf("") }
    var unlockPinError by remember { mutableStateOf<String?>(null) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .widthIn(max = 520.dp)
                .heightIn(max = 690.dp)
                .testTag("user_management_dialog")
        ) {
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = strings.userManageTitle,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        IconButton(
                            onClick = onOpenInfo,
                            modifier = Modifier.testTag("user_manage_dialog_info_button")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Info,
                                contentDescription = strings.userRoleInfoTitle,
                                tint = BrandBlue
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Rounded.Close, contentDescription = strings.close)
                    }
                }

                // Device Connection Mode Selector: Mode Kasir (Store Device) vs Mode Pemantau (Remote Viewer)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FilterChip(
                        selected = selectedMode == AppAccessMode.CASHIER,
                        onClick = { onSetAccessMode(AppAccessMode.CASHIER) },
                        leadingIcon = { Icon(Icons.Rounded.Person, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        label = { Text(strings.userModeCashierLabel, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_chip_cashier")
                    )
                    FilterChip(
                        selected = selectedMode == AppAccessMode.MONITOR,
                        onClick = { onSetAccessMode(AppAccessMode.MONITOR) },
                        leadingIcon = { Icon(Icons.Rounded.Visibility, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        label = { Text(strings.userModeMonitorLabel, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("mode_chip_monitor")
                    )
                }

                if (selectedMode == AppAccessMode.CASHIER) {
                    // Section A: Role Selector (Owner vs Kasir / Karyawan)
                    Text(
                        text = strings.staffRoleSectionTitle,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FilterChip(
                            selected = staffRole == StaffRole.OWNER,
                            onClick = {
                                if (staffRole != StaffRole.OWNER) {
                                    if (store.ownerPin.isBlank()) {
                                        onSwitchStaffRole(StaffRole.OWNER, "")
                                    } else {
                                        unlockPinInput = ""
                                        unlockPinError = null
                                        showOwnerPinUnlock = true
                                    }
                                }
                            },
                            leadingIcon = {
                                Icon(Icons.Rounded.AdminPanelSettings, contentDescription = null, modifier = Modifier.size(18.dp))
                            },
                            label = { Text(strings.roleOwnerLabel, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("role_chip_owner")
                        )
                        FilterChip(
                            selected = staffRole == StaffRole.CASHIER,
                            onClick = { onSwitchStaffRole(StaffRole.CASHIER, "") },
                            leadingIcon = {
                                Icon(Icons.Rounded.Badge, contentDescription = null, modifier = Modifier.size(18.dp))
                            },
                            label = { Text(strings.roleCashierLabel, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                            modifier = Modifier
                                .weight(1f)
                                .testTag("role_chip_cashier")
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = if (staffRole == StaffRole.OWNER) strings.roleOwnerDesc else strings.roleCashierDesc,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(12.dp)
                        )
                    }

                    // Owner-only Controls: Customize Cashier Permissions, Security PIN, & Remote Monitor Accounts
                    if (staffRole == StaffRole.OWNER) {
                        HorizontalDivider()
                        Text(
                            text = strings.cashierPermissionsTitle,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    allowProducts = false
                                    allowSalesReport = false
                                    allowExpenses = false
                                    allowHistory = false
                                    allowSettings = false
                                    allowCancelRefund = false
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(strings.presetCashierTxOnly, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                            FilledTonalButton(
                                onClick = {
                                    allowProducts = true
                                    allowSalesReport = true
                                    allowExpenses = true
                                    allowHistory = true
                                    allowSettings = true
                                    allowCancelRefund = true
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(strings.presetCashierAllFeatures, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            }
                        }

                        ReceiptToggleRowItem(strings.permAllowProducts, allowProducts, { allowProducts = it })
                        ReceiptToggleRowItem(strings.permAllowSalesReport, allowSalesReport, { allowSalesReport = it })
                        ReceiptToggleRowItem(strings.permAllowExpenses, allowExpenses, { allowExpenses = it })
                        ReceiptToggleRowItem(strings.permAllowHistory, allowHistory, { allowHistory = it })
                        ReceiptToggleRowItem(strings.permAllowCancelRefund, allowCancelRefund, { allowCancelRefund = it })
                        ReceiptToggleRowItem(strings.permAllowSettings, allowSettings, { allowSettings = it })

                        HorizontalDivider()

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(Icons.Rounded.Lock, contentDescription = null, tint = BrandBlue, modifier = Modifier.size(18.dp))
                            Text(
                                text = strings.ownerPinSectionTitle,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        OutlinedTextField(
                            value = pinDraft,
                            onValueChange = { raw ->
                                if (raw.length <= 6 && raw.all(Char::isDigit)) {
                                    pinDraft = raw
                                }
                            },
                            label = { Text(strings.ownerPinLabel) },
                            supportingText = { Text(strings.ownerPinHint) },
                            visualTransformation = if (showPinChars) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { showPinChars = !showPinChars }) {
                                    Icon(
                                        imageVector = if (showPinChars) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                                        contentDescription = null
                                    )
                                }
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("owner_pin_input")
                        )

                        Button(
                            onClick = {
                                onSavePermissionsAndPin(
                                    pinDraft,
                                    allowProducts,
                                    allowSalesReport,
                                    allowExpenses,
                                    allowHistory,
                                    allowSettings,
                                    allowCancelRefund
                                )
                            },
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("save_permissions_pin_button")
                        ) {
                            Icon(Icons.Rounded.Save, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(strings.savePermissionsButton)
                        }

                        HorizontalDivider()

                        // Remote Monitoring (1 to 2 Google Accounts)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = strings.monitorEmailListLabel,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = BrandBlue.copy(alpha = 0.14f)
                            ) {
                                Text(
                                    text = "${store.monitorEmails.size}/2",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = BrandBlue,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }
                        Text(
                            text = strings.monitorLimitNote,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = emailInput,
                                onValueChange = { emailInput = it },
                                placeholder = { Text(strings.monitorEmailInputHint) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                singleLine = true,
                                enabled = store.monitorEmails.size < 2,
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("monitor_email_input")
                            )
                            Button(
                                onClick = {
                                    val trimmed = emailInput.trim()
                                    if (trimmed.contains("@")) {
                                        onAddMonitorEmail(trimmed)
                                        emailInput = ""
                                    }
                                },
                                enabled = store.monitorEmails.size < 2 && emailInput.trim().contains("@"),
                                shape = RoundedCornerShape(14.dp),
                                modifier = Modifier
                                    .height(54.dp)
                                    .testTag("add_monitor_email_button")
                            ) {
                                Icon(Icons.Rounded.Add, contentDescription = strings.addMonitorEmailButton)
                            }
                        }

                        if (store.monitorEmails.isEmpty()) {
                            Text(
                                text = strings.noMonitorEmailsYet,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                store.monitorEmails.forEach { email ->
                                    Surface(
                                        shape = RoundedCornerShape(12.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 12.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(
                                                text = email,
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Medium,
                                                modifier = Modifier.weight(1f)
                                            )
                                            IconButton(
                                                onClick = { onRemoveMonitorEmail(email) },
                                                modifier = Modifier.size(36.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Rounded.DeleteOutline,
                                                    contentDescription = strings.delete,
                                                    tint = StatusError,
                                                    modifier = Modifier.size(18.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = strings.monitorStoresAvailableLabel,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )

                    if (authorizedMonitorStores.isEmpty()) {
                        Text(
                            text = String.format(strings.monitorNoStoresLinked, userEmail.ifBlank { "-" }),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            authorizedMonitorStores.forEach { entry ->
                                val isCurrentTarget = localPrefs.monitorTargetUserId == entry.userId && isMonitorMode
                                Surface(
                                    shape = RoundedCornerShape(14.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (isCurrentTarget) Icons.Rounded.CheckCircle else Icons.Rounded.Storefront,
                                            contentDescription = null,
                                            tint = if (isCurrentTarget) StatusSuccess else BrandBlue
                                        )
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = entry.storeName.ifBlank { strings.defaultStoreName },
                                                style = MaterialTheme.typography.titleSmall,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Text(
                                                text = entry.ownerEmail,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                        if (isCurrentTarget) {
                                            OutlinedButton(onClick = onDisconnectMonitorStore) {
                                                Text(strings.disconnectPrinterButton)
                                            }
                                        } else {
                                            Button(onClick = { onConnectMonitorStore(entry) }) {
                                                Text(strings.connectStoreButton)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showOwnerPinUnlock) {
        AlertDialog(
            onDismissRequest = { showOwnerPinUnlock = false },
            title = { Text(strings.enterPinTitle) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = strings.enterPinSubtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    OutlinedTextField(
                        value = unlockPinInput,
                        onValueChange = {
                            if (it.length <= 6 && it.all(Char::isDigit)) {
                                unlockPinInput = it
                                unlockPinError = null
                            }
                        },
                        label = { Text(strings.pinInputLabel) },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        singleLine = true,
                        isError = unlockPinError != null,
                        supportingText = unlockPinError?.let { err -> { Text(err) } },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val ok = onSwitchStaffRole(StaffRole.OWNER, unlockPinInput)
                        if (ok) {
                            showOwnerPinUnlock = false
                        } else {
                            unlockPinError = strings.wrongPinError
                        }
                    }
                ) {
                    Text(strings.confirm)
                }
            },
            dismissButton = {
                TextButton(onClick = { showOwnerPinUnlock = false }) {
                    Text(strings.cancel)
                }
            }
        )
    }
}

@Composable
internal fun TutorialUsageDialog(
    @Suppress("UNUSED_PARAMETER") language: String,
    onDismiss: () -> Unit
) {
    val strings = LocalAppStrings.current
    val steps = strings.tutorialStepsList()

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .widthIn(max = 520.dp)
                .heightIn(max = 680.dp)
                .testTag("tutorial_guide_dialog")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 10.dp, top = 12.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Rounded.MenuBook, contentDescription = null, tint = BrandBlue)
                        Text(
                            text = strings.tutorialTitle,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Rounded.Close, contentDescription = strings.close)
                    }
                }
                HorizontalDivider()

                Column(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .verticalScroll(rememberScrollState())
                        .padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    steps.forEach { (title, body) ->
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = BrandBlue
                                )
                                Text(
                                    text = body,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }

                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(onClick = onDismiss, shape = RoundedCornerShape(12.dp)) {
                        Text(strings.close)
                    }
                }
            }
        }
    }
}

@Composable
internal fun LanguagePickerDialog(
    selectedCode: String,
    onDismiss: () -> Unit,
    onSelectLanguage: (PosLanguageOption) -> Unit
) {
    val strings = LocalAppStrings.current
    val normalizedSelected = AppLanguageCatalog.normalizeCode(selectedCode)
    var searchQuery by remember { mutableStateOf("") }
    var selectedRegion by remember { mutableStateOf<LocalizationRegion?>(null) }

    val groupedLanguages = remember(searchQuery, selectedRegion) {
        val q = searchQuery.trim().lowercase()
        LocalizationRegion.entries
            .filter { selectedRegion == null || it == selectedRegion }
            .mapNotNull { region ->
                val list = AppLanguageCatalog.all.filter { option ->
                    option.region == region && (
                        q.isEmpty() ||
                            option.nativeName.lowercase().contains(q) ||
                            option.regionLabel.lowercase().contains(q) ||
                            option.code.lowercase().contains(q)
                        )
                }
                if (list.isEmpty()) null else region to list
            }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .widthIn(max = 520.dp)
                .heightIn(max = 680.dp)
                .testTag("language_picker_dialog")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 10.dp, top = 12.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Rounded.Language, contentDescription = null, tint = BrandBlue)
                        Column {
                            Text(
                                text = strings.languageSection,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = strings.languagePickerSubtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Rounded.Close, contentDescription = strings.close)
                    }
                }
                HorizontalDivider()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text(strings.searchLanguageHint, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                        trailingIcon = if (searchQuery.isNotEmpty()) {
                            {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Rounded.Close, contentDescription = strings.close)
                                }
                            }
                        } else null,
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("language_search_input")
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(
                            selected = selectedRegion == null,
                            onClick = { selectedRegion = null },
                            label = { Text(strings.all) }
                        )
                        LocalizationRegion.entries.forEach { region ->
                            FilterChip(
                                selected = selectedRegion == region,
                                onClick = {
                                    selectedRegion = if (selectedRegion == region) null else region
                                },
                                label = { Text(region.label(strings)) }
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .fillMaxWidth()
                        .testTag("language_list"),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    groupedLanguages.forEach { (region, itemsInRegion) ->
                        item(key = "lang_hdr_${region.code}") {
                            Text(
                                text = region.label(strings),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = BrandBlue,
                                modifier = Modifier.padding(top = 6.dp, bottom = 2.dp, start = 4.dp)
                            )
                        }
                        items(itemsInRegion, key = { it.code }) { option ->
                            val isSelected = option.code == normalizedSelected
                            Surface(
                                onClick = { onSelectLanguage(option) },
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSelected) BrandBlue.copy(alpha = 0.12f)
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.42f),
                                border = if (isSelected) BorderStroke(1.5.dp, BrandBlue)
                                else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 52.dp)
                                    .testTag("lang_option_${option.code}")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = option.flagEmoji,
                                        fontSize = 22.sp
                                    )
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = option.nativeName,
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                            color = if (isSelected) BrandBlue else MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "${option.regionLabel} • ${option.code.uppercase()}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Rounded.CheckCircle,
                                            contentDescription = option.nativeName,
                                            tint = BrandBlue
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(strings.close)
                    }
                }
            }
        }
    }
}

@Composable
internal fun CurrencyPickerDialog(
    selectedCode: String,
    onDismiss: () -> Unit,
    onSelectCurrency: (PosCurrencyOption) -> Unit
) {
    val strings = LocalAppStrings.current
    val normalizedSelected = AppCurrencyCatalog.normalizeCode(selectedCode)
    val activeOption = remember(normalizedSelected) { AppCurrencyCatalog.fromCode(normalizedSelected) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedRegion by remember { mutableStateOf<LocalizationRegion?>(null) }

    val groupedCurrencies = remember(searchQuery, selectedRegion) {
        val q = searchQuery.trim().lowercase()
        LocalizationRegion.entries
            .filter { selectedRegion == null || it == selectedRegion }
            .mapNotNull { region ->
                val list = AppCurrencyCatalog.all.filter { option ->
                    option.region == region && (
                        q.isEmpty() ||
                            option.code.lowercase().contains(q) ||
                            option.name.lowercase().contains(q) ||
                            option.country.lowercase().contains(q) ||
                            option.symbol.lowercase().contains(q)
                        )
                }
                if (list.isEmpty()) null else region to list
            }
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .safeDrawingPadding()
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .widthIn(max = 520.dp)
                .heightIn(max = 690.dp)
                .testTag("currency_picker_dialog")
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, end = 10.dp, top = 12.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Rounded.Payments, contentDescription = null, tint = BrandGreen)
                        Column {
                            Text(
                                text = strings.currencyPickerTitle,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = strings.currencySettingsSubtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Rounded.Close, contentDescription = strings.close)
                    }
                }
                HorizontalDivider()

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = BrandGreen.copy(alpha = 0.10f),
                        border = BorderStroke(1.dp, BrandGreen.copy(alpha = 0.25f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = strings.currencySamplePreviewLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "${activeOption.flagEmoji} ${activeOption.code} (${activeOption.symbol})",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            Text(
                                text = PosFormatters.formatCurrency(12500.50, activeOption.code),
                                style = MaterialTheme.typography.titleMedium,
                                fontFamily = JetBrainsMonoFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = BrandGreen
                            )
                        }
                    }

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text(strings.searchCurrencyHint, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                        leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                        trailingIcon = if (searchQuery.isNotEmpty()) {
                            {
                                IconButton(onClick = { searchQuery = "" }) {
                                    Icon(Icons.Rounded.Close, contentDescription = strings.close)
                                }
                            }
                        } else null,
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("currency_search_input")
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        FilterChip(
                            selected = selectedRegion == null,
                            onClick = { selectedRegion = null },
                            label = { Text(strings.all) }
                        )
                        LocalizationRegion.entries.forEach { region ->
                            FilterChip(
                                selected = selectedRegion == region,
                                onClick = {
                                    selectedRegion = if (selectedRegion == region) null else region
                                },
                                label = { Text(region.label(strings)) }
                            )
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .fillMaxWidth()
                        .testTag("currency_list"),
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    groupedCurrencies.forEach { (region, itemsInRegion) ->
                        item(key = "curr_hdr_${region.code}") {
                            Text(
                                text = region.label(strings),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = BrandGreen,
                                modifier = Modifier.padding(top = 6.dp, bottom = 2.dp, start = 4.dp)
                            )
                        }
                        items(itemsInRegion, key = { it.code }) { option ->
                            val isSelected = option.code == normalizedSelected
                            Surface(
                                onClick = { onSelectCurrency(option) },
                                shape = RoundedCornerShape(14.dp),
                                color = if (isSelected) BrandGreen.copy(alpha = 0.12f)
                                else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.42f),
                                border = if (isSelected) BorderStroke(1.5.dp, BrandGreen)
                                else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(min = 52.dp)
                                    .testTag("currency_option_${option.code}")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 14.dp, vertical = 10.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Text(
                                        text = option.flagEmoji,
                                        fontSize = 22.sp
                                    )
                                    Surface(
                                        shape = RoundedCornerShape(10.dp),
                                        color = if (isSelected) BrandGreen else BrandBlue.copy(alpha = 0.12f)
                                    ) {
                                        Text(
                                            text = option.symbol,
                                            style = MaterialTheme.typography.labelLarge,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSelected) Color.White else BrandBlue,
                                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                        )
                                    }
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = "${option.code} — ${option.name}",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                            color = if (isSelected) BrandGreen else MaterialTheme.colorScheme.onSurface,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = option.country,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Rounded.CheckCircle,
                                            contentDescription = option.code,
                                            tint = BrandGreen
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(strings.close)
                    }
                }
            }
        }
    }
}
