package org.chevalierlabsas.cashier.home.data.datasource

actual fun getDeviceName(): String {
    return System.getProperty("os.name") ?: "Desktop"
}
