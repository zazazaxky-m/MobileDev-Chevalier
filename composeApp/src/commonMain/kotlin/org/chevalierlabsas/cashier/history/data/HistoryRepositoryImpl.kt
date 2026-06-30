package org.chevalierlabsas.cashier.history.data

import org.chevalierlabsas.cashier.history.domain.TransactionHistory
import org.chevalierlabsas.cashier.history.domain.repository.HistoryRepository

import org.chevalierlabsas.cashier.history.data.datasource.HistoryRemoteDataSource
import org.chevalierlabsas.cashier.home.data.datasource.UserLocalDataSource
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class HistoryRepositoryImpl(
    private val remoteDataSource: HistoryRemoteDataSource,
    private val localUserDataSource: UserLocalDataSource
) : HistoryRepository {
    override suspend fun getTransactions(): List<TransactionHistory> {
        return try {
            val userId = localUserDataSource.getUser().first().replace(" ", "_")
            if (userId.isBlank()) return emptyList()

            val response = remoteDataSource.getHistory(userId)
            val histories = response.getOrNull()?.histories ?: emptyList()
            
            val months = listOf(
                "Januari", "Februari", "Maret", "April", "Mei", "Juni",
                "Juli", "Agustus", "September", "Oktober", "November", "Desember"
            )

            histories.map { dto ->
                var dateStr = dto.createdAt ?: ""
                try {
                    val instant = Instant.parse(dateStr)
                    val date = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
                    dateStr = "${date.dayOfMonth} ${months[date.monthNumber - 1]} ${date.year}"
                } catch (e: Exception) {
                    // ignore and use raw dateStr if parsing fails
                }
                
                TransactionHistory(
                    totalPrice = dto.total,
                    totalItems = dto.items,
                    date = dateStr
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
