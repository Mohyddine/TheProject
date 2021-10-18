package com.mehyo.theproject.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mehyo.theproject.databinding.FragmentSplashBinding
import com.mehyo.theproject.ui.vm.DataStoreViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SplashFragment : Fragment() {

    private var _binding:FragmentSplashBinding?=null
    private val binding get() = _binding!!
    private val dataStoreViewModel: DataStoreViewModel by sharedViewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentSplashBinding.inflate(inflater,container,false)
        //return inflater.inflate(R.layout.fragment_splash, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            dataStoreViewModel.readLoginFromDataStore.observe(viewLifecycleOwner,{
                isLoggedIn(it)
            })
        },1000)
    }
    //if remember login is saved navigate timerFragment
    //if remember login isn't saved navigate loginFragment
    private fun isLoggedIn(loggedIn:Boolean?) {
        if (loggedIn != null){
            if (loggedIn) {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToTimerFragment())
            } else {
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
    }

    //set viewBinding to null when fragment is destroyed to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}