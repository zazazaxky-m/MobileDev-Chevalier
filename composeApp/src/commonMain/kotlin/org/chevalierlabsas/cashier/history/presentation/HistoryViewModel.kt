package org.chevalierlabsas.cashier.history.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.*
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.chevalierlabsas.cashier.history.domain.TransactionHistory
import org.chevalierlabsas.cashier.history.domain.repository.HistoryRepository

import kotlin.time.Clock

class HistoryViewModel(
    private val repository: HistoryRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()

    init {
        loadTransactions()
    }

    @OptIn(kotlin.time.ExperimentalTime::class)
    private fun loadTransactions() {
        viewModelScope.launch {
            val allTransactions = repository.getTransactions()
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date

        val todayList = mutableListOf<TransactionHistory>()
        val weekList = mutableListOf<TransactionHistory>()
        val monthList = mutableListOf<TransactionHistory>()
        val olderList = mutableListOf<TransactionHistory>()

        val months = listOf(
            "Januari", "Februari", "Maret", "April", "Mei", "Juni",
            "Juli", "Agustus", "September", "Oktober", "November", "Desember"
        )

        for (transaction in allTransactions) {
            val transactionDate = parseDate(transaction.date, months)
            if (transactionDate != null) {
                val daysDiff = today.toEpochDays() - transactionDate.toEpochDays()
                when {
                    daysDiff == 0L -> todayList.add(transaction)
                    daysDiff in 1L..6L -> weekList.add(transaction)
                    daysDiff in 7L..29L -> monthList.add(transaction)
                    else -> olderList.add(transaction)
                }
            } else {
                olderList.add(transaction)
            }
        }

        _state.value = HistoryState(
            todayTransactions = todayList,
            thisWeekTransactions = weekList,
            thisMonthTransactions = monthList,
            olderTransactions = olderList
        )
        }
    }

    private fun parseDate(dateStr: String, months: List<String>): LocalDate? {
        return try {
            val parts = dateStr.split(" ")
            val day = parts[0].toInt()
            val monthIndex = months.indexOf(parts[1]) + 1
            val year = parts[2].toInt()
            LocalDate(year, monthIndex, day)
        } catch (_: Exception) {
            null
        }
    }
}
