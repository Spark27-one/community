package com.example.demo.controller;

import com.example.demo.cache.TagCache;
import com.example.demo.dto.QuestionDto;
import com.example.demo.pojo.Question;
import com.example.demo.pojo.User;
import com.example.demo.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Integer id,
            HttpServletRequest request,
            Model model
    ){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if (title==null||title==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if (description==null||description==""){
            model.addAttribute("error","内容不能为空");
            return "publish";
        }
        if (tag==null||tag==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(TagCache.filterInvalid(tag))){
            model.addAttribute("error","输入非法标签"+TagCache.filterInvalid(tag));
            return "publish";
        }
        User user = (User)request.getSession().getAttribute("user");
        if(null==user){
            model.addAttribute("error","用户未登录");
            return "publish";
        }
        Question question=new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        model.addAttribute("tags", TagCache.get());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
    @GetMapping("/question/publish/{id}")
    public String edit(@PathVariable(name="id") Integer id,Model model,
                       HttpServletRequest request){
        QuestionDto question=questionService.getById(id);
        User user=(User) request.getSession().getAttribute("user");
        if (null!=user && question.getCreator()==user.getId()){
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
        }
        else
            return "redirect:/";
    }
}
