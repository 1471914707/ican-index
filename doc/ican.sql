CREATE DATABASE IF NOT EXISTS `ican` DEFAULT CHARACTER SET utf8;
use ican;
set names utf8;
/*国家省份城市选择信息*/
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `parent_id` int(11) UNSIGNED NOT NULL COMMENT '所属于的上一级id',
  `name` varchar(150) NOT NULL COMMENT '地方名',
  `name_en` varchar(150) NOT NULL COMMENT '地方名英文',
  `first` varchar(5) NOT NULL COMMENT '首字母',
  `type` tinyint(2) UNSIGNED NOT NULL COMMENT '类型,0-未定义，1-国家,2-省份/州，3-城市',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='地区';
/*国家省份城市选择信息 end*/

/*基本角色*/
/*开启活动表*/
DROP TABLE IF EXISTS `activity`;
CREATE TABLE `activity` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) unsigned NOT NULL COMMENT '活动主办方,目前只支持学校',
  `name` varchar(100) NOT NULL COMMENT '活动名称',
  `current` int(11) unsigned NOT NULL COMMENT '活动对象',
  `start_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '预计开始时间',
  `end_time` datetime NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '预计结束时间',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动';

DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `headshot` varchar(150) NOT NULL COMMENT '头像(学校学院头像会显示在学生主页上,慎重上传)',
  `name` varchar(150) NOT NULL COMMENT '姓名',
  `sex` tinyint(2) UNSIGNED NOT NULL default '0' COMMENT '性别(1-男 2-女)',
  `password` varchar(128) NOT NULL COMMENT '加密后的密码',
  `salt` varchar(32) NOT NULL COMMENT '密码盐',
  `role` tinyint(2) UNSIGNED NOT NULL COMMENT '角色,0-未定义,1-超级管理员,2-普通管理员,3-学校账号,4-二级学院账号,5-导师账号,6-学生账号',
  `status` tinyint(2) UNSIGNED NOT NULL default '0' COMMENT '状态（0-初始化,1-生效,2-失效）',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='基本用户表';

DROP TABLE IF EXISTS `auth_photo`;
CREATE TABLE `auth_photo` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) UNSIGNED NOT NULL COMMENT '用户id',
  `url` varchar(150) NOT NULL COMMENT '认证照片路径',
  `remark` varchar(150) NOT NULL COMMENT '备注',
  `type` tinyint(2) UNSIGNED NOT NULL COMMENT '角色,0-未定义,1-超级管理员,2-普通管理员,3-学校账号,4-二级学院账号,5-导师账号,6-学生账号',
  `status` tinyint(2) UNSIGNED NOT NULL default '0' COMMENT '状态（0-初始化,1-通过,2-不通过）',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='认证照片表';

DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` int(11) UNSIGNED NOT NULL COMMENT '用户id',
  `phone` varchar(20) NOT NULL COMMENT '手机',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='管理员表';

DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `id` int(11) UNSIGNED NOT NULL COMMENT '用户id',
  `phone` varchar(20) NOT NULL COMMENT '手机',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `name` varchar(150) NOT NULL COMMENT '校名',
  `url` varchar(500) NOT NULL COMMENT '官网',
  `banner` varchar(500) NOT NULL COMMENT '横幅地址',
  `country` int(10) UNSIGNED NOT NULL COMMENT '国家',
  `province` int(10) UNSIGNED NOT NULL COMMENT '省份',
  `city` int(10) UNSIGNED NOT NULL COMMENT '城市',
  `address` varchar(500) NOT NULL COMMENT '地址',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='学校表';

/*跟进表*/
DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `follow_user_id` int(11) UNSIGNED NOT NULL COMMENT '跟进人id',
  `follow_user_name` varchar(20) NOT NULL COMMENT '跟进人姓名',
  `follow_id` int(11) UNSIGNED NOT NULL COMMENT '被跟进的id',
  `follow_type` tinyint(2) UNSIGNED NOT NULL COMMENT '被跟进类型,1-学校,2-申议',
  `mode` tinyint(2) UNSIGNED NOT NULL COMMENT '跟进方式,1-电话,2-QQ,3-微信,4-邮箱',
  `content` varchar(500) NOT NULL COMMENT '跟进内容',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_follow_user_id` (`follow_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='跟进记录表';

