package com.entrevista.appdepeliculas.data.network

import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

class Repository(private val requestQueue: RequestQueue) {

    fun getData(url: String, cb: (arr: JSONObject) -> Unit ){

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET
            , url
            , null
            , { response ->
                cb(response)
            }
            , {
                //Toast.makeText(this.application, "Error", Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(jsonObjectRequest)
    }
}