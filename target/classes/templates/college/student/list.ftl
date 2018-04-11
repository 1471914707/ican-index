<!DOCTYPE HTML>
<html>
<head>
    <title>${college.collegeName} | 学生列表</title>
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
                <div class="profile_details" style="margin-top: 5%">
                    <el-row>
                        <el-col :span="12" style="line-height: 60px"><span>学校：</span></el-col>
                        <el-col :span="10">
                            <a href="/bk?id=${schoolId}" target="_blank">
                                <img src="${school.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 18%"></a>
                        </el-col>
                    </el-row>
                </div>
                <button id="showLeftPush" style="padding-top: 30px;">
                    <img  src="http://cdn.ican.com/public/images/bars.png" style="max-width:18.003px;max-height:23.333px;"></button>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper">
            <div class="main-page">
                <!--grids-->
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <h2 style="display: inline-block">学生列表</h2>
                        <div style="float: right;display: inline-block;margin-right: 1.5%;margin-top: 1%">
                            <el-row>
                                <el-col :span="16" style="margin-right: 15px;"><el-input v-model="jobId" placeholder="请输入学号"></el-input></el-col>
                                <el-col :span="4"><el-button type="success" icon="el-icon-search" @click="loadStudentList()"></el-button></el-col>
                            </el-row>
                        </div>
                    </div>
                    <el-row v-if="!loading">
                        <div class="panel panel-widget">
                            <template v-if="!loading">
                                <el-table
                                        :data="list"
                                        style="width: 100%"
                                >
                                    <el-table-column
                                            style="max-width: 40px;"
                                            prop="jobId"
                                            label="学号"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="departmentName"
                                            label="系"
                                            width="180"
                                            :filters="departmentList"
                                            :filter-method="filterDepartmentHandler"
                                            :filter-multiple="false">
                                    </el-table-column>
                                    <el-table-column
                                            prop="majorName"
                                            label="专业"
                                            width="180"
                                            :filters="departmentList"
                                            :filter-method="filterDepartmentHandler"
                                            :filter-multiple="false">
                                    </el-table-column>
                                    <el-table-column
                                            prop="current"
                                            label="届"
                                            width="150"
                                            :filters="currentList"
                                            :filter-method="filterCurrentHandler"
                                            :filter-multiple="false">
                                    </el-table-column>
                                    <el-table-column
                                            prop="clazzName"
                                            label="班级"
                                            width="180"
                                            :filters="clazzList"
                                            :filter-method="filterClazzHandler"
                                            :filter-multiple="false">
                                    </el-table-column>
                                    <el-table-column
                                            prop="name"
                                            label="姓名"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="phone"
                                            label="电话"
                                            width="150">
                                    </el-table-column>
                                    <el-table-column
                                            prop="email"
                                            label="邮箱"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="gmtCreate"
                                            label="注册时间"
                                            width="150">
                                    </el-table-column>
                                    <el-table-column
                                            fixed="right"
                                            label="操作"
                                            min-width="150">
                                        <template slot-scope="scope">
                                            <el-button type="text" size="small" @click="detail(scope.row.id)">查看</el-button>
                                            <el-button type="text" size="small" @click="openMessageWindow(scope.row.id)">私信</el-button>
                                        </template>
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
                            <a href="/college">首页</a> > 学生列表
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
        <#if current??>
        var current = ${current}
        <#else>
        var current = 0;
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
                    degree:0,
                    jobId:'',
                    departmentId:0,
                    collegeId:0,
                    clazzId:0,
                    current:0,
                    collegeList:[],
                    departmentList:[],
                    currentList:[],
                    clazzList:[]
                }
            },
            mounted: function () {
                this.loadStudentList();
                this.loadCollegeList();
                for (var i=2018; i<2022; i++) {
                    this.currentList.push({text: i, value: i});
                }
            },
            methods:{
                detail:function (id) {
                    if (activityId > 0) {
                        window.open('/college/student/detail?activityId='+activityId+'&studentId=' + id);
                    } else {
                        window.open('/college/student/detail?studentId=' + id);
                    }
                },
                loadStudentList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/college/student/listJson',{
                        degree:self.degree,
                        jobId:self.jobId,
                        activityId:activityId,
                        collegeId:self.collegeId,
                        clazzId:self.clazzId,
                        departmentId:self.departmentId,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].gmtCreate = self.getDate(self.list[i].gmtCreate);
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                openMessageWindow:function (toId) {
                    window.open ('/message?toId='+toId, 'newwindow',
                            'height=600, width=400, top=150,left=500%, toolbar=no, menubar=no, scrollbars= no, resizable=no,location=true,status=no');
                },
                filterCollegeHandler:function (value, row, column) {
                    this.collegeId = value;
                    this.loadDepartmentList(value);
                    this.departmentId = 0;
                    this.clazzList = [];
                    this.clazzId = 0;
                    this.loadStudentList();
                },
                filterDepartmentHandler:function (value, row, column) {
                    this.departmentId = value;
                    this.clazzList = [];
                    this.loadStudentList();
                },
                filterCurrentHandler:function (value, row, column) {
                    this.current = value;
                    this.loadClazzList(this.departmentId,value);
                    this.clazzId = 0;
                    this.loadStudentList();
                },
                filterClazzHandler:function (value, row, column) {
                    this.clazzId = value;
                    this.loadStudentList();
                },
                loadCollegeList:function () {
                    var self = this;
                    Api.get("/collegeListJson",{},function (result) {
                        if (result.code == 0) {
                            self.collegeList = [];
                            var list = result.data;
                            for (var i=0; i<list.length; i++) {
                                self.collegeList.push({text: list[i].name, value: list[i].id});
                            }
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                loadDepartmentList:function (collegeId) {
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
                loadClazzList:function (departmentId, current) {
                    var self = this;
                    Api.get("/clazzListJson",{departmentId:departmentId,current:current},function (result) {
                        if (result.code == 0) {
                            self.clazzList = [];
                            var list = result.data;
                            for (var i=0; i<list.length; i++) {
                                self.clazzList.push({text: list[i].name, value: list[i].id});
                            }
                        }
                    });
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadStudentList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadStudentList(this.page, this.size);
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