package com.example.techtally

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class laptopLenovoLegion7iFullDetailsViewAll : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_laptop_lenovo_legion7i_full_details_view_all)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val goToUserDashboardActivity = findViewById<ImageView>(R.id.laptopLenovoLegion7ISeeMoreButton)
        goToUserDashboardActivity.setOnClickListener {
            val intent = Intent(this, ViewAllActivity::class.java)
            startActivity(intent)
        }
        // Scroll to top functionality for ScrollView
        val scrollView: ScrollView = findViewById(R.id.SamsungGalaxyS24FullDetailsScrollView)
        val backToTopTextView: TextView = findViewById(R.id.SamsungS24BackToTop)

        // Scroll to the top of the ScrollView
        backToTopTextView.setOnClickListener {
            scrollView.scrollTo(0, 0)
        }
        // see all reviews 1
        // Navigate from SamsungGalaxyS24FullDetails to SamsungGalaxyS24ReviewsPage
        val goToSamsungGalaxyS24SeeAllRatingsAndReviews1 = findViewById<ImageView>(R.id.SamsungS24SeeAllReviews1)
        goToSamsungGalaxyS24SeeAllRatingsAndReviews1.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24ReviewsPage::class.java)
            startActivity(intent)
        }
        // see all reviews 2
        // Navigate from SamsungGalaxyS24FullDetails to SamsungGalaxyS24ReviewsPage
        val goToSamsungGalaxyS24SeeAllRatingsAndReviews2 = findViewById<TextView>(R.id.SamsungS24SeeAllReviews2)
        goToSamsungGalaxyS24SeeAllRatingsAndReviews2.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24ReviewsPage::class.java)
            startActivity(intent)
        }
        // see all reviews 3
        // Navigate from SamsungGalaxyS24FullDetails to SamsungGalaxyS24ReviewsPage
        val goToSamsungGalaxyS24ReviewsPage = findViewById<TextView>(R.id.numberOfReviews)
        goToSamsungGalaxyS24ReviewsPage.setOnClickListener {
            val intent = Intent(this, SamsungGalaxyS24ReviewsPage::class.java)
            startActivity(intent)
        }
    }
}