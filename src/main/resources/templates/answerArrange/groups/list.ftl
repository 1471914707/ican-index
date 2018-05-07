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
        .title-groups{
            font-weight: bolder;
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
                <div class="grids">5

                        <template v-if="!loading">
                            <div class="panel panel-widget">
                                <el-button type="text" v-if="role==4" @click="edit();index1=-1">新增</el-button>
                            <template v-for="(item, index) in list">
                                <el-row>
                                    <el-col :span="5" class="col-border">
                                        <el-button style="float: right; padding: 12px 10px" type="text" v-if="role==4" @click="deleteGroups(index)">删除</el-button>
                                        <el-button style="float: right; padding: 12px 10px" type="text" v-if="role==4" @click="index1=index;edit();">编辑</el-button>
                                        <div class="col-div">编号：{{item.id}}</div>
                                        <div class="col-div">组名：<span class="title-groups">{{item.name}}</span></div>
                                        <div class="col-div">组长：{{item.userName}}</div>
                                        <div class="col-div">时间：{{item.ratingTime}}</div>
                                        <div class="col-div">地点：{{item.place}}</div>
                                    </el-col>
                                    <el-col :span="6" class="col-border">
                                        <div class="col-div">
                                            <span class="title-groups">小组成员：</span><el-button style="float: right; padding: 3px 0" type="text" v-if="role==4" @click="groupsId=item.id;openTeacherDialog();">增加</el-button></div>
                                        <div class="col-div">
                                            <template v-for="teacher in list[index].teacherList">
                                                <a @click="teacherRatingInfo(item.id, teacher.id)" v-if="role==4 || role==3">{{teacher.name}}</a>
                                                <span v-if="role==5">{{teacher.name}}</span>
                                                ({{teacher.departmentName}})
                                                <i class="el-icon-close" @click="deleteTeacher(item.id, teacher.id)" v-if="role==4"></i><br>
                                            </template>
                                        </div>
                                    </el-col>
                                    <el-col :span="13" class="col-border">
                                        <div class="col-div">
                                            <span class="title-groups">检查项目：</span><el-button style="float: right; padding: 3px 0" type="text" v-if="role==4" @click="projectDialog=true;groupsId=item.id">增加</el-button></div>
                                        <div class="col-div">
                                            <template v-for="project in list[index].projectList">
                                                <a @click="projectRatingInfo(item.id, project.id)" v-if="role==4 || role==3">{{project.title}}</a>
                                                <a @click="ratingProject(item.id, project.id);projectTitle='评价'+project.title;" v-if="role==5">{{project.title}}</a>
                                                ({{project.student.clazzName}}-{{project.student.name}}/指导教师：{{project.student.teacherName}})
                                                <i @click="deleteProject(item.id, project.id)" class="el-icon-close" v-if="role==4"></i><br>
                                            </template>
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                            </template>
                        </template>
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
            <template v-for="(item, index) in chooseTeacherList">
                <el-radio v-model="selectedTeacherId" :label="item.id" style="line-height: 50px;">
                    <img :src="item.headshot" style="width: 45px;height: 45px;border-radius: 50%">
                    <#--{{item.name}}({{item.departmentName}})-->
                    <span v-html="item.name"></span>
                    </el-radio>
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

        <el-dialog
                title="评价情况"
                :visible.sync="ratingDialog"
                width="60%">
                <el-table
                        :data="ratingList"
                        style="width: 100%">
                    <el-table-column
                            prop="teacher.name"
                            label="教师">
                    </el-table-column>
                    <el-table-column
                            prop="project.title"
                            label="项目">
                    </el-table-column>
                    <el-table-column
                            prop="gmtCreate"
                            label="时间">
                    </el-table-column>
                    <el-table-column
                            prop="score"
                            label="评分">
                    </el-table-column>
                    <el-table-column
                            prop="remark"
                            label="点评">
                    </el-table-column>
                </el-table>
                <span slot="footer" class="dialog-footer">
                      <el-button type="primary" @click="ratingDialog = false">关闭</el-button>
                </span>
        </el-dialog>

        <el-dialog
                :title="projectTitle"
                :visible.sync="teacherRatingDialog"
                width="40%"
                :before-close="handleClose">
            <el-form ref="form" :model="rating" label-width="40px">
                <el-form-item label="评分">
                    <el-input-number v-model="rating.score" :step="10" :min="0" :max="100"></el-input-number>
                </el-form-item>
                <el-form-item label="评价">
                    <el-input
                            type="textarea"
                            :rows="4"
                            placeholder="请输入内容"
                            v-model="rating.remark">
                    </el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="teacherRatingDialog = false">取 消</el-button>
                <el-button type="primary" @click="saveTeacherRating()">保 存</el-button>
            </span>
        </el-dialog>

        <el-dialog
                title="编辑小组"
                :visible.sync="editDialog"
                width="40%">
            <el-form ref="form" :model="groups" label-width="80px">
                <el-form-item label="小组名称">
                    <el-input v-model="groups.name"></el-input>
                </el-form-item>
                <el-form-item label="负责教师">
                    <el-select v-model="groups.userId" placeholder="请选择">
                        <el-option
                                v-for="item in teacherList"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="答辩时间">
                    <el-date-picker
                            v-model="groups.ratingTime"
                            type="datetime"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="选择日期时间">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="地点">
                    <el-input v-model="groups.place"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="saveGroups()">保存</el-button>
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
                    validProjectList:[],
                    ratingDialog:false,
                    ratingList:[],
                    rating:{id:0,answerId:answerId,groupsId:0,projectId:0,score:'',remark:''},
                    teacherRatingDialog:false,
                    projectTitle:'',
                    editDialog:false,
                    groups:{id:0,name:'',userId:null,ratingTime:'',place:'',answerId:answerId},
                    index1:-1,
                    chooseTeacherList:[]
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
                            for (var i=0; i<self.teacherList.length; i++) {
                                self.teacherList[i].isChoose = 0;
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
                                                    self.teacherList[k].isChoose = 1;
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
                                 self.teacherList[k].isChoose = 1;
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
                openTeacherDialog:function () {
                    this.chooseTeacherList = [];
                    for (var i=0; i<this.teacherList.length; i++) {
                        if (this.teacherList[i].isChoose != 1){
                            var teacher = JSON.parse(JSON.stringify(this.teacherList[i]));
                            teacher.name = "<span style=\"color: limegreen\">" + teacher.name + "(" + teacher.departmentName + ")</span>";
                            this.chooseTeacherList.push(teacher);
                        }
                    }
                    for (var i=0; i<this.teacherList.length; i++) {
                        if (this.teacherList[i].isChoose && this.teacherList[i].isChoose == 1){
                            var teacher = JSON.parse(JSON.stringify(this.teacherList[i]));
                            teacher.name = "<span style=\"color: orangered\">" + teacher.name + "(" + teacher.departmentName + ")</span>";
                            this.chooseTeacherList.push(teacher);
                        }
                    }
                    this.teacherDialog = true;
                },
                saveGroupsProject:function () {
                    var self = this;
                    Api.post("/answerArrange/groups/addProject",{groupsId:self.groupsId,projectId:self.selectedProjectId},
                            function (result) {
                                if (result.code == 0) {
                                    var i = 0;
                                    for (; i<self.list.length; i++) {
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
                teacherRatingInfo:function (groupsId, teacherId) {
                    var self = this;
                    Api.get("/answerArrange/groups/teacherRating",{groupsId:groupsId,teacherId:teacherId},function (result) {
                       if (result.code == 0) {
                           self.ratingList = result.data;
                           for (var i=0; i<self.ratingList.length; i++) {
                               self.ratingList[i].gmtCreate = self.getDate(self.ratingList[i].gmtCreate);
                               for (var j=0; j<self.projectList.length; j++) {
                                   if (self.ratingList[i].projectId == self.projectList[j].id){
                                       self.ratingList[i].project = JSON.parse(JSON.stringify(self.projectList[j]));
                                       break;
                                   }
                               }
                               for (var j=0; j<self.teacherList.length; j++) {
                                   if (self.ratingList[i].teacherId == self.teacherList[j].id) {
                                       self.ratingList[i].teacher = JSON.parse(JSON.stringify(self.teacherList[j]));
                                   }
                               }
                           }
                           self.ratingDialog = true;
                       } else {
                           self.$message({showClose: true, message: result.msg, type: 'error'});
                       }
                    });
                },
                projectRatingInfo:function (groupsId, projectId) {
                    var self = this;
                    Api.get("/answerArrange/groups/projectRating",{groupsId:groupsId,projectId:projectId},function (result) {
                        if (result.code == 0) {
                            self.ratingList = result.data;
                            for (var i=0; i<self.ratingList.length; i++) {
                                self.ratingList[i].gmtCreate = self.getDate(self.ratingList[i].gmtCreate);
                                for (var j=0; j<self.projectList.length; j++) {
                                    if (self.ratingList[i].projectId == self.projectList[j].id){
                                        self.ratingList[i].project = JSON.parse(JSON.stringify(self.projectList[j]));
                                        break;
                                    }
                                }
                                for (var j=0; j<self.teacherList.length; j++) {
                                    if (self.ratingList[i].teacherId == self.teacherList[j].id) {
                                        self.ratingList[i].teacher = JSON.parse(JSON.stringify(self.teacherList[j]));
                                    }
                                }
                            }
                            self.ratingDialog = true;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                ratingProject:function (groupsId, projectId) {
                    var self = this;
                    self.rating.groupsId = groupsId;
                    self.rating.projectId = projectId;
                    Api.get("/answerArrange/groups/teacher/rating/info",{groupsId:groupsId,projectId:projectId},function (result) {
                       if (result.code == 0) {
                           if (result.data.code == 0) {
                               self.rating = result.data.rating;
                           }
                           self.teacherRatingDialog = true;
                       } else {
                           self.$message({showClose: true, message: result.msg, type: 'error'});
                       }
                    });

                },
                saveTeacherRating:function () {
                  var self = this;
                  Api.post("/answerArrange/groups/teacher/rating/save",self.rating,function (result) {
                      if (result.code == 0) {
                          self.$message({showClose: true, message: '保存成功', type: 'success'});
                      } else {
                          self.$message({showClose: true, message: result.msg, type: 'error'});
                      }
                  });
                },
                edit:function () {
                    var index = parseInt(this.index1);
                    alert(index)
                    if (index < 0) {
                        this.groups = {id:0,name:'',userId:null,ratingTime:'',place:'',answerId:answerId};
                    } else {
                        this.groups.id = this.list[index].id;
                        this.groups.name = this.list[index].name;
                        this.groups.userId = this.list[index].userId;
                        this.groups.ratingTime = this.list[index].ratingTime;
                        this.groups.place = this.list[index].place;
                        this.groups.answerId = this.list[index].answerId;
                    }
                    this.editDialog = true;
                },
                saveGroups:function () {
                    var self = this;
                    Api.post("/answerArrange/groups/save",self.groups,function (result) {
                       if (result.code == 0) {
                           self.page = 1;
                           self.loadGroupsList();
                           self.editDialog = false;
                       } else {
                           self.$message({showClose: true, message: result.msg, type: 'error'});
                       }
                    });
                },
                deleteGroups:function (index) {
                    var self = this;
                    self.$confirm('此操作将永久删除该组此项目, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        var groupsId = self.list[index].id;
                        Api.post("/answerArrange/groups/delete",{groupsId:groupsId},function (result) {
                            if (result.code == 0) {
                                self.loadGroupsList();
                            } else {
                                self.$message({showClose: true, message: result.msg, type: 'error'});
                            }
                        });
                    }).catch(function () {
                        self.$message({type: 'info', message: '已取消删除'});
                    });
                },
                handleClose:function () {
                    this.teacherRatingDialog = false;
                    this.rating = {id: 0, answerId: answerId, groupsId: 0, projectId: 0, score: '', remark: ''};
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