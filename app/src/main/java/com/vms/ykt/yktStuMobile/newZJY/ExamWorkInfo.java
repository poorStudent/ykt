package com.vms.ykt.yktStuMobile.newZJY;

/*
{"retCode":"0","data":
{"4028813884c3b5ec0184cd9c28d037d2":{"isTemplatePaper":"0","courseNameAlias":"课程",
"courseName":"中国传统文化与哲学（2022下）工业机器人20级1班（专本）","examId":"4028813884c3b5ec0184cd9c28d037d2",
"examScore":100,"structure":[{"id":"e45b9b3aacf34b6ab6d38aa31bbeb153","name":"单选题","count":2,"total":40},
{"id":"a9c2bc75bb104afd83056d5a56dcdfd3","name":"多选题","count":2,"total":40},
{"id":"6b55d60288c84a4fb4601f62eb018fe0","name":"判断题","count":1,"total":20}],
"paperId":"fc64f089f3f04ee596de6d5f8c5854e0"}
}
}
*/
public class ExamWorkInfo {

    String paperId;
    String structure;


    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }


}
