package com.example.demo.dto;


import com.example.demo.pojo.User;
import lombok.Data;

@Data
public class NotificationDto {
    private Integer id;
    private Long gmtCreate;
    private Integer status;
    private Integer notifier;
    private String notifierName;
    private String outerTitle;
    private String typeName;
    private Integer outerId;
    private Integer type;
}
