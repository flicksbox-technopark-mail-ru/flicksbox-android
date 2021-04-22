package ru.flicksbox.content.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.flicksbox.R
import ru.flicksbox.content.main.slider.SliderFragment

class ContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        if (supportFragmentManager.findFragmentById(R.id.slider_content_top_wrapper) == null) {
            val fm = supportFragmentManager.beginTransaction()
            fm.add(R.id.slider_content_top_wrapper, SliderFragment())
            fm.commit()
        }
    }
}