package com.example.demo.service;

import com.example.demo.dto.NotificationDto;
import com.example.demo.dto.PageDto;
import com.example.demo.enums.NotificationEnum;
import com.example.demo.enums.NotificationStatusEnum;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.Notification;
import com.example.demo.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;
    public PageDto list(Integer id, Integer page, Integer size) {
        PageDto pageDto=new PageDto();
        Integer totalPage;
        Integer totalcount=notificationMapper.countByUserId(id);
        if (totalcount % size==0){
            totalPage=totalcount/size;
        }else{
            totalPage=totalcount/size+1;
        }
        if (page<1) page=1;
        if (page>totalPage&&totalPage!=0) page=totalPage;
        //else page=1;
        pageDto.setPageination(totalPage,page);
        if (page!=pageDto.getPage()) page=pageDto.getPage();
        Integer offset=size*(page - 1);
        List<Notification> notifications=notificationMapper.listByuserId(id,offset,size);
        if (notifications.size()==0){
            return pageDto;
        }
        List<NotificationDto> notificationDtos=new ArrayList<>();
        for(Notification notification:notifications){
            NotificationDto notificationDto=new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            notificationDto.setTypeName(NotificationEnum.nameOfType(notification.getType()));
            notificationDtos.add(notificationDto);
        }
        pageDto.setData(notificationDtos);
        return  pageDto;
    }

    public int unreadCount(Integer id) {
        return notificationMapper.selsectunreadCount(id);
    }

    public NotificationDto read(Integer id, User user) {
        Notification notification=notificationMapper.selectById(id);
        if (notification==null){
            throw new ClassCastException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND.getMessage());
        }
        if (notification.getReceiver()!=user.getId())
            throw new ClassCastException(CustomizeErrorCode.READ_NOTIFICATION_FAIL.getMessage());
        notificationMapper.updataStatus(notification.getId());
        NotificationDto notificationDto=new NotificationDto();
        BeanUtils.copyProperties(notification,notificationDto);
        notificationDto.setTypeName(NotificationEnum.nameOfType(notification.getType()));
        return notificationDto;
    }
}
