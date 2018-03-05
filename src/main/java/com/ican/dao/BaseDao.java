package com.ican.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao {

    int insert(Object object);

    int delete(int id);

    Object select(int id);

    int update(Object object);

    List list(Map params);

    int count(Map params);
}
