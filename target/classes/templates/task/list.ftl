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
            <h1 style="margin-top: 12px;margin-left: 3%">项目任务安排情况<h1>
        </div>
        <div id="page-wrapper" style="width: 90%">
            <template v-if="projectId <= 0">
                您尚未申请项目。
            </template>
            <template v-if="projectId > 0">
                <el-row>
                    <el-col :span="24">
                    <div style="float: left"><h2>项目：{{title}}</h2></div>
                    <div style="float: right" style="width:30%"><el-button type="primary" @click="edit(0)">增加任务</el-button>
                        开启通知：
                        <el-switch
                                v-model="warn"
                                active-color="#13ce66"
                                inactive-color="#999"
                                @change="changeWarn()">
                        </el-switch>
                    </div>
                    </el-col>
                </el-row><br>
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
                                    <span v-html="scope.row.titleShow"></span>
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
                                    label="修改状态"
                                    width="120"
                                    :filters="[{text: '待进行', value: 1}, {text: '进行中', value: 2}, {text: '已完成', value: 3}]"
                                    :filter-method="filterHandler"
                                    :filter-multiple=false>
                                <template slot-scope="scope">
                                    <el-select v-model="scope.row.status" placeholder="请选择" @change="changeStatus(scope.row.id)">
                                        <el-option
                                                v-for="item in statusList"
                                                :key="item.value"
                                                :label="item.label"
                                                :value="item.value">
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    fixed="right"
                                    label="操作"
                                    width="120">
                                <template slot-scope="scope">
                                    <el-button type="text" @click="edit(scope.row.id)">编辑</el-button>
                                    <el-button type="text" @click="deleteTask(scope.row.id)">删除</el-button>
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

        <el-dialog
                :title="task.id>0?'编辑':'新增'"
                :visible.sync="editDialog"
                width="50%">
            <el-form ref="form" :model="task" label-width="80px">
                <el-form-item label="任务安排">
                    <el-input v-model="task.title"></el-input>
                </el-form-item>
                <el-form-item label="任务描述">
                    <el-input
                            type="textarea"
                            :rows="2"
                            placeholder="请输入内容"
                            v-model="task.content">
                    </el-input>
                </el-form-item>
                <el-form-item label="开始时间">
                    <el-date-picker
                            v-model="task.startTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            @change="startTimeChange"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="结束时间">
                    <el-date-picker
                            v-model="task.endTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            @change="startTimeChange"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveTask()">立即创建</el-button>
                    <el-button @click="editDialog=false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>

    <script>
        <#if activityId??>
        var activityId = ${activityId}
        <#else>
        var activityId = 0;
        </#if>
        <#if project.warn??>
        var warn = ${project.warn}
        <#else>
        var warn = 0;
        </#if>
        <#if project.id??>
        var projectId = ${project.id}
        <#else>
        var projectId = 0;
        </#if>
        <#if project.complete??>
        var complete = ${project.complete}
        <#else>
        var complete = 0;
        </#if>
        <#if project.title??>
        var title = '${project.title}'
        <#else>
        var title = 0;
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
                    editDialog: false,
                    task: {
                        id: 0,
                        activityId: activityId,
                        projectId:projectId,
                        title: '',
                        content: '',
                        startTime: '',
                        endTime: ''
                    },
                    warn:warn,
                    oldWarn:warn,
                    complete:complete,
                    projectId:projectId,
                    title:title,
                    status:0,
                    statusList:[{label:'待进行',value:1},{label:'进行中',value:2},{label:'已完成',value:3}],
                    oldStatusList:[]
                }
            },
            mounted: function () {
                this.loadTaskList();
                this.warn = this.toBoolean(this.oldWarn);
            },
            methods:{
                loadTaskList:function () {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/task/student/listJson',{
                        activityId:activityId,
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
                                self.oldStatusList.push({status: self.list[i].status});
                                self.list[i].titleShow = self.list[i].title;
                                if (self.list[i].status == 3){
                                    self.list[i].titleShow = "<del>" + self.list[i].title + "</del>";
                                }
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                edit:function (id) {
                    if (id > 0) {
                        for (var i=0; i<this.list.length; i++) {
                            if (this.list[i].id == id){
                                this.task = JSON.parse(JSON.stringify(this.list[i]));
                                break;
                            }
                        }
                    } else {
                        this.task = {id: 0, activityId: activityId, projectId:projectId, title: '', content: '', startTime: '', endTime: ''};
                    }
                    this.editDialog = true;
                },
                changeWarn:function () {
                    var self = this;
                    self.warn = self.warnsel;
                    Api.post("/task/student/warn",{activityId:activityId},function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: '修改成功', type: 'success'});
                            self.oldWarn = result.data;
                            self.warn = self.toBoolean(result.data);
                        } else {
                            self.$message({showClose: true, message: '修改失败', type: 'error'});
                            self.warn = self.toBoolean(self.oldWarn);
                        }
                    });
                },
                saveTask:function () {
                    var self = this;
                    Api.post("/task/student/save",self.task,function (result) {
                        if (result.code == 0) {
                            self.complete = result.data;
                            self.loadTaskList();
                            self.$message({showClose: true, message: '保存成功', type: 'success'});
                            self.editDialog = false;
                        } else {
                            self.$message({showClose: true, message: '保存失败', type: 'error'});
                        }
                    });
                },
                deleteTask:function (taskId) {
                    var self = this;
                    this.$confirm('此操作将永久删除该任务, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/task/delete",{taskId:taskId},function (result) {
                            if (result.code == 0) {
                                self.$message({showClose: true, message: '删除成功', type: 'success'});
                                for (var i=0; i<self.list.length; i++) {
                                    if (self.list[i].id == taskId) {
                                        self.list.splice(i, 1);
                                        self.oldStatusList.splice(i, 1);
                                    }
                                }
                                self.complete = result.data;
                            } else {
                                self.$message({showClose: true, message: '保存失败', type: 'error'});
                            }
                        });
                    }).catch(function () {
                        this.$message({type: 'info', message: '已取消删除'});
                    });
                },
                changeStatus:function (taskId) {
                    var self = this;
                    var status1 = 0;
                    var j = 0;
                    //查找改变的状态
                    for (var i=0; i<self.list.length; i++) {
                        if (self.list[i].id == taskId) {
                            status1 = self.list[i].status;
                            j = i;
                        }
                    }
                    Api.post("/task/student/status",{taskId:taskId,status:status1},function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: '更改成功', type: 'success'});
                            self.oldStatusList[j] = self.list[j].status;
                            self.complete = result.data;
                            if (status1 == 3){
                                for (var i=0; i<self.list.length; i++) {
                                    if (self.list[i].id == taskId){
                                        alert("ddd")
                                        self.list[i].titleShow = "<del>" + self.list[i].title + "</del>";
                                        break;
                                    }
                                }
                            } else {
                                for (var i=0; i<self.list.length; i++) {
                                    if (self.list[i].id == taskId){
                                        self.list[i].titleShow = self.list[i].title;
                                        break;
                                    }
                                }
                            }
                        } else {
                            self.$message({showClose: true, message: '保存失败', type: 'error'});
                            self.list[j].status = self.oldStatusList[j];
                        }
                    });
                },
                filterHandler:function(value, row, column) {
                    alert(value);
                    this.status = value;
                    this.loadTaskList();
                },
                toBoolean:function (warn) {
                  if (warn == 2 || warn){
                    return true;
                  } else {
                    return false;
                  }
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(".")[0];
                    }
                    return '';
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