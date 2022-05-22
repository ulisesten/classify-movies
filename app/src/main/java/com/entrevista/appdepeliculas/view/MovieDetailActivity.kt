package com.entrevista.appdepeliculas.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import com.entrevista.appdepeliculas.R
import com.entrevista.appdepeliculas.databinding.ActivityMovieDetailBinding
import com.entrevista.appdepeliculas.model.MovieDetail
import com.entrevista.appdepeliculas.view.adapters.MovieVideoAdapter
import com.entrevista.appdepeliculas.view.adapters.TextViewAdapter
import com.entrevista.appdepeliculas.viewmodel.MovieDetailViewModel


class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.getStringExtra(MOVIE_ID) as String

        val detailUrl = resources.getString(R.string.detail_url, movieId)

        val model = MovieDetailViewModel(Volley.newRequestQueue(this), detailUrl, movieId)

        model.detail.observe(this){ details ->

            val posterUrl = resources.getString(R.string.poster_path_url, details.posterPath)

            attachData(binding, details)
            model.setImageUrl(binding.ivPoster, posterUrl)

        }

        model.video.observe(this){ videoData ->
            binding.videoRecyclerview.adapter = MovieVideoAdapter(videoData)
        }
    }

    private fun attachData(binding: ActivityMovieDetailBinding, details:MovieDetail){
        with(binding) {
            tvDetailTitle.text = details.title
            tvReleaseDate.text = details.releaseDate
            tvDetailSynopsis.text = details.overview

            genreRecyclerview.adapter = TextViewAdapter(details.genres)
            producerRecyclerview.adapter = TextViewAdapter(details.producers)

        }
    }

}