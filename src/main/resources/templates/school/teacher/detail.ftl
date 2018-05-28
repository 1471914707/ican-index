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
                            <a href="/school/teacher/list"><el-collapse-item title="教师情况" name="3">
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
            <div style="float: right;margin-right: 20px;">
                <div class="profile_details" style="margin-top: 10px;">
                    <a href="/bk?id=${school.id?c}" target="_blank">
                        <img src="${school.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 18%"></a>
                </div>
                <button id="showLeftPush" style="padding-top: 30px;">
                    <img  src="http://cdn.ican.com/public/images/bars.png" style="max-width:18.003px;max-height:23.333px;"></button>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper">
            <div class="main-page">
                <el-card class="grids">
                    <#--<div slot="header" class="clearfix progressbar-heading grids-heading">
                        <span>{{teacher.name}}</span>
                    </div>-->
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>头像：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                <div class="profile_img">
                                    <span class="prfil-img"><img :src="teacher.headshot" alt=""> </span>
                                    <div class="clearfix"></div>
                                </div>
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>姓名：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{teacher.name}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>电话：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{teacher.phone}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>邮箱：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{teacher.email}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>职称：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{teacher.degreeName}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>教师证件号：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{teacher.jobId}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>所在院系：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                <template v-for="(item, index) in departmentList">
                                    {{item.collegeVO.collegeName}} / {{item.name}} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                    <a @click="deleteDepartmentTeacher(item.id)" href="#" style="color: #409EFF">删除</a>
                                </template>
                            </div></el-col>
                        </el-row>
                </el-card>
            </div>
            <br><br>

            <el-card class="grids">
                <el-row>
                    <el-col style="margin-bottom: 10px;">认证图片：</el-col>
                    <el-col :span="8" v-for="(item, index) in authPhotoList" :offset="index > 0 ? 2 : 0">
                        <el-card :body-style="{ padding: '0px' }">
                            <img :src="item.url" class="image">
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
                            <a href="/">首页</a> > <a href="/school/teacher/list">教师：{{teacher.name}}</a>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <script type="text/javascript">
        <#if teacherId??>
        var teacherId = ${teacherId}
        <#else>
        var teacherId = 0;
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    teacher:{},
                    authPhotoList:[],
                    departmentList:[]
                }
            },
            watch:{
            },
            mounted: function () {
                this.loadTeacher();
            },
            methods:{
                loadTeacher:function () {
                    var self = this;
                    Api.get('/school/teacher/info',{
                        id:teacherId
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data) {
                                self.teacher = result.data.teacher;
                                self.teacher.degreeName = self.getDegreeName(self.teacher.degree, self.teacher.degreeName);
                                self.authPhotoList = result.data.authPhotoList;
                                self.departmentList = result.data.departmentList;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                deleteDepartmentTeacher:function (departmentId) {
                  var self = this;
                  Api.post("/school/teacher/deleteDepartmentTeacher",{
                      teacherId:teacherId,
                      departmentId:departmentId
                  },function (result) {
                      if (result.code == 0) {
                          for (var i=0; i<self.departmentList.length; i++) {
                              if (self.departmentList[i].id == departmentId) {
                                  self.departmentList.splice(i, 1);
                                  break;
                              }
                          }
                          self.$message({showClose: true, message: '删除成功', type: 'success'});
                      } else {
                          self.$message({showClose: true, message: result.msg, type: 'error'});
                      }
                  })
                },
                getDegreeName:function (degree,degreeName) {
                    switch (degree){
                        case 1:
                            return '助教';
                        case 2:
                            return '讲师';
                        case 3:
                            return '副教授';
                        case 4:
                            return '教授';
                        case 5:
                            return '高级工程师';
                        case 6:
                            return degreeName;
                    }
                },
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