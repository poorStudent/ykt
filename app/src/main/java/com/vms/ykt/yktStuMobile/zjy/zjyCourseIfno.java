package com.vms.ykt.yktStuMobile.zjy;

/*
     ******************************

     Create : XuanRan
     Date : 
     Used For : 

     ******************************
*/

import java.io.Serializable;
import java.util.ArrayList;

/** {"thumbnail":"https://file.icve.com.cn/ssykt/868/675/D91A8D482C821B932C09034CD3E90CF2.png?x-oss-process=image/resize,m_fixed,w_220,h_180,limit_0/format,jpg",
 "process":0,"InviteCode":"589xxa","courseCode":"","courseSystemType":0,
 "courseOpenId":"z8pjaeuulrbdnflicdpg","openClassId":"mty7au2uq6hay0t4yghww",
 "totalScore":-1.0,"mainTeacherName":"张雅琳","courseName":"西门子sinamics g120/s120变频技术与应用",
 "openClassName":"工业机器人20级1班(专本)","Id":"nho8au2uaj1mekvvod4qyq",
 "mainTeacherNum":"2022400002","openClassCode":"",
 "assistTeacherName":"张雅琳","assistTeacherNum":"2022400002"}

 **/
public class zjyCourseIfno
{
    private String thumbnail;
    private String process;
    private String InviteCode;
    private String courseOpenId;
    private String openClassId;
    private String mainTeacherName;
    private String Id;
    private String courseName;
    private String openClassName;
    private ArrayList<zjyModuleListInfo> mZjyModuleListInfos;


    public void setThumbnail(String thumbnail)
    {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail()
    {
        return thumbnail;
    }

    public void setProcess(String process)
    {
        this.process = process;
    }

    public String getProcess()
    {
        return process;
    }

    public void setInviteCode(String inviteCode)
    {
        InviteCode = inviteCode;
    }

    public String getInviteCode()
    {
        return InviteCode;
    }

    public void setCourseOpenId(String courseOpenId)
    {
        this.courseOpenId = courseOpenId;
    }

    public String getCourseOpenId()
    {
        return courseOpenId;
    }

    public void setOpenClassId(String openClassId)
    {
        this.openClassId = openClassId;
    }

    public String getOpenClassId()
    {
        return openClassId;
    }

    public void setMainTeacherName(String mainTeacherName)
    {
        this.mainTeacherName = mainTeacherName;
    }

    public String getMainTeacherName()
    {
        return mainTeacherName;
    }

    public void setId(String id)
    {
        Id = id;
    }

    public String getId()
    {
        return Id;
    }

    public void setCourseName(String courseName)
    {
        this.courseName = courseName;
    }

    public String getCourseName()
    {
        return courseName;
    }

    public void setOpenClassName(String openClassName)
    {
        this.openClassName = openClassName;
    }

    public String getOpenClassName()
    {
        return openClassName;
    }}
