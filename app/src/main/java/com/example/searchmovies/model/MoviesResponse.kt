package com.example.searchmovies.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse (
    @SerializedName("Search")
    var Search: List<Movies>,
    @SerializedName("totalResults")
    var TotalReults: Int,
    @SerializedName("Responde")
    var Response: Boolean
)

