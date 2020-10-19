package com.alis.news.ui.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.alis.news.R
import com.alis.news.extension.gone
import com.alis.news.extension.visible
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_everything.*
import kotlinx.android.synthetic.main.fragment_top_headlines.*

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

        setOnNavigationItemReselectedListener()
        addNavControllerDestinationChangedListener()
    }

    private fun setOnNavigationItemReselectedListener() {
        bottomNav.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.navigation_top_headlines -> {
                    recycler_top_headlines.smoothScrollToPosition(1)
                }
                R.id.navigation_everything -> {
                    recycler_everything.smoothScrollToPosition(1)
                }
            }
        }
    }

    private fun addNavControllerDestinationChangedListener() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.navigation_top_headlines, R.id.navigation_everything -> {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    bottomNav.visible()
                }
                R.id.detailsFragment -> {
                    window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                    bottomNav.gone()
                }
            }
        }
    }
}