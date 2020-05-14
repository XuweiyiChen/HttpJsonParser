package com.xuweic.dotify.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.nfc.Tag
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ericchee.songdataprovider.Song
import com.xuweic.dotify.DotifyApplication
import com.xuweic.dotify.R
import com.xuweic.dotify.SongAdaptor
import com.xuweic.dotify.SongManager
import kotlin.properties.Delegates

class SongListFragment: Fragment() {

    companion object {
        val TAG = SongListFragment::class.java.simpleName

        const val SONG_LIST = "song list"
    }

    private lateinit var everySongs: List<Song>

    private var onEmailSelectedListener: OnEmailSelectedListener? = null
    private lateinit var songAdaptor: SongAdaptor
    lateinit var dotifyApplication: DotifyApplication
    lateinit var songManager: SongManager

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is OnEmailSelectedListener) {
            onEmailSelectedListener = context
        }
        if (context != null) {
            dotifyApplication = context.applicationContext as DotifyApplication
            songManager = dotifyApplication.songManager
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        dotifyApplication?.allSongs?.let {
//            this.everySongs = it
//        } ?: run {
//            this.everySongs = listOf()
//        }
        this.everySongs = songManager?.allSongs ?: listOf()

    }

    fun updateShuffle(newSongs: List<Song>) {
        songAdaptor.change(newSongs as MutableList<Song>)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.activity_recyclerview, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvSongs = view.findViewById<RecyclerView>(R.id.rvSongs)

        songAdaptor = context?.let { SongAdaptor(everySongs, it) }!!
        rvSongs.adapter = songAdaptor

        if (songAdaptor != null) {
            songAdaptor.onSongClickListener = {song ->
                onEmailSelectedListener?.onSongSelected(song)
                dotifyApplication.songManager.onSongClick()
            }
        }
    }
}

interface OnEmailSelectedListener {
    fun onSongSelected(song: Song)
}