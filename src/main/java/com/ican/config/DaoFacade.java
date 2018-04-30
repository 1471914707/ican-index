package com.ican.config;

import com.ican.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("DaoFacade")
public class DaoFacade {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private SchoolDao schoolDao;

    @Autowired
    private AuthPhotoDao authPhotoDao;

    @Autowired
    private CollegeDao collegeDao;

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private DepartmentTeacherDao departmentTeacherDao;

    @Autowired
    private ClazzDao clazzDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private FollowDao followDao;

    @Autowired
    private SchoolAppealDao schoolAppealDao;

    @Autowired
    private ActivityDao activityDao;

    @Autowired
    private PaperDao paperDao;

    @Autowired
    private PaperStudentDao paperStudentDao;

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private MajorDao majorDao;

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private ArrangeDao arrangeDao;

    @Autowired
    private AnswerArrangeDao answerArrangeDao;

    @Autowired
    private GroupsDao groupsDao;

    @Autowired
    private GroupsTeacherDao groupsTeacherDao;

    @Autowired
    private RatingDao ratingDao;

    @Autowired
    private CountsDao countsDao;

    public CountsDao getCountsDao() {
        return countsDao;
    }

    public void setCountsDao(CountsDao countsDao) {
        this.countsDao = countsDao;
    }

    public AnswerArrangeDao getAnswerArrangeDao() {
        return answerArrangeDao;
    }

    public void setAnswerArrangeDao(AnswerArrangeDao answerArrangeDao) {
        this.answerArrangeDao = answerArrangeDao;
    }

    public GroupsDao getGroupsDao() {
        return groupsDao;
    }

    public void setGroupsDao(GroupsDao groupsDao) {
        this.groupsDao = groupsDao;
    }

    public GroupsTeacherDao getGroupsTeacherDao() {
        return groupsTeacherDao;
    }

    public void setGroupsTeacherDao(GroupsTeacherDao groupsTeacherDao) {
        this.groupsTeacherDao = groupsTeacherDao;
    }

    public RatingDao getRatingDao() {
        return ratingDao;
    }

    public void setRatingDao(RatingDao ratingDao) {
        this.ratingDao = ratingDao;
    }

    public ArrangeDao getArrangeDao() {
        return arrangeDao;
    }

    public void setArrangeDao(ArrangeDao arrangeDao) {
        this.arrangeDao = arrangeDao;
    }

    public FileDao getFileDao() {
        return fileDao;
    }

    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public BlogDao getBlogDao() {
        return blogDao;
    }

    public void setBlogDao(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    public MajorDao getMajorDao() {
        return majorDao;
    }

    public void setMajorDao(MajorDao majorDao) {
        this.majorDao = majorDao;
    }

    public MessageDao getMessageDao() {
        return messageDao;
    }

    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public CityDao getCityDao() {
        return cityDao;
    }

    public void setCityDao(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    public UserInfoDao getUserInfoDao() {
        return userInfoDao;
    }

    public void setUserInfoDao(UserInfoDao userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    public AdminDao getAdminDao() {
        return adminDao;
    }

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public SchoolDao getSchoolDao() {
        return schoolDao;
    }

    public void setSchoolDao(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    public AuthPhotoDao getAuthPhotoDao() {
        return authPhotoDao;
    }

    public void setAuthPhotoDao(AuthPhotoDao authPhotoDao) {
        this.authPhotoDao = authPhotoDao;
    }

    public CollegeDao getCollegeDao() {
        return collegeDao;
    }

    public void setCollegeDao(CollegeDao collegeDao) {
        this.collegeDao = collegeDao;
    }

    public TeacherDao getTeacherDao() {
        return teacherDao;
    }

    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public DepartmentDao getDepartmentDao() {
        return departmentDao;
    }

    public void setDepartmentDao(DepartmentDao departmentDao) {
        this.departmentDao = departmentDao;
    }

    public DepartmentTeacherDao getDepartmentTeacherDao() {
        return departmentTeacherDao;
    }

    public void setDepartmentTeacherDao(DepartmentTeacherDao departmentTeacherDao) {
        this.departmentTeacherDao = departmentTeacherDao;
    }

    public ClazzDao getClazzDao() {
        return clazzDao;
    }

    public void setClazzDao(ClazzDao clazzDao) {
        this.clazzDao = clazzDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public FollowDao getFollowDao() {
        return followDao;
    }

    public void setFollowDao(FollowDao followDao) {
        this.followDao = followDao;
    }

    public SchoolAppealDao getSchoolAppealDao() {
        return schoolAppealDao;
    }

    public void setSchoolAppealDao(SchoolAppealDao schoolAppealDao) {
        this.schoolAppealDao = schoolAppealDao;
    }

    public ActivityDao getActivityDao() {
        return activityDao;
    }

    public void setActivityDao(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }

    public PaperDao getPaperDao() {
        return paperDao;
    }

    public void setPaperDao(PaperDao paperDao) {
        this.paperDao = paperDao;
    }

    public PaperStudentDao getPaperStudentDao() {
        return paperStudentDao;
    }

    public void setPaperStudentDao(PaperStudentDao paperStudentDao) {
        this.paperStudentDao = paperStudentDao;
    }

    public ProjectDao getProjectDao() {
        return projectDao;
    }

    public void setProjectDao(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }
}
