package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("vehicle")
@Data
public class Vehicle {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer driverId;
    private String vehicleInfo;
}
