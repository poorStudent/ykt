package com.vms.ykt.yktStuWeb.Cqooc;
//data: [{id: "59402", title: "作业1", unitId: "325062",…}, {id: "59403", title: "作业2", unitId: "325062",…},…]
//0: {id: "59402", title: "作业1", unitId: "325062",…}
//chapter: {id: "325062", title: "第一章 好工作为啥不是你的菜？", status: "1", pubClass: ""}
//id: "59402"
//pubClass: "26787"
//status: "4"
//title: "作业1"
//unitId: "325062"

//data: [{id: 7694, title: "2021-2022财经素养公选课考试2"}, {id: 7597, title: "2021-2022-2学期数字经济+财经素养公选课期末考试"},…]
//0: {id: 7694, title: "2021-2022财经素养公选课考试2"}
//id: 7694
//title: "2021-2022财经素养公选课考试2"
public class examTask {
    private int type;
    private String chapter;
    private String id;
    private String status;
    private String pubClass;
    private String title;
    private String unitId;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPubClass() {
        return pubClass;
    }

    public void setPubClass(String pubClass) {
        this.pubClass = pubClass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
}
