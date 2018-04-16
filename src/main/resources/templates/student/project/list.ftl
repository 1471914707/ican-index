<!DOCTYPE HTML>
<html>
<head>
    <title>我的项目</title>
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
        .text {
            font-size: 14px;
        }

        .item {
            margin-bottom: 18px;
        }

        .clearfix:before,
        .clearfix:after {
            display: table;
            content: "";
        }
        .clearfix:after {
            clear: both
        }

        .box-card {
            width: 480px;
        }
        .project-title{
            font-weight: bolder;
        }
        .project-row{
            margin-bottom: 20px;
        }
        @media screen and (max-width: 900px) {
            .project-teacher {
                display: none;
            }
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section" style="height: 68px;">
            <h1 style="margin-top: 12px;margin-left: 3%">项目情况<h1>
        </div>
        <div id="page-wrapper" style="width: 90%">
            <#--还没有选题-->
            <template v-if="code == 1">
                {{msg}}
            </template>
                <#--还没申报项目-->
            <template v-if="code == 2">
                <el-form ref="form" :model="project" label-width="80px">
                    <el-form-item label="项目题目">
                        <el-input v-model="project.title"></el-input>
                    </el-form-item>
                    <el-form-item label="项目概述">
                        <el-input
                                type="textarea"
                                :autosize="{ minRows: 10, maxRows: 20}"
                                placeholder="请输入内容"
                                v-model="project.content">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="项目条件">
                        <el-input
                                type="textarea"
                                :autosize="{ minRows: 10, maxRows: 20}"
                                placeholder="请输入内容"
                                v-model="project.conditions">
                        </el-input>
                    </el-form-item>
                    <el-form-item label="工作量">
                        <template>
                            <el-radio v-model="project.size" label="大">大</el-radio>
                            <el-radio v-model="project.size" label="适中">适中</el-radio>
                            <el-radio v-model="project.size" label="小">小</el-radio>
                        </template>
                    </el-form-item>
                    <el-form-item label="难度">
                        <template>
                            <el-radio v-model="project.difficulty" label="难">难</el-radio>
                            <el-radio v-model="project.difficulty" label="一般">一般</el-radio>
                            <el-radio v-model="project.difficulty" label="易">易</el-radio>
                        </template>
                    </el-form-item>
                    <el-form-item label="预计人数">
                           <el-input-number v-model="project.num" :min="1" :max="100"></el-input-number>
                    </el-form-item>
                    <el-form-item label="开始时间">
                        <el-date-picker
                                v-model="project.startTime"
                                type="date"
                                value-format="yyyy-MM-dd"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item label="结束时间">
                        <el-date-picker
                                v-model="project.endTime"
                                type="date"
                                value-format="yyyy-MM-dd"
                                placeholder="选择日期">
                        </el-date-picker>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="saveProject()">立即保存</el-button>
                        <el-button @click="cancelProject()">取消</el-button>
                    </el-form-item>
                </el-form>
            </template>
                <#--已经有了项目,但有通过和未通过的区分-->
            <template v-if="code == 3">
                <el-card class="box-card" style="width:90%;">
                    <div slot="header" class="clearfix">
                        <span class="project-title">{{project3.project.title}}</span>
                        <el-button style="float: right; padding: 3px 0" type="text" v-if="editFlag==1" @click="code=2;loadProject();">编辑</el-button>
                    </div>
                    <div class="text item">
                        <el-row>
                            <el-col :span="6" class="project-teacher">
                                <el-row class="project-row">
                                    <el-col :span="6" class="project-title">指导教师：</el-col>
                                    <el-col :span="10"><a style="cursor: pointer;" @click="openBk(project3.teacher.id)">{{project3.teacher.name}}</a></el-col>
                                </el-row>
                                <el-row class="project-row">
                                    <el-col :span="6" class="project-title">手机号码：</el-col>
                                    <el-col :span="10">{{project3.teacher.phone}}</el-col>
                                </el-row>
                                <el-row class="project-row">
                                    <el-col :span="6" class="project-title">电子邮箱：</el-col>
                                    <el-col :span="10">{{project3.teacher.email}}</el-col>
                                </el-row>
                            </el-col>
                            <el-col :span="18">
                                <el-row class="project-row" :gutter="10">
                                    <el-col :xs="0" :sm="6" :md="6" :lg="3" :xl="2" class="project-title">项目题目：</el-col>
                                    <el-col :xs="24" :sm="18" :md="18" :lg="18" :xl="18">{{project3.project.title}}</el-col>
                                </el-row>
                                <el-row class="project-row" :gutter="10">
                                    <el-col :xs="0" :sm="6" :md="6" :lg="3" :xl="2" class="project-title">项目概述：</el-col>
                                    <el-col :xs="24" :sm="18" :md="18" :lg="18" :xl="18">{{project3.project.content}}</el-col>
                                </el-row>
                                <el-row class="project-row" :gutter="10">
                                    <el-col :xs="0" :sm="6" :md="6" :lg="3" :xl="2" class="project-title">项目条件：</el-col>
                                    <el-col :xs="24" :sm="18" :md="18" :lg="18" :xl="18">{{project3.project.conditions}}</el-col>
                                </el-row>
                                <el-row class="project-row" :gutter="10">
                                    <el-col :xs="0" :sm="6" :md="6" :lg="3" :xl="2" class="project-title">工作量：</el-col>
                                    <el-col :xs="24" :sm="18" :md="18" :lg="18" :xl="18">{{project3.project.size}}</el-col>
                                </el-row>
                                <el-row class="project-row" :gutter="10">
                                    <el-col :xs="0" :sm="6" :md="6" :lg="3" :xl="2" class="project-title">项目难度：</el-col>
                                    <el-col :xs="24" :sm="18" :md="18" :lg="18" :xl="18">{{project3.project.difficulty}}</el-col>
                                </el-row>
                                <el-row class="project-row" :gutter="10">
                                    <el-col :xs="0" :sm="6" :md="6" :lg="3" :xl="2" class="project-title">预计人数：</el-col>
                                    <el-col :xs="24" :sm="18" :md="18" :lg="18" :xl="18">{{project3.project.num}}</el-col>
                                </el-row>
                                <el-row class="project-row" :gutter="10">
                                    <el-col :xs="0" :sm="6" :md="6" :lg="3" :xl="2" class="project-title">预计时间：</el-col>
                                    <el-col :xs="24" :sm="18" :md="18" :lg="18" :xl="18">{{getDate(project3.project.startTime)}}~~{{getDate(project3.project.endTime)}}</el-col>
                                </el-row>
                            </el-col>
                        </el-row>
                    </div>
                </el-card><br>
                <el-card class="box-card" style="width: 90%">
                    <div slot="header" class="clearfix">
                        <span>审核意见</span>
                    </div>
                    <template>
                        <el-table
                                :data="followList"
                                style="width: 100%">
                            <el-table-column
                                    prop="followUserName"
                                    label="审核人"
                                    width="180">
                            </el-table-column>
                            <el-table-column
                                    prop="gmtCreate"
                                    label="时间"
                                    width="180">
                            </el-table-column>
                            <el-table-column
                                    prop="content"
                                    label="建议内容"
                                    width="180">
                            </el-table-column>
                            <el-table-column
                                    prop="mode"
                                    label="意见">
                            </el-table-column>
                        </el-table>
                    </template>
                </el-card>
            </template>
        </div>

    </div>

    <script>
        <#if activityId??>
        var activityId = ${activityId}
        <#else>
        var activityId = 0;
        </#if>
        <#if msg??>
        var msg = '${msg}'
        <#else>
        var msg = 0;
        </#if>
        <#if code??>
        var code = ${code}
        <#else>
        var code = 0;
        </#if>
        <#if editFlag??>
        var editFlag = ${editFlag}
        <#else>
        var editFlag = 0;
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    loading: false,
                    editDialog: false,
                    project: {
                        id: 0,
                        activityId: activityId,
                        title: '',
                        content: '',
                        conditions: '',
                        size: '',
                        difficulty: '',
                        num: 1,
                        startTime: '',
                        endTime: ''
                    },
                    code:code,
                    followList:[],
                    activity:{},
                    msg:msg,
                    editFlag:editFlag,
                    project3:{}
                }
            },
            mounted: function () {
                if (code == 3){
                    this.loadProject();
                }
                if (code == 2){
                    this.loadPaper();
                }
            },
            methods:{
                loadPaper:function () {
                    var self = this;
                    self.loading = true;
                    Api.get('/student/paper/my',{
                        activityId:activityId
                    },function (result) {
                        if (result.code == 0) {
                            self.paper = result.data;
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                loadProject:function () {
                    var self = this;
                    self.loading = true;
                    Api.get('/student/project/info',{
                        activityId:activityId
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.project) {
                                self.project3 = result.data.project;
                                self.followList = result.data.followList;
                                for (var i=0; i<self.followList.length; i++) {
                                    self.followList[i].gmtCreate = self.getDate(self.followList[i].gmtCreate);
                                    if (self.followList[i].mode == 2){
                                        self.followList[i].mode = '通过';
                                    } else if (self.followList[i].mode == 1) {
                                        self.followList[i].mode = '不通过';
                                    } else {
                                        self.followList[i].mode = '建议';
                                    }
                                }
                                if (self.code == 2) {
                                    self.project = result.data.project.project;
                                }
                            }
                            self.activity = result.data.activity;
                            self.loading = false;
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                saveProject:function () {
                    var self = this;
                    Api.post('/student/project/save',self.project,function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: '保存成功', type: 'success'});
                            location.reload();
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                cancelProject:function () {
                    location.reload();
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                openBk:function (id) {
                    window.open('/bk?id=' + id);
                }
            }
        });
    </script>
</div>
</body>
</html>