package com.vms.ykt.yktStuWeb.zjy;

import java.util.ArrayList;

/**
 * {"code":1,"courseOpenId":"uoqaoksbixovw0s4anrhq","openClassId":"5zogauaufrvd1yb5ansr4w","cellList":[
 * {"Id":"712taokspotigm0kwovvoq","cellType":1,"isGJS":1,"isOriginal":0,"fromType":2,
 * "parentId":"712taoksnyznqtonyec9g","courseOpenId":"uoqaoksbixovw0s4anrhq","topicId":"712taoksnyznqtonyec9g",
 * "categoryName":"视频","categoryNameDb":"视频","cellName":"1.1 导学：东方红，中国出了个毛泽东——总体评价",
 * "resourceUrl":"doc/g@5176C2C88CB063D46CA4BF049169ABF1.mp4","externalLinkUrl":"",
 * "cellContent":"","sortOrder":1,"isAllowDownLoad":false,"childNodeList":[],"upCellId":"0","stuCellCount":1,"
 * stuCellPercent":100},
 * {"Id":"712taokszqzillaawdstvw","cellType":1,"isGJS":1,"isOriginal":0,"fromType":2,"parentId":"712taoksnyznqtonyec9g","courseOpenId":"uoqaoksbixovw0s4anrhq","topicId":"712taoksnyznqtonyec9g","categoryName":"文档","categoryNameDb":"pdf文档","cellName":"1.1 导学：东方红，中国出了个毛泽东——总体评价","resourceUrl":"doc/g@1EE6B1BF4DAF18BE30E5C4B0F93C28E2.pdf","externalLinkUrl":"","cellContent":"","sortOrder":2,"isAllowDownLoad":false,"childNodeList":[],"upCellId":"712taokspotigm0kwovvoq","stuCellCount":1,"stuCellPercent":100},{"Id":"712taoksp4boupiis8gfqq","cellType":1,"isGJS":1,"isOriginal":0,"fromType":2,"parentId":"712taoksnyznqtonyec9g","courseOpenId":"uoqaoksbixovw0s4anrhq","topicId":"712taoksnyznqtonyec9g","categoryName":"文档","categoryNameDb":"pdf文档","cellName":"1.2 求索问道：雄关漫道真如铁","resourceUrl":"doc/g@EDB6C0125D8E08BFCAB0745BA5E4622E.pdf","externalLinkUrl":"","cellContent":"","sortOrder":3,"isAllowDownLoad":false,"childNodeList":[],"upCellId":"712taokszqzillaawdstvw","stuCellCount":1,"stuCellPercent":100},{"Id":"712taoksd6df84f1sgproa","cellType":1,"isGJS":1,"isOriginal":0,"fromType":2,"parentId":"712taoksnyznqtonyec9g","courseOpenId":"uoqaoksbixovw0s4anrhq","topicId":"712taoksnyznqtonyec9g","categoryName":"视频","categoryNameDb":"视频","cellName":"1.2求索问道：雄关漫道真如铁","resourceUrl":"doc/g@DD126EDD2CCB2A4D53791B75A19D08AD.mp4","externalLinkUrl":"","cellContent":"","sortOrder":4,"isAllowDownLoad":false,"childNodeList":[],"upCellId":"712taoksp4boupiis8gfqq","stuCellCount":1,"stuCellPercent":100},{"Id":"712taoksykdilnm0ians7w","cellType":1,"isGJS":1,"isOriginal":0,"fromType":2,"parentId":"712taoksnyznqtonyec9g","courseOpenId":"uoqaoksbixovw0s4anrhq","topicId":"712taoksnyznqtonyec9g","categoryName":"文档","categoryNameDb":"pdf文档","cellName":"1.3 思想旗帜：高举旗帜不动摇","resourceUrl":"doc/g@8D23833D6FC6F7E33D7097417CD174D6.pdf","externalLinkUrl":"","cellContent":"","sortOrder":5,"isAllowDownLoad":false,"childNodeList":[],"upCellId":"712taoksd6df84f1sgproa","stuCellCount":1,"stuCellPercent":100},{"Id":"712taoks4y5me3yt4lshkg","cellType":1,"isGJS":1,"isOriginal":0,"fromType":2,"parentId":"712taoksnyznqtonyec9g","courseOpenId":"uoqaoksbixovw0s4anrhq","topicId":"712taoksnyznqtonyec9g","categoryName":"视频","categoryNameDb":"视频","cellName":"1.3思想旗帜：高举旗帜不动摇","resourceUrl":"doc/g@4092363F3DD0FA934FE7F5E58C93E24D.mp4","externalLinkUrl":"","cellContent":"","sortOrder":6,"isAllowDownLoad":false,"childNodeList":[],"upCellId":"712taoksykdilnm0ians7w","stuCellCount":1,"stuCellPercent":100},{"Id":"712taoksq5ncgvukble0bg","cellType":1,"isGJS":1,"isOriginal":0,"fromType":2,"parentId":"712taoksnyznqtonyec9g","courseOpenId":"uoqaoksbixovw0s4anrhq","topicId":"712taoksnyznqtonyec9g","categoryName":"文档","categoryNameDb":"pdf文档","cellName":"1.4 精神财富：中国人的精神符号","resourceUrl":"doc/g@6FB5960D00104B31250C6741D847418F.pdf","externalLinkUrl":"","cellContent":"","sortOrder":7,"isAllowDownLoad":false,"childNodeList":[],"upCellId":"712taoks4y5me3yt4lshkg","stuCellCount":1,"stuCellPercent":100},{"Id":"712taokseodc9bgywzpr4a","cellType":1,"isGJS":1,"isOriginal":0,"fromType":2,"parentId":"712taoksnyznqtonyec9g","courseOpenId":"uoqaoksbixovw0s4anrhq","topicId":"712taoksnyznqtonyec9g","categoryName":"视频","categoryNameDb":"视频","cellName":"1.4精神财富：中国人的精神符号","resourceUrl":"doc/g@883FEEFAB1AF371359984FCAC4056230.mp4","externalLinkUrl":"","cellContent":"","sortOrder":8,"isAllowDownLoad":false,"childNodeList":[],"upCellId":"712taoksq5ncgvukble0bg","stuCellCount":1,"stuCellPercent":100}]}
**/
public class zjyCellInfo {
    private String Id;

