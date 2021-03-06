<!DOCTYPE HTML>
<html>
<head>
    <title>${school.schoolName} | 教师详情</title>
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
        <div class="sidebar" role="navigation">
            <div class="navbar-collapse">
                <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right dev-page-sidebar mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar" id="cbp-spmenu-s1">
                    <div>
                        <el-collapse>
                            <a href="/school/activity/list"><el-collapse-item title="活动列表" name="1">
                            </el-collapse-item></a>
                            <a href="/school/college/list"><el-collapse-item title="二级学院" name="2">
                            </el-collapse-item></a>
                            <a href="/school/student/list"><el-collapse-item title="教师情况" name="3">
                            </el-collapse-item></a>
                            <a href="/school/student/list"><el-collapse-item title="学生情况" name="4">
                            </el-collapse-item></a>
                            <el-collapse-item title="个人设置" name="5">
                                <div style="color: #409EFF;cursor: pointer">
                                    <div onclick="javascript:window.location.href='/school/edit'">个人资料</div>
                                    <div onclick="javascript:window.location.href='/resetPassword'">密码修改</div>
                                    <div onclick="javascript:window.location.href='/logout'">退出</div>
                                </div>
                            </el-collapse-item>
                        </el-collapse>
                    </div>
                </nav>
            </div>
        </div>
        <div class="sticky-header header-section ">
            <div class="header-left">
                <img src="${school.banner}" style="height: 84px;width: auto">
                <div class="clearfix"> </div>
            </div>
            <div class="header-right" style="float: right;margin-right: 50px;">
                <el-row>
                    <el-col :span="10">
                        <div style="width: 1px;height: 1px;"></div>
                    </el-col>
                    <el-col :span="4">
                        <a href="/bk?id=${schoolId}" target="_blank">
                            <img src="${school.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 18%"></a>
                    </el-col>
                    <el-col :span="4">
                        <a href="/bk?id=${college.id?c}" target="_blank">
                            <img src="${college.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 18%"></a>
                    </el-col>
                    <el-col :span="6">
                        <button id="showLeftPush" style="padding-top: 30px;float:right;">
                            <img  src="http://cdn.ican.com/public/images/bars.png" style="max-width:18.003px;max-height:23.333px;"></button>
                        <div class="clearfix"></div>
                    </el-col>
                </el-row>
            </div>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper">
            <div class="main-page">
                <el-card class="grids">
                    <div slot="header" class="clearfix grids-heading">
                        <div style="display: inline-block"><h2>{{student.name}}</h2></div>
                        <div style="display: inline-block;float: right;">
                            <a href="#" style="color: #409EFF" @click="deleteStudent()">删除</a></div>
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

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/">首页</a> > <a href="/school/student/list">学生列表</a> > 学生：{{student.name}}
                        </p>
                    </div>
                </div>
            </div>
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
                    Api.get('/college/student/info',{
                        id:studentId,
                        activityId:activityId
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data) {
                                self.student = result.data;
                                self.authPhotoList = result.data.authPhotoList;
                                if (activityId > 0) {
                                    self.project = result.data.project;
                                    self.paper = result.data.paper;
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
    <script>
        var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
                showLeftPush = document.getElementById( 'showLeftPush' ),
                body = document.body;

        showLeftPush.onclick = function() {
            classie.toggle( this, 'active' );
            classie.toggle( body, 'cbp-spmenu-push-toright' );
            classie.toggle( menuLeft, 'cbp-spmenu-open' );
            disableOther( 'showLeftPush' );
        };
        showLeftPush.click();
        function disableOther( button ) {
            if( button !== 'showLeftPush' ) {
                classie.toggle( showLeftPush, 'disabled' );
            }
        }
    </script>

</div>
</body>
</html>