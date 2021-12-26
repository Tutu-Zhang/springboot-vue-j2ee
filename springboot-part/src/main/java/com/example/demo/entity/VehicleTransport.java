package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("vehicle_transport")
@Data
public class VehicleTransport {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer vehicleId;
    private Integer transportId;
}
