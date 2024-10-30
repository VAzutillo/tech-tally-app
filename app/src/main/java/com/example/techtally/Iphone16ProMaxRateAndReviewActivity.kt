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

class Iphone16ProMaxRateAndReviewActivity : AppCompatActivity() {

    private var selectedRating: Int = 0 // Declare the selectedRating variable
    private var percentage_of_ratings: Float = 0.0f
    private var smartphoneId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iphone16_pro_max_rate_and_review)

        // Retrieve the selected rating from the intent
        selectedRating = intent.getIntExtra("IPHONE16PROMAX_SELECTED_RATING", 0)
        // Retrieve the username from the intent
        val userName = intent.getStringExtra("USER_NAME") ?: "Guest" // Default to "Guest" if null
        Log.d("Iphone16ProMaxRateAndReviewActivity", "Selected Rating: $selectedRating, User Name: $userName") // Debug log

        // Set the username to the TextView
        val nameTextView = findViewById<TextView>(R.id.Iphone16ProMaxRateAndReviewNameOfUser)
        nameTextView.text = userName // Display the username

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate from Iphone16ProMaxRateAndReviewActivity to Iphone16ProMaxFullDetails
        val backToIphone16ProMaxFullDetails = findViewById<ImageView>(R.id.Iphone16ProMaxRateAndReviewBackButton)
        backToIphone16ProMaxFullDetails.setOnClickListener {
            val intent = Intent(this, Iphone16ProMaxFullDetails::class.java)
            startActivity(intent)
        }
        // Update the UI based on the selected rating
        updateRatingButtons(selectedRating)

        // Handle the submit button click
        val submitButton = findViewById<Button>(R.id.Iphone16ProMaxRateAndReviewSubmitButton)
        submitButton.setOnClickListener {
            submitReview()
        }
    }

    private fun submitReview() {
        // Get user input
        val commentInput = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.Iphone16ProMaxRateAndReviewComment)
        val Iphone16ProMaxRateOfTheUser = selectedRating // Use selectedRating
        val Iphone16ProMaxCommentOfTheUser = commentInput.text.toString()

        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 2)


        // Retrieve the username based on the guest status
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isGuest = sharedPreferences.getBoolean("IS_GUEST", false)
        val userName = if (isGuest) "Guest" else sharedPreferences.getString("USER_NAME", null)

        // Create a review request object
        val reviewRequest = ReviewRequest(
            username = userName ?: "Guest",
            rating = Iphone16ProMaxRateOfTheUser,
            comment = Iphone16ProMaxCommentOfTheUser,
            smartphone_id = smartphoneId
        )

        RetrofitClient.instance.submitIphone16ProMaxReview(reviewRequest).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    // Log to ensure this section is reached
                    Log.d("Iphone16ProMaxRateAndReviewActivity", "Review submission successful. Navigating to Iphone16ProMaxReviewsPage.")

                    // Prepare new review and intent
                    val newReview = Iphone16ProMaxReview(username = userName ?: "Guest", rating = Iphone16ProMaxRateOfTheUser, comment = Iphone16ProMaxCommentOfTheUser, smartphoneId = smartphoneId)
                    val iPhone16ProMaxPercentageOfRatings = sharedPreferences.getFloat("IPHONE16PROMAX_PERCENTAGE_OF_RATINGS", 0.0f)

                    val resultIntent = Intent()
                    resultIntent.putExtra("NEW_RATING", Iphone16ProMaxRateOfTheUser) // Pass the new rating
                    resultIntent.putExtra("IPHONE16PROMAX_PERCENTAGE_OF_RATINGS", iPhone16ProMaxPercentageOfRatings)
                    setResult(RESULT_OK, resultIntent) // Set the result to OK

                    val reviewPageIphone16ProMaxIntent = Intent(this@Iphone16ProMaxRateAndReviewActivity, Iphone16ProMaxReviewsPage::class.java)
                    reviewPageIphone16ProMaxIntent.putExtra("IPHONE16PROMAX_NEW_REVIEW", newReview)
                    reviewPageIphone16ProMaxIntent.putExtra("IPHONE16PROMAX_PERCENTAGE_OF_RATINGS", iPhone16ProMaxPercentageOfRatings)
                    reviewPageIphone16ProMaxIntent.putExtra("IPHONE16PROMAX_TOTAL_REVIEWS", getIphone16ProMaxUpdatedNumberOfReviews())
                    reviewPageIphone16ProMaxIntent.putExtra("SMARTPHONE_ID", smartphoneId)
                        startActivity(reviewPageIphone16ProMaxIntent) // Start the review page activity

                        finish() // Close this activity and return to the previous one
                } else {
                    Log.e("Iphone16ProMaxRateAndReviewActivity", "Error submitting review: ${response.errorBody()?.string()}")
                    Toast.makeText(this@Iphone16ProMaxRateAndReviewActivity, "Failed to submit review: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Toast.makeText(this@Iphone16ProMaxRateAndReviewActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    // Implement this method to return the updated number of reviews
    private fun getIphone16ProMaxUpdatedNumberOfReviews(): Int {
        return 10
    }
    private fun updateRatingButtons(selectedRating: Int) {
        val buttons = arrayOf(
            findViewById<Button>(R.id.Iphone16ProMaxRateAndReviewButtonTally1),
            findViewById<Button>(R.id.Iphone16ProMaxRateAndReviewButtonTally2),
            findViewById<Button>(R.id.Iphone16ProMaxRateAndReviewButtonTally3),
            findViewById<Button>(R.id.Iphone16ProMaxRateAndReviewButtonTally4),
            findViewById<Button>(R.id.Iphone16ProMaxRateAndReviewButtonTally5)
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
