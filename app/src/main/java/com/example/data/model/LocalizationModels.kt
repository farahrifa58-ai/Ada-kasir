package com.example.data.model

import com.example.ui.theme.AppStrings
import java.util.Locale

enum class LocalizationRegion(val code: String) {
    SOUTHEAST_ASIA("SOUTHEAST_ASIA"),
    ASIA("ASIA"),
    EUROPE("EUROPE"),
    RUSSIA("RUSSIA"),
    AMERICA("AMERICA"),
    AUSTRALIA("AUSTRALIA");

    fun label(strings: AppStrings): String = when (this) {
        SOUTHEAST_ASIA -> strings.regionSoutheastAsia
        ASIA -> strings.regionAsia
        EUROPE -> strings.regionEurope
        RUSSIA -> strings.regionRussia
        AMERICA -> strings.regionAmerica
        AUSTRALIA -> strings.regionAustralia
    }
}

data class PosLanguageOption(
    val code: String,
    val nativeName: String,
    val regionLabel: String,
    val localeTag: String,
    val region: LocalizationRegion,
    val flagEmoji: String
)

object AppLanguageCatalog {
    val all: List<PosLanguageOption> = listOf(
        // Southeast Asia
        PosLanguageOption("id", "Bahasa Indonesia", "Indonesia", "id-ID", LocalizationRegion.SOUTHEAST_ASIA, "🇮🇩"),
        PosLanguageOption("ms", "Bahasa Melayu", "Malaysia & Brunei", "ms-MY", LocalizationRegion.SOUTHEAST_ASIA, "🇲🇾"),
        PosLanguageOption("tl", "Filipino", "Philippines", "fil-PH", LocalizationRegion.SOUTHEAST_ASIA, "🇵🇭"),
        PosLanguageOption("vi", "Tiếng Việt", "Vietnam", "vi-VN", LocalizationRegion.SOUTHEAST_ASIA, "🇻🇳"),
        PosLanguageOption("th", "ภาษาไทย", "Thailand", "th-TH", LocalizationRegion.SOUTHEAST_ASIA, "🇹🇭"),

        // Asia
        PosLanguageOption("ja", "日本語", "Japan", "ja-JP", LocalizationRegion.ASIA, "🇯🇵"),
        PosLanguageOption("ko", "한국어", "South Korea", "ko-KR", LocalizationRegion.ASIA, "🇰🇷"),
        PosLanguageOption("zh", "简体中文", "China", "zh-CN", LocalizationRegion.ASIA, "🇨🇳"),
        PosLanguageOption("hi", "हिन्दी", "India", "hi-IN", LocalizationRegion.ASIA, "🇮🇳"),

        // Europe
        PosLanguageOption("en_gb", "English (UK)", "United Kingdom", "en-GB", LocalizationRegion.EUROPE, "🇬🇧"),
        PosLanguageOption("fr", "Français", "France", "fr-FR", LocalizationRegion.EUROPE, "🇫🇷"),
        PosLanguageOption("de", "Deutsch", "Germany", "de-DE", LocalizationRegion.EUROPE, "🇩🇪"),
        PosLanguageOption("es", "Español", "Spain", "es-ES", LocalizationRegion.EUROPE, "🇪🇸"),
        PosLanguageOption("it", "Italiano", "Italy", "it-IT", LocalizationRegion.EUROPE, "🇮🇹"),
        PosLanguageOption("nl", "Nederlands", "Netherlands", "nl-NL", LocalizationRegion.EUROPE, "🇳🇱"),
        PosLanguageOption("pt", "Português", "Portugal", "pt-PT", LocalizationRegion.EUROPE, "🇵🇹"),
        PosLanguageOption("pl", "Polski", "Poland", "pl-PL", LocalizationRegion.EUROPE, "🇵🇱"),
        PosLanguageOption("tr", "Türkçe", "Turkey", "tr-TR", LocalizationRegion.EUROPE, "🇹🇷"),

        // Russia
        PosLanguageOption("ru", "Русский", "Russia", "ru-RU", LocalizationRegion.RUSSIA, "🇷🇺"),

        // America
        PosLanguageOption("en", "English (US)", "United States", "en-US", LocalizationRegion.AMERICA, "🇺🇸"),
        PosLanguageOption("es_mx", "Español (México)", "Mexico & LATAM", "es-MX", LocalizationRegion.AMERICA, "🇲🇽"),
        PosLanguageOption("pt_br", "Português (Brasil)", "Brazil", "pt-BR", LocalizationRegion.AMERICA, "🇧🇷"),

        // Australia
        PosLanguageOption("en_au", "English (Australia)", "Australia", "en-AU", LocalizationRegion.AUSTRALIA, "🇦🇺")
    )

