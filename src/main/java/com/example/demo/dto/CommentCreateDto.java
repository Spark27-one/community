package com.example.demo.dto;

import lombok.Data;

@Data
public class CommentCreateDto {
    private Integer parentId;
    private String content;
    private Integer type;
}
