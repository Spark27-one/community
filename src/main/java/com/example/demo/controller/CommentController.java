package com.example.demo.controller;

import com.example.demo.dto.CommentCreateDto;
import com.example.demo.dto.CommentDto;
import com.example.demo.dto.ResultDto;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.User;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if (commentCreateDto==null || commentCreateDto.getContent()==null){
            return ResultDto.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment=new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        commentService.insert(comment,user);
        return ResultDto.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDto<List<CommentDto>> comments(@PathVariable(name = "id") Integer id){
        List<CommentDto> commentDtos = commentService.listByQuestionId(id, CommentTypeEnum.COMMENT.getType());
        return ResultDto.okOf(commentDtos);
    }
}
