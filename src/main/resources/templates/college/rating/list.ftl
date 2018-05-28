<!DOCTYPE HTML>
<html>
<head>
    <title>答辩成绩情况</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
<#include '/include/cssjs_common_new.ftl'>
    <style>
        .col-border{
            border: solid #000000 1px;
            min-height:30px;
            text-align: center;
            line-height: 30px;
        }
        .col-border-title{
            border: solid #000000 1px;
            line-height:35px;
            text-align: center;
            font-weight: bolder;
            background: #409EFF;
            color: white;
        }
        .col-div{
            text-align: left;
            padding-left:10px;
            padding-top: 10px;
            padding-bottom: 10px;
            padding-right: 10px;
        }
        .col-div:nth-child(even){
            background: #f4f7f9;
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
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">{{activity.name}}-{{answerArrange.name}}--成绩单</h2>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper" style="width:90%">
            <div>
                <el-input v-model="content" placeholder="请输入内容" style="width:200px;"></el-input>
                <el-button icon="el-icon-search" type="success" circle @click="search()"></el-button>
                <br>
            </div>
            <div class="main-page">
                <div class="grids">
                    <template v-if="loading">
                    <#include '/include/common/loading.ftl'>
                    </template>
                    <template v-if="!loading">
                        <div class="panel panel-widget">
                            <el-row>
                                <el-col :span="2" class="col-border-title">
                                    学生
                                </el-col>
                                <el-col :span="4" class="col-border-title">
                                    系部
                                </el-col>
                                <el-col :span="4" class="col-border-title">
                                    专业班级
                                </el-col>
                                <el-col :span="2" class="col-border-title">
                                    导师
                                </el-col>
                                <el-col :span="3" class="col-border-title">
                                    项目
                                </el-col>
                                <el-col :span="3" class="col-border-title">
                                    导师评分
                                </el-col>
                                <el-col :span="3" class="col-border-title">
                                    小组评分
                                </el-col>
                                <el-col :span="3" class="col-border-title">
                                    总评
                                </el-col>
                            </el-row>
                            <template v-for="(item, index) in list">
                                <el-row>
                                    <el-row>
                                        <el-col :span="2" class="col-border">
                                            {{item.studentName}}
                                        </el-col>
                                        <el-col :span="4" class="col-border">
                                            {{item.departmentName}}
                                        </el-col>
                                        <el-col :span="4" class="col-border">
                                            {{item.majorName}}-{{item.clazzName}}
                                        </el-col>
                                        <el-col :span="2" class="col-border">
                                            {{item.teacherName}}
                                        </el-col>
                                        <el-col :span="3" class="col-border">
                                            {{item.projectName}}
                                        </el-col>
                                        <el-col :span="3" class="col-border">
                                            {{item.teacherRating}}
                                        </el-col>
                                        <el-col :span="3" class="col-border">
                                            {{item.groupsRating}}
                                        </el-col>
                                        <el-col :span="3" class="col-border">
                                            {{item.totalRating}}
                                        </el-col>
                                    </el-row>
                                </el-row>
                        </div>
                    </template>
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

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    page:1,
                    size:20,
                    total:0,
                    answerArrange:{},
                    activity:{},
                    ratingList:[],
                    projectList:[],
                    list:[],
                    oldList:[],
                    loading:false,
                    content:''
                }
            },
            mounted: function () {
                this.loadRatingList();
            },
            methods:{
                loadRatingList:function () {
                    var self = this;
                    self.loading = true;
                    Api.get("/college/rating/listJson",{activityId:activityId,answerId:answerId},function (result) {
                        if (result.code == 0) {
                            self.projectList = result.data.projectList;
                            self.ratingList = result.data.ratingList;
                            self.total = result.data.total;
                            self.answerArrange = result.data.answerArrange;
                            self.activity = result.data.activity;
                            //处理一下成绩相关,项目区分
                            self.list = [];
                            var teacherRatio = self.answerArrange.ratio1 / 100;
                            var groupsRatio = self.answerArrange.ratio2 / 100;
                            for (var i=0; i<self.projectList.length; i++) {
                               var info = {};
                               var groupsNumTotal = 0;
                               var groupsScoreTotal = 0;
                               info.studentName = self.projectList[i].student.name;
                               info.projectName = self.projectList[i].project.title;
                               info.departmentName = self.projectList[i].departmentName;
                               info.majorName = self.projectList[i].majorName;
                               info.clazzName = self.projectList[i].clazzName;
                               info.teacherName = self.projectList[i].teacher.name;
                               for (var j=0; j<self.ratingList.length; j++) {
                                   if (self.ratingList[j].teacherId == self.projectList[i].project.teacherId && self.ratingList[j].projectId == self.projectList[i].id){
                                        //项目的指导教师评分
                                        info.teacherRating = self.ratingList[j].score;
                                        continue;
                                   }
                                   if (self.ratingList[j].projectId == self.projectList[i].id){
                                       groupsNumTotal ++;
                                       groupsScoreTotal += self.ratingList[j].score;
                                   }
                               }
                               if (groupsNumTotal != 0) {
                                   info.groupsRating = (groupsScoreTotal / groupsNumTotal).toFixed(2);
                                   info.totalRating = info.teacherRating * teacherRatio + info.groupsRating * groupsRatio;
                                   info.totalRating = info.totalRating.toFixed(2);
                               } else {
                                   info.groupsRating = 0;
                                   info.totalRating = info.teacherRating * teacherRatio;
                                   info.totalRating = info.totalRating.toFixed(2);
                               }
                               self.list.push(info);
                            }
                            self.oldList = JSON.parse(JSON.stringify(self.list));
                            self.loading = false;
                        } else {
                            self.$message({showClose: true, message: '系统出错，请重新刷新', type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                search:function () {
                    if (this.content.trim() == '') {
                        this.$message({showClose: true, message: '请输入搜索内容', type: 'success'});
                        return true;
                    }
                    this.loading = true;
                    var tempList = [];
                    for (var i=0; i<this.oldList.length; i++) {
                        if (this.oldList[i].studentName.indexOf(this.content) >= 0 ||
                                this.oldList[i].teacherName.indexOf(this.content) >= 0 ||
                                this.oldList[i].departmentName.indexOf(this.content) >= 0 ||
                                this.oldList[i].majorName.indexOf(this.content) >= 0 ||
                                this.oldList[i].clazzName.indexOf(this.content) >= 0){
                            tempList.push(JSON.parse(JSON.stringify(this.oldList[i])));
                        }
                    }
                    this.list = JSON.parse(JSON.stringify(tempList));
                    this.loading = false;
                }
            }
        });
    </script>
</div>
</body>
</html>