package com.mehyo.theproject.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mehyo.theproject.databinding.FragmentDetailsBinding
import androidx.navigation.fragment.navArgs

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //filling the textViews with the received args data
        binding.tvUserid.append(" ${args.todoItem.userId}")
        binding.tvTodoid.append(" ${args.todoItem.id}")
        binding.tvTitle.append(" ${args.todoItem.title}")
        binding.tvCompletedValue.append(" ${args.todoItem.completed}")

    }

    //set viewBinding to null when fragment is destroyed to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}