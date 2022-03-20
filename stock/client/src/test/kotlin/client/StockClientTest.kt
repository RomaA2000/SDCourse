package client

import kotlinx.coroutines.runBlocking
import kotlin.test.assertEquals

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import io.ktor.client.request.*
import io.ktor.client.request.forms.*

import io.ktor.http.Parameters

import BaseTest

import server.Server
import user.StockInformation
import user.UserStorage

class StockClientTest : BaseTest() {
    private val serverProvider: Server by lazy { TestApplication.get() }
    private val stockClient: Client by lazy { TestApplication.get() }

    @BeforeEach
    fun prepare(): Unit = runBlocking {
        listOf(
            Pair("MCRS", "Microsoft"),
            Pair("NVDI", "Nvidia corporation"),
            Pair("GOOG", "Alphabet company")
        ).forEach {
            client.submitForm(
                url = "${serverProvider.url()}/buy",
                formParameters = Parameters.build {
                    append("name", it.second)
                    append("toFind", it.first)
                    append("delta", "1000.0")
                }
            )
        }
    }

    @Test
    fun emptyTest(): Unit = runBlocking {
        val name = "1"

        UserStorage.user(name)

        assertEquals(listOf(), stockClient.stocks(name))
    }

    @Test
    fun emptyCurrency(): Unit = runBlocking {
        val name = "2"

        UserStorage.user(name)

        val currency = stockClient.currency(name)
        assertEquals(0.0, currency)
    }

    @Test
    fun buyingStock(): Unit = runBlocking {
        val name = "3"
        val toFind = "NVDI"

        UserStorage.user(name)
        UserStorage.funds(name, 1000.0)

        stockClient.stock(name, toFind, 1.0)

        assertEquals(listOf(StockInformation(toFind, 1.0)), UserStorage.findUser(name).stocks)
    }

    @Test
    fun moneyBuying(): Unit = runBlocking {
        val name = "4"
        val toFind = "MCRS"

        UserStorage.user(name)
        UserStorage.funds(name, 1000.0)

        val price = getPrice(toFind)
        stockClient.stock(name, toFind, 1.0)

        val currency = UserStorage.findUser(name).currency
        assertEquals(1000.0 - price, currency, 2.0)
    }


    private suspend fun getPrice(toFind: String): Double {
        val urlString = "${serverProvider.url()}/stock/$toFind"
        val stockInfo: String = client.get(urlString) {}
        return stockInfo.trim().split(" ").last().toDouble()
    }
}
