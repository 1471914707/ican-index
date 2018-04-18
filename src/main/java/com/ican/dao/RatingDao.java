package com.ican.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RatingDao extends BaseDao {
    List listByGroupsId(@Param("groupsIds") String groupsIds);

}
