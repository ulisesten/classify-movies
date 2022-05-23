package com.entrevista.appdepeliculas.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.RequestQueue
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.entrevista.appdepeliculas.data.network.Repository
import com.entrevista.appdepeliculas.model.Movie
import kotlinx.coroutines.*

class MainViewModel(private val repository:Repository, private var _url:String): ViewModel() {

    private val popularMovieList: LiveData<MutableList<Movie>> by lazy {
        MutableLiveData<MutableList<Movie>>().also { liveData ->
            loadContentList(liveData)
        }
    }

    private val playingNowMovieList: LiveData<MutableList<Movie>> by lazy {
        MutableLiveData<MutableList<Movie>>().also { liveData ->
            loadContentList(liveData)
        }
    }

    val popularContent: LiveData<MutableList<Movie>>
        get() {
            return popularMovieList
        }

    val playingNowContent: LiveData<MutableList<Movie>>
        get() {
            return playingNowMovieList
        }

    fun setUrl(url:String){
        this._url = url
    }

    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        url?.let {
            Glide.with(imageView)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        }
    }

    private fun loadContentList(liveData: MutableLiveData<MutableList<Movie>>){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getData(_url) { json ->
                val jsonList = json.getJSONArray("results")
                val list = mutableListOf<Movie>()

                for (i in 0 until jsonList.length()) {
                    val movie = jsonList.getJSONObject(i)

                    list.add(
                        Movie(
                            movie.getString("id"),
                            movie.getString("title"),
                            movie.getString("popularity"),
                            movie.getString("poster_path")
                        )
                    )
                }

                liveData.value = list.sortedByDescending {
                    it.popularity.toFloat()
                }.toMutableList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}