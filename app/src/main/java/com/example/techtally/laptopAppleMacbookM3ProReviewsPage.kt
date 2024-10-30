package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class laptopAppleMacbookM3ProReviewsPage : AppCompatActivity() {

    private lateinit var AppleMacbookM3ProReviewsPageRecyclerView: RecyclerView
    private lateinit var laptopAppleMacbookM3ProReviewsAdapter: laptopAppleMacbookM3ProReviewsAdapter
    private val reviewListOfAppleMacbookM3Pro = mutableListOf<AppleMacbookM3ProReview>() // List to hold review data
    private var AppleMacbookM3ProMaxNumberOfReviews: Int = 0 // Variable to hold the number of reviews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_laptop_apple_macbook_m3_pro_reviews_page)

        // Find the RecyclerView in activity_reviews_page.xml
        AppleMacbookM3ProReviewsPageRecyclerView = findViewById(R.id.AppleMacbookM3ProReviewsPageRecyclerView)

        // Set up the RecyclerView with a LinearLayoutManager in reverse order
        val applemacbookm3proLayoutManager = LinearLayoutManager(this)
        applemacbookm3proLayoutManager.reverseLayout = true // Reverse layout to show newest items at the top
        applemacbookm3proLayoutManager.stackFromEnd = true  // Stack items from the end
        AppleMacbookM3ProReviewsPageRecyclerView.layoutManager = applemacbookm3proLayoutManager

        // Initialize the adapter
        laptopAppleMacbookM3ProReviewsAdapter = laptopAppleMacbookM3ProReviewsAdapter(reviewListOfAppleMacbookM3Pro)
        AppleMacbookM3ProReviewsPageRecyclerView.adapter = laptopAppleMacbookM3ProReviewsAdapter

        // Retrieve the number of reviews from SharedPreferences
        AppleMacbookM3ProMaxNumberOfReviews = getAppleMacbookM3ProNumberOfReviews()
        getAppleMacbookM3ProBySmartphoneId()

        // Check if there's a new review passed from SamsungGalaxyS24RateAndReviewActivity
        intent.getParcelableExtra<AppleMacbookM3ProReview>("APPLEMACBOOKM3PRO_NEW_REVIEW")?.let { newReview ->
            reviewListOfAppleMacbookM3Pro.add(0, newReview) // Add the new review at the top of the list
            laptopAppleMacbookM3ProReviewsAdapter.notifyItemInserted(0) // Notify adapter that an item was inserted at position 0
            AppleMacbookM3ProReviewsPageRecyclerView.scrollToPosition(0) // Scroll to the top of the list
            onReviewSubmitted() // Call to update number of reviews

            // Fetch reviews for the smartphone when the activity is created
            val smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 3)
        }
        val backButton: ImageView = findViewById(R.id.AppleMacbookM3ProReviewsPageBackButton)
        backButton.setOnClickListener {
            val intent = Intent(this, Iphone16ProMaxFullDetails::class.java).apply {
                putParcelableArrayListExtra("APPLEMACBOOKM3PRO_REVIEWS_LIST", ArrayList(reviewListOfAppleMacbookM3Pro)) // Pass the reviews list
            }
            startActivity(intent)
            finish()
        }
    }
    private fun getAppleMacbookM3ProNumberOfReviews(): Int {
        val sharedPreferences = getSharedPreferences("MyAppleMacbookM3ProPreferences", MODE_PRIVATE)
        return sharedPreferences.getInt("AppleMacbookM3ProMaxNumberOfReviews", 0) // Default to 0 if not found
    }
    private fun getAppleMacbookM3ProBySmartphoneId() {
        val smartphoneId = 3
        RetrofitClient.apiService.getAppleMacbookM3ProBySmartphoneId(smartphoneId).enqueue(object :
            Callback<List<AppleMacbookM3ProReview>> {
            override fun onResponse(call: Call<List<AppleMacbookM3ProReview>>, response: Response<List<AppleMacbookM3ProReview>>) {
                if (response.isSuccessful && response.body() != null) {
                    reviewListOfAppleMacbookM3Pro.clear()
                    reviewListOfAppleMacbookM3Pro.addAll(response.body()!!)
                    laptopAppleMacbookM3ProReviewsAdapter.notifyDataSetChanged()

                    AppleMacbookM3ProMaxNumberOfReviews = reviewListOfAppleMacbookM3Pro.size
                    saveIphone16ProMaxReviewsPageNumberOfReviews(AppleMacbookM3ProMaxNumberOfReviews)
                } else {
                    Log.e("Iphone16ProMaxReviewsPage", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<AppleMacbookM3ProReview>>, t: Throwable) {
                Log.e("laptopAppleMacbookM3ProReviewsPage", "Failure: ${t.message}")
            }
        })
    }
    // Function to be called when a new review is submitted
    private fun onReviewSubmitted() {
        // Increment the number of reviews
        AppleMacbookM3ProMaxNumberOfReviews++
        // Save the updated number of reviews to SharedPreferences
        saveIphone16ProMaxReviewsPageNumberOfReviews(AppleMacbookM3ProMaxNumberOfReviews)
    }
    private fun saveIphone16ProMaxReviewsPageNumberOfReviews(count: Int) {
        val sharedPreferences = getSharedPreferences("MyAppleMacbookM3ProMaxPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("AppleMacbookM3ProMaxNumberOfReviews", count)
        editor.apply() // Save the changes asynchronously
        Log.d("laptopAppleMacbookM3ProReviewsPage", "Saved AppleMacbookM3ProMaxNumberOfReviews: $count")
    }
}