package com.example.techtally

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.techtally.databinding.ActivityAddNewDeviceBinding

class AddNewDeviceActivity : AppCompatActivity() {

    // View binding for the layout
    private lateinit var binding: ActivityAddNewDeviceBinding

    // Declaring UI components for device type selection, brand selection, and pop-ups
    // For type of device
    private lateinit var smartphoneButton: Button           // For device type Smartphone button
    private lateinit var tabletButton: Button               // For device type Tablet button
    // For Brands
    private lateinit var brandsButtonText: Button           // For Brands button
    private lateinit var brandPopup: FrameLayout            // Layout for Brands button pop-up
    // For Uploading Image
    private lateinit var uploadImageButton: Button          // For upload image button
    private lateinit var imageUploadPopup: FrameLayout      // Layout for Image upload pop-up
    private lateinit var btnUploadFromDevice: Button        // Upload image from device button inside the pop-up
    // For Overview
    private lateinit var overviewPopup: FrameLayout         // Layout for Overview pop-up
    private lateinit var btnContinue: Button                // Continue button inside the pop-up
    // For Uploading Device
    private lateinit var uploadDevicePopup: FrameLayout     // Layout for Upload Device pop-up
    private lateinit var uploadBtn: Button                  // upload button
    private lateinit var YesBtn: Button                     // Yes button inside the pop-up
    private lateinit var NoBtn: Button                      // No button inside the pop-up
    // For Specifications
    private lateinit var specificationsPopup: FrameLayout   // Layout for Specifications pop-up
    private lateinit var specificationsBtn: Button          // Specifications button
    private lateinit var backButton: TextView               // Back button(textView) inside the pop-up
    private lateinit var continueButton: Button             // Continue button inside the pop-up

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivityAddNewDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize UI components
        // For device type
        smartphoneButton = binding.AddSmarphoneButton   // Initialize smartphone button
        tabletButton = binding.addTabletButton          // Initialize tablet button
        // For brands
        brandsButtonText = binding.brandsButton         // Initialize brands button
        brandPopup = binding.brandPopup                 // Initialize Layout brandPop-up
        // For uploading image
        uploadImageButton = binding.uploadImageBtn          // Initialize uploadImage button
        imageUploadPopup = binding.imageUploadPopup         // Initialize Layout imageUploadPop-up
        btnUploadFromDevice = binding.btnUploadFromDevice   // Initialize btnUploadFromDevice inside the pop-up
        // For overview
        overviewPopup = binding.popupLayout             // Initialize Layout overviewPop-up
        btnContinue = binding.btnContinue               // Initialize btnContinue inside the pop-up
        // For uploadDevice
        uploadDevicePopup = binding.uploadDevicePopup   // Initialize Layout uploadDevicePop-up
        YesBtn = binding.YesBtn                         // Initialize Yes button inside the pop-up
        NoBtn = binding.NoBtn                           // Initialize No button inside the pop-up
        uploadBtn = binding.uploadBtn                   // Initialize upload button
        // For specifications
        specificationsPopup = binding.specificationsPopup   // Initialize Layout specificationsPop-up
        specificationsBtn = binding.specificationsBtn       // Initialize specifications button
        backButton = binding.backButton                     // Make sure to define this in your XML
        continueButton = binding.continueButton             // Make sure to define this in your XML

        // Show popup when Upload button is clicked
        uploadBtn.setOnClickListener {
            uploadDevicePopup.visibility = View.VISIBLE
        }

        // Hide popup when Yes button are clicked
        YesBtn.setOnClickListener {
            uploadDevicePopup.visibility = View.GONE
        }
        // Hide popup when No button are clicked
        NoBtn.setOnClickListener {
            uploadDevicePopup.visibility = View.GONE
        }

        // Show popup when Specifications button is clicked
        specificationsBtn.setOnClickListener {
            specificationsPopup.visibility = View.VISIBLE
        }
        // Hide popup when Back button are clicked
        backButton.setOnClickListener {
            specificationsPopup.visibility = View.GONE
        }
        // Hide popup when Continue button are clicked
        continueButton.setOnClickListener {
            specificationsPopup.visibility = View.GONE
        }

        // Show the overview pop-up when the Overview EditText is clicked
        binding.etOverview.setOnClickListener {
            showPopup()
        }
        // get the text from the pop-up into the overview EditText when Continue is clicked
        binding.btnContinue.setOnClickListener {
            val inputText = binding.etDeviceOverview.text.toString()
            if (inputText.isNotEmpty()) {
                // Set the input text to etOverview (convert it to String)
                binding.etOverview.setText(inputText)
            }
            // Hide the popup after setting the text
            hidePopup()
        }

        // Show the image upload pop-up when the Upload Image button is clicked
        uploadImageButton.setOnClickListener {
            imageUploadPopup.visibility = View.VISIBLE
        }
        // Hide the image upload pop-up when Upload from Device is clicked
        btnUploadFromDevice.setOnClickListener {
            imageUploadPopup.visibility = View.GONE
        }

        // Change button styles when either the smartphone or tablet button is clicked
        smartphoneButton.setOnClickListener {
            changeButtonStyle(smartphoneButton, "#FFFFFF", "#2F2F2F")   // Selected style
            changeButtonStyle(tabletButton, "#2F2F2F", "#FFFFFF")       // Unselected style
        }
        // Change button styles when either the smartphone or tablet button is clicked
        tabletButton.setOnClickListener {
            changeButtonStyle(tabletButton, "#FFFFFF", "#2F2F2F")       // Selected style
            changeButtonStyle(smartphoneButton, "#2F2F2F", "#FFFFFF")   // Unselected style
        }

        // Show the brand pop-up when the brand button is clicked
        brandsButtonText.setOnClickListener {
            blurBackground()
            brandPopup.visibility = View.VISIBLE
        }
        // Brand selection buttons logic
        setupBrandSelection()
    }
    // Function to set up brand selection buttons (, Apple, Samsung, Xiaomi, Realme, Oppo)
    private fun setupBrandSelection() {
        // Find each brand button by its ID
        val btnApple: Button = findViewById(R.id.btnApple)
        val btnSamsung: Button = findViewById(R.id.btnSamsung)
        val btnXiaomi: Button = findViewById(R.id.btnXiaomi)
        val btnRealme: Button = findViewById(R.id.btnRealme)
        val btnOppo: Button = findViewById(R.id.btnOppo)

        // Group all brand buttons in a list
        val brandButtons = listOf(btnApple, btnSamsung, btnXiaomi, btnRealme, btnOppo)
        // Set a click listener for each button to select a brand
        for (button in brandButtons) {
            button.setOnClickListener {
                selectBrand(button.text.toString()) // Call selectBrand() with the button's text (brand name)
            }
        }
    }
    // Function to select a brand and set it on the brand button text, then hide the brand pop-up
    private fun selectBrand(brand: String) {
        brandsButtonText.text = brand       // Set the selected brand as the button's text
        brandPopup.visibility = View.GONE   // Hide the brand pop-up
    }

    // Function to show the overview pop-up
    private fun showPopup() {
        overviewPopup.visibility = View.VISIBLE
    }
    // Function to hide the overview pop-up
    private fun hidePopup() {
        overviewPopup.visibility = View.GONE
    }

    // Function to apply background blur
    private fun blurBackground() {
        // not set yet
    }

    // Helper function to change the background color and text color of a button
    private fun changeButtonStyle(button: Button, backgroundColor: String, textColor: String) {
        button.setBackgroundColor(Color.parseColor(backgroundColor))    // Set the background color
        button.setTextColor(Color.parseColor(textColor))
    }
}