package com.vms.ykt.yktStuWeb.zjy;

public class zjyChildCellInfo extends zjyCellInfo {
    @Override
    public String getId() {
        return Id;
    }

    @Override
    public void setId(String ParmsId) {
        Id = ParmsId;
    }

    @Override
    public String getCellType() {
        return cellType;
    }

    @Override
    public void setCellType(String ParmsCellType) {
        cellType = ParmsCellType;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(String ParmsParentId) {
        parentId = ParmsParentId;
    }

    @Override
    public String getTopicId() {
        return topicId;
    }

    @Override
    public void setTopicId(String ParmsTopicId) {
        topicId = ParmsTopicId;
    }

    @Override
    public String getCategoryName() {
        return categoryName;
    }

    @Override
    public void setCategoryName(String ParmsCategoryName) {
        categoryName = ParmsCategoryName;
    }

    @Override
    public String getCourseOpenId() {
        return courseOpenId;
    }

    @Override
    public void setCourseOpenId(String ParmsCourseOpenId) {
        courseOpenId = ParmsCourseOpenId;
    }

    @Override
    public String getCellName() {
        return cellName;
    }

    @Override
    public void setCellName(String ParmsCellName) {
        cellName = ParmsCellName;
    }

    @Override
    public String getUpCellId() {
        return upCellId;
    }

    @Override
    public void setUpCellId(String ParmsUpCellId) {
        upCellId = ParmsUpCellId;
    }

    @Override
    public String getStuCellFourPercent() {
        return stuCellFourPercent;
    }

    @Override
    public void setStuCellFourPercent(String ParmsStuCellFourPercent) {
        stuCellFourPercent = ParmsStuCellFourPercent;
    }

    @Override
    public String getStuCellPercent() {
        return stuCellPercent;
    }

    @Override
    public void setStuCellPercent(String ParmsStuCellPercent) {
        stuCellPercent = ParmsStuCellPercent;
    }

    @Override
    public String getCellContent() {
        return cellContent;
    }

    @Override
    public void setCellContent(String ParmsCellContent) {
        cellContent = ParmsCellContent;
    }

    private String Id;
    private String cellType;
    private String parentId;
    private String topicId;
    private String categoryName;
    private String courseOpenId;
    private String cellName;
    private String upCellId;
    private String stuCellFourPercent;
    private String stuCellPercent;
    private String cellContent;

}
