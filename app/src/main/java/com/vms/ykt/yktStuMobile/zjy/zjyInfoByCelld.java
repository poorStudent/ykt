package com.vms.ykt.yktStuMobile.zjy;

/**
 * {"code":1,"msg":"获得成功！","cellInfo":
 * {"topicId":"712taoksr6bmqbiyikyvg","cellId":"712taoksn7pifns0mwqnua",
 * "cellName":"教案","cellType":4,"cellContent":"",
 * resourceUrl":"","categoryName":"子节点",
 * "externalLinkUrl":"",
 * "extension":"其他","isAllowDownLoad":false,"stuCellViewTime":0.0,
 * "stuCellPicCount":0,"stuStudyNewlyTime":0.0,
 * "stuStudyNewlyPicNum":0,"cellLogId":"","token":"",
 * "isNeedUpdate":0,"audioVideoLong":0.00,"studyCellPercent":0.0}}
 * <p>
 * {"code":1,"msg":"获得成功！",
 * "cellInfo":{"topicId":"712taoksnyznqtonyec9g",
 * "cellId":"712taokspotigm0kwovvoq",
 * "cellName":"1.1 导学：东方红，中国出了个毛泽东——总体评价",
 * "cellType":1,"cellContent":"",
 * "categoryName":"视频",
 * "externalLinkUrl":"",
 * "extension":"video","isAllowDownLoad":false,
 * "stuCellViewTime":24.0,"stuCellPicCount":1,
 * "stuStudyNewlyTime":17.0,"stuStudyNewlyPicNum":0,
 * "cellLogId":"","token":"o1eaxium59jxkpsgqlrq","isNeedUpdate":0,
 * "audioVideoLong":464.00,"studyCellPercent":3.66}}
 * <p>
 * {"code":1,"msg":"获得成功！","cellInfo":{"topicId":"712taoksnyznqtonyec9g",
 * "cellId":"712taokszqzillaawdstvw",
 * "cellName":"1.1 导学：东方红，中国出了个毛泽东——总体评价","cellType":1,
 * "cellContent":"","resourceUrl":"{\"extension\":\"pdf\",\"category\":\"office\",\"urls\"
 * "categoryName":"文档",
 * "externalLinkUrl":"","extension":"office",
 * "isAllowDownLoad":false,"stuCellViewTime":0.0,"stuCellPicCount":1,
 * "stuStudyNewlyTime":0.0,"stuStudyNewlyPicNum":0,"cellLogId":"etfxaxiuhide66sq8x8aaw",
 * "token":"ddfxaxiux4relpkugizigw","isNeedUpdate":0,
 * "audioVideoLong":0.00,"studyCellPercent":0.0}}
 * <p>
 * {"code":1,"msg":"获得成功！","cellInfo":
 * {"topicId":"alwwaoksmo5e6t2phowx6a","cellId":"alwwaoksmlji0b4dunrpxw",
 * "cellName":"第一章  毛泽东思想的形成和历史地位","cellType":1,"cellContent":"",
 * categoryName":"ppt","externalLinkUrl":"",
 * "extension":"office","isAllowDownLoad":false,
 * "stuCellViewTime":0.0,"stuCellPicCount":1,"stuStudyNewlyTime":0.0,
 * "stuStudyNewlyPicNum":0,"cellLogId":"mddaaxiumiva1tub88g5ja",
 * "token":"mddaaxiuo65cbardeb8ww",
 * "isNeedUpdate":0,"audioVideoLong":0.00,"studyCellPercent":0.0}} *
 **/

public class zjyInfoByCelld {
    private String topicId;
    private String cellId;
    private String cellName;
    private String cellType;
    private String token;
    private String stuStudyNewlyPicNum;
    private String stuStudyNewlyTime;
    private String audioVideoLong;
    private String stuCellViewTime;
    private String studyCellPercent;
    private String cellLogId;
    private String stuCellPicCount;
    private String categoryName;

    //private  String resourceUrl;
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getCellId() {
        return cellId;
    }

    public void setCellId(String cellId) {
        this.cellId = cellId;
    }

    public String getCellName() {
        return cellName;
    }

