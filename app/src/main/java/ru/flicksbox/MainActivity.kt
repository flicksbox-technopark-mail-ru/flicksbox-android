package ru.flicksbox

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.flicksbox.movie.presentation.favourites.FavouritesFragment
import ru.flicksbox.movie.presentation.main.MainFragment
import ru.flicksbox.movie.presentation.player.NavigationController
import ru.flicksbox.movie.presentation.search.SearchFragment
import ru.flicksbox.user.presentation.ProfileFragment

class MainActivity : AppCompatActivity(), NavigationController {
    private var navView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.nav_view)
        navView?.setOnNavigationItemSelectedListener(navListener)
        if (savedInstanceState == null)
            replaceFragment(mainFragment)
    }

    private val mainFragment = MainFragment()
    private val searchFragment = SearchFragment()
    private val favouritesFragment = FavouritesFragment()
    private val profileFragment = ProfileFragment()

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_main -> replaceFragment(mainFragment)
            R.id.navigation_search -> replaceFragment(searchFragment)
            R.id.navigation_favourites -> replaceFragment(FavouritesFragment())
            R.id.navigation_profile -> replaceFragment(profileFragment)
        }
        true
    }

    private fun replaceFragment(fragment : Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment).commit()
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