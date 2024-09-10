package com.example.todolist.listener

import com.example.todolist.data.TaskDataModel

interface TaskItemClickListener {
    fun editTaskItem(taskDataModel: TaskDataModel)
    fun completeTaskItem(taskDataModel: TaskDataModel)
}