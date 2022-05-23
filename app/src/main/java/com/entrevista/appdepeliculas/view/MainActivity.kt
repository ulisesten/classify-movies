package com.entrevista.appdepeliculas.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.volley.toolbox.Volley
import com.entrevista.appdepeliculas.R
import com.entrevista.appdepeliculas.data.network.Repository
import com.entrevista.appdepeliculas.databinding.ActivityMainBinding
import com.entrevista.appdepeliculas.model.Movie
import com.entrevista.appdepeliculas.view.adapters.MainAdapter
import com.entrevista.appdepeliculas.viewmodel.MainViewModel

const val MOVIE_ID = "movie id"

class MainActivity: AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository(Volley.newRequestQueue(this))
        val viewModel = MainViewModel(repository, resources.getString(R.string.popular_url))

        viewModel.popularContent.observe(this){ popularMovieList ->
            binding.popularRecyclerview
                .adapter = MainAdapter(popularMovieList, viewModel::setImageUrl, ::adapterOnClick)
        }

        viewModel.setUrl(resources.getString(R.string.playing_now_url))

        viewModel.playingNowContent.observe(this){ playingNowMovieList ->
            binding.playingNowRecyclerview
                .adapter = MainAdapter(playingNowMovieList, viewModel::setImageUrl, ::adapterOnClick)
        }

    }

    /**
     * @author Ulises Mtz Elías
     * @brief Click event handler: Cambia a la actividad de información detallada sobre la película
     * @param movie: clase de datos, modelo de película, se encuentra en /model
     * */
    private fun adapterOnClick(movie: Movie){
        val intent = Intent(this@MainActivity, MovieDetailActivity()::class.java)
        intent.putExtra(MOVIE_ID, movie.id)
        startActivity(intent)
    }
}