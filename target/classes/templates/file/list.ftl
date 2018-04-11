<!DOCTYPE HTML>
<html>
<head>
    <title>${college.collegeName}活动列表</title>
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
        <!--left-fixed -navigation-->
        <div class="sidebar" role="navigation">
            <div class="navbar-collapse">
                <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right dev-page-sidebar mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar" id="cbp-spmenu-s1">
                    <div>
                        <el-collapse>
                            <a href="/college/activity/list"><el-collapse-item title="活动列表" name="1">
                            </el-collapse-item></a>
                            <a href="/college/college/list"><el-collapse-item title="二级学院" name="2">
                            </el-collapse-item></a>
                            <a href="/college/teacher/list"><el-collapse-item title="教师情况" name="3">
                            </el-collapse-item></a>
                            <a href="/college/student/list"><el-collapse-item title="学生情况" name="4">
                            </el-collapse-item></a>
                            <el-collapse-item title="个人设置" name="5">
                                <div style="color: #409EFF;cursor: pointer">
                                    <div onclick="javascript:window.location.href='/school/edit'">个人资料</div>
                                    <div onclick="javascript:window.location.href='/password'">密码修改</div>
                                    <div onclick="javascript:window.location.href='/logout'">退出</div>
                                </div>
                            </el-collapse-item>
                        </el-collapse>
                    </div>
                </nav>
            </div>
        </div>
        <div class="sticky-header header-section ">
            <div class="header-left">
                <img src="${school.banner}">
                <div class="clearfix"> </div>
            </div>
            <div class="header-right" style="float: right;margin-right: 50px;">
                <div class="profile_details" style="margin-top: 5%">
                    <el-row>
                        <el-col :span="12" style="line-height: 60px"><span>学校：</span></el-col>
                        <el-col :span="10">
                            <a href="/bk?id=${schoolId}" target="_blank">
                                <img src="${school.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 18%"></a>
                        </el-col>
                    </el-row>
                </div>
                <button id="showLeftPush" style="padding-top: 30px;">
                    <img  src="http://cdn.ican.com/public/images/bars.png" style="max-width:18.003px;max-height:23.333px;"></button>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper">
            <div class="main-page">
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <h2>{{activity.name}}共享文件列表
                            <el-upload
                                    class="upload-demo"
                                    action="/photoUpload"
                                    :show-file-list="false"
                                    :on-success="photoUploadSuccess">
                                <el-button size="small" type="primary">点击上传</el-button>
                                <div slot="tip" class="el-upload__tip">文件大小不能超过20mb</div>
                            </el-upload>
                        </h2>
                    </div>
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
                                        label="操作"
                                        min-width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="small" @click="fileDownload(scope.row.url)">下载</el-button>
                                        <el-button type="text" size="small" @click="fileDelete(scope.row.id)">删除</el-button>
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
                    activity:{}
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
                    Api.get('/college/file/listJson',{
                        activityId:activityId,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                self.activity = result.data.activity;
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
                photoUploadSuccess:function (result, file, fileList) {
                    var self = this;
                    if (result.code == 0){
                        Api.post("/college/file/save",{
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
                        Api.post("/college/file/delete",{id:id},function (result) {
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
                    this.loadActivityList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadActivityList(this.page, this.size);
                }
            }
        });
    </script>
    <script>
        var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
                showLeftPush = document.getElementById( 'showLeftPush' ),
                body = document.body;

        showLeftPush.onclick = function() {
            classie.toggle( this, 'active' );
            classie.toggle( body, 'cbp-spmenu-push-toright' );
            classie.toggle( menuLeft, 'cbp-spmenu-open' );
            disableOther( 'showLeftPush' );
        };
        showLeftPush.click();

        function disableOther( button ) {
            if( button !== 'showLeftPush' ) {
                classie.toggle( showLeftPush, 'disabled' );
            }
        }
    </script>

</div>
</body>
</html>