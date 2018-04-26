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
            <h2 style="margin-left: 3%;">{{activity.name}}--共享文件列表</h2>
            <div class="clearfix"> </div>
        </div>

        <div id="page-wrapper" style="width: 90%">
            <div class="main-page">
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <template v-if="userInfo.role == 4">
                        <el-upload
                                class="upload-demo"
                                action="/docUpload"
                                :show-file-list="false"
                                :on-success="docUploadSuccess">
                            <el-button size="small" type="primary">点击上传</el-button>
                            <div slot="tip" class="el-upload__tip">文件大小不能超过20mb</div>
                        </el-upload>
                            <br />
                        </template>
                        <template>
                            <el-radio v-model="radio" label="1">全部</el-radio>
                            <el-radio v-model="radio" label="2">活动共享</el-radio>
                            <el-radio v-model="radio" label="3">个人</el-radio>
                            <el-radio v-model="radio" label="4">教师可见</el-radio>
                            <el-radio v-model="radio" label="5">学生可见</el-radio>
                        </template>

                    </div><br>
                    <div class="panel panel-widget">
                        <template v-if="!loading">
                            <el-table
                                    :data="list"
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
                                        label="文件名称"
                                        width="600">
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
                                        <el-button type="text" size="small" @click="fileUrl=scope.row.url;fileUrlDialog=true;">链接</el-button>
                                        <template v-if="userInfo.role == 4">
                                            <el-button type="text" size="small" @click="fileDelete(scope.row.id)">删除</el-button>
                                        </template>
                                    </template>
                                </el-table-column>
                            </el-table>
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
                        :page-sizes="[20, 30, 40, 50]"
                        :page-size="size"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </div>

            <el-dialog
                    title="请复制这条链接"
                    :visible.sync="fileUrlDialog"
                    width="50%">
                <span>{{fileUrl}}</span>
                <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="fileUrlDialog = false">关 闭</el-button>
              </span>
            </el-dialog>


        </div>

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/">首页</a> > 活动共享文件列表
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
                    page:1,
                    size:20,
                    total:0,
                    list: [],
                    loading:false,
                    editFlag:false,
                    activity:{},
                    userInfo:{},
                    fileUrlDialog:false,
                    fileUrl:'',
                    radio:'1'
                }
            },
            mounted: function () {
                this.loadFileList();
            },
            methods:{
                loadFileList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/file/listJson',{
                        activityId:activityId,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                self.activity = result.data.activity;
                                self.userInfo = result.data.userInfo;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].gmtCreate = self.getDate(self.list[i].gmtCreate);
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                docUploadSuccess:function (result, file, fileList) {
                    var self = this;
                    if (result.code == 0){
                        Api.post("/file/save",{
                            activityId:activityId,
                            name:file.name,
                            url:result.data
                        },function (data) {
                           if (data.code == 0) {
                               self.loadFileList(1,20);
                               self.$message({showClose: true, message: '保存成功', type: 'success'});
                           } else {
                               self.$message({showClose: true, message: '保存失败', type: 'error'});
                           }
                        });
                    } else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
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
                fileDownload:function (url) {
                  window.open(url);
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