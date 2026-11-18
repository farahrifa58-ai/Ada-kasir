package com.example.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.data.model.AppCurrencyCatalog
import com.example.data.model.AppLanguageCatalog
import com.example.data.model.PosCurrencyOption

@Immutable
interface AppStrings {
    val appName: String
    val developerBy: String

    // Auth
    val loginTitle: String
    val loginSubtitle: String
    val loginButton: String
    val loginSyncNote: String
    val loginCancelled: String
    val loginSavedSessionNote: String
    val loginErrorFallback: String
    val loginSigningIn: String

    // Main Menus
    val menuTitle: String
    val menuDashboard: String
    val menuCashier: String
    val menuProducts: String
    val menuSalesReport: String
    val menuExpenses: String
    val menuHistory: String
    val menuSettings: String

    // Greetings & Common
    val greetingMorning: String
    val greetingAfternoon: String
    val greetingEvening: String
    val greetingNight: String
    val searchPlaceholder: String
    val cancel: String
    val confirm: String
    val save: String
    val delete: String
    val edit: String
    val close: String
    val all: String
    val loading: String
    val retry: String

    // Dashboard
    val todaySales: String
    val todayTransactions: String
    val salesTrendTitle: String
    val salesTrendSubtitle: String
    val periodToday: String
    val period7Days: String
    val period30Days: String
    val periodThisMonth: String
    val topProductsTitle: String
    val soldLabel: String
    val noSalesDataYet: String
    val recentTransactionsTitle: String
    val viewAllHistory: String
    val noTransactionsYet: String
    val stockAlertsTitle: String
    val allStockHealthy: String
    val lowStockBadge: String
    val outOfStockBadge: String

    // Cashier
    val searchProductHint: String
    val allProducts: String
    val cartTitle: String
    val emptyCartHint: String
    val addToCart: String
    val selectQuantityTitle: String
    val quickAmountLabel: String
    val manualDecimalLabel: String
    val estimatedSubtotal: String
    val subtotalLabel: String
    val customerNameLabel: String
    val customerNameHint: String
    val discountLabel: String
    val discountNone: String
    val discountPercent: String
    val discountAmount: String
    val taxLabel: String
    val taxNone: String
    val taxAuto: String
    val taxManual: String
    val paymentMethodLabel: String
    val methodCash: String
    val methodBankTransfer: String
    val methodQris: String
    val methodOther: String
    val amountReceivedLabel: String
    val exactCashButton: String
    val changeLabel: String
    val grandTotalLabel: String
    val completeTransactionButton: String
    val insufficientStockError: String
    val insufficientPaymentError: String
    val transactionSuccessMsg: String
    val viewCartButton: String

    // Receipt & Print
    val receiptTitle: String
    val receiptNumber: String
    val receiptDate: String
    val receiptCustomer: String
    val receiptCashierNote: String
    val printReceipt: String
    val savePdf: String
    val shareWhatsApp: String
    val printerWidth58: String
    val printerWidth80: String
    val selectPrinterTitle: String
    val noPairedPrinters: String
    val printSuccessMsg: String
    val printFailedMsg: String
    val pdfSavedMsg: String
    val bluetoothPermissionNeeded: String
    val systemPrintFallback: String

    // Products
    val addProduct: String
    val editProduct: String
    val productNameLabel: String
    val productSkuLabel: String
    val productCategoryLabel: String
    val productUnitLabel: String
    val productCostPriceLabel: String
    val productSellPriceLabel: String
    val productStockLabel: String
    val productMinStockLabel: String
    val productDescLabel: String
    val productPhotoLabel: String
    val pickFromGallery: String
    val takePhoto: String
    val manageCategories: String
    val addCategory: String
    val editCategory: String
    val categoryNameLabel: String
    val noProductsFound: String
    val deleteProductConfirm: String
    val stockWarningLeft: String

    // History
    val searchTrxHint: String
    val filterDateLabel: String
    val filterMethodLabel: String
    val trxDetailTitle: String
    val purchasedItemsLabel: String
    val reprintReceipt: String
    val shareReceipt: String
    val cancelTransaction: String
    val refundTransaction: String
    val cancelConfirmTitle: String
    val refundConfirmTitle: String
    val cancelRefundWarning: String
    val reasonOptionalLabel: String
    val statusCompleted: String
    val statusCancelled: String
    val statusRefunded: String

    // Settings
    val storeProfileSection: String
    val storeNameLabel: String
    val ownerNameLabel: String
    val phoneLabel: String
    val addressLabel: String
    val defaultTaxPercentLabel: String
    val saveChanges: String
    val profileSavedMsg: String
    val themeSection: String
    val lightTheme: String
    val darkTheme: String
    val languageSection: String
    val languageIndonesian: String
    val languageEnglish: String
    val backupRestoreSection: String
    val manualBackupButton: String
    val restoreBackupButton: String
    val lastBackupLabel: String
    val neverBackedUp: String
    val backupInProgress: String
    val backupSuccessMsg: String
    val restoreConfirmTitle: String
    val restoreConfirmBody: String
    val restoreSuccessMsg: String
    val restoreInvalidMsg: String
    val aboutSection: String
    val versionLabel: String
    val aboutDescription: String
    val copyrightText: String
    val googleAccountSection: String
    val syncedWithGoogle: String
    val signOutButton: String

    // Sales Report
    val reportFilterDate: String
    val reportFilterMonth: String
    val reportFilterYear: String
    val reportTotalSales: String
    val reportOmset: String
    val reportHpp: String
    val reportGrossProfit: String
    val reportExpensesSummary: String
    val reportNetProfit: String
    val reportPaymentBreakdown: String
    val reportSelectDate: String

    // Expenses
    val addExpense: String
    val editExpense: String
    val expenseTitleLabel: String
    val expenseCategoryLabel: String
    val expenseAmountLabel: String
    val expenseNotesLabel: String
    val expenseDateLabel: String
    val todayExpensesLabel: String
    val monthExpensesLabel: String
    val noExpensesYet: String
    val deleteExpenseConfirm: String
    val expenseSavedMsg: String

    // Settings Grouped Sections & Printer Management
    val settingsConfigSection: String
    val settingsHardwareSection: String
    val settingsDataSection: String
    val settingsAccountSection: String
    val storeProfileSubtitle: String
    val appearanceLanguageTitle: String
    val appearanceLanguageSubtitle: String
    val printerSettingsTitle: String
    val printerSettingsSubtitle: String
    val backupRestoreSubtitle: String
    val connectedPrintersTitle: String
    val selectedPrinterLabel: String
    val noPrinterSelected: String
    val defaultPaperWidthLabel: String
    val refreshPrintersButton: String
    val testPrintButton: String
    val selectPrinterButton: String
    val disconnectPrinterButton: String
    val printerConnectedBadge: String
    val printerPairedBadge: String
    val printerSelectedBadge: String
    val openBluetoothSettingsButton: String

    // Splash
    val splashByDeveloper: String

    // Receipt Customization
    val receiptCashierLabel: String
    val receiptCustomTitle: String
    val receiptCustomSubtitle: String
    val receiptPreviewLabel: String
    val receiptShowLogoLabel: String
    val receiptShowCashierLabel: String
    val receiptShowPhoneLabel: String
    val receiptShowAddressLabel: String
    val receiptShowCustomerLabel: String
    val receiptFooterMessageLabel: String
    val receiptFooterMessageHint: String
    val receiptCustomSavedMsg: String

    // User Management / Monitor Mode
    val userManageTitle: String
    val userManageSubtitle: String
    val userRoleInfoTitle: String
    val userRoleInfoBody: String
    val userModeCashierLabel: String
    val userModeMonitorLabel: String
    val userModeCashierDesc: String
    val userModeMonitorDesc: String
    val monitorEmailListLabel: String
    val monitorEmailInputHint: String
    val addMonitorEmailButton: String
    val noMonitorEmailsYet: String
    val monitorStoresAvailableLabel: String
    val monitorNoStoresLinked: String
    val connectStoreButton: String
    val connectedBadge: String
    val monitorBannerTitle: String
    val monitorReadOnlyError: String

    // Store Icon & Background Color
    val storeAvatarPhotoTab: String
    val storeAvatarIconTab: String
    val storeIconPickerLabel: String
    val storeIconColorLabel: String
    val changeStorePhotoButton: String
    val removeStorePhotoButton: String

