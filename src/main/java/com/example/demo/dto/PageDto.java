package com.example.demo.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDto {
    private List<QuestionDto> questionDtos;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages=new ArrayList<>();
    private Integer totalPage;
    public void setPageination(Integer totalPage, Integer page) {
        this.totalPage=totalPage;
        this.page=page;

        pages.add(page);
        for (int i=1;i<=3;i++){
            if(page-i>0)
                pages.add(0,page-i);
            if (page+i<=totalPage)
                pages.add(page+i);
        }
        //是否展示前一页
        if (page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }
        //是否展示后一页
        if (page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }

        if (pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }

        if (pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }
    }
}
