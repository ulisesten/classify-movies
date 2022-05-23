package com.entrevista.appdepeliculas.viewmodel

import android.content.res.Resources
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.RequestQueue
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.entrevista.appdepeliculas.R
import com.entrevista.appdepeliculas.data.network.Repository
import com.entrevista.appdepeliculas.model.MovieDetail
import com.entrevista.appdepeliculas.model.VideoData
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.json.JSONArray

class MovieDetailViewModel(private val repository: Repository, private val movieId:String): ViewModel() {
    private lateinit var trailersUrl: String
    private lateinit var movieDetailUrl: String

    private val movieDetail: LiveData<MovieDetail> by lazy {
        MutableLiveData<MovieDetail>().also { liveData ->
            loadMovieDetail(liveData)
        }
    }

    private val videoData: LiveData<MutableList<VideoData>> by lazy {
        MutableLiveData<MutableList<VideoData>>().also { liveData ->
            loadVideoData(liveData)
        }
    }

    val detail: LiveData<MovieDetail>
        get() {
            return movieDetail
        }

    val video: LiveData<MutableList<VideoData>>
        get() {
            return videoData
        }

    fun setTrailersUrl(url:String){
        this.trailersUrl = url
    }

    fun setMovieDetailUrl(url:String){
        this.movieDetailUrl = url
    }

    fun setResource(res: Resources){
        viewModelScope.launch {
            trailersUrl = res.getString(R.string.trailers_url, movieId)
            movieDetailUrl = res.getString(R.string.detail_url, movieId)
        }
    }

    fun setImageViewSrc(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }

    @BindingAdapter("view")
    fun loadVideo(view:YouTubePlayerView, data:VideoData){
        viewModelScope.launch {
            view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(data.key, 0f)

                }
            })
        }
    }

    private fun loadMovieDetail(detail: MutableLiveData<MovieDetail>){
        viewModelScope.launch(Dispatchers.IO) {
            //val url =
            //    "https://api.themoviedb.org/3/movie/${movieId}?api_key=325b090f323374b186299125326c4c79"

            repository.getData(movieDetailUrl) { data ->
                val genresJson = data.getJSONArray("genres")
                val producersJson = data.getJSONArray("production_companies")
                detail.value = MovieDetail(
                    "",
                    data.getString("title"),
                    data.getString("vote_average"),
                    data.getString("release_date"),
                    parseJsonList(genresJson,"name"),
                    data.getString("popularity"),
                    data.getString("backdrop_path"),
                    data.getString("overview"),
                    parseJsonList(producersJson, "name")
                )
            }
        }
    }

    private fun parseJsonList(list:JSONArray, name:String):List<String>{
        val result = mutableListOf<String>()
        for(i in 0 until list.length()){
            result.add(list.getJSONObject(i).getString(name))
        }

        return result
    }

    private fun loadVideoData(detail: MutableLiveData<MutableList<VideoData>>){
        viewModelScope.launch(Dispatchers.IO) {
            //val url = "https://api.themoviedb.org/3/movie/${movieId}/videos?api_key=325b090f323374b186299125326c4c79"

            repository.getData(trailersUrl) { data ->
                val jsonList = data.getJSONArray("results")
                val list = mutableListOf<VideoData>()
                for (i in 0 until jsonList.length()) {
                    val video = jsonList.getJSONObject(i)
                    list.add(
                        VideoData(
                            video.getString("site"),
                            video.getString("key")
                        )
                    )
                }

                detail.value = list
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}