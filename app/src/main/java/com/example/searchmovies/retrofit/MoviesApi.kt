package com.example.searchmovies.retrofit

import com.example.searchmovies.model.Movies
import com.example.searchmovies.model.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("?")
    fun getInfoAdditional(@Query("i") rating: String, @Query("apikey") apikey: String = "9b9bce0a") : Call<Movies>

    @GET("?")
    fun getMovies(@Query("s") title: String, @Query("apikey") apikey: String = "9b9bce0a") : Call<MoviesResponse>

}