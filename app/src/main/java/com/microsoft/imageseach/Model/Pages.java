package com.microsoft.imageseach.Model;

/**
 * Created by Admin on 5/3/2016.
 */
public class Pages {

    private long pageid;
    private int ns;
    private String title;
    private int index;
    private ThumbNail thumbNail;

    public ThumbNail getThumbNail() {
        return thumbNail;
    }

    public void setThumbNail(ThumbNail thumbNail) {
        this.thumbNail = thumbNail;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNs() {
        return ns;
    }

    public void setNs(int ns) {
        this.ns = ns;
    }

    public long getPageid() {
        return pageid;
    }

    public void setPageid(long pageid) {
        this.pageid = pageid;
    }



}
