package com.ican.controller.student;

import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.PaperVO;
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
import java.util.*;

@Api("学生")
@Controller
@RequestMapping("/student/paper")
public class PaperSTController {
    private final static Logger logger = LoggerFactory.getLogger(PaperSTController.class);

    @RequestMapping(value = {"", "/", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam("activityId") int activityId,
                        HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("activityId", activityId);
        return "/student/paper/list";
    }

    @ApiOperation("检查选题是否开放")
    @ResponseBody
    @RequestMapping(value = {"/paperStatus"}, method = RequestMethod.GET)
    public BaseResult paperStatus(@RequestParam("activityId") int activityId,
                                  HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (activity.getPaper() == 2){
                //已经开启
                BaseResultUtil.setSuccess(result, null);
                return result;
            }
        } catch (Exception e) {
            logger.error("检查选题是否开放异常", e);
            return result;
        }
        return result;
    }

    @ApiOperation("获取个人选题")
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult my(@RequestParam(value = "activityId",required = true) int activityId,
                         HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Student student = Constant.ServiceFacade.getStudentService().select(self.getId());
            if (activityId <= 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null || student.getCollegeId() != activity.getUserId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<PaperStudent> paperStudentList = Constant.ServiceFacade.getPaperStudentService().list(null, activityId, 0, 0, 0, 0, 0, 0, 0,
                    self.getId(), 0, null, 1, 1);
            Paper paper = new Paper();
            PaperVO paperVO = new PaperVO();
            if (paperStudentList != null && paperStudentList.size() > 0) {
                paper = Constant.ServiceFacade.getPaperService().select(paperStudentList.get(0).getPaperId());
                paperVO = Constant.ServiceFacade.getPaperWebService().getPaperVO(paper);
            }
            BaseResultUtil.setSuccess(result, paperVO);
            return result;
        } catch (Exception e) {
            logger.error("获取个人选题异常", e);
            return result;
        }
    }

    @ApiOperation("获取选题列表")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               @RequestParam(value = "title", defaultValue = "",required = false) String title,
                               @RequestParam(value = "activityId",required = true) int activityId,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        try {
            Student student = Constant.ServiceFacade.getStudentService().select(self.getId());
            if (activityId <= 0) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Activity activity = Constant.ServiceFacade.getActivityService().select(activityId);
            if (activity == null || student.getCollegeId() != activity.getUserId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }

            List<Paper> paperList = Constant.ServiceFacade.getPaperService().list(null, activityId, 0, 0, student.getCollegeId(), 0,
                    0, title, 0,"id desc", page, size);
            int total = Constant.ServiceFacade.getPaperService().count(null, activityId, 0, 0, student.getCollegeId(), 0, 0,
                    title,0);
            List<PaperVO> paperVOList = Constant.ServiceFacade.getPaperWebService().listVO(paperList);
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

    @ApiOperation("选择选题")
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult info(@RequestParam(value = "activityId", defaultValue = "0") int activityId,
                           @RequestParam(value = "paperId", defaultValue = "0") int paperId,
                           @RequestParam(value = "version", defaultValue = "0") int version,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (activityId <= 0 || paperId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            Paper paper = Constant.ServiceFacade.getPaperService().select(paperId);
            Student student = Constant.ServiceFacade.getStudentService().select(self.getId());
            Activity activity = Constant.ServiceFacade.getActivityService().select(paper.getActivityId());
            if (activity == null || paper == null || student == null || paper.getCollegeId() != student.getCollegeId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            int paperStudentTotal = Constant.ServiceFacade.getPaperStudentService().count(null, activityId, 0, 0, 0,
                    0, 0, 0, 0, student.getId(), paperId);
            if (paper.getMaxNumber() <= paperStudentTotal){
                result.setMsg("选题已满");
                return result;
            }
            if (version != paper.getVersion()) {
                result.setMsg("刚有同学已选，请刷新页面重新确定是否还可选");
                return result;
            }
            //更新版本
            paper.setVersion(version + 1);
            Constant.ServiceFacade.getPaperService().save(paper);
            //删除旧的关联
            List<PaperStudent> paperStudentList = Constant.ServiceFacade.getPaperStudentService().list(null, activityId, 0, 0, 0, 0, 0, 0,
                    0, self.getId(), 0, null, 1, 10);
            for (PaperStudent paperStudent : paperStudentList) {
                Constant.ServiceFacade.getPaperStudentService().delete(paperStudent.getId());
            }
            //保持关联
            PaperStudent paperStudent = new PaperStudent();
            paperStudent.setActivityId(activityId);
            paperStudent.setClazzId(student.getClazzId());
            paperStudent.setStudentId(self.getId());
            paperStudent.setCollegeId(student.getCollegeId());
            paperStudent.setCurrent(student.getCurrent());
            paperStudent.setPaperId(paperId);
            paperStudent.setMajorId(student.getMajorId());
            paperStudent.setSchoolId(student.getSchoolId());
            paperStudent.setTeacherId(paper.getTeacherId());
            paperStudent.setDepartmentId(student.getDepartmentId());
            student.setTeacherId(paper.getTeacherId());
            Constant.ServiceFacade.getStudentService().save(student);
            Constant.ServiceFacade.getPaperStudentService().save(paperStudent);

            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("选择选题异常", e);
            return result;
        }
    }

    @ApiOperation("删除关联选题")
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult info(@RequestParam(value = "paperStudentId", defaultValue = "0") int paperStudentId,
                           HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        if (paperStudentId <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        UserInfo self = Ums.getUser(request);
        try {
            PaperStudent paperStudent = Constant.ServiceFacade.getPaperStudentService().select(paperStudentId);
            if (paperStudent == null || paperStudent.getStudentId() != self.getId()) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Constant.ServiceFacade.getPaperStudentService().delete(paperStudentId);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除关联选题异常", e);
            return result;
        }
    }

}
