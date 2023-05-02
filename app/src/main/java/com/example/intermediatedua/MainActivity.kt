package com.example.intermediatedua

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.navigation.findNavController
import com.example.intermediatedua.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment)
        when(navController.currentDestination?.id){
            R.id.homeFragment -> finish()
            R.id.loginFragment -> finish()
            R.id.registerScreen -> navController.navigate(R.id.action_registerScreen_to_loginFragment)
            R.id.addStoryFragment -> navController.navigate(R.id.action_addStoryFragment_to_homeFragment)
            else -> onBackPressed()
        }
    }
}