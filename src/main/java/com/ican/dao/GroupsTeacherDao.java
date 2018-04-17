package com.ican.dao;

import com.ican.domain.GroupsTeacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupsTeacherDao extends BaseDao {
    List<GroupsTeacher> listByGroupsIds(@Param("groupsIds") String groupsIds);
}
