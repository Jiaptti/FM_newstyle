package com.fastapp.viroyal.fm_newstyle.api;

import com.fastapp.viroyal.fm_newstyle.data.base.Data;
import com.fastapp.viroyal.fm_newstyle.data.entity.HimalayanBean;
import com.fastapp.viroyal.fm_newstyle.data.entity.HimalayanEntity;
import com.fastapp.viroyal.fm_newstyle.data.entity.TracksBeanList;
import com.fastapp.viroyal.fm_newstyle.util.test.HimalayanEntityTest;
import com.fastapp.viroyal.fm_newstyle.util.test.HimalayanTest;

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

    @GET("metadata/albums")
    Observable<HimalayanTest<HimalayanEntityTest>> getCommentList1(@Query("calcDimension") String calcDimension,
                                                                    @Query("categoryId") int categoryId, @Query("device") String device,
                                                                    @Query("pageId") int pageId, @Query("pageSize") int pageSize, @Query("version") String version);

    @GET("mobile/v1/album")
    Observable<Data<HimalayanBean>> getAlbumsList(@Query("albumId") int albumId,@Query("pageSize")int pageSize);

    @GET("mobile/v1/album")
    Observable<Data<HimalayanBean>> getAlbumsList1(@Query("albumId") int albumId, @Query("pageId")int pageId,
                                                   @Query("pageSize")int pageSize);

}