    // PPN & Discount Settings
    val taxDiscountTitle: String
    val taxDiscountSubtitle: String
    val defaultTaxModeLabel: String
    val defaultManualTaxAmountLabel: String
    val defaultDiscountModeLabel: String
    val defaultDiscountValueLabel: String
    val taxDiscountSavedMsg: String

    // Receipt Prefix
    val receiptPrefixLabel: String
    val receiptPrefixHint: String

    // Owner vs Cashier Roles, Feature Permissions, PIN & Monitor Limit
    val staffRoleSectionTitle: String
    val roleOwnerLabel: String
    val roleOwnerDesc: String
    val roleCashierLabel: String
    val roleCashierDesc: String
    val cashierPermissionsTitle: String
    val presetCashierTxOnly: String
    val presetCashierAllFeatures: String
    val permAllowProducts: String
    val permAllowSalesReport: String
    val permAllowExpenses: String
    val permAllowHistory: String
    val permAllowCancelRefund: String
    val permAllowSettings: String
    val ownerPinSectionTitle: String
    val ownerPinLabel: String
    val ownerPinHint: String
    val savePermissionsButton: String
    val permissionsSavedMsg: String
    val enterPinTitle: String
    val enterPinSubtitle: String
    val wrongPinError: String
    val switchToOwnerButton: String
    val monitorLimitNote: String
    val monitorLimitReachedMsg: String

    // Tutorial & Cashier Polish
    val settingsGuideSection: String
    val tutorialTitle: String
    val tutorialSubtitle: String
    val cartItemCountLabel: String
    val clearCartButton: String
    val billSummaryTitle: String

    // Operating Hours & Cashier Reference UI
    val storeOperatingHoursLabel: String
    val storeOperatingHoursHint: String
    val showOperatingHoursOnReceiptLabel: String
    val receiptShowOperatingHoursLabel: String
    val addShortButton: String
    val chooseUnitPrefix: String
    val productsInCartLabel: String
    val quickUnitDialogTitle: String
    val quickSelectUnitPrefix: String
    val manualWeightLabel: String
    val estimatedPriceLabel: String
    val addConfirmButton: String
    val stockLabelPrefix: String
    val unlimitedStockLabel: String

    // Regions, Multi-Language & Multi-Currency
    val regionSoutheastAsia: String
    val regionAsia: String
    val regionEurope: String
    val regionRussia: String
    val regionAmerica: String
    val regionAustralia: String
    val currencySectionTitle: String
    val currencySettingsSubtitle: String
    val currencyPickerTitle: String
    val currencySamplePreviewLabel: String
    val currencySavedMsg: String
    val languagePickerSubtitle: String
    val searchLanguageHint: String
    val searchCurrencyHint: String
    val aboutOpenHint: String
    val developerLabel: String

    // Comprehensive Audit Completions
    val trxShortLabel: String
    val transactionsPluralLabel: String
    val includingTaxPrefix: String
    val reportHppSubtitle: String
    val marginLabel: String
    val salesMinusCogsLabel: String
    val previousLabel: String
    val nextLabel: String
    val decreaseQtyLabel: String
    val increaseQtyLabel: String
    val monitorWord: String
    val prefixLabel: String
    val pinInputLabel: String
    val defaultStoreName: String
    val receiptDefaultCustomer: String
    val receiptSampleItem1: String
    val receiptSampleItem2: String
    val receiptDefaultHours: String
    val receiptDefaultAddress: String
    val receiptPhonePrefix: String
    val receiptFilePrefix: String
    val testPrintHeader: String
    val testPrintStatusReady: String
    val defaultCategoryOther: String
    val discountAmountWord: String
    val taxManualWord: String
    val defaultManualTaxWord: String
    val productCostPriceWord: String
    val productSellPriceWord: String
    val expenseAmountWord: String
    val storePhotoCd: String
    val businessIconCd: String
    val supermarketBgCd: String

    // Error Fallbacks
    val errProductNameEmpty: String
    val errCategoryNameEmpty: String
    val errStoreNameEmpty: String
    val errEmailEmpty: String
    val errMonitorSelfEmail: String
    val errExpensesInvalid: String
    val errInvalidPin: String
    val errNoMonitorTarget: String
    val errConnectStoreFailed: String
    val errCartEmpty: String
    val errSaveFailed: String

    // Expense Categories
    val expCatOperational: String
    val expCatStockPurchase: String
    val expCatUtilities: String
    val expCatSalary: String
    val expCatTransport: String
    val expCatOther: String

    // Business Icon Options
    val bizIconStorefront: String
    val bizIconCafe: String
    val bizIconRestaurant: String
    val bizIconFastFood: String
    val bizIconGrocery: String
    val bizIconBakery: String
    val bizIconPharmacy: String
    val bizIconFashion: String
    val bizIconElectronics: String
    val bizIconLaundry: String
    val bizIconService: String

    // Tutorial Steps
    val tutorialStep1Title: String
    val tutorialStep1Body: String
    val tutorialStep2Title: String
    val tutorialStep2Body: String
    val tutorialStep3Title: String
    val tutorialStep3Body: String
    val tutorialStep4Title: String
    val tutorialStep4Body: String
    val tutorialStep5Title: String
    val tutorialStep5Body: String
    val tutorialStep6Title: String
    val tutorialStep6Body: String
}

