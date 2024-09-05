package com.practicum.playlistmaker.dto

data class SongDTO(
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: Int,
    val artworkUrl100: String
)