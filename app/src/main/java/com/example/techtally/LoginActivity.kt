package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    // Declare a variable for view binding to access views in the layout
    private lateinit var binding: ActivityLoginBinding

    // Hardcoded admin credentials for direct login without registration
    //private val adminUsername = "admin"
    //private val adminPassword = "admin123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // Enable edge-to-edge display for a more immersive UI
        binding = ActivityLoginBinding.inflate(layoutInflater)  // Inflate the layout using view binding
        setContentView(binding.root)

        // Check if the user is already logged in
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false)
        val userName = sharedPreferences.getString("USER_NAME", null)

        // If the user is logged in, navigate to UserDashboardActivity
        if (isLoggedIn && userName != null) {
            val intent = Intent(this, UserDashboardActivity::class.java)
            intent.putExtra("USER_NAME", userName)
            startActivity(intent)
            finish()
            return
        }

        // Get the user input by clicking the login button
        binding.button2.setOnClickListener {
            // Get user input from login fields
            val loginInput = binding.loginUserInput.text.toString()
            val loginPassword = binding.loginPassword.text.toString()

            // Check if fields are empty and show the hint by changing text color to red
            if (loginInput.isEmpty()) {
                binding.loginUsernameEmailHint.setHintTextColor(resources.getColor(R.color.red)) // Change hint text color to red
                binding.loginUsernameEmailHint.hint = "username/email is required"
            }

            if (loginPassword.isEmpty()) {
                binding.loginPasswordHint.setHintTextColor(resources.getColor(R.color.red)) // Change hint text color to red
                binding.loginPasswordHint.hint = "password is required"
            }

            // Return early if either field is empty
            if (loginInput.isEmpty() || loginPassword.isEmpty()) {
                return@setOnClickListener
            }

            //    Check if admin is logging in with hardcoded credentials
            //    if (loginInput == adminUsername && loginPassword == adminPassword) {
            //    Navigate to AdminDashboardActivity for admin users
            //    val intent = Intent(this@LoginActivity, AdminDashboardActivity::class.java)
            //    intent.putExtra("ADMIN_NAME", adminUsername) // Pass hardcoded admin name
            //    startActivity(intent)
            //    finish()
            //    return@setOnClickListener
            //    }

            // Create a login request object with the user's input for regular login
            val loginRequest = LoginRequest(loginInput, loginPassword)

            // Make an API call to login the user using Retrofit
            RetrofitClient.instance.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        // Get the login response from the server
                        val loginResponse = response.body()

                        // Check if the login was successful
                        if (loginResponse != null && loginResponse.success) {
                            // Check if the logged-in user is an admin or a regular user
                            when (loginResponse.role) {
                                "admin" -> {
                                    // Save user session
                                    saveUserSession(loginInput, false) // Not a guest
                                    // Navigate to AdminDashboardActivity for admin users
                                    val intent = Intent(this@LoginActivity, AdminDashboardActivity::class.java)
                                    intent.putExtra("ADMIN_NAME", loginInput) // Pass username or email
                                    startActivity(intent)
                                    finish()
                                }
                                "user" -> {
                                    // Save user session
                                    saveUserSession(loginInput, false) // Not a guest
                                    // Navigate to UserDashboardActivity for regular users
                                    val intent = Intent(this@LoginActivity, UserDashboardActivity::class.java)
                                    intent.putExtra("USER_NAME", loginInput) // Pass username or email
                                    startActivity(intent)
                                    finish()
                                }
                                else -> {
                                    // Handle unexpected roles or errors
                                    showErrorDialog("Unexpected user role")
                                }
                            }
                        } else {
                            // Show the error dialog if login failed
                            showErrorDialog("Invalid username/email or password") // Show this for invalid credentials
                        }
                    } else {
                        // Log the error response for debugging
                        Log.e("LoginActivity", "Error: ${response.errorBody()?.string()}")
                        // Show the error dialog indicating incorrect credentials
                        showErrorDialog("Invalid username/email or password") // Show this for invalid credentials
                    }
                }

                // Handle the case where the API call fails
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // Log error for debugging
                    Log.e("LoginActivity", "API call failed: ${t.message}")
                    // Show a toast message indicating the failure
                    Toast.makeText(this@LoginActivity, "Login failed: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        // Adjust padding for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            // Get system bar insets and adjust padding accordingly
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ContinueAsGuestBtn.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_GUEST", true) // Save guest status
            editor.putBoolean("IS_LOGGED_IN", false) // Guest is not logged in
            editor.apply()

            val intent = Intent(this, UserDashboardActivity::class.java)
            intent.putExtra("IS_GUEST", true)  // Pass flag for guest
            startActivity(intent)
        }

        // Navigate from loginPage to forgotPasswordPage
        binding.forgotPasswordBtn.setOnClickListener {
            // Start ForgotPasswordActivity
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        // Navigate from loginPage to SignupPage
        binding.ToSignUp.setOnClickListener {
            // Start SignupActivity
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to save user session data, including guest status
    private fun saveUserSession(userName: String, isGuest: Boolean) {
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("IS_LOGGED_IN", true)  // Save login state
        editor.putString("USER_NAME", userName)  // Save username
        editor.putBoolean("IS_GUEST", isGuest)   // Save guest status
        editor.apply()  // Apply changes
    }

    private fun showErrorDialog(message: String) {
        // Inflate the custom layout using a layout inflater
        val dialogView = layoutInflater.inflate(R.layout.login_error_dialog, null)

        // Create an AlertDialog builder and set the custom layout
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setView(dialogView)

        // Get references to the views in the custom layout
        val dialogMessage = dialogView.findViewById<TextView>(R.id.loginErrorMessage)
        val dialogButton = dialogView.findViewById<Button>(R.id.loginErrorOkayBtn)

        // Set the title and message
        dialogMessage.text = message

        // Create and show the dialog
        val dialog = dialogBuilder.create()

        // Set the click listener for the button
        dialogButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}