package org.chevalierlabsas.cashier.home.data

import org.chevalierlabsas.cashier.home.domain.Item

interface DummyDataSource {
    fun getDatas(): List<Item>
}

class DummyDataSourceImpl : DummyDataSource {
    override fun getDatas(): List<Item> = listOf(
        Item(
            id = 1,
            userId = "1",
            name = "Item 1",
            price = 100000.0
        ),
        Item(
            id = 2,
            userId = "1",
            name = "Item 2",
            price = 200000.0
        ),
        Item(
            id = 3,
            userId = "1",
            name = "Item 3",
            price = 300000.0
        ),
        Item(
            id = 4,
            userId = "1",
            name = "Item 4",
            price = 310000.0
        ),
        Item(
            id = 5,
            userId = "1",
            name = "Item 5",
            price = 700000.0
        ),
        Item(
            id = 6,
            userId = "1",
            name = "Item 6",
            price = 250000.0
        ),
        Item(
            id = 7,
            userId = "1",
            name = "Item 7",
            price = 180000.0
        ),
        Item(
            id = 8,
            userId = "1",
            name = "Item 8",
            price = 50000.0
        ),
        Item(
            id = 9,
            userId = "1",
            name = "Item 9",
            price = 19000.0
        ),
        Item(
            id = 10,
            userId = "1",
            name = "Item 10",
            price = 5000.0
        ),
    )
}