package com.entrevista.appdepeliculas.model

class MovieDetail(
    val id: String,
    val title: String,
    val voteAverage:String,
    val releaseDate:String,
    val genres:List<String>,
    val popularity: String,
    val posterPath: String,
    val overview:String,
    val producers:List<String>
    )