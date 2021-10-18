package com.mehyo.theproject.ui.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mehyo.theproject.databinding.FragmentTimerBinding
import com.mehyo.theproject.ui.vm.DataStoreViewModel
import kotlinx.coroutines.flow.first
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.text.SimpleDateFormat
import java.util.*

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!
    private lateinit var currentDate: String
    private val dataStoreViewModel: DataStoreViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()
        lifecycleScope.launchWhenCreated {
            firstRunDate(getFirstRun())
        }
        binding.timer.start()
    }

    //saving the timer value when leaving the timer screen
    override fun onPause() {
        super.onPause()
        val timerTime: Long = SystemClock.elapsedRealtime() - binding.timer.base
        dataStoreViewModel.saveTimerBaseToDataStore(timerTime)
        binding.timer.stop()
    }

    //first run check
    //saving date of first run and displaying it
    //working with the timer reading and modifying the value
    private fun firstRunDate(fr: Boolean) {
        if (fr) {
            val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm")
            currentDate = sdf.format(Date())
            dataStoreViewModel.saveFirstRunDateToDataStore(currentDate)
            binding.timer.base = SystemClock.elapsedRealtime()
            dataStoreViewModel.saveFirstRunToDataStore(false)
        } else {
            dataStoreViewModel.readTimerBaseFromDataStore.observe(viewLifecycleOwner, {
                timerBaseCal(it)
            })
        }
        dataStoreViewModel.readFirstRunDateFromDataStore.observe(viewLifecycleOwner, { frd ->
            binding.firstRunDate.text = frd.toString()
        })
    }

    private fun timerBaseCal(savedTimerBase: Long) {
        binding.timer.base = SystemClock.elapsedRealtime() - savedTimerBase
    }

    //onBackPressed implementation to exit the app when BackPressed from the timer screen
    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            exit()
        }.isEnabled
    }

    //exit app option inside alert dialog
    private fun exit() {
        AlertDialog.Builder(requireActivity())
            .setTitle("Exit")
            .setMessage("Do you want to exit the app?")
            .setCancelable(true)
            .setPositiveButton("Yes", DialogInterface.OnClickListener { _, _ ->
                this.requireActivity().finish()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })
            .show()
    }

    //getting FirstRun value from flow
    private suspend fun getFirstRun(): Boolean {
        return dataStoreViewModel.readFirstRunFromDataStore.first()
    }

    //set viewBinding to null when fragment is destroyed to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}