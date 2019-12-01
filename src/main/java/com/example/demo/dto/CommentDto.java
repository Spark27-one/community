package com.example.demo.dto;

import com.example.demo.pojo.User;
import lombok.Data;

@Data
public class CommentDto {
    private Integer id;
    private Integer parentId;
    private Integer type;
    private Integer commentator;
    private Long  gmtCreate;
    private Long gmtModified;
    private Integer likeCount;
    private String content;
    private Integer commentCount;
    private User user;
}
