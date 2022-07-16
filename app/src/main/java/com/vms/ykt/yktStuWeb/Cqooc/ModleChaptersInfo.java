package com.vms.ykt.yktStuWeb.Cqooc;
/**{"meta":{"total":"19","start":"1","size":"19"},"data":
 * [{"id":"325711","title":"项目一 三相异步电动机点动\/连续运行控制","level":"2",
 * "selfId":1,"parentId":"325710","status":"1","pubClass":"","isElective":2},
 * {"id":"325716","title":"项目一 抢答器","level":"2","selfId":1,"parentId":"325715","status":"1","pubClass":"","isElective":2},{"id":"325721","title":"项目一 恒温\/恒压供水","level":"2","selfId":1,"parentId":"325720","status":"1","pubClass":"","isElective":2},{"id":"325723","title":"项目一 电梯实时高度显示","level":"2","selfId":1,"parentId":"325722","status":"1","pubClass":"","isElective":2},{"id":"325725","title":"项目一 步进电机控制","level":"2","selfId":1,"parentId":"325724","status":"1","pubClass":"","isElective":2},{"id":"325727","title":"项目一 两台电机异地控制","level":"2","selfId":1,"parentId":"325726","status":"1","pubClass":"","isElective":2},{"id":"325710","title":"模块一 位逻辑指令","level":"1","selfId":2,"parentId":null,"status":"1","pubClass":"","isElective":2},{"id":"325712","title":"项目二 三相异步电动机正反转运行控制","level":"2","selfId":2,"parentId":"325710","status":"1","pubClass":"","isElective":2},{"id":"325717","title":"项目二 流水灯","level":"2","selfId":2,"parentId":"325715","status":"1","pubClass":"","isElective":2},{"id":"325728","title":"项目二 两台电机同步控制","level":"2","selfId":2,"parentId":"325726","status":"1","pubClass":"","isElective":2},{"id":"325713","title":"项目三 三相异步电动机Y-D降压起动控制","level":"2","selfId":3,"parentId":"325710","status":"1","pubClass":"","isElective":2},{"id":"325715","title":"模块二 功能指令","level":"1","selfId":3,"parentId":null,"status":"1","pubClass":"","isElective":2},{"id":"325718","title":"项目三 倒计时","level":"2","selfId":3,"parentId":"325715","status":"1","pubClass":"","isElective":2},{"id":"325714","title":"项目四 三相异步电动机周期工作","level":"2","selfId":4,"parentId":"325710","status":"1","pubClass":"","isElective":2},{"id":"325719","title":"项目四 交通灯","level":"2","selfId":4,"parentId":"325715","status":"1","pubClass":"","isElective":2},{"id":"325720","title":"模块三 模拟量","level":"1","selfId":4,"parentId":null,"status":"1","pubClass":"","isElective":2},{"id":"325722","title":"模块四 高速计数","level":"1","selfId":5,"parentId":null,"status":"1","pubClass":"","isElective":2},{"id":"325724","title":"模块五 PWM\/PTO","level":"1","selfId":6,"parentId":null,"status":"1","pubClass":"","isElective":2},{"id":"325726","title":"模块六：通信","level":"1","selfId":7,"parentId":null,"status":"1","pubClass":"","isElective":2}]}**/
public class ModleChaptersInfo {
    private String id;
    private String title;
    private String level;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String ParmsId) {
        id = ParmsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String ParmsTitle) {
        title = ParmsTitle;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String ParmsLevel) {
        level = ParmsLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String ParmsStatus) {
        status = ParmsStatus;
    }

    public String getIsElective() {
        return isElective;
    }

    public void setIsElective(String ParmsIsElective) {
        isElective = ParmsIsElective;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String ParmsParentId) {
        parentId = ParmsParentId;
    }

    private String isElective;
    private String parentId;
}
