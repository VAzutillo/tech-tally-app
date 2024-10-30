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
import com.example.techtally.databinding.ActivityIphone16ProMaxFullDetailsBinding
import com.example.techtally.databinding.ActivityLaptopMacbookM3ProFullDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class laptopAppleMacbookM3ProFullDetails : AppCompatActivity() {

    // Binding object to access views in the activity's layout
    private lateinit var binding: ActivityLaptopMacbookM3ProFullDetailsBinding

    // Variables to track if the user is a guest and the number of reviews
    private var isGuest: Boolean = true  // This flag determines if the user is a guest or logged in
    private var number_of_reviews: Int = 0 // Track the number of reviews

    // RecyclerView and Adapter for displaying reviews list
    private lateinit var AppleMacbookM3ProReviewsPageRecyclerView: RecyclerView
    private lateinit var laptopAppleMacbookM3ProReviewsAdapter: laptopAppleMacbookM3ProReviewsAdapter
    private val reviewListOfAppleMacbookM3Pro = mutableListOf<AppleMacbookM3ProReview>()

    // Array to track the clicked state of each rating button (for 5 buttons)
    private var buttonStates = BooleanArray(5)

    // Variables for rating calculations
    private var percentage_of_ratings: Float = 0.0f
    private var smartphoneId = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLaptopMacbookM3ProFullDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the RecyclerView and set the layout manager
        AppleMacbookM3ProReviewsPageRecyclerView = findViewById(R.id.AppleMacbookM3ProReviewsPageRecyclerView)
        val applemacbookm3proLayoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true // Reverse layout to show newest items at the top
            stackFromEnd = true  // Stack items from the end
        }
        AppleMacbookM3ProReviewsPageRecyclerView.layoutManager = applemacbookm3proLayoutManager // Set the layout manager
        laptopAppleMacbookM3ProReviewsAdapter = laptopAppleMacbookM3ProReviewsAdapter(reviewListOfAppleMacbookM3Pro)
        AppleMacbookM3ProReviewsPageRecyclerView.adapter = laptopAppleMacbookM3ProReviewsAdapter

        // Initialize the guest status
        updateGuestStatus()

        // Set up all buttons and TextViews from the binding object
        setupClickListeners()

        // Retrieve the guest status from the intent if passed from LoginActivity
        isGuest = intent.getBooleanExtra("IS_GUEST", true)

        // Retrieve the reviews list from intent
        intent.getParcelableArrayListExtra<AppleMacbookM3ProReview>("APPLEMACBOOKM3PRO_REVIEWS_LIST")?.let { newReview ->
            reviewListOfAppleMacbookM3Pro.addAll(0, newReview) // Add the received reviews to the list
            laptopAppleMacbookM3ProReviewsAdapter.notifyDataSetChanged() // Notify adapter about the changes
            laptopAppleMacbookM3ProReviewsAdapter.notifyItemInserted(0)
            AppleMacbookM3ProReviewsPageRecyclerView.scrollToPosition(0)
        }
        // Fetch latest reviews from API
        fetchSmartphoneRatings()

        // Scroll to top functionality for ScrollView
        val scrollView: ScrollView = findViewById(R.id.AppleMacbookM3ProFullDetailsScrollView)
        val backToTopTextView: TextView = findViewById(R.id.AppleMacbookM3ProBackToTop)

        // Scroll to the top of the ScrollView
        backToTopTextView.setOnClickListener {
            scrollView.scrollTo(0, 0)
        }
        // Navigate from laptopAppleMacbookM3ProFullDetails to laptopAppleMackbookM3ProReviewsPage
        val goToLaptopAppleMacbookM3ProSeeAllRatingsAndReviews1 = findViewById<ImageView>(R.id.AppleMacbookM3ProSeeAllReviews1)
        goToLaptopAppleMacbookM3ProSeeAllRatingsAndReviews1.setOnClickListener {
            val intent = Intent(this, laptopAppleMacbookM3ProReviewsPage::class.java)
            startActivity(intent)
        }
        // Navigate from laptopAppleMacbookM3ProFullDetails to laptopAppleMackbookM3ProReviewsPage
        val goToLaptopAppleMacbookM3ProSeeAllRatingsAndReviews2 = findViewById<TextView>(R.id.AppleMacbookM3ProSeeAllReviews2)
        goToLaptopAppleMacbookM3ProSeeAllRatingsAndReviews2.setOnClickListener {
            val intent = Intent(this, laptopAppleMacbookM3ProReviewsPage::class.java)
            startActivity(intent)
        }
        // Navigate back to UserDashboardActivity when back button is clicked
        val backToUserDashboardActivity = findViewById<ImageView>(R.id.AppleMacbookM3ProFullDetailsBackButton)
        backToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }
        // Navigate from Iphone16ProMaxFullDetails to Iphone16ProMaxReviewsPage
        val goTolaptopAppleMacbookM3ProReviewsPage = findViewById<TextView>(R.id.AppleMacbookM3ProNumberOfReviews)
        goTolaptopAppleMacbookM3ProReviewsPage.setOnClickListener {
            val intent = Intent(this, laptopAppleMacbookM3ProReviewsPage::class.java)
            startActivity(intent)
        }
        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 3)
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
                    binding.AppleMacbookM3ProNumberOfReviews.text = "$number_of_reviews reviews"
                    binding.AppleMacbookM3ProPercentageOfRatings.text = "${percentage_of_ratings}"
                }
            }

            override fun onFailure(call: Call<SmartphoneRatingsResponse>, t: Throwable) {
                Log.e("laptopAppleMacbookM3ProFullDetails", "Error fetching ratings: ${t.message}")
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
        Log.d("laptopAppleMacbookM3ProFullDetails", "isGuest: $isGuest")
    }

    // Set up click listeners for rating buttons
    private fun setupClickListeners() {
        binding.AppleMacbookM3ProFullDetailsRateButtonTally1.setOnClickListener { handleButtonClick(0, binding.AppleMacbookM3ProFullDetailsRateButtonTally1) }
        binding.AppleMacbookM3ProFullDetailsRateButtonTally2.setOnClickListener { handleButtonClick(1, binding.AppleMacbookM3ProFullDetailsRateButtonTally2) }
        binding.AppleMacbookM3ProFullDetailsRateButtonTally3.setOnClickListener { handleButtonClick(2, binding.AppleMacbookM3ProFullDetailsRateButtonTally3) }
        binding.AppleMacbookM3ProFullDetailsRateButtonTally4.setOnClickListener { handleButtonClick(3, binding.AppleMacbookM3ProFullDetailsRateButtonTally4) }
        binding.AppleMacbookM3ProFullDetailsRateButtonTally5.setOnClickListener { handleButtonClick(4, binding.AppleMacbookM3ProFullDetailsRateButtonTally5) }
    }

    // Handle rating button click based on user input
    private fun handleButtonClick(index: Int, button: Button) {
        Log.d("laptopAppleMacbookM3ProFullDetails", "Button clicked: $index. isGuest: $isGuest")
        if (isGuest) {
            showCustomDialog2()     // Show guest prompt if user is not logged in
        } else {
            // Update button states and change background colors based on rating
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.black)

            for (i in buttonStates.indices) {
                buttonStates[i] = i <= index
                val targetButton = when (i) {
                    0 -> binding.AppleMacbookM3ProFullDetailsRateButtonTally1
                    1 -> binding.AppleMacbookM3ProFullDetailsRateButtonTally2
                    2 -> binding.AppleMacbookM3ProFullDetailsRateButtonTally3
                    3 -> binding.AppleMacbookM3ProFullDetailsRateButtonTally4
                    4 -> binding.AppleMacbookM3ProFullDetailsRateButtonTally5
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

            val intent = Intent(this, laptopAppleMacbookM3ProRateAndReviewActivity::class.java).apply {
                putExtra("APPLEMACBOOKM3PRO_SELECTED_RATING", selectedRating)
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
        private const val REQUEST_CODE_REVIEW = 3
    }
}