package com.entrevista.appdepeliculas.view.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.entrevista.appdepeliculas.databinding.MovieViewLayoutBinding
import com.entrevista.appdepeliculas.model.Movie

class MainAdapter(
    private val movies: MutableList<Movie>,
    private val loadImage:(imageView: ImageView, url: String?) -> Unit,
    private val onClick:(movie: Movie) -> Unit): RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: MovieViewLayoutBinding, val loadImage:(imageView: ImageView, url: String?) -> Unit, val onClick: (Movie) -> Unit): RecyclerView.ViewHolder(binding.root){

            fun bind(movie: Movie) {
                binding.imageView.setOnClickListener { onClick(movie) }
                loadImage(binding.imageView, "https://image.tmdb.org/t/p/w500" + movie.posterPath)
            }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding = MovieViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding, loadImage, onClick)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder:MyViewHolder, position: Int) {
        val movie = movies[position]

        with(holder){
            bind(movie)
            binding.tvTitle.text = movie.title
            binding.tvPopularity.text = movie.popularity

        }
    }
}