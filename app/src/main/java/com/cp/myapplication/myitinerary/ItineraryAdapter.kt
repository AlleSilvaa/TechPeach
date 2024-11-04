package com.cp.myapplication.myitinerary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cp.myapplication.R
import com.cp.myapplication.network.Itinerario

class ItineraryAdapter(
    private val itineraries: List<Itinerario>,
    private val onClick: (Itinerario) -> Unit
) : RecyclerView.Adapter<ItineraryAdapter.ItineraryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItineraryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_itinerary, parent, false)
        return ItineraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItineraryViewHolder, position: Int) {
        val itinerary = itineraries[position]
        holder.bind(itinerary, onClick)
    }

    override fun getItemCount(): Int {
        return itineraries.size
    }

    class ItineraryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.text_view_itinerary_name)

        fun bind(itinerary: Itinerario, onClick: (Itinerario) -> Unit) {
            nameTextView.text = itinerary.nome
            itemView.setOnClickListener { onClick(itinerary) }
        }
    }
}
