package com.example.empowerize_project.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.empowerize_project.R

class MainActivity : AppCompatActivity() {

    private lateinit var btnStart: Button
    private lateinit var btnLookCom: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStart = findViewById(R.id.btnStart)
        btnLookCom = findViewById(R.id.btnLookCom)

        btnStart.setOnClickListener {
            val intent = Intent(this, Insertion::class.java)
            startActivity(intent)
        }

        btnLookCom.setOnClickListener {
            val intent = Intent(this, Fetching::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide();
    }
}