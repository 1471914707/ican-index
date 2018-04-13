package com.ican.config;

import com.ican.domain.School;
import com.ican.service.*;
import com.ican.webservice.PaperWebService;
import com.ican.webservice.ProjectWebService;
import com.ican.webservice.StudentWebService;
import com.ican.webservice.TeacherWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ServiceFacade")
public class ServiceFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private CityService cityService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private AuthPhotoService authPhotoService;

    @Autowired
    private CollegeService collegeService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentTeacherService departmentTeacherService;

    @Autowired
    private ClazzService clazzService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private FollowService followService;

    @Autowired
    private SchoolAppealService schoolAppealService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private PaperService paperService;

    @Autowired
    private PaperStudentService paperStudentService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MajorService majorService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ArrangeService arrangeService;

    @Autowired
    private GroupsService groupsService;

    @Autowired
    private GroupsTeacherService groupsTeacherService;

    @Autowired
    private RatingService ratingService;



    @Autowired
    private ProjectWebService projectWebService;

    @Autowired
    private StudentWebService studentWebService;

    @Autowired
    private PaperWebService paperWebService;

    @Autowired
    private TeacherWebService teacherWebService;


    public GroupsService getGroupsService() {
        return groupsService;
    }

    public void setGroupsService(GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    public GroupsTeacherService getGroupsTeacherService() {
        return groupsTeacherService;
    }

    public void setGroupsTeacherService(GroupsTeacherService groupsTeacherService) {
        this.groupsTeacherService = groupsTeacherService;
    }

    public RatingService getRatingService() {
        return ratingService;
    }

    public void setRatingService(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    public ArrangeService getArrangeService() {
        return arrangeService;
    }

    public void setArrangeService(ArrangeService arrangeService) {
        this.arrangeService = arrangeService;
    }

    public TeacherWebService getTeacherWebService() {
        return teacherWebService;
    }

    public void setTeacherWebService(TeacherWebService teacherWebService) {
        this.teacherWebService = teacherWebService;
    }

    public FileService getFileService() {
        return fileService;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public BlogService getBlogService() {
        return blogService;
    }

    public void setBlogService(BlogService blogService) {
        this.blogService = blogService;
    }

    public MajorService getMajorService() {
        return majorService;
    }

    public void setMajorService(MajorService majorService) {
        this.majorService = majorService;
    }

    public PaperWebService getPaperWebService() {
        return paperWebService;
    }

    public void setPaperWebService(PaperWebService paperWebService) {
        this.paperWebService = paperWebService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public SchoolService getSchoolService() {
        return schoolService;
    }

    public void setSchoolService(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public CityService getCityService() {
        return cityService;
    }

    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    public UserInfoService getUserInfoService() {
        return userInfoService;
    }

    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }

    public AuthPhotoService getAuthPhotoService() {
        return authPhotoService;
    }

    public void setAuthPhotoService(AuthPhotoService authPhotoService) {
        this.authPhotoService = authPhotoService;
    }

    public CollegeService getCollegeService() {
        return collegeService;
    }

    public void setCollegeService(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    public TeacherService getTeacherService() {
        return teacherService;
    }

    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public DepartmentTeacherService getDepartmentTeacherService() {
        return departmentTeacherService;
    }

    public void setDepartmentTeacherService(DepartmentTeacherService departmentTeacherService) {
        this.departmentTeacherService = departmentTeacherService;
    }

    public ClazzService getClazzService() {
        return clazzService;
    }

    public void setClazzService(ClazzService clazzService) {
        this.clazzService = clazzService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    public FollowService getFollowService() {
        return followService;
    }

    public void setFollowService(FollowService followService) {
        this.followService = followService;
    }

    public SchoolAppealService getSchoolAppealService() {
        return schoolAppealService;
    }

    public void setSchoolAppealService(SchoolAppealService schoolAppealService) {
        this.schoolAppealService = schoolAppealService;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    public PaperService getPaperService() {
        return paperService;
    }

    public void setPaperService(PaperService paperService) {
        this.paperService = paperService;
    }

    public PaperStudentService getPaperStudentService() {
        return paperStudentService;
    }

    public void setPaperStudentService(PaperStudentService paperStudentService) {
        this.paperStudentService = paperStudentService;
    }

    public ProjectService getProjectService() {
        return projectService;
    }

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public ProjectWebService getProjectWebService() {
        return projectWebService;
    }

    public void setProjectWebService(ProjectWebService projectWebService) {
        this.projectWebService = projectWebService;
    }

    public StudentWebService getStudentWebService() {
        return studentWebService;
    }

    public void setStudentWebService(StudentWebService studentWebService) {
        this.studentWebService = studentWebService;
    }
}
