package com.example.demo.pojo;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long  gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
}
