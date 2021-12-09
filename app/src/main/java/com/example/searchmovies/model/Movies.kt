package com.example.searchmovies.model

import com.google.gson.annotations.SerializedName

data class Movies (
    @SerializedName("Title")
    var Title: String,
    @SerializedName("Released")
    var Year: String,
    @SerializedName("Poster")
    var Poster: String,
    @SerializedName("Runtime")
    var Runtime: String,
    @SerializedName("imdbRating")
    var Rating: Float,
    @SerializedName("Plot")
    var Plot: String,
    @SerializedName("imdbID")
    var ID: String,
    @SerializedName("Director")
    var Diretion: String
)