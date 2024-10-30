package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ViewAllActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_view_all)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Navigate from ViewAllActivity to UserDashboardActivity
        val backToUserDashboardActivity = findViewById<ImageView>(R.id.viewAllBackButton)
        backToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, UserDashboardActivity::class.java)
            startActivity(intent)
        }
        val ipad13ProFullDetails = findViewById<TextView>(R.id.tabletIpad13ProSeeMoreButton)
        ipad13ProFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val iphone16PromaxFullDetails = findViewById<TextView>(R.id.smartphoneIphone16ProMaxSeeMoreButton)
        iphone16PromaxFullDetails.setOnClickListener {
            val intent = Intent(this, iphone16PromaxFullDetails::class.java)
            startActivity(intent)
        }
        val laptopAppleM2MacBookPro14FullDetails = findViewById<TextView>(R.id.laptopAppleM2MacBookPro14SeeMoreButton)
        laptopAppleM2MacBookPro14FullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val laptopMacBookM3ProFullDetails = findViewById<TextView>(R.id.laptopMacBookM3ProSeeMoreButton)
        laptopMacBookM3ProFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val smartphoneGooglePixel9ProFullDetails = findViewById<TextView>(R.id.smartphoneGooglePixel9ProSeeMoreButton)
        smartphoneGooglePixel9ProFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val laptopHuaweiMatebook15FullDetails = findViewById<TextView>(R.id.laptopHuaweiMatebook15SeeMoreButton)
        laptopHuaweiMatebook15FullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val laptopHuaweiMatebookXProFullDetails = findViewById<TextView>(R.id.laptopHuaweiMatebookXProSeeMoreButton)
        laptopHuaweiMatebookXProFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val smartphoneInfinixNote40ProPlusFullDetails = findViewById<TextView>(R.id.smartphoneInfinixNote40ProPlusSeeMoreButton)
        smartphoneInfinixNote40ProPlusFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val tabletLenovoChromebookDuet11FullDetails = findViewById<TextView>(R.id.tabletLenovoChromebookDuet11SeeMoreButton)
        tabletLenovoChromebookDuet11FullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val laptopLenovoLegion7IPlusFullDetails = findViewById<TextView>(R.id.laptopLenovoLegion7ISeeMoreButton)
        laptopLenovoLegion7IPlusFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val laptopLenovoThinkPadT14sFullDetails = findViewById<TextView>(R.id.laptopLenovoThinkPadT14sSeeMoreButton)
        laptopLenovoThinkPadT14sFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val tabletOppoPad2FullDetails = findViewById<TextView>(R.id.tabletOppoPad2SeeMoreButton)
        tabletOppoPad2FullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val smartphoneOppoReno12ProFullDetails = findViewById<TextView>(R.id.smartphoneOppoReno12ProSeeMoreButton)
        smartphoneOppoReno12ProFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val smartphoneRealme13ProPlusFullDetails = findViewById<TextView>(R.id.smartphoneRealme13ProPlusSeeMoreButton)
        smartphoneRealme13ProPlusFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val tabletRealmePad2FullDetails = findViewById<TextView>(R.id.tabletRealmePad2SeeMoreButton)
        tabletRealmePad2FullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val laptopSamsungGalaxyBook3FullDetails = findViewById<TextView>(R.id.laptopSamsungGalaxyBook3SeeMoreButton)
        laptopSamsungGalaxyBook3FullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val laptopGalaxyBook4SeriesFullDetails = findViewById<TextView>(R.id.laptopGalaxyBook4SeriesSeeMoreButton)
        laptopGalaxyBook4SeriesFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val smartphoneSamsungGalaxyS24FullDetails = findViewById<TextView>(R.id.smartphoneSamsungGalaxyS24SeeMoreButton)
        smartphoneSamsungGalaxyS24FullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val smartphoneSamsungGalaxyS24UltraFullDetails = findViewById<TextView>(R.id.smartphoneSamsungGalaxyS24UltraSeeMoreButton)
        smartphoneSamsungGalaxyS24UltraFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val tabletGalaxyTabS10UltraFullDetails = findViewById<TextView>(R.id.tabletGalaxyTabS10UltraSeeMoreButton)
        tabletGalaxyTabS10UltraFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val smartphoneVivoX100ProFullDetails = findViewById<TextView>(R.id.smartphoneVivoX100ProSeeMoreButton)
        smartphoneVivoX100ProFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val smartphoneXiaomi14UltraFullDetails = findViewById<TextView>(R.id.smartphoneXiaomi14UltraSeeMoreButton)
        smartphoneXiaomi14UltraFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val laptopXiaomiNotebookProFullDetails = findViewById<TextView>(R.id.laptopXiaomiNotebookProSeeMoreButton)
        laptopXiaomiNotebookProFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
        val tabletXiaomiPad6ProFullDetails = findViewById<TextView>(R.id.tabletXiaomiPad6ProSeeMoreButton)
        tabletXiaomiPad6ProFullDetails.setOnClickListener {
            val intent = Intent(this, Ipad13ProFullDetails::class.java)
            startActivity(intent)
        }
    }
}