DROP TABLE IF EXISTS `college`;
CREATE TABLE `college` (
  `id` int(11) UNSIGNED NOT NULL COMMENT '用户id',
  `school_id` int(11) UNSIGNED NOT NULL COMMENT '学校id',
  `phone` varchar(20) NOT NULL COMMENT '手机',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `name` varchar(500) NOT NULL COMMENT '二级学院名称',
  `url` varchar(500) NOT NULL COMMENT '官网',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_school_id` (`school_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='二级学院';

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(11) UNSIGNED NOT NULL COMMENT '用户id',
  `phone` varchar(20) NOT NULL COMMENT '手机',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `job_id` varchar(50) NOT NULL COMMENT '工号',
  `degree` tinyint(2) NOT NULL COMMENT '职称,0-未填写,1-助教,2-讲师,3-副教授,4-教授,5-高级工程师,6-自定义',
  `degree_name` varchar(50) NOT NULL COMMENT '自定义职称',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='教师';

DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `school_id` int(11) UNSIGNED NOT NULL COMMENT '学校id',
  `college_id` int(11) UNSIGNED NOT NULL COMMENT '二级学院id',
  `name` varchar(150) NOT NULL COMMENT '系名',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_school_id` (`school_id`),
  KEY `idx_college_id` (`college_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系表';

DROP TABLE IF EXISTS `department_teacher`;
CREATE TABLE `department_teacher` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `department_id` int(11) UNSIGNED NOT NULL COMMENT '系id',
  `teacher_id` int(11) UNSIGNED NOT NULL COMMENT '教师id',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_college_id` (`college_id`),
  KEY `idx_department_id` (`department_id`),
  KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系-教师关联表';

DROP TABLE IF EXISTS `clazz`;
CREATE TABLE `clazz` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `school_id` int(11) UNSIGNED NOT NULL COMMENT '学校id',
  `college_id` int(11) UNSIGNED NOT NULL COMMENT '二级学院id',
  `department_id` int(11) UNSIGNED NOT NULL COMMENT '系id',
  `current` int(11) UNSIGNED NOT NULL COMMENT '多少届',
  `name` varchar(150) NOT NULL COMMENT '班名',
  `amount` tinyint(2) NOT NULL COMMENT '人数',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_school_id` (`school_id`),
  KEY `idx_college_id` (`college_id`),
  KEY `idx_department_id` (`department_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='班表';

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(11) UNSIGNED NOT NULL COMMENT '用户id',
  `school_id` int(11) UNSIGNED NOT NULL COMMENT '学校id',
  `college_id` int(11) UNSIGNED NOT NULL COMMENT '二级学院id',
  `department_id` int(11) UNSIGNED NOT NULL COMMENT '系id',
  `clazz_id` int(11) UNSIGNED NOT NULL COMMENT '班级id',
  `teacher_id` int(11) UNSIGNED NOT NULL COMMENT '最终导师id',
  `current` int(11) UNSIGNED NOT NULL COMMENT '多少届',
  `phone` varchar(20) NOT NULL COMMENT '手机',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `job_id` varchar(50) NOT NULL COMMENT '学号',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_school_id` (`school_id`),
  KEY `idx_college_id` (`college_id`),
  KEY `idx_department_id` (`department_id`),
  KEY `idx_clazz_id` (`clazz_id`),
  KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='学生';

DROP TABLE IF EXISTS `school_appeal`;
CREATE TABLE `school_appeal` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(150) NOT NULL COMMENT '姓名',
  `school_name` varchar(200) NOT NULL COMMENT '校名',
  `phone` varchar(20) NOT NULL COMMENT '手机',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `content` varchar(500) NOT NULL COMMENT '内容',
  `status` tinyint(2) UNSIGNED NOT NULL default '0' COMMENT '状态（0-初始化，1-提交成功，2-处理中，3-通过（通知），4-驳回）',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='学校申议表';

DROP TABLE IF EXISTS `follow`;
CREATE TABLE `follow` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `follow_user_id` int(11) UNSIGNED NOT NULL COMMENT '跟进人id',
  `follow_user_name` varchar(20) NOT NULL COMMENT '跟进人姓名',
  `follow_id` int(11) UNSIGNED NOT NULL COMMENT '被跟进的id',
  `follow_type` tinyint(2) UNSIGNED NOT NULL COMMENT '被跟进类型,1-学校,2-申议',
  `mode` tinyint(2) UNSIGNED NOT NULL COMMENT '跟进方式,1-电话,2-QQ,3-微信,4-邮箱',
  `content` varchar(1500) NOT NULL COMMENT '跟进内容',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_follow_user_id` (`follow_user_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='跟进记录表';

/*暂时用redis处理*/
DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `login_ticket` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) UNSIGNED NOT NULL COMMENT '用户id',
  `ticket` VARCHAR(45) NOT NULL COMMENT '登录票',
  `expired` DATETIME NOT NULL COMMENT '过期时间',
  `status` tinyint(2) NOT NULL DEFAULT 0 COMMENT '默认0,1-登录态,2-退出',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `ticket_UNIQUE` (`ticket` ASC)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*基本角色 end*/

/*选题模块*/
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `current` int(11) UNSIGNED NOT NULL COMMENT '多少届',
  `school_id` int(11) UNSIGNED NOT NULL COMMENT '学校id',
  `college_id` int(11) UNSIGNED NOT NULL COMMENT '二级学院id',
  `department_id` int(11) UNSIGNED NOT NULL COMMENT '系id',
  `clazz_id` int(11) UNSIGNED NOT NULL COMMENT '班级id',
  `teacher_id` int(11) UNSIGNED NOT NULL COMMENT '教师id',
  `title` varchar(300) NOT NULL COMMENT '题目',
  `require` varchar(500) NOT NULL COMMENT '要求与说明',
  `max_number` tinyint(2) UNSIGNED NOT NULL  default '0' COMMENT '需要最多学生数量',
  `min_number` tinyint(2) UNSIGNED NOT NULL  default '0' COMMENT '需要最少学生数量',
  `remark` varchar(500) NOT NULL COMMENT '备注',
  `status` tinyint(2) UNSIGNED NOT NULL  default '0' COMMENT '状态（0-初始化，1-提交成功,2-需要修改,3-选题中,4-已被选,5-全被选,6-进行中,7-结束,8-失效）',
  `version` int(11) UNSIGNED NOT NULL COMMENT '版本号，做乐观锁用',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_school_id` (`school_id`),
  KEY `idx_college_id` (`college_id`),
  KEY `idx_department_id` (`department_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='毕业设计选题表（自选课题，学生自己补充）';

DROP TABLE IF EXISTS `paper_student`;
CREATE TABLE `paper_student` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_id` int(11) UNSIGNED NOT NULL COMMENT '某活动',
  `current` int(11) UNSIGNED NOT NULL COMMENT '某届',
  `school_id` int(11) UNSIGNED NOT NULL COMMENT '学校id',
  `college_id` int(11) UNSIGNED NOT NULL COMMENT '二级学院id',
  `teacher_id` int(11) UNSIGNED NOT NULL COMMENT '教师id',
  `clazz_id` int(11) UNSIGNED NOT NULL COMMENT '班级id',
  `student_id` int(11) UNSIGNED NOT NULL COMMENT '学生id',
  `paper_id` int(11) UNSIGNED NOT NULL COMMENT '课题id',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_school_id` (`school_id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_paper_id` (`paper_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='毕业设计选题与学生关联表';

DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `activity_id` int(11) UNSIGNED NOT NULL COMMENT '多少届',
  `current` int(11) UNSIGNED NOT NULL COMMENT '多少届',
  `school_id` int(11) UNSIGNED NOT NULL COMMENT '学校id',
  `college_id` int(11) UNSIGNED NOT NULL COMMENT '二级学院id',
  `department_id` int(11) UNSIGNED NOT NULL COMMENT '系id',
  `clazz_id` int(11) UNSIGNED NOT NULL COMMENT '班级id',
  `teacher_id` int(11) UNSIGNED NOT NULL COMMENT '教师id',
  `student_id` int(11) UNSIGNED NOT NULL COMMENT '学生id',
  `title` varchar(300) NOT NULL COMMENT '题目',
  `content` varchar(2500) NOT NULL COMMENT '概述',
  `start_time` DATETIME NOT NULL COMMENT '开始时间',
  `end_time` DATETIME NOT NULL COMMENT '结束时间',
  `warn` tinyint(2) UNSIGNED NOT NULL  default '0' COMMENT '开启任务即将到期提醒？（0-初始化,1-不开,2-开)',
  `complete` tinyint(2) UNSIGNED NOT NULL  default '0' COMMENT '进度（0-初始化)',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_teacher_id` (`teacher_id`),
  KEY `idx_school_id` (`school_id`),
  KEY `idx_college_id` (`college_id`),
  KEY `idx_department_id` (`department_id`),
  KEY `idx_student_id` (`student_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='学生各自的项目';

#应该单独一个课题，让学生自己补充，叫什么好？
#paper(论文),project（项目）,task（任务）,item（一则）
/*选题模块 end*/

/*安排与指导模块*/
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `owner_id` int(11) UNSIGNED NOT NULL COMMENT '发起人id',
  `executor_id` int(11) UNSIGNED NOT NULL COMMENT '执行人id',
  `teacher_id` int(11) UNSIGNED NOT NULL COMMENT '教师id',
  `student_id` int(11) UNSIGNED NOT NULL COMMENT '学生id',
  `project_id` int(11) UNSIGNED NOT NULL COMMENT '课题id',
  `title` varchar(300) NOT NULL COMMENT '题目',
  `content` varchar(1500) NOT NULL COMMENT '概述',
  `start_time` DATETIME NOT NULL COMMENT '预计开始时间',
  `end_time` DATETIME NOT NULL COMMENT '预计结束时间',
  `accept` tinyint(2) UNSIGNED NOT NULL  default '0' COMMENT '教师验收是否通过（0-初始化,1-待验收,2-再修改,3-通过)',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_owner_id` (`owner_id`),
  KEY `idx_executor_id` (`executor_id`),
  KEY `idx_teacher_id` (`teahcer_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='安排发起的任务';

DROP TABLE IF EXISTS `file_arrange`;
CREATE TABLE `file_arrange` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) UNSIGNED NOT NULL COMMENT '发起人id',
  `activity_id` int(11) UNSIGNED NOT NULL COMMENT '针对的活动',
  `type` tinyint(2) UNSIGNED NOT NULL  default '0' COMMENT '针对类型要求（0-未知,1-超级管理员,2-管理员,3-学校,4-二级学院,5-导师,6-学生）',
  `name` varchar(300) NOT NULL COMMENT '名称（开题报告、任务书...）',
  `weight` tinyint(2) UNSIGNED NOT NULL COMMENT '权重,用于优先级排序',
  `start_time` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '开始时间',
  `end_time`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '截止时间',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_activity_id` (`activity_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='发起文件收集表';

DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) UNSIGNED NOT NULL COMMENT '保存人id',
  `entity_id` int(11) UNSIGNED NOT NULL COMMENT '针对的东西id',
  `entity_type` tinyint(2) UNSIGNED NOT NULL default '0' COMMENT '类型（0-未知,1-文件上传安排,2-博客,3-学校,4-二级学院,5-导师,6-学生,7-课题,8-task,,9-聊天文件）',
  `name` varchar(300) NOT NULL COMMENT '名称',
  `url` varchar(500) NOT NULL COMMENT '保存路径',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  INDEX `entity_index` (`entity_id` ASC, `entity_type` ASC)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='文件资源表';

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `content` varchar(1500) NOT NULL COMMENT '评论内容',
  `user_id` int(11) UNSIGNED NOT NULL NOT NULL COMMENT '评论人',
  `entity_id` int(11) UNSIGNED NOT NULL COMMENT '评论的对象id',
  `entity_type` tinyint(2) UNSIGNED NOT NULL  default '0' COMMENT '类型（0-未知，1-task，2-博客，3-回复）',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  INDEX `entity_index` (`entity_id` ASC, `entity_type` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通用评论表';

DROP TABLE IF EXISTS `comment_push`;
CREATE TABLE `comment_push` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `student_id` int(11) UNSIGNED NOT NULL COMMENT '学生id',
  `teacher_id` int(11) UNSIGNED NOT NULL NOT NULL COMMENT '导师id',
  `task_id` int(11) UNSIGNED NOT NULL NOT NULL COMMENT '任务id',
  `remark` varchar(500) NOT NULL COMMENT '备注',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_student_id` (`student_id`),
  KEY `idx_teacher_id` (`teacher_id`),
  INDEX `entity_index` (`entity_id` ASC, `entity_type` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='通用评论推送教师鉴定表';
/*安排与指导模块 end*/

/*站内交流与博客模块*/
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) UNSIGNED NOT NULL COMMENT '用户id',
  `title` varchar(150) NOT NULL COMMENT '标题',
  `content` text NOT NULL COMMENT '内容',
  `like_count` int(11) UNSIGNED NOT NULL COMMENT '点赞数量',
  `comment_count` int(11) UNSIGNED NOT NULL COMMENT '评论数量',
  `hits` int(11) UNSIGNED NOT NULL COMMENT '热度',
  `status` tinyint(2) UNSIGNED NOT NULL default '0' COMMENT '状态(0-初始化,1-生效,2-失效(封停))',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='文章博客';

DROP TABLE IF EXISTS `message`;
CREATE TABLE `message` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `from_id` int(11) UNSIGNED NOT NULL COMMENT '发送者id',
  `to_id` int(11) UNSIGNED NOT NULL COMMENT '接受者id',
  `content` text NOT NULL COMMENT '内容',
  `has_read` tinyint(2) UNSIGNED NOT NULL COMMENT '是否已读，0-初始化，1-未读,2-已读',
  `conversation_id` VARCHAR(45) NOT NULL COMMENT '对话id',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  INDEX `conversation_index` (`conversation_id` ASC),
  INDEX `gmt_create` (`gmt_create` ASC))
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天消息';
/*站内交流与博客模块 end*/

/*指导评分模块*/
/*还是保存在redis好了*/
DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `current` int(11) UNSIGNED NOT NULL COMMENT '多少届',
  `school_id` int(11) UNSIGNED NOT NULL COMMENT '学校id',
  `college_id` int(11) UNSIGNED NOT NULL COMMENT '二级学院id',
  `user_id` int(11) UNSIGNED NOT NULL COMMENT '负责人id',
  `name` VARCHAR(50) NOT NULL COMMENT '组名',
  `teacher_ids` VARCHAR(500) NOT NULL COMMENT '导师id组',
  `project_ids` VARCHAR(500) NOT NULL COMMENT '负责项目id组',
  `type` tinyint(2) UNSIGNED NOT NULL default '0' COMMENT '类型,0未定义,1-正式,2-非正式',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_school_id` (`school_id`),
  KEY `idx_college_id` (`college_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评分组';

DROP TABLE IF EXISTS `group_teacher`;
CREATE TABLE `group_teacher` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `teacher_id` VARCHAR(500) NOT NULL COMMENT '导师id',
  `group_id` VARCHAR(500) NOT NULL COMMENT '组id',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified`  DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_group_id` (`group_id`),
  KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评分组与教师关联表';

DROP TABLE IF EXISTS `rating`;
CREATE TABLE `rating` (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `current` int(11) UNSIGNED NOT NULL COMMENT '多少届',
  `school_id` int(11) UNSIGNED NOT NULL COMMENT '学校id',
  `college_id` int(11) UNSIGNED NOT NULL COMMENT '二级学院id',
  `project_id` int(11) UNSIGNED NOT NULL COMMENT '项目id',
  `teacher_id` int(11) UNSIGNED NOT NULL COMMENT '指导教师id',
  `ratio` tinyint(2) UNSIGNED NOT NULL COMMENT '比例',
  `score` tinyint(2) UNSIGNED NOT NULL COMMENT '得分',
  `remark` VARCHAR(500) NOT NULL COMMENT '建议',
  `type` tinyint(2) UNSIGNED NOT NULL default '0' COMMENT '类型,0未定义,1-正式,2-非正式',
  `gmt_create` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '增加时间',
  `gmt_modified` DATETIME NOT NULL DEFAULT '1970-01-01 00:00:00' COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_school_id` (`school_id`),
  KEY `idx_college_id` (`college_id`),
  KEY `idx_project_id` (`project_id`),
  KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评分';
/*指导评分模块 end*/

/*统计分析模块*/
/*统计分析模块 end*/
