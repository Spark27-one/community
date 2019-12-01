package com.example.demo.mapper;

import com.example.demo.pojo.Notification;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {
    @Insert("insert into notification (notifier,receiver,outerId,type,gmt_create,status,notifier_name,outer_title) "+
            "values(#{notifier},#{receiver},#{outerId},#{type},#{gmtCreate},#{status},#{notifierName},#{outerTitle})")
    void insert(Notification notification);
    @Select("select count(1) from notification where receiver=#{userId}")
    Integer countByUserId(@Param(value = "userId")Integer id);
    @Select("select * from notification where receiver=#{userId} ORDER BY gmt_create DESC limit #{offset},#{size}")
    List<Notification> listByuserId(@Param(value = "userId") Integer userId,@Param(value = "offset") Integer offset, @Param(value = "size")Integer size);
    @Select("select count(1) from notification where receiver=#{userId} and status=0")
    int selsectunreadCount(@Param(value = "userId")Integer id);
    @Select("select * from notification where id=#{id}")
    Notification selectById(@Param(value = "id")Integer id);
    @Update("update notification set status=1 where id=#{id}")
    void updataStatus(@Param(value = "id")Integer id);
}
