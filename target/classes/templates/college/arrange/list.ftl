<!DOCTYPE HTML>
<html>
<head>
    <title>文件列表</title>
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
        .el-button+.el-button{
            margin-left: 0px;
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section " style="line-height: 68px;">
            <h2 style="margin-left: 3%;">{{activity.name}}--安排计划</h2>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper">
            <div class="main-page">
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <template v-if="userInfo.role == 4">
                            <el-button type="success" @click="edit(0)">新增</el-button>
                            <br /><br />
                        </template>
                    </div>
                    <div class="panel panel-widget">
                        <template v-if="!loading">
                            <el-row>
                                <el-col :span="6">计划</el-col>
                                <el-col :span="3">开始时间</el-col>
                                <el-col :span="3">结束时间</el-col>
                                <el-col :span="2">审核需要</el-col>
                                <el-col :span="2">文件要求</el-col>
                                <el-col :span="2">上移</el-col>
                                <el-col :span="2">下移</el-col>
                                <el-col :span="2">删除</el-col>
                            </el-row>
                            <br />
                            <div style="background:#dddddd;width:100%;height: 1px;"><br />
                                <template v-for="(item, index) in list">
                                    <el-row>
                                        <el-col :span="6">{{item.name}}</el-col>
                                        <el-col :span="3">{{item.startTime}}</el-col>
                                        <el-col :span="3">{{item.endTime}}</el-col>
                                        <el-col :span="2">
                                            <template v-if="item.follow == 2">
                                                <i class="el-icon-check"></i>
                                                <template>
                                        </el-col>
                                        <el-col :span="2">
                                            <template v-if="item.file == 2">
                                                <i class="el-icon-check"></i>
                                                <template>
                                        </el-col>
                                        <el-col :span="2">
                                            <template v-if="index != 0">
                                                <i class="el-icon-arrow-up"></i>
                                                <template>
                                        </el-col>
                                        <el-col :span="2">
                                            <template v-if="index != list.length-1">
                                                <i class="el-icon-arrow-down"></i>
                                                <template>
                                        </el-col>
                                        <el-col :span="2">
                                            <i class="el-icon-close"></i>
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
        </div>
    </div>

    <script>
        <#if activityId??>
        var activityId = ${activityId}
        <#else>
        var activityId = 0;
        </#if>
        <#if collegeId??>
        var collegeId = ${collegeId}
        <#else>
        var collegeId = 0;
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    page:1,
                    size:20,
                    total:0,
                    list: [],
                    loading:false,
                    editFlag:false,
                    activity:{},
                    userInfo:{},
                    arrange:{id:0,userId:collegeId,activityId:activityId,follow:0,file:0,name:'',weight:0,startTime:'',endTime:''}
                }
            },
            mounted: function () {
                this.loadArrangeList();
            },
            methods:{
                loadArrangeList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/arrange/listJson',{
                        activityId:activityId,
                        collegeId:collegeId
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                self.activity = result.data.activity;
                                self.userInfo = result.data.userInfo;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].startTime = self.getDate(self.list[i].startTime);
                                    self.list[i].endTime = self.getDate(self.list[i].endTime);
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                fileDelete:function (id) {
                    var self = this;
                    this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/file/delete",{id:id},function (result) {
                            if (result.code == 0) {
                                self.$message({showClose: true, message: '删除成功', type: 'success'});
                                for (var i=0; i<self.list.length; i++) {
                                    if (self.list[i].id == id) {
                                        self.list.splice(i, 1);
                                    }
                                }
                            } else {
                                self.$message({showClose: true, message: result.msg, type: 'error'});
                            }
                        });

                    }).catch(function () {
                        this.$message({type: 'info', message: '已取消删除'});
                    });
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadFileList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadFileList(this.page, this.size);
                }
            }
        });
    </script>
</div>
</body>
</html>