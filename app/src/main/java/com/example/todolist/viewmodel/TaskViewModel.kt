package com.example.todolist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.TaskDataModel
import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskViewModel: ViewModel() {
    var taskData = MutableLiveData<MutableList<TaskDataModel>>()
    init {
        taskData.value = mutableListOf()
    }

    fun addTaskData(newTask: TaskDataModel){
        val list = taskData.value
        list!!.add(newTask)
        taskData.postValue(list)
    }

    fun updateTaskData(id: UUID, name: String, desc: String, dueTime: LocalTime?){
        val list = taskData.value
        val task = list!!.find { it.id == id }!!
        task.name = name
        task.desc = desc
        task.dueTime = dueTime
        taskData.postValue(list)
    }

    fun setCompleted(taskItem: TaskDataModel){
        val list = taskData.value
        val task = list!!.find { it.id == taskItem.id }!!
        if (task.completeDate == null)
            task.completeDate = LocalDate.now()
        taskData.postValue(list)
    }
}