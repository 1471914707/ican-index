<!DOCTYPE HTML>
<html>
<head>
    <title>活动列表</title>
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
        .el-button+.el-button{
            margin-left: 0px;
        }
        .el-button--text {
            margin-right: 10px;
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
            <div class="header-left">
                <img src="${student.salt}">
                <div class="clearfix"> </div>
            </div>
            <div class="header-right" style="float: right;line-height: 68px;">
                <el-row>
                    <el-col :xs="0" :sm="15" :md="15" :lg="10" :xl="10">
                        <a href="/bk?id=${student.status?c}" target="_blank">
                            <img src="${student.phone}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 10%;display: inline-block"></a>
                        <a href="/bk?id=${student.role?c}" target="_blank">
                            <img src="${student.email}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 10%;display: inline-block"></a>
                        <a href="/bk?id=${student.id?c}" target="_blank">
                            <img src="${student.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 10%;display: inline-block"></a>
                    </el-col>
                    <el-col :xs="20" :sm="9" :md="8" :lg="10" :xl="9">
                        <a href="/student/edit" target="_blank">${student.name}</a> &nbsp;&nbsp; <a href="/message/my" target="_blank"> 消息</a> &nbsp;&nbsp; <a href="/logout">退出</a>
                    </el-col>
                </el-row>
            </div>
        </div>
        <div id="page-wrapper" style="width: 90%;">
            <div class="main-page">
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <h2>活动列表</h2>
                    </div>
                    <div class="panel panel-widget">
                        <template v-if="!loading">
                            <el-table
                                    :data="list"
                                    style="width: 100%"
                            >
                                <el-table-column
                                        style="max-width: 40px;"
                                        prop="id"
                                        label="序号"
                                        width="80">
                                </el-table-column>
                                <el-table-column
                                        label="发起人"
                                        width="150">
                                    <template slot-scope="scope">
                                        <a @click="openBk(college.id)" href="#" style="cursor: pointer;color: #409EFF">{{college.name}}</a>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                        prop="name"
                                        label="活动名称"
                                        width="200">
                                </el-table-column>
                                <el-table-column
                                        prop="current"
                                        label="对象(届)"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        prop="startTime"
                                        label="开始时间"
                                        width="150">
                                </el-table-column>
                                <el-table-column
                                        prop="endTime"
                                        label="结束时间"
                                        width="150">
                                </el-table-column>
                                <el-table-column
                                        fixed="right"
                                        label="操作"
                                        min-width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="small" @click="paper(scope.row.id)">我的选题</el-button>
                                        <el-button type="text" size="small" @click="project(scope.row.id,scope.row.userId)">我的项目</el-button>
                                        <el-button type="text" size="small" @click="task(scope.row.id)">计划安排</el-button>
                                        <el-button type="text" size="small" @click="fileArrange(scope.row.id)">共享文档</el-button>
                                        <el-button type="text" size="small" @click="arrange(scope.row.id, scope.row.userId)">流程查看</el-button>
                                        <el-button type="text" size="small" @click="rating(scope.row.id)">评审答辩</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </template>
                        <template v-if="loading">
                        <#include '/include/common/loading.ftl'>
                        </template>
                    </div>
                </div>
            </div>

            <div class="block-pagination">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-sizes="[10, 20, 30, 40]"
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
                            <a href="/">首页</a> > 活动列表
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        <#if student.id??>
        var studentId = ${student.id?c}
        <#else>
        var studentId = 0;
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    page:1,
                    size:20,
                    total:0,
                    list: [],
                    loading:false,
                    userList:[],
                    college:{}
                }
            },
            mounted: function () {
                this.loadActivityList();
            },
            methods:{
                loadActivityList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/student/activity/listJson',{
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                self.college = result.data.college;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].startTime = self.getDate(self.list[i].startTime);
                                    self.list[i].endTime = self.getDate(self.list[i].endTime);
                                    for (var j=0; j<self.userList.length; i++) {
                                        if (self.userList[j].id == self.list[i].userId) {
                                            self.list[i].userName = self.userList[j].name;
                                            break;
                                        }
                                    }
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
                openBk:function (id) {
                    window.open('/bk?id=' + id);
                },
                paper:function (id) {
                    var self = this;
                    Api.get('/student/paper/paperStatus',{
                        activityId:id
                    },function (result) {
                        if (result.code == 0) {
                                window.open('/student/paper/list?activityId=' + id);
                        }else {
                            self.$message({showClose: true, message: '选题暂未开放', type: 'error'});
                        }
                    });
                },
                project:function (id) {
                    window.open('/student/project/list?activityId=' + id);
                },
                task:function (id) {
                    window.open('/task/student/list?activityId=' + id);
                },
                arrange:function (id, collegeId) {
                    window.open('/arrange/list?activityId=' + id + '&collegeId=' + collegeId);
                },
                fileArrange:function (id) {
                    window.open('/file/list?activityId=' + id);
                },
                rating:function (id) {
                    window.open('/student/rating/list?activityId=' + id);
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadActivityList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadActivityList(this.page, this.size);
                }
            }
        });
    </script>
</div>
</body>
</html>