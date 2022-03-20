package getter

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

import io.ktor.http.HttpStatusCode

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

import io.ktor.routing.get
import io.ktor.routing.routing

import io.ktor.application.call
import io.ktor.response.respond

import client.Client
import user.UserStorage

private const val USER_DOES_NOT_EXIST = "User does not exist"

class Getter : KoinComponent {
    private val client by inject<Client>()

    private fun makeErrorMessage(name: String) : String {
        return "Invalid $name parameter"
    }

    val server = embeddedServer(Netty, port = 8081) {
        routing {
            get("/user/register/{name}") {

                UserStorage.user(call.parameters["name"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, makeErrorMessage("name")))

                call.respond("OK")
            }

            get("/user/{name}/refill/{delta}") {
                val delta = call.parameters["delta"]?.toDoubleOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, makeErrorMessage("delta"))

                val name = call.parameters["name"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, makeErrorMessage("name"))

                when {
                    delta < 0 -> {
                        return@get call.respond(HttpStatusCode.Forbidden, "Negative delta")
                    }
                    else -> {
                        UserStorage.funds(name, delta)

                        call.respond("OK")
                    }
                }
            }

            get("/user/{name}/stocks") {
                val name = call.parameters["name"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, makeErrorMessage("name"))

                try {
                    val joinToString = client.stocks(name)
                        .map { view ->
                            val countPrice = "$${view.name}: ${view.quantity} * $${view.price}"
                            countPrice
                        }
                        .joinToString("\n")
                    joinToString
                        .ifEmpty { "Empty" }
                        .let { call.respond(it) }
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, USER_DOES_NOT_EXIST)
                }
            }

            get("/user/{name}/currency") {
                val name = call.parameters["name"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, makeErrorMessage("name"))

                try {
                    call.respond("Now status: ${client.currency(name)}")
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.NotFound, USER_DOES_NOT_EXIST)
                }
            }

            get("/user/{name}/buy/{toFind}/{delta}") {
                val name = call.parameters["name"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, makeErrorMessage("name"))

                val toFind = call.parameters["toFind"]
                    ?: return@get call.respond(HttpStatusCode.BadRequest, makeErrorMessage("small name"))

                val delta = call.parameters["delta"]?.toDoubleOrNull()
                    ?: return@get call.respond(HttpStatusCode.BadRequest, makeErrorMessage("delta"))

                try {
                    client.stock(name, toFind, delta)
                    call.respond("OK")
                } catch (e: IllegalArgumentException) {
                    call.respond(HttpStatusCode.BadRequest, e.message ?: "Null as message")
                }
            }
        }
    }
}
