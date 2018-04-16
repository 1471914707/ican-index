package com.ican.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProjectDao extends BaseDao {
    List<Integer> listByWarn(@Param("warn") int warn, @Param("page") int page, @Param("size") int size);

    int countByWarn(@Param("warn") int warn);
}