object IndonesianStrings : AppStrings {
    override val appName = "ADA KASIR"
    override val developerBy = "Rafline Dev"
    override val loginTitle = "ADA KASIR"
    override val loginSubtitle = "Aplikasi Point of Sale modern dengan sinkronisasi cloud otomatis di seluruh perangkat Anda."
    override val loginButton = "Masuk dengan Google"
    override val loginSyncNote = "Cukup login dengan satu akun Google. Semua data toko, produk, stok, dan transaksi tersinkronisasi otomatis."
    override val loginCancelled = "Login Google dibatalkan. Silakan pilih akun untuk melanjutkan."
    override val loginSavedSessionNote = "Sesi akun tersimpan (%s). Tekan Masuk dengan Google untuk membuka Dashboard."
    override val loginErrorFallback = "Gagal masuk dengan Google. Periksa koneksi atau akun Google Anda, lalu coba lagi."
    override val loginSigningIn = "Memproses masuk..."
    override val menuTitle = "Menu Utama"
    override val menuDashboard = "Dashboard"
    override val menuCashier = "Kasir"
    override val menuProducts = "Produk"
    override val menuSalesReport = "Laporan Penjualan"
    override val menuExpenses = "Pengeluaran"
    override val menuHistory = "Riwayat"
    override val menuSettings = "Pengaturan"
    override val greetingMorning = "Selamat Pagi"
    override val greetingAfternoon = "Selamat Siang"
    override val greetingEvening = "Selamat Sore"
    override val greetingNight = "Selamat Malam"
    override val searchPlaceholder = "Cari..."
    override val cancel = "Batal"
    override val confirm = "Konfirmasi"
    override val save = "Simpan"
    override val delete = "Hapus"
    override val edit = "Ubah"
    override val close = "Tutup"
    override val all = "Semua"
    override val loading = "Memuat..."
    override val retry = "Coba Lagi"
    override val todaySales = "Total Penjualan Hari Ini"
    override val todayTransactions = "Jumlah Transaksi Hari Ini"
    override val salesTrendTitle = "Grafik Tren Penjualan"
    override val salesTrendSubtitle = "Aktivitas Kasir & Jam Sibuk"
    override val periodToday = "Hari ini"
    override val period7Days = "7 hari"
    override val period30Days = "30 hari"
    override val periodThisMonth = "Bulan ini"
    override val topProductsTitle = "Produk Terlaris"
    override val soldLabel = "terjual"
    override val noSalesDataYet = "Belum ada transaksi selesai pada periode ini."
    override val recentTransactionsTitle = "Transaksi Terbaru"
    override val viewAllHistory = "Lihat Semua Riwayat"
    override val noTransactionsYet = "Belum ada transaksi tercatat."
    override val stockAlertsTitle = "Notifikasi Stok"
    override val allStockHealthy = "Semua stok produk dalam kondisi aman."
    override val lowStockBadge = "Stok Menipis"
    override val outOfStockBadge = "Habis"
    override val searchProductHint = "Cari nama atau kode SKU produk..."
    override val allProducts = "Semua produk"
    override val cartTitle = "Keranjang"
    override val emptyCartHint = "Ketuk produk untuk menambahkan ke keranjang"
    override val addToCart = "Tambah ke Keranjang"
    override val selectQuantityTitle = "Jumlah Pembelian"
    override val quickAmountLabel = "Pilihan Cepat"
    override val manualDecimalLabel = "Input Manual (misal: 0.75, 1.25)"
    override val estimatedSubtotal = "Subtotal Barang"
    override val subtotalLabel = "Subtotal"
    override val customerNameLabel = "Nama Pelanggan (Opsional)"
    override val customerNameHint = "Boleh dikosongkan"
    override val discountLabel = "Diskon"
    override val discountNone = "Tanpa Diskon"
    override val discountPercent = "Persentase (%)"
    override val discountAmount = "Nominal (Rp)"
    override val taxLabel = "PPN"
    override val taxNone = "Tanpa PPN"
    override val taxAuto = "PPN Otomatis"
    override val taxManual = "PPN Manual (Rp)"
    override val paymentMethodLabel = "Metode Pembayaran"
    override val methodCash = "Tunai"
    override val methodBankTransfer = "Transfer Bank"
    override val methodQris = "QRIS"
    override val methodOther = "Metode Lainnya"
    override val amountReceivedLabel = "Uang Diterima"
    override val exactCashButton = "Uang Pas"
    override val changeLabel = "Kembalian"
    override val grandTotalLabel = "Total Bayar"
    override val completeTransactionButton = "Selesaikan Transaksi"
    override val insufficientStockError = "Stok produk tidak mencukupi."
    override val insufficientPaymentError = "Uang diterima kurang dari total pembayaran."
    override val transactionSuccessMsg = "Transaksi berhasil disimpan!"
    override val viewCartButton = "Lihat Keranjang"
    override val receiptTitle = "Struk Transaksi"
    override val receiptNumber = "No. Transaksi"
    override val receiptDate = "Waktu"
    override val receiptCustomer = "Pelanggan"
    override val receiptCashierNote = "Terima kasih telah berbelanja!"
    override val printReceipt = "Cetak Struk"
    override val savePdf = "Simpan PDF"
    override val shareWhatsApp = "Bagikan WA"
    override val printerWidth58 = "Thermal 58 mm"
    override val printerWidth80 = "Thermal 80 mm"
    override val selectPrinterTitle = "Pilih Printer Bluetooth"
    override val noPairedPrinters = "Tidak ada printer Bluetooth terpasang. Gunakan cetak sistem PDF atau pasangkan printer di pengaturan Bluetooth HP."
    override val printSuccessMsg = "Struk berhasil dikirim ke printer."
    override val printFailedMsg = "Gagal mencetak ke printer Bluetooth."
    override val pdfSavedMsg = "Struk PDF berhasil disimpan."
    override val bluetoothPermissionNeeded = "Izin Bluetooth diperlukan untuk mencetak ke printer thermal."
    override val systemPrintFallback = "Cetak via Sistem / PDF"
    override val addProduct = "Tambah Produk"
    override val editProduct = "Edit Produk"
    override val productNameLabel = "Nama Produk"
    override val productSkuLabel = "Kode Produk / SKU"
    override val productCategoryLabel = "Kategori"
    override val productUnitLabel = "Satuan"
    override val productCostPriceLabel = "Harga Modal (Rp)"
    override val productSellPriceLabel = "Harga Jual (Rp)"
    override val productStockLabel = "Jumlah Stok"
    override val productMinStockLabel = "Stok Minimum (Opsional)"
    override val productDescLabel = "Deskripsi Produk (Opsional)"
    override val productPhotoLabel = "Foto Produk"
    override val pickFromGallery = "Pilih dari Galeri"
    override val takePhoto = "Ambil Foto Kamera"
    override val manageCategories = "Kelola Kategori"
    override val addCategory = "Tambah Kategori"
    override val editCategory = "Ubah Kategori"
    override val categoryNameLabel = "Nama Kategori"
    override val noProductsFound = "Belum ada produk yang sesuai."
    override val deleteProductConfirm = "Hapus produk ini dari daftar?"
    override val stockWarningLeft = "Sisa stok"
    override val searchTrxHint = "Cari nomor transaksi atau pelanggan..."
    override val filterDateLabel = "Periode Tanggal"
    override val filterMethodLabel = "Metode Bayar"
    override val trxDetailTitle = "Detail Transaksi"
    override val purchasedItemsLabel = "Daftar Produk Dibeli"
    override val reprintReceipt = "Cetak Ulang Struk"
    override val shareReceipt = "Bagikan Struk"
    override val cancelTransaction = "Batalkan Transaksi"
    override val refundTransaction = "Pengembalian (Refund)"
    override val cancelConfirmTitle = "Batalkan Transaksi?"
    override val refundConfirmTitle = "Proses Pengembalian Barang?"
    override val cancelRefundWarning = "Status transaksi akan diperbarui dan stok produk akan dikembalikan secara otomatis."
    override val reasonOptionalLabel = "Alasan (Opsional)"
    override val statusCompleted = "Selesai"
    override val statusCancelled = "Dibatalkan"
    override val statusRefunded = "Dikembalikan"
    override val storeProfileSection = "Profil Toko"
    override val storeNameLabel = "Nama Toko"
    override val ownerNameLabel = "Nama Pemilik"
    override val phoneLabel = "Nomor HP"
    override val addressLabel = "Alamat Toko"
    override val defaultTaxPercentLabel = "Tarif PPN Otomatis (%)"
    override val saveChanges = "Simpan Perubahan"
    override val profileSavedMsg = "Profil toko berhasil disimpan dan disinkronkan."
    override val themeSection = "Tema Aplikasi"
    override val lightTheme = "Tema Terang"
    override val darkTheme = "Tema Gelap"
    override val languageSection = "Bahasa"
    override val languageIndonesian = "Bahasa Indonesia"
    override val languageEnglish = "English"
    override val backupRestoreSection = "Pencadangan dan Pemulihan Data"
    override val manualBackupButton = "Backup Manual"
    override val restoreBackupButton = "Restore Data"
    override val lastBackupLabel = "Backup Terakhir"
    override val neverBackedUp = "Belum pernah dilakukan backup"
    override val backupInProgress = "Memproses cadangan data..."
    override val backupSuccessMsg = "Cadangan data berhasil dibuat dan disimpan."
    override val restoreConfirmTitle = "Konfirmasi Pemulihan Data"
    override val restoreConfirmBody = "Pemulihan akan memperbarui data produk, kategori, transaksi, dan profil toko Anda. Lanjutkan?"
    override val restoreSuccessMsg = "Data berhasil dipulihkan dan disinkronkan."
    override val restoreInvalidMsg = "File cadangan tidak valid atau rusak."
    override val aboutSection = "Tentang Aplikasi"
    override val versionLabel = "Versi Aplikasi"
    override val aboutDescription = "Aplikasi Point of Sale (POS) modern untuk mengelola transaksi kasir, produk, stok, dan riwayat toko dengan sinkronisasi real-time."
    override val copyrightText = "© 2026 Rafline Dev. Hak cipta dilindungi."
    override val googleAccountSection = "Akun Google & Sinkronisasi"
    override val syncedWithGoogle = "Tersinkronisasi otomatis dengan akun Google"
    override val signOutButton = "Keluar Akun"
    override val reportFilterDate = "Tanggal"
    override val reportFilterMonth = "Bulan"
    override val reportFilterYear = "Tahun"
    override val reportTotalSales = "Total Penjualan"
    override val reportOmset = "Omset"
    override val reportHpp = "HPP / Modal"
    override val reportGrossProfit = "Laba Kotor"
    override val reportExpensesSummary = "Pengeluaran Periode Ini"
    override val reportNetProfit = "Laba Bersih Estimasi"
    override val reportPaymentBreakdown = "Rincian Metode Pembayaran"
    override val reportSelectDate = "Pilih Tanggal"
    override val addExpense = "Tambah Pengeluaran"
    override val editExpense = "Edit Pengeluaran"
    override val expenseTitleLabel = "Nama / Keterangan Pengeluaran"
    override val expenseCategoryLabel = "Kategori Pengeluaran"
    override val expenseAmountLabel = "Nominal (Rp)"
    override val expenseNotesLabel = "Catatan (Opsional)"
    override val expenseDateLabel = "Tanggal Pengeluaran"
    override val todayExpensesLabel = "Pengeluaran Hari Ini"
    override val monthExpensesLabel = "Pengeluaran Bulan Ini"
    override val noExpensesYet = "Belum ada catatan pengeluaran."
    override val deleteExpenseConfirm = "Hapus catatan pengeluaran ini?"
    override val expenseSavedMsg = "Pengeluaran berhasil disimpan."
    override val settingsConfigSection = "Konfigurasi Toko & POS"
    override val settingsHardwareSection = "Hardware & Struk"
    override val settingsDataSection = "Data & Cadangan"
    override val settingsAccountSection = "Akun & Aplikasi"
    override val storeProfileSubtitle = "Nama toko, alamat, nomor HP, foto, & PPN"
    override val appearanceLanguageTitle = "Tampilan & Bahasa"
    override val appearanceLanguageSubtitle = "Tema aplikasi dan bahasa tampilan"
    override val printerSettingsTitle = "Printer Kasir"
    override val printerSettingsSubtitle = "Kelola printer Bluetooth & daftar tersambung"
    override val backupRestoreSubtitle = "Backup JSON manual & pulihkan data toko"
    override val connectedPrintersTitle = "Daftar Printer Tersambung"
    override val selectedPrinterLabel = "Printer Kasir Aktif"
    override val noPrinterSelected = "Belum ada printer default terpilih"
    override val defaultPaperWidthLabel = "Ukuran Kertas Struk Default"
    override val refreshPrintersButton = "Refresh Daftar"
    override val testPrintButton = "Tes Cetak"
    override val selectPrinterButton = "Pilih"
    override val disconnectPrinterButton = "Putuskan"
    override val printerConnectedBadge = "Tersambung"
    override val printerPairedBadge = "Terpasang"
    override val printerSelectedBadge = "Aktif"
    override val openBluetoothSettingsButton = "Buka Pengaturan Bluetooth HP"
    override val splashByDeveloper = "by Rafline Dev"
    override val receiptCashierLabel = "Kasir"
    override val receiptCustomTitle = "Kustomisasi Struk"
    override val receiptCustomSubtitle = "Logo toko, nama kasir, HP, alamat & footer"
    override val receiptPreviewLabel = "Pratinjau Gambar Struk"
    override val receiptShowLogoLabel = "Tampilkan Logo Toko"
    override val receiptShowCashierLabel = "Tampilkan Nama Kasir"
    override val receiptShowPhoneLabel = "Tampilkan Nomor HP Toko"
    override val receiptShowAddressLabel = "Tampilkan Alamat Toko"
    override val receiptShowCustomerLabel = "Tampilkan Nama Pelanggan"
    override val receiptFooterMessageLabel = "Pesan Footer Struk"
    override val receiptFooterMessageHint = "Contoh: Terima kasih, selamat belanja kembali!"
    override val receiptCustomSavedMsg = "Kustomisasi struk berhasil disimpan."
    override val userManageTitle = "Kelola Pengguna"
    override val userManageSubtitle = "Mode Kasir & Mode Pemantau multi-email"
    override val userRoleInfoTitle = "Informasi Mode Kasir & Pemantau"
    override val userRoleInfoBody = "• Mode Kasir: Akses penuh untuk mencatat transaksi, mengelola produk, stok, pengeluaran, laporan, dan pengaturan toko.\n\n• Mode Pemantau (Owner): Digunakan pemilik toko untuk memantau omset, laba, grafik penjualan, stok, dan riwayat secara real-time (hanya-baca) menggunakan email Google yang berbeda.\n\n• Cara menautkan: Pada akun Kasir, masukkan email Google Owner ke daftar Email Pemantau. Lalu login di HP Owner dengan email tersebut dan pilih Mode Pemantau."
    override val userModeCashierLabel = "Mode Kasir"
    override val userModeMonitorLabel = "Mode Pemantau"
    override val userModeCashierDesc = "Akses kasir penuh (kelola produk & transaksi)"
    override val userModeMonitorDesc = "Pantau penjualan toko lain secara real-time (hanya-baca)"
    override val monitorEmailListLabel = "Email Pemantau Terdaftar (Untuk Owner)"
    override val monitorEmailInputHint = "Contoh: owner@gmail.com"
    override val addMonitorEmailButton = "Tambah Email"
    override val noMonitorEmailsYet = "Belum ada email pemantau yang ditautkan."
    override val monitorStoresAvailableLabel = "Toko yang Dapat Dipantau"
    override val monitorNoStoresLinked = "Belum ada toko Kasir yang menautkan email Google ini (%s)."
    override val connectStoreButton = "Pantau Toko"
    override val connectedBadge = "Terhubung"
    override val monitorBannerTitle = "Mode Pemantau (Hanya Baca)"
    override val monitorReadOnlyError = "Mode Pemantau bersifat hanya-baca. Ubah ke Mode Kasir untuk mengedit data."
    override val storeAvatarPhotoTab = "Foto Toko"
    override val storeAvatarIconTab = "Ikon Usaha"
    override val storeIconPickerLabel = "Pilih Ikon Usaha"
    override val storeIconColorLabel = "Warna Background Ikon"
    override val changeStorePhotoButton = "Upload / Ubah Foto"
    override val removeStorePhotoButton = "Pakai Ikon Usaha"
    override val taxDiscountTitle = "PPN & Diskon"
    override val taxDiscountSubtitle = "Edit PPN otomatis, PPN manual, & diskon default"
    override val defaultTaxModeLabel = "Mode PPN Default Kasir"
    override val defaultManualTaxAmountLabel = "Nominal PPN Manual Default (Rp)"
    override val defaultDiscountModeLabel = "Mode Diskon Default Kasir"
    override val defaultDiscountValueLabel = "Nilai Diskon Default"
    override val taxDiscountSavedMsg = "Pengaturan PPN & diskon berhasil disimpan."
    override val receiptPrefixLabel = "Prefix Nomor Nota / Struk"
    override val receiptPrefixHint = "Contoh: AK, TOKO, INV"
    override val staffRoleSectionTitle = "Hak Akses & Peran Perangkat Ini"
    override val roleOwnerLabel = "Owner"
    override val roleOwnerDesc = "Akses penuh ke semua fitur toko, laporan, produk, pengeluaran, & pengaturan."
    override val roleCashierLabel = "Kasir / Karyawan"
    override val roleCashierDesc = "Default hanya transaksi kasir. Fitur tambahan bisa diaktifkan oleh Owner."
    override val cashierPermissionsTitle = "Kustomisasi Fitur Kasir / Karyawan"
    override val presetCashierTxOnly = "Hanya Transaksi"
    override val presetCashierAllFeatures = "Semua Fitur"
    override val permAllowProducts = "Kelola Produk & Stok"
    override val permAllowSalesReport = "Lihat Laporan Penjualan"
    override val permAllowExpenses = "Kelola Pengeluaran"
    override val permAllowHistory = "Lihat Riwayat Transaksi"
    override val permAllowCancelRefund = "Batal / Refund Transaksi"
    override val permAllowSettings = "Buka Menu Pengaturan"
    override val ownerPinSectionTitle = "PIN Keamanan Owner (4-6 Angka)"
    override val ownerPinLabel = "PIN Keamanan (Opsional)"
    override val ownerPinHint = "Diminta saat beralih dari mode Kasir kembali ke Owner"
    override val savePermissionsButton = "Simpan Hak Akses & PIN"
    override val permissionsSavedMsg = "Pengaturan pengguna & PIN keamanan disimpan."
    override val enterPinTitle = "Masukkan PIN Owner"
    override val enterPinSubtitle = "Masukkan PIN keamanan untuk membuka seluruh fitur Owner."
    override val wrongPinError = "PIN keamanan salah. Silakan coba lagi."
    override val switchToOwnerButton = "Mode Owner"
    override val monitorLimitNote = "Sambungkan 1 sampai 2 akun Google untuk pantau omset jarak jauh real-time."
    override val monitorLimitReachedMsg = "Maksimal 2 akun pemantau sudah tersambung."
    override val settingsGuideSection = "Panduan & Tentang Aplikasi"
    override val tutorialTitle = "Tutorial Penggunaan Singkat"
    override val tutorialSubtitle = "Cara memakai aplikasi, kasir, cetak struk & pantau jarak jauh"
    override val cartItemCountLabel = "Item"
    override val clearCartButton = "Kosongkan"
    override val billSummaryTitle = "Ringkasan Tagihan"
    override val storeOperatingHoursLabel = "Jam Operasional (Opsional)"
    override val storeOperatingHoursHint = "Contoh: Senin - Minggu 08.00 - 21.00"
    override val showOperatingHoursOnReceiptLabel = "Tampilkan Jam Operasional di Struk"
    override val receiptShowOperatingHoursLabel = "Tampilkan Jam Operasional"
    override val addShortButton = "Tambah"
    override val chooseUnitPrefix = "Pilih"
    override val productsInCartLabel = "Produk di Keranjang"
    override val quickUnitDialogTitle = "Pilih Berat / Jumlah"
    override val quickSelectUnitPrefix = "Pilihan Cepat"
    override val manualWeightLabel = "Atau Ketik Jumlah"
    override val estimatedPriceLabel = "Estimasi Harga"
    override val addConfirmButton = "Tambahkan"
    override val stockLabelPrefix = "Stok:"
    override val unlimitedStockLabel = "Unlimited"

