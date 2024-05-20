package com.example.floweasy.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import com.example.floweasy.databinding.ActivityMainBinding
import androidx.navigation.ui.NavigationUI
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.floweasy.R

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout:DrawerLayout
    lateinit var binding:ActivityMainBinding
    lateinit var toggle:ActionBarDrawerToggle
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        drawerLayout = binding.drawerLayout

        // Setup ActionBarDrawerToggle
        toggle= ActionBarDrawerToggle(this, binding.drawerLayout,
            R.string.nav_open,
            R.string.nav_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Set the toolbar
        setSupportActionBar(binding.toolbar)

        // Setup NavController
        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        // Setup NavigationUI to work with ActionBar
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        // Setup NavigationUI to handle the NavigationView
        NavigationUI.setupWithNavController(binding.navView, navController)

        val sharedPreferences = getSharedPreferences("UsersPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "Mr/Mrs X")
        val headerView = binding.navView.getHeaderView(0)
        val nameUserTextView = headerView.findViewById<TextView>(R.id.name_user)
        val userProfileAvatar=headerView.findViewById<ImageView>(R.id.profile_iv)
        nameUserTextView.text = getString(R.string.nav_header_username, username)

        userProfileAvatar.setOnClickListener {
            if(navController.currentDestination?.id== R.id.profileFragment) return@setOnClickListener
            navController.navigate(R.id.profileFragment)
            drawerLayout.closeDrawers()

        }

        // Use the retrieved data

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Handle the action
                    if(navController.currentDestination?.id== R.id.homeFragment){
                        drawerLayout.closeDrawers()
                        return@setNavigationItemSelectedListener true
                    }
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.nav_about -> {
                    // Handle the action
                    if(navController.currentDestination?.id== R.id.aboutFragment){
                        drawerLayout.closeDrawers()
                        return@setNavigationItemSelectedListener true
                    }
                    navController.navigate(R.id.aboutFragment)
                    true
                }
                R.id.nav_privacy -> {
                    // Handle the action
                    if(navController.currentDestination?.id== R.id.privacyFragment){
                        drawerLayout.closeDrawers()
                        return@setNavigationItemSelectedListener true
                    }
                    navController.navigate(R.id.privacyFragment)
                    true
                }
                R.id.nav_profile -> {
                    // Handle the action to navigate to profile
                    if(navController.currentDestination?.id== R.id.profileFragment){
                        drawerLayout.closeDrawers()
                        return@setNavigationItemSelectedListener true
                    }
                    navController.navigate(R.id.profileFragment)
                    true
                }
                R.id.nav_exit ->{
                    setAlertDialog()
                    true
                }
                else -> false
            }.also {
                // Close the drawer after handling the click
                drawerLayout.closeDrawers()
            }
        }


    }
    private fun setAlertDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit?")
        builder.setIcon(R.drawable.ic_exit_blue_24)
        builder.setMessage("Are you sure you want to exit the app?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            finish()
            dialog.dismiss() // Dismiss the dialog when OK button is clicked
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss() // Dismiss the dialog when Cancel button is clicked
        }
        builder.show() // Display the alert dialog
    }
    override fun onSupportNavigateUp(): Boolean {
        // to handle navigation when the user presses the "Up" button in the action bar.
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {

        if (navController.currentDestination?.id != R.id.homeFragment) {
            // if the current fragment is not home, it will navigate directly to home fragment
            navController.popBackStack(R.id.homeFragment, false)
        } else if(navController.currentDestination?.id == R.id.homeFragment){
            setAlertDialog()
        }else{
            finish()
            super.onBackPressed()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        clearSharedPreferences()
    }

    private fun clearSharedPreferences() {
        val sharedPreferences = getSharedPreferences("UsersPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}