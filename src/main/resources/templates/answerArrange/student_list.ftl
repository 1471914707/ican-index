<!DOCTYPE HTML>
<html>
<head>
    <title>答辩安排列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
<#include '/include/cssjs_common_new.ftl'>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">{{activity.name}}--答辩安排</h2>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper" style="width:90%">
            <div class="main-page">
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                    </div>
                    <div class="panel panel-widget">
                        <template v-if="!loading">
                            <el-table
                                    :data="answerList"
                                    style="width: 100%"
                            >
                                <el-table-column
                                        style="max-width: 40px;"
                                        prop="id"
                                        label="序号">
                                </el-table-column>
                                <el-table-column
                                        prop="name"
                                        label="安排名称">
                                    <template slot-scope="scope">
                                        <a @click="openGroups(scope.row.id)" style="cursor: pointer;color: #409EFF">{{scope.row.name}}</a>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                        prop="startTime"
                                        label="开始时间">
                                </el-table-column>
                                <el-table-column
                                        prop="endTime"
                                        label="结束时间">
                                </el-table-column>
                                <el-table-column
                                        prop="ratio1"
                                        label="导师评分比例(%)">
                                </el-table-column>
                                <el-table-column
                                        prop="ratio2"
                                        label="小组评分比例(%)">
                                </el-table-column>
                                <el-table-column
                                        prop="type"
                                        label="类型">
                                </el-table-column>
                                <el-table-column
                                        prop="groups.name"
                                        label="评分小组">
                                </el-table-column>
                                <el-table-column
                                        prop="groups.ratingTime"
                                        label="答辩时间">
                                </el-table-column>
                                <el-table-column
                                        prop="groups.place"
                                        label="地点">
                                </el-table-column>
                                <el-table-column
                                        prop="rating.teacherRating"
                                        label="导师评分">
                                </el-table-column>
                                <el-table-column
                                        prop="rating.groupsRating"
                                        label="小组评分">
                                </el-table-column>
                                <el-table-column
                                        prop="rating.totalRating"
                                        label="总分">
                                </el-table-column>
                                    <el-table-column
                                            fixed="right"
                                            label="操作"
                                            min-width="120">
                                        <template slot-scope="scope">
                                            <el-button type="text" size="small" @click="ratingInfo(scope.row.id)" :loading="ratingLoading">评价信息</el-button>
                                    </el-table-column>
                                </template>
                            </el-table>
                        </template>
                        <template v-if="loading">
                        <#include '/include/common/loading.ftl'>
                        </template>
                    </div>
                </div>
            </div>

        </div>

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/">首页</a> > 答辩列表
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <el-dialog
                title="评价情况"
                :visible.sync="ratingDialog"
                width="60%">
            <el-table
                    :data="projectRatingList"
                    style="width: 100%">
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
    </div>

    <script>
        <#if activityId??>
        var activityId = ${activityId}
        <#else>
        var activityId = 0;
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    activity:{},
                    ratingList: [],
                    answerList:[],
                    groupsList:[],
                    teacherId:0,
                    loading:false,
                    projectId:0,
                    ratingLoading:false,
                    ratingDialog:false,
                    projectRatingList:[]
                }
            },
            mounted: function () {
                this.loadAnswerArrangeList();
            },
            methods:{
                loadAnswerArrangeList:function () {
                    var self = this;
                    self.loading = true;
                    Api.get('/answerArrange/groups/student/rating',{
                        activityId:activityId
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.answerList) {
                                self.answerList = result.data.answerList;
                                self.ratingList = result.data.ratingList;
                                self.activity = result.data.activity;
                                self.teacherId = result.data.teacherId;
                                self.groupsList = result.data.groupsList;
                                self.projectId = result.data.projectId;
                                for (var i=0; i<self.answerList.length; i++) {
                                    self.answerList[i].startTime = self.getDate(self.answerList[i].startTime);
                                    self.answerList[i].endTime = self.getDate(self.answerList[i].endTime);
                                    self.answerList[i].type = self.answerList[i].type == 2 ? '正式' : '非正式';
                                    for (var j=0; j<self.groupsList.length; j++) {
                                        if (self.groupsList[j].answerId == self.answerList[i].id) {
                                            self.answerList[i].groups = JSON.parse(JSON.stringify(self.groupsList[j]));
                                            self.answerList[i].groups.ratingTime = self.getDate2(self.answerList[i].groups.ratingTime);
                                            break;
                                        }
                                    }
                                    var info = {};
                                    var groupsNumTotal = 0;
                                    var groupsScoreTotal = 0;
                                    var teacherRatio = self.answerList[i].ratio1 / 100;
                                    var groupsRatio = self.answerList[i].ratio2 / 100;
                                    for (var j=0; j<self.ratingList.length; j++) {
                                        if (self.ratingList[j].answerId == self.answerList[i].id && self.ratingList[j].teacherId == self.teacherId){
                                            //项目的指导教师评分
                                            info.teacherRating = self.ratingList[j].score;
                                            continue;
                                        }
                                        if (self.ratingList[j].answerId == self.answerList[i].id && self.ratingList[j].projectId == self.projectId){
                                            groupsNumTotal ++;
                                            groupsScoreTotal += self.ratingList[j].score;
                                        }
                                    }
                                    info.groupsRating = (groupsScoreTotal / groupsNumTotal).toFixed(2);
                                    info.totalRating = info.teacherRating * teacherRatio + info.groupsRating * groupsRatio;
                                    self.answerList[i].rating = info;
                                }
                                self.loading = false;
                            }
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                ratingInfo:function () {
                    this.ratingLoading = true;
                    this.projectRatingList = [];
                    for (var i=0; i<this.ratingList.length; i++) {
                        if (this.projectId == this.ratingList[i].projectId) {
                            this.projectRatingList.push(JSON.parse(JSON.stringify(this.ratingList[i])));
                        }
                    }
                    for (var i=0; i<this.projectRatingList.length; i++) {
                        this.projectRatingList[i].gmtCreate = this.getDate2(this.projectRatingList[i].gmtCreate);
                    }
                    this.ratingDialog = true;
                    this.ratingLoading = false;
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                getDate2:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(".")[0];
                    }
                    return '';
                }
            }
        });
    </script>
</div>
</body>
</html>