package database

import com.mongodb.rx.client.MongoCollection
import com.mongodb.client.model.Filters
import data.Currency
import data.Item
import data.User
import org.bson.Document
import rx.Observable

class Reactive {
    private val users: MongoCollection<Document> = DataBase.users()
    private val items: MongoCollection<Document> = DataBase.items()

    private val name = "id"

    private fun toCollection(
        doc: Document,
        collection: MongoCollection<Document>
    ): Observable<Boolean> {
        val eq = Filters.eq(
            name,
            doc.getInteger(name)
        )
        return collection.find(
            eq
        ).toObservable()
            .singleOrDefault(null)
            .flatMap { foundDoc ->
                when (foundDoc) {
                    null -> {
                        val map = collection.insertOne(doc)
                            .asObservable()
                            .isEmpty
                            .map { !it }
                        map
                    }
                    else -> Observable.just(false)
                }
            }
    }

    fun user(user: User): Observable<Boolean> = toCollection(user.toDocument(), users)

    fun item(item: Item): Observable<Boolean> = toCollection(item.toDocument(), items)

    fun itemsById(id: Int): Observable<Item> {
        val eq = Filters.eq(name, id)
        return users
            .find(
                eq
            ).toObservable()
            .map { val type = "currency"
                Currency.valueOf(it.getString(type)) }
            .flatMap { currency ->
                val map = items
                    .find()
                    .toObservable()
                    .map {
                        Item.fromDocument(it).changeCurrency(currency)
                    }
                map
            }
    }
}