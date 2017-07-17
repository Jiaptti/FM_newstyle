package com.fastapp.viroyal.fm_newstyle.data.entity;

/**
 * Created by hanjiaqi on 2017/7/6.
 */

public class HimalayanBean {
    private AlbumBean album;
    private UserBean user;
    private TracksBean tracks;
    private boolean showFeedButton;
    private String giftAlbum;
    private String viewTab;
    private boolean hasGroupInfo;

    public AlbumBean getAlbum() {
        return album;
    }

    public void setAlbum(AlbumBean album) {
        this.album = album;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public TracksBean getTracks() {
        return tracks;
    }

    public void setTracks(TracksBean tracks) {
        this.tracks = tracks;
    }

    public boolean isShowFeedButton() {
        return showFeedButton;
    }

    public void setShowFeedButton(boolean showFeedButton) {
        this.showFeedButton = showFeedButton;
    }

    public String getGiftAlbum() {
        return giftAlbum;
    }

    public void setGiftAlbum(String giftAlbum) {
        this.giftAlbum = giftAlbum;
    }

    public String getViewTab() {
        return viewTab;
    }

    public void setViewTab(String viewTab) {
        this.viewTab = viewTab;
    }

    public boolean isHasGroupInfo() {
        return hasGroupInfo;
    }

    public void setHasGroupInfo(boolean hasGroupInfo) {
        this.hasGroupInfo = hasGroupInfo;
    }
}
