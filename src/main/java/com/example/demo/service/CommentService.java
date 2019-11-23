package com.example.demo.service;

import com.example.demo.dto.CommentCreateDto;
import com.example.demo.dto.CommentDto;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId()==null ||  comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType()==CommentTypeEnum.COMMENT.getType()){
            Comment comment1=commentMapper.selectByParentId(comment.getParentId());
            if (comment1==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else{
            Question question=questionMapper.getById(comment.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            else {
                commentMapper.insert(comment);
                questionMapper.updateComment(question.getId());
            }
        }
    }

    public List<CommentDto> listByQuestionId(Integer id) {
        List<Comment> comments=commentMapper.selectByQuestionId(id,CommentTypeEnum.QUESTION.getType());
        if (comments.size()==0){
            return new ArrayList<>();
        }
        //评论人去重
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds=new ArrayList();
        userIds.addAll(commentators);
        //获取评论人
        List<User> users =userMapper.findByIdIn(userIds);
        Map<Integer,User> userMap=users.stream().collect(Collectors.toMap(user->user.getId(),user -> user));
        //转换Comment为commentDto
        List<CommentDto> commentDtos=comments.stream().map(comment -> {
            CommentDto commentDto=new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMap.get(comment.getCommentator()));
            return commentDto;
        }).collect(Collectors.toList());
        return commentDtos;
    }
}
