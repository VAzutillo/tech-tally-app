package com.example.techtally

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// RetrofitClient is an object that provides a singleton instance of the Retrofit service
// It sets up Retrofit to communicate with the API backend and handles JSON parsing.
object RetrofitClient {
    // The base URL for API requests.
    // "10.0.2.2" is used to access the host machine from the Android emulator.
    private const val BASE_URL = "http://10.0.2.2:8000/api/"

    // Create a Gson instance with lenient parsing settings.
    // Lenient mode allows parsing malformed or unexpected JSON.
    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    // A lazy-initialized instance of the ApiService.
    // This ensures that Retrofit and the ApiService are created only when needed, improving performance.
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)  // Sets the base URL for the API endpoints.
            .addConverterFactory(GsonConverterFactory.create(gson))  // Adds a Gson converter for parsing JSON responses.
            .build()  // Builds the Retrofit instance.
            .create(ApiService::class.java)  // Creates the ApiService implementation for making API calls.
    }
    // A secondary property to expose the ApiService.
    // This is just an alias for 'instance', providing an alternative way to access the API service.
    val apiService: ApiService by lazy {
        instance  // Refers to the same ApiService instance.
    }
}