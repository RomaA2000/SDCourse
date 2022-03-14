package server

import database.Reactive
import io.netty.handler.codec.http.HttpResponseStatus
import data.Currency
import data.Item
import data.User

class Controller(private val reactive: Reactive) {

    private fun get(name: String, value: Map<String, List<String>>): String {
        return (value[name] ?: error("$name not found "))[0]
    }

    private fun user(value: Map<String, List<String>>): Result {
        val user = User(
            get("id", value).toInt(),
            get("name", value),
            Currency.valueOf(get("currency", value))
        )
        return Result(
            HttpResponseStatus.OK,
            reactive.user(
                user
            ).map { toString() }
        )
    }

    private fun item(value: Map<String, List<String>>): Result {
        val item = Item(
            get("id", value).toInt(),
            get("name", value),
            get("price", value).toDouble(),
            Currency.valueOf(get("currency", value))
        )
        return Result(
            HttpResponseStatus.OK,
            reactive.item(
                item
            ).map { toString() }
        )
    }

    fun process(path: String, value: Map<String, List<String>>): Result = when (path) {
        "add-user" -> user(value)
        "add-item" -> item(value)
        "get-items" -> items(value)
        else -> Result.error("no such path")
    }

    private fun items(value: Map<String, List<String>>): Result {
        return Result(
            HttpResponseStatus.OK,
            reactive.itemsById(get("id", value).toInt()).map { toString() }
        )
    }
}