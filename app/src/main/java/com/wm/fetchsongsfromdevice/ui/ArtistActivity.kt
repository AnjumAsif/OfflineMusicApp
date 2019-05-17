package com.wm.fetchsongsfromdevice.ui

import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.wm.fetchsongsfromdevice.R
import com.wm.fetchsongsfromdevice.adapter.AlbumAdapter
import com.wm.fetchsongsfromdevice.adapter.AlbumModel
import com.wm.fetchsongsfromdevice.adapter.ArtistAdapter
import com.wm.fetchsongsfromdevice.adapter.ArtistModel
import kotlinx.android.synthetic.main.activity_artist.*
import kotlinx.android.synthetic.main.activity_main.*

class ArtistActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artist)
        recyclerArtist.layoutManager = LinearLayoutManager(this)
        recyclerArtist.adapter = ArtistAdapter(this, getListOfArtist(this));

    }
    private fun getListOfArtist(context: ArtistActivity): ArrayList<ArtistModel> {
        val where: String? = null
        val uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI
        val id = MediaStore.Audio.Artists._ID
        val artistKey = MediaStore.Audio.Artists.ARTIST_KEY
        val artist = MediaStore.Audio.Artists.ARTIST
        val numberOfAlbums = MediaStore.Audio.Artists.NUMBER_OF_ALBUMS
        val numberOfTracks = MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        val columns = arrayOf(id, artistKey, artist, numberOfAlbums, numberOfTracks)
        val cursor = context.contentResolver.query(uri, columns, where, null, null)
        val list = ArrayList<ArtistModel>()

        // add artist to list
        if (cursor.moveToFirst()) {
            do {
                val artistData = ArtistModel(
                        artistId = cursor.getLong(cursor.getColumnIndex(id)),
                        artist = cursor.getString(cursor.getColumnIndex(artist)),
                        artistKey = cursor.getString(cursor.getColumnIndex(artistKey)),
                        numberOfAlbum = cursor.getString(cursor.getColumnIndex(numberOfAlbums)),
                        numberOfTracks = cursor.getString(cursor.getColumnIndex(numberOfTracks)))
                list.add(artistData)

            } while (cursor.moveToNext())
        }
        cursor.close()

        return list
    }
}