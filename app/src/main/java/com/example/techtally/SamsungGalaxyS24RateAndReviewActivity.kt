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

class SamsungGalaxyS24RateAndReviewActivity : AppCompatActivity() {

    private var selectedRating: Int = 0 // Declare the selectedRating variable
    private var percentage_of_ratings: Float = 0.0f
    private var smartphoneId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_samsung_galaxy_s24_rate_and_review)

        // Retrieve the selected rating from the intent
        selectedRating = intent.getIntExtra("SELECTED_RATING", 0)
        // Retrieve the username from the intent
        val userName = intent.getStringExtra("USER_NAME") ?: "Guest" // Default to "Guest" if null
        Log.d("SamsungGalaxyS24RateAndReviewActivity", "Selected Rating: $selectedRating, User Name: $userName") // Debug log

        // Set the username to the TextView
        val nameTextView = findViewById<TextView>(R.id.textView12)
        nameTextView.text = userName // Display the username

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Navigate from SamsungGalaxyS24RateAndReviewActivity to SamsungGalaxyS24FullDetails
        val goToSamsungGalaxyS24FullDetails = findViewById<ImageView>(R.id.rateAndReviewBackButton)
        goToSamsungGalaxyS24FullDetails.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
            startActivity(intent)
        }

        // Update the UI based on the selected rating
        updateRatingButtons(selectedRating)

        // Handle the submit button click
        val submitButton = findViewById<Button>(R.id.rateAndReviewSubmitButton)
        submitButton.setOnClickListener {
            submitReview()
        }
    }

    private fun submitReview() {
        // Get user input
        val commentInput = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.rateAndReviewComment)
        val rateOfTheUser = selectedRating // Use selectedRating
        val commentOfTheUser = commentInput.text.toString()

        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 1)

        // Retrieve the username based on the guest status
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isGuest = sharedPreferences.getBoolean("IS_GUEST", false)
        val userName = if (isGuest) "Guest" else sharedPreferences.getString("USER_NAME", null)

        // Create a review request object
        val reviewRequest = ReviewRequest(
            username = userName ?: "Guest",
            rating = rateOfTheUser,
            comment = commentOfTheUser,
            smartphone_id = smartphoneId
        )

        RetrofitClient.instance.submitReview(reviewRequest).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {
                    val newReview = SamsungGalaxyS24Review(username = userName ?: "Guest", rating = rateOfTheUser, comment = commentOfTheUser, smartphoneId = smartphoneId,)
                    val percentageOfRatings = sharedPreferences.getFloat("PERCENTAGE_OF_RATINGS", 0.0f)

                    val resultIntent = Intent()
                    resultIntent.putExtra("NEW_RATING", rateOfTheUser) // Pass the new rating
                    resultIntent.putExtra("PERCENTAGE_OF_RATINGS", percentageOfRatings)
                    setResult(RESULT_OK, resultIntent) // Set the result to OK

                    // Now navigate to SamsungGalaxyS24ReviewsPage
                    val reviewPageIntent = Intent(this@SamsungGalaxyS24RateAndReviewActivity, SamsungGalaxyS24ReviewsPage::class.java)
                    reviewPageIntent.putExtra("NEW_REVIEW", newReview) // Pass the review object
                    reviewPageIntent.putExtra("PERCENTAGE_OF_RATINGS", percentageOfRatings) // Pass updated percentage
                    reviewPageIntent.putExtra("TOTAL_REVIEWS", getUpdatedNumberOfReviews()) // Pass the total number of reviews
                    reviewPageIntent.putExtra("SMARTPHONE_ID", smartphoneId)
                    startActivity(reviewPageIntent) // Start the review page activity

                    finish() // Close this activity and return to the previous one
                } else {
                    Log.e("SamsungGalaxyS24RateAndReviewActivity", "Error submitting review: ${response.errorBody()?.string()}")
                    Toast.makeText(this@SamsungGalaxyS24RateAndReviewActivity, "Failed to submit review: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Toast.makeText(this@SamsungGalaxyS24RateAndReviewActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Implement this method to return the updated number of reviews
    private fun getUpdatedNumberOfReviews(): Int {
        return 10
    }

    private fun updateRatingButtons(selectedRating: Int) {
        val buttons = arrayOf(
            findViewById<Button>(R.id.rateAndReviewButtonTally1),
            findViewById<Button>(R.id.rateAndReviewButtonTally2),
            findViewById<Button>(R.id.rateAndReviewButtonTally3),
            findViewById<Button>(R.id.rateAndReviewButtonTally4),
            findViewById<Button>(R.id.rateAndReviewButtonTally5)
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