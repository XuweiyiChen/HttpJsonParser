package com.xuweic.dotify

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class ApiManager(context: Context) {

    private var queue: RequestQueue = Volley.newRequestQueue(context)
    lateinit var allSongs: UserInfo

    fun getAllSong(onAllSongReady: (UserInfo) -> Unit) {
        Log.i("chenxuweiyi", "hey I am here")
        val songURL = "https://raw.githubusercontent.com/echeeUW/codesnippets/master/user_info.json"

        val request = StringRequest(Request.Method.GET, songURL,
            { response ->
                // success
                val gson = Gson()
                allSongs = gson.fromJson(response, UserInfo::class.java)

                onAllSongReady(allSongs)
            },
            {
                // error
                onAllSongReady(allSongs)
            })

        queue.add(request)
    }

}