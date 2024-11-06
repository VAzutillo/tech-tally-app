package com.example.techtally

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.techtally.laptopAppleMacbookM3ProReviewsAdapter.AppleMacbookM3ProViewHolder

class SamsungGalaxyTabS10UltraReviewsAdapter (private var samsunggalaxytabs10ultraReviews: MutableList<SamsungGalaxyTabS10UltraReview>) : RecyclerView.Adapter<SamsungGalaxyTabS10UltraReviewsAdapter.SamsungGalaxyTabS10UltraViewHolder>()  {
    // Inner class 'ReviewViewHolder' that represents a single item view in the RecyclerView.
    // It holds references to the TextViews inside the layout for displaying review details.
    inner class SamsungGalaxyTabS10UltraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Finding TextViews from the layout using their IDs.
        private val SamsungGalaxyTabS10UltraNameOfTheUser: TextView =
            itemView.findViewById(R.id.SamsungGalaxyTabS10UltraNameOfTheUser)
        private val SamsungGalaxyTabS10UltraRateOfTheUser: TextView =
            itemView.findViewById(R.id.SamsungGalaxyTabS10UltraRateOfTheUser)
        private val SamsungGalaxyTabS10UltraCommentOfTheUser: TextView =
            itemView.findViewById(R.id.SamsungGalaxyTabS10UltraCommentOfTheUser)

        // bind function sets the review data (username, rating, comment) to the corresponding TextViews.
        fun bind(samsunggalaxytabs10ultrareview: SamsungGalaxyTabS10UltraReview) {
            // Setting the username TextView with the 'username' property from the Review object.
            SamsungGalaxyTabS10UltraNameOfTheUser.text = samsunggalaxytabs10ultrareview.username
            // Converting the rating (integer) to a string and setting it to the rating TextView.
            SamsungGalaxyTabS10UltraRateOfTheUser.text = samsunggalaxytabs10ultrareview.rating.toString()
            // Setting the comment from the Review object to the comment TextView.
            SamsungGalaxyTabS10UltraCommentOfTheUser.text = samsunggalaxytabs10ultrareview.comment
        }
}
    // method to called when the RecyclerView needs a new ViewHolder (for each item in the list).
    // It inflates the item layout XML (samsung_galaxy_s24_review_item_layout) and returns a ReviewViewHolder object.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.example.techtally.SamsungGalaxyTabS10UltraReviewsAdapter.SamsungGalaxyTabS10UltraViewHolder {
        // Inflating the XML layout for a single review item in the RecyclerView.
        val SamsungGalaxyTabS10Ultraview = LayoutInflater.from(parent.context)
            .inflate(R.layout.samsung_galaxy_tab_s10_ultra_review_item_layout, parent, false)
        return SamsungGalaxyTabS10UltraViewHolder(SamsungGalaxyTabS10Ultraview)   // Returning a new instance of ReviewViewHolder, which contains the inflated view.
    }
    // method to called to bind data to the ViewHolder at the given position
    // It takes the review data at the specified position in the list and binds it to the ViewHolder.
    override fun onBindViewHolder(holder: SamsungGalaxyTabS10UltraViewHolder, position: Int) {
        // Getting the review data at the current position and binding it to the ViewHolder.
        holder.bind(samsunggalaxytabs10ultraReviews[position])
    }

    // This method returns the total number of items in the 'reviews' list
    override fun getItemCount(): Int {
        // Returning the size of the reviews list, which is the number of items in the RecyclerView
        return samsunggalaxytabs10ultraReviews.size
    }
    // Method to add a new review to the list and notify the adapter
    fun addReview(samsunggalaxytabs10ultrareview: SamsungGalaxyTabS10UltraReview) {
        samsunggalaxytabs10ultraReviews.add(samsunggalaxytabs10ultrareview) // Adding the new review to the list
        notifyItemInserted(samsunggalaxytabs10ultraReviews.size - 1) // Notifying the RecyclerView that a new item has been inserted at the last position in the list
    }
}