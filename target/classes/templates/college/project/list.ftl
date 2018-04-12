<!DOCTYPE HTML>
<html>
<head>
    <title>${school.schoolName} | 选题列表</title>
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
        .el-form-item__label{
            font-weight: bolder;
        }
        .el-form-item{
            width:33%;
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <!--left-fixed -navigation-->
        <div class="sidebar" role="navigation">
            <div class="navbar-collapse">
                <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right dev-page-sidebar mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar" id="cbp-spmenu-s1">
                    <div>
                        <el-collapse>
                            <a href="/college/activity/list"><el-collapse-item title="活动列表" name="1">
                            </el-collapse-item></a>
                            <a href="/college/college/list"><el-collapse-item title="二级学院" name="2">
                            </el-collapse-item></a>
                            <a href="/college/teacher/list"><el-collapse-item title="教师情况" name="3">
                            </el-collapse-item></a>
                            <a href="/college/student/list"><el-collapse-item title="学生情况" name="4">
                            </el-collapse-item></a>
                            <el-collapse-item title="个人设置" name="5">
                                <div style="color: #409EFF;cursor: pointer">
                                    <div onclick="javascript:window.location.href='/school/edit'">个人资料</div>
                                    <div onclick="javascript:window.location.href='/password'">密码修改</div>
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
                <img src="${school.banner}">
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
                <!--grids-->
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <div style="float: right;display: inline-block;margin-right: 1.5%;margin-top: 1%">
                            <el-row>
                                <el-col :span="16" style="margin-right: 15px;"><el-input v-model="title" placeholder="请输入标题"></el-input></el-col>
                                <el-col :span="4"><el-button type="success" icon="el-icon-search" @click="loadProjectList()"></el-button></el-col>
                            </el-row>
                        </div><br/>
                        <h2 style="display: inline-block;text-align: center">${school.schoolName}毕业设计（论文）-学生项目汇总</h2>
                        <h2 style="display: inline-block;text-align: center">（{{current}}届）</h2>
                    </div>
                    <el-row v-if="!loading">
                        <div class="panel panel-widget">
                            <template v-if="!loading">
                                <el-table
                                        :data="list"
                                        style="width: 100%">
                                    <el-table-column type="expand">
                                        <template slot-scope="props">
                                            <el-form label-position="left" inline class="demo-table-expand">
                                                <el-form-item label="学生姓名">
                                                    <span>{{ props.row.student.name }}({{ props.row.student.jobId }})</span>
                                                </el-form-item>
                                                <el-form-item label="所属专业">
                                                    <span>{{ props.row.collegeName }}-{{ props.row.majorName }}</span>
                                                </el-form-item>
                                                <br/>
                                                <el-form-item label="项目名称">
                                                    <span>{{ props.row.project.title }}</span>
                                                </el-form-item>
                                                <el-form-item label="申报时间">
                                                    <span>{{ props.row.project.gmtCreate }}</span>
                                                </el-form-item>
                                                <el-form-item label="来源课题">
                                                    <span>{{ props.row.paper.title }}</span>
                                                </el-form-item>
                                                <br>
                                                <el-form-item label="指导教师">
                                                    <span>{{ props.row.teacher.name }}</span>
                                                </el-form-item>
                                                <el-form-item label="教师职称">
                                                    <span>{{ props.row.teacher.degreeName }}</span>
                                                </el-form-item>
                                                <el-form-item label="导师联系电话">
                                                    <span>{{ props.row.teacher.phone }}</span>
                                                </el-form-item>
                                                <el-form-item label="导师联系邮箱">
                                                    <span>{{ props.row.teacher.email }}</span>
                                                </el-form-item>

                                            </el-form>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                            label="序号"
                                            prop="project.id">
                                    </el-table-column>
                                    <el-table-column
                                            label="姓名"
                                            prop="student.name">
                                    </el-table-column>
                                    <el-table-column
                                            label="所在系"
                                            prop="departmentName"
                                            :filters="departmentList"
                                            :filter-method="filterDepartmentHandler"
                                            :filter-multiple="false">
                                    </el-table-column>
                                    <el-table-column
                                            label="专业"
                                            prop="majorName"
                                            :filters="majorList"
                                            :filter-method="filterMajorHandler"
                                            :filter-multiple="false">
                                    </el-table-column>
                                    <el-table-column
                                            label="班级"
                                            prop="clazzName"
                                            :filters="clazzList"
                                            :filter-method="filterClazzHandler"
                                            :filter-multiple="false">
                                    </el-table-column>
                                    <el-table-column
                                            label="导师"
                                            prop="teacher.name"
                                            :filters="teacherList"
                                            :filter-method="filterTeacherHandler"
                                            :filter-multiple="false">
                                    </el-table-column>
                                    <el-table-column
                                            label="题目"
                                            prop="project.title">
                                    </el-table-column>
                                    <el-table-column
                                            label="工作量"
                                            prop="project.size">
                                    </el-table-column>
                                    <el-table-column
                                            label="难度"
                                            prop="project.difficulty">
                                    </el-table-column>
                                    <el-table-column
                                            label="预计人数"
                                            prop="project.number">
                                    </el-table-column>
                                </el-table>
                            </template>

                            <template v-if="loading">
                            <#include '/include/common/loading.ftl'>
                            </template>
                        </div>
                    </el-row>
                    <template v-if="loading">
                    <#include '/include/common/loading.ftl'>
                    </template>
                </div>
            </div>

            <div class="block-pagination">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-sizes="[20, 30, 40, 50]"
                        :page-size="size"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </div>

        </div>

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/school">首页</a> > 项目列表
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <script>
        <#if activityId??>
        var activityId = ${activityId}
        <#else>
        var activityId = 0;
        </#if>
        <#if college.id??>
        var collegeId = ${college.id?c}
        <#else>
        var collegeId = 0;
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    slogan:'全站最热门广告位招租！！！有意者电话联系18813960106！！！',
                    page:1,
                    size:20,
                    total:0,
                    list: [],
                    loading:false,
                    teacherId:0,
                    departmentId:0,
                    collegeId:0,
                    majorId:0,
                    clazzId:0,
                    title:'',
                    departmentList:[],
                    majorList:[],
                    clazzList:[],
                    teacherList:[],
                    current:'--',
                }
            },
            mounted: function () {
                this.loadProjectList();
                this.loadDepartmentList();
                this.loadTeacherList();
            },
            methods:{
                loadProjectList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/college/project/listJson',{
                        activityId:activityId,
                        title:self.title,
                        departmentId:self.departmentId,
                        majorId:self.majorId,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].project.gmtCreate = self.list[i].project.gmtCreate.split(" ")[0];
                                    self.list[i].teacher.degreeName = self.getDegreeName(self.list[i].teacher.degree, self.list[i].teacher.degreeName);
                                }
                                if (self.list.length > 0) {
                                    self.current = self.list[0].project.current;
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
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
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                filterDepartmentHandler:function (value, row, column) {
                    this.departmentId = value;
                    this.majorList = [];
                    this.loadMajorList(value);
                    this.loadProjectList();
                },
                filterMajorHandler:function (value, row, column) {
                    this.majorId = value;
                    this.clazzList = [];
                    this.loadClazzList(value);
                    this.loadProjectList();
                },
                filterTeacherHandler:function (value, row, column) {
                    this.teacherId = value;
                    this.loadProjectList();
                },
                filterClazzHandler:function (value, row, column) {
                    this.clazzId = value;
                    this.loadProjectList();
                },
                loadDepartmentList:function () {
                    var self = this;
                    Api.get("/departmentListJson",{collegeId:collegeId},function (result) {
                        if (result.code == 0) {
                            self.departmentList = [];
                            var list = result.data;
                            for (var i=0; i<list.length; i++) {
                                self.departmentList.push({text: list[i].name, value: list[i].id});
                            }
                        }
                    });
                },
                loadMajorList:function (id) {
                    var self = this;
                    Api.get("/majorListJson",{departmentId:id},function (result) {
                        if (result.code == 0) {
                            self.majorList = [];
                            var list = result.data;
                            for (var i=0; i<list.length; i++) {
                                self.majorList.push({text: list[i].name, value: list[i].id});
                            }
                        }
                    });
                },
                loadClazzList:function (id) {
                    var self = this;
                    Api.get("/clazzListJson",{departmentId:0,majorId:id,current:0},function (result) {
                        if (result.code == 0) {
                            self.clazzList = [];
                            var list = result.data;
                            for (var i=0; i<list.length; i++) {
                                self.clazzList.push({text: list[i].name, value: list[i].id});
                            }
                        }
                    });
                },
                loadTeacherList:function () {
                    var self = this;
                    Api.get("/teacherListJson",{},function (result) {
                        if (result.code == 0) {
                            self.teacherList = [];
                            var list = result.data.list;
                            for (var i=0; i<list.length; i++) {
                                self.teacherList.push({text: list[i].name, value: list[i].id});
                            }
                        }
                    });
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadProjectList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadProjectList(this.page, this.size);
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