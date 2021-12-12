package dao

import model.TasksList

interface Storage {
    fun findListByName(name: String): TasksList?

    fun createTasksList(name: String)
    fun deleteTasksList(name: String)

    fun addTask(listName: String, name: String, content: String): Int
    fun deleteTask(listName: String, id: Int)
}