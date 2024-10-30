package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class laptopAppleMacbookM3ProRateAndReviewActivity : AppCompatActivity() {

    private var selectedRating: Int = 0 // Declare the selectedRating variable
    private var percentage_of_ratings: Float = 0.0f
    private var smartphoneId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_laptop_apple_macbook_m3_pro_rate_and_review)

        // Retrieve the selected rating from the intent
        selectedRating = intent.getIntExtra("APPLEMACBOOKM3PRO_SELECTED_RATING", 0)
        // Retrieve the username from the intent
        val userName = intent.getStringExtra("USER_NAME") ?: "Guest" // Default to "Guest" if null
        Log.d("AppleMacbookM3ProRateAndReviewActivity", "Selected Rating: $selectedRating, User Name: $userName") // Debug log

        // Set the username to the TextView
        val nameTextView = findViewById<TextView>(R.id.AppleMacbookRateAndReviewNameOfUser)
        nameTextView.text = userName // Display the username

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Navigate from laptopAppleMacbookM3ProRateAndReviewActivity to laptopAppleMacbookM3ProMaxFullDetails
        val backToLaptopAppleMacbookM3ProFullDetails = findViewById<ImageView>(R.id.AppleMacbookM3ProRateAndReviewBackButton)
        backToLaptopAppleMacbookM3ProFullDetails.setOnClickListener {
            val intent = Intent(this, laptopAppleMacbookM3ProFullDetails::class.java)
            startActivity(intent)
        }
        // Update the UI based on the selected rating
        updateRatingButtons(selectedRating)

        // Handle the submit button click
        val submitButton = findViewById<Button>(R.id.AppleMacbookM3ProRateAndReviewSubmitButton)
        submitButton.setOnClickListener {
            submitReview()
        }
    }

    private fun submitReview() {
        // Get user input
        val commentInput = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.AppleMacbookM3ProRateAndReviewComment)
        val AppleMacbookM3ProRateOfTheUser = selectedRating // Use selectedRating
        val AppleMacbookM3ProCommentOfTheUser = commentInput.text.toString()

        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 3)

        // Retrieve the username based on the guest status
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isGuest = sharedPreferences.getBoolean("IS_GUEST", false)
        val userName = if (isGuest) "Guest" else sharedPreferences.getString("USER_NAME", null)

        // Create a review request object
        val reviewRequest = ReviewRequest(
            username = userName ?: "Guest",
            rating = AppleMacbookM3ProRateOfTheUser,
            comment = AppleMacbookM3ProCommentOfTheUser,
            smartphone_id = smartphoneId
        )

        RetrofitClient.instance.submitAppleMacbookM3ProReview(reviewRequest).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    // Log to ensure this section is reached
                    Log.d("laptopAppleMacbookM3ProRateAndReviewActivity", "Review submission successful. Navigating to Iphone16ProMaxReviewsPage.")

                    // Prepare new review and intent
                    val newReview = AppleMacbookM3ProReview(username = userName ?: "Guest", rating = AppleMacbookM3ProRateOfTheUser, comment = AppleMacbookM3ProCommentOfTheUser, smartphoneId = smartphoneId)
                    val AppleMacbookM3ProPercentageOfRatings = sharedPreferences.getFloat("APPLEMACBOOKM3PRO_PERCENTAGE_OF_RATINGS", 0.0f)

                    val resultIntent = Intent()
                    resultIntent.putExtra("NEW_RATING", AppleMacbookM3ProRateOfTheUser) // Pass the new rating
                    resultIntent.putExtra("APPLEMACBOOKM3PRO_PERCENTAGE_OF_RATINGS", AppleMacbookM3ProPercentageOfRatings)
                    setResult(RESULT_OK, resultIntent) // Set the result to OK

                    val reviewPageAppleMacbookM3ProIntent = Intent(this@laptopAppleMacbookM3ProRateAndReviewActivity, laptopAppleMacbookM3ProReviewsPage::class.java)
                    reviewPageAppleMacbookM3ProIntent.putExtra("APPLEMACBOOKM3PRO_NEW_REVIEW", newReview)
                    reviewPageAppleMacbookM3ProIntent.putExtra("APPLEMACBOOKM3PRO_PERCENTAGE_OF_RATINGS", AppleMacbookM3ProPercentageOfRatings)
                    reviewPageAppleMacbookM3ProIntent.putExtra("APPLEMACBOOKM3PRO_TOTAL_REVIEWS", getAppleMacbookM3ProUpdatedNumberOfReviews())
                    reviewPageAppleMacbookM3ProIntent.putExtra("SMARTPHONE_ID", smartphoneId)
                    startActivity(reviewPageAppleMacbookM3ProIntent) // Start the review page activity

                    finish() // Close this activity and return to the previous one
                } else {
                    Log.e("laptopAppleMacbookM3ProRateAndReviewActivity", "Error submitting review: ${response.errorBody()?.string()}")
                    Toast.makeText(this@laptopAppleMacbookM3ProRateAndReviewActivity, "Failed to submit review: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Toast.makeText(this@laptopAppleMacbookM3ProRateAndReviewActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    // Implement this method to return the updated number of reviews
    private fun getAppleMacbookM3ProUpdatedNumberOfReviews(): Int {
        return 10
    }
    private fun updateRatingButtons(selectedRating: Int) {
        val buttons = arrayOf(
            findViewById<Button>(R.id.AppleMacbookM3ProRateAndReviewButtonTally1),
            findViewById<Button>(R.id.AppleMacbookM3ProRateAndReviewButtonTally2),
            findViewById<Button>(R.id.AppleMacbookM3ProRateAndReviewButtonTally3),
            findViewById<Button>(R.id.AppleMacbookM3ProRateAndReviewButtonTally4),
            findViewById<Button>(R.id.AppleMacbookM3ProRateAndReviewButtonTally5)
        )

        // Update the button backgrounds based on the selected rating
        for (i in buttons.indices) {
            buttons[i].backgroundTintList = if (i < selectedRating) {
                ContextCompat.getColorStateList(this, R.color.black) // Change to #2F2F2F
            } else {
                ContextCompat.getColorStateList(this, R.color.backgroundColorOfButton) // Reset to default color
            }
        }
    }
}