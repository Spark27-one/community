package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {
    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatarUrl) values " +
            "(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void  insert(User user);

    @Select({"select * from user where token=#{token}"})
    User findByToken(@Param("token") String token);
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") int id);
    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId")String accountId);
    @Update("update user set name=#{name},avatarUrl=#{avatarUrl},token=#{token},gmt_modified=#{gmtModified} where id=#{id}")
    void update(User user);
    @Select("<script>"
            + "SELECT * FROM user WHERE id IN "
            + "<foreach item='item' index='index' collection='userIds'      open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    List<User> findByIdIn(@Param("userIds")List<Integer> userIds);
}
