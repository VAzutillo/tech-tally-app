package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivityForgotpasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    // Binding object for accessing views in the layout
    private lateinit var binding: ActivityForgotpasswordBinding
    // Database helper to interact with the database


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate the layout for this activity
        binding = ActivityForgotpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the UserDatabaseHelper


        // Adjust padding for edge-to-edge display to account for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate from forgotPasswordPage to loginPage
        binding.BackToLogInBtn.setOnClickListener {
            // Create an Intent to start the LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            // Start the LoginActivity
            startActivity(intent)
        }

        // Navigate from forgotPasswordPage to newPasswordPage
        val goToNewPassword = findViewById<Button>(R.id.ContinueBtn)
        goToNewPassword.setOnClickListener {
            // Create an Intent to start the NewPasswordActivity
            val intent = Intent(this, NewPasswordActivity::class.java)
            // Start the NewPasswordActivity
            startActivity(intent)
        }
    }
}