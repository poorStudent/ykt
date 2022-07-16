package com.vms.ykt.yktStuMobile.mooc;

import java.util.Random;

/**{"code":1,"list":[
 * {"id":"bbszhtlq-gisjat6ujjljmrruz461sg","isEssenceTopic":false,"title":"综合讨论区","content":"综合讨论区",
 * "topicCount":"64","stuCount":"45","dateCreated":"2022年02月16日 16:06","isClose":0},
 * {"id":"bbsktjlq-gisjat6ujjljmrruz461sg","isEssenceTopic":false,"title":"课堂交流区","content":"课堂交流区",
 * "topicCount":"12","stuCount":"158","dateCreated":"2022年02月16日 16:06","isClose":0},
 * {"id":"bbslsdyq-gisjat6ujjljmrruz461sg","isEssenceTopic":false,"title":"老师答疑区","content":"老师答疑区",
 * "topicCount":"12","stuCount":"7","dateCreated":"2022年02月16日 16:06","isClose":0},
 * {"id":"bbsjhq-gisjat6ujjljmrruz461sg","isEssenceTopic":true,"title":"精华区","content":"精华区",
 * "topicCount":"0","stuCount":"0","dateCreated":"2022年02月16日 16:06","isClose":0}],
 * "pagination":{"totalCount":4,"pageSize":20,"pageIndex":1}}**/
public class DiscussInfo {
    private String id;
    private String topicCount;
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public String getTopicCount() {
        return topicCount;
    }

    public void setTopicCount(String ParmsTopicCount) {
        topicCount = ParmsTopicCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String ParmsTitle) {
        title = ParmsTitle;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String ParmsDateCreated) {
        dateCreated = ParmsDateCreated;
    }

    public String getIsClose() {
        return isClose;
    }

    public void setIsClose(String ParmsIsClose) {
        isClose = ParmsIsClose;
    }

    public String getIsEssenceTopic() {
        return isEssenceTopic;
    }

    public void setIsEssenceTopic(String ParmsIsEssenceTopic) {
        isEssenceTopic = ParmsIsEssenceTopic;
    }

    private String dateCreated;
    private String isClose;
    private String isEssenceTopic;

/*Content: "综合讨论区"
Id: "bbszhtlq-j7fbabaurl5ocrj9ibrfxw"
IsAutomaticClose: 1
IsShow: 1
Title: "综合讨论区"
categoryType: 0
isCourseEndTime: false
isEssenceArea: false
stuCount: 277
topicCount: 1079*/

}
