package com.ican.controller.bk;
import com.ican.config.Constant;
import com.ican.domain.*;
import com.ican.service.UserInfoService;
import com.ican.util.BaseResult;
import com.ican.util.BaseResultUtil;
import com.ican.util.Ums;
import com.ican.vo.BkVO;
import com.ican.vo.BlogVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@Controller
@RequestMapping("/bk")
@Api("博客交流专区")
public class IndexBController {
    private final static Logger logger = LoggerFactory.getLogger(IndexBController.class);

    @RequestMapping(value = {"", "/", "/index", "/list"}, method = RequestMethod.GET)
    public String index(@RequestParam(value = "id", required = false, defaultValue = "0") String userId,
                        HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.valueOf(userId);
            UserInfo userInfo = null;
            if (id > 0) {
                userInfo = Constant.ServiceFacade.getUserInfoService().select(id);
            } else {
                userInfo = Ums.getUser(request);
            }
            if (userInfo == null) {
                return "redirect:/index";
            }
            BkVO bk = new BkVO();
            bk.setId(userInfo.getId());
            bk.setHeadshot(userInfo.getHeadshot());
            bk.setGmtCreate(userInfo.getGmtCreate().split(" ")[0]);
            bk.setName(userInfo.getName());
            bk.setRole(userInfo.getRole());
            switch (userInfo.getRole()) {
                case UserInfoService.USER_SCHOOL:
                    School school = Constant.ServiceFacade.getSchoolService().select(userInfo.getId());
                    bk.setUrl(school.getUrl());
                    bk.setPhone(school.getPhone());
                    bk.setEmail(school.getEmail());
                    bk.setRoleName("学校");
                    break;
                case UserInfoService.USER_COLLEGE:
                    College college = Constant.ServiceFacade.getCollegeService().select(userInfo.getId());
                    bk.setRoleName("二级学院");
                    bk.setEmail(college.getEmail());
                    bk.setPhone(college.getPhone());
                    bk.setUrl(college.getUrl());
                    break;
                case UserInfoService.USER_TEACHER:
                    Teacher teacher = Constant.ServiceFacade.getTeacherService().select(userInfo.getId());
                    switch (teacher.getDegree()) {
                        case 1:
                            bk.setRoleName("助教");
                            break;
                        case 2:
                            bk.setRoleName("讲师");
                            break;
                        case 3:
                            bk.setRoleName("副教授");
                            break;
                        case 4:
                            bk.setRoleName("教授");
                            break;
                        case 5:
                            bk.setRoleName("高级工程师");
                            break;
                        case 6:
                            bk.setRoleName(teacher.getDegreeName());
                            break;
                    }
                    break;
                case UserInfoService.USER_STUDENT:
                    Student student = Constant.ServiceFacade.getStudentService().select(userInfo.getId());
                    bk.setRoleName(student.getCurrent() + "届学生");
                    break;
            }
            int total = Constant.ServiceFacade.getBlogService().count(null, userInfo.getId(),0);
            bk.setTotal(total);
            request.setAttribute("bk", bk);
            request.setAttribute("id", userId);
            return "/bk/list";
        } catch (Exception e) {
            logger.error("跳转交流区异常", e);
            return "/index";
        }
    }

    @ApiOperation("信息列表获取")
    @RequestMapping(value = "/listJson", method = RequestMethod.GET)
    @ResponseBody
    public BaseResult listJson(@RequestParam(value = "id", defaultValue = "0", required = false) int id,
                               @RequestParam(value = "type", defaultValue = "1") int type,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "size", defaultValue = "20") int size,
                               HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            UserInfo userInfo = null;
            if (id > 0) {
                userInfo = Constant.ServiceFacade.getUserInfoService().select(id);
            } else {
                userInfo = Ums.getUser(request);
            }
            if (userInfo == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            List<Blog> blogList = new ArrayList<>();
            int total = 0;
            if (type == 1) {
                //本人的
                blogList = Constant.ServiceFacade.getBlogService().list(null, userInfo.getId(), 0, "id desc", page, size);
                total = Constant.ServiceFacade.getBlogService().count(null, userInfo.getId(), 0);
            } else {
                int schoolId = 0;
                switch (userInfo.getRole()) {
                    case UserInfoService.USER_SCHOOL:
                        schoolId = userInfo.getId();
                        break;
                    case UserInfoService.USER_COLLEGE:
                        College college = Constant.ServiceFacade.getCollegeService().select(userInfo.getId());
                        schoolId = college.getSchoolId();
                        break;
                    case UserInfoService.USER_TEACHER:
                        Teacher teacher = Constant.ServiceFacade.getTeacherService().select(userInfo.getId());
                        schoolId = teacher.getSchoolId();
                        break;
                    case UserInfoService.USER_STUDENT:
                        Student student = Constant.ServiceFacade.getStudentService().select(userInfo.getId());
                        schoolId = student.getSchoolId();
                        break;
                    default:
                        return result;
                }
                blogList = Constant.ServiceFacade.getBlogService().list(null, 0, schoolId, "id desc", page, size);
                total = Constant.ServiceFacade.getBlogService().count(null, 0, schoolId);
            }
            Set<String> userInfoSet = new HashSet<>();
            for (Blog blog : blogList) {
                userInfoSet.add(blog.getUserId() + "");
            }
            String userInfoIds = String.join(",", userInfoSet);
            List<UserInfo> userInfoList = Constant.ServiceFacade.getUserInfoService().list(userInfoIds, null, null, 0, null, 1, 100);
            Map userInfoMap = new HashMap();
            for (UserInfo user : userInfoList) {
                userInfoMap.put(user.getId(), user);
            }
            List<BlogVO> blogVOList = new ArrayList<>();
            for (Blog blog : blogList) {
                BlogVO blogVO = new BlogVO(blog);
                UserInfo user = (UserInfo) userInfoMap.get(blog.getUserId());
                blogVO.setName(user.getName());
                blogVO.setHeadshot(user.getHeadshot());
                blogVOList.add(blogVO);
            }
            Map data = new HashMap();
            data.put("list", blogVOList);
            data.put("total", total);
            BaseResultUtil.setSuccess(result, data);
            return result;
        } catch (Exception e) {
            logger.error("信息列表获取异常", e);
            return result;
        }
    }

    @ApiOperation("发布一条内容")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult add(@RequestParam(value = "content", defaultValue = "") String content,
                          @RequestParam(value = "image",defaultValue = "[]") String image,
                          HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        try {
            UserInfo self = Ums.getUser(request);
            if (self == null || StringUtils.isEmpty(content)) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            Blog blog = new Blog();
            blog.setUserId(self.getId());
            blog.setContent(content);
            blog.setImage(image);
            switch (self.getRole()) {
                case UserInfoService.USER_SCHOOL:
                    blog.setSchoolId(self.getId());
                    break;
                case UserInfoService.USER_COLLEGE:
                    College college = Constant.ServiceFacade.getCollegeService().select(self.getId());
                    blog.setSchoolId(college.getSchoolId());
                    break;
                case UserInfoService.USER_TEACHER:
                    Teacher teacher = Constant.ServiceFacade.getTeacherService().select(self.getId());
                    blog.setSchoolId(teacher.getSchoolId());
                    break;
                case UserInfoService.USER_STUDENT:
                    Student student = Constant.ServiceFacade.getStudentService().select(self.getId());
                    blog.setSchoolId(student.getSchoolId());
                    break;
                default:
                    return result;
            }
            Constant.ServiceFacade.getBlogService().save(blog);
            BaseResultUtil.setSuccess(result, blog);
            return result;
        } catch (Exception e) {
            logger.error("发布异常", e);
            return result;
        }
    }

    @ApiOperation("删除一条内容")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult delete(@RequestParam(value = "id",defaultValue = "0") int id,
                             HttpServletRequest request, HttpServletResponse response) {
        BaseResult result = BaseResultUtil.initResult();
        UserInfo self = Ums.getUser(request);
        if (self == null || id <= 0) {
            result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
            return result;
        }
        try {
            Blog blog = Constant.ServiceFacade.getBlogService().select(id);
            if (blog == null) {
                result.setMsg(BaseResultUtil.MSG_PARAMETER_ERROR);
                return result;
            }
            if (blog.getUserId() != self.getId()) {
                result.setMsg("不能删除别人发布的内容");
                return result;
            }
            Constant.ServiceFacade.getBlogService().delete(id);
            BaseResultUtil.setSuccess(result, null);
            return result;
        } catch (Exception e) {
            logger.error("删除内容异常", e);
            return result;
        }
    }
}
