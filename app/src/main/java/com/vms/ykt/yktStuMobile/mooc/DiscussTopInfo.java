package com.vms.ykt.yktStuMobile.mooc;
/**list":[{"userId":"utbzaikuaovc89bj200x1a","dateCreated":"2022年04月29日 23:09","content":"nn",
 * "topicId":"c759ayaup4fcnz1ljxe7hg","displayName":"吴亦","title":"bb","isMine":true,"replyCount":"0",
 * "isTransferEssence":0,"isNowTopic":true,"thumbnail":"https://mooc.icve.com.cn/common/images/default_avatar_small.jpg",
 * "urlList":[]}]**/
public class DiscussTopInfo {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String ParmsUserId) {
        userId = ParmsUserId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String ParmsTopicId) {
        topicId = ParmsTopicId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String ParmsDisplayName) {
        displayName = ParmsDisplayName;
    }

    public String getIsNowTopic() {
        return isNowTopic;
    }

    public void setIsNowTopic(String ParmsIsNowTopic) {
        isNowTopic = ParmsIsNowTopic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String ParmsTitle) {
        title = ParmsTitle;
    }

    public String getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(String ParmsReplyCount) {
        replyCount = ParmsReplyCount;
    }

    private String topicId;
    private String displayName;
    private String isNowTopic;
    private String title;
    private String replyCount;


}
