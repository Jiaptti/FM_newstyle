package com.fastapp.viroyal.fm_newstyle.model.entity;

import android.util.Log;

import com.fastapp.viroyal.fm_newstyle.AppConstant;
import com.fastapp.viroyal.fm_newstyle.api.Api;
import com.fastapp.viroyal.fm_newstyle.model.base.BaseEntity;

import rx.Observable;

/**
 * Created by hanjiaqi on 2017/7/6.
 */

public class TracksBeanList extends BaseEntity{
    private int trackId;
    private int uid;
    private String playUrl64;
    private String playUrl32;
    private String playPathHq;
    private String playPathAacv164;
    private String playPathAacv224;
    private String title;
    private int duration;
    private int albumId;
    private int rankingId;
    private boolean isPaid;
    private boolean isVideo;
    private boolean isDraft;
    private int processState;
    private long createdAt;
    private String coverSmall;
    private String coverMiddle;
    private String coverLarge;
    private String nickname;
    private String smallLogo;
    private int userSource;
    private int opType;
    private boolean isPublic;
    private int likes;
    private int playtimes;
    private int comments;
    private int shares;
    private int status;
    private int position;
    private String albumTitle;
    private int commentsCounts;
    private int favoritesCounts;
    private int id;
    private boolean isAuthorized;
    private boolean isFree;
    private String playPath32;
    private String playPath64;
    private int playsCounts;
    private int priceTypeId;
    private int sampleDuration;
    private int sharesCounts;
    private long updatedAt;
    private String tags;
    private boolean fromTrack;
    private String intro;


    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public boolean getFromTrack() {
        return fromTrack;
    }

    public void setFromTrack(boolean fromTrack) {
        this.fromTrack = fromTrack;
    }

    public int getRankingId() {
        return rankingId;
    }

    public void setRankingId(int rankingId) {
        this.rankingId = rankingId;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public int getCommentsCounts() {
        return commentsCounts;
    }

    public void setCommentsCounts(int commentsCounts) {
        this.commentsCounts = commentsCounts;
    }

    public int getFavoritesCounts() {
        return favoritesCounts;
    }

    public void setFavoritesCounts(int favoritesCounts) {
        this.favoritesCounts = favoritesCounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getPlayPath32() {
        return playPath32;
    }

    public void setPlayPath32(String playPath32) {
        this.playPath32 = playPath32;
    }

    public String getPlayPath64() {
        return playPath64;
    }

    public void setPlayPath64(String playPath64) {
        this.playPath64 = playPath64;
    }

    public int getPlaysCounts() {
        return playsCounts;
    }

    public void setPlaysCounts(int playsCounts) {
        this.playsCounts = playsCounts;
    }

    public int getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(int priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public int getSampleDuration() {
        return sampleDuration;
    }

    public void setSampleDuration(int sampleDuration) {
        this.sampleDuration = sampleDuration;
    }

    public int getSharesCounts() {
        return sharesCounts;
    }

    public void setSharesCounts(int sharesCounts) {
        this.sharesCounts = sharesCounts;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPlayUrl64() {
        return playUrl64;
    }

    public void setPlayUrl64(String playUrl64) {
        this.playUrl64 = playUrl64;
    }

    public String getPlayUrl32() {
        return playUrl32;
    }

    public void setPlayUrl32(String playUrl32) {
        this.playUrl32 = playUrl32;
    }

    public String getPlayPathHq() {
        return playPathHq;
    }

    public void setPlayPathHq(String playPathHq) {
        this.playPathHq = playPathHq;
    }

    public String getPlayPathAacv164() {
        return playPathAacv164;
    }

    public void setPlayPathAacv164(String playPathAacv164) {
        this.playPathAacv164 = playPathAacv164;
    }

    public String getPlayPathAacv224() {
        return playPathAacv224;
    }

    public void setPlayPathAacv224(String playPathAacv224) {
        this.playPathAacv224 = playPathAacv224;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public boolean isDraft() {
        return isDraft;
    }

    public void setDraft(boolean draft) {
        isDraft = draft;
    }

    public int getProcessState() {
        return processState;
    }

    public void setProcessState(int processState) {
        this.processState = processState;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverMiddle() {
        return coverMiddle;
    }

    public void setCoverMiddle(String coverMiddle) {
        this.coverMiddle = coverMiddle;
    }

    public String getCoverLarge() {
        return coverLarge;
    }

    public void setCoverLarge(String coverLarge) {
        this.coverLarge = coverLarge;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSmallLogo() {
        return smallLogo;
    }

    public void setSmallLogo(String smallLogo) {
        this.smallLogo = smallLogo;
    }

    public int getUserSource() {
        return userSource;
    }

    public void setUserSource(int userSource) {
        this.userSource = userSource;
    }

    public int getOpType() {
        return opType;
    }

    public void setOpType(int opType) {
        this.opType = opType;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getPlaytimes() {
        return playtimes;
    }

    public void setPlaytimes(int playtimes) {
        this.playtimes = playtimes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public Observable getPageAt(int categoryId, int pageId, int pageSize) {
        if(categoryId == 57){
            return Api.getInstance().getApiService().getHotTracksList(categoryId, pageId, pageSize, "android", "main", "5.4.93");
        } else {
             return Api.getInstance().getApiService().getTrackList(categoryId, pageId, pageSize);
        }
    }
}