    override val regionSoutheastAsia = "Asia Tenggara"
    override val regionAsia = "Asia"
    override val regionEurope = "Eropa"
    override val regionRussia = "Rusia"
    override val regionAmerica = "Amerika"
    override val regionAustralia = "Australia"
    override val currencySectionTitle = "Mata Uang"
    override val currencySettingsSubtitle = "Pilih mata uang dari berbagai negara"
    override val currencyPickerTitle = "Pilih Mata Uang Toko"
    override val currencySamplePreviewLabel = "Pratinjau format harga"
    override val currencySavedMsg = "Mata uang toko berhasil diubah."
    override val languagePickerSubtitle = "Pilih bahasa tampilan dari berbagai wilayah dunia"
    override val searchLanguageHint = "Cari bahasa atau negara..."
    override val searchCurrencyHint = "Cari mata uang, kode, atau negara..."
    override val aboutOpenHint = "Ketuk kartu untuk melihat info & detail aplikasi"
    override val developerLabel = "Pengembang"

    override val trxShortLabel = "Trx"
    override val transactionsPluralLabel = "Transaksi"
    override val includingTaxPrefix = "Termasuk PPN"
    override val reportHppSubtitle = "Harga pokok penjualan"
    override val marginLabel = "Margin"
    override val salesMinusCogsLabel = "Penjualan - HPP"
    override val previousLabel = "Sebelumnya"
    override val nextLabel = "Berikutnya"
    override val decreaseQtyLabel = "Kurangi"
    override val increaseQtyLabel = "Tambah"
    override val monitorWord = "Pemantau"
    override val prefixLabel = "Prefix"
    override val pinInputLabel = "PIN (4-6 digit)"
    override val defaultStoreName = "Toko ADA KASIR"
    override val receiptDefaultCustomer = "Budi Santoso"
    override val receiptSampleItem1 = "Beras Premium 5 Kg"
    override val receiptSampleItem2 = "Air Mineral"
    override val receiptDefaultHours = "Buka 08.00 - 21.00"
    override val receiptDefaultAddress = "Jl. Raya Utama No. 12"
    override val receiptPhonePrefix = "Telp"
    override val receiptFilePrefix = "Struk"
    override val testPrintHeader = "TES CETAK PRINTER"
    override val testPrintStatusReady = "Status: Terhubung & Siap"
    override val defaultCategoryOther = "Lainnya"
    override val discountAmountWord = "Nominal"
    override val taxManualWord = "PPN Manual"
    override val defaultManualTaxWord = "Nominal PPN Manual Default"
    override val productCostPriceWord = "Harga Modal"
    override val productSellPriceWord = "Harga Jual"
    override val expenseAmountWord = "Nominal"
    override val storePhotoCd = "Foto toko"
    override val businessIconCd = "Ikon usaha"
    override val supermarketBgCd = "Latar belakang toko"

