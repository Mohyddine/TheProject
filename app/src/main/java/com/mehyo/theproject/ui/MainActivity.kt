package com.mehyo.theproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import androidx.appcompat.app.AlertDialog
import com.mehyo.theproject.NavDrawerDirections
import com.mehyo.theproject.R
import com.mehyo.theproject.databinding.ActivityMainBinding
import com.mehyo.theproject.ui.vm.DataStoreViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController:NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var listener: NavController.OnDestinationChangedListener
    private val dataStoreViewModel:DataStoreViewModel  by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController=findNavController(R.id.fragment)
        binding.navigationView.setupWithNavController(navController)

        appBarConfiguration= AppBarConfiguration(setOf(R.id.timerFragment),binding.drawerLayout)
        setupActionBarWithNavController(navController,appBarConfiguration)

        val logoutItem = binding.navigationView.menu.findItem(R.id.logout)
        logoutItem.setOnMenuItemClickListener {
            logout()
            binding.drawerLayout.close()
            return@setOnMenuItemClickListener true
        }

        listener=NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment -> {
                    hideTop(true)
                    window?.statusBarColor=ContextCompat.getColor(this, R.color.white)
                }
                R.id.loginFragment -> {
                    hideTop(true)
                    window?.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
                }
                R.id.timerFragment -> {
                    hideTop(false)
                    window?.statusBarColor=ContextCompat.getColor(this, R.color.colorPrimaryDark)
                }
                R.id.listFragment -> {
                    hideTop(false)
                }
            }
        }
    }

    private fun hideTop(hideTop: Boolean) {
        if (hideTop) {
            supportActionBar?.hide()
        } else {
            supportActionBar?.show()
        }
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }
    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun logout(){
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setCancelable(true)
            .setPositiveButton("Yes") { _, _ ->
                dataStoreViewModel.clearDataStore()

                navController.navigate(NavDrawerDirections.toLogin())
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
}