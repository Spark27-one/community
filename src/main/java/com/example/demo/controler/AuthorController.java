package com.example.demo.controler;

import com.example.demo.dto.AccessTokenDto;
import com.example.demo.dto.GithubUser;
import com.example.demo.pojo.User;
import com.example.demo.provider.GithubProvider;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client_id}")
    private String client_id;
    @Value("${github.client_secret}")
    private String client_secret;
    @Value("${github.redirect_uri}")
    private String redirect_uri;
    @Autowired
    private UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletResponse response){
        AccessTokenDto accessTokenDto=new AccessTokenDto();
        accessTokenDto.setClient_id(client_id);
        accessTokenDto.setCode(code);
        accessTokenDto.setClient_secret(client_secret);
        accessTokenDto.setRedirect_uri(redirect_uri);
        accessTokenDto.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDto);
        GithubUser user=githubProvider.getUser(accessToken);
        //System.out.println(user.getName());
        if(user!=null&&user.getId()!=null){
            //登陆成功，写进cookie和session
            User user1=new User();
            user1.setAccountId(String.valueOf(user.getId()));
            user1.setName(user.getName());
            user1.setAvatarUrl(user.getAvatar_url());
            //System.out.println(user1.getName());
            String token=UUID.randomUUID().toString();
            user1.setToken(token);
            userService.createOrUpdate(user1);
           // request.getSession().setAttribute("user",user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            //登陆失败
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
