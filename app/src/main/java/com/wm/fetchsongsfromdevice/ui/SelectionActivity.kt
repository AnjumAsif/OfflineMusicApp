package com.wm.fetchsongsfromdevice.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wm.fetchsongsfromdevice.R
import kotlinx.android.synthetic.main.activity_selection.*

class SelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        button_song.setOnClickListener {
            startActivity(Intent(this@SelectionActivity, MainActivity::class.java))
        }
        button_album.setOnClickListener {
            startActivity(Intent(this@SelectionActivity, AlbumActivity::class.java))
        }
        button_artist.setOnClickListener {
            startActivity(Intent(this@SelectionActivity, ArtistActivity::class.java))
        }
    }
}
