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
                        <h2>安排列表</h2>
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
                                        label="序号"
                                        width="80">
                                </el-table-column>
                                <el-table-column
                                        prop="name"
                                        label="安排名称"
                                        width="200">
                                    <template slot-scope="scope">
                                        <a @click="openGroups(scope.row.id)" style="cursor: pointer;color: #409EFF">{{scope.row.name}}</a>
                                    </template>
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
                                        prop="ratio1"
                                        label="指导教师评分比例(%)"
                                        width="150">
                                </el-table-column>
                                <el-table-column
                                        prop="ratio2"
                                        label="小组教师评分比例(%)"
                                        width="150">
                                </el-table-column>
                                <el-table-column
                                        prop="type"
                                        label="类型"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        prop="groups.name"
                                        label="评分小组"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        prop="groups.ratingTime"
                                        label="答辩时间"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        prop="groups.place"
                                        label="地点"
                                        width="150">
                                </el-table-column>
                                    <el-table-column
                                            fixed="right"
                                            label="操作"
                                            min-width="120">
                                        <template slot-scope="scope">
                                            <el-button type="text" size="small" @click="ratingInfo(scope.row.id)">评价信息</el-button>
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
                            <a href="/">首页</a> > 答辩安排列表
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
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                answerArrangeDelete:function (id) {
                    var self = this;
                    this.$confirm('此操作将永久删除该答辩安排, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/answerArrange/delete",{id:id},function (result) {
                            if (result.code == 0) {
                                for (var i=0; i<self.list.length; i++) {
                                    if (self.list[i].id == id) {
                                        self.list.splice(i, 1);
                                    }
                                }
                                self.$message({showClose: true, message: "删除成功", type: 'success'});
                            } else {
                                self.$message({showClose: true, message: result.msg, type: 'error'});
                            }
                        });

                    }).catch(function () {
                        this.$message({type: 'info', message: '已取消删除'});
                    });
                },
                edit:function (id) {
                    var self = this;
                    if (id == 0) {
                        this.answerArrange = {id:0,name:'',activityId:activityId,startTime:'',endTime:'',ratio1:0,ratio2:0,type:'1'};
                    } else {
                        for (var i=0; i<this.list.length; i++) {
                            if (this.list[i].id == id) {
                                this.answerArrange = JSON.parse(JSON.stringify(this.list[i]));
                                this.answerArrange.type = this.answerArrange.type == '正式'?'2':'1';
                            }
                        }
                    }
                    this.editFlag = true;
                    return true;
                },
                saveAnswerArrange:function () {
                    var self = this;
                    Api.post("/answerArrange/save",self.answerArrange,function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: "保存成功", type: 'success'});
                            self.loadAnswerArrangeList();
                            self.editFlag = false;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                handleRatio1Change:function (ratio1) {
                    this.answerArrange.ratio2 = 100 - ratio1;
                },
                openGroups:function (id) {
                    window.location.href = '/answerArrange/groups?activityId='+activityId+'&answerId=' + id;
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