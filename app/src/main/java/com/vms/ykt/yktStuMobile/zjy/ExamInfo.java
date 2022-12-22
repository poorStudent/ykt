package com.vms.ykt.yktStuMobile.zjy;


import java.io.Serializable;

/**"examList":[
 * {"examId":"qdezaiiuhljdmsuq8fy07w","examType":1,"ztWay":2,"examTermTimeId":"cpmayiu36vie6w6sujka",
 * "title":"uiu","remark":"uiuiu（请注意，教师已开启本次考试题目乱序、选项乱序设置，请根据选项内容作答！！！）",
 * "startDate":"2022-05-01 19:24:00","endDate":"2022-05-03 23:21:00","readStuCount":0,"unReadStuCount":0,
 * "unTakeCount":0,"stuExamState":"未答","examStuId":"","isExamEnd":0,"isAnswerOrPreview":1,"isSetTime":1,
 * "isPreview":1,"fixedPublishTime":"","isForbid":0,"examWays":1,"isAuthenticate":1,"isValidExam":1,"reasonText":"",
 * "isVerified":1},
 * {"examId":"gdjaikuazdhwpkys9oo3w","examType":1,"ztWay":4,"examTermTimeId":"4o8aayku1bbgsjgihnidw","title":"333","remark":"333","startDate":"2022-04-25 00:00:00","endDate":"2022-05-03 23:26:00","readStuCount":0,"unReadStuCount":0,"unTakeCount":0,"stuExamState":"未答","examStuId":"","isExamEnd":0,"isAnswerOrPreview":1,"isSetTime":1,"isPreview":1,"fixedPublishTime":"","isForbid":0,"examWays":1,"isAuthenticate":1,"isValidExam":1,"reasonText":"","isVerified":0},{"examId":"naxaikuu7jglt3bnsno9w","examType":1,"ztWay":2,"examTermTimeId":"skxwazgu6xeuwolcxszsa","title":"综合测试","remark":"","startDate":"2022-05-10 22:23:00","endDate":"2022-09-10 22:23:00","readStuCount":0,"unReadStuCount":0,"unTakeCount":0,"stuExamState":"未答","examStuId":"","isExamEnd":0,"isAnswerOrPreview":2,"isSetTime":1,"isPreview":1,"fixedPublishTime":"","isForbid":0,"examWays":1,"isAuthenticate":0,"isValidExam":1,"reasonText":"","isVerified":0}]}
 {"code":1,"courseOpenId":"b8j0aikutoxccbrq7hcw","openClassId":"uv70aikuirxmsdqcjvv69a","termId":"qvezaocnhapfyoq9i9ztua","list":[
 * {"Id":"qdezaiiuhljdmsuq8fy07w","Title":"uiu","Remark":"uiuiu（请注意，教师已开启本次考试题目乱序、选项乱序设置，请根据选项内容作答！！！）",
 * "examType":1,"examTermTimeId":"cpmayiu36vie6w6sujka","stuStartDate":"/Date(1651433040000)/",
 * "stuEndDate":"/Date(1651620060000)/","isAuthenticate":1,"dataState":3,"stuOnlineExamId":"",
 * "State":0,"examWays":1,"IsDoExam":0,"isValid":1,"reasonText":null,"markTeaId":"","type":1,"getScore":0.0,
 * "isPreview":1,"fixedPublishTime":"/Date(1652888436106)/","IsExtraOnlineExam":0,"isVerified":1,"questionOrderType":1,
 * "optionOrderType":1},**/

 public class ExamInfo  {
  private String examId;
  private String examType;
  private String ztWay;
  private String examTermTimeId;
  private String title;
  private String startDate;

  private String examStuId;

 private String readStuCount;
 private String isExamEnd;
 private String stuExamState;
 private String remark;
 private String endDate;


 private String Id;

 private String stuEndDate;
 private String markTeaId;

 public String getStartDate() {
  return startDate;
 }

 public void setStartDate(String startDate) {
  this.startDate = startDate;
 }

 private String reasonText;


 public String getExamId() {
  if (examId==null||examId.isEmpty()) {
   return Id;
  }
  return examId;
 }

 public void setExamId(String ParmsExamId) {
  examId = ParmsExamId;
 }

 public String getExamType() {
  return examType;
 }

 public void setExamType(String ParmsExamType) {
  examType = ParmsExamType;
 }

 public String getZtWay() {
  return ztWay;
 }

 public void setZtWay(String ParmsZtWay) {
  ztWay = ParmsZtWay;
 }

 public String getExamTermTimeId() {
  return examTermTimeId;
 }

 public void setExamTermTimeId(String ParmsExamTermTimeId) {
  examTermTimeId = ParmsExamTermTimeId;
 }

 public String getTitle() {
  return title;
 }

 public void setTitle(String ParmsTitle) {
  title = ParmsTitle;
 }

 public String getExamStuId() {
  return examStuId;
 }

 public void setExamStuId(String ParmsExamStuId) {
  examStuId = ParmsExamStuId;
 }

 public String getReadStuCount() {
  return readStuCount;
 }

 public void setReadStuCount(String ParmsReadStuCount) {
  readStuCount = ParmsReadStuCount;
 }

 public String getIsExamEnd() {
  return isExamEnd;
 }

 public void setIsExamEnd(String ParmsIsExamEnd) {
  isExamEnd = ParmsIsExamEnd;
 }

 public String getStuExamState() {
  return stuExamState;
 }

 public void setStuExamState(String ParmsStuExamState) {
  stuExamState = ParmsStuExamState;
 }

 public String getRemark() {
  return remark;
 }

 public void setRemark(String ParmsRemark) {
  remark = ParmsRemark;
 }

 public String getEndDate() {
  return endDate;
 }

 public void setEndDate(String ParmsEndDate) {
  endDate = ParmsEndDate;
 }


 public String getId() {

  if (examId==null||examId.isEmpty()) {
   return Id;
  }
  return examId;
 }

 public void setId(String ParmsId) {
  Id = ParmsId;
 }


 public String getStuEndDate() {
  return stuEndDate;
 }

 public void setStuEndDate(String ParmsStuEndDate) {
  stuEndDate = ParmsStuEndDate;
 }

 public String getMarkTeaId() {
  return markTeaId;
 }

 public void setMarkTeaId(String ParmsMarkTeaId) {
  markTeaId = ParmsMarkTeaId;
 }

 public String getReasonText() {
  return reasonText;
 }

 public void setReasonText(String ParmsReasonText) {
  reasonText = ParmsReasonText;
 }



}
