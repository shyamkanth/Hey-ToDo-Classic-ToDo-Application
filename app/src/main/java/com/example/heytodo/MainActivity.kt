package com.example.heytodo

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.heytodo.adapter.TaskAdaptor
import com.example.heytodo.databinding.ActivityMainBinding
import com.example.heytodo.modal.entity.Tasks
import com.example.heytodo.utils.Utils
import com.example.heytodo.viewModelFactory.TaskViewModelFactory
import com.example.heytodo.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdaptor: TaskAdaptor
    private lateinit var taskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initValues()
        initClicks()
    }

    @SuppressLint("SetTextI18n")
    private fun initValues() {
        val app = application as HeyToDo
        val taskViewModelFactory = TaskViewModelFactory(app.taskRepository)
        taskViewModel = ViewModelProvider(this, taskViewModelFactory)[TaskViewModel::class.java]
        getAllTask().observe(this) { taskList ->
            if(taskList.isEmpty()){
                binding.tvPendingTask.text = "Pending Tasks ("+taskList.size+")"
                binding.rvList.visibility = View.GONE
                binding.imgPlaceHolder.visibility = View.VISIBLE
            } else {
                binding.tvPendingTask.text = "Pending Tasks ("+taskList.size+")"
                binding.rvList.visibility = View.VISIBLE
                binding.imgPlaceHolder.visibility = View.GONE
                taskAdaptor = TaskAdaptor(this, taskList, taskViewModel)
                binding.rvList.adapter = taskAdaptor
                binding.rvList.layoutManager = LinearLayoutManager(this)
            }
        }
    }

    private fun initClicks() {
        binding.btnAddNewTask.setOnClickListener {
            addTask()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun addTask(){
        Utils.showAddTaskDialog(this){
            taskViewModel.insertTask(it)
            Utils.showAlertDialogInSameActivity(this, Utils.BsType.SUCCESS, "Task Added Successfully")
            refreshTask()
        }
    }

    private fun getAllTask() : LiveData<List<Tasks>> {
        return taskViewModel.getAllTask()
    }

    @SuppressLint("SetTextI18n")
    fun refreshTask(){
        getAllTask().observe(this) { taskList ->
            if(taskList.isEmpty()){
                binding.tvPendingTask.text = "Pending Tasks ("+taskList.size+")"
                binding.rvList.visibility = View.GONE
                binding.imgPlaceHolder.visibility = View.VISIBLE
            } else {
                binding.tvPendingTask.text = "Pending Tasks ("+taskList.size+")"
                binding.rvList.visibility = View.VISIBLE
                binding.imgPlaceHolder.visibility = View.GONE
                taskAdaptor = TaskAdaptor(this, taskList, taskViewModel)
                binding.rvList.adapter = taskAdaptor
                binding.rvList.layoutManager = LinearLayoutManager(this)
            }
        }
    }
}