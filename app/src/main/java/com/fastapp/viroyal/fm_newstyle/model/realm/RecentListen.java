package com.fastapp.viroyal.fm_newstyle.model.realm;

import io.realm.RealmObject;

/**
 * Created by hanjiaqi on 2017/9/20.
 */

public class RecentListen extends RealmObject{
    private int albumId;
    private int trackId;
    private String albumTitle;
    private String title;
    private String coverSmall;

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }
}
