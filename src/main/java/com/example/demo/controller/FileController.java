package com.example.demo.controller;

import com.example.demo.dto.FileDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDto upload(){
        FileDto fileDto=new FileDto();
        fileDto.setSuccess(1);
        fileDto.setMessage("图片上传尚未完善");
        fileDto.setUrl("/images/jiazai.png");
        return fileDto;
    }
}
