package com.example.demo.dto;

import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import lombok.Data;

@Data
public class ResultDto {
    private Integer code;
    private String message;
    public static ResultDto errorOf(Integer code,String message){
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(code);
        resultDto.setMessage(message);
        return resultDto;
    }
   public static ResultDto errorOf(CustomizeErrorCode customizeErrorCode) {
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(customizeErrorCode.getCode());
        resultDto.setMessage(customizeErrorCode.getMessage());
        return resultDto;
    }
    public static ResultDto okOf(){
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(200);
        resultDto.setMessage("成功！！");
        return resultDto;
    }

    public static ResultDto errorOf(CustomizeException e) {
        ResultDto resultDto=new ResultDto();
        resultDto.setCode(e.getCode());
        resultDto.setMessage(e.getMessage());
        return resultDto;
    }
}
