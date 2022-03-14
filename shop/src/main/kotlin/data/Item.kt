package data

import org.bson.Document

class Item(
    private val id: Int,
    private val name: String,
    private var price: Double,
    private var currency: Currency
) : DocumentEntity {
    override fun toDocument(): Document {
        return Document(
            mapOf(
                "id" to id,
                "name" to name,
                "price" to price,
                "currency" to currency.name
            )
        )
    }

    fun changeCurrency(other: Currency): Item {
        price = Currency.convert(currency, other, price)
        currency = other
        return this
    }

    companion object {
        fun fromDocument(doc: Document): Item {
            return Item(
                doc.getInteger("id"),
                doc.getString("name"),
                doc.getDouble("price"),
                Currency.valueOf(doc.getString("currency"))
            )
        }
    }
}