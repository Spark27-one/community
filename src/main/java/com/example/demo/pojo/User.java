package com.example.demo.pojo;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
}
