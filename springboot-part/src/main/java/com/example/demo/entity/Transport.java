package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("transport")
@Data
public class Transport {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String sender;
    private String senderId;
    private String senderAddress;
    private String senderNum;
    private String receiver;
    private String receiverId;
    private String receiverAddress;
    private String receiverNum;
    private String cargoInfo;
    private Double cargoWeight;
    private String cargoSituation;
    private String cargoPosition;
    private Integer vehicleId;
    private String vehicleInfo;
}
