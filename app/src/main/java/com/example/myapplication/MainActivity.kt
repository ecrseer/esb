package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController



    private var listenerEscondeFab = object : NavController.OnDestinationChangedListener {
        override fun onDestinationChanged(
            controller: NavController,
            destination: NavDestination,
            arguments: Bundle?
        ) {
            val fabDeveSumir = destination.id == R.id.NotaViewPagerFragment
            if (fabDeveSumir) {
                binding.fab?.visibility = View.GONE
            } else {
                binding.fab?.visibility = View.VISIBLE
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
    }



    fun setupNavegacao() {
        navController = findNavController(R.id.Nav_hostNovo)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener (listenerEscondeFab)
    }

    override fun onResume() {
        super.onResume()
        setSupportActionBar(binding.toolbar)
        setupNavegacao()


    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.Nav_hostNovo)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


}