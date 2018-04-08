package com.ican.service.impl;

import com.ican.config.Constant;
import com.ican.exception.icanServiceException;
import com.ican.domain.College;
import com.ican.service.CollegeService;
import com.ican.to.CollegeTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CollegeServiceImpl implements CollegeService {
    @Override
    @Transactional
    public int delete(int id) throws icanServiceException {
        Constant.DaoFacade.getUserInfoDao().delete(id);
        return Constant.DaoFacade.getCollegeDao().delete(id);
    }

    @Override
    public College select(int id) throws icanServiceException {
        return (College) Constant.DaoFacade.getCollegeDao().select(id);
    }

    @Override
    public int insert(College college) throws icanServiceException {
        return Constant.DaoFacade.getCollegeDao().insert(college);
    }

    @Override
    public int update(College college) throws icanServiceException {
        return Constant.DaoFacade.getCollegeDao().update(college);
    }

    @Override
    public int save(College college) throws icanServiceException {
        if (college == null) {
            return -1;
        }
        if (college.getId() > 0) {
            if (StringUtils.isEmpty(college.getName())) {
                college.setName("");
            }
            if (StringUtils.isEmpty(college.getUrl())) {
                college.setUrl("");
            }
            insert(college);
        }
        return college.getId();
    }

    @Transactional
    @Override
    public int save(CollegeTO collegeTO) throws icanServiceException {

        int id = Constant.ServiceFacade.getUserInfoService().save(collegeTO.toUserInfo());
        if (collegeTO.getId() <= 0) {
            College college = collegeTO.toCollege();
            college.setId(id);
            if (StringUtils.isEmpty(college.getName())) {
                college.setName("");
            }
            if (StringUtils.isEmpty(college.getUrl())) {
                college.setUrl("");
            }
            Constant.ServiceFacade.getCollegeService().insert(college);
        } else {
            Constant.ServiceFacade.getCollegeService().update(collegeTO.toCollege());
        }
        return id;
    }

    @Override
    public List<College> list(String ids, int schoolId, String order, int page, int size) throws icanServiceException {
        Map params = new HashMap();
        params.put("ids", ids);
        params.put("schoolId", schoolId);
        params.put("order", order);
        params.put("start", (page - 1) * size);
        params.put("size", size);
        return Constant.DaoFacade.getCollegeDao().list(params);
    }

    @Override
    public int count(String ids, int schoolId) throws icanServiceException {
        Map params = new HashMap();
        params.put("ids", ids);
        params.put("schoolId", schoolId);
        return Constant.DaoFacade.getCollegeDao().count(params);
    }
}
