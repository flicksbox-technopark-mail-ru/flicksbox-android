package ru.flicksbox.movie.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.flicksbox.R

class FavouritesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        if (supportFragmentManager.findFragmentById(R.id.favourites_fragment_wrapper) == null) {
            val fm = supportFragmentManager.beginTransaction()
            fm.add(R.id.favourites_fragment_wrapper, FavouritesFragment())
            fm.commit()
        }
    }
}