package com.it.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Integer id;
    private String Snum;
    private String Susername;
    private String Spassword;
    private String Ssex;
    private String Sbirth;
    private String Sphone;
    private Integer Soption;
    private Integer Status;

    //逻辑视图
    public String getStatusStr(){
        if (Status == null){
            return "未知";
        }
        return Status == 0 ? "未选择":"已选择";
    }
}
