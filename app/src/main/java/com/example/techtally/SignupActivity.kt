package com.example.techtally

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivitySignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupUsername.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val username = binding.signupUsername.text.toString()
                if (username.isEmpty()) {
                    binding.usernameHint.text = "Username is required"
                    binding.usernameHint.setTextColor(Color.RED)
                } else {
                    checkUsernameAvailability(username)
                }
            }
        }

        binding.signupPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s?.toString() ?: ""
                if (password.isEmpty() || password.contains(" ")) {
                    binding.passwordHint.text = "Password cannot be empty or contain spaces"
                    binding.passwordHint.setTextColor(Color.RED)
                    binding.signupPassword.setTextColor(Color.BLACK)
                } else if (password.length < 8 || password.length > 16) {
                    binding.passwordHint.text = "The minimum is 8 characters and maximum of 16 characters"
                    binding.passwordHint.setTextColor(Color.RED)
                } else if (!containsSpecialCharacter(password)) {
                    binding.passwordHint.text = "The password must contain at least one special character (!@#\$&*)"
                    binding.passwordHint.setTextColor(Color.RED)
                } else {
                    binding.passwordHint.text = getString(R.string.strong_password)
                    binding.passwordHint.setTextColor(Color.parseColor("#32CD32"))
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.signupEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s?.toString() ?: ""
                if (email.endsWith("@gmail.com") || email.endsWith("@yahoo.com")) {
                    checkEmailAvailability(email)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.button3.setOnClickListener {
            val signupUsername = binding.signupUsername.text.toString()
            val signupEmail = binding.signupEmail.text.toString()
            val signupPassword = binding.signupPassword.text.toString()
            val signupConfirmPassword = binding.signupConfirmPassword.text.toString()

            binding.usernameHint.text = ""
            binding.emailHint.text = ""
            binding.passwordHint.text = ""
            binding.confirmPasswordHint.text = ""

            var allFieldsValid = true

            if (signupUsername.isEmpty()) {
                binding.usernameHint.text = "Username is required"
                binding.usernameHint.setTextColor(Color.RED)
                allFieldsValid = false
            }

            if (signupEmail.isEmpty()) {
                binding.emailHint.text = "Email is required"
                binding.emailHint.setTextColor(Color.RED)
                allFieldsValid = false
            } else if (!isValidEmail(signupEmail)) {
                binding.emailHint.text = "Invalid email"
                binding.emailHint.setTextColor(Color.RED)
                allFieldsValid = false
            }

            if (signupPassword.isEmpty()) {
                binding.passwordHint.text = "Password is required"
                binding.passwordHint.setTextColor(Color.RED)
                allFieldsValid = false
            } else if (signupPassword.length < 8 || signupPassword.length > 16) {
                binding.passwordHint.text = "The password must be between 8 and 16 characters"
                binding.passwordHint.setTextColor(Color.RED)
                allFieldsValid = false
            } else if (signupPassword.contains(" ")) {
                binding.passwordHint.text = "Password cannot contain spaces"
                binding.passwordHint.setTextColor(Color.RED)
                allFieldsValid = false
            } else if (!containsSpecialCharacter(signupPassword)) {
                binding.passwordHint.text = "The password must contain at least one special character (!@#\$&*)"
                binding.passwordHint.setTextColor(Color.RED)
                allFieldsValid = false
            }

            if (signupConfirmPassword.isEmpty()) {
                binding.confirmPasswordHint.text = "Confirm password is required"
                binding.confirmPasswordHint.setTextColor(Color.RED)
                allFieldsValid = false
            } else if (signupPassword != signupConfirmPassword) {
                binding.confirmPasswordHint.text = "Passwords do not match"
                binding.confirmPasswordHint.setTextColor(Color.RED)
                allFieldsValid = false
            }

            if (allFieldsValid) {
                registerUser(signupUsername, signupEmail, signupPassword, signupConfirmPassword)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ToLogIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser(username: String, email: String, password: String, password_confirmation: String) {
        val request = UserSignupRequest(username, email, password, password_confirmation)

        RetrofitClient.instance.registerUser(request).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@SignupActivity, response.body()?.message ?: "Signup successful", Toast.LENGTH_SHORT).show()
                    if (response.body()?.navigate == true) {
                        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        if (errorBody.contains("username")) {
                            binding.usernameHint.text = "The username has already been taken."
                            binding.usernameHint.setTextColor(Color.RED)
                        } else if (errorBody.contains("email")) {
                            binding.emailHint.text = "The email has already been taken."
                            binding.emailHint.setTextColor(Color.RED)
                        } else {
                            Toast.makeText(this@SignupActivity, "Signup failed: $errorBody", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Toast.makeText(this@SignupActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkUsernameAvailability(username: String) {
        RetrofitClient.instance.checkUsername(username).enqueue(object : Callback<UsernameCheckResponse> {
            override fun onResponse(call: Call<UsernameCheckResponse>, response: Response<UsernameCheckResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.exists == true) {
                        binding.usernameHint.text = "The username has already been taken."
                        binding.usernameHint.setTextColor(Color.RED)
                    } else {
                        binding.usernameHint.text = "Username available"
                        binding.usernameHint.setTextColor(Color.parseColor("#32CD32"))
                    }
                } else {
                    binding.usernameHint.text = "Error checking username"
                    binding.usernameHint.setTextColor(Color.RED)
                }
            }

            override fun onFailure(call: Call<UsernameCheckResponse>, t: Throwable) {
                binding.usernameHint.text = "API call failed: ${t.message}"
                binding.usernameHint.setTextColor(Color.RED)
            }
        })
    }

    private fun checkEmailAvailability(email: String) {
        RetrofitClient.instance.checkEmail(email).enqueue(object : Callback<EmailCheckResponse> {
            override fun onResponse(call: Call<EmailCheckResponse>, response: Response<EmailCheckResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.exists == true) {
                        binding.emailHint.text = "The email has already been taken."
                        binding.emailHint.setTextColor(Color.RED)
                    } else {
                        binding.emailHint.text = "Email available"
                        binding.emailHint.setTextColor(Color.parseColor("#32CD32"))
                    }
                } else {
                    binding.emailHint.text = "Error checking email"
                    binding.emailHint.setTextColor(Color.RED)
                }
            }

            override fun onFailure(call: Call<EmailCheckResponse>, t: Throwable) {
                binding.emailHint.text = "API call failed: ${t.message}"
                binding.emailHint.setTextColor(Color.RED)
            }
        })
    }

    private fun containsSpecialCharacter(password: String): Boolean {
        val specialCharacters = "!@#\$&*"
        return password.any { it in specialCharacters }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.endsWith("@gmail.com") || email.endsWith("@yahoo.com")
    }
}