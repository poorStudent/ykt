package com.vms.ykt.yktStuWeb.icve;

import java.io.Serializable;

/**{"code":1,"list":[
 * {"id":"2ai-arumrlldlezi5xcg8g","schedule":"0.78","datecreated":"2021/4/28 17:25:55","title":"单片机原理与应用",
 * "cover":"https://file.icve.com.cn/doc_public2/555/222/8AC37AC3258FF25FF220380C8CE5A1F0.jpg?x-oss-process=image/resize,m_fixed,w_198,h_112,limit_0",
 * "studyhours":"84.0","state":"3"},
 * {"id":"brjvahcknj9lmq9m9cargq","schedule":"1.59","datecreated":"2021/4/28 17:24:55","title":"单片机技术基础",
 * "cover":"https://file.icve.com.cn/doc_public/243/871/3CCD9D5569792102769205F0850CF61E.jpg?x-oss-process=image/resize,m_fixed,w_198,h_112,limit_0","studyhours":"120.0","state":"3"},{"id":"59ynaf6pjo1ajtx227s31q","schedule":"0.00","datecreated":"2021/4/28 17:18:20","title":"JavaScript及框架应用","cover":"https://file.icve.com.cn/file_doc_public/395/384/62E600EA00DE0884DCC4B22FC34D3F7C.jpg?x-oss-process=image/resize,m_fixed,w_198,h_112,limit_0","studyhours":"60.0","state":"3"}],"userid":"2w7jafiswazbrev468vb5q","pagination":{"totalCount":3,"pageSize":12,"pageIndex":1}}**/

public class icveCourseInfo  {
    private String id;
    private String schedule;
    private String datecreated;
    private String title;
    private String cover;
    private String studyhours;
    private String state;
    private String studyscore;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getStudyscore() {
        return studyscore;
    }

    public void setStudyscore(String studyscore) {
        this.studyscore = studyscore;
    }

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String ParmsSchedule) {
        schedule = ParmsSchedule;
    }

    public String getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(String ParmsDatecreated) {
        datecreated = ParmsDatecreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String ParmsTitle) {
        title = ParmsTitle;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String ParmsCover) {
        cover = ParmsCover;
    }

    public String getStudyhours() {
        return studyhours;
    }

    public void setStudyhours(String ParmsStudyhours) {
        studyhours = ParmsStudyhours;
    }

    public String getState() {
        return state;
    }

    public void setState(String ParmsState) {
        state = ParmsState;
    }
}
