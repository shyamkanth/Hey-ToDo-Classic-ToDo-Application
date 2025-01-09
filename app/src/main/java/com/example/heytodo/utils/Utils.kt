package com.example.heytodo.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import com.example.heytodo.R
import com.example.heytodo.databinding.AddTaskLayoutBinding
import com.example.heytodo.databinding.CustomDialogLayoutBinding
import com.example.heytodo.modal.entity.Tasks

object Utils {

    enum class BsType {
        INFO,
        SUCCESS,
        ERROR
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    fun showAlertDialogInSameActivity(
        currentActivity: Activity,
        dialogType: BsType,
        message: String,
    ) {
        val binding = CustomDialogLayoutBinding.inflate(currentActivity.layoutInflater)
        when (dialogType) {
            BsType.SUCCESS -> {
                binding.imageViewSuccess.setImageResource(R.drawable.ic_success)
                binding.textViewMessage.setTextColor(currentActivity.getColor(R.color.primary))
                binding.buttonOk.setBackgroundColor(currentActivity.getColor(R.color.primary))
                binding.textViewMessage.text = "Success!"
            }

            BsType.INFO -> {
                binding.imageViewSuccess.setImageResource(R.drawable.ic_info)
                binding.textViewMessage.setTextColor(currentActivity.getColor(R.color.primary_info))
                binding.buttonOk.setBackgroundColor(currentActivity.getColor(R.color.primary_info))
                binding.textViewMessage.text = "Info!"
            }

            BsType.ERROR -> {
                binding.imageViewSuccess.setImageResource(R.drawable.ic_error)
                binding.textViewMessage.setTextColor(currentActivity.getColor(R.color.primary_error))
                binding.buttonOk.setBackgroundColor(currentActivity.getColor(R.color.primary_error))
                binding.textViewMessage.text = "Error!"
            }
        }
        binding.textViewDescription.text = message
        binding.buttonOk.text = "OK"
        val alertDialog = AlertDialog.Builder(currentActivity)
            .setView(binding.root)
            .setCancelable(false)
            .create()

        binding.buttonOk.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    fun showAlertDialog(
        currentActivity: Activity,
        dialogType: BsType,
        message: String,
        targetActivity: Class<*>,
        finishActivity: Boolean = false
    ) {
        val binding = CustomDialogLayoutBinding.inflate(currentActivity.layoutInflater)
        when (dialogType) {
            BsType.SUCCESS -> {
                binding.imageViewSuccess.setImageResource(R.drawable.ic_success)
                binding.textViewMessage.setTextColor(currentActivity.getColor(R.color.primary))
                binding.buttonOk.setBackgroundColor(currentActivity.getColor(R.color.primary))
                binding.textViewMessage.text = "Success!"
            }

            BsType.INFO -> {
                binding.imageViewSuccess.setImageResource(R.drawable.ic_info)
                binding.textViewMessage.setTextColor(currentActivity.getColor(R.color.primary_info))
                binding.buttonOk.setBackgroundColor(currentActivity.getColor(R.color.primary_info))
                binding.textViewMessage.text = "Info!"
            }

            BsType.ERROR -> {
                binding.imageViewSuccess.setImageResource(R.drawable.ic_error)
                binding.textViewMessage.setTextColor(currentActivity.getColor(R.color.primary_error))
                binding.buttonOk.setBackgroundColor(currentActivity.getColor(R.color.primary_error))
                binding.textViewMessage.text = "Error!"
            }
        }
        binding.textViewDescription.text = message
        binding.buttonOk.text = "OK"
        val alertDialog = AlertDialog.Builder(currentActivity)
            .setView(binding.root)
            .setCancelable(false)
            .create()

        binding.buttonOk.setOnClickListener {
            alertDialog.dismiss()
            val intent = Intent(currentActivity, targetActivity).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            currentActivity.startActivity(intent)
            if (finishActivity) {
                currentActivity.finish()
            }
        }

        alertDialog.show()
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    fun showDeleteDialog(
        currentActivity: Activity,
        onDelete: () -> Unit
    ) {
        val binding = CustomDialogLayoutBinding.inflate(currentActivity.layoutInflater)
        binding.imageViewSuccess.setImageResource(R.drawable.ic_error)
        binding.textViewMessage.setTextColor(currentActivity.getColor(R.color.primary_error))
        binding.buttonOk.setBackgroundColor(currentActivity.getColor(R.color.primary_error))
        binding.textViewMessage.text = "DELETE"
        binding.textViewDescription.text = "This action will delete this information and all its associated data. Are you sure you want to delete?"
        binding.buttonOk.visibility = View.GONE
        binding.buttonCancel.visibility = View.VISIBLE
        binding.buttonCancel.text = "CANCEL"
        binding.buttonDelete.visibility = View.VISIBLE
        binding.buttonDelete.text = "DELETE"
        val alertDialog = AlertDialog.Builder(currentActivity)
            .setView(binding.root)
            .setCancelable(false)
            .create()

        binding.buttonCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        binding.buttonDelete.setOnClickListener {
            onDelete()
            alertDialog.dismiss()
        }
        alertDialog.show()
    }


    @SuppressLint("SetTextI18n")
    fun showAddTaskDialog(
        currentActivity: Activity,
        onSave: (Tasks) -> Unit
    ) {
        val binding = AddTaskLayoutBinding.inflate(currentActivity.layoutInflater)
        val alertDialog = AlertDialog.Builder(currentActivity)
            .setView(binding.root)
            .setCancelable(false)
            .create()

        binding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        binding.btnAddTask.text = "Add"
        binding.dialogTitle.text = "Add Task"
        binding.btnAddTask.setOnClickListener {
            if(binding.etAddTaskName.text.isNullOrEmpty()){
                binding.etAddTaskName.error = "Task name is required"
                return@setOnClickListener
            } else {
                val taskName = binding.etAddTaskName.text.toString().trim()
                val taskDescription = binding.etAddTaskDescription.text.toString().trim()
                val task = Tasks(
                    id = 0L,
                    taskName = taskName,
                    taskDescription = taskDescription
                )
                onSave(task)
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }


    @SuppressLint("SetTextI18n")
    fun showUpdateTaskDialog(
        currentActivity: Activity,
        tasks: Tasks,
        onSave: (Tasks) -> Unit
    ) {
        val binding = AddTaskLayoutBinding.inflate(currentActivity.layoutInflater)
        val alertDialog = AlertDialog.Builder(currentActivity)
            .setView(binding.root)
            .setCancelable(false)
            .create()

        binding.btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }

        binding.btnAddTask.text = "Update"
        binding.dialogTitle.text = "Update Task"
        binding.etAddTaskName.setText(tasks.taskName)
        binding.etAddTaskDescription.setText(tasks.taskDescription)
        binding.btnAddTask.setOnClickListener {
            if(binding.etAddTaskName.text.isNullOrEmpty()){
                binding.etAddTaskName.error = "Task name is required"
                return@setOnClickListener
            } else {
                val taskName = binding.etAddTaskName.text.toString().trim()
                val taskDescription = binding.etAddTaskDescription.text.toString().trim()
                val task = Tasks(
                    id = tasks.id,
                    taskName = taskName,
                    taskDescription = taskDescription
                )
                onSave(task)
                alertDialog.dismiss()
            }
        }
        alertDialog.show()
    }
}
