package user

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private const val USER_DOES_NOT_EXIST = "User does not exist"

object UserStorage {
    private val mutex = Mutex()
    private val users = mutableListOf<User>()

    suspend fun funds(name: String, delta: Double) = mutex.withLock {
        val user = users.find { it.name == name }
            ?: throw IllegalArgumentException(USER_DOES_NOT_EXIST)

        users.removeIf { it.name == name }

        val element = User(
            user.name,
            user.currency + delta,
            user.stocks
        )

        users.add(
            element
        )
    }

    fun findUser(name: String) = users.find { it.name == name }
        ?: throw IllegalArgumentException(USER_DOES_NOT_EXIST)

    suspend fun stock(stockName: String, toFind: String, delta: Double) = mutex.withLock {
        val user = users.find { it.name == stockName }
            ?: throw IllegalArgumentException(USER_DOES_NOT_EXIST)

        val quantity = user.stocks.find { it.name == toFind }?.quantity ?: 0.0

        checkQuantity(delta, quantity, stockName, toFind, user)
    }

    suspend fun user(name: String) = mutex.withLock {
        when {
            users.find { it.name == name } != null -> {
                throw IllegalArgumentException("Already have this user")
            }
            else -> {
                val user = User(name)

                users.add(user)
            }
        }
    }

    private fun checkQuantity(
        delta: Double,
        quantity: Double,
        stockName: String,
        toFind: String,
        user: User
    ) = when {
        -delta > quantity -> {
            throw IllegalArgumentException("Insufficient stocks")
        }
        else -> {
            users.removeIf { it.name == stockName }

            val stock = StockInformation(toFind, quantity + delta)

            val element = user.copy(
                stocks = user.stocks.filter { it.name != toFind }
                    .plus(stock)
            )

            users.add(
                element
            )
        }
    }
}
