package com.pi.todosc40

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pi.todosc40.database.entity.Todo
import com.pi.todosc40.databinding.ItemTodoBinding

class TodosAdapter(var todosList: List<Todo>) : Adapter<TodosAdapter.TodosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodosViewHolder(binding = binding)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        val todo = todosList[position]
        Log.e("ee", todo.isDone.toString(), )
        holder.binding.apply {
            if (todo.isDone){
                doneText.visibility = View.VISIBLE
                icDone.visibility = View.GONE
                verticalLine.setBackgroundResource(R.color.green)
                todoTitleTv.setTextColor(holder.itemView.resources.getColor(R.color.green))
            }else{
                doneText.visibility = View.GONE
                icDone.visibility = View.VISIBLE
                verticalLine.setBackgroundResource(R.color.blue)
                todoTitleTv.setTextColor(holder.itemView.resources.getColor(R.color.blue))
            }
            todoTitleTv.text = todo.title
            todoDescriptionTv.text = todo.description
            leftView.setOnClickListener {
                listener?.onDelete(todo)
            }
            icDone.setOnClickListener() {
                listener?.onDoneClick(todo)
            }
        }

        Log.e("onBindViewHolder", "BINDING DATA OF TODO: ${todo.id}")
    }

    override fun getItemCount(): Int = todosList.size
    public fun refreshList(newList: List<Todo>) {
        todosList = newList
        notifyDataSetChanged()
    }

    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onDelete(todo: Todo)
        fun onDoneClick(todo: Todo)
        fun onItemViewClick(todo: Todo)
    }

    class TodosViewHolder(val binding: ItemTodoBinding) : ViewHolder(binding.root)

}