<!DOCTYPE HTML>
<html>
<head>
    <title>项目计划</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
<#include '/include/cssjs_common_new.ftl'>
    <style>

    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section" style="height: 68px;">
            <h1 style="margin-top: 12px;margin-left: 3%">{{name}}--{{title}}<h1>
        </div>
        <div id="page-wrapper" style="width: 90%">
            <template v-if="projectId <= 0">
                该学生尚未申请项目。
            </template>
            <template v-if="projectId > 0">
                <el-row>
                    <el-col :span="24">
                        <template v-if="total < 10">
                            当任务数量少于十条的时候不会显示进度
                        </template>
                        <template v-if="total >= 10">
                            <el-progress
                                    :text-inside="true"
                                    :show-text="true"
                                    :stroke-width="22"
                                    :percentage="complete"
                                    status="success"></el-progress>
                        </template>
                    </el-col>
                </el-row><br>
                <div>
                    <template>
                        <el-table
                                :data="list"
                                style="width: 100%">
                            <el-table-column type="expand">
                                <template slot-scope="props">
                                    {{props.row.content}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="标题">
                                <template slot-scope="scope">
                                    <span v-html="scope.row.title"></span>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    label="开始时间"
                                    prop="startTime"
                                    width="150">
                            </el-table-column>
                            <el-table-column
                                    label="结束时间"
                                    prop="endTime"
                                    width="150">
                            </el-table-column>
                            <el-table-column
                                    fixed="right"
                                    label="状态"
                                    width="120"
                                    :filters="[{text: '待进行', value: 1}, {text: '进行中', value: 2}, {text: '已完成', value: 3}]"
                                    :filter-method="filterHandler"
                                    :filter-multiple=false>
                                <template slot-scope="scope">
                                    <span v-html="scope.row.status"></span>
                                </template>
                            </el-table-column>
                        </el-table>
                    </template>

                </div>
            </template>
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

    <script>
        <#if student.id??>
        var studentId = ${student.id?c}
        <#else>
        var studentId = 0;
        </#if>
        <#if student.name??>
        var name = '${student.name}'
        <#else>
        var name = 0;
        </#if>
        <#if project.id??>
        var projectId = ${project.id?c}
        <#else>
        var projectId = 0;
        </#if>
        <#if project.title??>
        var title = '${project.title}'
        <#else>
        var title = 0;
        </#if>
        <#if project.complete??>
        var complete = ${project.complete}
        <#else>
        var complete = 0;
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    page:1,
                    size:20,
                    total:0,
                    list: [],
                    loading: false,
                    name:name,
                    complete:complete,
                    projectId:projectId,
                    title:title,
                    status:0
                }
            },
            mounted: function () {
                this.loadTaskList();
            },
            methods:{
                loadTaskList:function () {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/task/listJson',{
                        projectId:projectId,
                        status:self.status,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            self.list = result.data.list;
                            self.total = result.data.total;
                            for (var i=0; i<self.list.length; i++) {
                                self.list[i].startTime = self.getDate(self.list[i].startTime);
                                self.list[i].endTime = self.getDate(self.list[i].endTime);
                                if (self.list[i].status == 3) {
                                    self.list[i].title = "<del>" + self.list[i].title + "</del>";
                                }
                                self.list[i].status = self.getStatusName(self.list[i].status);
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(".")[0];
                    }
                    return '';
                },
                filterHandler:function(value, row, column) {
                    alert(value);
                    this.status = value;
                    this.loadTaskList();
                },
                getStatusName:function (status) {
                    if (status == 1) {
                        return "<span style=\"color: red;\">待进行</span>";
                    }
                    if (status == 2) {
                        return "<span style=\"color: green;\">进行中</span>";
                    }
                    if (status == 3) {
                        return '<i class="el-icon-check"></i>';
                    }
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadTaskList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadTaskList(this.page, this.size);
                }
            }
        });
    </script>
</div>
</body>
</html>