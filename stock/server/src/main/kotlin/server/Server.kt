package server

import io.ktor.application.call
import io.ktor.http.HttpStatusCode

import io.ktor.request.receiveParameters
import io.ktor.response.respond

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing

import kotlinx.coroutines.runBlocking
import client.StockStorage

private fun makeErrorMessage(name: String) : String {
    return "Invalid $name parameter"
}

fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/stock/{toFind}") {
                val toFind = call.parameters["toFind"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, makeErrorMessage("small name"))

                val stock = StockStorage.stock(toFind)
                    ?: return@get call.respond(HttpStatusCode.NotFound, "Not find")

                val data = """
                    $$toFind: ${stock.companyName}
                    Available: ${stock.quantity}
                    Price: ${stock.price}
                """
                call.respond(
                    data.trimIndent()
                )
            }

            post("/buy") {
                val params = call.receiveParameters()

                val toFind = params["toFind"]
                    ?: return@post call.respond(HttpStatusCode.BadRequest, makeErrorMessage("small name"))

                val companyName = params["company"]
                    ?: return@post call.respond(HttpStatusCode.BadRequest, makeErrorMessage("company"))

                val quantity = params["quantity"]?.toDoubleOrNull()
                    ?: return@post call.respond(HttpStatusCode.BadRequest, makeErrorMessage("quantity"))

                StockStorage.addStock(toFind, companyName, quantity)

                call.respond("OK")
            }

            post("/stock/order/{toFind}") {
                val toFind = call.parameters["toFind"]
                    ?: return@post call.respond(HttpStatusCode.BadRequest, makeErrorMessage("small name"))

                val params = call.receiveParameters()

                val quantity = params["quantity"]?.toDoubleOrNull()
                    ?: return@post call.respond(HttpStatusCode.BadRequest, makeErrorMessage("quantity"))

                try {
                    val price = StockStorage.addAndGetPrice(toFind, quantity)
                    call.respond("OK $price")
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Null as message")
                }
            }
        }
    }.start()

    while (true) {
        Thread.sleep(1000)
        runBlocking { StockStorage.market() }
    }
}
