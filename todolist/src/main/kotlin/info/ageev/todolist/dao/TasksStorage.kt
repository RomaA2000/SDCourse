package dao

import model.TasksList

class TasksStorage : Storage {
    override fun findListByName(name: String): TasksList? {
        TODO("Not yet implemented")
    }

    override fun createTasksList(name: String) {
        TODO("Not yet implemented")
    }

    override fun deleteTasksList(name: String) {
        TODO("Not yet implemented")
    }

    override fun addTask(listName: String, name: String, content: String): Int {
        TODO("Not yet implemented")
    }

    override fun deleteTask(listName: String, id: Int) {
        TODO("Not yet implemented")
    }
}