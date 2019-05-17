package com.wm.fetchsongsfromdevice.adapter

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.wm.fetchsongsfromdevice.R


internal class ArtistAdapter(private val context: Context, private val listOfArtist: ArrayList<ArtistModel>) : RecyclerView.Adapter<ArtistAdapter.ViewHolderAlbum>() {
    override fun getItemCount(): Int {
        return listOfArtist.size;
    }

    override fun onCreateViewHolder(parentGroup: ViewGroup, position: Int): ViewHolderAlbum {
        return ViewHolderAlbum(LayoutInflater.from(context).inflate(R.layout.artist_row_item, parentGroup, false))
    }

    override fun onBindViewHolder(holder: ViewHolderAlbum, position: Int) {
        holder.albumTitle.text = listOfArtist[position].artist
        holder.textViewSongs.text = listOfArtist[position].numberOfAlbum+" Albums"
        holder.textViewTracks.text = listOfArtist[position].numberOfTracks+" Tracks"
    }

    class ViewHolderAlbum(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var albumTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        var textViewSongs: TextView = itemView.findViewById(R.id.textViewSongs)
        var textViewTracks: TextView = itemView.findViewById(R.id.textViewTracks)
    }


}
