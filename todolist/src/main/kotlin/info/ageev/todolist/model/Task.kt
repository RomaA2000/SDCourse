package model

enum class Status {
    OPEN,
    CLOSED
}

data class Task(val id: Int, val name: String, val content: String, val status: Status) {
    val isClosed get() = status == Status.CLOSED
}