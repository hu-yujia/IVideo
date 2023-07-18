package com.example.ivideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ivideo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ActivityMainBinding.inflate(layoutInflater)
    }
}