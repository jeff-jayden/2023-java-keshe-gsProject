package com.it.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    private Integer id;
    private String Tnum;
    private String Tusername;
    private String Tpassword;
    private String Tsex;
    private String Tpro;
    private String Toption;
}
