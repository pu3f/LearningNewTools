package com.example.todolist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.TaskDataModel
import com.example.todolist.listener.TaskItemClickListener
import com.example.todolist.viewholder.TaskItemViewHolder
import com.example.todolist.databinding.TaskItemBinding

class TaskItemAdapter(
    private var taskData: List<TaskDataModel>,
    private var clickListener: TaskItemClickListener
): RecyclerView.Adapter<TaskItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = TaskItemBinding.inflate(from, parent, false)
        return TaskItemViewHolder(parent.context, binding, clickListener)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.bindingTaskItem(taskData[position])
    }

    override fun getItemCount(): Int = taskData.size
}