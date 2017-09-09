package com.fastapp.viroyal.fm_newstyle.api;

import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.model.entity.TrackInfoBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hanjiaqi on 2017/6/29.
 */

public interface ApiService {

    @GET("mobile/discovery/v2/category/metadata/albums")
    Observable<Data<HimalayanEntity>> getCommentList(@Query("calcDimension") String calcDimension,
                                                     @Query("categoryId") int categoryId, @Query("device") String device,
                                                     @Query("pageId") int pageId, @Query("pageSize") int pageSize, @Query("version") String version);

    @GET("mobile/v1/album")
    Observable<Data<HimalayanBean>> getAlbumsList(@Query("albumId") int albumId, @Query("pageSize")int pageSize);

    @GET("v1/track/baseInfo")
    Observable<TrackInfoBean> getTrackInfo(@Query("trackId")int trackId);

    @GET("mobile/v1/album/track")
    Observable<TracksData> getTrackList(@Query("albumId") int albumId, @Query("pageId") int pageId, @Query("pageSize")int pageSize);
}
