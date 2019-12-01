package com.example.demo.service;

import com.example.demo.dto.CommentDto;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.enums.NotificationEnum;
import com.example.demo.enums.NotificationStatusEnum;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Notification;
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
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
    public void insert(Comment comment,User commentUser) {
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
            Question question=questionMapper.getById(comment1.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //插入评论
            commentMapper.insert(comment);
            //添加评论数
            commentMapper.updateComment(comment1.getId());
            //增加回复
            createNotify(comment, comment1.getCommentator(), commentUser.getName(),question.getTitle(),NotificationEnum.REPLY_COMMENT.getType(),question.getId());
        }else{
            Question question=questionMapper.getById(comment.getParentId());
            if (question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            else {
                //插入评论
                commentMapper.insert(comment);
                //添加评论数
                questionMapper.updateComment(question.getId());
                //增加回复
                createNotify(comment, question.getCreator(), commentUser.getName(),question.getTitle(),NotificationEnum.REPLY_QUESTION.getType(),question.getId());
            }
        }
    }

    private void createNotify(Comment comment, Integer receiver, String notifityName,String outerTitle,int type,int outerid) {
        if (receiver==comment.getCommentator())
            return;
        Notification notification=new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(type);
        notification.setOuterId(outerid);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifityName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDto> listByQuestionId(Integer id,Integer type) {
        List<Comment> comments=commentMapper.selectByQuestionId(id,type);
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
