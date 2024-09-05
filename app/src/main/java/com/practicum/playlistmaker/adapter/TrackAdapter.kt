package com.practicum.playlistmaker.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.SearchViewHolder
import com.practicum.playlistmaker.dto.SongDTO

class TrackAdapter (
    private var tracks: List<SongDTO>
) : RecyclerView.Adapter<SearchViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_track_card, parent, false)
        return SearchViewHolder(view)
    }

    override fun getItemCount(): Int = tracks.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItems(newItems: List<SongDTO>) {
        tracks = newItems
        notifyDataSetChanged()
    }

}