package com.fastapp.viroyal.fm_newstyle.api;

import com.fastapp.viroyal.fm_newstyle.model.base.Data;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.model.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.model.entity.RankingTracks;
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

    @GET("mobile/v1/album/track")
    Observable<TracksData> getTrackList(@Query("albumId") int albumId, @Query("pageId") int pageId, @Query("pageSize")int pageSize);

    @GET("mobile/discovery/v3/rankingList/track")
    Observable<RankingTracks> getHotTracksList(@Query("rankingListId")int rankingListId, @Query("pageId")int pageId, @Query("pageSize")int pageSize,
                                               @Query("device") String device, @Query("target")String target, @Query("version")String version);
}
