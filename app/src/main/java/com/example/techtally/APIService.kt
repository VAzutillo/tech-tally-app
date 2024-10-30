package com.example.techtally

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

// Data class representing the user signup request payload for the API.
// It includes the necessary fields for user registration: username, email, password, and password confirmation.
data class UserSignupRequest(
    val username: String,                 // Username field for registration
    val email: String,                    // Email field for registration
    val password: String,                 // Password field for registration
    val password_confirmation: String     // Password confirmation to ensure the user entered the same password twice
)

// Data class representing the response structure from the signup API.
// It includes a message and a boolean indicating whether to navigate to another activity.
data class SignupResponse(
    val message: String,                  // Message returned by the API, "Signup successful"
    val navigate: Boolean                 // Boolean indicating if the app should navigate to another activity after signup
)

// Data class representing the login request payload for the API.
// It includes the fields necessary for logging in: username (or email) and password.
data class LoginRequest(
    val username: String,                 // Username or email for login
    val password: String                  // Password for login
)

// Data class representing the response structure from the login API.
// It includes a boolean indicating if the login was successful, a message, and a role to differentiate between user and admin.
data class LoginResponse(
    val success: Boolean,                 // Boolean indicating if the login was successful
    val message: String,                  // Message returned by the API, "Login successful" or "Invalid credentials"
    val role: String                      // Role returned by the API, either "admin" or "user"
)

data class UsernameCheckResponse(
    val exists: Boolean
)
data class EmailCheckResponse(
    val exists: Boolean
)

data class ReviewRequest(
    val username: String,
    val rating: Int,
    val comment: String?,
    val smartphone_id: Int
)

data class ReviewResponse(
    val success: Boolean,
    val message: String,
    val data: ReviewData?
)
data class ReviewData(
    val id: Int,
    val username: String,
    val rating: Int,
    val comment: String?
)

data class SmartphoneRatingsResponse(
    val number_of_reviews: Int,
    val percentage_of_ratings: Float
)
data class Smartphone(
    val id: Int,
    val name: String,
    val brand: String,
    val model: String,
    // Add other fields as needed
)

// Retrofit interface defining the API endpoints for the app.
interface ApiService {
    // Endpoint for user registration.
    // Sends a POST request to the "client" endpoint with a UserSignupRequest body and expects a SignupResponse.
    @POST("client")  // The API endpoint for registering a new user
    fun registerUser(@Body request: UserSignupRequest): Call<SignupResponse>

    // Endpoint for user login.
    // Sends a POST request to the "login" endpoint with a LoginRequest body and expects a LoginResponse.
    @POST("login")  // The API endpoint for logging in a user
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @GET("client/username/{username}")
    fun checkUsername(@Path("username") username: String): Call<UsernameCheckResponse>

    @GET("client/email/{email}")
    fun checkEmail(@Path("email") email: String): Call<EmailCheckResponse>

    @POST("review")
    fun submitReview(@Body reviewRequest: ReviewRequest): Call<ReviewResponse>

    //@GET("review")
    //fun getReviews(): Call<List<Review>>

    @POST("review")
    fun submitIphone16ProMaxReview(@Body reviewRequest: ReviewRequest): Call<ReviewResponse>

    @POST("review")
    fun submitAppleMacbookM3ProReview(@Body reviewRequest: ReviewRequest): Call<ReviewResponse>

    //@GET("review")
    //fun getIphone16ProMaxReviews(): Call<List<Iphone16ProMaxReview>>

    @GET("reviews")
    fun getIphone16ProMaxBySmartphoneId(@Query("smartphone_id") smartphoneId: Int): Call<List<Iphone16ProMaxReview>>

    @GET("reviews")
    fun getSamsungGalaxyS24BySmartphoneId(@Query("smartphone_id") smartphoneId: Int): Call<List<SamsungGalaxyS24Review>>

    @GET("reviews")
    fun getAppleMacbookM3ProBySmartphoneId(@Query("smartphone_id") smartphoneId: Int): Call<List<AppleMacbookM3ProReview>>


    @GET("smartphone/{id}/ratings")
    fun getRatings(@Path("id") smartphoneId: Int): Call<SmartphoneRatingsResponse>

}
