package org.chevalierlabsas.cashier.history.data.dto

import kotlinx.serialization.Serializable
import org.chevalierlabsas.cashier.history.domain.TransactionHistory
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Serializable
data class Histories(
    val id: Int? = null,
    val total: Double? = null,
    val items: Int? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null
) {
    fun toDomain(): TransactionHistory {
        var dateStr = createdAt ?: ""
        try {
            val instant = Instant.parse(dateStr)
            val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
            val months = listOf(
                "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember"
            )
            dateStr = "${date.dayOfMonth} ${months[date.monthNumber - 1]} ${date.year}"
        } catch (e: Exception) {
            // ignore and use raw dateStr if parsing fails
        }
        
        return TransactionHistory(
            totalPrice = total ?: 0.0,
            totalItems = items ?: 0,
            date = dateStr
        )
    }
}
