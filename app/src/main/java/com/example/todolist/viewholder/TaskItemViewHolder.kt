package com.example.todolist.viewholder

import android.content.Context
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.data.TaskDataModel
import com.example.todolist.databinding.TaskItemBinding
import com.example.todolist.listener.TaskItemClickListener
import java.time.format.DateTimeFormatter

class TaskItemViewHolder(
    private var context: Context,
    private var binding: TaskItemBinding,
    private var clickListener: TaskItemClickListener
): RecyclerView.ViewHolder(binding.root){
    private val timeFormat = DateTimeFormatter.ofPattern("HH:mm")

    fun bindingTaskItem(taskData: TaskDataModel) {
        binding.tvName.text = taskData.name

        if (taskData.isCompleted()){
            binding.tvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            binding.tvDueTime.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
        binding.btnComplete.apply {
            setImageResource(taskData.imageResource())
            setColorFilter(taskData.imageColor(context))
            setOnClickListener {
                clickListener.completeTaskItem(taskData)
            }
        }
        binding.cvTaskContainer.setOnClickListener {
            clickListener.editTaskItem(taskData)
        }

        if (taskData.dueTime != null)
            binding.tvDueTime.text = timeFormat.format(taskData.dueTime)
        else
            binding.tvDueTime.text = ""
    }
}