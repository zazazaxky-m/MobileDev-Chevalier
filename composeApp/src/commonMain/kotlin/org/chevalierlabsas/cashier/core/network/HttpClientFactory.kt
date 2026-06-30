package org.chevalierlabsas.cashier.core.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.auth.providers.BearerTokens
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json
import org.chevalierlabsas.cashier.core.preferences.AppPreferences

object HttpClientFactory {
    fun create(
        engine: HttpClientEngine,
        preferences: AppPreferences
    ): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                level = LogLevel.BODY
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Network Log: $message")
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val accessToken = preferences.getToken().firstOrNull()
                        if (accessToken.isNullOrBlank()) {
                            null
                        } else {
                            BearerTokens(
                                accessToken = accessToken,
                                refreshToken = ""
                            )
                        }
                    }
                }
            }
        }
    }
}
