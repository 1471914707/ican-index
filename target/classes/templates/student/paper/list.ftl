<!DOCTYPE HTML>
<html>
<head>
    <title>我的选题</title>
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
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
            <div class="header-left">
                <img src="${student.salt}">
                <div class="clearfix"> </div>
            </div>
            <div class="header-right" style="float: right;line-height: 68px;">
                <el-row>
                    <el-col :xs="0" :sm="15" :md="15" :lg="10" :xl="10">
                        <a href="/bk?id=${student.status?c}" target="_blank">
                            <img src="${student.phone}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 10%;display: inline-block"></a>
                        <a href="/bk?id=${student.role?c}" target="_blank">
                            <img src="${student.email}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 10%;display: inline-block"></a>
                        <a href="/bk?id=${student.id?c}" target="_blank">
                            <img src="${student.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 10%;display: inline-block"></a>
                    </el-col>
                    <el-col :xs="20" :sm="9" :md="8" :lg="10" :xl="9">
                        <a href="/student/edit" target="_blank">${student.name}</a> &nbsp;&nbsp; <a href="/message/my" target="_blank"> 消息</a> &nbsp;&nbsp; <a href="/logout">退出</a>
                    </el-col>
                </el-row>
            </div>
        </div>
        <div id="page-wrapper" style="width: 90%">
            <div>
                <el-row>
                    <el-col :span="2"><h4>已选课题：</h4></el-col>
                    <el-col :span="15">
                        <el-row>
                            <el-col :span="3">学生：${student.name}</el-col>
                            <el-col :span="5">题目：{{paper.paper.title}}</el-col>
                            <el-col :span="8">要求：{{paper.paper.requires}}</el-col>
                            <el-col :span="8">备注：{{paper.paper.remark}}</el-col>
                        </el-row>
                        <br>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="2"><h4>教师信息：</h4></el-col>
                    <el-col :span="15">
                        <el-row>
                            <el-col :span="3">教师：{{paper.teacher.name}}</el-col>
                            <el-col :span="6">院系：{{paper.college.collegeName + "-" + paper.department.name}}</el-col>
                            <el-col :span="5">电话：{{paper.teacher.phone}}</el-col>
                            <el-col :span="5">邮箱：{{paper.teacher.email}}</el-col>
                        </el-row>
                    </el-col>
                </el-row>
                <div style="float: right;">
                    <el-select v-model="numStatus" @change="numStatusChange()">
                        <el-option label="全部" value="1"></el-option>
                        <el-option label="可选" value="2"></el-option>
                        <el-option label="满选" value="3"></el-option>
                    </el-select>
                </div>
            </div>
            <br /><br /><br />
            <div class="main-page">
                <!--grids-->
                <div class="grids">
                    <el-row v-if="!loading">
                        <div class="panel panel-widget">
                            <template v-if="!loading">
                                <el-table
                                        :data="list"
                                        style="width: 100%"
                                >
                                    <el-table-column
                                            style="max-width: 40px;"
                                            prop="paper.id"
                                            label="序号"
                                            width="100">
                                    </el-table-column>
                                    <#--<el-table-column
                                            prop="college.collegeName"
                                            label="所在学院"
                                            width="150">
                                    </el-table-column>-->
                                    <el-table-column
                                            label="所在学院"
                                            width="150">
                                        <template slot-scope="scope">
                                            <a @click="openBk(scope.row.college.id)" href="#" style="cursor: pointer;color: #409EFF">{{scope.row.college.collegeName}}</a>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                            prop="department.name"
                                            label="所在系（部门）"
                                            width="150">
                                    </el-table-column>
                                    <el-table-column
                                            label="教师"
                                            width="150">
                                        <template slot-scope="scope">
                                            <a @click="openBk(scope.row.teacher.id)" href="#" style="cursor: pointer;color: #409EFF">{{scope.row.teacher.name}}</a>
                                        </template>
                                    </el-table-column>
                                    <#--<el-table-column
                                            prop="teacher.name"
                                            label="教师"
                                            width="150">
                                    </el-table-column>-->
                                    <el-table-column
                                            prop="paper.title"
                                            label="毕业设计（论文）题目"
                                            width="250">
                                    </el-table-column>
                                    <el-table-column
                                            prop="paper.requires"
                                            label="要求和说明"
                                            width="250">
                                    </el-table-column>
                                    <el-table-column
                                            label="需要人数"
                                            width="100">
                                        <template slot-scope="scope">
                                            <span v-if="scope.row.paper.minNumber == scope.row.paper.maxNumber">{{scope.row.paper.minNumber}}</span>
                                            <span v-if="scope.row.paper.minNumber != scope.row.paper.maxNumber">{{scope.row.paper.minNumber + '-' + scope.row.paper.maxNumber}}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                            prop="paper.remark"
                                            label="备注"
                                            width="250">
                                    </el-table-column>
                                    <el-table-column
                                            prop="num"
                                            label="已选人数"
                                            width="100">
                                    </el-table-column>
                                    <el-table-column
                                            fixed="right"
                                            label="操作">
                                        <template slot-scope="scope">
                                            <el-button type="text" size="small" @click="saveStudentPaper(scope.row.paper.id,scope.row.paper.version);">选择</el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </template>
                            <template v-if="loading">
                            <#include '/include/common/loading.ftl'>
                            </template>
                        </div>
                    </el-row>
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
                        :page-sizes="[20, 30, 40, 50]"
                        :page-size="size"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
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
                    slogan:'全站最热门广告位招租！！！有意者电话联系18813960106！！！',
                    page:1,
                    size:20,
                    total:0,
                    list: [],
                    tempList:[],
                    loading:false,
                    current:'--',
                    editDialog:false,
                    infoDialog:false,
                    activity:{},
                    paper:{},
                    numStatus:'1'
                }
            },
            mounted: function () {
                this.loadPaperList();
                this.loadStudentPaper();
            },
            methods:{
                loadPaperList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/student/paper/listJson',{
                        activityId:activityId,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list.length > 0) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                self.tempList = result.data.list;
                            }
                            self.activity = result.data.activity;
                            self.loading = false;
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                loadStudentPaper:function () {
                    var self = this;
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
                saveStudentPaper:function (paperId, version, index) {
                    var self = this;
                    alert(self.paper.paper.id);
                    Api.post('/student/paper/save',{
                        activityId:activityId,
                        paperId:paperId,
                        version:version
                    },function (result) {
                        if (result.code == 0) {
                            for (var i=0; i<self.list.length; i++) {
                                if (self.list[i].paper.id == paperId) {
                                    self.list[i].num += 1;
                                }
                                if (self.list[i].paper.id == self.paper.paper.id) {
                                    self.list[i].num -= 1;
                                }
                            }
                            self.$message({showClose: true, message: '选择成功', type: 'success'});
                            self.loadStudentPaper();
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                deleteStudentPaper:function (paperStudentId) {
                    var self = this;
                    Api.post('/student/paper/save',{
                        paperStudentId:paperStudentId
                    },function (result) {
                        if (result.code == 0) {
                            self.paper = {};
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                numStatusChange:function () {
                    if (this.numStatus == 1) {
                        this.list = this.tempList;
                    }
                    if (this.numStatus == 2) {
                        this.list = [];
                        for (var i=0; i<this.tempList.length; i++) {
                            if (this.tempList[i].num < this.tempList[i].paper.maxNumber) {
                                this.list.push(this.tempList[i]);
                            }
                        }
                    }
                    if (this.numStatus == 3) {
                        this.list = [];
                        for (var i=0; i<this.tempList.length; i++) {
                            if (this.tempList[i].num == this.tempList[i].paper.maxNumber) {
                                this.list.push(this.tempList[i]);
                            }
                        }
                    }
                },
                openBk:function (id) {
                    window.open('/bk?id=' + id);
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                handleClose:function () {
                    this.studentList = [];
                    this.infoDialog = false;
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadPaperList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadPaperList(this.page, this.size);
                }
            }
        });
    </script>
</div>
</body>
</html>