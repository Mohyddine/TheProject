package com.mehyo.theproject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mehyo.theproject.R
import com.mehyo.theproject.model.TodoItem
import com.mehyo.theproject.databinding.TodoItemBinding

class TodoViewHolder(private val binding:TodoItemBinding):RecyclerView.ViewHolder(binding.root){

    private val cvCompleteColor = ContextCompat.getColor(binding.root.context,R.color.cvCompleteColor)
    private val cvIncompleteColor = ContextCompat.getColor(binding.root.context,R.color.cvIncompleteColor)

    fun bind(todoItem: TodoItem,clickListener: OnItemClickListener){
        binding.tvTodoTitle.text=todoItem.title
        if (todoItem.completed){
            binding.cvTodo.setCardBackgroundColor(cvCompleteColor)
        }else{
            binding.cvTodo.setCardBackgroundColor(cvIncompleteColor)
        }
        binding.cvTodo.setOnClickListener {
            clickListener.onItemClicked(todoItem)
        }
    }
}
class TodoAdapter(private val itemClickListener: OnItemClickListener):RecyclerView.Adapter<TodoViewHolder>() {
    private var todoItems= listOf<TodoItem>()

    fun setData(list:List<TodoItem>){
        this.todoItems=list
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder = TodoViewHolder(TodoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) = holder.bind(todoItems[position],itemClickListener)

    override fun getItemCount(): Int =todoItems.size
}

interface OnItemClickListener{
    fun onItemClicked(todoItem: TodoItem)
}