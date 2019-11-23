package com.example.demo.advice;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.ResultDto;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    Object handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response){
        String contentType=request.getContentType();
        if("application/json".equals(contentType)){
            ResultDto resultDto=null;
            if(e instanceof CustomizeException){
                resultDto= ResultDto.errorOf((CustomizeException)e);
            }else{
                resultDto= ResultDto.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer=response.getWriter();
                writer.write(JSON.toJSONString(resultDto));
                writer.close();
            }catch (IOException ioe){
            }
            return null;
        }else{
            if(e instanceof CustomizeException){
                model.addAttribute("messages",e.getMessage());
            }else{
                model.addAttribute("messages",CustomizeErrorCode.SYS_ERROR.getMessage());
            }
            return new ModelAndView("error");
        }
    }
}
