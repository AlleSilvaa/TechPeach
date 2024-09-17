package com.cp.myapplication.myitinerary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cp.myapplication.R
import com.cp.myapplication.network.Itinerario

class ItineraryAdapter(
    private val itineraries: List<Itinerario>,
    private val onItemClick: (Itinerario) -> Unit
) : RecyclerView.Adapter<ItineraryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_itinerary, parent, false)
        return ItineraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItineraryViewHolder, position: Int) {
        val itinerary = itineraries[position]
        holder.bind(itinerary)
        holder.itemView.setOnClickListener { onItemClick(itinerary) }
    }

    override fun getItemCount(): Int = itineraries.size
}
