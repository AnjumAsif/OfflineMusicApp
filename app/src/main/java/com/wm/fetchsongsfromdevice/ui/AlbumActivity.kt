package com.wm.fetchsongsfromdevice.ui

import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.wm.fetchsongsfromdevice.R
import com.wm.fetchsongsfromdevice.adapter.AlbumAdapter
import com.wm.fetchsongsfromdevice.adapter.AlbumModel
import kotlinx.android.synthetic.main.activity_main.*


class AlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album)
        //for list view
        //recycler.layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = GridLayoutManager(this, 3)
        recycler.adapter = AlbumAdapter(this, getListOfAlbums(this));
    }

    private fun getListOfAlbums(context: Context): ArrayList<AlbumModel> {
        val where: String? = null
        val uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
        val _id = MediaStore.Audio.Albums._ID
        val album_name = MediaStore.Audio.Albums.ALBUM
        val artist = MediaStore.Audio.Albums.ARTIST
        val albumart = MediaStore.Audio.Albums.ALBUM_ART
        val tracks = MediaStore.Audio.Albums.NUMBER_OF_SONGS
//        val mediaData = MediaStore.Audio.Media.DATA
        //cursor.moveToPosition(position)
//        val pathId = cursor.getString(mediaData)
        val columns = arrayOf(_id, album_name, artist, albumart, tracks)
        val cursor = context.contentResolver.query(uri, columns, where, null, null)
        val list = ArrayList<AlbumModel>()

        // add playlsit to list

        if (cursor.moveToFirst()) {

            do {
                val albumData = AlbumModel()
                albumData
                        .albumId = cursor.getLong(cursor.getColumnIndex(_id))
                albumData.albumName = cursor.getString(cursor
                        .getColumnIndex(album_name))
                albumData.albumArtist = cursor.getString(cursor
                        .getColumnIndex(artist))
                albumData.albumArt = cursor.getString(cursor
                        .getColumnIndex(albumart))
                albumData.albumTrack = cursor.getString(cursor
                        .getColumnIndex(tracks))
               /* albumData.albumMediaData = cursor.getString(cursor
                        .getColumnIndexOrThrow(mediaData))*/
                list.add(albumData)

            } while (cursor.moveToNext())
        }
        cursor.close()

        return list
    }
}