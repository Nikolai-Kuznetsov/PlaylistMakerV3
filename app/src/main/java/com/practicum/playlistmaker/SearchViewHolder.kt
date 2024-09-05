package com.practicum.playlistmaker;

import android.annotation.SuppressLint
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.practicum.playlistmaker.dto.SongDTO
import com.practicum.playlistmaker.utils.TimeFormatter


class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val trackNameView: TextView
    private val artistNameAndTimeView: TextView
    private val artworkUrl100View: ImageView

    init {
        trackNameView = itemView.findViewById(R.id.track_name)
        artistNameAndTimeView = itemView.findViewById(R.id.arist_and_time)
        artworkUrl100View = itemView.findViewById(R.id.cover)
    }

    @SuppressLint("SetTextI18n")
    fun bind(model: SongDTO) {
        trackNameView.text = model.trackName
        artistNameAndTimeView.text = model.artistName + " · " + TimeFormatter.convertTime(model.trackTimeMillis)

        val requestOptions = RequestOptions()
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.error_image)
            .transform(RoundedCorners(14))
            .diskCacheStrategy(DiskCacheStrategy.ALL)

        Glide.with(itemView.context)
            .load(model.artworkUrl100)
            .apply(requestOptions)
            .into(artworkUrl100View)
    }
}