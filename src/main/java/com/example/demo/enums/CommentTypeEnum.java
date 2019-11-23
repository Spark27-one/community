package com.example.demo.enums;

public enum  CommentTypeEnum {
    QUESTION(1),
    COMMENT(2);
    private  Integer type;

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
            if(commentTypeEnum.getType()==type)
                return true;
        }
        return false;
    }

    public Integer getType(){
        return this.type;
    }
    CommentTypeEnum(Integer type){
        this.type=type;
    }
}
