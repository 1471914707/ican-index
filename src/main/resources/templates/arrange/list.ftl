<!DOCTYPE HTML>
<html>
<head>
    <title>计划</title>
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
        i{
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section " style="line-height: 68px;">
                <h2 style="margin-left: 3%;">{{activity.name}}--安排计划</h2>
                <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper" style="padding-top: 80px;">
            <div class="main-page">
                    <template v-if="userInfo.role == 4">
                        <el-button type="success" @click="edit(0)">新增</el-button>
                        <br /><br />
                    </template>
                <div class="grids">
                    <div class="panel panel-widget">
                        <template v-if="!loading">
                            <el-row>
                                <el-col :span="6">计划</el-col>
                                <el-col :span="3">开始时间</el-col>
                                <el-col :span="3">结束时间</el-col>
                                <el-col :span="2">审核需要</el-col>
                                <el-col :span="2">文件要求</el-col>
                                <el-col :span="2">对象</el-col>
                                <el-col :span="6">操作</el-col>
                            </el-row>
                            <br />
                            <div style="background:#dddddd;width:100%;height: 1px;"></div><br />
                                <template v-for="(item, index) in list">
                                    <el-row>
                                        <el-col :span="6">{{item.name}}</el-col>
                                        <el-col :span="3">{{item.startTime}}</el-col>
                                        <el-col :span="3">{{item.endTime}}</el-col>
                                        <el-col :span="2">
                                            <i class="el-icon-check" v-if="item.follow == 2"></i>
                                            <div style="width: 1px;height: 1px;" v-if="item.follow != 2"></div>
                                        </el-col>
                                        <el-col :span="2">
                                            <i class="el-icon-check" v-if="item.file == 2"></i>
                                            <div style="width: 1px;height: 1px;" v-if="item.file != 2"></div>
                                        </el-col>
                                        <el-col :span="2">
                                            <span v-if="item.obj == 1">教师、学生</span>
                                            <span v-if="item.obj == 2">学生</span>
                                            <span v-if="item.obj == 3">教师</span>
                                            <div style="width: 1px;height: 1px;"></div>
                                        </el-col>
                                        <el-col :span="1" v-if="userInfo.role == 4">
                                            <i class="el-icon-arrow-up" v-if="index != 0" @click="arrangeShift(item.id,index,1)"></i>
                                            <div style="width: 1px;height: 1px;" v-if="index == 0"></div>
                                        </el-col>
                                        <el-col :span="1" v-if="userInfo.role == 4">
                                            <i class="el-icon-arrow-down" v-if="index != list.length-1" @click="arrangeShift(item.id,index,2)"></i>
                                            <div style="width: 1px;height: 1px;" v-if="index == list.length-1"></div>
                                        </el-col>
                                        <el-col :span="1" v-if="userInfo.role == 4">
                                            <i class="el-icon-edit" @click="edit(item.id)"></i>
                                            <div style="width: 1px;height: 1px;"></div>
                                        </el-col>
                                        <el-col :span="1" v-if="userInfo.role == 4">
                                            <i class="el-icon-close" @click="arrangeDelete(item.id)"></i>
                                            <div style="width: 1px;height: 1px;" v-if="index == 0"></div>
                                        </el-col>

                                        <el-col :span="4" v-if="userInfo.role == 6">提交相关文件</el-col>

                                        <el-col :span="3" v-if="userInfo.role == 5">提交相关文件</el-col>
                                        <el-col :span="3" v-if="userInfo.role == 5">查看审核情况</el-col>
                                    </el-row>

                                    <div style="background:#dddddd;width:100%;height: 1px;margin-top: 15px;margin-bottom: 15px;"></div>
                                </template>
                        </template>
                        <template v-if="loading">
                        <#include '/include/common/loading.ftl'>
                        </template>
                    </div>
                </div>
            </div>

            <template v-if="editFlag">
                <el-dialog
                        :title="新增安排"
                        :visible.sync="editFlag"
                        width="70%">
                    <el-form ref="activity" :model="arrange" label-width="80px" style="width: 80%">
                        <el-form-item label="计划内容">
                            <el-input type="textarea" v-model="arrange.name"></el-input>
                        </el-form-item>
                        <el-form-item label="开始时间">
                            <el-date-picker
                                    v-model="arrange.startTime"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    @change="startTimeChange"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束时间">
                            <el-date-picker
                                    v-model="arrange.endTime"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    @change="endTimeChange"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="审核需要">
                            <el-switch v-model="arrange.follow"></el-switch>
                        </el-form-item>
                        <el-form-item label="文件要求">
                            <el-switch v-model="arrange.file"></el-switch>
                        </el-form-item>
                        <el-form-item label="对象">
                            <el-select v-model="arrange.obj" placeholder="请选择活动区域">
                                <el-option label="区域一" value="shanghai"></el-option>
                                <el-option label="区域二" value="beijing"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                    <el-button @click="editFlag = false">取 消</el-button>
                    <el-button type="primary" @click="saveArrange()">确 定</el-button>
                  </span>
                </el-dialog>

            </template>

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
                    total:0,
                    list: [],
                    loading:false,
                    editFlag:false,
                    activity:{},
                    userInfo:{},
                    arrange:{id:0,userId:collegeId,activityId:activityId,follow:false,file:false,name:'',weight:0,startTime:'',endTime:''}
                }
            },
            mounted: function () {
                this.loadArrangeList();
            },
            methods:{
                loadArrangeList:function (page, size) {
                    var self = this;
                    self.loading = true;
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
                arrangeDelete:function (id) {
                    var self = this;
                    this.$confirm('此操作将永久删除该安排, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/arrange/delete",{id:id},function (result) {
                            if (result.code == 0){
                                self.$message({showClose: true, message: '删除成功', type: 'success'});
                                self.loadArrangeList();
                            } else {
                                self.$message({showClose: true, message: '删除失败', type: 'error'});
                            }
                        });

                    }).catch(function () {
                        this.$message({type: 'info', message: '已取消删除'});
                    });
                },
                saveArrange:function () {
                    var self = this;
                    if (self.list.length > 0) {
                        self.arrange.weight = self.list[0].weight + 1;
                    } else{
                        self.arrange.weight = 0;
                    }
                    if (self.activity.follow == true){
                        self.activity.follow == 2;
                    }else{
                        self.activity.follow == 1;
                    }
                    if (self.activity.file == true){
                        self.activity.file == 2;
                    }else{
                        self.activity.file == 1;
                    }
                    Api.post("/arrange/save",self.arrange,function (result) {
                        if (result.code == 0){
                            self.$message({showClose: true, message: '增加成功', type: 'success'});
                            self.loadArrangeList();
                            self.arrange = {id:0,userId:collegeId,activityId:activityId,follow:false,file:false,name:'',weight:0,startTime:'',endTime:''};
                            self.editFlag = false;
                        } else {
                            self.$message({showClose: true, message: '增加失败', type: 'error'});
                        }
                    });
                },
                edit:function (id) {
                    var self = this;
                    if (id > 0){
                        Api.get("/arrange/info",{id:id},function (result) {
                            if (result.code == 0) {
                                self.arrange = result.data;
                                self.arrange.follow == 2 ? self.arrange.follow=true : self.arrange.follow=false;
                                self.arrange.file == 2 ? self.arrange.file=true : self.arrange.file=false;
                            }else{
                                self.arrange = {id:0,userId:collegeId,activityId:activityId,follow:false,file:false,name:'',weight:0,startTime:'',endTime:''};
                            }
                        });
                    }
                    self.editFlag = true;
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                arrangeShift:function (id,index,flag) {
                    var self = this;
                    var fromId = self.list[index].id;;
                    var toId = 0;
                    if (flag == 1){
                        toId = self.list[index-1].id;
                    }else if (flag == 2){
                        toId = self.list[index+1].id;
                    }else{
                        self.$message({showClose: true, message: '异常操作', type: 'error'});
                        return false;
                    }
                    Api.post("/arrange/exchange",{fromId:fromId,toId:toId},function (result) {
                        if (result.code == 0){
                            self.$message({showClose: true, message: '移动成功', type: 'success'});
                            self.loadArrangeList();
                        } else {
                            self.$message({showClose: true, message: '移动失败', type: 'error'});
                        }
                    });
                }
            }
        });
    </script>
</div>
</body>

</html>