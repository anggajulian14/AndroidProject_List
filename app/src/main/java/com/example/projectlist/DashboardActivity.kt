package com.example.projectlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val button1 : CardView = findViewById(R.id.buton1)
        val button2 : CardView = findViewById(R.id.buton2)

        button1.setOnClickListener{

            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)

            finish()

        }

        button2.setOnClickListener{

            val intent = Intent(this, SpinnerActivity::class.java)

            startActivity(intent)

            finish()

        }

    }
}