package com.vms.ykt.yktStuWeb.Cqooc;
//body: {choices: ["票面利率", "购入价格", "票面价格", "市场价格"]}
//id: "963904"
//points: "2"
//question: "债券的价值有两部分构成，一是各期利息的现值，二是（）的现值"
//type: "0"
public class ExamPreview {
    private String id;
    private String points;
    private String question;
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
