package com.vms.ykt.yktStuWeb.Cqooc;
//打开测验问题详情
/**
 * {"id":102339,"created":1652838894837,"lastUpdated":1655994823629,"ownerId":654171,"title":"1.1.1初识PLC",
 * "body":[
 * {"desc":"单选题","type":"0","total":"0","questions":[]},
 * {"desc":"多选题","type":"1","total":"1","questions":[{"points":"3","question":"<p>PLC的一个扫描周期主要包含&nbsp;<span style=\"text-decoration:underline;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>&nbsp;、<span style=\"text-decoration:underline;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span> 、&nbsp;<span style=\"text-decoration:underline;\"> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span>三个阶段</p>","body":{"choices":["输入采样","程序执行","输出刷新","处理中断"]},"type":"1","id":"95494"}]},
 * {"desc":"","type":"2","total":"0","questions":[]},
 * {"desc":"","type":"3","total":"0","questions":[]},
 * {"desc":"判断题","type":"4","total":"1","questions":[{"points":"2","question":"<p>传统继电-接触器控制的控制逻辑是通过触点的串并联实现来的。</p>","body":{},"type":"4","id":"95493"}]}],
 * "courseId":"334570416","parentId":"325711","status":1,"submitEnd":1656950400000,"number":5,"content":"独立完成","gradeId":null,"owner":"137352008110103","score":5,"correctNum":"176","totalNum":"224","accuracy":79}**/
public class testQuestionInfo {
private String points;
private String desc;
private String total;
private String type;
private String question;
private String body;
private String id;

}
//课件形式测验问题详情
/**{

 "id": 102339,
 "title": "1.1.1初识PLC",
 "body": [
 {
 "desc": "单选题",
 "type": "0",
 "total": "0",
 "questions": []
 },
 {
 "desc": "多选题",
 "type": "1",
 "total": "1",
 "questions": [
 {
 "points": "3",
 "question": "<p>PLC的一个扫描周期主要包含&nbsp;<span style=\"text-decoration:underline;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span>&nbsp;、<span style=\"text-decoration:underline;\">&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span> 、&nbsp;<span style=\"text-decoration:underline;\"> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</span>三个阶段</p>",
 "body": {
 "answer": [
 "0",
 "1",
 "2"
 ],
 "choices": [
 "输入采样",
 "程序执行",
 "输出刷新",
 "处理中断"
 ]
 },
 "type": "1",
 "id": "95494"
 }
 ]
 },
 {
 "desc": "",
 "type": "2",
 "total": "0",
 "questions": []
 },
 {
 "desc": "",
 "type": "3",
 "total": "0",
 "questions": []
 },
 {
 "desc": "判断题",
 "type": "4",
 "total": "1",
 "questions": [
 {
 "points": "2",
 "question": "<p>传统继电-接触器控制的控制逻辑是通过触点的串并联实现来的。</p>",
 "body": {
 "answer": "1"
 },
 "type": "4",
 "id": "95493"
 }
 ]
 }
 ],
 "status": 1,
 "submitEnd": 1656950400000,
 "number": 5,
 "content": "独立完成"
 }*/