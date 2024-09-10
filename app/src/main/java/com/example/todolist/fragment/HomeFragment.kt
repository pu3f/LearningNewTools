package com.example.todolist.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.adapter.TaskItemAdapter
import com.example.todolist.data.TaskDataModel
import com.example.todolist.listener.TaskItemClickListener
import com.example.todolist.viewmodel.TaskViewModel
import com.example.todolist.databinding.FragmentHomeBinding


class HomeFragment : Fragment(), TaskItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskViewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        binding.efaBtnNewTask.setOnClickListener {
            NewTaskSheet(null).show(parentFragmentManager, "newTaskTag")
        }

        val deviceId: String = arguments?.getString(DEVICE_ID).toString()

        binding.tvIdfaVal.text = deviceId

        setRecyclerView()
    }

    private fun setRecyclerView() {
        taskViewModel.taskData.observe(requireActivity()) {
            binding.rvToDoList.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = TaskItemAdapter(it, this@HomeFragment)
            }
        }
    }

    override fun editTaskItem(taskDataModel: TaskDataModel) {
        NewTaskSheet(taskDataModel).show(parentFragmentManager, "newTaskTag")
    }

    override fun completeTaskItem(taskDataModel: TaskDataModel) {
        taskViewModel.setCompleted(taskDataModel)
    }

    companion object {
        const val DEVICE_ID = "deviceId"


        fun newInstance(imei: String): HomeFragment {
            val fragment = HomeFragment()

            val bundle = Bundle().apply {
                putString(DEVICE_ID, imei)
            }

            fragment.arguments = bundle

            return fragment
        }
    }
}