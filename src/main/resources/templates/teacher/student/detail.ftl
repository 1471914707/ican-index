<!DOCTYPE HTML>
<html>
<head>
    <title>学生详情</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
<#include '/include/cssjs_common_new.ftl'>
    <style>
        ul{
            list-style-type:none;
        }
        .el-carousel__mask, .el-cascader-menu, .el-cascader-menu__item.is-disabled:hover, .el-collapse-item__header, .el-collapse-item__wrap{
            background-color: #2a2f43;
            color: #fff;
            font-weight: 600;
            padding-left: 5%;
        }
        .clearfix:before,
        .clearfix:after {
            display: table;
            content: "";
        }
        .clearfix:after {
            clear: both
        }
        .el-row {
            padding: 10px;
        }
        .avatar-uploader .el-upload {
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
        }
        .avatar-uploader .el-upload:hover {
            border-color: #409EFF;
        }
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
            border-radius: 50%;
        }
        .radio label, .checkbox label, label{
            font-weight: 900;
            color:#000;
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">学生（{{student.name}}）情况</h2>
            <div class="header-right" style="float: right;margin-right: 50px;line-height: 68px;">
                <el-row>
                    <el-col :xs="0" :sm="4" :md="6" :lg="6" :xl="8">
                        <div style="width: 1px;height: 1px;"></div>
                    </el-col>
                    <el-col :xs="12" :sm="8" :md="8" :lg="6" :xl="8">
                        <a href="/bk?id=${teacher.id}" target="_blank">
                            <img src="${teacher.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 4%"></a>
                    </el-col>
                    <el-col :xs="0" :sm="8" :md="6" :lg="6" :xl="3">
                        <a href="/teacher/edit" target="_blank">个人资料</a>
                    </el-col>
                    <el-col :xs="0" :sm="4" :md="4" :lg="3" :xl="3">
                        <a href="/logout">退出</a>
                    </el-col>
                </el-row>
            </div>
        </div>
        <div id="page-wrapper">
            <div class="main-page">
                <el-card class="grids">
                    <div slot="header" class="clearfix grids-heading">
                        <div style="display: inline-block"><h2>{{student.name}}</h2></div>
                    </div>
                    <el-row>
                        <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                            <h3>头像：</h3>
                        </div></el-col>
                        <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                            <div class="profile_img">
                                <span class="prfil-img"><img :src="student.headshot" alt=""> </span>
                                <div class="clearfix"></div>
                            </div>
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                            <h3>姓名：</h3>
                        </div></el-col>
                        <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                            {{student.name}}
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                            <h3>学校：</h3>
                        </div></el-col>
                        <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                            {{student.schoolName}}
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                            <h3>二级学院：</h3>
                        </div></el-col>
                        <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                            {{student.collegeName}}
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                            <h3>系别：</h3>
                        </div></el-col>
                        <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                            {{student.departmentName}}
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                            <h3>年级（届）：</h3>
                        </div></el-col>
                        <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                            {{student.current}}
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                            <h3>班级：</h3>
                        </div></el-col>
                        <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                            {{student.clazzName}}
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                            <h3>学号：</h3>
                        </div></el-col>
                        <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                            {{student.jobId}}
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                            <h3>电话：</h3>
                        </div></el-col>
                        <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                            {{student.phone}}
                        </div></el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                            <h3>邮箱：</h3>
                        </div></el-col>
                        <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                            {{student.email}}
                        </div></el-col>
                    </el-row>
                </el-card>
            </div>
            <br><br>

            <template v-if="activityId > 0">
                <div class="main-page">
                    <el-card class="grids">
                        <div slot="header" class="clearfix grids-heading">
                            <h2>选题情况</h2>
                        </div>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>指导教师：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{student.teacherName}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>题目：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{paper.title}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>要求：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{paper.require}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>备注：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{paper.remark}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>人数：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{paper.minNumber}}-{{paper.maxNumber}}
                            </div></el-col>
                        </el-row>
                    </el-card>
                </div>
                <br><br>
            </template>

            <template v-if="activityId > 0">
                <div class="main-page">
                    <el-card class="grids">
                        <div slot="header" class="clearfix grids-heading">
                            <h2>项目情况</h2>
                        </div>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>指导教师：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{student.teacherName}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>题目：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{project.title}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>内容：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{project.content}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>条件：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{project.conditions}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>工作量：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{project.size}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>难度：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{project.difficulty}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>预计人数：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{project.number}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>进度：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{project.complete}}%
                            </div></el-col>
                        </el-row>
                    </el-card>
                </div>
                <br><br>
            </template>

            <el-card class="grids">
                <el-row>
                    <el-col style="margin-bottom: 10px;">认证图片：</el-col>
                    <el-col :span="8" v-for="(item, index) in authPhotoList" :offset="index > 0 ? 2 : 0">
                        <el-card :body-style="{ padding: '0px' }">
                            <img :src="item.url" style="max-width: 100%;height: auto;">
                        </el-card>
                    </el-col>
                </el-row>
            </el-card>

        </div>

    </div>

    <script type="text/javascript">
        <#if activityId??>
        var activityId = ${activityId}
        <#else>
        var activityId = 0;
        </#if>
        <#if studentId??>
        var studentId = ${studentId}
        <#else>
        var studentId = 0;
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    activityId:activityId,
                    student:{},
                    authPhotoList:[],
                    paper:{},
                    project:{}
                }
            },
            watch:{
            },
            mounted: function () {
                this.loadStudent();
                this.activityId = activityId;
            },
            methods:{
                loadStudent:function () {
                    var self = this;
                    Api.get('/teacher/student/info',{
                        id:studentId,
                        activityId:activityId
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data) {
                                self.student = result.data;
                                self.authPhotoList = result.data.authPhotoList;
                                if (activityId > 0) {
                                    if (result.data.project) {
                                        self.project = result.data.project;
                                    }
                                    if (result.data.paper) {
                                        self.paper = result.data.paper;
                                    }
                                }
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                }
            }
        });
    </script>
</div>
</body>
</html>