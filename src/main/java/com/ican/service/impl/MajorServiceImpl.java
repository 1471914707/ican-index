package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.domain.Major;
import com.ican.exception.icanServiceException;
import com.ican.service.MajorService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MajorServiceImpl implements MajorService {
    @Override
    public int insert(Major major) throws icanServiceException {
        return Constant.ServiceFacade.getMajorService().insert(major);
    }

    @Override
    public Major select(int id) throws icanServiceException {
        return (Major) Constant.ServiceFacade.getMajorService().select(id);
    }

    @Override
    public int update(Major major) throws icanServiceException {
        return Constant.ServiceFacade.getMajorService().update(major);
    }

    @Override
    public int delete(int id) throws icanServiceException {
        return Constant.ServiceFacade.getMajorService().delete(id);
    }

    @Override
    public int save(Major major) throws icanServiceException {
        if (major == null) {
            return 0;
        }
        if (major.getId() > 0) {
            update(major);
        } else {
            if (StringUtils.isEmpty(major.getName())) {
                major.setName("");
            }
            insert(major);
        }
        return major.getId();
    }

    @Override
    public List<Major> list(String ids, int schoolId, int collegeId, int departmentId, String order, int page, int size) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("schoolId", schoolId);
        param.put("collegeId", collegeId);
        param.put("departmentId", departmentId);
        param.put("order", order);
        param.put("start", (page - 1) * size);
        param.put("size", size);
        return Constant.DaoFacade.getMajorDao().list(param);
    }

    @Override
    public int count(String ids, int schoolId, int collegeId, int departmentId) throws icanServiceException {
        Map param = new HashMap();
        param.put("ids", ids);
        param.put("schoolId", schoolId);
        param.put("collegeId", collegeId);
        param.put("departmentId", departmentId);
        return Constant.DaoFacade.getMajorDao().count(param);
    }
}
