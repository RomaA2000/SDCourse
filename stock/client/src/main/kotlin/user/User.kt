package user

data class User(
    val name: String,
    val currency: Double = 0.0,
    val stocks: List<StockInformation> = listOf()
)

data class StockInformation(
    val name: String,
    val quantity: Double = 0.0
)

data class StockView(
    val name: String,
    val quantity: Double = 0.0,
    val price: Double
)
