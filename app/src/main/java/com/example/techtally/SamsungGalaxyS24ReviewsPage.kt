package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SamsungGalaxyS24ReviewsPage : AppCompatActivity() {

    private lateinit var reviewsRecyclerView: RecyclerView
    private lateinit var samsungGalaxyS24ReviewsAdapter: SamsungGalaxyS24ReviewsAdapter
    private val samsungGalaxyS24ReviewsList = mutableListOf<SamsungGalaxyS24Review>() // List to hold review data
    private var numberOfReviews: Int = 0 // Variable to hold the number of reviews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_samsung_galaxy_s24_reviews_page)

        // Find the RecyclerView in activity_reviews_page.xml
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView)

        // Set up the RecyclerView with a LinearLayoutManager in reverse order
        val samsungLayoutManager = LinearLayoutManager(this)
        samsungLayoutManager.reverseLayout = true // Reverse layout to show newest items at the top
        samsungLayoutManager.stackFromEnd = true  // Stack items from the end
        reviewsRecyclerView.layoutManager = samsungLayoutManager

        // Initialize the adapter
        samsungGalaxyS24ReviewsAdapter = SamsungGalaxyS24ReviewsAdapter(samsungGalaxyS24ReviewsList)
        reviewsRecyclerView.adapter = samsungGalaxyS24ReviewsAdapter

        // Retrieve the number of reviews from SharedPreferences
        numberOfReviews = getNumberOfReviews()
        getSamsungGalaxyS24BySmartphoneId()

        // Check if there's a new review passed from SamsungGalaxyS24RateAndReviewActivity
        intent.getParcelableExtra<SamsungGalaxyS24Review>("NEW_REVIEW")?.let { newReview ->
            samsungGalaxyS24ReviewsList.add(0, newReview) // Add the new review at the top of the list
            samsungGalaxyS24ReviewsAdapter.notifyItemInserted(0) // Notify adapter that an item was inserted at position 0
            reviewsRecyclerView.scrollToPosition(0) // Scroll to the top of the list
            onReviewSubmitted() // Call to update number of reviews

            // Fetch reviews for the smartphone when the activity is created
            val smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 1)

        }

        val backButton: ImageView = findViewById(R.id.samsungGalaxyS24ReviewsPageBackButton)
        backButton.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java).apply {
                putParcelableArrayListExtra("REVIEWS_LIST", ArrayList(samsungGalaxyS24ReviewsList)) // Pass the reviews list
            }
            startActivity(intent) // Start SamsungGalaxyS24FullDetails activity
            finish()
        }
    }

    private fun getNumberOfReviews(): Int {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        return sharedPreferences.getInt("numberOfReviews", 0) // Default to 0 if not found
    }

    private fun getSamsungGalaxyS24BySmartphoneId() {
        val smartphoneId = 1 // Replace this with the desired smartphone ID
        RetrofitClient.apiService.getSamsungGalaxyS24BySmartphoneId(smartphoneId).enqueue(object : Callback<List<SamsungGalaxyS24Review>> {
            override fun onResponse(call: Call<List<SamsungGalaxyS24Review>>, response: Response<List<SamsungGalaxyS24Review>>) {
                if (response.isSuccessful && response.body() != null) {
                    samsungGalaxyS24ReviewsList.clear()
                    samsungGalaxyS24ReviewsList.addAll(response.body()!!)
                    samsungGalaxyS24ReviewsAdapter.notifyDataSetChanged()

                    numberOfReviews = samsungGalaxyS24ReviewsList.size
                    saveNumberOfReviews(numberOfReviews)
                } else {
                    Log.e("SamsungGalaxyS24ReviewsPage", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<SamsungGalaxyS24Review>>, t: Throwable) {
                Log.e("SamsungGalaxyS24ReviewsPage", "Failure: ${t.message}")
            }
        })
    }

    // Function to be called when a new review is submitted
    private fun onReviewSubmitted() {
        // Increment the number of reviews
        numberOfReviews++
        // Save the updated number of reviews to SharedPreferences
        saveNumberOfReviews(numberOfReviews)
    }

    private fun saveNumberOfReviews(count: Int) {
        val sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("numberOfReviews", count)
        editor.apply() // Save the changes asynchronously
        Log.d("SamsungGalaxyS24ReviewsPage", "Saved numberOfReviews: $count")
    }
}
