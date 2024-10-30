package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.techtally.databinding.ActivitySmartphoneBinding
import com.example.techtally.databinding.ActivityUserDashboardBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SmartphoneActivity : AppCompatActivity() {
    // View Binding for the layout
    private lateinit var binding: ActivitySmartphoneBinding

    private var percentage_of_ratings: Float = 0.0f
    private var smartphoneId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Inflate the layout using view binding
        binding = ActivitySmartphoneBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 1)
        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 2)
        fetchIphone16ProMaxRatings()
        fetchSamsungS24Ratings()

        // Navigate from SmartphoneActivity to UserDashboardActivity
        val backToUserDashboardActivity = findViewById<ImageView>(R.id.smartphoneBackButton)
        backToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to UserDashboardActivity
        val SamsungGalaxyS24UltraSeeMoreBTN =
            findViewById<TextView>(R.id.smartphoneSamsungGalaxyS24UltraSeeMoreButton)
        SamsungGalaxyS24UltraSeeMoreBTN.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24UltraSeeMoreBTN::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to SamsungGalaxyS24FullDetails
        val goToSamsungGalaxyS24FullDetails =
            findViewById<TextView>(R.id.smartphoneSamsungGalaxyS24SeeMoreButton)
        goToSamsungGalaxyS24FullDetails.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24FullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to Iphone16ProMaxFullDetails
        val goToSamsungIphone16ProMaxFullDetails =
            findViewById<TextView>(R.id.smartphoneIphone16ProMaxSeeMoreButton)
        goToSamsungIphone16ProMaxFullDetails.setOnClickListener {
            val intent = Intent(this, Iphone16ProMaxFullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to SamsungGalaxyS24UltraFullDetails
        val goToSamsungSamsungGalaxyS24UltraFullDetails =
            findViewById<TextView>(R.id.smartphoneSamsungGalaxyS24UltraSeeMoreButton)
        goToSamsungSamsungGalaxyS24UltraFullDetails.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24UltraFullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to VivoX100ProFullDetails
        val goToVivoX100ProFullDetails =
            findViewById<TextView>(R.id.smartphoneVivoX100ProSeeMoreButton)
        goToVivoX100ProFullDetails.setOnClickListener {
            val intent = Intent(this, VivoX100ProFullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from SmartphoneActivity to Realme13ProPlusFullDetails
        val goToRealme13ProPlusFullDetails =
            findViewById<TextView>(R.id.smartphoneRealme13ProPlusSeeMoreButton)
        goToRealme13ProPlusFullDetails.setOnClickListener {
            val intent = Intent(this, Realme13ProPlusFullDetails::class.java)
            startActivity(intent)
        }
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
                Log.e(
                    "UserDashboardActivity",
                    "Error fetching iPhone 16 Pro Max ratings: ${t.message}"
                )
            }
        })
    }
}