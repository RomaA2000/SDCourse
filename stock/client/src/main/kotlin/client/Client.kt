package client

import io.ktor.http.Parameters

import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import server.Server
import user.UserStorage
import user.StockView

class Client : KoinComponent {
    private val serverProvider by inject<Server>()
    private val client = HttpClient(CIO)

    suspend fun stocks(name: String): List<StockView> =
        UserStorage.findUser(name)
            .stocks
            .map {
                val view = StockView(
                    it.name,
                    it.quantity,
                    price(it.name)
                )
                view
            }

    suspend fun currency(name: String): Double {
        val user = UserStorage.findUser(name)

        val sumOfStocks = stocks(name).sumOf { view ->
            view.quantity * view.price
        }
        return user.currency + sumOfStocks
    }

    suspend fun stock(name: String, toFind: String, quantity: Double) {
        UserStorage.stock(name, toFind, quantity)

        val url = "${serverProvider.url()}/stock/$toFind/order"

        val formParameters = Parameters.build {
            val value = quantity.toString()
            append("quantity", value)
        }

        val form = client.submitForm<String>(
            url = url,
            formParameters = formParameters
        )

        val response: String = form

        checkResponse(response, name, toFind, quantity)
    }

    private suspend fun checkResponse(
        response: String,
        name: String,
        toFind: String,
        quantity: Double
    ) {
        when {
            !response.startsWith("OK") -> {
                UserStorage.stock(name, toFind, -quantity)
                throw IllegalArgumentException(response)
            }
            else -> {
                val last = response.trim().split(" ").last()
                val delta = -quantity * last.toDouble()
                UserStorage.funds(name, delta)
            }
        }
    }

    private suspend fun price(toFind: String): Double {
        val urlString: String = "${serverProvider.url()}/stock/$toFind"
        val stockInfo: String = client.get(urlString) {}
        val list = stockInfo.trim().split(" ")
        return list.last().toDouble()
    }
}