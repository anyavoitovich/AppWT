package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread.sleep(3000)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        val levelsButton = findViewById<Button>(R.id.levelsButton)

        levelsButton.setOnClickListener {
            val playIntent = Intent(this, Levels::class.java)
            startActivity(playIntent)

        }
        levelsButton.setOnClickListener {
            try {
                val playIntent = Intent(this, Levels::class.java)
                startActivity(playIntent)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error starting Levels activity: ${e.message}")
            }
        }
    }
}