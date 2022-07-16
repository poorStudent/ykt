package com.vms.ykt.yktStuWeb.zjy;
 /**[{"id":"712taoksc7ha5seeotsj4w","name":"01毛泽东思想及历史地位","sortOrder":1,"percent":100.0},
         * {"id":"712taoksg5phmvpl39ba","name":"02新民主主义革命理论","sortOrder":2,"percent":100.0},**/
public class zjyModuleInfo {
    private String id;

     public String getId() {
         return id;
     }

     public void setId(String ParmsId) {
         id = ParmsId;
     }

     public String getName() {
         return name;
     }

     public void setName(String ParmsName) {
         name = ParmsName;
     }

     public String getPercent() {
         return percent;
     }

     public void setPercent(String ParmsPercent) {
         percent = ParmsPercent;
     }

     public String getSortOrder() {
         return sortOrder;
     }

     public void setSortOrder(String ParmsSortOrder) {
         sortOrder = ParmsSortOrder;
     }

     private String name;
    private String percent;
    private String sortOrder;
}
