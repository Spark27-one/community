package com.example.demo.controller;

import com.example.demo.dto.NotificationDto;
import com.example.demo.enums.NotificationEnum;
import com.example.demo.pojo.User;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Integer id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDto notificationDTO = notificationService.read(id, user);

        if (NotificationEnum.REPLY_COMMENT.getName() == notificationDTO.getTypeName()
                || NotificationEnum.REPLY_QUESTION.getName() == notificationDTO.getTypeName()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}
