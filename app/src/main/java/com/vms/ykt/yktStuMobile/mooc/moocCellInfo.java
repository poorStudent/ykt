package com.vms.ykt.yktStuMobile.mooc;

import java.util.ArrayList;

/**{"code":1,"courseOpenId":"gisjat6ujjljmrruz461sg","cellList":[
 * {"id":"osjat6uckdcluctifvzjq","resId":"","cellType":1,"categoryName":"视频","cellName":"1.1 五种基本句型概述 new",
 * "childNodeList":[],"isStudyFinish":false},
 * {"id":"osjat6ugb1oobbajfzvxa","resId":"","cellType":1,"categoryName":"文档","cellName":"1.1 五种句型概述 new",
 * "childNodeList":[],"isStudyFinish":false},
 * {"id":"osjat6unppjq0ld3g6hca","resId":"","cellType":1,"categoryName":"ppt","cellName":"1.1 五种句型概述",
 * "childNodeList":[],"isStudyFinish":false},
 * {"id":"osjat6ukjnl34v60lxmq","resId":"t4wjat6ujbllkkl4vabr0q","cellType":6,"categoryName":"作业",
 * "cellName":"五种句型概述 （new）","childNodeList":[],"isStudyFinish":false}]}
 * **/
public class moocCellInfo {
    private String id;
    private String cellType;
    private String categoryName;
    private String cellName;
    private String isStudyFinish;
    private String modId;
    private String resId;

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }



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

    public ArrayList<moocChildCellInfo> getChildNodeList() {
        return childNodeList;
    }



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



    public void setChildNodeList(ArrayList<moocChildCellInfo> ParmsChildNodeList) {
        childNodeList = ParmsChildNodeList;
    }

    private ArrayList<moocChildCellInfo> childNodeList;

    /*Id: "p7nbabauqytkpoxueh1mw"
categoryName: "文档"
cellContent: "doc/g@A7E87E3A89573716376FF343094ED312.doc"
cellName: "1-学习指导"
cellType: 1
childNodeList: []
courseOpenId: "j7fbabaurl5ocrj9ibrfxw"
externalLinkUrl: ""
isAllowDownLoad: false
isGJS: 1
isStudyFinish: false
isUnlock: true
parentId: "p7nbabauaklaqbcumhk4pa"
resId: ""
resourceUrl: "doc/g@A7E87E3A89573716376FF343094ED312.doc"
sortOrder: 4
topicId: "p7nbabauaklaqbcumhk4pa"
upCellId: "0"*/
}
