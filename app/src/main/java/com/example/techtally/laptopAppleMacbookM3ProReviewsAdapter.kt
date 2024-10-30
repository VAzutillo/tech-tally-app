package com.example.techtally

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class laptopAppleMacbookM3ProReviewsAdapter(private var applemacbookm3proReviews: MutableList<AppleMacbookM3ProReview>) : RecyclerView.Adapter<laptopAppleMacbookM3ProReviewsAdapter.AppleMacbookM3ProViewHolder>() {
    // Inner class 'ReviewViewHolder' that represents a single item view in the RecyclerView.
    // It holds references to the TextViews inside the layout for displaying review details.
    inner class AppleMacbookM3ProViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Finding TextViews from the layout using their IDs.
        private val AppleMacbookM3ProNameOfTheUser: TextView =
            itemView.findViewById(R.id.AppleMacbookM3ProNameOfTheUser)
        private val AppleMacbookM3ProRateOfTheUser: TextView =
            itemView.findViewById(R.id.AppleMacbookM3ProRateOfTheUser)
        private val AppleMacbookM3ProCommentOfTheUser: TextView =
            itemView.findViewById(R.id.AppleMacbookM3ProCommentOfTheUser)

        // bind function sets the review data (username, rating, comment) to the corresponding TextViews.
        fun bind(applemacbookm3proreview: AppleMacbookM3ProReview) {
            // Setting the username TextView with the 'username' property from the Review object.
            AppleMacbookM3ProNameOfTheUser.text = applemacbookm3proreview.username
            // Converting the rating (integer) to a string and setting it to the rating TextView.
            AppleMacbookM3ProRateOfTheUser.text = applemacbookm3proreview.rating.toString()
            // Setting the comment from the Review object to the comment TextView.
            AppleMacbookM3ProCommentOfTheUser.text = applemacbookm3proreview.comment
        }
    }

    // method to called when the RecyclerView needs a new ViewHolder (for each item in the list).
    // It inflates the item layout XML (samsung_galaxy_s24_review_item_layout) and returns a ReviewViewHolder object.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppleMacbookM3ProViewHolder {
        // Inflating the XML layout for a single review item in the RecyclerView.
        val AppleMacbookM3Proview = LayoutInflater.from(parent.context)
            .inflate(R.layout.apple_macbook_m3_pro_review_item_layout, parent, false)
        return AppleMacbookM3ProViewHolder(AppleMacbookM3Proview)   // Returning a new instance of ReviewViewHolder, which contains the inflated view.
    }

    // method to called to bind data to the ViewHolder at the given position
    // It takes the review data at the specified position in the list and binds it to the ViewHolder.
    override fun onBindViewHolder(holder: AppleMacbookM3ProViewHolder, position: Int) {
        // Getting the review data at the current position and binding it to the ViewHolder.
        holder.bind(applemacbookm3proReviews[position])
    }

    // This method returns the total number of items in the 'reviews' list
    override fun getItemCount(): Int {
        // Returning the size of the reviews list, which is the number of items in the RecyclerView
        return applemacbookm3proReviews.size
    }

    // Method to add a new review to the list and notify the adapter
    fun addReview(applemacbookm3proreview: AppleMacbookM3ProReview) {
        applemacbookm3proReviews.add(applemacbookm3proreview) // Adding the new review to the list
        notifyItemInserted(applemacbookm3proReviews.size - 1) // Notifying the RecyclerView that a new item has been inserted at the last position in the list
    }
}