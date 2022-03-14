package database

import com.mongodb.rx.client.MongoClients
import com.mongodb.rx.client.MongoCollection
import org.bson.Document

object DataBase {
    private val database = MongoClients
        .create("mongodb://localhost:27000")
        .getDatabase("webCatalog")

    fun users(): MongoCollection<Document> {
        return database.getCollection("users")
    }

    fun items(): MongoCollection<Document> {
        return database.getCollection("items")
    }
}