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
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">毕业设计（论文）平台--教师工作台</h2>
            <div class="header-right" style="float: right;margin-right: 50px;line-height: 68px;">
                <el-row>
                    <el-col :xs="0" :sm="4" :md="6" :lg="6" :xl="8">
                        <div style="width: 1px;height: 1px;"></div>
                    </el-col>
                    <el-col :xs="12" :sm="8" :md="8" :lg="6" :xl="6">
                        <a href="/bk?id=${teacher.id?c}" target="_blank">
                            <img src="${teacher.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 10%"></a>
                    </el-col>
                    <el-col :xs="0" :sm="8" :md="6" :lg="8" :xl="6">
                        <a href="/teacher/edit" target="_blank">${teacher.name}</a> &nbsp;&nbsp; <a href="/message/my" target="_blank"> 私信</a>
                        <a href="/logout">退出</a>
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
                                        <a @click="openBk(scope.row.userId)" href="#" style="cursor: pointer;color: #409EFF">{{scope.row.userName}}</a>
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
                                        <el-button type="text" size="small" @click="student(scope.row.id,scope.row.userId)">学生情况</el-button>
                                        <el-button type="text" size="small" @click="paper(scope.row.id)">我的选题</el-button>
                                        <el-button type="text" size="small" @click="project(scope.row.id,scope.row.userId)">学生项目</el-button>
                                        <el-button type="text" size="small" @click="fileArrange(scope.row.id)">共享文档</el-button>
                                        <el-button type="text" size="small" @click="arrange(scope.row.id, scope.row.userId)">流程查看</el-button>
                                        <el-button type="text" size="small" @click="rating(scope.row.id)">评审答辩</el-button>
                                        <el-button type="text" size="small" @click="project(scope.row.id)">统计情况</el-button>
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
        <#if teacher.id??>
        var teacherId = ${teacher.id?c}
        <#else>
        var teacherId = 0;
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
                    userList:[]
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
                    Api.get('/teacher/activity/listJson',{
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                self.userList = result.data.userList;
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
                student:function (id,collegeId) {
                    window.open('/teacher/student/list?activityId=' + id + '&collegeId=' + collegeId);
                },
                paper:function (id) {
                    window.open('/teacher/paper/list?activityId=' + id);
                },
                project:function (id,collegeId) {
                    window.open('/teacher/project/list?activityId=' + id + '&collegeId=' + collegeId);
                },
                arrange:function (id) {
                    window.open('/arrange/list?activityId=' + id + '&teacherId=' + teacherId);
                },
                majorTeacher:function (id) {
                    window.open('/teacher/major/list?activityId=' + id);
                },
                fileArrange:function (id) {
                    window.open('/answerArrange/list?activityId=' + id);
                },
                rating:function (id) {
                    window.open('/answerArrange/list?activityId=' + id);
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