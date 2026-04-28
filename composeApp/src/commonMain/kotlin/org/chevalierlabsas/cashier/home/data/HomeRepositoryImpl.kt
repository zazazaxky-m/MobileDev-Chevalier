package org.chevalierlabsas.cashier.home.data

import org.chevalierlabsas.cashier.home.domain.Item
import org.chevalierlabsas.cashier.home.domain.repository.HomeRepository

class HomeRepositoryImpl(private val dataSource: DummyDataSource): HomeRepository {
    
    override suspend fun getItems(): List<Item> {
        return dataSource.getDatas()
    }

    override suspend fun postItem(item: Item): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(id: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun putItem(item: Item): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun postTransaction(): Result<Boolean> {
        TODO("Not yet implemented")
    }
}
