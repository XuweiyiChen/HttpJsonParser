package com.xuweic.dotify

import com.ericchee.songdataprovider.Song
import com.ericchee.songdataprovider.SongDataProvider

class SongManager {
    var allSongs: MutableList<Song> = SongDataProvider.getAllSongs() as MutableList<Song>

    var readSongCount= 1

    fun onSongClick() {
        readSongCount++
    }

}