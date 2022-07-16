package com.vms.ykt.yktStuMobile.zjy;

import java.util.ArrayList;

/*
     ******************************
web
  {"code":1,"topicList":[{"id":"712taoksnyznqtonyec9g","name":"微课","sortOrder":1,"upTopicId":"0"},
 * {"id":"712taoksr6bmqbiyikyvg","name":"理论库","sortOrder":2,"upTopicId":"712taoksnyznqtonyec9g"},

  android

 {"topicId":"712taoksnyznqtonyec9g",
 "topicName":"微课","sortOrder":0,
 "upTopicId":"0"}
*/
public class zjyTopicList
{
    private String topicId;
    private String id;
    private String topicName;

    private String name;
    private String sortOrder;
    private String upTopicId;
    public String getId() {
        if (id==null)return topicId;
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


    private ArrayList<zjyCellList> mZjyCellLists;

    public void setTopicId(String topicId)
    {
        this.topicId = topicId;
    }

    public String getTopicId()
    {
        if (id==null)return topicId;
        return id;
    }

    public void setTopicName(String topicName)
    {
        this.topicName = topicName;
    }

    public String getTopicName()
    {
        return topicName;
    }

    public void setSortOrder(String sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public String getSortOrder()
    {
        return sortOrder;
    }

    public void setUpTopicId(String upTopicId)
    {
        this.upTopicId = upTopicId;
    }

    public String getUpTopicId()
    {
        return upTopicId;
    }}
