package com.example.techtally

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.techtally.databinding.ActivityUserDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDashboardActivity : AppCompatActivity() {
    // View Binding for the layout
    private lateinit var binding: ActivityUserDashboardBinding

    // Declaring UI components
    // For User's profile
    private lateinit var greetingTextView: TextView     // For tvLogin/Signup and For User's name
    private lateinit var profilePopup: FrameLayout      // Layout for profile pop up when click
    private lateinit var profileBtn2: ImageView         // Profile Logo and exit button for pop up
    private lateinit var profileBtn: ImageView          // Profile Logo and to view pop up
    private lateinit var loginSignupBtn: TextView       // Another tvLogin/Signup and get User's name inside the pop up
    // For logout
    private lateinit var logoutBtn: ImageView           // LogoutImageView
    private var percentage_of_ratings: Float = 0.0f

    private var smartphoneId = 0
    private lateinit var searchBar: AutoCompleteTextView
    private val suggestions = listOf(
        "Samsung",                     //Samsung Brands
        "Samsung Galaxy S24",
        "Samsung Galaxy S24 Ultra",
        "Samsung Galaxy Tab S10",
        "Samsung Galaxy Book4",
        "Samsung Galaxy Book3",
        "Apple",                        //Apple Brands
        "Apple iPhone 16 Pro Max",
        "Apple Macbook M3 Pro",
        "Apple M2 Macbook Pro 14",
        "Xiaomi",                       //Xiaomi Brands
        "Xiaomi 14 Ultra",
        "Xiaomi Pad 6 Pro",
        "Xiaomi Notebook Pro",
        "Oppo",                         //Oppo Brands
        "Oppo Reno 12 Pro",
        "Oppo Pad 2",
        "Realme",                       //Realme Brands
        "Realme 13 Pro Plus",
        "Realme Pad 2",
        "Vivo",                         //Vivo Brands
        "Vivo X100 Pro",
        "Google",                       //Pixel Brands
        "Google Pixel 9 Pro",
        "Infinix",                      //Infinix Brands
        "Infinix Note 40 Pro+",
        "Huawei",                   //Huawei Brands
        "Huawei Matebook 15",
        "Huawei Matebook X pro",
        "Lenovo",                   //Lenovo Brands
        "Lenovo Legion 7i",
        "Lenovo ThinkPad T14s",
        "Lenovo Chromebook Duet 11"

    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enables edge-to-edge display mode
        enableEdgeToEdge()
        // Inflate the layout using view binding
        binding = ActivityUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val searchBar = findViewById<AutoCompleteTextView>(R.id.searchBar)
        val dropdownAdapter =
            ArrayAdapter<String>(this, R.layout.dropdown_item, mutableListOf())
        searchBar.setAdapter(dropdownAdapter)

        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Get suggestions based on the current input
                val filteredList = getFilteredSuggestions(s.toString())

                // Update the dropdownAdapter with filtered results or "Smartphone not found" if no match
                dropdownAdapter.clear()
                dropdownAdapter.addAll(filteredList)
                dropdownAdapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        searchBar.setOnItemClickListener { parent, view, position, id ->
            val selectedSuggestion = parent.getItemAtPosition(position) as String
            if (selectedSuggestion == "Smartphone not found") {
                // Optional: Show a toast message or handle accordingly
                Toast.makeText(this, "Smartphone not found", Toast.LENGTH_SHORT).show()
            } else {
                navigateToDetailPage(selectedSuggestion)
            }
        }

        // For User's profile
        greetingTextView =
            findViewById(R.id.greetingTextView)      // Initialize tvLogin/Signup and User's name
        profilePopup =
            binding.profilePopup                         // Initialize Layout profile pop up
        profileBtn2 =
            binding.profileBtn2                           // Initialize profile button for open pop up
        profileBtn =
            binding.profileBtn                             // Initialize profile button for close pop up
        loginSignupBtn =
            binding.loginSignupBtn                     // Initialize tvLoginSignup and User's name inside the pop up


        // Initialize logout button
        logoutBtn = findViewById(R.id.logoutBtn)

        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 1)
        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 2)
        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 3)
        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 4)

        fetchSamsungS24Ratings()
        fetchIphone16ProMaxRatings()
        fetchAppleMacbookM3ProRatings()
        fetchSamsungGalaxyTabS10UltraRatings()

        // Display the logout confirmation popup
        logoutBtn.setOnClickListener {
            showLogoutDialog()
        }

        // Get SharedPreferences data for user login and guest status
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("IS_LOGGED_IN", false)
        val isGuest = sharedPreferences.getBoolean("IS_GUEST", false)
        val userName = sharedPreferences.getString("USER_NAME", "")
        val greetingTextView: TextView = findViewById(R.id.greetingTextView)
        val loginSignupBtn: TextView = findViewById(R.id.loginSignupBtn)

        // Update UI based on login or guest status
        if (isGuest) {
            // If the user is a guest, display "Login/Signup"
            greetingTextView.text = "Login/Signup"
            greetingTextView.setOnClickListener {
                // Navigate from UserDashboardActivity to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            // If the user is a guest, display "Login/Signup" inside the profile popup
            loginSignupBtn.text = "Login/Signup"
            loginSignupBtn.setOnClickListener {
                // Navigate from profile popup to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            // Disable profileBtn for guests
            profileBtn.isEnabled = false
        } else if (isLoggedIn) {
            // If the user is logged in, show the username
            greetingTextView.text = "Hello, $userName!"
            loginSignupBtn.text = "$userName!"
            // Enable profileBtn for logged-in users
            profileBtn.isEnabled = true
        } else {
            // Default case show "Login/Signup"
            greetingTextView.text = "Login/Signup"
            greetingTextView.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            loginSignupBtn.text = "Login/Signup"
            loginSignupBtn.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            // Disable profileBtn for non-logged-in and non-guest users
            profileBtn.isEnabled = false
        }

// Show popup when profile button is clicked
        profileBtn.setOnClickListener {
            if (isLoggedIn) {
                // Show profile popup only if the user is logged in
                profilePopup.visibility = View.VISIBLE
            }
        }

// Hide popup when profile button2 is clicked
        profileBtn2.setOnClickListener {
            // Hide profile popup
            profilePopup.visibility = View.GONE
        }


        //ito yong code sa horizontal scrollview
        class ImageAdapter(
            private val context: Context,
            private val images: List<Int>,
            private val titles: List<String>
        ) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

            inner class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
                val imageView: ImageView = view.findViewById(R.id.samsung_galaxy_s24_ultra)
                val imageTitle: TextView = view.findViewById(R.id.ImageTitle1)
            }

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
                return ImageViewHolder(view)
            }

            override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
                holder.imageView.setImageResource(images[position])
                holder.imageTitle.text = titles[position]

                // Set an OnClickListener for each image based on position
                holder.imageView.setOnClickListener {
                    val intent = when (position) {
                        0 -> Intent(context, SamsungGalaxyS24UltraFullDetails::class.java)
                        1 -> Intent(context, Xiaomi14UltraFullDetailsUserDashboard::class.java)
                        2 -> Intent(context, Iphone16ProMaxUserDashboard::class.java)
                        3 -> Intent(context, SmartphonesOppoReno12ProUserDashboard::class.java)
                        4 -> Intent(context, Realme13ProPlusUserDashboard::class.java)
                        else -> null
                    }
                    intent?.let { context.startActivity(it) }
                }
            }

            override fun getItemCount() = images.size
        }

        val images = listOf(
            R.drawable.samsungs24ultra_fea,
            R.drawable.xiaomi14_feature,
            R.drawable.iphone16promax_fea,
            R.drawable.opporeno12pro_fea,
            R.drawable.realme13proplus_feature
        )

        val titles = listOf(
            "",
            "",
            "",
            "",
            ""
        )

        val recyclerView: RecyclerView = findViewById(R.id.RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ImageAdapter(this, images, titles) // Pass the context as 'this'

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)


        // Navigate from UserDashboardActivity to SamsungGalaxyS24FullDetailsActivity
        val goTopSamsungGalaxyS24FullDetails =
            findViewById<TextView>(R.id.samsungGalaxyS24SeeMoreButton)
        goTopSamsungGalaxyS24FullDetails.setOnClickListener {
            // if the user is guess pass it to SamsungGalaxyS24FullDetails
            val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
            intent.putExtra("IS_GUEST", false) // Pass the guest flag
            startActivity(intent)
        }

        // Navigate from UserDashboardActivity to SamsungGalaxyS24UltraFullDetailsActivity
        val goToSamsungGalaxyS24UltraFullDetails =
            findViewById<TextView>(R.id.samsungGalaxyS24UltraSeeMoreButton)
        goToSamsungGalaxyS24UltraFullDetails.setOnClickListener {
            // if the user is guess pass it to SamsungGalaxyS24FullDetails
            val intent = Intent(this, SamsungGalaxyS24UltraFullDetails::class.java)
            intent.putExtra("IS_GUEST", false) // Pass the guest flag
            startActivity(intent)
        }

        // Navigate from UserDashboardActivity to Iphone16ProMaxFullDetailsActivity
        val goToIphone16ProMaxFullDetails = findViewById<TextView>(R.id.iphone16ProMaxSeeMoreButton)
        goToIphone16ProMaxFullDetails.setOnClickListener {
            // if the user is guess pass it to SamsungGalaxyS24FullDetails
            val intent = Intent(this, Iphone16ProMaxFullDetails::class.java)
            intent.putExtra("IS_GUEST", false) // Pass the guest flag
            startActivity(intent)
        }

        // Navigate from UserDashboardActivity to laptopAppleMacbookM3ProFullDetailsActivity
        val goTolaptopAppleMacbookM3ProFullDetails =
            findViewById<TextView>(R.id.laptopMacBookM3ProSeeMoreButton)
        goTolaptopAppleMacbookM3ProFullDetails.setOnClickListener {
            // if the user is guess pass it to SamsungGalaxyS24FullDetails
            val intent = Intent(this, laptopAppleMacbookM3ProFullDetails::class.java)
            intent.putExtra("IS_GUEST", false) // Pass the guest flag
            startActivity(intent)
        }

        // Navigate from UserDashboardActivity to SamsungGalaxyTabS10UltraFullDetails
        val goToSamsungGalaxyTabS10UltraFullDetails =
            findViewById<TextView>(R.id.UserDashboardSamsungGalaxyTabS10UltraSeeMoreButton)
        goToSamsungGalaxyTabS10UltraFullDetails.setOnClickListener {
            // if the user is guess pass it to SamsungGalaxyS24FullDetails
            val intent = Intent(this, SamsungGalaxyTabS10UltraFullDetails::class.java)
            intent.putExtra("IS_GUEST", false) // Pass the guest flag
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to SamsungGalaxyS24FullDetailsActivity
        val xiaomiNotebookProFullDetails =
            findViewById<TextView>(R.id.xiaomiNotebookProSeeMoreButton)
        xiaomiNotebookProFullDetails.setOnClickListener {
            val intent = Intent(this, laptopXiaomiNotebookPro120gFullDetails::class.java)
            startActivity(intent)
        }
        //
        val ipad13ProFullDetails = findViewById<TextView>(R.id.ipad13ProSeeMoreButton)
        ipad13ProFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to SamsungGalaxyS24FullDetailsActivity
        val oppoPad2FullDetails = findViewById<TextView>(R.id.oppoPad2SeeMoreButton)
        oppoPad2FullDetails.setOnClickListener {
            val intent = Intent(this, OppoPad2FullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to SamsungGalaxyS24FullDetailsActivity
        val galaxyBook4SeriesFullDetails = findViewById<TextView>(R.id.galaxyBook4SeeMoreButton)
        galaxyBook4SeriesFullDetails.setOnClickListener {
            val intent = Intent(this, laptopSamsungGalaxyBook4SeriesFullDetails::class.java)
            startActivity(intent)
        }
        val smartphoneOppoReno12ProFullDetails = findViewById<TextView>(R.id.smartphoneOppoReno12ProSeeMoreButton)
        smartphoneOppoReno12ProFullDetails.setOnClickListener {
            val intent = Intent(this, laptopSamsungGalaxyBook4SeriesFullDetails::class.java)
            startActivity(intent)
        }
        val tabletOppoPad2FullDetails = findViewById<TextView>(R.id.tabletOppoPad2SeeMoreButton)
        tabletOppoPad2FullDetails.setOnClickListener {
            val intent = Intent(this, OppoPad2FullDetails::class.java)
            startActivity(intent)
        }
        val smartphoneGooglePixel9ProFullDetails = findViewById<TextView>(R.id.smartphoneGooglePixel9ProSeeMoreButton)
        smartphoneGooglePixel9ProFullDetails.setOnClickListener {
            val intent = Intent(this, smartphoneGooglePixel9ProFullDetails::class.java)
            startActivity(intent)
        }
        val tabletXiaomiPad6ProFullDetails = findViewById<TextView>(R.id.tabletXiaomiPad6ProSeeMoreButton)
        tabletXiaomiPad6ProFullDetails.setOnClickListener {
            val intent = Intent(this, XiaomiPad6ProFullDetails::class.java)
            startActivity(intent)
        }
        val laptopXiaomiNotebookProFullDetails = findViewById<TextView>(R.id.laptopXiaomiNotebookProSeeMoreButton)
        laptopXiaomiNotebookProFullDetails.setOnClickListener {
            val intent = Intent(this, laptopXiaomiNotebookPro120gFullDetails::class.java)
            startActivity(intent)
        }
        val tabletRealmePad2FullDetails = findViewById<TextView>(R.id.tabletRealmePad2SeeMoreButton)
        tabletRealmePad2FullDetails.setOnClickListener {
            val intent = Intent(this, RealmePad2FullDetails::class.java)
            startActivity(intent)
        }










        // Navigate from UserDashboardActivity to SmartphoneActivity
        val goTopSmartphoneActivity = findViewById<ImageView>(R.id.smartphonBtn)
        goTopSmartphoneActivity.setOnClickListener {
            val intent = Intent(this, SmartphoneActivity::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to TabletActivity
        val goToLaptopActivity = findViewById<ImageView>(R.id.laptopBtn)
        goToLaptopActivity.setOnClickListener {
            val intent = Intent(this, LaptopActivity::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to LaptopActivity
        val goToTabletActivity = findViewById<ImageView>(R.id.tabletBtn)
        goToTabletActivity.setOnClickListener {
            val intent = Intent(this, TabletActivity::class.java)
            startActivity(intent)
        }
        // Navigate from UserDashboardActivity to ViewAllActivity
        val viewAllButton = findViewById<TextView>(R.id.viewAllButton)
        viewAllButton.setOnClickListener {
            val intent = Intent(this, ViewAllActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getFilteredSuggestions(query: String): List<String> {
        val results = suggestions.filter { it.contains(query, ignoreCase = true) }
        return results.ifEmpty { listOf("Smartphone not found") }
    }
    private fun navigateToDetailPage(selectedSuggestion: String) {
        when (selectedSuggestion) {
            "Samsung" -> {
                // Navigate to the new page that displays all Samsung smartphones
                val intent = Intent(this, SamsungSearchActivity::class.java)
                startActivity(intent)
            }
            "Samsung Galaxy S24" -> {
                // Existing navigation
                val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
                startActivity(intent)
            }
            "Samsung Galaxy Book3" -> {
                // Existing navigation
                val intent = Intent(this, laptopSamsungGalaxyBook3FullDetails::class.java)
                startActivity(intent)
            }
            "Samsung Galaxy Book4" -> {
                // Existing navigation
                val intent = Intent(this, laptopSamsungGalaxyBook4SeriesFullDetails::class.java)
                startActivity(intent)
            }
            "Samsung Galaxy S24 Ultra" -> {
                val intent = Intent(this, SamsungGalaxyS24UltraFullDetails::class.java)
                startActivity(intent)
            }
            "iPhone 16 Pro Max" -> {
                val intent = Intent(this, Iphone16ProMaxFullDetails::class.java)
                startActivity(intent)
            }
            "Apple Macbook M3 Pro " -> {
                val intent = Intent(this, laptopAppleMacbookM3ProFullDetails::class.java)
                startActivity(intent)
            }
            "Apple Macbook M2 Pro 14 " -> {
                val intent = Intent(this, laptopAppleM2MacBookPro14FullDetails::class.java)
                startActivity(intent)
            }
            "Xiaomi 14 Ultra" -> {
                val intent = Intent(this, Xiaomi14UltraFullDetails::class.java)
                startActivity(intent)
            }
            "Xiaomi Notebook Pro 120g " -> {
                val intent = Intent(this, laptopXiaomiNotebookPro120gFullDetails::class.java)
                startActivity(intent)
            }
            "Xiaomi Pad 6 Pro " -> {
                val intent = Intent(this, XiaomiPad6ProFullDetails::class.java)
                startActivity(intent)
            }
            "Oppo Reno 12 Pro " -> {
                val intent = Intent(this, smartphoneOppoReno12ProFullDetails::class.java)
                startActivity(intent)
            }
            "Oppo Pad 2 " -> {
            val intent = Intent(this, OppoPad2FullDetails::class.java)
            startActivity(intent)
            }
            "Realme 13 Pro Plus " -> {
                val intent = Intent(this, Realme13ProPlusFullDetails::class.java)
                startActivity(intent)
            }
            "Realme Pad 2 " -> {
            val intent = Intent(this, RealmePad2FullDetails::class.java)
            startActivity(intent)
            }
            "Vivo X100 Pro " -> {
                val intent = Intent(this, VivoX100ProFullDetails::class.java)
                startActivity(intent)
            }
            "Google Pixel 9 Pro " -> {
                val intent = Intent(this, smartphoneGooglePixel9ProFullDetails::class.java)
                startActivity(intent)
            }
            "Infinix Note 40 Pro+ " -> {
                val intent = Intent(this, smartphoneInfinixNote40PlusFullDetails::class.java)
                startActivity(intent)
            }
            "Huawei Matebook 15 " -> {
                val intent = Intent(this, laptopHuaweiMatebook15FullDetails::class.java)
                startActivity(intent)
            }
            "Huawei Matebook X pro " -> {
                val intent = Intent(this, laptopHuaweiMatebookXProFullDetails::class.java)
                startActivity(intent)
            }
            "Lenovo Legion 7i " -> {
            val intent = Intent(this, laptopLenovoLegion7iFullDetails::class.java)
            startActivity(intent)
            }
            "Lenovo Chromebook Duet 11 " -> {
                val intent = Intent(this, laptopLenovoThinkPadT14sFullDetails::class.java)
                startActivity(intent)
            }
        }
    }

    // Show logout confirmation dialog popup
    private fun showLogoutDialog() {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.logout_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView) // Set the custom layout as the dialog view

        // Find buttons in the custom layout
        val logoutYesBtn = dialogView.findViewById<Button>(R.id.LogoutYesBtn)
        val logoutNoBtn = dialogView.findViewById<Button>(R.id.LogoutNoBtn)
        val dialog = builder.create()

        // Handle 'Yes' button click - logs the user out
        logoutYesBtn.setOnClickListener {
            // Clear login information
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_LOGGED_IN", false) // Clear login state
            editor.putString("USER_NAME", null) // Clear username
            editor.apply() // Apply changes

            dialog.dismiss() // Dismiss the dialog

            // Redirect to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // close this activity
        }
        // Handle 'No' button click - closes the dialog
        logoutNoBtn.setOnClickListener {
            dialog.dismiss() // dismiss the dialog
        }
        dialog.show() // Show the dialog
    }
    override fun onBackPressed() {
        // Show confirmation dialog when back button is pressed
        showExitConfirmationDialog()
    }

    private fun fetchSamsungS24Ratings() {
        val samsungSmartphoneId = 1 // Set the specific ID for Samsung Galaxy S24
        RetrofitClient.apiService.getRatings(samsungSmartphoneId).enqueue(object :
            Callback<SmartphoneRatingsResponse> {
            override fun onResponse(
                call: Call<SmartphoneRatingsResponse>,
                response: Response<SmartphoneRatingsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val ratingsData = response.body()!!

                    val samsungPercentageOfRatings = ratingsData.percentage_of_ratings
                    binding.SamsungS24percentageOfRatings1.text = "$samsungPercentageOfRatings"
                }
            }

            override fun onFailure(call: Call<SmartphoneRatingsResponse>, t: Throwable) {
                Log.e("UserDashboardActivity", "Error fetching Samsung S24 ratings: ${t.message}")
            }
        })
    }

    // Function to fetch and display ratings for iPhone 16 Pro Max
    private fun fetchIphone16ProMaxRatings() {
        val iphoneSmartphoneId = 2 // Set the specific ID for iPhone 16 Pro Max
        RetrofitClient.apiService.getRatings(iphoneSmartphoneId).enqueue(object :
            Callback<SmartphoneRatingsResponse> {
            override fun onResponse(
                call: Call<SmartphoneRatingsResponse>,
                response: Response<SmartphoneRatingsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val ratingsData = response.body()!!

                    val iphonePercentageOfRatings = ratingsData.percentage_of_ratings
                    binding.Iphone16ProMaxPercentageOfRatings1.text = "$iphonePercentageOfRatings"
                }
            }

            override fun onFailure(call: Call<SmartphoneRatingsResponse>, t: Throwable) {
                Log.e("UserDashboardActivity", "Error fetching iPhone 16 Pro Max ratings: ${t.message}")
            }
        })
    }

    // Function to fetch and display ratings for iPhone 16 Pro Max
    private fun fetchAppleMacbookM3ProRatings() {
        val SmartphoneId = 3 // Set the specific ID for iPhone 16 Pro Max
        RetrofitClient.apiService.getRatings(SmartphoneId).enqueue(object :
            Callback<SmartphoneRatingsResponse> {
            override fun onResponse(
                call: Call<SmartphoneRatingsResponse>,
                response: Response<SmartphoneRatingsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val ratingsData = response.body()!!

                    val PercentageOfRatings = ratingsData.percentage_of_ratings
                    binding.AppleMacbookM3ProPercentageOfRatings1.text = "$PercentageOfRatings"
                }
            }

            override fun onFailure(call: Call<SmartphoneRatingsResponse>, t: Throwable) {
                Log.e("UserDashboardActivity", "Error fetching iPhone 16 Pro Max ratings: ${t.message}")
            }
        })
    }

    // Function to fetch and display ratings for Samsung Galaxy Tab S10 Ultra
    private fun fetchSamsungGalaxyTabS10UltraRatings() {
        val SmartphoneId = 4 // Set the specific ID for iPhone 16 Pro Max
        RetrofitClient.apiService.getRatings(SmartphoneId).enqueue(object :
            Callback<SmartphoneRatingsResponse> {
            override fun onResponse(
                call: Call<SmartphoneRatingsResponse>,
                response: Response<SmartphoneRatingsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val ratingsData = response.body()!!

                    val PercentageOfRatings = ratingsData.percentage_of_ratings
                    binding.SamsungGalaxyTabS10UltraPercentageOfRatings1.text = "$PercentageOfRatings"
                }
            }

            override fun onFailure(call: Call<SmartphoneRatingsResponse>, t: Throwable) {

            }
        })
    }


    private fun showExitConfirmationDialog() {
        // Inflate the custom layout for the dialog
        val dialogView = layoutInflater.inflate(R.layout.exit_dialog, null)

        // Create an AlertDialog and set the custom view
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)

        // Create the dialog
        val dialog = builder.create()

        // Get the buttons from the custom layout
        val exitYesButton = dialogView.findViewById<Button>(R.id.ExitYesBtn)
        val exitNoButton = dialogView.findViewById<Button>(R.id.ExitNoBtn)

        // Set click listener for the Yes button
        exitYesButton.setOnClickListener {
            finishAffinity()    // Exit the app
            dialog.dismiss()    // close the dialog
        }
        // Set click listener for the No button
        exitNoButton.setOnClickListener {
            dialog.dismiss() // close the dialog
        }
        // Show the dialog
        dialog.show()
    }

    // Method to clear user session data if the user logged-out
    private fun clearUserSession() {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()  // Clear all data
        editor.apply()  // apply the changes
    }
}