    public void setCellName(String cellName) {
        this.cellName = cellName;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public String getToken() {
        if (guIdToken!=null)return guIdToken;
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStuStudyNewlyPicNum() {
        return stuStudyNewlyPicNum;
    }

    public void setStuStudyNewlyPicNum(String stuStudyNewlyPicNum) {
        this.stuStudyNewlyPicNum = stuStudyNewlyPicNum;
    }

    public String getStuStudyNewlyTime() {
        return stuStudyNewlyTime;
    }

    public void setStuStudyNewlyTime(String stuStudyNewlyTime) {
        this.stuStudyNewlyTime = stuStudyNewlyTime;
    }

    public String getAudioVideoLong() {
        return audioVideoLong;
    }

    public void setAudioVideoLong(String audioVideoLong) {
        this.audioVideoLong = audioVideoLong;
    }

    public String getStuCellViewTime() {
        return stuCellViewTime;
    }

    public void setStuCellViewTime(String stuCellViewTime) {
        this.stuCellViewTime = stuCellViewTime;
    }

    public String getStudyCellPercent() {
        return studyCellPercent;
    }

    public void setStudyCellPercent(String studyCellPercent) {
        this.studyCellPercent = studyCellPercent;
    }

    public String getCellLogId() {
        return cellLogId;
    }

    public void setCellLogId(String cellLogId) {
        this.cellLogId = cellLogId;
    }

    public String getStuCellPicCount() {
        return stuCellPicCount;
    }

    public void setStuCellPicCount(String stuCellPicCount) {
        this.stuCellPicCount = stuCellPicCount;
    }

    /**
     * {"code":1,"dtype":0,"courseOpenId":"uoqaoksbixovw0s4anrhq","courseName":"毛泽东思想和中国特色社会主义理论体系",
     * "openClassId":"5zogauaufrvd1yb5ansr4w","moduleId":"712taoksvkjk8r1eeic22w","topicId":"712taoksbkrfcgjqzkajw",
     * "cellId":"712taoksfq5dulkk00ljw","cellName":"3.2 道路选择：新民主主义社会是一个过渡性的社会",
     * "cellType":1,"categoryName":"pdf文档","cellContent":"","pageCount":2,"audioVideoLong":0.00,
     * "isAllowDownLoad":false,"externalLinkUrl":"","downLoadUrl":"https://file.icve.com.cn/file_doc/695/827/ADDCEEAD68AB13588E0479CF39E0B193.pdf?response-content-disposition=attachment;filename=3.2 道路选择：新民主主义社会是一个过渡性的社会.pdf",
     * "cellLogId":"","userType":1,"resUrl":"{\"extension\":\"pdf\",\"category\":\"office\",\"urls\":{\"status\":\"https://upload.icve.com.cn/doc/g@ADDCEEAD68AB13588E0479CF39E0B193.pdf/status?time=637863187782943421&token=5A6E776E1935C8506BECCEB8B743CB54\",\"preview\":\"https://file.icve.com.cn/file_doc/695/827/ADDCEEAD68AB13588E0479CF39E0B193.pdf\",\"download\":\"https://file.icve.com.cn/file_doc/695/827/ADDCEEAD68AB13588E0479CF39E0B193.pdf?response-content-disposition=attachment;filename=3.2 道路选择：新民主主义社会是一个过渡性的社会.pdf\",\"preview_oss_ori\":\"https://file.icve.com.cn/file_doc/695/827/ADDCEEAD68AB13588E0479CF39E0B193.pdf\",\"oss_ori_internal_url\":\"https://icve.oss-cn-hangzhou-internal.aliyuncs.com/file_doc/695/827/ADDCEEAD68AB13588E0479CF39E0B193.pdf\",\"preview_oss_gen\":\"https://file.icve.com.cn/file_gen_doc/695/827/ADDCEEAD68AB13588E0479CF39E0B193.pdf\",\"oss_gen_internal_url\":\"https://icve.oss-cn-hangzhou-internal.aliyuncs.com/file_gen_doc/695/827/ADDCEEAD68AB13588E0479CF39E0B193.pdf\",\"owa_url\":\"https://ppt2.icve.com.cn/op/view.aspx?src=https://icve.oss-cn-hangzhou-internal.aliyuncs.com/file_doc/695/827/ADDCEEAD68AB13588E0479CF39E0B193.pdf\"},
     * \"args\":{\"page_count\":2},\"h5PreviewUrl\":\"\",\"isH5\":0,\"status\":2}",
     * "isDownLoad":false,"rarList":[],"flag":"s","stuCellViewTime":0.0,"stuCellPicCount":1,"stuStudyNewlyTime":0.00,
     * "stuStudyNewlyPicCount":999,"cellPercent":100.0,"guIdToken":"ucjkaicu9l9kl8hpeddva",
     * "isNeedUpdate":0,"position":0.0,"cellPositions":[],"cellQuestionList":""}
     **/

    private String moduleId;

    private String guIdToken;


    private String pageCount;
    private String cellPercent;

    private String stuStudyNewlyPicCount;

    private String courseName;
    private String courseOpenId;
    private String openClassId;

    public String getCourseOpenId() {
        return courseOpenId;
    }

    public void setCourseOpenId(String ParmsCourseOpenId) {
        courseOpenId = ParmsCourseOpenId;
    }

    public String getOpenClassId() {
        return openClassId;
    }

    public void setOpenClassId(String ParmsOpenClassId) {
        openClassId = ParmsOpenClassId;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String ParmsModuleId) {
        moduleId = ParmsModuleId;
    }

    public String getGuIdToken() {
        if (guIdToken!=null)return guIdToken;
        return token;
    }

    public void setGuIdToken(String ParmsGuIdToken) {
        guIdToken = ParmsGuIdToken;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String ParmsPageCount) {
        pageCount = ParmsPageCount;
    }

    public String getCellPercent() {
        return cellPercent;
    }

    public void setCellPercent(String ParmsCellPercent) {
        cellPercent = ParmsCellPercent;
    }


    public String getStuStudyNewlyPicCount() {
        return stuStudyNewlyPicCount;
    }

    public void setStuStudyNewlyPicCount(String ParmsStuStudyNewlyPicCount) {
        stuStudyNewlyPicCount = ParmsStuStudyNewlyPicCount;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String ParmsCourseName) {
        courseName = ParmsCourseName;
    }


}
