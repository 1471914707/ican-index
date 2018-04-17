<!DOCTYPE HTML>
<html>
<head>
    <title>答辩分组情况</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
    <style>
        .col-border{
            border: solid #000000 1px;
            min-height:210px;
        }
        .col-div{
            text-align: left;
            padding-left:10px;
            padding-top: 10px;
            padding-bottom: 10px;
            padding-right: 10px;
        }
        body a {
            cursor: pointer;
            color: #409EFF;
        }
        i{
            cursor: pointer;
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">{{activity.name}}-{{answerArrange.name}}--分组情况</h2>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper" style="width:90%">
            <div class="main-page">
                <div class="grids">
                    <div class="panel panel-widget">
                        <template v-if="!loading">
                            <template v-for="(item, index) in list">
                                <el-row>
                                    <el-col :span="4" class="col-border">
                                        <div class="col-div">编号：{{item.id}}</div>
                                        <div class="col-div">组名：{{item.name}}</div>
                                        <div class="col-div">组长：{{item.userName}}</div>
                                        <div class="col-div">时间：{{item.ratingTime}}</div>
                                        <div class="col-div">地点：{{item.place}}</div>
                                    </el-col>
                                    <el-col :span="6" class="col-border">
                                        <div class="col-div">小组成员：<el-button style="float: right; padding: 3px 0" type="text" v-if="role==4" @click="teacherDialog=true;groupsId=item.id">增加</el-button></div>
                                        <div class="col-div">
                                            <template v-for="teacher in list[index].teacherList">
                                                <a @click="">{{teacher.name}}</a>({{teacher.departmentName}})<i class="el-icon-close" @click="deleteTeacher(item.id, teacher.id)"></i><br>
                                            </template>
                                        </div>
                                    </el-col>
                                    <el-col :span="12" class="col-border">
                                        <div class="col-div">检查项目：<el-button style="float: right; padding: 3px 0" type="text" v-if="role==4" @click="projectDialog=true;groupsId=item.id">增加</el-button></div>
                                        <div class="col-div">
                                            <template v-for="project in list[index].projectList">
                                                <a @click="">{{project.title}}</a>({{project.student.clazzName}}-{{project.student.name}})
                                                <i @click="deleteProject(item.id, project.id)" class="el-icon-close"></i><br>
                                            </template>
                                        </div>
                                    </el-col>
                                </el-row>
                            </template>
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
                            <a href="/">首页</a> > 答辩安排列表 > 分组情况
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <el-dialog
                title="选择教师"
                :visible.sync="teacherDialog"
                width="90%">
            <template v-for="(item, index) in teacherList">
                <el-radio v-model="selectedTeacherId" :label="item.id" style="line-height: 50px;">
                    <img :src="item.headshot" style="width: 45px;height: 45px;border-radius: 50%">
                    {{item.name}}({{item.departmentName}})</el-radio>
            </template>
            <span slot="footer" class="dialog-footer">
                <el-button @click="teacherDialog = false">取 消</el-button>
                <el-button type="primary" @click="saveGroupsTeacher()">确 定</el-button>
           </span>
        </el-dialog>
        <el-dialog
                title="选择项目"
                :visible.sync="projectDialog"
                width="90%">
            <template v-for="(item, index) in validProjectList">
                <el-radio v-model="selectedProjectId" :label="item.id" style="line-height: 50px;">
                    {{item.title}}({{item.student.clazzName}}-{{item.student.name}})</el-radio>
            </template>
            <span slot="footer" class="dialog-footer">
                <el-button @click="projectDialog = false">取 消</el-button>
                <el-button type="primary" @click="saveGroupsProject()">确 定</el-button>
           </span>
        </el-dialog>
    </div>

    <script>
        <#if activityId??>
        var activityId = ${activityId}
        <#else>
        var activityId = 0;
        </#if>
        <#if answerId??>
        var answerId = ${answerId}
        <#else>
        var answerId = 0;
        </#if>
        <#if role??>
        var role = ${role}
        <#else>
        var role = 0;
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    page:1,
                    size:20,
                    total:0,
                    studentList:[],
                    majorList:[],
                    departmentList:[],
                    teacherList:[],
                    projectList:[],
                    clazzList:[],
                    list: [],
                    loading:false,
                    role:role,
                    activity:{},
                    answerArrange:{},
                    teacherDialog:false,
                    projectDialog:false,
                    groupsId:0,
                    projectId:0,
                    selectedTeacherId:0,
                    selectedProjectId:0,
                    validProjectList:[]
                }
            },
            mounted: function () {
                this.loadInfoList();
            },
            methods:{
                loadInfoList:function () {
                    var self = this;
                    Api.get("/answerArrange/groups/infoListJson",{activityId:activityId},function (result) {
                        if (result.code == 0) {
                            self.studentList = result.data.studentList;
                            self.projectList = result.data.projectList;
                            self.departmentList = result.data.departmentList;
                            self.majorList = result.data.majorList;
                            self.teacherList = result.data.teacherList;
                            self.clazzList = result.data.clazzList;
                            //项目关联学生
                            for (var i=0; i<self.projectList.length; i++) {
                                for (var j=0; j<self.studentList.length; j++) {
                                    if (self.projectList[i].studentId == self.studentList[j].id){
                                        self.projectList[i].student = self.studentList[j];
                                    }
                                }
                            }
                            self.loadGroupsList();
                        } else {
                            self.$message({showClose: true, message: '系统出错，请重新刷新', type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                loadGroupsList:function () {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/answerArrange/groups/listJson',{
                        answerId:answerId,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                self.activity = result.data.activity;
                                self.answerArrange = result.data.answerArrange;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].ratingTime = self.getDate(self.list[i].ratingTime);
                                    //小组负责人
                                    for (var j=0; j<self.teacherList.length; j++){
                                        if (self.teacherList[j].id == self.list[i].userId){
                                            self.list[i].userName = self.teacherList[j].name;
                                        }
                                    }
                                    //各组成员
                                    var teacherIdList = self.list[i].teacherIds.split(",");
                                    self.list[i].teacherList = [];
                                    if (teacherIdList.length > 0) {
                                        for (var z=0; z<teacherIdList.length; z++) {
                                            for (var k=0; k<self.teacherList.length; k++) {
                                                if (teacherIdList[z] == self.teacherList[k].id) {
                                                    self.list[i].teacherList.push({id:teacherIdList[z],name:self.teacherList[k].name,departmentName:self.teacherList[k].departmentName});
                                                   break;
                                                }
                                            }
                                        }
                                    }
                                    //负责项目
                                    var projectIdList = self.list[i].projectIds.split(",");
                                    self.list[i].projectList = [];
                                    if (projectIdList.length > 0) {
                                        for (var z=0; z<projectIdList.length; z++) {
                                            for (var k=0; k<self.projectList.length; k++) {
                                                if (projectIdList[z] == self.projectList[k].id) {
                                                    self.list[i].projectList.push({id:projectIdList[z],title:self.projectList[k].title,student:self.projectList[k].student});
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                //还没被选择的项目才能出现在validProjectList上
                                if (result.data.projectIds) {
                                    var projectIds = result.data.projectIds.split(",");
                                    self.validProjectList = JSON.parse(JSON.stringify(self.projectList));
                                    for (var j=0; j<projectIds.length-1; j++) {
                                        for (var i=0; i<self.validProjectList.length; i++) {
                                            if (projectIds[j] == self.validProjectList[i].id){
                                                self.validProjectList.splice(i, 1);
                                            }
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
                saveGroupsTeacher:function () {
                  var self = this;
                  Api.post("/answerArrange/groups/addTeacher",{groupsId:self.groupsId,teacherId:self.selectedTeacherId},
                  function (result) {
                     if (result.code == 0) {
                         var i = 0;
                         for (; i<self.list[i].length; i++) {
                             if (self.list[i].id == self.groupsId) {
                                 break;
                             }
                         }
                         for (var k=0; k<self.teacherList.length; k++) {
                             if (self.selectedTeacherId == self.teacherList[k].id) {
                                 self.list[i].teacherList.push({id:self.selectedTeacherId,name:self.teacherList[k].name,departmentName:self.teacherList[k].departmentName});
                                 break;
                             }
                         }
                         self.$message({showClose: true, message: '选择成功', type: 'success'});
                         self.teacherDialog = false;
                     } else {
                         self.$message({showClose: true, message: result.msg, type: 'error'});
                     }
                  });
                },
                saveGroupsProject:function () {
                    var self = this;
                    Api.post("/answerArrange/groups/addProject",{groupsId:self.groupsId,projectId:self.selectedProjectId},
                            function (result) {
                                if (result.code == 0) {
                                    var i = 0;
                                    for (; i<self.list[i].length; i++) {
                                        if (self.list[i].id == self.groupsId) {
                                            break;
                                        }
                                    }
                                    for (var k=0; k<self.projectList.length; k++) {
                                        if (self.selectedProjectId == self.projectList[k].id) {
                                            self.list[i].projectList.push({id:self.selectedProjectId,title:self.projectList[k].title,student:self.projectList[k].student});
                                            break;
                                        }
                                    }
                                    //减少可选的项目
                                    for (var j=0; j<self.validProjectList.length; i++) {
                                        if (self.selectedProjectId == self.validProjectList[j].id) {
                                            self.validProjectList.splice(j, 1);
                                            break;
                                        }
                                    }
                                    self.selectedProjectId = null;
                                    self.$message({showClose: true, message: '选择成功', type: 'success'});
                                    self.projectDialog = false;
                                } else {
                                    self.$message({showClose: true, message: result.msg, type: 'error'});
                                }
                            });
                },
                deleteTeacher:function (groupsId, teacherId) {
                    var self = this;
                    self.$confirm('此操作将永久删除该组此教师, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/answerArrange/groups/deleteTeacher",{groupsId:groupsId,teacherId:teacherId},
                            function (result) {
                                if (result.code == 0) {
                                    for (var i=0; i<self.list.length; i++) {
                                        if (groupsId == self.list[i].id) {
                                            for (var j=0; j<self.list[i].teacherList.length; j++) {
                                                if (teacherId == self.list[i].teacherList[j].id) {
                                                    self.list[i].teacherList.splice(j, 1);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    self.loadGroupsList();
                                    self.$message({showClose: true, message: '删除成功', type: 'success'});
                                } else {
                                    self.$message({showClose: true, message: result.msg, type: 'error'});
                                }
                        });
                    }).catch(function () {
                        self.$message({type: 'info', message: '已取消删除'});
                    });
                },
                deleteProject:function (groupsId, projectId) {
                    var self = this;
                    self.$confirm('此操作将永久删除该组此项目, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/answerArrange/groups/deleteProject",{groupsId:groupsId,projectId:projectId},
                            function (result) {
                                if (result.code == 0) {
                                    for (var i=0; i<self.list.length; i++) {
                                        if (groupsId == self.list[i].id) {
                                            for (var j=0; j<self.list[i].projectList.length; j++) {
                                                if (projectId == self.list[i].projectList[j].id) {
                                                    self.list[i].projectList.splice(j, 1);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    for (var i=0; i<self.projectList.length; i++) {
                                        if (self.projectList[i].id == projectId) {
                                            self.validProjectList.push(JSON.parse(JSON.stringify(self.projectList[i])));
                                            break;
                                        }
                                    }
                                    self.$message({showClose: true, message: '删除成功', type: 'success'});
                                } else {
                                    self.$message({showClose: true, message: result.msg, type: 'error'});
                                }
                        });
                    }).catch(function () {
                        self.$message({type: 'info', message: '已取消删除'});
                    });
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(".")[0];
                    }
                    return '';
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadGroupsList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadGroupsList(this.page, this.size);
                }
            }
        });
    </script>
</div>
</body>
</html>