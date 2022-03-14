package data

import org.bson.Document

interface DocumentEntity {
    fun toDocument(): Document
}