package org.chevalierlabsas.cashier.home.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import org.chevalierlabsas.cashier.core.network.BASE_URL
import org.chevalierlabsas.cashier.core.network.V1
import org.chevalierlabsas.cashier.home.data.dto.CreateUserRequest
import org.chevalierlabsas.cashier.home.data.dto.CreateUserResponse

class UserRemoteDataSourceImpl(
    private val client: HttpClient
) : UserRemoteDataSource {
    override suspend fun createUser(request: CreateUserRequest): Result<CreateUserResponse> {
        val response = client.post(urlString = "$BASE_URL/$V1/user") {
            setBody(request)
        }
        return when (response.status.value) {
            201 -> Result.success(response.body())
            else -> Result.failure(Exception(response.status.description))
        }
    }
}
