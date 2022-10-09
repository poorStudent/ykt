package com.vms.ykt.yktStuMobile.newZJY;

public class CellInfo {
    private String itemId;
    private String parentid;
    private String name;
    private String eType;
    private String waretype;
    private String resID;
    private String resUrl;
    private String videotime;
    private int over;

    public int getOver() {
        return over;
    }

    public void setOver(int over) {
        this.over = over;
    }

    public String getWaretype() {
        return waretype;
    }

    public void setWaretype(String waretype) {
        this.waretype = waretype;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getVideotime() {
        return videotime;
    }

    public void setVideotime(String videotime) {
        this.videotime = videotime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    public String getResID() {
        return resID;
    }

    public void setResID(String resID) {
        this.resID = resID;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }
}
