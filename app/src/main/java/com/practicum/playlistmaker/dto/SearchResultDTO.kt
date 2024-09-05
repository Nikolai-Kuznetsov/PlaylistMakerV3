package com.practicum.playlistmaker.dto

data class SearchResultDTO (
    val resultCount: Int,
    val results: List<SongDTO>
)