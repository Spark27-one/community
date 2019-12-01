package com.example.demo.interceptor;

import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.User;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class SessionInterception implements HandlerInterceptor {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies=request.getCookies();
        if(cookies != null && cookies.length!=0)
            for(Cookie cookie:cookies){
                if("token".equals(cookie.getName())){
                    String token=cookie.getValue();
                    List<User> users=userMapper.findByToken(token);
                    if(users.size()!=0){
                        request.getSession().setAttribute("user",users.get(0));
                        int unreadCount=notificationService.unreadCount(users.get(0).getId());
                        request.getSession().setAttribute("unreadMessage",unreadCount);
                    }
                    break;
                }
            }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
