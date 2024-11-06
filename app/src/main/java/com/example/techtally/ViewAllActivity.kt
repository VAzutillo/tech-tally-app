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
import com.example.techtally.databinding.ActivityTabletBinding
import com.example.techtally.databinding.ActivityViewAllBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewAllActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewAllBinding

    private var percentage_of_ratings: Float = 0.0f
    private var smartphoneId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityViewAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 1)
        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 2)
        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 3)
        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 4)

        fetchSamsungS24Ratings()
        fetchIphone16ProMaxRatings()
        fetchAppleMacbookM3ProRatings()
        fetchSamsungGalaxyTabS10UltraRatings()

        // Navigate from ViewAllActivity to UserDashboardActivity
        val backToUserDashboardActivity = findViewById<ImageView>(R.id.viewAllBackButton)
        backToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }






        val ipad13ProFullDetails = findViewById<TextView>(R.id.tabletIpad13ProSeeMoreButton)
        ipad13ProFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val iphone16PromaxFullDetails = findViewById<TextView>(R.id.viewAllIphone16ProMaxSeeMoreButton)
        iphone16PromaxFullDetails.setOnClickListener {
            val intent = Intent(this, Iphone16ProViewAll::class.java)
            startActivity(intent)
        }
        val laptopAppleM2MacBookPro14FullDetails = findViewById<TextView>(R.id.laptopAppleM2MacBookPro14SeeMoreButton)
        laptopAppleM2MacBookPro14FullDetails.setOnClickListener {
            val intent = Intent(this, laptopAppleM2MacBookPro14ViewAll::class.java)
            startActivity(intent)
        }
        val laptopMacBookM3ProFullDetails = findViewById<TextView>(R.id.laptopMacBookM3ProSeeMoreButton)
        laptopMacBookM3ProFullDetails.setOnClickListener {
            val intent = Intent(this, laptopAppleMacbookM3ProFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val smartphoneGooglePixel9ProFullDetails = findViewById<TextView>(R.id.smartphoneGooglePixel9ProSeeMoreButton)
        smartphoneGooglePixel9ProFullDetails.setOnClickListener {
            val intent = Intent(this, smartphoneGooglePixel9ProFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val laptopHuaweiMatebook15FullDetails = findViewById<TextView>(R.id.laptopHuaweiMatebook15SeeMoreButton)
        laptopHuaweiMatebook15FullDetails.setOnClickListener {
            val intent = Intent(this, laptopHuaweiMatebook15FullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val laptopHuaweiMatebookXProFullDetails = findViewById<TextView>(R.id.laptopHuaweiMatebookXProSeeMoreButton)
        laptopHuaweiMatebookXProFullDetails.setOnClickListener {
            val intent = Intent(this, laptopHuaweiMatebookXProFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val smartphoneInfinixNote40ProPlusFullDetails = findViewById<TextView>(R.id.smartphoneInfinixNote40ProPlusSeeMoreButton)
        smartphoneInfinixNote40ProPlusFullDetails.setOnClickListener {
            val intent = Intent(this, smartphoneInfinixNote40PlusFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val tabletLenovoChromebookDuet11FullDetails = findViewById<TextView>(R.id.tabletLenovoChromebookDuet11SeeMoreButton)
        tabletLenovoChromebookDuet11FullDetails.setOnClickListener {
            val intent = Intent(this, LenovoChromebookDuet11ViewAll::class.java)
            startActivity(intent)
        }
        val laptopLenovoLegion7IPlusFullDetails = findViewById<TextView>(R.id.laptopLenovoLegion7ISeeMoreButton)
        laptopLenovoLegion7IPlusFullDetails.setOnClickListener {
            val intent = Intent(this, laptopLenovoLegion7iFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val laptopLenovoThinkPadT14sFullDetails = findViewById<TextView>(R.id.laptopLenovoThinkPadT14sSeeMoreButton)
        laptopLenovoThinkPadT14sFullDetails.setOnClickListener {
            val intent = Intent(this, laptopLenovoThinkPadT14sFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val tabletOppoPad2FullDetails = findViewById<TextView>(R.id.tabletOppoPad2SeeMoreButton)
        tabletOppoPad2FullDetails.setOnClickListener {
            val intent = Intent(this, OppoPad2FullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val smartphoneOppoReno12ProFullDetails = findViewById<TextView>(R.id.smartphoneOppoReno12ProSeeMoreButton)
        smartphoneOppoReno12ProFullDetails.setOnClickListener {
            val intent = Intent(this, smartphoneOppoReno12ProFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val smartphoneRealme13ProPlusFullDetails = findViewById<TextView>(R.id.smartphoneRealme13ProPlusSeeMoreButton)
        smartphoneRealme13ProPlusFullDetails.setOnClickListener {
            val intent = Intent(this, Realme13ProPlusViewAll::class.java)
            startActivity(intent)
        }
        val tabletRealmePad2FullDetails = findViewById<TextView>(R.id.tabletRealmePad2SeeMoreButton)
        tabletRealmePad2FullDetails.setOnClickListener {
            val intent = Intent(this, RealmePad2FullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val laptopSamsungGalaxyBook3FullDetails = findViewById<TextView>(R.id.laptopSamsungGalaxyBook3SeeMoreButton)
        laptopSamsungGalaxyBook3FullDetails.setOnClickListener {
            val intent = Intent(this, laptopSamsungGalaxyBook3FullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val laptopGalaxyBook4SeriesFullDetails = findViewById<TextView>(R.id.laptopGalaxyBook4SeriesSeeMoreButton)
        laptopGalaxyBook4SeriesFullDetails.setOnClickListener {
            val intent = Intent(this, laptopSamsungGalaxyBook4SeriesFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val smartphoneSamsungGalaxyS24FullDetails = findViewById<TextView>(R.id.smartphoneSamsungGalaxyS24SeeMoreButton)
        smartphoneSamsungGalaxyS24FullDetails.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24FullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val smartphoneSamsungGalaxyS24UltraFullDetails = findViewById<TextView>(R.id.smartphoneSamsungGalaxyS24UltraSeeMoreButton)
        smartphoneSamsungGalaxyS24UltraFullDetails.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24UltraFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val tabletGalaxyTabS10UltraFullDetails = findViewById<TextView>(R.id.tabletGalaxyTabS10UltraSeeMoreButton)
        tabletGalaxyTabS10UltraFullDetails.setOnClickListener {
            val intent = Intent(this, GalaxyTabS10UltraFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val smartphoneVivoX100ProFullDetails = findViewById<TextView>(R.id.smartphoneVivoX100ProSeeMoreButton)
        smartphoneVivoX100ProFullDetails.setOnClickListener {
            val intent = Intent(this, VivoX100ProFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val smartphoneXiaomi14UltraFullDetails = findViewById<TextView>(R.id.smartphoneXiaomi14UltraSeeMoreButton)
        smartphoneXiaomi14UltraFullDetails.setOnClickListener {
            val intent = Intent(this, Xiaomi14UltraFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val laptopXiaomiNotebookProFullDetails = findViewById<TextView>(R.id.laptopXiaomiNotebookProSeeMoreButton)
        laptopXiaomiNotebookProFullDetails.setOnClickListener {
            val intent = Intent(this, laptopXiaomiNotebookPro120gFullDetailsViewAll::class.java)
            startActivity(intent)
        }
        val tabletXiaomiPad6ProFullDetails = findViewById<TextView>(R.id.tabletXiaomiPad6ProSeeMoreButton)
        tabletXiaomiPad6ProFullDetails.setOnClickListener {
            val intent = Intent(this, XiaomiPad6ProFullDetailsViewAll::class.java)
            startActivity(intent)
        }
    }

    private fun fetchSamsungS24Ratings() {
        val SmartphoneId = 1 // Set the specific ID for Samsung Galaxy S24
        RetrofitClient.apiService.getRatings(SmartphoneId).enqueue(object :
            Callback<SmartphoneRatingsResponse> {
            override fun onResponse(
                call: Call<SmartphoneRatingsResponse>,
                response: Response<SmartphoneRatingsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val ratingsData = response.body()!!

                    val samsungPercentageOfRatings = ratingsData.percentage_of_ratings
                    binding.ViewAllSamsungS24percentageOfRatings1.text = "$samsungPercentageOfRatings"
                }
            }

            override fun onFailure(call: Call<SmartphoneRatingsResponse>, t: Throwable) {
                Log.e("UserDashboardActivity", "Error fetching Samsung S24 ratings: ${t.message}")
            }
        })
    }

    // Function to fetch and display ratings for iPhone 16 Pro Max
    private fun fetchIphone16ProMaxRatings() {
        val SmartphoneId = 2 // Set the specific ID for iPhone 16 Pro Max
        RetrofitClient.apiService.getRatings(SmartphoneId).enqueue(object :
            Callback<SmartphoneRatingsResponse> {
            override fun onResponse(
                call: Call<SmartphoneRatingsResponse>,
                response: Response<SmartphoneRatingsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val ratingsData = response.body()!!

                    val iphonePercentageOfRatings = ratingsData.percentage_of_ratings
                    binding.ViewAllIphone16ProMaxPercentageOfRatings1.text = "$iphonePercentageOfRatings"
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
                    binding.ViewAllAppleMacbookM3ProPercentageOfRatings1.text = "$PercentageOfRatings"
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
                    binding.ViewAllSamsungGalaxyTabS10UltraPercentageOfRatings1.text = "$PercentageOfRatings"
                }
            }

            override fun onFailure(call: Call<SmartphoneRatingsResponse>, t: Throwable) {

            }
        })
    }
}