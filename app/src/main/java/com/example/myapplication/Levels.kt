package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Levels : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.levels_pad)
        val playButton = findViewById<Button>(R.id.playButton)

        playButton.setOnClickListener {
            val playIntent = Intent(this, GamePad::class.java)
            startActivity(playIntent)

        }
    }
}