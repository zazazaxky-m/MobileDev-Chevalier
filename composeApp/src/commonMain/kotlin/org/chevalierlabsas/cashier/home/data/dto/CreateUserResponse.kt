package org.chevalierlabsas.cashier.home.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserResponse(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("user")
    val user: UserDto
)

@Serializable
data class UserDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("token")
    val token: String,
    @SerialName("updatedAt")
    val updatedAt: String,
    @SerialName("createdAt")
    val createdAt: String
)
