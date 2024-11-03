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

class SamsungGalaxyTabS10UltraRateAndReviewActivity : AppCompatActivity() {

    private var selectedRating: Int = 0 // Declare the selectedRating variable
    private var percentage_of_ratings: Float = 0.0f
    private var smartphoneId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_samsung_galaxy_tab_s10_ultra_rate_and_review)

        // Retrieve the selected rating from the intent
        selectedRating = intent.getIntExtra("SAMSUNGGALAXYTABS10ULTRA_SELECTED_RATING", 0)
        // Retrieve the username from the intent
        val userName = intent.getStringExtra("USER_NAME") ?: "Guest" // Default to "Guest" if null

        // Set the username to the TextView
        val nameTextView = findViewById<TextView>(R.id.SamsungGalaxyTabS10UltraRateAndReviewNameOfUser)
        nameTextView.text = userName // Display the username

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Navigate from SamsungGalaxyTabS10UltraRateAndReviewActivity to SamsungGalaxyTabS10UltraFullDetails
        val backToSamsungGalaxyTabS10UltraFullDetails = findViewById<ImageView>(R.id.SamsungGalaxyTabS10UltraRateAndReviewBackButton)
        backToSamsungGalaxyTabS10UltraFullDetails.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyTabS10UltraFullDetails::class.java)
            startActivity(intent)
        }
        // Update the UI based on the selected rating
        updateRatingButtons(selectedRating)

        // Handle the submit button click
        val submitButton = findViewById<Button>(R.id.SamsungGalaxyTabS10UltraRateAndReviewSubmitButton)
        submitButton.setOnClickListener {
            submitReview()
        }
    }
    private fun submitReview() {
        // Get user input
        val commentInput = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.SamsungGalaxyTabS10UltraRateAndReviewComment)
        val SamsungGalaxyTabS10UltraRateOfTheUser = selectedRating // Use selectedRating
        val SamsungGalaxyTabS10CommentOfTheUser = commentInput.text.toString()

        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 4)


        // Retrieve the username based on the guest status
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isGuest = sharedPreferences.getBoolean("IS_GUEST", false)
        val userName = if (isGuest) "Guest" else sharedPreferences.getString("USER_NAME", null)

        // Create a review request object
        val reviewRequest = ReviewRequest(
            username = userName ?: "Guest",
            rating = SamsungGalaxyTabS10UltraRateOfTheUser,
            comment = SamsungGalaxyTabS10CommentOfTheUser,
            smartphone_id = smartphoneId
        )

        RetrofitClient.instance.submitSamsungGalaxyTabS10UltraReview(reviewRequest).enqueue(object : Callback<ReviewResponse> {
            override fun onResponse(call: Call<ReviewResponse>, response: Response<ReviewResponse>) {
                if (response.isSuccessful) {

                    // Prepare new review and intent
                    val newReview = SamsungGalaxyTabS10UltraReview(username = userName ?: "Guest", rating = SamsungGalaxyTabS10UltraRateOfTheUser, comment = SamsungGalaxyTabS10CommentOfTheUser, smartphoneId = smartphoneId)
                    val SamsungGalaxyTabS10UltraPercentageOfRatings = sharedPreferences.getFloat("SAMSUNGGALAXYTABS10ULTRA_PERCENTAGE_OF_RATINGS", 0.0f)

                    val resultIntent = Intent()
                    resultIntent.putExtra("NEW_RATING", SamsungGalaxyTabS10UltraRateOfTheUser) // Pass the new rating
                    resultIntent.putExtra("SAMSUNGGALAXYTABS10ULTRA_PERCENTAGE_OF_RATINGS", SamsungGalaxyTabS10UltraPercentageOfRatings)
                    setResult(RESULT_OK, resultIntent) // Set the result to OK

                    val reviewPageSamsungGalaxyTabS10UltraIntent = Intent(this@SamsungGalaxyTabS10UltraRateAndReviewActivity, SamsungGalaxyTabS10UltraReviewsPage::class.java)
                    reviewPageSamsungGalaxyTabS10UltraIntent.putExtra("SAMSUNGGALAXYTABS10ULTRA_NEW_REVIEW", newReview)
                    reviewPageSamsungGalaxyTabS10UltraIntent.putExtra("SAMSUNGGALAXYTABS10ULTRA_PERCENTAGE_OF_RATINGS", SamsungGalaxyTabS10UltraPercentageOfRatings)
                    reviewPageSamsungGalaxyTabS10UltraIntent.putExtra("SAMSUNGGALAXYTABS10ULTRA_TOTAL_REVIEWS", getSamsungGalaxyTabS10UltraUpdatedNumberOfReviews())
                    reviewPageSamsungGalaxyTabS10UltraIntent.putExtra("SMARTPHONE_ID", smartphoneId)
                    startActivity(reviewPageSamsungGalaxyTabS10UltraIntent) // Start the review page activity

                    finish() // Close this activity and return to the previous one
                }
            }

            override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                Toast.makeText(this@SamsungGalaxyTabS10UltraRateAndReviewActivity, "API call failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
    // Implement this method to return the updated number of reviews
    private fun getSamsungGalaxyTabS10UltraUpdatedNumberOfReviews(): Int {
        return 10
    }
    private fun updateRatingButtons(selectedRating: Int) {
        val buttons = arrayOf(
            findViewById<Button>(R.id.SamsungGalaxyTabS10UltraRateAndReviewButtonTally1),
            findViewById<Button>(R.id.SamsungGalaxyTabS10UltraRateAndReviewButtonTally2),
            findViewById<Button>(R.id.SamsungGalaxyTabS10UltraRateAndReviewButtonTally3),
            findViewById<Button>(R.id.SamsungGalaxyTabS10UltraRateAndReviewButtonTally4),
            findViewById<Button>(R.id.SamsungGalaxyTabS10UltraRateAndReviewButtonTally5)
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
