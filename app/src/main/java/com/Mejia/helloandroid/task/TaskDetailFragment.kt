package com.Mejia.helloandroid.ui.task

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.Mejia.helloandroid.R
import com.Mejia.helloandroid.data.task.Task
import com.Mejia.helloandroid.data.task.TaskRepository
import androidx.navigation.fragment.findNavController
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Context

class TaskDetailFragment : Fragment(R.layout.fragment_task_detail) {

    private lateinit var repository: TaskRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = TaskRepository(requireContext())

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etDescription = view.findViewById<EditText>(R.id.etDescription)
        val etDate = view.findViewById<EditText>(R.id.etDate)
        val cbReminder = view.findViewById<CheckBox>(R.id.cbReminder)
        val btnSave = view.findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {

            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            val date = etDate.text.toString()
            val reminder = cbReminder.isChecked

            val task = Task(
                id = repository.getAllTasks().size + 1,
                title = title,
                description = description,
                date = date,
                hasReminder = reminder
            )

            repository.addTask(task)

            // 🔥 AQUÍ VA EL RECORDATORIO
            if (reminder) {

                val intent = Intent(
                    requireContext(),
                    com.Mejia.helloandroid.receiver.TaskReminderReceiver::class.java
                )

                val pendingIntent = PendingIntent.getBroadcast(
                    requireContext(),
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )

                val alarmManager = requireContext()
                    .getSystemService(Context.ALARM_SERVICE) as AlarmManager

                val triggerTime = System.currentTimeMillis() + 30000 // 30 segundos

                alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    triggerTime,
                    pendingIntent
                )
            }

            println("Tarea guardada:")
            println(task)

            findNavController().navigateUp()
        }
    }
}