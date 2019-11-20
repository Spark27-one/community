package com.example.demo.pojo;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private int creator;
    private int viewCount;
    private int commentCount;
    private int likeCount;
}
