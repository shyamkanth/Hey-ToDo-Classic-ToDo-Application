package com.example.heytodo.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.heytodo.CustomBottomSheetDialogFragment
import com.example.heytodo.MainActivity
import com.example.heytodo.R
import com.example.heytodo.databinding.NoteItemBinding
import com.example.heytodo.modal.entity.Tasks
import com.example.heytodo.utils.Utils
import com.example.heytodo.viewmodel.TaskViewModel

class TaskAdaptor(
    private val activity: Activity,
    private var taskList: List<Tasks>,
    private val taskViewModel: TaskViewModel
) : RecyclerView.Adapter<TaskAdaptor.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdaptor.ViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: TaskAdaptor.ViewHolder, position: Int) {
        val task = taskList[position]
        holder.binding.tvTaskName.text = task.taskName
        if (task.taskDescription.isNotEmpty()) {
            holder.binding.tvTaskDesc.setTextColor(ContextCompat.getColor(activity, R.color.primary_info))
            if (task.taskDescription.length > 100) {
                val readMore = activity.getString(R.string.read_more)
                val spannableReadMore = SpannableString(readMore).apply {
                    setSpan(
                        ForegroundColorSpan(ContextCompat.getColor(activity, R.color.black)),
                        0,
                        length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
                val spannableText = SpannableStringBuilder()
                    .append(task.taskDescription.take(100))
                    .append(" ")
                    .append(spannableReadMore)
                holder.binding.tvTaskDesc.text = spannableText
            } else {
                holder.binding.tvTaskDesc.text = task.taskDescription
            }
            holder.binding.tvTaskDesc.setTypeface(null, Typeface.BOLD)
        } else {
            holder.binding.tvTaskDesc.text = "No Description available"
            holder.binding.tvTaskDesc.setTextColor(ContextCompat.getColor(activity, R.color.black))
            holder.binding.tvTaskDesc.setTypeface(null, Typeface.NORMAL)
        }
        holder.binding.icEdit.setOnClickListener {
            Utils.showUpdateTaskDialog(activity, task) { updatedTask ->
                taskViewModel.updateTask(updatedTask)
                holder.binding.tvTaskName.text = updatedTask.taskName
                holder.binding.tvTaskDesc.text = updatedTask.taskDescription
                Utils.showAlertDialogInSameActivity(activity, Utils.BsType.SUCCESS, "Task Updated Successfully")
                (activity as MainActivity).refreshTask()
            }
        }
        holder.binding.icDelete.setOnClickListener {
            Utils.showDeleteDialog(activity) {
                taskViewModel.deleteTask(task)
                notifyItemRemoved(position)
                Utils.showAlertDialogInSameActivity(activity, Utils.BsType.SUCCESS, "Task Removed Successfully")
                (activity as MainActivity).refreshTask()
            }
        }
        holder.binding.layTaskInfo.setOnClickListener {
            CustomBottomSheetDialogFragment.showDialog(
                activity as FragmentActivity, Utils.BsType.INFO, task.taskName, task.taskDescription
            )
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    inner class ViewHolder(val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root)
}