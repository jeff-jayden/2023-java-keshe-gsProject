package com.it.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    private Integer id;
    private String Toname;
    private String Ttype;
    private String Tbackground;
    private String Tdescription;
    private String StudentID;
    private Integer Status;

    public String getStatusStr() {
        if (Status == null) {
            return "未知";
        }
        return Status == 0 ? "未选择" : "已选择";
    }
}