    override val errProductNameEmpty = "Nama produk wajib diisi."
    override val errCategoryNameEmpty = "Nama kategori wajib diisi."
    override val errStoreNameEmpty = "Nama toko wajib diisi."
    override val errEmailEmpty = "Email wajib diisi."
    override val errMonitorSelfEmail = "Email pemantau tidak boleh sama dengan email akun Kasir."
    override val errExpensesInvalid = "Nama dan nominal pengeluaran harus valid."
    override val errInvalidPin = "PIN keamanan Owner harus 4-6 digit angka."
    override val errNoMonitorTarget = "Pilih toko terlebih dahulu untuk Mode Pemantau."
    override val errConnectStoreFailed = "Gagal terhubung ke toko pemantau."
    override val errCartEmpty = "Keranjang masih kosong."
    override val errSaveFailed = "Gagal menyimpan data."

    override val expCatOperational = "Operasional"
    override val expCatStockPurchase = "Belanja Stok"
    override val expCatUtilities = "Listrik & Air"
    override val expCatSalary = "Gaji & Bonus"
    override val expCatTransport = "Transportasi"
    override val expCatOther = "Lainnya"

    override val bizIconStorefront = "Toko / Retail"
    override val bizIconCafe = "Kafe / Kopi"
    override val bizIconRestaurant = "Restoran / Warung"
    override val bizIconFastFood = "Kuliner / Snack"
    override val bizIconGrocery = "Minimarket / Sembako"
    override val bizIconBakery = "Roti & Kue"
    override val bizIconPharmacy = "Apotek / Kesehatan"
    override val bizIconFashion = "Butik / Fashion"
    override val bizIconElectronics = "Elektronik / Gadget"
    override val bizIconLaundry = "Laundry"
    override val bizIconService = "Jasa / Bengkel"

    override val tutorialStep1Title = "1. Profil Usaha & Ikon Toko"
    override val tutorialStep1Body = "Atur nama toko, nomor HP, alamat, upload foto toko atau pilih ikon usaha & warna background di menu Pengaturan -> Profil Toko."
    override val tutorialStep2Title = "2. Kelola Produk & Stok"
    override val tutorialStep2Body = "Buka menu Produk untuk menambahkan barang, kategori, harga modal & jual, satuan (Pcs, Kg, Liter, dll), serta batas notifikasi stok menipis."
    override val tutorialStep3Title = "3. Cara Pakai Menu Kasir"
    override val tutorialStep3Body = "Ketuk produk untuk masuk ke keranjang (ketuk beberapa kali untuk menambah jumlah, angka pada ikon keranjang otomatis bertambah). Buka keranjang untuk atur diskon, PPN, pilih Tunai/QRIS/Transfer, lalu klik Selesaikan Transaksi."
    override val tutorialStep4Title = "4. Pengaturan PPN, Diskon & Struk"
    override val tutorialStep4Body = "Atur tarif PPN otomatis/manual & diskon default di kartu PPN & Diskon. Ubah prefix nomor nota, pesan footer, dan sambungkan printer Bluetooth 58mm/80mm di Printer Kasir."
    override val tutorialStep5Title = "5. Laporan, Pengeluaran & Riwayat"
    override val tutorialStep5Body = "Pantau omset, modal HPP, laba bersih, catat pengeluaran operasional, serta cetak ulang struk atau proses batal/refund di Riwayat."
    override val tutorialStep6Title = "6. Hak Akses Owner, Kasir, PIN & Pantau Jarak Jauh"
    override val tutorialStep6Body = "Di Kelola Pengguna, Owner punya akses penuh & bisa mengatur fitur apa saja yang boleh dibuka Kasir/Karyawan (default hanya transaksi), memasang PIN Keamanan, serta menautkan 1–2 akun Google untuk pantau toko jarak jauh."
}

