package com.xuweic.dotify

import android.app.Application
import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class DotifyApplication: Application() {

    lateinit var songManager: SongManager
    lateinit var apiManager: ApiManager

    var readSongCount= 1

    override fun onCreate() {
        super.onCreate()

        apiManager = ApiManager(this)
        songManager = SongManager()
    }

}