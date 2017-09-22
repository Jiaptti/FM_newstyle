package com.fastapp.viroyal.fm_newstyle.model.entity;

import com.fastapp.viroyal.fm_newstyle.api.Api;
import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.base.BaseEntity;

import rx.Observable;

/**
 * Created by hanjiaqi on 2017/6/28.
 */

public class HimalayanEntity extends BaseEntity {
    private int id;
    private int albumId;
    private int uid;
    private String intro;
    private String nickname;
    private String albumCoverUrl290;
    private String coverSmall;
    private String coverMiddle;
    private String coverLarge;
    private String title;
    private String tags;
    private int tracks;
    private int playsCounts;
    private int isFinished;
    private int serialState;
    private int trackId;
    private String trackTitle;
    private String provider;
    private boolean isPaid;
    private int commentsCount;
    private int priceTypeId;
    private int refundSupportType;
    private boolean isVipFree;
    private boolean fromRecent;
    private String categoryName;

    public boolean isFromRecent() {
        return fromRecent;
    }

    public void setFromRecent(boolean fromRecent) {
        this.fromRecent = fromRecent;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAlbumCoverUrl290() {
        return albumCoverUrl290;
    }

    public void setAlbumCoverUrl290(String albumCoverUrl290) {
        this.albumCoverUrl290 = albumCoverUrl290;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }

    public int getPlaysCounts() {
        return playsCounts;
    }

    public void setPlaysCounts(int playsCounts) {
        this.playsCounts = playsCounts;
    }

    public int getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

    public int getSerialState() {
        return serialState;
    }

    public void setSerialState(int serialState) {
        this.serialState = serialState;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        this.trackTitle = trackTitle;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(int priceTypeId) {
        this.priceTypeId = priceTypeId;
    }

    public int getRefundSupportType() {
        return refundSupportType;
    }

    public void setRefundSupportType(int refundSupportType) {
        this.refundSupportType = refundSupportType;
    }

    public boolean isIsVipFree() {
        return isVipFree;
    }

    public void setIsVipFree(boolean isVipFree) {
        this.isVipFree = isVipFree;
    }

    @Override
    public Observable<Data<HimalayanEntity>> getPageAt(int categoryId, int pageId, int pageSize) {
        return Api.getInstance().getApiService().getCommentList("hot",categoryId,"android",pageId, pageSize,"5.4.93");
    }
}
