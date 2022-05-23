package com.entrevista.appdepeliculas.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entrevista.appdepeliculas.databinding.VideoViewLayoutBinding
import com.entrevista.appdepeliculas.model.VideoData
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class MovieVideoAdapter(private val list:MutableList<VideoData>, private val loadVideo: (view: YouTubePlayerView, key:String) -> Unit): RecyclerView.Adapter<MovieVideoAdapter.ViewHolder>() {

    class ViewHolder(val binding: VideoViewLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VideoViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val provider = list[position]
        with(holder) {
            loadVideo(binding.videoView, provider.key)
        }
    }

    override fun getItemCount(): Int = list.size
}