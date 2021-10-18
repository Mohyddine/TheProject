package com.mehyo.theproject.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mehyo.theproject.ui.adapter.OnItemClickListener
import com.mehyo.theproject.ui.adapter.TodoAdapter
import com.mehyo.theproject.databinding.FragmentListBinding
import com.mehyo.theproject.model.TodoItem
import com.mehyo.theproject.ui.vm.ListViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ListFragment : Fragment(),OnItemClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var todoAdapter: TodoAdapter
    private val listViewModel: ListViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initialize todoAdapter with ItemClickListener
        todoAdapter=TodoAdapter(this@ListFragment)

        //observe the data from the viewModel and sent the list to the adapter
        //hide progressBar when data is available
        listViewModel.todosAPILiveData.observe(viewLifecycleOwner, {data->
            if (data!=null){
                todoAdapter.setData(data)
                todoAdapter.notifyDataSetChanged()
                binding.progressBar.visibility=View.GONE
            }
        })

        //implementing on refresh for swipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                Toast.makeText(activity, "Updated your data", Toast.LENGTH_SHORT).show()
                binding.swipeRefreshLayout.isRefreshing=false
            },3000)
        }

        //setting up the Recyclerview
        binding.rvTodos.apply {
            adapter=todoAdapter
            layoutManager=LinearLayoutManager(activity)
        }
    }

    //implementing onItemClicked to navigate to details screen
    //with the data related to the clicked item
    override fun onItemClicked(todoItem: TodoItem) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(todoItem))
    }

    //set viewBinding to null when fragment is destroyed to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}