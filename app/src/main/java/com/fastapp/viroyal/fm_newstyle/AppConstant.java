package com.fastapp.viroyal.fm_newstyle;

import java.io.File;

/**
 * Created by hanjiaqi on 2017/6/29.
 */

public class AppConstant {
    //DATA STORE
    public static final String DATA_PATH = AppContext.getInstance().getCacheDir().getAbsolutePath() + File.separator + "data";
    public static final String NET_DATA_PATH = DATA_PATH + File.separator + "net_cache";
    public static final String KEY_MODE_AUTO_CACHE = "key_mode_auto_cache";

    public static final String TAG = "tests";
    public static final String TYPE = "type";
    public static final String VH_CLASS = "vh_class";
    public static final String ALBUM_BUNDLE = "album_bundle";
    public static final String TRACK_BUNDLE = "track_bundle";
    public static final String DB_NAME = "himalayan,realm";
    public static final String PREF_NAME = "app.pref";
    public static final String TRACK_ID = "track_id";

    public static final String UPDATE_ITEM_STATUS = "update_item";
    public static final String MEDIA_START_PLAY = "media_start_play";
    public static final String LOADING_STATUS = "loading_item";
    public static final String ERROR_MESSAGE = "error_message";
    public static final String CURRENT_POSITION_VIEW = "current_position_view";
    public static final String UPDATE_TRACKS_UI = "update_tracks_ui";
    public static final String CACHE_PAGEID = "cache_page_id";
    public static final String MAX_COUNT = "max_count";
    public static final String SAVE_DATA = "save_data";
    public static final String MAX_PAGE_ID = "max_page_id";
    public static final String DATA_FILE = "cache_data_file.txt";

    public static final String FRAGMENT_MAIN = "fragment_main";
    public static final String RESET_DATA = "reset_data";
    public static final String PLAY_RECENT_STATE_CHANTE = "play_recent_state_change";


    //home tab and pagesize
    public static final int PAGESIZE = 10;
    public static final int PAGE_STORY = 11;
    public static final int PAGE_CROSSTALK = 12;
    public static final int PAGE_BOOK = 13;
    public static final int HOT_TRACKS_ID = 57;

    //current play status
    public static final int STATUS_PLAY = 101;
    public static final int STATUS_PAUSE = 102;
    public static final int STATUS_STOP = 103;
    public static final int STATUS_RESUME = 104;
    public static final int STATUS_NONE = 105;
    public static final int STATUS_UPDATE_TIME = 106;

    public static final int REQUEST_BODY_ERROR = 1001;
    public static final int NAVIGATION_TYPE = 1002;
    public static final int SETTINGS_TYPE = 1003;
    public static final int SETTINGS_RECENT = 1004;

    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 3;


    //Http Error
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;
    /**
     * 证书出错
     */
    public static final int SSL_ERROR = 1005;

    /**
     * 服务器响应超时
     */
    public static final int SOCKET_TIMEOUT_ERROR = 1006;

    /**
     * 服务器请求超时
     */
    public static final int CONNECT_ERROR = 1007;
}
