package com.laiyifen.capital.inhouse.bean;

import java.io.Serializable;

public class JPushMessageBean implements Serializable {
    private String url;
    private String title;
    private String badge;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }
}
