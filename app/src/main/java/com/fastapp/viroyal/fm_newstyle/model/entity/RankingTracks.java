package com.fastapp.viroyal.fm_newstyle.model.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/9/10.
 */

public class RankingTracks {
    private String calcPeriod;
    private int categoryId;
    private String contentType;
    private String coverPath;
    private ImagesBean images;
    private boolean isAllPaid;
    private String key;
    private int maxPageId;
    private String msg;
    private int pageId;
    private int pageSize;
    private int period;
    private int rankingListId;
    private String rankingRule;
    private int ret;
    private ShareContentBean shareContent;
    private String subtitle;
    private String title;
    private int top;
    private int totalCount;
    private List<CategoriesBean> categories;
    private List<RankingTracksBean> list;

    public String getCalcPeriod() {
        return calcPeriod;
    }

    public void setCalcPeriod(String calcPeriod) {
        this.calcPeriod = calcPeriod;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public boolean isIsAllPaid() {
        return isAllPaid;
    }

    public void setIsAllPaid(boolean isAllPaid) {
        this.isAllPaid = isAllPaid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getMaxPageId() {
        return maxPageId;
    }

    public void setMaxPageId(int maxPageId) {
        this.maxPageId = maxPageId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getRankingListId() {
        return rankingListId;
    }

    public void setRankingListId(int rankingListId) {
        this.rankingListId = rankingListId;
    }

    public String getRankingRule() {
        return rankingRule;
    }

    public void setRankingRule(String rankingRule) {
        this.rankingRule = rankingRule;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public ShareContentBean getShareContent() {
        return shareContent;
    }

    public void setShareContent(ShareContentBean shareContent) {
        this.shareContent = shareContent;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<CategoriesBean> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoriesBean> categories) {
        this.categories = categories;
    }

    public List<RankingTracksBean> getList() {
        return list;
    }

    public void setList(List<RankingTracksBean> list) {
        this.list = list;
    }



    public static class ImagesBean {

        private String title;
        private List<?> list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
            this.list = list;
        }
    }

    public static class ShareContentBean {

        private String content;
        private int lengthLimit;
        private String picUrl;
        private String rowKey;
        private String shareType;
        private String subtitle;
        private String title;
        private String url;
        private String weixinPic;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getLengthLimit() {
            return lengthLimit;
        }

        public void setLengthLimit(int lengthLimit) {
            this.lengthLimit = lengthLimit;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getRowKey() {
            return rowKey;
        }

        public void setRowKey(String rowKey) {
            this.rowKey = rowKey;
        }

        public String getShareType() {
            return shareType;
        }

        public void setShareType(String shareType) {
            this.shareType = shareType;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getWeixinPic() {
            return weixinPic;
        }

        public void setWeixinPic(String weixinPic) {
            this.weixinPic = weixinPic;
        }
    }

    public static class CategoriesBean {

        private int id;
        private String key;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
