package com.pi.todosc40.tabs.list_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.pi.todosc40.R
import com.pi.todosc40.TodosAdapter
import com.pi.todosc40.database.MyDataBase
import com.pi.todosc40.database.entity.Todo
import com.pi.todosc40.databinding.FragmentTodoListBinding
import com.pi.todosc40.utils.clearTime
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar

class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding
    private val todosList = mutableListOf<Todo>(
        Todo(
            id = 1,
            title = "",
            description = "",
            isDone = false,
            date = 2323232
        )
    )
    private lateinit var todosAdapter: TodosAdapter
    private var selectedDay = CalendarDay.today()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        refreshTodos()
        initCalendarListener()
    }

    private fun initCalendarListener() {
        binding.calendarView.currentDate = CalendarDay.today()
        binding.calendarView.selectedDate = CalendarDay.today()
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            selectedDay = date
            refreshTodos()
        }
    }

    fun refreshTodos() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, selectedDay.year)
        calendar.set(Calendar.MONTH, selectedDay.month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, selectedDay.day)
        calendar.clearTime()

        val todosList = MyDataBase.getInstance(requireContext()).getTodosDao()
            .getTodosByDate(calendar.timeInMillis)
        todosAdapter.refreshList(todosList.reversed())
    }

    private fun initRecyclerView() {
        todosAdapter = TodosAdapter(todosList)
        todosAdapter.listener = object : TodosAdapter.OnItemClickListener {
            override fun onDelete(todo: Todo) {
                MyDataBase.getInstance(requireContext()).getTodosDao().deleteTodo(todo)
                refreshTodos()
            }

            override fun onDoneClick(todo: Todo) {
                Log.e("done", "aloooo", )
                // Toggle the isDone status (if it's done, make it not done and vice versa)
                todo.isDone = !todo.isDone

                // Update the todo in the database
                MyDataBase.getInstance(requireContext()).getTodosDao().updateTodo(todo)
                
                // Refresh the list to reflect the changes
                refreshTodos()

            }

            override fun onItemViewClick(todo: Todo) {
                TODO("Not yet implemented")
            }

        }
        binding.todosRecyclerView.adapter = todosAdapter
    }
}
