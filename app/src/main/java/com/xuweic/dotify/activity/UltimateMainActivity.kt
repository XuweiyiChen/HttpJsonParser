package com.xuweic.dotify.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.ericchee.songdataprovider.Song
import com.squareup.picasso.Picasso
import com.xuweic.dotify.ApiManager
import com.xuweic.dotify.DotifyApplication
import com.xuweic.dotify.R
import com.xuweic.dotify.fragment.OnEmailSelectedListener
import com.xuweic.dotify.fragment.SongListFragment

class UltimateMainActivity : AppCompatActivity(), OnEmailSelectedListener {

    private lateinit var tvBrief: TextView
    private lateinit var btShuffle: Button
    private lateinit var btFetch: Button
    private lateinit var app: ApiManager
    private lateinit var tvUsername: TextView
    private lateinit var imgPhone: ImageView


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ultimate__main_)

        title = "Fetch for Username's Info"
        btFetch = findViewById(R.id.btFetch)
        tvUsername = findViewById(R.id.tvUserName)
        imgPhone = findViewById(R.id.imgPhone)


        app = (application as DotifyApplication).apiManager


        btFetch.setOnClickListener {
            app.getAllSong { allSongs ->
                var allSongs = allSongs
                if (allSongs!=null) {
                    tvUsername.text = allSongs.firstName + " " + allSongs.lastName
                    Picasso.get().load(allSongs.profilePicURL).into(imgPhone);
                } else {
                    Toast.makeText(this, "Here are some errors!", Toast.LENGTH_SHORT).show()
                }
            }
        }


        val songListFragment = SongListFragment()
        val context = this
        val applicationContext = context.applicationContext
        val dotifyApplication: DotifyApplication = applicationContext as DotifyApplication
        val songManager = dotifyApplication.songManager
        val allSongs: MutableList<Song> = songManager.allSongs


        val argumentBundle = Bundle()
        argumentBundle.putParcelableArray(SongListFragment.SONG_LIST, allSongs.toTypedArray());
        songListFragment.arguments = argumentBundle


        songListFragment.arguments = argumentBundle


        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragContainer, songListFragment, SongListFragment.TAG)
            .commit()

        supportFragmentManager.addOnBackStackChangedListener {
            val hasBackStack = supportFragmentManager.backStackEntryCount > 0
            if (hasBackStack) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }

        btShuffle = findViewById(R.id.btShuffle)

        btShuffle.setOnClickListener {
            val newSongs = allSongs.shuffled()
            getSongListFragment()?.updateShuffle(newSongs)
        }
        tvBrief = findViewById(R.id.tvBrief)
    }


    private fun getSongListFragment() = supportFragmentManager.findFragmentByTag(SongListFragment.TAG) as? SongListFragment

    @SuppressLint("SetTextI18n")
    override fun onSongSelected(song: Song) {
        val context = this
        val dotifyApplication: DotifyApplication = context.applicationContext as DotifyApplication
        tvBrief.text = "How many time clicks: " + dotifyApplication.songManager.readSongCount.toString()
    }
}

