package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Paper;
import com.ican.exception.icanServiceException;
import com.ican.service.PaperService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaperServiceImpl implements PaperService{

    @Override
    public int insert(Paper paper) throws icanServiceException {
        return Constant.DaoFacade.getPaperDao().insert(paper);
    }

    @Override
    public Paper select(int id) throws icanServiceException {
        return (Paper) Constant.DaoFacade.getPaperDao().select(id);
    }

    @Override
    public int update(Paper paper) throws icanServiceException {
        return Constant.DaoFacade.getPaperDao().update(paper);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.DaoFacade.getPaperDao().delete(id);
    }

    @Override
    public int save(Paper paper) throws icanServiceException {
        if (paper == null) {
            return 0;
        }
        if (paper.getId() > 0) {
            update(paper);
        } else {
            if (StringUtils.isEmpty(paper.getRemark())) {
                paper.setRemark("");
            }
            if (StringUtils.isEmpty(paper.getRequire())) {
                paper.setRequire("");
            }
            insert(paper);
        }
        return paper.getId();
    }

    @Override
    public List<Paper> list(String ids, int current, int schoolId, int collegeId, int departmentId, int clazzId,
                            int teacherId, String name, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("current", current);
        param.put("schoolId", schoolId);
        param.put("collegeId", collegeId);
        param.put("departmentId", departmentId);
        param.put("clazzId", clazzId);
        param.put("teacherId", teacherId);
        param.put("name", name);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getPaperDao().list(param);
    }

    @Override
    public int count(String ids, int current, int schoolId, int collegeId, int departmentId,
                      int clazzId, int teacherId, String name) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("current", current);
        param.put("schoolId", schoolId);
        param.put("collegeId", collegeId);
        param.put("departmentId", departmentId);
        param.put("clazzId", clazzId);
        param.put("teacherId", teacherId);
        param.put("name", name);
        return Constant.DaoFacade.getPaperDao().count(param);
    }
}
