package com.vms.ykt.yktStuWeb.icve;

public class chapterInfo {
    private String Id;//Id:  3ai-arumbq5evlnban5onw
    private String Title;//Title:  第一节 单片机概述
    private String SortOrder;
    private String SectionId;//SectionId:  3ai-arumhafczglyjx505g
    private String ResId;

    public String getId() {
        return Id;
    }

    public void setId(String ParmsId) {
        Id = ParmsId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String ParmsTitle) {
        Title = ParmsTitle;
    }

    public String getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(String ParmsSortOrder) {
        SortOrder = ParmsSortOrder;
    }

    public String getSectionId() {
        return SectionId;
    }

    public void setSectionId(String ParmsSectionId) {
        SectionId = ParmsSectionId;
    }

    public String getResId() {
        return ResId;
    }

    public void setResId(String ParmsResId) {
        ResId = ParmsResId;
    }

    private String cells;
    private String knowleges;

    public String getCells() {
        return cells;
    }

    public void setCells(String cells) {
        this.cells = cells;
    }


    public String getKnowleges() {
        return knowleges;
    }

    public void setKnowleges(String knowleges) {
        this.knowleges = knowleges;
    }
}
