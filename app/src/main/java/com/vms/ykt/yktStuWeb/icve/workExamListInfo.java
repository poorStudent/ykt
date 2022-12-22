package com.vms.ykt.yktStuWeb.icve;
/*CourseId: "drkvazokz4hb0ly7zrff7q"
EndDate: "2115/1/9 17:10:00"
Id: "ptkcazokqjzbd2xvpyxwmg"
StartDate: "2015/5/9 17:10:00"
Status: 1
Title: "钳工专业认知习题"**/

import java.io.Serializable;

/*Content: "<p>钳加工试题五&nbsp;</p>"
DateCreated: "2015/6/29 20:55:27"
EndDate: "2024/6/28 20:28:00"
Id: "rnfyacakwihhkfvvsl9-uw"
LimitTime: "100"
PublishDate: "2024/6/29 20:28:00"
StartDate: "2015/6/29 20:28:00"
Status: "1"
Title: "钳加工试题五"
TotalScore: "100.0"
UserId: "h63yafaksrhcjc4j3nrdua"*/
public class workExamListInfo  {

    private String Title;
    private String EndDate;
    private String Id;
    private String StartDate;
    private String Status;
    private String LimitTime;
    private String TotalScore;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getLimitTime() {
        return LimitTime;
    }

    public void setLimitTime(String limitTime) {
        LimitTime = limitTime;
    }

    public String getTotalScore() {
        return TotalScore;
    }

    public void setTotalScore(String totalScore) {
        TotalScore = totalScore;
    }
}
