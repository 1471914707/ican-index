package com.ican.webservice;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.vo.TeacherVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeacherWebService {

    private final static Logger logger = LoggerFactory.getLogger(TeacherWebService.class);

    public HashMap listVO(int schoolId, int collegeId, int degree, String jobId) {
        HashMap data = new HashMap();
        if (schoolId <= 0 && collegeId <= 0) {
            return data;
        }
        try {
            //先找出该学校之下的所有系
            int departmentTotal = Constant.ServiceFacade.getDepartmentService().count(null, schoolId, collegeId);
            List<Department> departmentList = Constant.ServiceFacade.getDepartmentService().list(null, schoolId, collegeId, null, 1, departmentTotal);
            //拿到系ids
            Set<String> departmentSet = new HashSet<>();
            Map departmentNameMap = new HashMap();
            for (Department department : departmentList) {
                departmentSet.add(department.getId() + "");
                departmentNameMap.put(department.getId(), department.getName());
            }
            String departmentIds = String.join(",", departmentSet);
            //查出所有的关联教师id
            int departmentTeacherTotal = Constant.ServiceFacade.getDepartmentTeacherService().count(departmentIds, 0, 0);
            List<DepartmentTeacher> departmentTeacherList = Constant.ServiceFacade.getDepartmentTeacherService().list(departmentIds, 0, 0, null, 1, departmentTeacherTotal);
            Set<String> teacherSet = new HashSet<>();
            Map departmentTeacherMap = new HashMap();
            for (DepartmentTeacher departmentTeacher : departmentTeacherList) {
                teacherSet.add(departmentTeacher.getTeacherId() + "");
                departmentTeacherMap.put(departmentTeacher.getTeacherId(), departmentNameMap.get(departmentTeacher.getDepartmentId()));
            }
            String teacherIds = String.join(",", teacherSet);
            //查出教师列表
            List<Teacher> teacherList = Constant.ServiceFacade.getTeacherService().list(teacherIds, jobId, degree, "id desc", 1, 100);
            int teacherTotal = Constant.ServiceFacade.getTeacherService().count(teacherIds, jobId, 0);
            Set<String> userInfoSet = new HashSet<>();
            for (Teacher teacher : teacherList) {
                userInfoSet.add(teacher.getId() + "");
            }
            //对应的基础信息
            String userInfoIds = String.join(",", userInfoSet);
            List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(userInfoIds, null, null, UserInfoService.USER_TEACHER, null, 1, 100);
            Map userInfoMap = new HashMap();
            for (UserInfo userInfo : userInfoList) {
                userInfoMap.put(userInfo.getId(), userInfo);
            }
            List<TeacherVO> teacherVOList = new ArrayList<>();
            for (Teacher teacher : teacherList) {
                TeacherVO teacherVO = new TeacherVO(teacher, (UserInfo) userInfoMap.get(teacher.getId()));
                teacherVO.setDepartmentName((String)departmentTeacherMap.get(teacher.getId()));
                teacherVOList.add(teacherVO);
            }
            data.put("list", teacherVOList);
            data.put("total",teacherTotal);
            return data;
        } catch (Exception e) {
            logger.error("获取教师列表信息异常", e);
            return data;
        }
    }

}
