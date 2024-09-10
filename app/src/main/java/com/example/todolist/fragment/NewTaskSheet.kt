package com.example.todolist.fragment

import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todolist.data.TaskDataModel
import com.example.todolist.databinding.FragmentNewTaskSheetBinding
import com.example.todolist.viewmodel.TaskViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalTime

class NewTaskSheet(var taskDataModel: TaskDataModel?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (taskDataModel !== null) {
            binding.tvTaskTitle.text = "Edit Task"
            val editable = Editable.Factory.getInstance()
            binding.tietName.text = editable.newEditable(taskDataModel!!.name)
            binding.tietDesc.text = editable.newEditable(taskDataModel!!.desc)
            if (taskDataModel!!.dueTime != null) {
                dueTime = taskDataModel!!.dueTime
                updateTimeButtonText()
            }
        } else {
            binding.tvTaskTitle.text = "New Task"
        }
        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]
        binding.btnSave.setOnClickListener {
            saveAction()
        }
        binding.btnTimePicker.setOnClickListener {
            openTimePicker()
        }
    }

    private fun openTimePicker() {
        if (dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }

    private fun updateTimeButtonText() {
        binding.btnTimePicker.text = String.format("%02d:%02d", dueTime?.hour, dueTime?.minute)
    }

    private fun saveAction() {
        val name = binding.tietName.text.toString()
        val desc = binding.tietDesc.text.toString()
        if (taskDataModel == null) {
            val newTask = TaskDataModel(name, desc, dueTime, null)
            taskViewModel.addTaskData(newTask)
        } else {
            taskViewModel.updateTaskData(taskDataModel!!.id, name, desc, dueTime)
        }
        binding.tietName.setText("")
        binding.tietDesc.setText("")
        dismiss()
    }
}