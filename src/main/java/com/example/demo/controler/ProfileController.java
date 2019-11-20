package com.example.demo.controler;

import com.example.demo.dto.PageDto;
import com.example.demo.pojo.User;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


@Controller
public class ProfileController {

    @Autowired
    QuestionService questionService;
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size,
                          HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null) return "redirect:/";
        if ("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        if ("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        PageDto pageDto = questionService.listByuserId(user.getId(), page, size);
        model.addAttribute("pageDto",pageDto);
        return "profile";
    }
}
