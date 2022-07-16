package com.vms.ykt.yktStuWeb.icve;

public class SectionInfo {
    private String Id;//3ai-arumhafczglyjx505g
    private String CourseId;//2ai-arumrlldlezi5xcg8g
    private String SortOrder;//=1
    private String chapters;//=1
    private String Title;//第1章 单片机基础知识

    public String getChapters() {
        return chapters;
    }

    public void setChapters(String chapters) {
        this.chapters = chapters;
    }

    public String getId() {
        return Id;
    }

    public void setId(String ParmsId) {
        Id = ParmsId;
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String ParmsCourseId) {
        CourseId = ParmsCourseId;
    }

    public String getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(String ParmsSortOrder) {
        SortOrder = ParmsSortOrder;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String ParmsTitle) {
        Title = ParmsTitle;
    }


}