object EnglishStrings : AppStrings {
    override val appName = "ADA KASIR"
    override val developerBy = "Rafline Dev"
    override val loginTitle = "ADA KASIR"
    override val loginSubtitle = "Modern Point of Sale application with automatic cloud sync across all your devices."
    override val loginButton = "Sign in with Google"
    override val loginSyncNote = "Just sign in with one Google account. Your store profile, products, stock, and transactions sync automatically."
    override val loginCancelled = "Google Sign-In cancelled. Please choose an account to continue."
    override val loginSavedSessionNote = "Saved session (%s). Tap Sign in with Google to open Dashboard."
    override val loginErrorFallback = "Google Sign-In failed. Please check your connection or Google account and try again."
    override val loginSigningIn = "Signing in..."
    override val menuTitle = "Main Menu"
    override val menuDashboard = "Dashboard"
    override val menuCashier = "Cashier"
    override val menuProducts = "Products"
    override val menuSalesReport = "Sales Report"
    override val menuExpenses = "Expenses"
    override val menuHistory = "History"
    override val menuSettings = "Settings"
    override val greetingMorning = "Good Morning"
    override val greetingAfternoon = "Good Afternoon"
    override val greetingEvening = "Good Evening"
    override val greetingNight = "Good Night"
    override val searchPlaceholder = "Search..."
    override val cancel = "Cancel"
    override val confirm = "Confirm"
    override val save = "Save"
    override val delete = "Delete"
    override val edit = "Edit"
    override val close = "Close"
    override val all = "All"
    override val loading = "Loading..."
    override val retry = "Retry"
    override val todaySales = "Today's Total Sales"
    override val todayTransactions = "Today's Transactions"
    override val salesTrendTitle = "Sales Trend Chart"
    override val salesTrendSubtitle = "Cashier Activity & Peak Hours"
    override val periodToday = "Today"
    override val period7Days = "7 days"
    override val period30Days = "30 days"
    override val periodThisMonth = "This month"
    override val topProductsTitle = "Best-Selling Products"
    override val soldLabel = "sold"
    override val noSalesDataYet = "No completed transactions in this period yet."
    override val recentTransactionsTitle = "Recent Transactions"
    override val viewAllHistory = "View All History"
    override val noTransactionsYet = "No transactions recorded yet."
    override val stockAlertsTitle = "Stock Notifications"
    override val allStockHealthy = "All product stock levels are healthy."
    override val lowStockBadge = "Low Stock"
    override val outOfStockBadge = "Out of Stock"
    override val searchProductHint = "Search product name or SKU..."
    override val allProducts = "All products"
    override val cartTitle = "Cart"
    override val emptyCartHint = "Tap a product to add it to the cart"
    override val addToCart = "Add to Cart"
    override val selectQuantityTitle = "Purchase Quantity"
    override val quickAmountLabel = "Quick Select"
    override val manualDecimalLabel = "Manual Input (e.g. 0.75, 1.25)"
    override val estimatedSubtotal = "Item Subtotal"
    override val subtotalLabel = "Subtotal"
    override val customerNameLabel = "Customer Name (Optional)"
    override val customerNameHint = "May be left blank"
    override val discountLabel = "Discount"
    override val discountNone = "No Discount"
    override val discountPercent = "Percentage (%)"
    override val discountAmount = "Amount"
    override val taxLabel = "Tax (VAT/Sales Tax)"
    override val taxNone = "No Tax"
    override val taxAuto = "Auto Tax"
    override val taxManual = "Manual Tax"
    override val paymentMethodLabel = "Payment Method"
    override val methodCash = "Cash"
    override val methodBankTransfer = "Bank Transfer"
    override val methodQris = "QRIS / QR Pay"
    override val methodOther = "Other"
    override val amountReceivedLabel = "Amount Received"
    override val exactCashButton = "Exact Cash"
    override val changeLabel = "Change"
    override val grandTotalLabel = "Grand Total"
    override val completeTransactionButton = "Complete Transaction"
    override val insufficientStockError = "Insufficient product stock."
    override val insufficientPaymentError = "Amount received is less than grand total."
    override val transactionSuccessMsg = "Transaction saved successfully!"
    override val viewCartButton = "View Cart"
    override val receiptTitle = "Transaction Receipt"
    override val receiptNumber = "Transaction No."
    override val receiptDate = "Time"
    override val receiptCustomer = "Customer"
    override val receiptCashierNote = "Thank you for shopping with us!"
    override val printReceipt = "Print Receipt"
    override val savePdf = "Save PDF"
    override val shareWhatsApp = "Share WA"
    override val printerWidth58 = "Thermal 58 mm"
    override val printerWidth80 = "Thermal 80 mm"
    override val selectPrinterTitle = "Select Bluetooth Printer"
    override val noPairedPrinters = "No paired Bluetooth thermal printer found. Use System Print / PDF or pair your printer in Android Bluetooth settings."
    override val printSuccessMsg = "Receipt sent to printer."
    override val printFailedMsg = "Failed to print to Bluetooth printer."
    override val pdfSavedMsg = "Receipt PDF saved successfully."
    override val bluetoothPermissionNeeded = "Bluetooth permission is required to print to thermal printers."
    override val systemPrintFallback = "System Print / PDF"
    override val addProduct = "Add Product"
    override val editProduct = "Edit Product"
    override val productNameLabel = "Product Name"
    override val productSkuLabel = "Product Code / SKU"
    override val productCategoryLabel = "Category"
    override val productUnitLabel = "Unit"
    override val productCostPriceLabel = "Cost Price"
    override val productSellPriceLabel = "Sell Price"
    override val productStockLabel = "Stock Quantity"
    override val productMinStockLabel = "Minimum Stock (Optional)"
    override val productDescLabel = "Product Description (Optional)"
    override val productPhotoLabel = "Product Photo"
    override val pickFromGallery = "Choose from Gallery"
    override val takePhoto = "Take Photo"
    override val manageCategories = "Manage Categories"
    override val addCategory = "Add Category"
    override val editCategory = "Edit Category"
    override val categoryNameLabel = "Category Name"
    override val noProductsFound = "No matching products found."
    override val deleteProductConfirm = "Delete this product from inventory?"
    override val stockWarningLeft = "Remaining stock"
    override val searchTrxHint = "Search transaction number or customer..."
    override val filterDateLabel = "Date Period"
    override val filterMethodLabel = "Payment Method"
    override val trxDetailTitle = "Transaction Details"
    override val purchasedItemsLabel = "Purchased Items"
    override val reprintReceipt = "Reprint Receipt"
    override val shareReceipt = "Share Receipt"
    override val cancelTransaction = "Cancel Transaction"
    override val refundTransaction = "Refund Transaction"
    override val cancelConfirmTitle = "Cancel Transaction?"
    override val refundConfirmTitle = "Process Refund?"
    override val cancelRefundWarning = "Transaction status will be updated and product stock will be restored automatically."
    override val reasonOptionalLabel = "Reason (Optional)"
    override val statusCompleted = "Completed"
    override val statusCancelled = "Canceled"
    override val statusRefunded = "Refunded"
    override val storeProfileSection = "Store Profile"
    override val storeNameLabel = "Store Name"
    override val ownerNameLabel = "Owner Name"
    override val phoneLabel = "Phone Number"
    override val addressLabel = "Store Address"
    override val defaultTaxPercentLabel = "Auto Tax Rate (%)"
    override val saveChanges = "Save Changes"
    override val profileSavedMsg = "Store profile saved and synchronized."
    override val themeSection = "App Theme"
    override val lightTheme = "Light Theme"
    override val darkTheme = "Dark Theme"
    override val languageSection = "Language"
    override val languageIndonesian = "Bahasa Indonesia"
    override val languageEnglish = "English (US)"
    override val backupRestoreSection = "Data Backup & Restore"
    override val manualBackupButton = "Manual Backup"
    override val restoreBackupButton = "Restore Data"
    override val lastBackupLabel = "Last Backup"
    override val neverBackedUp = "Never backed up yet"
    override val backupInProgress = "Processing data backup..."
    override val backupSuccessMsg = "Backup created and saved successfully."
    override val restoreConfirmTitle = "Confirm Data Restore"
    override val restoreConfirmBody = "Restoring will update your products, categories, transactions, and store profile. Continue?"
    override val restoreSuccessMsg = "Data restored and synchronized successfully."
    override val restoreInvalidMsg = "Invalid or corrupted backup file."
    override val aboutSection = "About App"
    override val versionLabel = "App Version"
    override val aboutDescription = "Modern Point of Sale (POS) application to manage cashier transactions, products, stock, and store history with real-time sync."
    override val copyrightText = "© 2026 Rafline Dev. All rights reserved."
    override val googleAccountSection = "Google Account & Sync"
    override val syncedWithGoogle = "Automatically synchronized with Google account"
    override val signOutButton = "Sign Out"
    override val reportFilterDate = "Date"
    override val reportFilterMonth = "Month"
    override val reportFilterYear = "Year"
    override val reportTotalSales = "Total Sales"
    override val reportOmset = "Revenue"
    override val reportHpp = "COGS / Capital"
    override val reportGrossProfit = "Gross Profit"
    override val reportExpensesSummary = "Period Expenses"
    override val reportNetProfit = "Estimated Net Profit"
    override val reportPaymentBreakdown = "Payment Method Breakdown"
    override val reportSelectDate = "Select Date"
    override val addExpense = "Add Expense"
    override val editExpense = "Edit Expense"
    override val expenseTitleLabel = "Expense Title"
    override val expenseCategoryLabel = "Expense Category"
    override val expenseAmountLabel = "Amount"
    override val expenseNotesLabel = "Notes (Optional)"
    override val expenseDateLabel = "Expense Date"
    override val todayExpensesLabel = "Today's Expenses"
    override val monthExpensesLabel = "This Month's Expenses"
    override val noExpensesYet = "No expenses recorded yet."
    override val deleteExpenseConfirm = "Delete this expense record?"
    override val expenseSavedMsg = "Expense saved successfully."
    override val settingsConfigSection = "Store & POS Configuration"
    override val settingsHardwareSection = "Hardware & Receipt"
    override val settingsDataSection = "Data & Backup"
    override val settingsAccountSection = "Account & App"
    override val storeProfileSubtitle = "Store name, address, phone, photo, & tax"
    override val appearanceLanguageTitle = "Appearance & Language"
    override val appearanceLanguageSubtitle = "App theme and display language"
    override val printerSettingsTitle = "Cashier Printer"
    override val printerSettingsSubtitle = "Manage Bluetooth printers & connected list"
    override val backupRestoreSubtitle = "Manual JSON backup & restore store data"
    override val connectedPrintersTitle = "Connected Printers List"
    override val selectedPrinterLabel = "Active Cashier Printer"
    override val noPrinterSelected = "No default printer selected"
    override val defaultPaperWidthLabel = "Default Receipt Paper Width"
    override val refreshPrintersButton = "Refresh List"
    override val testPrintButton = "Test Print"
    override val selectPrinterButton = "Select"
    override val disconnectPrinterButton = "Disconnect"
    override val printerConnectedBadge = "Connected"
    override val printerPairedBadge = "Paired"
    override val printerSelectedBadge = "Active"
    override val openBluetoothSettingsButton = "Open Android Bluetooth Settings"
    override val splashByDeveloper = "by Rafline Dev"
    override val receiptCashierLabel = "Cashier"
    override val receiptCustomTitle = "Receipt Customization"
    override val receiptCustomSubtitle = "Store logo, cashier, phone, address & footer"
    override val receiptPreviewLabel = "Receipt Image Preview"
    override val receiptShowLogoLabel = "Show Store Logo"
    override val receiptShowCashierLabel = "Show Cashier Name"
    override val receiptShowPhoneLabel = "Show Store Phone"
    override val receiptShowAddressLabel = "Show Store Address"
    override val receiptShowCustomerLabel = "Show Customer Name"
    override val receiptFooterMessageLabel = "Receipt Footer Message"
    override val receiptFooterMessageHint = "e.g. Thank you, please come again!"
    override val receiptCustomSavedMsg = "Receipt customization saved."
    override val userManageTitle = "Manage Users"
    override val userManageSubtitle = "Cashier Mode & Multi-Email Monitor Mode"
    override val userRoleInfoTitle = "Cashier & Monitor Mode Guide"
    override val userRoleInfoBody = "• Cashier Mode: Full access to process sales, manage products, stock, expenses, reports, and store settings.\n\n• Monitor Mode (Owner): Allows the store owner to monitor sales, profit, charts, inventory, and history in real-time (read-only) using a different Google email.\n\n• How to link: On the Cashier account, add the Owner's Google email to Authorized Monitor Emails. Then sign in on the Owner's device with that email and switch to Monitor Mode."
    override val userModeCashierLabel = "Cashier Mode"
    override val userModeMonitorLabel = "Monitor Mode"
    override val userModeCashierDesc = "Full POS access (manage products & sales)"
    override val userModeMonitorDesc = "Monitor linked store sales in real-time (read-only)"
    override val monitorEmailListLabel = "Authorized Monitor Emails (For Owner)"
    override val monitorEmailInputHint = "e.g. owner@gmail.com"
    override val addMonitorEmailButton = "Add Email"
    override val noMonitorEmailsYet = "No monitor emails linked yet."
    override val monitorStoresAvailableLabel = "Linked Cashier Stores"
    override val monitorNoStoresLinked = "No cashier store has authorized this email (%s) yet."
    override val connectStoreButton = "Monitor Store"
    override val connectedBadge = "Connected"
    override val monitorBannerTitle = "Monitor Mode (Read-Only)"
    override val monitorReadOnlyError = "Monitor Mode is read-only. Switch to Cashier Mode to edit data."
    override val storeAvatarPhotoTab = "Store Photo"
    override val storeAvatarIconTab = "Business Icon"
    override val storeIconPickerLabel = "Choose Business Icon"
    override val storeIconColorLabel = "Icon Background Color"
    override val changeStorePhotoButton = "Upload / Change Photo"
    override val removeStorePhotoButton = "Use Business Icon"
    override val taxDiscountTitle = "Tax & Discount"
    override val taxDiscountSubtitle = "Configure auto tax, manual tax, & default discount"
    override val defaultTaxModeLabel = "Default Cashier Tax Mode"
    override val defaultManualTaxAmountLabel = "Default Manual Tax Amount"
    override val defaultDiscountModeLabel = "Default Cashier Discount Mode"
    override val defaultDiscountValueLabel = "Default Discount Value"
    override val taxDiscountSavedMsg = "Tax & discount settings saved."
    override val receiptPrefixLabel = "Receipt Number Prefix"
    override val receiptPrefixHint = "e.g. AK, SHOP, INV"
    override val staffRoleSectionTitle = "Role & Permissions on This Device"
    override val roleOwnerLabel = "Owner"
    override val roleOwnerDesc = "Full access to all features, sales reports, products, expenses, & settings."
    override val roleCashierLabel = "Cashier / Staff"
    override val roleCashierDesc = "Transaction-only by default. Owner can grant additional features."
    override val cashierPermissionsTitle = "Customize Cashier / Staff Features"
    override val presetCashierTxOnly = "Transactions Only"
    override val presetCashierAllFeatures = "All Features"
    override val permAllowProducts = "Manage Products & Stock"
    override val permAllowSalesReport = "View Sales Report"
    override val permAllowExpenses = "Manage Expenses"
    override val permAllowHistory = "View Transaction History"
    override val permAllowCancelRefund = "Cancel / Refund Transactions"
    override val permAllowSettings = "Access Settings Menu"
    override val ownerPinSectionTitle = "Owner Security PIN (4-6 Digits)"
    override val ownerPinLabel = "Security PIN (Optional)"
    override val ownerPinHint = "Required when switching back from Cashier to Owner mode"
    override val savePermissionsButton = "Save Permissions & PIN"
    override val permissionsSavedMsg = "User permissions & security PIN saved."
    override val enterPinTitle = "Enter Owner PIN"
    override val enterPinSubtitle = "Enter security PIN to unlock full Owner access."
    override val wrongPinError = "Incorrect security PIN. Please try again."
    override val switchToOwnerButton = "Owner Mode"
    override val monitorLimitNote = "Link 1 to 2 Google accounts for real-time remote store monitoring."
    override val monitorLimitReachedMsg = "Maximum of 2 monitor accounts already linked."
    override val settingsGuideSection = "Guide & About App"
    override val tutorialTitle = "Quick Usage Tutorial"
    override val tutorialSubtitle = "How to use the app, checkout, print receipts & monitor remotely"
    override val cartItemCountLabel = "Items"
    override val clearCartButton = "Clear"
    override val billSummaryTitle = "Bill Summary"
    override val storeOperatingHoursLabel = "Operating Hours (Optional)"
    override val storeOperatingHoursHint = "e.g. Mon - Sun 08:00 - 21:00"
    override val showOperatingHoursOnReceiptLabel = "Show Operating Hours on Receipt"
    override val receiptShowOperatingHoursLabel = "Show Operating Hours"
    override val addShortButton = "Add"
    override val chooseUnitPrefix = "Select"
    override val productsInCartLabel = "Items in Cart"
    override val quickUnitDialogTitle = "Select Weight / Quantity"
    override val quickSelectUnitPrefix = "Quick Select"
    override val manualWeightLabel = "Or Type Quantity"
    override val estimatedPriceLabel = "Estimated Price"
    override val addConfirmButton = "Add"
    override val stockLabelPrefix = "Stock:"
    override val unlimitedStockLabel = "Unlimited"

