package com.fastapp.viroyal.fm_newstyle.api;

import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.model.entity.NavigationBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksData;
import com.fastapp.viroyal.fm_newstyle.model.entity.TracksInfo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hanjiaqi on 2017/6/29.
 */

public interface ApiService {

    @GET("mobile/discovery/v1/categories")
    Observable<Data<List<NavigationBean>>> getNavigationList(@Query("device")String device, @Query("picVersion")int picVersion, @Query("scale")int scale);

    @GET("mobile/discovery/v2/category/metadata/albums")
    Observable<Data<List<HimalayanEntity>>> getCommentList(@Query("calcDimension") String calcDimension,
                                                     @Query("categoryId") int categoryId, @Query("device") String device,
                                                     @Query("pageId") int pageId, @Query("pageSize") int pageSize, @Query("version") String version);

    @GET("mobile/v1/album/track")
    Observable<Data<TracksBean>> getTrackList(@Query("albumId") int albumId, @Query("pageId") int pageId, @Query("pageSize")int pageSize);



    @GET("mobile/discovery/v3/rankingList/track")
    Observable<Data<List<TracksBeanList>>> getHotTracksList(@Query("rankingListId")int rankingListId, @Query("pageId")int pageId, @Query("pageSize")int pageSize,
                                               @Query("device") String device, @Query("target")String target, @Query("version")String version);

    @GET("v1/track/baseInfo")
    Observable<TracksInfo> getTracksInfo(@Query("trackId")int trackId);
}
