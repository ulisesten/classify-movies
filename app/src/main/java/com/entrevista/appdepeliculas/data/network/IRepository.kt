package com.entrevista.appdepeliculas.data.network

import org.json.JSONObject

interface IRepository {

    fun getData(url: String, cb: (arr: JSONObject) -> Unit )
}