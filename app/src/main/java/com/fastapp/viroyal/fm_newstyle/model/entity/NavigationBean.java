package com.fastapp.viroyal.fm_newstyle.model.entity;


public class NavigationBean {
    private int id;
    private String name;
    private String title;
    private boolean isChecked;
    private int orderNum;
    private String coverPath;
    private boolean selectedSwitch;
    private boolean isFinished;
    private String contentType;
    private int categoryType;
    private boolean filterSupported;
    private boolean isPaid;
    private int moduleType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIsChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public boolean isSelectedSwitch() {
        return selectedSwitch;
    }

    public void setSelectedSwitch(boolean selectedSwitch) {
        this.selectedSwitch = selectedSwitch;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(int categoryType) {
        this.categoryType = categoryType;
    }

    public boolean isFilterSupported() {
        return filterSupported;
    }

    public void setFilterSupported(boolean filterSupported) {
        this.filterSupported = filterSupported;
    }

    public boolean isIsPaid() {
        return isPaid;
    }

    public void setIsPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }

    public int getModuleType() {
        return moduleType;
    }

    public void setModuleType(int moduleType) {
        this.moduleType = moduleType;
    }
}
