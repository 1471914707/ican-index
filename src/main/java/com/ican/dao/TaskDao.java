package com.ican.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TaskDao extends BaseDao {
    int countByNowDay(@Param("executorId") int executorId, @Param("nowDay") String nowDay);
}
