package com.Mejia.helloandroid.ui.task

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Mejia.helloandroid.R
import com.Mejia.helloandroid.data.task.TaskRepository

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private lateinit var repository: TaskRepository
    private lateinit var adapter: TaskAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = TaskRepository(requireContext())

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerTasks)
        val btnAdd = view.findViewById<Button>(R.id.btnAddTask)

        // Adapter inicial vacío
        adapter = TaskAdapter(emptyList())

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // cargar datos iniciales
        loadTasks()

        // botón agregar tarea
        btnAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_taskListFragment_to_taskDetailFragment
            )
        }
    }

    override fun onResume() {
        super.onResume()
        loadTasks() // recarga al volver del detalle
    }

    private fun loadTasks() {
        val tasks = repository.getAllTasks()
        adapter.updateData(tasks)
    }
}