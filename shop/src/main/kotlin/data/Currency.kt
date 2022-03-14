package data

enum class Currency(val norm: Double) {
    EUR(135.7),
    RUB(1.0),
    USD(120.0);

    companion object {
        fun convert(first: Currency, second: Currency, count: Double): Double {
            return count * (first.norm / second.norm)
        }
    }
}

