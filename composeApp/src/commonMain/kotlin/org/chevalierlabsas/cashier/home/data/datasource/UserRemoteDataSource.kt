package org.chevalierlabsas.cashier.home.data.datasource

import org.chevalierlabsas.cashier.home.data.dto.CreateUserRequest
import org.chevalierlabsas.cashier.home.data.dto.CreateUserResponse

interface UserRemoteDataSource {
    suspend fun createUser(request: CreateUserRequest): Result<CreateUserResponse>
}
