package com.ican.dao;

import com.ican.domain.Activity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActivityDao extends BaseDao {
    List<Activity> listByDate(@Param("date") String date, @Param("order") String order, @Param("start") int start, @Param("size") int size);
    int countByDate(@Param("date") String date);
}
