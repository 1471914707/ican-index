<!DOCTYPE HTML>
<html>
<head>
    <title>我的项目</title>
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
            <h1 style="margin-top: 12px;margin-left: 3%">项目任务安排情况<h1>
        </div>
        <div id="page-wrapper" style="width: 90%">
            <template v-if="project.id <= 0">
                您尚未申请项目。
            </template>
            <template v-if="project.id > 0">
                <div>
                    <div>{{project.title}}</div>
                    <div>增加任务：开启通知：</div>
                </div>
            </template>
        </div>

    </div>

    <script>
        <#if activityId??>
        var activityId = ${activityId}
        <#else>
        var activityId = 0;
        </#if>
        <#if warn??>
        var warn = ${warn}
        <#else>
        var warn = 0;
        </#if>
        <#if project??>
        var project = ${project}
        <#else>
        var project = {};
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    page:1,
                    size:20,
                    total:0,
                    list: [],
                    oldList:[],
                    loading: false,
                    editDialog: false,
                    task: {
                        id: 0,
                        activityId: activityId,
                        title: '',
                        content: '',
                        startTime: '',
                        endTime: ''
                    },
                    project:project,
                    warn:warn,
                    oldWarn:warn,
                    projectId:projectId
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
                    Api.get('/task/student/listJson',{
                        activityId:activityId,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            self.list = result.data.list;
                            self.total = result.data.total;
                            self.oldList = result.data.list;
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                changeWarn:function () {
                    var self = this;
                    Api.post("/task/student/warn",{activityId:activityId},function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: '修改成功', type: 'error'});
                            self.oldWarn = result.data;
                            self.warn = result.data;
                        } else {
                            self.$message({showClose: true, message: '修改失败', type: 'error'});
                            self.warn = self.oldWarn;
                        }
                    });
                },
                saveTask:function () {
                    var self = this;
                    Api.post("/task/student/save",self.task,function (result) {
                        if (result.code == 0) {
                            self.loadTaskList();
                            self.$message({showClose: true, message: '保存成功', type: 'error'});
                            self.editDialog = false;
                        } else {
                            self.$message({showClose: true, message: '保存失败', type: 'error'});
                        }
                    });
                },
                changeStatus:function (taskId) {
                    var self = this;
                    var status = 0;
                    var j = 0;
                    //查找改变的状态
                    for (var i=0; i<self.oldList.length; i++) {
                        if (self.oldList[i].status != self.list[i].status) {
                            status = self.list[i].status;
                            j = i;
                        }
                    }
                    Api.post("/task/student/status",{taskId:taskId,status:status},function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: '更改成功', type: 'error'});
                            self.oldList[j].status = self.list[j].status;
                            self.project.complete = result.data;
                        } else {
                            self.$message({showClose: true, message: '保存失败', type: 'error'});
                            self.list[j].status = self.list[j].status;
                        }
                    });
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