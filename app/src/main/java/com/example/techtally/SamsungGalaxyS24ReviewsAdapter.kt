package com.example.techtally

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Declaring the SamsungGalaxyS24ReviewsAdapter class which is a custom adapter for the RecyclerView.
// It takes a list of Review objects as input passed in as 'reviews'.
class SamsungGalaxyS24ReviewsAdapter(private var reviews: MutableList<SamsungGalaxyS24Review>) : RecyclerView.Adapter<SamsungGalaxyS24ReviewsAdapter.ReviewViewHolder>() {

    // Inner class 'ReviewViewHolder' that represents a single item view in the RecyclerView.
    // It holds references to the TextViews inside the layout for displaying review details.
    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Finding TextViews from the layout using their IDs.
        private val nameOfTheUser: TextView = itemView.findViewById(R.id.nameOfTheUser)
        private val rateOfTheUser: TextView = itemView.findViewById(R.id.rateOfTheUser)
        private val commentOfTheUser: TextView = itemView.findViewById(R.id.commentOfTheUser)

        // bind function sets the review data (username, rating, comment) to the corresponding TextViews.
        fun bind(review: SamsungGalaxyS24Review) {
            // Setting the username TextView with the 'username' property from the Review object.
            nameOfTheUser.text = review.username
            // Converting the rating (integer) to a string and setting it to the rating TextView.
            rateOfTheUser.text = review.rating.toString()
            // Setting the comment from the Review object to the comment TextView.
            commentOfTheUser.text = review.comment
        }
    }

    // method to called when the RecyclerView needs a new ViewHolder (for each item in the list).
    // It inflates the item layout XML (samsung_galaxy_s24_review_item_layout) and returns a ReviewViewHolder object.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        // Inflating the XML layout for a single review item in the RecyclerView.
        val view = LayoutInflater.from(parent.context).inflate(R.layout.samsung_galaxy_s24_review_item_layout, parent, false)
        return ReviewViewHolder(view)   // Returning a new instance of ReviewViewHolder, which contains the inflated view.
    }

    // method to called to bind data to the ViewHolder at the given position
    // It takes the review data at the specified position in the list and binds it to the ViewHolder.
    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        // Getting the review data at the current position and binding it to the ViewHolder.
        holder.bind(reviews[position])
    }
    // This method returns the total number of items in the 'reviews' list
    override fun getItemCount(): Int {
        // Returning the size of the reviews list, which is the number of items in the RecyclerView
        return reviews.size
    }

    // Method to add a new review to the list and notify the adapter
    fun addReview(review: SamsungGalaxyS24Review) {
        reviews.add(review) // Adding the new review to the list
        notifyItemInserted(reviews.size - 1) // Notifying the RecyclerView that a new item has been inserted at the last position in the list
    }
}