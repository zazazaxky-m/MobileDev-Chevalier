package org.chevalierlabsas.cashier

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform