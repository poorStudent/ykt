package com.vms.ykt.yktStuWeb.zjy;
/**
 * {"code":1,"topicList":[{"id":"712taoksnyznqtonyec9g","name":"微课","sortOrder":1,"upTopicId":"0"},
 * {"id":"712taoksr6bmqbiyikyvg","name":"理论库","sortOrder":2,"upTopicId":"712taoksnyznqtonyec9g"},
 * {"id":"712taoksoarkliihfrh3kg","name":"案例库","sortOrder":3,"upTopicId":"712taoksr6bmqbiyikyvg"}]}
 * **/
public class TopicInfo {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String ParmsName) {
        name = ParmsName;
    }

    public String getUpTopicId() {
        return upTopicId;
    }

    public void setUpTopicId(String ParmsUpTopicId) {
        upTopicId = ParmsUpTopicId;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String ParmsSortOrder) {
        sortOrder = ParmsSortOrder;
    }

    private String name;
    private String upTopicId;
    private String sortOrder;
}
