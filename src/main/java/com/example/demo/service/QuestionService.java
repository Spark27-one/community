package com.example.demo.service;


import com.example.demo.dto.PageDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    public PageDto list(Integer page, Integer size){
        PageDto pageDto=new PageDto();
        Integer totalPage;
        Integer totalcount=questionMapper.count();
        if (totalcount % size==0){
            totalPage=totalcount/size;
        }else{
            totalPage=totalcount/size+1;
        }
        if (page<1) page=1;
        if (page>totalPage&&totalPage!=0) page=totalPage;
        else page=1;
        pageDto.setPageination(totalPage,page);
        if (page!=pageDto.getPage()) page=pageDto.getPage();
        Integer offset=size*(page - 1);
        List<Question> questionList=questionMapper.list(offset,size);
        List<QuestionDto> questionDtos=new ArrayList<>();
        for(Question question:questionList){
            User user=userMapper.findById(question.getCreator());
            //System.out.println(question.getGmtCreate());
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            //System.out.println(questionDto.getGmtCreate());
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pageDto.setQuestionDtos(questionDtos);
        return  pageDto;
    }
    public PageDto listByuserId( Integer userId,Integer page, Integer size){

        PageDto pageDto=new PageDto();
        Integer totalPage;
        Integer totalcount=questionMapper.countByUserId(userId);
        if (totalcount % size==0){
            totalPage=totalcount/size;
        }else{
            totalPage=totalcount/size+1;
        }
        if (page<1) page=1;
        if (page>totalPage&&totalPage!=0) page=totalPage;
        else page=1;
        pageDto.setPageination(totalPage,page);
        if (page!=pageDto.getPage()) page=pageDto.getPage();
        Integer offset=size*(page - 1);
        List<Question> questionList=questionMapper.listByuserId(userId,offset,size);
        List<QuestionDto> questionDtos=new ArrayList<>();
        for(Question question:questionList){
            User user=userMapper.findById(question.getCreator());
            //System.out.println(question.getGmtCreate());
            QuestionDto questionDto=new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            //System.out.println(questionDto.getGmtCreate());
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pageDto.setQuestionDtos(questionDtos);
        return  pageDto;
    }

    public QuestionDto getById(Integer id) {
        Question question=questionMapper.getById(id);
        if (question==null){
            throw new CustomizeException("你找的问题不在了，可以换个试试哦");
        }
        QuestionDto questionDto=new QuestionDto();
        User user=userMapper.findById(question.getCreator());
        BeanUtils.copyProperties(question,questionDto);
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }
        else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
