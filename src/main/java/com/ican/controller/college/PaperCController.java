package com.ican.controller.college;


import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.util.*;
import com.ican.vo.PaperVO;
import com.ican.vo.StudentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

@Api("二级学院操作选题")
@Controller
@RequestMapping("/college/paper")
public class PaperCController {

    private final static Logger logger = LoggerFactory.getLogger(PaperCController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam("activityId") int activityId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        return "/college/paper/list";
    }

    @ApiOperation("获取选题列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "departmentId", defaultValue = "0") int departmentId,
                               @RequestParam(value = "teacherId", defaultValue = "0") int teacherId,
                               @RequestParam(value = "current", defaultValue = "0") int current,
                               @RequestParam(value = "activityId", defaultValue = "0") int activityId,
                               @RequestParam(value = "title", defaultValue = "",required = false) String title,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            List<Paper> paperList = Constant.ServiceFacade.getPaperService().list(null, activityId, current, 0, self.getId(), departmentId,
                    teacherId, title, 0,"id desc", page, size);
            List<PaperVO> paperVOList = Constant.ServiceFacade.getPaperWebService().listVO(paperList);
            int total = Constant.ServiceFacade.getPaperService().count(null, activityId, current, 0, self.getId(), departmentId, teacherId,
                    title,0);
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            Map data = new HashMap();
            data.put("list", paperVOList);
            data.put("total", total);
            data.put("activity", activity);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("获取选题列表异常", e);
            return result;
        }
    }

    @ApiOperation("获取选题详情")
    @ResponseBody
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public BaseResult info(@RequestParam(value = "id", defaultValue = "0") int id,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Paper paper = Constant.ServiceFacade.getPaperService().select(id);
            if (paper == null || paper.getCollegeId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            PaperVO paperVO = Constant.ServiceFacade.getPaperWebService().getPaperVO(paper);
            BaseResultUtil.setSuccess(result, paperVO);
            return result;
        } catch (Exception e) {
            logger.error("获取选题详情异常", e);
            return result;
        }
    }

    @ApiOperation("获取选题学生关联")
    @ResponseBody
    @RequestMapping(value = "/student", method = RequestMethod.GET)
    public BaseResult student(@RequestParam(value = "id", defaultValue = "0") int id,
                              HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            List<PaperStudent> paperStudentList = Constant.ServiceFacade.getPaperStudentService().list(null, 0, 0, 0, self.getId(), 0,
                    0, 0, 0, 0, id, "id desc", 1, 100);
            List<StudentVO> studentVOList = new ArrayList<>();
            if (paperStudentList != null && paperStudentList.size() > 0) {
                Set<String> studentSet = new HashSet<>();
                for (PaperStudent paperStudent : paperStudentList) {
                    studentSet.add(paperStudent.getStudentId() + "");
                }
                String studentIds = String.join(",", studentSet);
                List<Student> studentList = Constant.ServiceFacade.getStudentService().list(studentIds, 0, 0, 0, 0,
                        0, 0, null, null, 1, 100);
                studentVOList = Constant.ServiceFacade.getStudentWebService().listVO(studentList);
            }

            /*List<UserInfo> studentInfoList = Constant.ServiceFacade.getUserInfoService().list(studentIds, null, null, 0, null, 1, 100);
            Map studentMap = new HashMap();
            for (Student student : studentList) {
                studentMap.put(student.getId(), student);
            }
            List<StudentVO> studentVOList = new ArrayList<>();
            for (UserInfo userInfo : studentInfoList) {
                StudentVO studentVO = new StudentVO((Student) studentMap.get(userInfo.getId()), userInfo);
                studentVOList.add(studentVO);
            }*/


            BaseResultUtil.setSuccess(result, studentVOList);
            return result;
        } catch (Exception e) {
            logger.error("获取选题学生关联异常", e);
            return result;
        }
    }

    @ApiOperation("改变选题状态")
    @ResponseBody
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public BaseResult status(@RequestParam(value = "id", defaultValue = "0") int id,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            Paper paper = Constant.ServiceFacade.getPaperService().select(id);
            if (paper == null || paper.getCollegeId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (paper.getStatus() == 1) {
                paper.setStatus(2);
            } else {
                paper.setStatus(1);
            }
            Constant.ServiceFacade.getPaperService().save(paper);
            BaseResultUtil.setSuccess(result, paper.getStatus());
            return result;
        } catch (Exception e) {
            logger.error("获取选题详情异常", e);
            return result;
        }
    }

    @ApiOperation("导出选题excel表")
    @ResponseBody
    @RequestMapping(value = "/excel", method = RequestMethod.GET)
    public BaseResult excel(@RequestParam(value = "activityId", defaultValue = "0") int activityId,
                            HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (activityId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            College college = Constant.ServiceFacade.getCollegeService().select(self.getId());
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null || activity.getUserId() != college.getSchoolId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            int total = Constant.ServiceFacade.getPaperService().count(null, activityId, 0, college.getSchoolId(), self.getId(), 0, 0, null,0);
            List<Paper> paperList = Constant.ServiceFacade.getPaperService().list(null, activityId, 0, college.getSchoolId(),
                    self.getId(), 0, 0, null, 0,null, 1, total);
            List<PaperVO> paperVOList = Constant.ServiceFacade.getPaperWebService().listVO(paperList);
            if (paperVOList != null && paperVOList.size() > 0) {
                Collections.sort(paperVOList, new Comparator<PaperVO>() {
                    @Override
                    public int compare(PaperVO vo1, PaperVO vo2) {
                        if (vo1.getDepartment().getId() != vo2.getDepartment().getId()) {
                            return vo1.getDepartment().getId() - vo2.getDepartment().getId();
                        } else {
                            return vo1.getTeacher().getId() - vo2.getTeacher().getId();
                        }
                    }
                });
                List<List<String>> data = new ArrayList<List<String>>();
                for (PaperVO paperVO : paperVOList) {
                    List<String> vlist = new ArrayList<>();
                    vlist.add(paperVO.getPaper().getId() + "");
                    vlist.add(paperVO.getTeacher().getName());
                    switch (paperVO.getTeacher().getDegree()) {
                        case 1:
                            vlist.add("助教");
                            break;
                        case 2:
                            vlist.add("讲师");
                            break;
                        case 3:
                            vlist.add("副教授");
                            break;
                        case 4:
                            vlist.add("教授");
                            break;
                        case 5:
                            vlist.add("高级工程师");
                            break;
                        default:
                            vlist.add(paperVO.getTeacher().getDegreeName());
                            break;
                    }
                    vlist.add(paperVO.getDepartment().getName());
                    vlist.add(paperVO.getPaper().getTitle());
                    vlist.add(paperVO.getPaper().getRequires());
                    if (paperVO.getPaper().getMaxNumber() == paperVO.getPaper().getMinNumber()) {
                        vlist.add(paperVO.getPaper().getMinNumber() + "");
                    } else {
                        vlist.add(paperVO.getPaper().getMinNumber() + "-" + paperVO.getPaper().getMaxNumber());
                    }
                    vlist.add(paperVO.getPaper().getRemark());
                    data.add(vlist);
                }
                UserInfo collegeInfo = Constant.ServiceFacade.getUserInfoService().select(college.getId());
                String[] column = new String[]{"序号", "指导教师", "职称", "所在系（部门）", "毕业设计（论文）题目", "要求与说明", "需要学生数", "备注"};
                byte[] content = ExportUtil.exportExcel(data, column, collegeInfo.getName() + paperList.get(0).getCurrent() + "届毕业设计（论文）题目");
                response.setContentType("application/vnd.ms-excel;charset=gb18030");
                response.setHeader("Content-disposition", "inline; filename=paper" + DateUtil.getCurrentYM() + ".xls");
                response.setContentType("application/octet-stream;charset=UTF-8");
                OutputStream out = response.getOutputStream();
                out.write(content);
                out.flush();
                out.close();
                BaseResultUtil.setSuccess(result, null);
                return result;
            }
            return result;
        } catch (Exception e) {
            logger.error("导出选题excel表异常", e);
            return result;
        }
    }
}
