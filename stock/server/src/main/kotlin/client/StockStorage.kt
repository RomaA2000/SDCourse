package client

import kotlin.random.Random

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object StockStorage {
    private val mutex = Mutex()
    private val stocks = mutableListOf<Stock>()

    suspend fun addStock(toFind: String, companyName: String, quantity: Double) = mutex.withLock {
        val stock = stocks.find { it.name == toFind }

        stocks.removeIf { it.name == toFind }

        val price = stock?.price ?: Random.nextDouble(100.0, 1000.0)

        val element = Stock(
            toFind,
            companyName,
            quantity + (stock?.quantity ?: 0.0),
            price
        )

        stocks.add(
            element
        )
    }

    fun stock(toFind: String) = stocks.find { it.name == toFind }?.copy()

    suspend fun addAndGetPrice(toFind: String, quantity: Double): Double = mutex.withLock {
        val existingStock = stocks.find { it.name == toFind }
            ?: throw IllegalArgumentException("Error finding")

        return getPrice(existingStock, quantity, toFind)
    }

    private fun getPrice(existingStock: Stock, quantity: Double, toFind: String): Double {
        when {
            existingStock.quantity < -quantity -> {
                throw IllegalArgumentException("Not enough quantity")
            }
            else -> {
                stocks.removeIf { it.name == toFind }

                val stock = Stock(
                    existingStock.name,
                    existingStock.companyName,
                    quantity + existingStock.quantity,
                    existingStock.price
                )

                stocks.add(
                    stock
                )

                return existingStock.price
            }
        }
    }

    suspend fun market() = mutex.withLock {
        val price = Random.nextDouble(-10.0, 10.0)
        stocks.replaceAll {
            val stock = Stock(
                it.name,
                it.companyName,
                it.quantity,
                it.price + price
            )
            stock
        }
    }
}
