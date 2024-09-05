package com.practicum.playlistmaker.apiservice

import com.practicum.playlistmaker.dto.SearchResultDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIInterface {
    @GET("search")
    fun search(@Query("entity") entity: String, @Query("term") text: String) : Call<SearchResultDTO>
}