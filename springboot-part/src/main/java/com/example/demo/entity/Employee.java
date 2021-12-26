package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("employee")
@Data
public class Employee {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String employeeName;
    private String nickName;
    private Integer age;
    private String sex;
    private String position;
    private String situation;
}
