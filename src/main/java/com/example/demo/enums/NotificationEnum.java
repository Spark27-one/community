package com.example.demo.enums;

public enum NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论"),
    ;
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotificationEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }
    public static String nameOfType(int type) {
        for (NotificationEnum notificationTypeEnum : NotificationEnum.values()) {
            if (notificationTypeEnum.getType() == type) {
                return notificationTypeEnum.getName();
            }
        }
        return "";
    }
}
