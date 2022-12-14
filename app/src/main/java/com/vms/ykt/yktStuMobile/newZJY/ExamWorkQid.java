package com.vms.ykt.yktStuMobile.newZJY;

import java.util.Map;

public class ExamWorkQid {
    Map<String,String> questionIdAndContentIds;
    int questionCount;
    int pageCount;

    public Map<String, String> getQuestionIdAndContentIds() {
        return questionIdAndContentIds;
    }

    public void setQuestionIdAndContentIds(Map<String, String> questionIdAndContentIds) {
        this.questionIdAndContentIds = questionIdAndContentIds;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