    val supportedCodes: Set<String> = all.map { it.code }.toSet()

    fun normalizeCode(code: String?): String {
        val cleaned = code?.trim()?.lowercase(Locale.US).orEmpty()
        return if (cleaned in supportedCodes) cleaned else "id"
    }

    fun fromCode(code: String?): PosLanguageOption {
        val normalized = normalizeCode(code)
        return all.firstOrNull { it.code == normalized } ?: all.first()
    }

    fun localeFor(code: String?): Locale {
        val option = fromCode(code)
        return Locale.forLanguageTag(option.localeTag)
    }

    fun byRegion(): Map<LocalizationRegion, List<PosLanguageOption>> =
        LocalizationRegion.entries.associateWith { region ->
            all.filter { it.region == region }
        }
}

data class PosCurrencyOption(
    val code: String,
    val symbol: String,
    val name: String,
    val country: String,
    val region: LocalizationRegion,
    val flagEmoji: String,
    val decimalPlaces: Int,
    val groupingSeparator: Char,
    val decimalSeparator: Char,
    val quickCashNominals: List<Double>,
    val quickDiscountPresets: List<Pair<String, String>>
)

object AppCurrencyCatalog {
    val all: List<PosCurrencyOption> = listOf(
        // Southeast Asia
        PosCurrencyOption(
            code = "IDR",
            symbol = "Rp",
            name = "Indonesian Rupiah",
            country = "Indonesia",
            region = LocalizationRegion.SOUTHEAST_ASIA,
            flagEmoji = "🇮🇩",
            decimalPlaces = 0,
            groupingSeparator = '.',
            decimalSeparator = ',',
            quickCashNominals = listOf(10_000.0, 20_000.0, 50_000.0, 100_000.0),
            quickDiscountPresets = listOf("5000" to "Rp 5.000", "10000" to "Rp 10.000", "20000" to "Rp 20.000")
        ),
        PosCurrencyOption(
            code = "MYR",
            symbol = "RM",
            name = "Malaysian Ringgit",
            country = "Malaysia",
            region = LocalizationRegion.SOUTHEAST_ASIA,
            flagEmoji = "🇲🇾",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "RM 5", "10" to "RM 10", "20" to "RM 20")
        ),
        PosCurrencyOption(
            code = "SGD",
            symbol = "S$",
            name = "Singapore Dollar",
            country = "Singapore",
            region = LocalizationRegion.SOUTHEAST_ASIA,
            flagEmoji = "🇸🇬",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "S$ 5", "10" to "S$ 10", "20" to "S$ 20")
        ),
        PosCurrencyOption(
            code = "THB",
            symbol = "฿",
            name = "Thai Baht",
            country = "Thailand",
            region = LocalizationRegion.SOUTHEAST_ASIA,
            flagEmoji = "🇹🇭",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(100.0, 200.0, 500.0, 1000.0),
            quickDiscountPresets = listOf("20" to "฿ 20", "50" to "฿ 50", "100" to "฿ 100")
        ),
        PosCurrencyOption(
            code = "VND",
            symbol = "₫",
            name = "Vietnamese Dong",
            country = "Vietnam",
            region = LocalizationRegion.SOUTHEAST_ASIA,
            flagEmoji = "🇻🇳",
            decimalPlaces = 0,
            groupingSeparator = '.',
            decimalSeparator = ',',
            quickCashNominals = listOf(50_000.0, 100_000.0, 200_000.0, 500_000.0),
            quickDiscountPresets = listOf("10000" to "₫ 10.000", "20000" to "₫ 20.000", "50000" to "₫ 50.000")
        ),
        PosCurrencyOption(
            code = "PHP",
            symbol = "₱",
            name = "Philippine Peso",
            country = "Philippines",
            region = LocalizationRegion.SOUTHEAST_ASIA,
            flagEmoji = "🇵🇭",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(100.0, 200.0, 500.0, 1000.0),
            quickDiscountPresets = listOf("20" to "₱ 20", "50" to "₱ 50", "100" to "₱ 100")
        ),
        PosCurrencyOption(
            code = "BND",
            symbol = "B$",
            name = "Brunei Dollar",
            country = "Brunei",
            region = LocalizationRegion.SOUTHEAST_ASIA,
            flagEmoji = "🇧🇳",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "B$ 5", "10" to "B$ 10", "20" to "B$ 20")
        ),

        // Asia
        PosCurrencyOption(
            code = "JPY",
            symbol = "¥",
            name = "Japanese Yen",
            country = "Japan",
            region = LocalizationRegion.ASIA,
            flagEmoji = "🇯🇵",
            decimalPlaces = 0,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(1000.0, 2000.0, 5000.0, 10_000.0),
            quickDiscountPresets = listOf("100" to "¥ 100", "500" to "¥ 500", "1000" to "¥ 1,000")
        ),
        PosCurrencyOption(
            code = "KRW",
            symbol = "₩",
            name = "South Korean Won",
            country = "South Korea",
            region = LocalizationRegion.ASIA,
            flagEmoji = "🇰🇷",
            decimalPlaces = 0,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(5000.0, 10_000.0, 30_000.0, 50_000.0),
            quickDiscountPresets = listOf("1000" to "₩ 1,000", "3000" to "₩ 3,000", "5000" to "₩ 5,000")
        ),
        PosCurrencyOption(
            code = "CNY",
            symbol = "CN¥",
            name = "Chinese Yuan",
            country = "China",
            region = LocalizationRegion.ASIA,
            flagEmoji = "🇨🇳",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(20.0, 50.0, 100.0, 200.0),
            quickDiscountPresets = listOf("5" to "CN¥ 5", "10" to "CN¥ 10", "20" to "CN¥ 20")
        ),
        PosCurrencyOption(
            code = "INR",
            symbol = "₹",
            name = "Indian Rupee",
            country = "India",
            region = LocalizationRegion.ASIA,
            flagEmoji = "🇮🇳",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(100.0, 200.0, 500.0, 2000.0),
            quickDiscountPresets = listOf("50" to "₹ 50", "100" to "₹ 100", "200" to "₹ 200")
        ),
        PosCurrencyOption(
            code = "TWD",
            symbol = "NT$",
            name = "New Taiwan Dollar",
            country = "Taiwan",
            region = LocalizationRegion.ASIA,
            flagEmoji = "🇹🇼",
            decimalPlaces = 0,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(100.0, 500.0, 1000.0, 2000.0),
            quickDiscountPresets = listOf("50" to "NT$ 50", "100" to "NT$ 100", "200" to "NT$ 200")
        ),
        PosCurrencyOption(
            code = "SAR",
            symbol = "SAR",
            name = "Saudi Riyal",
            country = "Saudi Arabia",
            region = LocalizationRegion.ASIA,
            flagEmoji = "🇸🇦",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(20.0, 50.0, 100.0, 500.0),
            quickDiscountPresets = listOf("10" to "SAR 10", "20" to "SAR 20", "50" to "SAR 50")
        ),

        // Europe
        PosCurrencyOption(
            code = "EUR",
            symbol = "€",
            name = "Euro",
            country = "European Union",
            region = LocalizationRegion.EUROPE,
            flagEmoji = "🇪🇺",
            decimalPlaces = 2,
            groupingSeparator = '.',
            decimalSeparator = ',',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "€ 5", "10" to "€ 10", "20" to "€ 20")
        ),
        PosCurrencyOption(
            code = "GBP",
            symbol = "£",
            name = "British Pound",
            country = "United Kingdom",
            region = LocalizationRegion.EUROPE,
            flagEmoji = "🇬🇧",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "£ 5", "10" to "£ 10", "20" to "£ 20")
        ),
        PosCurrencyOption(
            code = "CHF",
            symbol = "CHF",
            name = "Swiss Franc",
            country = "Switzerland",
            region = LocalizationRegion.EUROPE,
            flagEmoji = "🇨🇭",
            decimalPlaces = 2,
            groupingSeparator = '\'',
            decimalSeparator = '.',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "CHF 5", "10" to "CHF 10", "20" to "CHF 20")
        ),
        PosCurrencyOption(
            code = "PLN",
            symbol = "zł",
            name = "Polish Zloty",
            country = "Poland",
            region = LocalizationRegion.EUROPE,
            flagEmoji = "🇵🇱",
            decimalPlaces = 2,
            groupingSeparator = ' ',
            decimalSeparator = ',',
            quickCashNominals = listOf(20.0, 50.0, 100.0, 200.0),
            quickDiscountPresets = listOf("10" to "zł 10", "20" to "zł 20", "50" to "zł 50")
        ),
        PosCurrencyOption(
            code = "SEK",
            symbol = "kr",
            name = "Swedish Krona",
            country = "Sweden",
            region = LocalizationRegion.EUROPE,
            flagEmoji = "🇸🇪",
            decimalPlaces = 2,
            groupingSeparator = ' ',
            decimalSeparator = ',',
            quickCashNominals = listOf(50.0, 100.0, 200.0, 500.0),
            quickDiscountPresets = listOf("20" to "kr 20", "50" to "kr 50", "100" to "kr 100")
        ),
        PosCurrencyOption(
            code = "TRY",
            symbol = "₺",
            name = "Turkish Lira",
            country = "Turkey",
            region = LocalizationRegion.EUROPE,
            flagEmoji = "🇹🇷",
            decimalPlaces = 2,
            groupingSeparator = '.',
            decimalSeparator = ',',
            quickCashNominals = listOf(50.0, 100.0, 200.0, 500.0),
            quickDiscountPresets = listOf("25" to "₺ 25", "50" to "₺ 50", "100" to "₺ 100")
        ),

        // Russia
        PosCurrencyOption(
            code = "RUB",
            symbol = "₽",
            name = "Russian Ruble",
            country = "Russia",
            region = LocalizationRegion.RUSSIA,
            flagEmoji = "🇷🇺",
            decimalPlaces = 2,
            groupingSeparator = ' ',
            decimalSeparator = ',',
            quickCashNominals = listOf(500.0, 1000.0, 2000.0, 5000.0),
            quickDiscountPresets = listOf("100" to "₽ 100", "250" to "₽ 250", "500" to "₽ 500")
        ),

        // America
        PosCurrencyOption(
            code = "USD",
            symbol = "$",
            name = "US Dollar",
            country = "United States",
            region = LocalizationRegion.AMERICA,
            flagEmoji = "🇺🇸",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "$ 5", "10" to "$ 10", "20" to "$ 20")
        ),
        PosCurrencyOption(
            code = "CAD",
            symbol = "CA$",
            name = "Canadian Dollar",
            country = "Canada",
            region = LocalizationRegion.AMERICA,
            flagEmoji = "🇨🇦",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "CA$ 5", "10" to "CA$ 10", "20" to "CA$ 20")
        ),
        PosCurrencyOption(
            code = "BRL",
            symbol = "R$",
            name = "Brazilian Real",
            country = "Brazil",
            region = LocalizationRegion.AMERICA,
            flagEmoji = "🇧🇷",
            decimalPlaces = 2,
            groupingSeparator = '.',
            decimalSeparator = ',',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "R$ 5", "10" to "R$ 10", "20" to "R$ 20")
        ),
        PosCurrencyOption(
            code = "MXN",
            symbol = "MX$",
            name = "Mexican Peso",
            country = "Mexico",
            region = LocalizationRegion.AMERICA,
            flagEmoji = "🇲🇽",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(100.0, 200.0, 500.0, 1000.0),
            quickDiscountPresets = listOf("20" to "MX$ 20", "50" to "MX$ 50", "100" to "MX$ 100")
        ),

        // Australia
        PosCurrencyOption(
            code = "AUD",
            symbol = "A$",
            name = "Australian Dollar",
            country = "Australia",
            region = LocalizationRegion.AUSTRALIA,
            flagEmoji = "🇦🇺",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "A$ 5", "10" to "A$ 10", "20" to "A$ 20")
        ),
        PosCurrencyOption(
            code = "NZD",
            symbol = "NZ$",
            name = "New Zealand Dollar",
            country = "New Zealand",
            region = LocalizationRegion.AUSTRALIA,
            flagEmoji = "🇳🇿",
            decimalPlaces = 2,
            groupingSeparator = ',',
            decimalSeparator = '.',
            quickCashNominals = listOf(10.0, 20.0, 50.0, 100.0),
            quickDiscountPresets = listOf("5" to "NZ$ 5", "10" to "NZ$ 10", "20" to "NZ$ 20")
        )
    )

    val supportedCodes: Set<String> = all.map { it.code }.toSet()

    fun normalizeCode(code: String?): String {
        val cleaned = code?.trim()?.uppercase(Locale.US).orEmpty()
        return if (cleaned in supportedCodes) cleaned else "IDR"
    }

    fun fromCode(code: String?): PosCurrencyOption {
        val normalized = normalizeCode(code)
        return all.firstOrNull { it.code == normalized } ?: all.first()
    }

    fun byRegion(): Map<LocalizationRegion, List<PosCurrencyOption>> =
        LocalizationRegion.entries.associateWith { region ->
            all.filter { it.region == region }
        }
}