    public String getId() {
        return Id;
    }

    public void setId(String ParmsId) {
        Id = ParmsId;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String ParmsCellType) {
        cellType = ParmsCellType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String ParmsParentId) {
        parentId = ParmsParentId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String ParmsTopicId) {
        topicId = ParmsTopicId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String ParmsCategoryName) {
        categoryName = ParmsCategoryName;
    }

    public String getCourseOpenId() {
        return courseOpenId;
    }

    public void setCourseOpenId(String ParmsCourseOpenId) {
        courseOpenId = ParmsCourseOpenId;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String ParmsCellName) {
        cellName = ParmsCellName;
    }

    public String getUpCellId() {
        return upCellId;
    }

    public void setUpCellId(String ParmsUpCellId) {
        upCellId = ParmsUpCellId;
    }

    public String getStuCellFourPercent() {
        return stuCellFourPercent;
    }

    public void setStuCellFourPercent(String ParmsStuCellFourPercent) {
        stuCellFourPercent = ParmsStuCellFourPercent;
    }

    public String getStuCellPercent() {
        return stuCellPercent;
    }

    public void setStuCellPercent(String ParmsStuCellPercent) {
        stuCellPercent = ParmsStuCellPercent;
    }

    public String getCellContent() {
        return cellContent;
    }

    public void setCellContent(String ParmsCellContent) {
        cellContent = ParmsCellContent;
    }



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

    public ArrayList<zjyChildCellInfo> getZjyChildCellInfo() {
        return MZjyChildCellInfo;
    }

    public void setZjyChildCellInfo(ArrayList<zjyChildCellInfo> ParmsZjyChildCellInfo) {
        MZjyChildCellInfo = ParmsZjyChildCellInfo;
    }

    private ArrayList<zjyChildCellInfo> MZjyChildCellInfo;
}
