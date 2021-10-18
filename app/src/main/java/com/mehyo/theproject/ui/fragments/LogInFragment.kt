package com.mehyo.theproject.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mehyo.theproject.constant.Constants
import com.mehyo.theproject.databinding.FragmentLoginBinding
import com.mehyo.theproject.ui.vm.DataStoreViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LogInFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val dataStoreViewModel: DataStoreViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogIn.setOnClickListener {
            val username=binding.TextInputEditTextUserName.text?.trim().toString()
            val password=binding.TextInputEditTextPass.text?.trim().toString()
            when (checkCredentials(username,password)){
                "SUPER_ADMIN1" -> {
                    shortToast("Welcome SUPER_ADMIN1")
                    hideKeyboard(view)
                    rememberLoginIfChecked()
                    goToTimerScreen()
                }
                "SUPER_ADMIN1_NO_PASS" -> {
                    shortToast("SUPER_ADMIN1 your password is not entered or wrong")
                }
                "SUPER_ADMIN2" -> {
                    shortToast("Welcome SUPER_ADMIN2")
                    hideKeyboard(view)
                    rememberLoginIfChecked()
                    goToTimerScreen()
                }
                "SUPER_ADMIN2_NO_PASS" -> {
                    shortToast("SUPER_ADMIN2 your password is not entered or wrong")
                }
                "NO_USER" -> {
                    longToast("$username is not Registered")
                }
                "NO_DATA" -> {
                    longToast("Kindly fill the required fields")
                }
            }
        }
    }

    private fun rememberLoginIfChecked(){
        if(binding.cbRememberMe.isChecked){
            dataStoreViewModel.saveLoginToDataStore(true)
        }
    }

    private fun shortToast(message:String){
        Toast.makeText(activity,message, Toast.LENGTH_SHORT).show()
    }
    private fun longToast(message:String){
        Toast.makeText(activity,message, Toast.LENGTH_LONG).show()
    }
    private fun hideKeyboard(view: View){
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
    private fun goToTimerScreen(){
        findNavController().navigate(LogInFragmentDirections.actionLoginFragmentToTimerFragment())
    }
    private fun checkCredentials(username:String, password:String):String{
        if (username.isNotEmpty() || password.isNotEmpty()){
            if (username == Constants.SUPER_ADMIN1) {
                if (password == Constants.SUPER_ADMIN1_PASS) {
                    return "SUPER_ADMIN1"
                }
                return "SUPER_ADMIN1_NO_PASS"
            } else if (username == Constants.SUPER_ADMIN2) {
                if (password == Constants.SUPER_ADMIN2_PASS) {
                    return "SUPER_ADMIN2"
                }
                return "SUPER_ADMIN2_NO_PASS"
            }
        }else{
            return "NO_DATA"
        }
       return "NO_USER"
    }

    //set viewBinding to null when fragment is destroyed to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}