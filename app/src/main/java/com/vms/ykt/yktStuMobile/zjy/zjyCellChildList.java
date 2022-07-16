package com.vms.ykt.yktStuMobile.zjy;

import java.util.ArrayList;

/*
     ******************************

     Create : XuanRan
     Date : 
     Used For : 

     ******************************
 {"upCellId":"0",
 "extension":"其他",
 "studyCellPercent":0.0,
 "cellType":1,
 "cellName":"第一章讲稿：“毛泽东思想及其历史地位”",
 "sortOrder":0,"isAllowDownLoad":false,
 "isStuSeeCell":0,
 "cellId":"712taoksxjzosea2ymdg",
 "externalLinkUrl":"",
 "categoryName":"文档","cellContent":""}
 
*/
public class zjyCellChildList extends zjyCellList
{
    private String topicId;
    private String upCellId;
    private String cellId;
    private String id;
    private String cellType;
    private String cellName;
    private String categoryName;
    private String isStuSeeCell;
    private String studyCellPercent;
    private ArrayList<zjyCellChildList> cellChildNodeList;

    public String getModID() {
        return modID;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }

    private String modID;

    @Override
    public String getId() {
        if (id==null)return cellId;
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public ArrayList<zjyCellChildList> getCellChildNodeList() {
        return cellChildNodeList;
    }

    @Override
    public void setCellChildNodeList(ArrayList<zjyCellChildList> cellChildNodeList) {
        this.cellChildNodeList = cellChildNodeList;
    }

    @Override
    public void setIsStuSeeCell(String isStuSeeCell) {
        this.isStuSeeCell = isStuSeeCell;
    }

    @Override
    public void setStudyCellPercent(String studyCellPercent) {
        this.studyCellPercent = studyCellPercent;
    }



    public String getIsStuSeeCell() {
        return isStuSeeCell;
    }

    public String getStudyCellPercent() {
        return studyCellPercent;
    }

    public void setUpCellId(String upCellId)
    {
        this.upCellId = upCellId;
    }

    public String getUpCellId()
    {
        return upCellId;
    }

    public void setCellId(String cellId)
    {
        this.cellId = cellId;
    }

    public String getCellId()
    {
        if (id==null)return cellId;
        return cellId;
    }

    public void setCellType(String cellType)
    {
        this.cellType = cellType;
    }

    public String getCellType()
    {
        return cellType;
    }

    public void setCellName(String cellName)
    {
        this.cellName = cellName;
    }

    public String getCellName()
    {
        return cellName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setTopicId(String topicId)
{
    this.topicId = topicId;
}

    public String getTopicId()
    {
        return topicId;
    }}
