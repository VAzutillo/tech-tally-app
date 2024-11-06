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

class SamsungGalaxyTabS10UltraReviewsPage : AppCompatActivity() {

    private lateinit var SamsungGalaxyTabS10UltraReviewsPageRecyclerView: RecyclerView
    private lateinit var SamsungGalaxyTabS10UltraReviewsAdapter: SamsungGalaxyTabS10UltraReviewsAdapter
    private val reviewListOfSamsungGalaxyTabS10Ultra= mutableListOf<SamsungGalaxyTabS10UltraReview>() // List to hold review data
    private var SamsungGalaxyTabS10UltraNumberOfReviews: Int = 0 // Variable to hold the number of reviews


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_samsung_galaxy_tab_s10_ultra_reviews_page)

        // Find the RecyclerView in activity_reviews_page.xml
        SamsungGalaxyTabS10UltraReviewsPageRecyclerView = findViewById(R.id.SamsungGalaxyTabS10UltraReviewsPageRecyclerView)

        // Set up the RecyclerView with a LinearLayoutManager in reverse order
        val samsunggalaxytabs10ultraLayoutManager = LinearLayoutManager(this)
        samsunggalaxytabs10ultraLayoutManager.reverseLayout = true // Reverse layout to show newest items at the top
        samsunggalaxytabs10ultraLayoutManager.stackFromEnd = true  // Stack items from the end
        SamsungGalaxyTabS10UltraReviewsPageRecyclerView.layoutManager = samsunggalaxytabs10ultraLayoutManager

        // Initialize the adapter
        SamsungGalaxyTabS10UltraReviewsAdapter = SamsungGalaxyTabS10UltraReviewsAdapter(reviewListOfSamsungGalaxyTabS10Ultra)
        SamsungGalaxyTabS10UltraReviewsPageRecyclerView.adapter = SamsungGalaxyTabS10UltraReviewsAdapter

        // Retrieve the number of reviews from SharedPreferences
        SamsungGalaxyTabS10UltraNumberOfReviews = getSamsungGalaxyTabS10UltraNumberOfReviews()
        getSamsungGalaxyTabS10UltraBySmartphoneId()

        // Check if there's a new review passed from SamsungGalaxyS24RateAndReviewActivity
        intent.getParcelableExtra<SamsungGalaxyTabS10UltraReview>("SAMSUNGGALAXYTABS10ULTRA_NEW_REVIEW")?.let { newReview ->
            reviewListOfSamsungGalaxyTabS10Ultra.add(0, newReview) // Add the new review at the top of the list
            SamsungGalaxyTabS10UltraReviewsAdapter.notifyItemInserted(0) // Notify adapter that an item was inserted at position 0
            SamsungGalaxyTabS10UltraReviewsPageRecyclerView.scrollToPosition(0) // Scroll to the top of the list
            onReviewSubmitted() // Call to update number of reviews

            // Fetch reviews for the smartphone when the activity is created
            val smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 4)
        }
        val backButton: ImageView = findViewById(R.id.SamsungGalaxyTabS10UltraReviewsPageBackButton)
        backButton.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyTabS10UltraFullDetails::class.java).apply {
                putParcelableArrayListExtra("SAMSUNGGALAXYTABS10ULTRA_REVIEWS_LIST", ArrayList(reviewListOfSamsungGalaxyTabS10Ultra)) // Pass the reviews list
            }
            startActivity(intent)
            finish()
        }
    }
    private fun getSamsungGalaxyTabS10UltraNumberOfReviews(): Int {
        val sharedPreferences = getSharedPreferences("MySamsungGalaxyTabS10UltraPreferences", MODE_PRIVATE)
        return sharedPreferences.getInt("SamsungGalaxyTabS10UltraNumberOfReviews", 0) // Default to 0 if not found
    }

    private fun getSamsungGalaxyTabS10UltraBySmartphoneId() {
        val smartphoneId = 4 // Replace this with the desired smartphone ID
        RetrofitClient.apiService.getSamsungGalaxyTabS10UltraBySmartphoneId(smartphoneId).enqueue(object : Callback<List<SamsungGalaxyTabS10UltraReview>> {
            override fun onResponse(call: Call<List<SamsungGalaxyTabS10UltraReview>>, response: Response<List<SamsungGalaxyTabS10UltraReview>>) {
                if (response.isSuccessful && response.body() != null) {
                    reviewListOfSamsungGalaxyTabS10Ultra.clear()
                    reviewListOfSamsungGalaxyTabS10Ultra.addAll(response.body()!!)
                    SamsungGalaxyTabS10UltraReviewsAdapter.notifyDataSetChanged()

                    SamsungGalaxyTabS10UltraNumberOfReviews = reviewListOfSamsungGalaxyTabS10Ultra.size
                    saveSamsungGalaxyTabS10UltraReviewsPageNumberOfReviews(SamsungGalaxyTabS10UltraNumberOfReviews)
                }
            }
            override fun onFailure(call: Call<List<SamsungGalaxyTabS10UltraReview>>, t: Throwable) {

            }
        })
    }
    // Function to be called when a new review is submitted
    private fun onReviewSubmitted() {
        // Increment the number of reviews
        SamsungGalaxyTabS10UltraNumberOfReviews++
        // Save the updated number of reviews to SharedPreferences
        saveSamsungGalaxyTabS10UltraReviewsPageNumberOfReviews(SamsungGalaxyTabS10UltraNumberOfReviews)
    }
    private fun saveSamsungGalaxyTabS10UltraReviewsPageNumberOfReviews(count: Int) {
        val sharedPreferences = getSharedPreferences("MySamsungGalaxyTabS10UltraPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("SamsungGalaxyTabS10UltraNumberOfReviews", count)
        editor.apply() // Save the changes asynchronously
    }
}