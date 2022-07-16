package com.vms.ykt.yktStuMobile.mooc;
/**
 {"code":1,"proces":{"moduleList":[
 {"id":"osjat6uta1eqnm3dz0mq","name":"学好英语的五件宝贝——五种基本句型","moduleType":1,"resId":""},
 {"id":"osjat6uobtos1qoejbqq","name":"语法重器——名词","moduleType":1,"resId":""}**/
public class moocModInfo {
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getModuleType() {
        return moduleType;
    }

    public String getResId() {
        return resId;
    }

    private String id;
    private String name;
    private String moduleType;

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public void setName(String ParmsName) {
        name = ParmsName;
    }

    public void setModuleType(String ParmsModuleType) {
        moduleType = ParmsModuleType;
    }

    public void setResId(String ParmsResId) {
        resId = ParmsResId;
    }

    private String resId;
    /*
    code: 1
isViewOver: true
lockStatus: 0
proces: {moduleList: [,…],…}
moduleList: [,…]
0: {id: "ornbabautkljslvrbfp6ia", name: "电路的组成及基本知识", sortOrder: 1, percent: 0, ModuleType: 1, ResId: "",…}
ModuleType: 1
ResId: ""
id: "ornbabautkljslvrbfp6ia"
isUnlock: true
name: "电路的组成及基本知识"
percent: 0
sortOrder: 1
*/
}
