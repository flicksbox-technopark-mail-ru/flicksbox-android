package ru.flicksbox

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.flicksbox.movie.presentation.player.NavigationController

class MainActivity : AppCompatActivity(), NavigationController {
    private var navView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_main,
                R.id.navigation_search,
                R.id.navigation_favourites,
                R.id.navigation_profile,
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView?.setupWithNavController(navController)
    }

    override fun showNavigation() {
        navView?.visibility = View.VISIBLE
        supportActionBar?.show()
    }

    override fun hideNavigation() {
        navView?.visibility = View.GONE
        supportActionBar?.hide()
    }
}