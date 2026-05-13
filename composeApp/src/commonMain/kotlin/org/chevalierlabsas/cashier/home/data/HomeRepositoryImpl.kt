package org.chevalierlabsas.cashier.home.data

import org.chevalierlabsas.cashier.home.domain.Item
import org.chevalierlabsas.cashier.home.domain.repository.HomeRepository

import org.chevalierlabsas.cashier.home.data.datasource.UserLocalDataSource
import org.chevalierlabsas.cashier.home.data.datasource.UserRemoteDataSource
import org.chevalierlabsas.cashier.home.data.datasource.ItemRemoteDataSource
import org.chevalierlabsas.cashier.home.data.datasource.getDeviceName
import org.chevalierlabsas.cashier.home.data.dto.CreateUserRequest
import org.chevalierlabsas.cashier.home.data.dto.PostItemRequest
import kotlinx.coroutines.flow.Flow

class HomeRepositoryImpl(
    private val dataSource: DummyDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val itemRemoteDataSource: ItemRemoteDataSource
): HomeRepository {
    
    override suspend fun getItems(userId: String): Result<List<Item>> {
        val result = itemRemoteDataSource.getItems(userId.replace(" ", "_"))
        
        return if (result.isSuccess) {
            try {
                val itemsResponse = result.getOrThrow()
                Result.success(itemsResponse.items.map { it.toDomain() })
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown remote error."))
        }
    }

    override suspend fun postItem(item: Item): Result<Boolean> {
        val request = PostItemRequest(
            price = item.price,
            name = item.name,
            userId = item.userId.replace(" ", "_")
        )
        val result = itemRemoteDataSource.postItem(request)
        return if (result.isSuccess) {
            Result.success(true)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error."))
        }
    }

    override suspend fun deleteItem(id: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun putItem(item: Item): Result<Boolean> {
        val request = PostItemRequest(
            price = item.price,
            name = item.name,
            userId = item.userId.replace(" ", "_")
        )
        val result = itemRemoteDataSource.updateItem(item.id, request)
        return if (result.isSuccess) {
            Result.success(true)
        } else {
            Result.failure(result.exceptionOrNull() ?: Exception("Unknown error."))
        }
    }

    override suspend fun postTransaction(): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun createUser() {
        val allowedChars = ('0'..'9')
        val randomIdentifier = (1..10).map { allowedChars.random() }.joinToString("")
        val userIdentifier = "${getDeviceName()}_$randomIdentifier"
        userLocalDataSource.saveUser(userIdentifier)
        saveUser(userIdentifier)
    }

    override suspend fun saveUser(user: String): Result<Boolean> {
        val request = CreateUserRequest(user)
        val result = userRemoteDataSource.createUser(request)
        return if (result.isSuccess) {
            Result.success(true)
        } else Result.failure(result.exceptionOrNull() ?: Exception("Unknown error."))
    }

    override fun getUser(): Flow<String> {
        return userLocalDataSource.getUser()
    }
}
