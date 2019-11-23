package com.example.demo.controller;

import com.example.demo.dto.CommentCreateDto;
import com.example.demo.dto.ResultDto;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.User;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDto commentCreateDto,
                          HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
        if (user==null){
            return ResultDto.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment=new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDto.okOf();
    }
}
