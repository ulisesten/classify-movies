package com.entrevista.appdepeliculas.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entrevista.appdepeliculas.databinding.VideoViewLayoutBinding
import com.entrevista.appdepeliculas.model.VideoData
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class MovieVideoAdapter(private val list:MutableList<VideoData>): RecyclerView.Adapter<MovieVideoAdapter.ViewHolder>() {

    class ViewHolder(val binding: VideoViewLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VideoViewLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieVideoAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val provider = list[position]
        with(holder) {
            binding.videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(provider.key, 0f)
                }
            })
        }
    }

    override fun getItemCount(): Int = list.size
}