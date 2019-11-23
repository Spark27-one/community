package com.example.demo.controller;

import com.example.demo.dto.CommentCreateDto;
import com.example.demo.dto.CommentDto;
import com.example.demo.dto.QuestionDto;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDto questionDto= questionService.getById(id);
        List<CommentDto> commentDtos=commentService.listByQuestionId(id);
        //增加阅读数
        questionService.incView(id);
        model.addAttribute("question",questionDto);
        model.addAttribute("comments",commentDtos);
        return "question";
    }
}
