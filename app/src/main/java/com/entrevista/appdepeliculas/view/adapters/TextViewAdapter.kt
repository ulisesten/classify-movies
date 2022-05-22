package com.entrevista.appdepeliculas.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entrevista.appdepeliculas.databinding.TexfieldLayoutBinding

class TextViewAdapter(private val list:List<String>):RecyclerView.Adapter<TextViewAdapter.ViewHolder>() {

    class ViewHolder(val binding:TexfieldLayoutBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(value:String){
            binding.texfieldView.text = value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TexfieldLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}