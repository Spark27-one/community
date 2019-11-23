package com.example.demo.mapper;

import com.example.demo.pojo.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select * from comment where parent_id=#{parentId}")
    Comment selectByParentId(@Param(value="parentId") Integer parentId);

    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values " +
            "(#{parentId},#{type},#{commentator},#{gmtCreate},#{gmtModified},#{likeCount},#{content})")
    void insert(Comment comment);
    @Select("select * from comment where parent_id=#{parentId} and type=#{type}")
    List<Comment> selectByQuestionId(@Param(value="parentId")Integer id, @Param(value="type")Integer type);
}
