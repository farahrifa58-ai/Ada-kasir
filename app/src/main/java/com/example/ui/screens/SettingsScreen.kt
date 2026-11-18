package com.example.ui.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.automirrored.rounded.ReceiptLong
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.Backup
import androidx.compose.material.icons.rounded.Bluetooth
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material.icons.rounded.ManageAccounts
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material.icons.rounded.Percent
import androidx.compose.material.icons.rounded.Print
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Restore
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import com.example.R
import com.example.data.firebase.BackupSummary
import com.example.data.model.AppAccessMode
import com.example.data.model.AppCurrencyCatalog
import com.example.data.model.AppLanguageCatalog
import com.example.data.model.DiscountType
import com.example.data.model.StaffRole
import com.example.data.model.StoreDirectoryEntry
import com.example.data.model.StoreSettings
import com.example.data.model.TaxType
import com.example.data.prefs.LocalAppPrefs
import com.example.ui.components.PhotoPickerSheetDialog
import com.example.ui.components.StoreProfileAvatar
import com.example.ui.components.SubScreenTopBar
import com.example.ui.theme.BrandBlue
import com.example.ui.theme.BrandGreen
import com.example.ui.theme.LocalAppCurrency
import com.example.ui.theme.LocalAppStrings
import com.example.ui.theme.StatusError
import com.example.ui.theme.StatusSuccess
import com.example.util.PairedPrinter
import com.example.util.PaperWidth
import com.example.util.PosFormatters
import com.example.util.ReceiptHelper
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    store: StoreSettings,
    localPrefs: LocalAppPrefs,
    userEmail: String,
    isBackupBusy: Boolean,
    authorizedMonitorStores: List<StoreDirectoryEntry> = emptyList(),
    isMonitorMode: Boolean = false,
    staffRole: StaffRole = StaffRole.OWNER,
    onBackToDashboard: () -> Unit,
    onSaveProfile: (String, String, String, String, String, Double) -> Unit,
    onSaveProfileWithIcon: ((String, String, String, String, String, String, String, Double) -> Unit)? = null,
    onSaveProfileWithDetails: ((String, String, String, String, String, String, String, Double, String, Boolean) -> Unit)? = null,
    onSaveTaxDiscountSettings: (TaxType, Double, Double, DiscountType, Double) -> Unit = { _, _, _, _, _ -> },
    onSaveReceiptCustomization: (Boolean, Boolean, Boolean, Boolean, Boolean, String) -> Unit = { _, _, _, _, _, _ -> },
    onSaveReceiptCustomizationWithPrefix: ((Boolean, Boolean, Boolean, Boolean, Boolean, String, String) -> Unit)? = null,
    onSaveReceiptCustomizationFull: ((Boolean, Boolean, Boolean, Boolean, Boolean, Boolean, String, String) -> Unit)? = null,
    onSaveUserPermissionsAndPin: (String, Boolean, Boolean, Boolean, Boolean, Boolean, Boolean) -> Unit = { _, _, _, _, _, _, _ -> },
    onSwitchStaffRole: (StaffRole, String) -> Boolean = { _, _ -> true },
    onSetAccessMode: (AppAccessMode) -> Unit = {},
    onAddMonitorEmail: (String) -> Unit = {},
    onRemoveMonitorEmail: (String) -> Unit = {},
    onConnectMonitorStore: (StoreDirectoryEntry) -> Unit = {},
    onDisconnectMonitorStore: () -> Unit = {},
    onSetDarkTheme: (Boolean) -> Unit,
    onSetLanguage: (String) -> Unit,
    onSetCurrency: (String) -> Unit = {},
    onSelectPrinter: (String, String) -> Unit,
    onClearPrinter: () -> Unit,
    onSetPrinterPaperWidth: (Int) -> Unit,
    onGenerateBackupJson: suspend () -> Result<String>,
    onValidateBackupJson: (String) -> Result<BackupSummary>,
    onRestoreBackupJson: (String) -> Unit,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val strings = LocalAppStrings.current
    val currency = LocalAppCurrency.current
    val scope = rememberCoroutineScope()

    var showProfileDialog by remember { mutableStateOf(false) }
    var showTaxDiscountDialog by remember { mutableStateOf(false) }
    var showAppearanceDialog by remember { mutableStateOf(false) }
    var showLanguagePickerDialog by remember { mutableStateOf(false) }
    var showCurrencyPickerDialog by remember { mutableStateOf(false) }
    var showPrinterDialog by remember { mutableStateOf(false) }
    var showReceiptCustomDialog by remember { mutableStateOf(false) }
    var showUserManageDialog by remember { mutableStateOf(false) }
    var showUserModeInfoDialog by remember { mutableStateOf(false) }
    var showBackupDialog by remember { mutableStateOf(false) }
    var showTutorialDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }
    var showPhotoPicker by remember { mutableStateOf(false) }

    var photoDataDraft by remember(store.photoData) { mutableStateOf(store.photoData) }

    var pendingRestoreJson by remember { mutableStateOf<String?>(null) }
    var pendingRestoreSummary by remember { mutableStateOf<BackupSummary?>(null) }
    var isSigningOut by remember { mutableStateOf(false) }

    val backupExportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        if (uri != null) {
            scope.launch {
                val res = onGenerateBackupJson()
                res.onSuccess { json ->
                    try {
                        context.contentResolver.openOutputStream(uri)?.use { out ->
                            out.write(json.toByteArray(Charsets.UTF_8))
                        }
                        Toast.makeText(context, strings.backupSuccessMsg, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(context, e.localizedMessage ?: strings.errSaveFailed, Toast.LENGTH_SHORT).show()
                    }
                }.onFailure { err ->
                    Toast.makeText(context, err.localizedMessage ?: strings.errSaveFailed, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val restoreImportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            try {
                val json = context.contentResolver.openInputStream(uri)?.bufferedReader()?.use { it.readText() } ?: ""
                val validation = onValidateBackupJson(json)
                validation.onSuccess { summary ->
                    pendingRestoreJson = json
                    pendingRestoreSummary = summary
                }.onFailure {
                    Toast.makeText(context, strings.restoreInvalidMsg, Toast.LENGTH_LONG).show()
                }
            } catch (_: Exception) {
                Toast.makeText(context, strings.restoreInvalidMsg, Toast.LENGTH_LONG).show()
            }
        }
    }

    val lastBackupStr = store.lastBackupAt?.toDate()?.time?.let {
        PosFormatters.formatDateTime(it, store.language)
    } ?: strings.neverBackedUp

    val printerSummarySubtitle = if (localPrefs.defaultPrinterName.isNotBlank()) {
        "${localPrefs.defaultPrinterName} • ${localPrefs.printerPaperWidthMm} mm"
    } else {
        "${strings.printerSettingsSubtitle} (${localPrefs.printerPaperWidthMm} mm)"
    }

    val taxDiscountSummary = remember(store, strings, currency.code) {
        val taxText = when (TaxType.fromCode(store.defaultTaxType)) {
            TaxType.NONE -> strings.taxNone
            TaxType.AUTO -> "${strings.taxAuto} ${PosFormatters.formatQuantity(store.defaultTaxPercent)}%"
            TaxType.MANUAL -> "${strings.taxManualWord}: ${PosFormatters.formatCurrency(store.defaultManualTaxAmount, currency.code)}"
        }
        val discText = when (DiscountType.fromCode(store.defaultDiscountType)) {
            DiscountType.NONE -> strings.discountNone
            DiscountType.PERCENT -> "${strings.discountLabel} ${PosFormatters.formatQuantity(store.defaultDiscountValue)}%"
            DiscountType.AMOUNT -> "${strings.discountLabel} ${PosFormatters.formatCurrency(store.defaultDiscountValue, currency.code)}"
        }
        "$taxText • $discText"
    }

    Column(modifier = modifier.fillMaxSize()) {
        SubScreenTopBar(
            title = strings.menuSettings,
            onBackClick = onBackToDashboard,
            centeredTitle = true
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .widthIn(max = 820.dp)
                    .fillMaxSize()
                    .testTag("settings_lazy_column"),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 36.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // SECTION 1: Konfigurasi Toko & POS
                item {
                    SettingsSectionHeader(strings.settingsConfigSection)
                }
                item {
                    SettingsMenuCard(
                        icon = Icons.Rounded.Storefront,
                        iconTint = Color(0xFF2563EB),
                        iconBg = Color(0xFFDBEAFE),
                        leadingContent = {
                            StoreProfileAvatar(
                                photoData = store.photoData,
                                storeIconKey = store.storeIconKey,
                                storeIconBgHex = store.storeIconBgHex,
                                size = 44.dp
                            )
                        },
                        title = strings.storeProfileSection,
                        subtitle = store.storeName.ifBlank { strings.storeProfileSubtitle },
                        onClick = { showProfileDialog = true },
                        modifier = Modifier.testTag("settings_store_profile_item")
                    )
                }
                item {
                    SettingsMenuCard(
                        icon = Icons.Rounded.Percent,
                        iconTint = Color(0xFF0D9488),
                        iconBg = Color(0xFFCCFBF1),
                        title = strings.taxDiscountTitle,
                        subtitle = taxDiscountSummary,
                        onClick = { showTaxDiscountDialog = true },
                        modifier = Modifier.testTag("settings_tax_discount_item")
                    )
                }
                item {
                    val themeLabel = if (store.darkTheme) strings.darkTheme else strings.lightTheme
                    val langOption = AppLanguageCatalog.fromCode(store.language)
                    SettingsMenuCard(
                        icon = Icons.Rounded.Palette,
                        iconTint = Color(0xFF059669),
                        iconBg = Color(0xFFD1FAE5),
                        title = strings.appearanceLanguageTitle,
                        subtitle = "$themeLabel • ${langOption.flagEmoji} ${langOption.nativeName}",
                        onClick = { showAppearanceDialog = true },
                        modifier = Modifier.testTag("settings_appearance_item")
                    )
                }
                item {
                    SettingsMenuCard(
                        icon = Icons.Rounded.Payments,
                        iconTint = Color(0xFF16A34A),
                        iconBg = Color(0xFFDCFCE7),
                        title = strings.currencySectionTitle,
                        subtitle = "${currency.flagEmoji} ${currency.code} (${currency.symbol}) • ${currency.name}",
                        onClick = { showCurrencyPickerDialog = true },
                        modifier = Modifier.testTag("settings_currency_item")
                    )
                }
                item {
                    val roleLabel = when {
                        isMonitorMode || localPrefs.accessMode == AppAccessMode.MONITOR.code -> strings.userModeMonitorLabel
                        staffRole == StaffRole.CASHIER -> strings.roleCashierLabel
                        else -> strings.roleOwnerLabel
                    }
                    SettingsMenuCard(
                        icon = Icons.Rounded.ManageAccounts,
                        iconTint = Color(0xFF0284C7),
                        iconBg = Color(0xFFE0F2FE),
                        title = strings.userManageTitle,
                        subtitle = "$roleLabel • ${store.monitorEmails.size}/2 ${strings.monitorWord}",
                        onClick = { showUserManageDialog = true },
                        trailingAccessory = {
                            IconButton(
                                onClick = { showUserModeInfoDialog = true },
                                modifier = Modifier.testTag("user_mode_info_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    contentDescription = strings.userRoleInfoTitle,
                                    tint = BrandBlue
                                )
                            }
                        },
                        modifier = Modifier.testTag("settings_user_management_item")
                    )
                }

                // SECTION 2: Hardware & Struk
                item {
                    Spacer(Modifier.height(2.dp))
                    SettingsSectionHeader(strings.settingsHardwareSection)
                }
                item {
                    SettingsMenuCard(
                        icon = Icons.Rounded.Print,
                        iconTint = Color(0xFF4F46E5),
                        iconBg = Color(0xFFE0E7FF),
                        title = strings.printerSettingsTitle,
                        subtitle = printerSummarySubtitle,
                        onClick = { showPrinterDialog = true },
                        modifier = Modifier.testTag("settings_printer_item")
                    )
                }
                item {
                    val prefix = PosFormatters.sanitizeReceiptPrefix(store.receiptNumberPrefix)
                    SettingsMenuCard(
                        icon = Icons.AutoMirrored.Rounded.ReceiptLong,
                        iconTint = Color(0xFFEA580C),
                        iconBg = Color(0xFFFFEDD5),
                        title = strings.receiptCustomTitle,
                        subtitle = "${strings.prefixLabel}: $prefix • ${strings.receiptCustomSubtitle}",
                        onClick = { showReceiptCustomDialog = true },
                        modifier = Modifier.testTag("settings_receipt_customization_item")
                    )
                }

                // SECTION 3: Data & Akun
                item {
                    Spacer(Modifier.height(2.dp))
                    SettingsSectionHeader(strings.settingsDataSection)
                }
                item {
                    SettingsMenuCard(
                        icon = Icons.Rounded.Backup,
                        iconTint = Color(0xFFD97706),
                        iconBg = Color(0xFFFEF3C7),
                        title = strings.backupRestoreSection,
                        subtitle = "${strings.lastBackupLabel}: $lastBackupStr",
                        onClick = { showBackupDialog = true },
                        modifier = Modifier.testTag("settings_backup_item")
                    )
                }
                item {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(enabled = !isSigningOut) {
                                    if (!isSigningOut) {
                                        isSigningOut = true
                                        onSignOut()
                                    }
                                }
                                .padding(horizontal = 16.dp, vertical = 13.dp)
                                .testTag("settings_sign_out_button"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(StatusError.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSigningOut) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(20.dp),
                                        color = StatusError,
                                        strokeWidth = 2.dp
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Rounded.Logout,
                                        contentDescription = strings.signOutButton,
                                        tint = StatusError,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = if (isSigningOut) strings.loading else strings.signOutButton,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = StatusError
                                )
                                Text(
                                    text = if (userEmail.isNotBlank()) userEmail else strings.syncedWithGoogle,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Icon(
                                imageVector = Icons.Rounded.ChevronRight,
                                contentDescription = null,
                                tint = StatusError.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                // SECTION 4: Panduan & Tentang Aplikasi (Bottom-most)
                item {
                    Spacer(Modifier.height(2.dp))
                    SettingsSectionHeader(strings.settingsGuideSection)
                }
                item {
                    SettingsMenuCard(
                        icon = Icons.Rounded.MenuBook,
                        iconTint = Color(0xFF0891B2),
                        iconBg = Color(0xFFCFFAFE),
                        title = strings.tutorialTitle,
                        subtitle = strings.tutorialSubtitle,
                        onClick = { showTutorialDialog = true },
                        modifier = Modifier.testTag("settings_tutorial_item")
                    )
                }
                item {
                    AboutAppBottomCard(
                        userEmail = userEmail,
                        onClick = { showAboutDialog = true },
                        modifier = Modifier.testTag("settings_about_item")
                    )
                }
            }
        }
    }

    // --- Dialog 1: Profil Toko / Bisnis + Ikon Usaha, Warna Background & Jam Operasional ---
    if (showProfileDialog) {
        StoreProfileEditorDialog(
            initialStoreName = store.storeName,
            initialOwnerName = store.ownerName,
            initialPhone = store.phone,
            initialAddress = store.address,
            initialOperatingHours = store.operatingHours,
            initialShowOperatingHoursOnReceipt = store.showReceiptOperatingHours,
            initialPhotoData = photoDataDraft,
            initialIconKey = store.storeIconKey,
            initialIconBgHex = store.storeIconBgHex,
            language = store.language,
            onDismiss = {
                photoDataDraft = store.photoData
                showProfileDialog = false
            },
            onOpenPhotoPicker = { showPhotoPicker = true },
            onClearPhoto = { photoDataDraft = "" },
            onSave = { name, owner, ph, addr, opHours, showOpHours, iconKey, iconBgHex ->
                when {
                    onSaveProfileWithDetails != null -> onSaveProfileWithDetails(
                        name,
                        owner,
                        ph,
                        addr,
                        photoDataDraft,
                        iconKey,
                        iconBgHex,
                        store.defaultTaxPercent,
                        opHours,
                        showOpHours
                    )
                    onSaveProfileWithIcon != null -> onSaveProfileWithIcon(
                        name,
                        owner,
                        ph,
                        addr,
                        photoDataDraft,
                        iconKey,
                        iconBgHex,
                        store.defaultTaxPercent
                    )
                    else -> onSaveProfile(name, owner, ph, addr, photoDataDraft, store.defaultTaxPercent)
                }
                showProfileDialog = false
            }
        )
    }

    if (showPhotoPicker) {
        PhotoPickerSheetDialog(
            onDismiss = { showPhotoPicker = false },
            onPhotoCompressed = { newPhoto ->
                photoDataDraft = newPhoto
            }
        )
    }

    // --- Dialog 1B: PPN & Diskon ---
    if (showTaxDiscountDialog) {
        TaxDiscountSettingsDialog(
            store = store,
            onDismiss = { showTaxDiscountDialog = false },
            onSave = { taxType, autoPct, manualAmt, discType, discVal ->
                onSaveTaxDiscountSettings(taxType, autoPct, manualAmt, discType, discVal)
                showTaxDiscountDialog = false
            }
        )
    }

    // --- Dialog 2: Tampilan & Bahasa ---
    if (showAppearanceDialog) {
        val currentLangOption = AppLanguageCatalog.fromCode(store.language)
        AlertDialog(
            onDismissRequest = { showAppearanceDialog = false },
            title = { Text(strings.appearanceLanguageTitle) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    Text(strings.themeSection, style = MaterialTheme.typography.titleSmall)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        FilterChip(
                            selected = !store.darkTheme,
                            onClick = { onSetDarkTheme(false) },
                            leadingIcon = { Icon(Icons.Rounded.LightMode, contentDescription = null, modifier = Modifier.size(18.dp)) },
                            label = { Text(strings.lightTheme) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = store.darkTheme,
                            onClick = { onSetDarkTheme(true) },
                            leadingIcon = { Icon(Icons.Rounded.DarkMode, contentDescription = null, modifier = Modifier.size(18.dp)) },
                            label = { Text(strings.darkTheme) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    HorizontalDivider()
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Rounded.Language, contentDescription = null, tint = BrandBlue, modifier = Modifier.size(18.dp))
                        Text(strings.languageSection, style = MaterialTheme.typography.titleSmall)
                    }
                    Surface(
                        onClick = {
                            showAppearanceDialog = false
                            showLanguagePickerDialog = true
                        },
                        shape = RoundedCornerShape(14.dp),
                        color = BrandBlue.copy(alpha = 0.08f),
                        border = BorderStroke(1.dp, BrandBlue.copy(alpha = 0.3f)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("open_language_picker_button")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(text = currentLangOption.flagEmoji, style = MaterialTheme.typography.titleLarge)
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = currentLangOption.nativeName,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${currentLangOption.regionLabel} • ${strings.languagePickerSubtitle}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = BrandBlue,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Icon(
                                imageVector = Icons.Rounded.ChevronRight,
                                contentDescription = strings.languageSection,
                                tint = BrandBlue
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showAppearanceDialog = false }) {
                    Text(strings.close)
                }
            }
        )
    }

    if (showLanguagePickerDialog) {
        LanguagePickerDialog(
            selectedCode = store.language,
            onDismiss = { showLanguagePickerDialog = false },
            onSelectLanguage = { selected ->
                onSetLanguage(selected.code)
                showLanguagePickerDialog = false
            }
        )
    }

    if (showCurrencyPickerDialog) {
        CurrencyPickerDialog(
            selectedCode = currency.code,
            onDismiss = { showCurrencyPickerDialog = false },
            onSelectCurrency = { selected ->
                onSetCurrency(selected.code)
                Toast.makeText(
                    context,
                    "${strings.currencySavedMsg} (${selected.code})",
                    Toast.LENGTH_SHORT
                ).show()
                showCurrencyPickerDialog = false
            }
        )
    }

    // --- Dialog 3: Printer Kasir & Daftar Printer Tersambung ---
    if (showPrinterDialog) {
        PrinterSettingsDialog(
            store = store,
            localPrefs = localPrefs,
            onDismiss = { showPrinterDialog = false },
            onSelectPrinter = onSelectPrinter,
            onClearPrinter = onClearPrinter,
            onSetPaperWidth = onSetPrinterPaperWidth
        )
    }

    // --- Dialog 4: Backup & Restore ---
    if (showBackupDialog) {
        AlertDialog(
            onDismissRequest = { if (!isBackupBusy) showBackupDialog = false },
            title = { Text(strings.backupRestoreSection) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "${strings.lastBackupLabel}: $lastBackupStr",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    if (isBackupBusy) {
                        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                        Text(
                            text = strings.backupInProgress,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    FilledTonalButton(
                        onClick = {
                            backupExportLauncher.launch("ADA_KASIR_Backup_${System.currentTimeMillis()}.json")
                        },
                        enabled = !isBackupBusy,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Rounded.Backup, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(strings.manualBackupButton)
                    }
                    OutlinedButton(
                        onClick = {
                            restoreImportLauncher.launch(arrayOf("application/json", "*/*"))
                        },
                        enabled = !isBackupBusy,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Icon(Icons.Rounded.Restore, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(strings.restoreBackupButton)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showBackupDialog = false }, enabled = !isBackupBusy) {
                    Text(strings.close)
                }
            }
        )
    }

    // --- Dialog 4B: Tutorial Penggunaan Singkat ---
    if (showTutorialDialog) {
        TutorialUsageDialog(
            language = store.language,
            onDismiss = { showTutorialDialog = false }
        )
    }

    // --- Dialog 5: Tentang Aplikasi & Akun Google ---
    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            modifier = Modifier.testTag("about_app_dialog"),
            title = { Text(strings.aboutSection) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ada_kasir_logo),
                            contentDescription = strings.appName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(52.dp)
                                .clip(RoundedCornerShape(14.dp))
                        )
                        Column {
                            Text(
                                text = strings.appName,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${strings.developerLabel}: ${strings.developerBy}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "${strings.versionLabel}: 1.0.0",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Text(strings.aboutDescription, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(strings.copyrightText, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    Text(strings.googleAccountSection, style = MaterialTheme.typography.titleSmall)
                    Text(
                        text = if (userEmail.isNotBlank()) "$userEmail • ${strings.syncedWithGoogle}" else strings.syncedWithGoogle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) {
                    Text(strings.close)
                }
            }
        )
    }

    val summary = pendingRestoreSummary
    val jsonToRestore = pendingRestoreJson
    if (summary != null && jsonToRestore != null) {
        AlertDialog(
            onDismissRequest = {
                pendingRestoreJson = null
                pendingRestoreSummary = null
            },
            title = { Text(strings.restoreConfirmTitle) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(strings.restoreConfirmBody)
                    HorizontalDivider()
                    Text("${strings.storeNameLabel}: ${summary.storeName}", style = MaterialTheme.typography.bodySmall)
                    Text("${strings.menuProducts}: ${summary.productCount}", style = MaterialTheme.typography.bodySmall)
                    Text("${strings.menuHistory}: ${summary.transactionCount}", style = MaterialTheme.typography.bodySmall)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        pendingRestoreJson = null
                        pendingRestoreSummary = null
                        showBackupDialog = false
                        onRestoreBackupJson(jsonToRestore)
                    }
                ) {
                    Text(strings.confirm)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        pendingRestoreJson = null
                        pendingRestoreSummary = null
                    }
                ) {
                    Text(strings.cancel)
                }
            }
        )
    }

    // --- Dialog 6: Kustomisasi Struk + Prefix Nota & Jam Operasional ---
    if (showReceiptCustomDialog) {
        ReceiptCustomizationWithPrefixDialog(
            store = store,
            onDismiss = { showReceiptCustomDialog = false },
            onSave = { showLogo, showCashier, showPhone, showAddress, showCustomer, showOpHours, footer, prefix ->
                when {
                    onSaveReceiptCustomizationFull != null -> onSaveReceiptCustomizationFull(
                        showLogo, showCashier, showPhone, showAddress, showCustomer, showOpHours, footer, prefix
                    )
                    onSaveReceiptCustomizationWithPrefix != null -> onSaveReceiptCustomizationWithPrefix(
                        showLogo, showCashier, showPhone, showAddress, showCustomer, footer, prefix
                    )
                    else -> onSaveReceiptCustomization(
                        showLogo, showCashier, showPhone, showAddress, showCustomer, footer
                    )
                }
                showReceiptCustomDialog = false
            }
        )
    }

    // --- Dialog 7: Kelola Pengguna (Owner, Kasir/Karyawan + PIN + Monitor 1-2 Akun) ---
    if (showUserManageDialog) {
        EnhancedUserManagementDialog(
            store = store,
            localPrefs = localPrefs,
            userEmail = userEmail,
            staffRole = staffRole,
            authorizedMonitorStores = authorizedMonitorStores,
            isMonitorMode = isMonitorMode,
            onDismiss = { showUserManageDialog = false },
            onOpenInfo = { showUserModeInfoDialog = true },
            onSetAccessMode = onSetAccessMode,
            onSwitchStaffRole = onSwitchStaffRole,
            onSavePermissionsAndPin = { pin, pProd, pRep, pExp, pHist, pSet, pCanc ->
                onSaveUserPermissionsAndPin(pin, pProd, pRep, pExp, pHist, pSet, pCanc)
            },
            onAddMonitorEmail = onAddMonitorEmail,
            onRemoveMonitorEmail = onRemoveMonitorEmail,
            onConnectMonitorStore = { entry ->
                onConnectMonitorStore(entry)
                showUserManageDialog = false
            },
            onDisconnectMonitorStore = onDisconnectMonitorStore
        )
    }

    // --- Dialog 8: Info (i) Mode Kasir & Pemantau ---
    if (showUserModeInfoDialog) {
        AlertDialog(
            onDismissRequest = { showUserModeInfoDialog = false },
            title = { Text(strings.userRoleInfoTitle) },
            text = {
                Text(
                    text = strings.userRoleInfoBody,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                TextButton(onClick = { showUserModeInfoDialog = false }) {
                    Text(strings.close)
                }
            },
            modifier = Modifier.testTag("user_mode_info_dialog")
        )
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
    )
}

@Composable
private fun SettingsMenuCard(
    icon: ImageVector,
    iconTint: Color,
    iconBg: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingAccessory: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val isDark = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    val containerTint = if (isDark) iconTint.copy(alpha = 0.22f) else iconBg
    val tintColor = if (isDark) iconTint.copy(alpha = 0.9f) else iconTint

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(horizontal = 16.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            if (leadingContent != null) {
                leadingContent()
            } else {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(containerTint),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = tintColor,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (trailingAccessory != null) {
                trailingAccessory()
            }
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun AboutAppBottomCard(
    userEmail: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.7f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ada_kasir_logo),
                    contentDescription = strings.appName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = strings.aboutSection,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${strings.appName} • ${strings.developerBy}",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = BrandBlue
                    )
                }
                Surface(
                    shape = RoundedCornerShape(50),
                    color = BrandBlue.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = "v1.0.0",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = BrandBlue,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = strings.aboutSection,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = strings.aboutDescription,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${strings.developerLabel}: ${strings.developerBy}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${strings.versionLabel} 1.0.0",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (userEmail.isNotBlank()) {
                Text(
                    text = "$userEmail • ${strings.syncedWithGoogle}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = BrandBlue.copy(alpha = 0.10f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            tint = BrandBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = strings.aboutOpenHint,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = BrandBlue
                        )
                    }
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = null,
                        tint = BrandBlue,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PrinterSettingsDialog(
    store: StoreSettings,
    localPrefs: LocalAppPrefs,
    onDismiss: () -> Unit,
    onSelectPrinter: (String, String) -> Unit,
    onClearPrinter: () -> Unit,
    onSetPaperWidth: (Int) -> Unit
) {
    val context = LocalContext.current
    val strings = LocalAppStrings.current
    val scope = rememberCoroutineScope()

    var printers by remember { mutableStateOf<List<PairedPrinter>>(emptyList()) }
    var isTestingPrint by remember { mutableStateOf(false) }
    var hasBtPermission by remember { mutableStateOf(true) }

    fun loadPrinters() {
        val granted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
        hasBtPermission = granted
        printers = if (granted) ReceiptHelper.getPairedBluetoothPrinters(context) else emptyList()
    }

    val btPermLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { grants ->
        hasBtPermission = grants.values.all { it }
        if (hasBtPermission) {
            printers = ReceiptHelper.getPairedBluetoothPrinters(context)
        } else {
            Toast.makeText(context, strings.bluetoothPermissionNeeded, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        loadPrinters()
    }

    val paperWidth = if (localPrefs.printerPaperWidthMm == 80) PaperWidth.MM_80 else PaperWidth.MM_58

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp)
                .testTag("printer_settings_dialog")
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
                    Text(strings.printerSettingsTitle, style = MaterialTheme.typography.titleLarge)
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Rounded.Close, contentDescription = strings.close)
                    }
                }

                Text(strings.defaultPaperWidthLabel, style = MaterialTheme.typography.labelLarge)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(
                        selected = paperWidth == PaperWidth.MM_58,
                        onClick = { onSetPaperWidth(58) },
                        label = { Text(strings.printerWidth58) }
                    )
                    FilterChip(
                        selected = paperWidth == PaperWidth.MM_80,
                        onClick = { onSetPaperWidth(80) },
                        label = { Text(strings.printerWidth80) }
                    )
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.45f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = strings.selectedPrinterLabel,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        if (localPrefs.defaultPrinterAddress.isBlank()) {
                            Text(
                                text = strings.noPrinterSelected,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            Text(
                                text = "${localPrefs.defaultPrinterName} (${localPrefs.defaultPrinterAddress})",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                FilledTonalButton(
                                    onClick = {
                                        isTestingPrint = true
                                        scope.launch {
                                            val res = ReceiptHelper.printTestReceipt(
                                                context = context,
                                                printerAddress = localPrefs.defaultPrinterAddress,
                                                store = store,
                                                paperWidth = paperWidth,
                                                strings = strings
                                            )
                                            isTestingPrint = false
                                            val msg = if (res.isSuccess) strings.printSuccessMsg else "${strings.printFailedMsg}: ${res.exceptionOrNull()?.localizedMessage}"
                                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                                        }
                                    },
                                    enabled = !isTestingPrint,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(strings.testPrintButton)
                                }
                                OutlinedButton(
                                    onClick = onClearPrinter,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(strings.disconnectPrinterButton)
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(strings.connectedPrintersTitle, style = MaterialTheme.typography.titleSmall)
                    TextButton(
                        onClick = {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                val needConnect = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED
                                val needScan = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
                                if (needConnect || needScan) {
                                    btPermLauncher.launch(
                                        arrayOf(
                                            Manifest.permission.BLUETOOTH_CONNECT,
                                            Manifest.permission.BLUETOOTH_SCAN
                                        )
                                    )
                                    return@TextButton
                                }
                            }
                            loadPrinters()
                        },
                        modifier = Modifier.testTag("refresh_printers_button")
                    ) {
                        Icon(Icons.Rounded.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(strings.refreshPrintersButton)
                    }
                }

                if (!hasBtPermission && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    OutlinedButton(
                        onClick = {
                            btPermLauncher.launch(
                                arrayOf(
                                    Manifest.permission.BLUETOOTH_CONNECT,
                                    Manifest.permission.BLUETOOTH_SCAN
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Rounded.Bluetooth, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text(strings.bluetoothPermissionNeeded)
                    }
                } else if (printers.isEmpty()) {
                    Text(
                        text = strings.noPairedPrinters,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        printers.forEach { printer ->
                            val isSelected = printer.address == localPrefs.defaultPrinterAddress
                            Surface(
                                shape = RoundedCornerShape(14.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
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
                                        imageVector = Icons.Rounded.Bluetooth,
                                        contentDescription = null,
                                        tint = if (printer.isConnected || isSelected) StatusSuccess else BrandBlue
                                    )
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = printer.name,
                                            style = MaterialTheme.typography.titleSmall,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = printer.address,
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                            Surface(
                                                shape = RoundedCornerShape(50),
                                                color = if (printer.isConnected) StatusSuccess.copy(alpha = 0.14f) else BrandBlue.copy(alpha = 0.12f)
                                            ) {
                                                Text(
                                                    text = if (printer.isConnected) strings.printerConnectedBadge else strings.printerPairedBadge,
                                                    style = MaterialTheme.typography.labelSmall,
                                                    color = if (printer.isConnected) StatusSuccess else BrandBlue,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                            if (isSelected) {
                                                Surface(
                                                    shape = RoundedCornerShape(50),
                                                    color = BrandGreen.copy(alpha = 0.16f)
                                                ) {
                                                    Text(
                                                        text = strings.printerSelectedBadge,
                                                        style = MaterialTheme.typography.labelSmall,
                                                        color = BrandGreen,
                                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    FilledTonalButton(
                                        onClick = { onSelectPrinter(printer.name, printer.address) }
                                    ) {
                                        Text(if (isSelected) strings.printerSelectedBadge else strings.selectPrinterButton)
                                    }
                                }
                            }
                        }
                    }
                }

                OutlinedButton(
                    onClick = {
                        try {
                            context.startActivity(Intent(Settings.ACTION_BLUETOOTH_SETTINGS))
                        } catch (_: Exception) {
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Rounded.Bluetooth, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(strings.openBluetoothSettingsButton)
                }
            }
        }
    }
}
