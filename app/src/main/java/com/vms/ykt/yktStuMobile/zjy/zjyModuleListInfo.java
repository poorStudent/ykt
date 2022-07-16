package com.vms.ykt.yktStuMobile.zjy;

import java.util.ArrayList;

/*


   web
id":"712taoksc7ha5seeotsj4w","name":"01毛泽东思想及历史地位","sortOrder":1,"percent":100.0},
* {"id":"712taoksg5phmvpl39ba","name":"02新民主主义革命理论","sortOrder":2,"percent":100.0},

 * android
 {"moduleId":"712taoksc7ha5seeotsj4w",
 "isFirstModule":1,
 "moduleName":"01毛泽东思想及历史地位",
 "sortOrder":0},
 {"moduleId":"712taoksg5phmvpl39ba",
 "isFirstModule":0,
 "moduleName":"02新民主主义革命理论",
 "sortOrder":1}
*/
public class zjyModuleListInfo
{
    private String moduleId;
    private String name;
    private String percent;
    private String isFirstModule;
    private String moduleName;
    private String sortOrder;

    private String id;
    public String getId() {
        if (id==null)return moduleId;
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }


    private ArrayList<zjyTopicList> mZjyTopicLists;

    public void setModuleId(String moduleId)
    {
        this.moduleId = moduleId;
    }

    public String getModuleId()
    {
        if (id==null)return moduleId;
        return id;
    }

    public void setIsFirstModule(String isFirstModule)
    {
        this.isFirstModule = isFirstModule;
    }

    public String getIsFirstModule()
    {
        return isFirstModule;
    }

    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    public String getModuleName()
    {
        return moduleName;
    }

    public void setSortOrder(String sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public String getSortOrder()
    {
        return sortOrder;
    }}
