package com.vms.ykt.yktStuMobile.zjy;

import java.util.*;

/*
     ******************************

     Create : XuanRan
     Date : 
     Used For : 

     ******************************
 {"topicId":"712taoksnyznqtonyec9g",
 "cellId":"712taokspotigm0kwovvoq",
 "upCellId":"0","cellType":1,
 "sortOrder":0,
 "cellName":"1.1 导学：东方红，中国出了个毛泽东——总体评价",
 "cellContent":"","categoryName":"视频",
 "externalLinkUrl":"",
 "extension":"其他",
 "isAllowDownLoad":false,
 "cellChildNodeList":[],
 "isStuSeeCell":0,"studyCellPercent":0.0}
*/
public class zjyCellList {
    private String topicId;
    private String cellId;
    private String isStuSeeCell;
    private String studyCellPercent;
    private String id;
    private String cellType;
    private String cellName;
    private String categoryName;
    private String upCellId;

    public String getModID() {
        return modID;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    private String modID;
    private ArrayList<zjyCellChildList> cellChildNodeList;

    public String getId() {
        if (id == null) return cellId;
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIsStuSeeCell(String isStuSeeCell) {
        this.isStuSeeCell = isStuSeeCell;
    }

    public void setStudyCellPercent(String studyCellPercent) {
        this.studyCellPercent = studyCellPercent;
    }


    public String getIsStuSeeCell() {
        return isStuSeeCell;
    }

    public String getStudyCellPercent() {
        return studyCellPercent;
    }


    public String getUpCellId() {
        return upCellId;
    }

    public void setUpCellId(String upCellId) {
        this.upCellId = upCellId;
    }


    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getCellId() {
        if (id == null) return cellId;
        return id;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCellChildNodeList(ArrayList<zjyCellChildList> cellChildNodeList) {
        this.cellChildNodeList = cellChildNodeList;
    }

    public ArrayList<zjyCellChildList> getCellChildNodeList() {
        return cellChildNodeList;
    }
}
