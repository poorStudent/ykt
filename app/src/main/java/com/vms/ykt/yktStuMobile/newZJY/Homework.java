package com.vms.ykt.yktStuMobile.newZJY;
/*
* {"success":true,"data":{"pageSize":10,"curPage":1,"totalCount":4,"totalPage":1,
* "startIndex":0,"items":
* [{"isComment":"0","endDate":1671206399000,"chapterName":"绪论",
* "sectionId":"402883e482f4d86e0182f6a603f3056a","title":"思考：为什么我们要继承和弘扬中国优秀传统文化？","commentEndDate":null,"totalScore":100.0,"commentCount":null,"sectionName":"绪论","itemId":"402883ab82f73c950182f84381c0118d","hwStatus":"2","commentStartDate":null,"scorePaper":100.0,"chapterId":"402883e482f4d86e0182f6a603f30569","location":"1-1","hwId":"402883ab82f73c950182f84381c5118e","editorType":null,"startDate":1662048000000},{"isComment":"0","endDate":1671206399000,"chapterName":"中国传统宗教","sectionId":"402883e4830e1056018317652088778e","title":"思考：图腾崇拜起源、内容和影响是什么？","commentEndDate":null,"totalScore":100.0,"commentCount":null,"sectionName":"中国传统宗教","itemId":"402883e4830e1056018317660870779e","hwStatus":"2","commentStartDate":null,"scorePaper":100.0,"chapterId":"402883e4830e105601831764a7847782","location":"2-1","hwId":"402883e4830e1056018317660876779f","editorType":null,"startDate":1662652800000},{"isComment":"0","endDate":1671206399000,"chapterName":"传统建筑","sectionId":"402883e484767a40018476e02cbf09e3","title":"课堂展示第一次主题（戏剧）","commentEndDate":null,"totalScore":100.0,"commentCount":null,"sectionName":"传统建筑","itemId":"4028813884a0066e0184acf5818c53a4","hwStatus":"2","commentStartDate":null,"scorePaper":100.0,"chapterId":"402883e484767a40018476dfeed009de","location":"10-1","hwId":"4028813884a0066e0184acf581ac53a5","editorType":null,"startDate":1669305600000},{"isComment":"0","endDate":1671206399000,"chapterName":"传统建筑","sectionId":"402883e484767a40018476e02cbf09e3","title":"课堂展示第二次主题（建筑）","commentEndDate":null,"totalScore":100.0,"commentCount":null,"sectionName":"传统建筑","itemId":"4028813884a0066e0184acf693f453fb","hwStatus":"2","commentStartDate":null,"scorePaper":100.0,"chapterId":"402883e484767a40018476dfeed009de","location":"10-1","hwId":"4028813884a0066e0184acf6941553fc","editorType":null,"startDate":1669305600000}],"otherResult":{},"searchItem":null,"orderItem":null},"code":"1","message":null}
*
* {"success":true,"data":{"pageSize":100,"curPage":1,"totalCount":20,"totalPage":1,"startIndex":0,
* "items":[{"note":"<p><span id=\"a26602fa-9a29-4a83-b6e9-8c8c58622caf\" class=\"attach-file resource-mod whaty-tiny-block whaty-filetype-ppt\" contenteditable=\"false\" data-attachfileid=\"1603407313154670594\" data-previewurl=\"https://spoc-yunpan-sdk.icve.com.cn/api/doc_view?userId=84ba6d40-2b58-11ed-95cb-f194ef08247f&amp;metaId=50cb09a9-3c34-48da-83fe-c7a54c93b017\" data-filesize=\"6240838\" data-metaid=\"50cb09a9-3c34-48da-83fe-c7a54c93b017\"> <span class=\"file-mod\"> <span class=\"typeico\"><img src=\"static/tinymce/images/types/type-ppt.png\" alt=\"attachFile-type-type-ppt.png\" /></span> <span class=\"name\">生活中的数字5.pptx</span> <span class=\"size\">6MB</span> </span> </span></p>\n<p>&nbsp;</p>","loginId":"venomms","stuId":"2w7jafiswazbrev468vb5q","homeworkType":"1","photo":"null","recommend":null,"commentCount":0,"trueName":"魏海旭","isSelfHomework":true,"homeworkId":"402883e582e2a7530182f3192b5c2ff2","commitTime":1671117130000,"hsId":"06f0268b2490b2348ec2d5a028518d20","commented":0}*/
public class Homework  {
    private String sectionId;
    private String title;
    private String itemId;
    private String totalScore;
    private String hwStatus;
    private String chapterId;
    private String hwId;
    private String scorePaper;
    private String endDate;
    private String isComment;
    private String note;
    private String loginId;
    private String trueName;
    private String hsId;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getHsId() {
        return hsId;
    }

    public void setHsId(String hsId) {
        this.hsId = hsId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(String totalScore) {
        this.totalScore = totalScore;
    }

    public String getHwStatus() {
        return hwStatus;
    }

    public void setHwStatus(String hwStatus) {
        this.hwStatus = hwStatus;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getHwId() {
        return hwId;
    }

    public void setHwId(String hwId) {
        this.hwId = hwId;
    }

    public String getScorePaper() {
        return scorePaper;
    }

    public void setScorePaper(String scorePaper) {
        this.scorePaper = scorePaper;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIsComment() {
        return isComment;
    }

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }
}
