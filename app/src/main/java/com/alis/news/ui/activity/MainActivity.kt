package com.alis.news.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.alis.news.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var toolbar: MaterialToolbar
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
    }

    private fun initializeViews() {
        toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)
        bottomNav = findViewById(R.id.bottom_nav)
        setUpNavController()
    }

    private fun setUpNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navigation_top_headlines, R.id.navigation_everything)
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)

        addNavControllerDestinationChangedListener()
    }

    private fun addNavControllerDestinationChangedListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_top_headlines -> window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                R.id.navigation_everything -> window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                R.id.detailsFragment -> window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        }
    }
}