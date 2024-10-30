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

class Iphone16ProMaxReviewsPage : AppCompatActivity() {

    private lateinit var Iphone16ProMaxReviewsPageRecyclerView: RecyclerView
    private lateinit var Iphone16ProMaxReviewsAdapter: Iphone16ProMaxReviewsAdapter
    private val reviewListOfIphone16ProMax = mutableListOf<Iphone16ProMaxReview>() // List to hold review data
    private var iPhone16ProMaxNumberOfReviews: Int = 0 // Variable to hold the number of reviews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iphone16_pro_max_reviews_page)

        // Find the RecyclerView in activity_reviews_page.xml
        Iphone16ProMaxReviewsPageRecyclerView = findViewById(R.id.Iphone16ProMaxReviewsPageRecyclerView)

        // Set up the RecyclerView with a LinearLayoutManager in reverse order
        val iphoneLayoutManager = LinearLayoutManager(this)
        iphoneLayoutManager.reverseLayout = true // Reverse layout to show newest items at the top
        iphoneLayoutManager.stackFromEnd = true  // Stack items from the end
        Iphone16ProMaxReviewsPageRecyclerView.layoutManager = iphoneLayoutManager

        // Initialize the adapter
        Iphone16ProMaxReviewsAdapter = Iphone16ProMaxReviewsAdapter(reviewListOfIphone16ProMax)
        Iphone16ProMaxReviewsPageRecyclerView.adapter = Iphone16ProMaxReviewsAdapter

        // Retrieve the number of reviews from SharedPreferences
        iPhone16ProMaxNumberOfReviews = getIphone16ProMaxNumberOfReviews()
        getIphone16ProMaxBySmartphoneId()

        // Check if there's a new review passed from SamsungGalaxyS24RateAndReviewActivity
        intent.getParcelableExtra<Iphone16ProMaxReview>("IPHONE16PROMAX_NEW_REVIEW")?.let { newReview ->
            reviewListOfIphone16ProMax.add(0, newReview) // Add the new review at the top of the list
            Iphone16ProMaxReviewsAdapter.notifyItemInserted(0) // Notify adapter that an item was inserted at position 0
            Iphone16ProMaxReviewsPageRecyclerView.scrollToPosition(0) // Scroll to the top of the list
            onReviewSubmitted() // Call to update number of reviews

            // Fetch reviews for the smartphone when the activity is created
            val smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 2)
        }
        val backButton: ImageView = findViewById(R.id.Iphone16ProMaxReviewsPageBackButton)
        backButton.setOnClickListener {
            val intent = Intent(this, Iphone16ProMaxFullDetails::class.java).apply {
                putParcelableArrayListExtra("IPHONE16PROMAX_REVIEWS_LIST", ArrayList(reviewListOfIphone16ProMax)) // Pass the reviews list
            }
            startActivity(intent)
            finish()
        }
    }
    private fun getIphone16ProMaxNumberOfReviews(): Int {
        val sharedPreferences = getSharedPreferences("MyIphone16ProMaxPreferences", MODE_PRIVATE)
        return sharedPreferences.getInt("iPhone16ProMaxNumberOfReviews", 0) // Default to 0 if not found
    }

    private fun getIphone16ProMaxBySmartphoneId() {
        val smartphoneId = 2 // Replace this with the desired smartphone ID
        RetrofitClient.apiService.getIphone16ProMaxBySmartphoneId(smartphoneId).enqueue(object : Callback<List<Iphone16ProMaxReview>> {
            override fun onResponse(call: Call<List<Iphone16ProMaxReview>>, response: Response<List<Iphone16ProMaxReview>>) {
                if (response.isSuccessful && response.body() != null) {
                    reviewListOfIphone16ProMax.clear()
                    reviewListOfIphone16ProMax.addAll(response.body()!!)
                    Iphone16ProMaxReviewsAdapter.notifyDataSetChanged()

                    iPhone16ProMaxNumberOfReviews = reviewListOfIphone16ProMax.size
                    saveIphone16ProMaxReviewsPageNumberOfReviews(iPhone16ProMaxNumberOfReviews)
                } else {
                    Log.e("Iphone16ProMaxReviewsPage", "Error: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Iphone16ProMaxReview>>, t: Throwable) {
                Log.e("Iphone16ProMaxReviewsPage", "Failure: ${t.message}")
            }
        })
    }
    // Function to be called when a new review is submitted
    private fun onReviewSubmitted() {
        // Increment the number of reviews
        iPhone16ProMaxNumberOfReviews++
        // Save the updated number of reviews to SharedPreferences
        saveIphone16ProMaxReviewsPageNumberOfReviews(iPhone16ProMaxNumberOfReviews)
    }
    private fun saveIphone16ProMaxReviewsPageNumberOfReviews(count: Int) {
        val sharedPreferences = getSharedPreferences("MyIphone16ProMaxPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("iPhone16ProMaxNumberOfReviews", count)
        editor.apply() // Save the changes asynchronously
        Log.d("Iphone16ProMaxReviewsPage", "Saved iPhone16ProMaxNumberOfReviews: $count")
    }
}
