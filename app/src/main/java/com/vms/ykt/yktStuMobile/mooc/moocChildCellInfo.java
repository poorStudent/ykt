package com.vms.ykt.yktStuMobile.mooc;

public class moocChildCellInfo extends  moocCellInfo {

    private String id;

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public void setResId(String ParmsResId) {
        resId = ParmsResId;
    }

    public void setCellType(String ParmsCellType) {
        cellType = ParmsCellType;
    }

    public void setCategoryName(String ParmsCategoryName) {
        categoryName = ParmsCategoryName;
    }

    public void setCellName(String ParmsCellName) {
        cellName = ParmsCellName;
    }

    public void setIsStudyFinish(String ParmsIsStudyFinish) {
        isStudyFinish = ParmsIsStudyFinish;
    }

    public void setChildNodeList(String ParmsChildNodeList) {
        childNodeList = ParmsChildNodeList;
    }

    private String resId;
    private String cellType;
    private String categoryName;
    private String cellName;
    private String isStudyFinish;
    private String childNodeList;

    public String getId() {
        return id;
    }

    public String getResId() {
        return resId;
    }

    public String getCellType() {
        return cellType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getCellName() {
        return cellName;
    }

    public String getIsStudyFinish() {
        return isStudyFinish;
    }

}
