package org.chevalierlabsas.cashier.history.data

import kotlinx.datetime.*
import org.chevalierlabsas.cashier.history.domain.TransactionHistory
import kotlin.time.ExperimentalTime

interface DummyDataSource {
    fun getData(): List<TransactionHistory>
}

class DummyDataSourceImpl : DummyDataSource {

    @OptIn(ExperimentalTime::class)
    override fun getData(): List<TransactionHistory> {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

        val todayStr = formatDate(today)
        val threeDaysAgoStr = formatDate(today.minus(3, DateTimeUnit.DAY))
        val fiveDaysAgoStr = formatDate(today.minus(5, DateTimeUnit.DAY))
        val tenDaysAgoStr = formatDate(today.minus(10, DateTimeUnit.DAY))
        val twentyDaysAgoStr = formatDate(today.minus(20, DateTimeUnit.DAY))
        val thirtyFiveDaysAgoStr = formatDate(today.minus(35, DateTimeUnit.DAY))

        return listOf(
            // Hari ini
            TransactionHistory(
                totalPrice = 250_000_000_000.0,
                totalItems = 10,
                date = todayStr
            ),
            TransactionHistory(
                totalPrice = 250_000_000_000.0,
                totalItems = 10,
                date = todayStr
            ),
            // Minggu ini
            TransactionHistory(
                totalPrice = 250_000_000_000.0,
                totalItems = 10,
                date = threeDaysAgoStr
            ),
            TransactionHistory(
                totalPrice = 250_000_000_000.0,
                totalItems = 10,
                date = fiveDaysAgoStr
            ),
            // Bulan ini
            TransactionHistory(
                totalPrice = 250_000_000_000.0,
                totalItems = 10,
                date = tenDaysAgoStr
            ),
            TransactionHistory(
                totalPrice = 250_000_000_000.0,
                totalItems = 10,
                date = twentyDaysAgoStr
            ),
            // Lebih lama
            TransactionHistory(
                totalPrice = 250_000_000_000.0,
                totalItems = 10,
                date = thirtyFiveDaysAgoStr
            ),
        )
    }

    @Suppress("DEPRECATION")
    private fun formatDate(date: LocalDate): String {
        val months = listOf(
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )
        return "${date.dayOfMonth} ${months[date.monthNumber - 1]} ${date.year}"
    }
}
