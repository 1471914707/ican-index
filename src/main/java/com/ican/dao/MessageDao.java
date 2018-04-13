package com.ican.dao;

import com.ican.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface MessageDao extends BaseDao {
    List<Message> listByUserId(@Param("id") int id, @Param("start") int start, @Param("size") int size);

    int countByUserId(@Param("id") int id);
}
