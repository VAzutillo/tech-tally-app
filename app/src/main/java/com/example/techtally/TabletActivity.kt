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
import com.example.techtally.databinding.ActivityTabletBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TabletActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTabletBinding

    private var percentage_of_ratings: Float = 0.0f
    private var smartphoneId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTabletBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        smartphoneId = intent.getIntExtra("SMARTPHONE_ID", 4)

        fetchSamsungGalaxyTabS10UltraRatings()

        // Navigate from TabletActivity to UserDashboardActivity
        val backToUserDashboardActivity = findViewById<ImageView>(R.id.tabletBackButton)
        backToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to GalaxyTabS10UltraFullDetails
        val galaxyTabS10UltraSeeMoreBTN = findViewById<TextView>(R.id.tabletGalaxyTabS10UltraSeeMoreButton)
        galaxyTabS10UltraSeeMoreBTN.setOnClickListener {
            val intent = Intent(this, GalaxyTabS10UltraFullDetailsUserDashboard::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to Ipad13ProFullDetails
        val ipad13ProSeeMoreBTN = findViewById<TextView>(R.id.tabletIpad13ProSeeMoreButton)
        ipad13ProSeeMoreBTN.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetailsTabletCategory::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to OppoPad2FullDetails
        val oppoPad2SeeMoreBTN = findViewById<TextView>(R.id.tabletOppoPad2SeeMoreButton)
        oppoPad2SeeMoreBTN.setOnClickListener {
            val intent = Intent(this, OppoPad2FullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to RealmePad2FullDetails
        val realmePad2SeeMoreBTN = findViewById<TextView>(R.id.tabletRealmePad2SeeMoreButton)
        realmePad2SeeMoreBTN.setOnClickListener {
            val intent = Intent(this, RealmePad2FullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to XiaomiPad6ProFullDetails
        val xiaomiPad6ProSeeMoreBTN = findViewById<TextView>(R.id.tabletXiaomiPad6ProSeeMoreButton)
        xiaomiPad6ProSeeMoreBTN.setOnClickListener {
            val intent = Intent(this, XiaomiPad6ProFullDetails::class.java)
            startActivity(intent)
        }
        // Navigate from TabletActivity to LenovoChromebookDuet11FullDetails
        val tabletLenovoChromebookDuet11SeeMoreBTN = findViewById<TextView>(R.id.tabletLenovoChromebookDuet11SeeMoreButton)
        tabletLenovoChromebookDuet11SeeMoreBTN.setOnClickListener {
            val intent = Intent(this, LenovoChromebookDuet11::class.java)
            startActivity(intent)
        }
    }
    private fun fetchSamsungGalaxyTabS10UltraRatings() {
        val SmartphoneId = 4
        RetrofitClient.apiService.getRatings(SmartphoneId).enqueue(object :
            Callback<SmartphoneRatingsResponse> {
            override fun onResponse(
                call: Call<SmartphoneRatingsResponse>,
                response: Response<SmartphoneRatingsResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val ratingsData = response.body()!!

                    val samsungPercentageOfRatings = ratingsData.percentage_of_ratings
                    binding.SamsungGalaxyTabS10UltraPercentageOfRatings1.text = "$samsungPercentageOfRatings"
                }
            }

            override fun onFailure(call: Call<SmartphoneRatingsResponse>, t: Throwable) {
                Log.e("UserDashboardActivity", "Error fetching Samsung S24 ratings: ${t.message}")
            }
        })
    }
}