    override val regionSoutheastAsia = "Southeast Asia"
    override val regionAsia = "Asia"
    override val regionEurope = "Europe"
    override val regionRussia = "Russia"
    override val regionAmerica = "America"
    override val regionAustralia = "Australia"
    override val currencySectionTitle = "Currency"
    override val currencySettingsSubtitle = "Choose store currency from global countries"
    override val currencyPickerTitle = "Select Store Currency"
    override val currencySamplePreviewLabel = "Price format preview"
    override val currencySavedMsg = "Store currency updated."
    override val languagePickerSubtitle = "Choose display language across global regions"
    override val searchLanguageHint = "Search language or country..."
    override val searchCurrencyHint = "Search currency, code, or country..."
    override val aboutOpenHint = "Tap card to open app & sync details"
    override val developerLabel = "Developer"

    override val trxShortLabel = "Trx"
    override val transactionsPluralLabel = "Transactions"
    override val includingTaxPrefix = "Includes tax"
    override val reportHppSubtitle = "Cost of goods sold"
    override val marginLabel = "Margin"
    override val salesMinusCogsLabel = "Sales - COGS"
    override val previousLabel = "Previous"
    override val nextLabel = "Next"
    override val decreaseQtyLabel = "Decrease"
    override val increaseQtyLabel = "Increase"
    override val monitorWord = "Monitor"
    override val prefixLabel = "Prefix"
    override val pinInputLabel = "PIN (4-6 digits)"
    override val defaultStoreName = "ADA KASIR Store"
    override val receiptDefaultCustomer = "John Doe"
    override val receiptSampleItem1 = "Premium Rice 5 Kg"
    override val receiptSampleItem2 = "Mineral Water"
    override val receiptDefaultHours = "Open 08:00 - 21:00"
    override val receiptDefaultAddress = "123 Main Street"
    override val receiptPhonePrefix = "Phone"
    override val receiptFilePrefix = "Receipt"
    override val testPrintHeader = "PRINTER TEST PRINT"
    override val testPrintStatusReady = "Status: Connected & Ready"
    override val defaultCategoryOther = "Other"
    override val discountAmountWord = "Amount"
    override val taxManualWord = "Manual Tax"
    override val defaultManualTaxWord = "Default Manual Tax"
    override val productCostPriceWord = "Cost Price"
    override val productSellPriceWord = "Sell Price"
    override val expenseAmountWord = "Amount"
    override val storePhotoCd = "Store photo"
    override val businessIconCd = "Business icon"
    override val supermarketBgCd = "Store background illustration"

