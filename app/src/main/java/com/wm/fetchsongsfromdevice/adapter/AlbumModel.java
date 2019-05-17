package com.wm.fetchsongsfromdevice.adapter;

public class AlbumModel {
    private long albumId;
    private String albumName;
    private String albumArtist;
    private String albumArt;
    private String albumTrack;
    private String albumMediaData;

    public String getAlbumMediaData() {
        return albumMediaData;
    }

    public void setAlbumMediaData(String albumMediaData) {
        this.albumMediaData = albumMediaData;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        this.albumArtist = albumArtist;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public String getAlbumTrack() {
        return albumTrack;
    }

    public void setAlbumTrack(String albumTrack) {
        this.albumTrack = albumTrack;
    }
}
