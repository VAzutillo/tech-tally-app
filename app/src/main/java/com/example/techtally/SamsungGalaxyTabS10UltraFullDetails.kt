package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.techtally.databinding.ActivitySamsungGalaxyTabS10UltraFullDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SamsungGalaxyTabS10UltraFullDetails : AppCompatActivity() {

    // Binding object to access views in the activity's layout
    private lateinit var binding: ActivitySamsungGalaxyTabS10UltraFullDetailsBinding

    // Variables to track if the user is a guest and the number of reviews
    private var isGuest: Boolean = true  // This flag determines if the user is a guest or logged in
    private var number_of_reviews: Int = 0 // Track the number of reviews

    // RecyclerView and Adapter for displaying reviews list
    private lateinit var SamsungGalaxyTabS10UltraReviewsPageRecyclerView: RecyclerView
    private lateinit var SamsungGalaxyTabS10UltraReviewsAdapter: SamsungGalaxyTabS10UltraReviewsAdapter
    private val reviewListOfSamsungGalaxyTabS10Ultra = mutableListOf<SamsungGalaxyTabS10UltraReview>()

    // Array to track the clicked state of each rating button (for 5 buttons)
    private var buttonStates = BooleanArray(5)

    // Variables for rating calculations
    private var percentage_of_ratings: Float = 0.0f
    private var smartphoneId = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySamsungGalaxyTabS10UltraFullDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize the RecyclerView and set the layout manager
        SamsungGalaxyTabS10UltraReviewsPageRecyclerView = findViewById(R.id.SamsungGalaxyTabS10UltraReviewsPageRecyclerView)
        val samsunggalaxytabs10ultraLayoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true // Reverse layout to show newest items at the top
            stackFromEnd = true  // Stack items from the end
        }
        SamsungGalaxyTabS10UltraReviewsPageRecyclerView.layoutManager = samsunggalaxytabs10ultraLayoutManager // Set the layout manager
        SamsungGalaxyTabS10UltraReviewsAdapter = SamsungGalaxyTabS10UltraReviewsAdapter(reviewListOfSamsungGalaxyTabS10Ultra)
        SamsungGalaxyTabS10UltraReviewsPageRecyclerView.adapter = SamsungGalaxyTabS10UltraReviewsAdapter

        // Initialize the guest status
        updateGuestStatus()

        // Set up all buttons and TextViews from the binding object
        setupClickListeners()

        // Retrieve the guest status from the intent if passed from LoginActivity
        isGuest = intent.getBooleanExtra("IS_GUEST", true)

        // Retrieve the reviews list from intent
        intent.getParcelableArrayListExtra<SamsungGalaxyTabS10UltraReview>("SAMSUNGGALAXYTABS10ULTRA_REVIEWS_LIST")?.let { newReview ->
            reviewListOfSamsungGalaxyTabS10Ultra.addAll(0, newReview) // Add the received reviews to the list
            SamsungGalaxyTabS10UltraReviewsAdapter.notifyDataSetChanged() // Notify adapter about the changes
            SamsungGalaxyTabS10UltraReviewsAdapter.notifyItemInserted(0)
            SamsungGalaxyTabS10UltraReviewsPageRecyclerView.scrollToPosition(0)
        }
        // Fetch latest reviews from API
        fetchSmartphoneRatings()

        // Scroll to top functionality for ScrollView
        val scrollView: ScrollView = findViewById(R.id.SamsungGalaxyTabS10UltraFullDetailsScrollView)
        val backToTopTextView: TextView = findViewById(R.id.SamsungGalaxyTabS10UltraBackToTop)

        // Scroll to the top of the ScrollView
        backToTopTextView.setOnClickListener {
            scrollView.scrollTo(0, 0)
        }
        // Navigate from SamsungGalaxyTabS10UltraFullDetails to SamsungGalaxyTabS10UltraReviewsPage
        val goToSamsungGalaxyTabS10UltraSeeAllReviews1 = findViewById<ImageView>(R.id.SamsungGalaxyTabS10UltraSeeAllReviews1)
        goToSamsungGalaxyTabS10UltraSeeAllReviews1.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyTabS10UltraReviewsPage::class.java)
            startActivity(intent)
        }
        // Navigate from SamsungGalaxyTabS10UltraFullDetails to SamsungGalaxyTabS10UltraReviewsPage
        val goToSamsungGalaxyTabS10UltraSeeAllReviews2 = findViewById<TextView>(R.id.SamsungGalaxyTabS10UltraSeeAllReviews2)
        goToSamsungGalaxyTabS10UltraSeeAllReviews2.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyTabS10UltraReviewsPage::class.java)
            startActivity(intent)
        }
        // Navigate back to UserDashboardActivity when back button is clicked
        val backToUserDashboardActivity = findViewById<ImageView>(R.id.SamsungGalaxyTabS10UltraFullDetailsBackButton)
        backToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }
        // Navigate from SamsungGalaxyTabS10UltraFullDetails to SamsungGalaxyTabS10UltraReviewsPage
        val goToSamsungGalaxyTabS10UltraReviewsPage = findViewById<TextView>(R.id.SamsungGalaxyTabS10UltraNumberOfReviews)
        goToSamsungGalaxyTabS10UltraReviewsPage.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyTabS10UltraReviewsPage::class.java)
            startActivity(intent)
        }
        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 4)
    }
    private fun fetchSmartphoneRatings() {
        RetrofitClient.apiService.getRatings(smartphoneId).enqueue(object : Callback<SmartphoneRatingsResponse> {
            override fun onResponse(
                call: Call<SmartphoneRatingsResponse>,
                response: Response<SmartphoneRatingsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val ratingsData = response.body()!!
                    number_of_reviews = ratingsData.number_of_reviews
                    percentage_of_ratings = ratingsData.percentage_of_ratings

                    // Update UI
                    binding.SamsungGalaxyTabS10UltraNumberOfReviews.text = "$number_of_reviews reviews"
                    binding.SamsungGalaxyTabS10UltraPercentageOfRatings.text = "${percentage_of_ratings}"
                }
            }

            override fun onFailure(call: Call<SmartphoneRatingsResponse>, t: Throwable) {
            }
        })
    }
    // onResume is called when the activity is resumed after being paused
    override fun onResume() {
        super.onResume()
        updateGuestStatus() // Refresh the guest status when the activity is resumed
        fetchSmartphoneRatings()
    }

    // Method to update the guest status by checking shared preferences
    private fun updateGuestStatus() {
        // Retrieve the guest status from shared preferences
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        isGuest = sharedPreferences.getBoolean("IS_GUEST", true) // Default to true if not found

        // Get the username from shared preferences (if available)
        val username = sharedPreferences.getString("USERNAME", "") ?: ""
    }

    // Set up click listeners for rating buttons
    private fun setupClickListeners() {
        binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally1.setOnClickListener { handleButtonClick(0, binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally1) }
        binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally2.setOnClickListener { handleButtonClick(1, binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally2) }
        binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally3.setOnClickListener { handleButtonClick(2, binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally3) }
        binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally4.setOnClickListener { handleButtonClick(3, binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally4) }
        binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally5.setOnClickListener { handleButtonClick(4, binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally5) }
    }

    // Handle rating button click based on user input
    private fun handleButtonClick(index: Int, button: Button) {
        if (isGuest) {
            showCustomDialog2()     // Show guest prompt if user is not logged in
        } else {
            // Update button states and change background colors based on rating
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black)

            for (i in buttonStates.indices) {
                buttonStates[i] = i <= index
                val targetButton = when (i) {
                    0 -> binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally1
                    1 -> binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally2
                    2 -> binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally3
                    3 -> binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally4
                    4 -> binding.SamsungGalaxyTabS10UltraFullDetailsRateButtonTally5
                    else -> null
                }
                targetButton?.backgroundTintList = if (buttonStates[i]) {
                    ContextCompat.getColorStateList(this, R.color.black)
                } else {
                    ContextCompat.getColorStateList(this, R.color.backgroundColorOfButton)
                }
            }
            val selectedRating = index + 1

            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val userName = sharedPreferences.getString("USER_NAME", "Guest") ?: "Guest"

            val intent = Intent(this, SamsungGalaxyTabS10UltraRateAndReviewActivity::class.java).apply {
                putExtra("SAMSUNGGALAXYTABS10ULTRA_SELECTED_RATING", selectedRating)
                putExtra("USER_NAME", userName)
                putExtra("SMARTPHONE_ID", smartphoneId)
            }
            startActivityForResult(intent, REQUEST_CODE_REVIEW)
        }
    }

    // Show an alert dialog for guests, prompting them to sign up or cancel
    private fun showCustomDialog2() {
        // Inflate the custom layout for the dialog
        val dialogView = layoutInflater.inflate(R.layout.dialog_custom2, null)

        // Create the AlertDialog with the custom view
        val builder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)

        // Get references to the views in the custom layout
        val dialogMessage: TextView = dialogView.findViewById(R.id.dialogMessage)
        val cancelButton: Button = dialogView.findViewById(R.id.LogoutNoBtn)
        val signUpButton: Button = dialogView.findViewById(R.id.LogoutYesBtn)

        // Set the dialog message
        dialogMessage.text = "To continue this activity you need an account first."

        // Create the dialog instance
        val dialog = builder.create()

        // Set click listener for the Sign Up button (navigates to SignupActivity)
        signUpButton.setOnClickListener {
            // Start the SignupActivity when the button is clicked
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
            dialog.dismiss()  // Dismiss the dialog after starting the activity
        }

        // Set a click listener for the Cancel button
        cancelButton.setOnClickListener {
            // Dismiss the dialog when Cancel is clicked
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }
    companion object {
        private const val REQUEST_CODE_REVIEW = 4
    }
}