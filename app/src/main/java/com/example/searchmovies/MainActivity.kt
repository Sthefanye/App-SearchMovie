package com.example.searchmovies

import com.example.searchmovies.retrofit.MoviesApi
import com.example.searchmovies.model.Movies
import com.example.searchmovies.model.MoviesResponse
import com.example.searchmovies.adapter.MoviesAdapter
import com.example.searchmovies.retrofit.RetrofitClient
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var btnSearch: ImageButton
    private lateinit var editTextSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSearch = findViewById(R.id.btn_search)
        editTextSearch = findViewById(R.id.search_movie)

        bindings()
    }
    // Inserting actions into buttons
    fun bindings(){
        btnSearch.setOnClickListener {
            getData(editTextSearch.text.toString())
            editTextSearch.hideKeyboard()
        }
        editTextSearch.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    getData(editTextSearch.text.toString())
                    editTextSearch.hideKeyboard()
                    false
                }
                else -> false
            }
        }
    }
    // get the movies
    fun getData(text: String) {
        val callback = RetrofitClient.moviesApi.getMovies(text)

        callback.enqueue(object : Callback<MoviesResponse> {

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                t.message?.let { Log.d("ERRO", it) }
                Toast.makeText (baseContext, "Oops, movie not found!",Toast.LENGTH_SHORT).show ()
            }

            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                var movieList = response.body()?.Search

                if (movieList != null) {
                    addMovies(movieList)
                }else {
                    Toast.makeText(baseContext,"Oops, movie not found!", Toast.LENGTH_SHORT).show ()
                }
            }
        })
    }

    fun addMovies(listMovies: List<Movies>){
        var recyclerView = findViewById<RecyclerView>(R.id.list_movies)
        var adapter = MoviesAdapter(listMovies)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

}