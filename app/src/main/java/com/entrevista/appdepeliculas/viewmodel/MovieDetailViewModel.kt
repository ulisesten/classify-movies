package com.entrevista.appdepeliculas.viewmodel

import android.content.res.Resources
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.entrevista.appdepeliculas.R
import com.entrevista.appdepeliculas.data.network.IRepository
import com.entrevista.appdepeliculas.model.MovieDetail
import com.entrevista.appdepeliculas.model.VideoData
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.json.JSONArray

class MovieDetailViewModel(private val repository: IRepository, private val movieId:String): ViewModel() {
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
    fun loadVideo(view:YouTubePlayerView, key:String){
        viewModelScope.launch(Dispatchers.IO) {
            view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(key, 0f)

                }
            })
        }
    }

    private fun loadMovieDetail(detail: MutableLiveData<MovieDetail>){
        viewModelScope.launch(Dispatchers.IO) {

            repository.getData(movieDetailUrl) { data ->
                val genresJson = data.getJSONArray("genres")
                val producersJson = data.getJSONArray("production_companies")
                detail.value = MovieDetail(
                    data.getString("id"),
                    data.getString("title"),
                    data.getString("vote_average"),
                    data.getString("release_date"),
                    parseJsonList(genresJson),
                    data.getString("popularity"),
                    data.getString("backdrop_path"),
                    data.getString("overview"),
                    parseJsonList(producersJson)
                )
            }
        }
    }

    private fun parseJsonList(list: JSONArray):List<String>{
        val result = mutableListOf<String>()
        for(i in 0 until list.length()){
            result.add(list.getJSONObject(i).getString("name"))
        }

        return result
    }

    private fun loadVideoData(detail: MutableLiveData<MutableList<VideoData>>){
        viewModelScope.launch(Dispatchers.IO) {

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