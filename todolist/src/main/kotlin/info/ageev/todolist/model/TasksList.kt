package model

class TasksList(val name: String) {
    private val list: MutableList<Task> = ArrayList()

    fun getTasks(): MutableList<Task> = list

    fun deleteTask(taskId: Int) = list.removeIf{task -> taskId == task.id}

    fun addTask(task: Task) = list.add(task)
}