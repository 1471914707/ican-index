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
        a {
            cursor: pointer;
            color: #409EFF;
        }
    </style>
</head>
<body>
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section " style="line-height: 68px;">
                <h2 style="margin-left: 3%;">{{activity.name}}--{{student.name!=null?student.name+'--'+project.title+'的':''}}安排计划</h2>
                <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper" style="padding-top: 80px;">
            <div class="main-page">
                    <template v-if="role == 4">
                        <el-button type="success" @click="edit(0)">新增</el-button>
                        <br /><br />
                    </template>
                <div class="grids">
                    <div class="panel panel-widget">
                        <template v-if="!loading">
                            <el-row>
                                <el-col :span="1">步骤</el-col>
                                <el-col :span="6" style="margin-right: 20px;">计划</el-col>
                                <el-col :span="3">开始时间</el-col>
                                <el-col :span="3">结束时间</el-col>
                                <el-col :span="2">审核需要</el-col>
                                <el-col :span="2">文件要求</el-col>
                                <el-col :span="2">对象</el-col>
                                <el-col :span="2">操作</el-col>
                            </el-row>
                            <br />
                            <div style="background:#dddddd;width:100%;height: 1px;"></div><br />
                                <template v-for="(item, index) in list">
                                    <el-row>
                                        <el-col :span="1">
                                            <div class="el-step__icon is-text">
                                                <div class="el-step__icon-inner">{{index+1}}</div></div>
                                        </el-col>
                                        <el-col :span="6" style="margin-right: 20px;">{{item.name}}</el-col>
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
                                        <el-col :span="1" v-if="role == 4">
                                            <i class="el-icon-arrow-up" v-if="index != 0" @click="arrangeShift(item.id,index,1)"></i>
                                            <div style="width: 1px;height: 1px;" v-if="index == 0"></div>
                                        </el-col>
                                        <el-col :span="1" v-if="role == 4">
                                            <i class="el-icon-arrow-down" v-if="index != list.length-1" @click="arrangeShift(item.id,index,2)"></i>
                                            <div style="width: 1px;height: 1px;" v-if="index == list.length-1"></div>
                                        </el-col>
                                        <el-col :span="1" v-if="role == 4">
                                            <i class="el-icon-edit" @click="edit(item.id)"></i>
                                            <div style="width: 1px;height: 1px;"></div>
                                        </el-col>
                                        <el-col :span="1" v-if="role == 4">
                                            <i class="el-icon-close" @click="arrangeDelete(item.id)"></i>
                                            <div style="width: 1px;height: 1px;" v-if="index == 0"></div>
                                        </el-col>

                                        <el-col :span="4" v-if="role == 6 && item.file == 2 && (item.obj == 1 || item.obj == 2)">
                                            <el-button type="success" size="mini" :loading="commitFileLoading" @click="openCommitFile(item.id)">提交文件</el-button>
                                        </el-col>

                                        <el-col :span="3" v-if="role == 5 && item.file == 2 && (item.obj == 1 || item.obj == 3) && project.id == 0">
                                            <el-button type="success" size="mini" :loading="commitFileLoading" @click="openCommitFile(item.id)">提交文件</el-button>
                                        </el-col>
                                        <el-col :span="3" v-if="role == 5 && item.file == 2 && (item.obj == 1 || item.obj == 3) && project.id > 0">
                                            <el-button type="success" size="mini" :loading="commitFileLoading" @click="openCommitFile2(item.id)">文件</el-button>
                                        </el-col>
                                        <el-col :span="3" v-if="role == 5 && item.follow == 2 && project.id <= 0">
                                            <el-button type="success" size="mini" :loading="commitFollowLoading" @click="openFollow(item.id)">审核</el-button>
                                        </el-col>
                                        <el-col :span="3" v-if="role == 5 && item.follow == 2 && project.id > 0">
                                            <el-button type="success" size="mini" :loading="commitFollowLoading" @click="openFollow2(item.id)">审核</el-button>
                                        </el-col>
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

            <el-dialog
                    title="文档上交情况"
                    :visible.sync="commitFileDialog"
                    width="60%">
                <template v-if="!commitFileLoading">
                    <el-table
                            :data="fileList"
                            style="width: 100%"
                    >
                        <el-table-column
                                prop="name"
                                label="文件名称">
                        </el-table-column>
                        <el-table-column
                                prop="gmtCreate"
                                label="上传时间"
                                width="150">
                        </el-table-column>
                        <el-table-column
                                fixed="right"
                                label="操作">
                            <template slot-scope="scope">
                                <el-button type="text" size="small" @click="fileDownload(scope.row.url)">下载</el-button>
                                <el-button type="text" size="small" @click="fileDelete(scope.row.id)">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </template><br>
                <el-upload
                        action="/docUpload"
                        :show-file-list="false"
                        :on-success="docUploadSuccess">
                    <el-button size="small" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">文件大小不能超过20mb</div>
                </el-upload>
                <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="commitFileDialog = false">关 闭</el-button>
              </span>
            </el-dialog>

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
                        <el-form-item label="要求对象">
                            <el-select v-model="arrange.obj" placeholder="请选择对象">
                                <el-option label="学生、教师" value="1"></el-option>
                                <el-option label="学生" value="2"></el-option>
                                <el-option label="教师" value="3"></el-option>
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
        <#if projectId??>
        var projectId = ${projectId}
        <#else>
        var projectId = 0;
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
                    role:0,
                    fileList:[],
                    commitFileLoading:false,
                    commitFileDialog:false,
                    commitFollowLoading:false,
                    commitFollowDialog:false,
                    project:{},
                    student:{},
                    arrangeId:0,
                    arrange:{id:0,userId:collegeId,activityId:activityId,follow:false,file:false,name:'',weight:0,startTime:'',endTime:'',obj:null}
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
                        collegeId:collegeId,
                        projectId:projectId
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                self.activity = result.data.activity;
                                self.role = result.data.role;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].startTime = self.getDate(self.list[i].startTime);
                                    self.list[i].endTime = self.getDate(self.list[i].endTime);
                                }
                                if (result.data.project) {
                                    self.project = result.data.project;
                                    self.student = result.data.student;
                                }
                            }
                            self.loading = false;
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                openCommitFile:function (arrangeId) {
                    if (arrangeId <= 0) {
                        return false;
                    }
                    this.commitFileLoading = true;
                    this.commitFileDialog = true;
                    this.arrangeId = arrangeId;
                    this.loadFileList();
                    /*new Date().Format("yyyy-MM-dd")
                    for (var i=0; i<this.list.length; i++) {
                        if (arrangeId == this.list[i].id) {
                            var nowDay = new Date(new Date().Forma
                            t("yyyy-MM-dd"));
                            var startDate = new Date(this.getDate(this.list[i].startTime));
                            var endDate = new Date(this.getDate(this.list[i].endTime));
                            if (nowDay >= startDate && nowDay <= endDate) {

                            } else {
                                this.$message({showClose: true, message: '现在不是文件提交时间', type: 'error'});
                            }
                        }
                    }*/
                },
                openCommitFile2:function (arrangeId) {
                    if (arrangeId <= 0) {
                        return false;
                    }
                    this.commitFileLoading = true;
                    this.commitFileDialog = true;
                    this.arrangeId = arrangeId;
                    this.loadFileList2();
                    /*new Date().Format("yyyy-MM-dd")
                    for (var i=0; i<this.list.length; i++) {
                        if (arrangeId == this.list[i].id) {
                            var nowDay = new Date(new Date().Forma
                            t("yyyy-MM-dd"));
                            var startDate = new Date(this.getDate(this.list[i].startTime));
                            var endDate = new Date(this.getDate(this.list[i].endTime));
                            if (nowDay >= startDate && nowDay <= endDate) {

                            } else {
                                this.$message({showClose: true, message: '现在不是文件提交时间', type: 'error'});
                            }
                        }
                    }*/
                },
                openFollow2:function () {
                    window.location.href = "/teacher/project/list?activityId="+activityId+"&collegeId="+collegeId;
                },
                openFollow:function () {
                  window.location.href = "/teacher/project/list?activityId="+activityId+"&collegeId="+collegeId;
                },
                loadFileList:function () {
                    var self = this;
                    Api.get("/file/arrange/listJson",{arrangeId:self.arrangeId},function (result) {
                        if (result.code == 0) {
                            self.fileList = result.data.list;
                            self.commitFileLoading = false;
                            for (var i=0; i<self.fileList.length; i++) {
                                self.fileList[i].gmtCreate = self.getDate(self.fileList[i].gmtCreate);
                            }
                        }else{
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                loadFileList2:function () {
                    var self = this;
                    Api.get("/file/arrange/student/listJson",{arrangeId:self.arrangeId,studentId:self.student.id},function (result) {
                        if (result.code == 0) {
                            self.fileList = result.data.list;
                            self.commitFileLoading = false;
                            for (var i=0; i<self.fileList.length; i++) {
                                self.fileList[i].gmtCreate = self.getDate(self.fileList[i].gmtCreate);
                            }
                        }else{
                            self.$message({showClose: true, message: result.msg, type: 'error'});
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
                                for (var i=0; i<self.fileList.length; i++) {
                                    if (self.fileList[i].id == id) {
                                        self.fileList.splice(i, 1);
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
                docUploadSuccess:function (result, file, fileList) {
                    var self = this;
                    new Date().Format("yyyy-MM-dd")
                    for (var i=0; i<this.list.length; i++) {
                        if (self.arrangeId == this.list[i].id) {
                            var nowDay = new Date(new Date().Format("yyyy-MM-dd"));
                            var startDate = new Date(this.getDate(this.list[i].startTime));
                            var endDate = new Date(this.getDate(this.list[i].endTime));
                            if (!(nowDay >= startDate && nowDay <= endDate)) {
                                this.$message({showClose: true, message: '现在不是文件提交时间', type: 'error'});
                                return false;
                            }
                        }
                    }

                    if (result.code == 0){
                        Api.post("/file/arrange/save",{
                            arrangeId:self.arrangeId,
                            name:file.name,
                            url:result.data
                        },function (data) {
                            if (data.code == 0) {
                                self.loadFileList();
                                self.$message({showClose: true, message: '保存成功', type: 'success'});
                            } else {
                                self.$message({showClose: true, message: '保存失败', type: 'error'});
                            }
                        });
                    } else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
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
                    if (self.arrange.follow == true){
                        self.arrange.follow = 2;
                    }else{
                        self.arrange.follow = 1;
                    }
                    if (self.arrange.file == true){
                        self.arrange.file = 2;
                    }else{
                        self.arrange.file = 1;
                    }
                    Api.post("/arrange/save",self.arrange,function (result) {
                        if (result.code == 0){
                            self.$message({showClose: true, message: '增加成功', type: 'success'});
                            self.loadArrangeList();
                            self.editFlag = false;
                            self.arrange = {id:0,userId:collegeId,activityId:activityId,follow:false,file:false,name:'',weight:0,startTime:'',endTime:''};

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
                                self.arrange.obj = self.arrange.obj + "";
                            }else{
                                self.arrange = {id:0,userId:collegeId,activityId:activityId,follow:false,file:false,name:'',weight:0,startTime:'',endTime:'',obj:null};
                            }
                        });
                    } else {
                       self.arrange = {id:0,userId:collegeId,activityId:activityId,follow:false,file:false,name:'',weight:0,startTime:'',endTime:'',obj:null};
                    }
                    self.editFlag = true;
                },
                fileDownload:function (url) {
                    window.open(url);
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