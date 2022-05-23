package com.entrevista.appdepeliculas.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.Volley
import com.entrevista.appdepeliculas.R
import com.entrevista.appdepeliculas.data.network.Repository
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
        binding.detailToolbar.setNavigationOnClickListener(onBackPressed)

        val movieId = intent.getStringExtra(MOVIE_ID) as String

        val repository = Repository(Volley.newRequestQueue(this))
        val model = MovieDetailViewModel(repository, movieId)
        model.setResource(resources)

        model.detail.observe(this){ details ->

            val posterUrl = resources.getString(R.string.poster_path_url, details.posterPath)

            attachData(binding, details)
            model.setImageViewSrc(binding.ivPoster, posterUrl)

        }

        model.video.observe(this){ videoData ->
            binding.videoRecyclerview.adapter = MovieVideoAdapter(videoData)
        }
    }

    private fun attachData(binding: ActivityMovieDetailBinding, details:MovieDetail){
        with(binding) {
            tvDetailTitle.text = details.title
            "(${details.releaseDate})".also { tvReleaseDate.text = it }
            "Rate ${details.voteAverage}/10".also { tvRate.text = it }
            tvDetailSynopsis.text = details.overview

            genreRecyclerview.adapter = TextViewAdapter(details.genres)
            producerRecyclerview.adapter = TextViewAdapter(details.producers)

        }
    }

    private val onBackPressed = View.OnClickListener {
        finishAndRemoveTask()
    }

}