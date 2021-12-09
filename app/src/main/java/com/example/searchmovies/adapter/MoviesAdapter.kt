package com.example.searchmovies.adapter
import com.example.searchmovies.retrofit.MoviesApi
import com.example.searchmovies.model.Movies
import com.example.searchmovies.retrofit.RetrofitClient
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovies.R
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesAdapter (private val list: List<Movies>)
    : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var title: TextView? = null
        var year: TextView? = null
        var poster: ImageView? = null
        var rank: TextView? = null
        var durantion: TextView? = null
        var plot: TextView? = null


        init {
            title = view.findViewById(R.id.title_movie)
            year = view.findViewById(R.id.year_movie)
            poster = view.findViewById(R.id.poster_movie)
            rank = view.findViewById(R.id.to_rank)
            durantion = view.findViewById(R.id.duration)
            plot = view.findViewById(R.id.plot)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_movies, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title?.text = list[position].Title
        Picasso.get().load(list[position].Poster).into(holder.poster)

        setInfoAdditional(holder, list[position].ID)
    }

    override fun getItemCount() = list.size

    // Search for additional movie information
    fun setInfoAdditional(holder: ViewHolder, id: String) {
        val callback = RetrofitClient.moviesApi.getInfoAdditional(id)

       callback.enqueue(object: Callback<Movies> {
            override fun onFailure(call: Call<Movies>, t: Throwable) {
                t.message?.let { Log.d("ERROR", it) }
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show ()
            }

            override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                val movie = response.body()
                holder.rank?.text = movie?.Rating.toString()
                holder.durantion?.text = movie?.Runtime.toString()
                holder.plot?.text = movie?.Plot.toString()
                holder.year?.text = movie?.Year.toString()
            }
        })
    }
}