    override val errProductNameEmpty = "Product name is required."
    override val errCategoryNameEmpty = "Category name is required."
    override val errStoreNameEmpty = "Store name is required."
    override val errEmailEmpty = "Email is required."
    override val errMonitorSelfEmail = "Monitor email cannot be the same as Cashier email."
    override val errExpensesInvalid = "Expense title and valid amount are required."
    override val errInvalidPin = "Owner security PIN must be 4-6 digits."
    override val errNoMonitorTarget = "Select a store first for Monitor Mode."
    override val errConnectStoreFailed = "Failed to connect monitor store."
    override val errCartEmpty = "Cart is empty."
    override val errSaveFailed = "Failed to save data."

    override val expCatOperational = "Operational"
    override val expCatStockPurchase = "Stock Purchase"
    override val expCatUtilities = "Utilities"
    override val expCatSalary = "Salary & Bonus"
    override val expCatTransport = "Transport"
    override val expCatOther = "Other"

    override val bizIconStorefront = "Store / Retail"
    override val bizIconCafe = "Cafe / Coffee"
    override val bizIconRestaurant = "Restaurant"
    override val bizIconFastFood = "Food & Snacks"
    override val bizIconGrocery = "Grocery / Minimarket"
    override val bizIconBakery = "Bakery & Pastry"
    override val bizIconPharmacy = "Pharmacy / Health"
    override val bizIconFashion = "Fashion / Boutique"
    override val bizIconElectronics = "Electronics / Gadgets"
    override val bizIconLaundry = "Laundry"
    override val bizIconService = "Services / Repair"

    override val tutorialStep1Title = "1. Store Profile & Logo"
    override val tutorialStep1Body = "Set your store name, phone, address, upload a store photo, or choose a business icon & background color in Settings -> Store Profile."
    override val tutorialStep2Title = "2. Products & Inventory"
    override val tutorialStep2Body = "Open Products to add items, categories, cost & selling prices, stock counts, and low-stock alert thresholds."
    override val tutorialStep3Title = "3. Cashier Checkout"
    override val tutorialStep3Body = "Tap products to add them to the cart (tap again to increase quantity or choose decimal units like Kg/Liter). Open the blue cart bar at the bottom to add customer name, discount, tax, payment method, and finish the sale."
    override val tutorialStep4Title = "4. Tax, Discount & Receipts"
    override val tutorialStep4Body = "Configure auto or manual tax & default discounts in Tax & Discount. Customize receipt prefix (e.g., AK / SHOP), footer message, and pair a 58mm/80mm Bluetooth thermal printer under Cashier Printer."
    override val tutorialStep5Title = "5. Sales Report, Expenses & History"
    override val tutorialStep5Body = "Track daily/monthly revenue, COGS, net profit, operational expenses, and reprint or refund past receipts in History."
    override val tutorialStep6Title = "6. Owner, Cashier Staff, PIN & Remote Monitor"
    override val tutorialStep6Body = "Switch between Owner (full access) and Cashier/Staff (default transaction-only, customizable features) protected by an Owner Security PIN. Link 1–2 Google accounts to monitor store sales remotely in Monitor Mode."
}

fun AppStrings.amountWithCurrency(baseLabel: String, currencySymbol: String): String =
    "$baseLabel ($currencySymbol)"

fun AppStrings.tutorialStepsList(): List<Pair<String, String>> = listOf(
    tutorialStep1Title to tutorialStep1Body,
    tutorialStep2Title to tutorialStep2Body,
    tutorialStep3Title to tutorialStep3Body,
    tutorialStep4Title to tutorialStep4Body,
    tutorialStep5Title to tutorialStep5Body,
    tutorialStep6Title to tutorialStep6Body
)

fun appStringsForLanguage(languageCode: String?): AppStrings =
    when (AppLanguageCatalog.normalizeCode(languageCode)) {
        "id" -> IndonesianStrings
        "ms" -> MalayStrings
        "tl" -> FilipinoStrings
        "vi" -> VietnameseStrings
        "th" -> ThaiStrings
        "ja" -> JapaneseStrings
        "ko" -> KoreanStrings
        "zh" -> ChineseSimplifiedStrings
        "hi" -> HindiStrings
        "en_gb" -> EnglishUkStrings
        "fr" -> FrenchStrings
        "de" -> GermanStrings
        "es" -> SpanishStrings
        "it" -> ItalianStrings
        "nl" -> DutchStrings
        "pt" -> PortugueseStrings
        "pl" -> PolishStrings
        "tr" -> TurkishStrings
        "ru" -> RussianStrings
        "en" -> EnglishStrings
        "es_mx" -> SpanishLatamStrings
        "pt_br" -> PortugueseBrazilStrings
        "en_au" -> EnglishAuStrings
        else -> IndonesianStrings
    }

val LocalAppStrings = staticCompositionLocalOf<AppStrings> { IndonesianStrings }

val LocalAppCurrency = staticCompositionLocalOf<PosCurrencyOption> { AppCurrencyCatalog.fromCode("IDR") }
