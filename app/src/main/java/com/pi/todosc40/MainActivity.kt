package com.pi.todosc40

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.pi.todosc40.databinding.ActivityMainBinding
import com.pi.todosc40.tabs.list_fragment.TodoListFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var listFragment = TodoListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showListFragment()
        binding.fab.setOnClickListener {
            val addTodoBottomSheet = AddTodoBottomSheet {
                listFragment.refreshTodos()
            }
            addTodoBottomSheet.show(supportFragmentManager, null)
        }
        binding.bottomNavigation.setOnItemSelectedListener {
            if (it.itemId == R.id.settingsTab) {
                return@setOnItemSelectedListener false
            } else {
                showListFragment()
            }
            return@setOnItemSelectedListener true
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)

    }

    private fun showListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, listFragment)
            .commit()
    }
}