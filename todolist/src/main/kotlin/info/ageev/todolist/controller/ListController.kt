package controller

import org.springframework.ui.Model

class ListController(private val taskListStorage: TasksStorage) {
    private fun updateTaskListView(map: Model, name: String) {
        map.addAttribute("taskList", taskListStorage.findListByName(name))
    }
}