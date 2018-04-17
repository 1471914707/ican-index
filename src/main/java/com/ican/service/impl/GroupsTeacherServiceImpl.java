package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.GroupsTeacher;
import com.ican.exception.icanServiceException;
import com.ican.service.GroupsTeacherService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupsTeacherServiceImpl implements GroupsTeacherService {
    @Override
    public int insert(GroupsTeacher groupsTeacher) throws icanServiceException {
        return Constant.DaoFacade.getGroupsTeacherDao().insert(groupsTeacher);
    }

    @Override
    public GroupsTeacher select(int id) throws icanServiceException {
        return (GroupsTeacher) Constant.DaoFacade.getGroupsTeacherDao().select(id);
    }

    @Override
    public int update(GroupsTeacher groupsTeacher) throws icanServiceException {
        return Constant.DaoFacade.getGroupsTeacherDao().update(groupsTeacher);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getGroupsTeacherDao().delete(id);
    }

    @Override
    public int save(GroupsTeacher groupsTeacher) throws icanServiceException {
        if (groupsTeacher == null) {
            return 0;
        }
        if (groupsTeacher.getId() > 0) {
            update(groupsTeacher);
        } else {
            insert(groupsTeacher);
        }
        return groupsTeacher.getId();
    }

    @Override
    public List<GroupsTeacher> list(String ids, int answerId, int teacherId, int groupsId, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("answerId", answerId);
        param.put("teacherId", teacherId);
        param.put("groupsId", groupsId);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getGroupsTeacherDao().list(param);
    }

    @Override
    public int count(String ids, int answerId, int teacherId, int groupsId) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("answerId", answerId);
        param.put("teacherId", teacherId);
        param.put("groupsId", groupsId);
        return Constant.DaoFacade.getGroupsTeacherDao().count(param);
    }

    @Override
    public List<GroupsTeacher> listByGroupsIds(String groupsIds) throws icanServiceException {
        return Constant.DaoFacade.getGroupsTeacherDao().listByGroupsIds(groupsIds);
    }
}
