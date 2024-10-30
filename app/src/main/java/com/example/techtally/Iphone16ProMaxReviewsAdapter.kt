package com.example.techtally

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Iphone16ProMaxReviewsAdapter(private var iPhoneReviews: MutableList<Iphone16ProMaxReview>) : RecyclerView.Adapter<Iphone16ProMaxReviewsAdapter.IphoneReviewViewHolder>() {
    // Inner class 'ReviewViewHolder' that represents a single item view in the RecyclerView.
    // It holds references to the TextViews inside the layout for displaying review details.
    inner class IphoneReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Finding TextViews from the layout using their IDs.
        private val Iphone16ProMaxNameOfTheUser: TextView = itemView.findViewById(R.id.Iphone16ProMaxNameOfTheUser)
        private val Iphone16ProMaxRateOfTheUser: TextView = itemView.findViewById(R.id.Iphone16ProMaxRateOfTheUser)
        private val Iphone16ProMaxCommentOfTheUser: TextView = itemView.findViewById(R.id.Iphone16ProMaxCommentOfTheUser)

        // bind function sets the review data (username, rating, comment) to the corresponding TextViews.
        fun bind(iphonereview: Iphone16ProMaxReview) {
            // Setting the username TextView with the 'username' property from the Review object.
            Iphone16ProMaxNameOfTheUser.text = iphonereview.username
            // Converting the rating (integer) to a string and setting it to the rating TextView.
            Iphone16ProMaxRateOfTheUser.text = iphonereview.rating.toString()
            // Setting the comment from the Review object to the comment TextView.
            Iphone16ProMaxCommentOfTheUser.text = iphonereview.comment
        }
}

    // method to called when the RecyclerView needs a new ViewHolder (for each item in the list).
    // It inflates the item layout XML (samsung_galaxy_s24_review_item_layout) and returns a ReviewViewHolder object.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IphoneReviewViewHolder {
        // Inflating the XML layout for a single review item in the RecyclerView.
        val Iphoneview = LayoutInflater.from(parent.context).inflate(R.layout.iphone_16_pro_max_review_item_layout, parent, false)
        return IphoneReviewViewHolder(Iphoneview)   // Returning a new instance of ReviewViewHolder, which contains the inflated view.
    }

    // method to called to bind data to the ViewHolder at the given position
    // It takes the review data at the specified position in the list and binds it to the ViewHolder.
    override fun onBindViewHolder(holder: IphoneReviewViewHolder, position: Int) {
        // Getting the review data at the current position and binding it to the ViewHolder.
        holder.bind(iPhoneReviews[position])
    }

    // This method returns the total number of items in the 'reviews' list
    override fun getItemCount(): Int {
        // Returning the size of the reviews list, which is the number of items in the RecyclerView
        return iPhoneReviews.size
    }

    // Method to add a new review to the list and notify the adapter
    fun addReview(iphonereview: Iphone16ProMaxReview) {
        iPhoneReviews.add(iphonereview) // Adding the new review to the list
        notifyItemInserted(iPhoneReviews.size - 1) // Notifying the RecyclerView that a new item has been inserted at the last position in the list
    }
}