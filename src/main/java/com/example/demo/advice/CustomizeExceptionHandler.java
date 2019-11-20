package com.example.demo.advice;

import com.example.demo.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle( Throwable e, Model model){
        if(e instanceof CustomizeException){
            model.addAttribute("messages",e.getMessage());
        }else{
            model.addAttribute("messages","服务跑丢了");
        }
        return new ModelAndView("error");
    }
}
