package org.chevalierlabsas.cashier.history.data

import org.chevalierlabsas.cashier.history.domain.TransactionHistory
import org.chevalierlabsas.cashier.history.domain.repository.HistoryRepository
import org.chevalierlabsas.cashier.history.data.datasource.HistoryRemoteDataSource
import org.chevalierlabsas.cashier.home.data.datasource.UserLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryRepositoryImpl(
    private val remoteDataSource: HistoryRemoteDataSource,
    private val localUserDataSource: UserLocalDataSource
) : HistoryRepository {
    
    override suspend fun getHistoryItems(userId: String): Result<List<TransactionHistory>> {
        return try {
            val response = remoteDataSource.getHistory(userId)
            if (response.isSuccess) {
                val histories = response.getOrNull()?.histories ?: emptyList()
                Result.success(histories.map { it.toDomain() })
            } else {
                Result.failure(response.exceptionOrNull() ?: Exception("Failed to fetch history"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUserId(): Flow<String> {
        return localUserDataSource.getUser().map { it.replace(" ", "_") }
    }
}
