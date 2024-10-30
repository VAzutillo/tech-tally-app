package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdminDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_dashboard)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val goToAddNewDevice = findViewById<Button>(R.id.addNewDeviceButton)
        goToAddNewDevice.setOnClickListener {
            // Start SignupActivity
            val intent = Intent(this, AddNewDeviceActivity::class.java)
            startActivity(intent)
        }
        val goToSmartphoneRatings = findViewById<Button>(R.id.smartphonesRatingsButton)
        goToSmartphoneRatings.setOnClickListener {
            // Start SignupActivity
            val intent = Intent(this, SmartphoneRatingsActivity::class.java)
            startActivity(intent)
        }
        val goToTabletRatings = findViewById<Button>(R.id.tabletRatingsButton)
        goToTabletRatings.setOnClickListener {
            // Start SignupActivity
            val intent = Intent(this, TabletRatingsActivity::class.java)
            startActivity(intent)
        }
    }
}