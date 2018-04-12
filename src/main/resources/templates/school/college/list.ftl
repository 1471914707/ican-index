<!DOCTYPE HTML>
<html>
<head>
    <title>学校申请列表</title>
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
        .person-info div{
            display: inline-block;
            padding-top: 10px;
            margin-bottom: 10px;
        }
        @media screen and (max-width: 900px) {
            .college-img{
                display: none;
            }
        }
        .avatar-uploader .el-upload {
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
        }
        .avatar-uploader .el-upload:hover {
            border-color: #409EFF;
        }
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
            border-radius: 50%;
        }
        body a{
            color:#409EFF;
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
                            <a href="/school/activity/list"><el-collapse-item title="活动列表" name="1">
                            </el-collapse-item></a>
                            <a href="/school/college/list"><el-collapse-item title="二级学院" name="2">
                            </el-collapse-item></a>
                            <a href="/school/teacher/list"><el-collapse-item title="教师情况" name="3">
                            </el-collapse-item></a>
                            <a href="/school/student/list"><el-collapse-item title="学生情况" name="4">
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
                <div class="profile_details" style="margin-top: 10px;">
                    <a href="/bk?id=${school.id?c}" target="_blank">
                        <img src="${school.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 18%"></a>
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
                        <h2>二级学院列表<i class="el-icon-circle-plus" @click="edit(0)" style="cursor: pointer;"></i></h2>
                    </div>
                    <#--<div class="panel panel-widget"  >-->
                        <el-row v-if="!loading">
                            <el-col :span="24" v-for="(item, index) in list" style="margin-bottom: 20px;">
                                <el-card :body-style="{ padding: '10px' }">
                                    <el-row>
                                        <el-col :span="6" class="college-img">
                                            <img :src="item.headshot" style="max-width: 80%;">
                                        </el-col>
                                        <el-col :span="8" class="person-info">
                                            <div>名称：{{item.collegeName}}</div><br>
                                            <div>官网：<a :href="item.url" target="_blank"> {{item.url}}</a></div><br>
                                            <div>最后更改时间：{{item.gmtModified}}</div><br>
                                        </el-col>
                                        <el-col :span="8" class="person-info">
                                            <div>负责人：{{item.name}}</div><br>
                                            <div>电话：{{item.phone}}</div><br>
                                            <div>邮箱：{{item.email}}</div><br>
                                        </el-col>
                                        <el-col :span="2" class="person-info">
                                            <div><a href="#" @click="openMessageWindow(item.id)">私信</a></div><br>
                                            <div><a href="#" @click="edit(item.id)">编辑</a></div><br>
                                            <div><a href="#" @click="collegeDelete(item.id)">删除</a></div><br>
                                        </el-col>
                                    </el-row>
                                </el-card>
                            </el-col>
                        </el-row>
                   <#-- </div>-->
                    <template v-if="loading">
                    <#include '/include/common/loading.ftl'>
                    </template>
                </div>
            </div>

            <template v-if="editFlag">
                <el-dialog
                        :title="college.id==0?'新增二级学院':'编辑二级学院'"
                        :visible.sync="editFlag"
                        width="35%">
                    <el-form ref="college" :model="college" label-width="80px">
                        <el-form-item label="头像">
                            <template v-if="college.headshot.trim().length > 0">
                                <el-upload
                                        class="avatar-uploader"
                                        action="/photoUpload"
                                        :show-file-list="false"
                                        :on-success="photoUploadSuccess">
                                    <img :src="college.headshot"
                                         style="max-height: 300px;max-width: 90%"><br>
                                    重新上传
                                </el-upload>
                            </template>
                            <template v-if="college.headshot.trim().length <= 0">
                                <el-upload
                                        class="avatar-uploader"
                                        action="/photoUpload"
                                        :show-file-list="false"
                                        :on-success="photoUploadSuccess">
                                    上传
                                </el-upload>
                            </template>
                        </el-form-item>
                        <el-form-item label="名称" style="width: 68%">
                            <el-input v-model="college.collegeName"></el-input>
                        </el-form-item>
                        <el-form-item label="负责人" style="width: 68%">
                            <el-input v-model="college.name"></el-input>
                        </el-form-item>
                        <el-form-item label="电话" style="width: 68%">
                            <el-input v-model="college.phone"></el-input>
                        </el-form-item>
                        <el-form-item label="邮箱" style="width: 68%">
                            <el-input v-model="college.email"></el-input>
                        </el-form-item>
                        <el-form-item label="官网" style="width: 68%">
                            <el-input v-model="college.url"></el-input>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                    <el-button @click="editFlag = false">取 消</el-button>
                    <el-button type="primary" @click="saveCollege()">保 存</el-button>
                  </span>
                </el-dialog>

            </template>

            <div class="block-pagination">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-sizes="[10, 20, 30, 50]"
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
                            <a href="/">首页</a> > 二级学院列表
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
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
                    college:{id:0,headshot:'',name:'',phone:'',email:'',collegeName:'',url:''}
                }
            },
            mounted: function () {
                this.loadCollegeList();
            },
            methods:{
                loadCollegeList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/school/college/listJson',{
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].gmtModified = self.getDate(self.list[i].gmtModified);
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                collegeDelete:function (id) {
                    var self = this;
                    this.$confirm('此操作将永久删除该二级学院, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/school/college/delete",{id:id},function (result) {
                            if (result.code == 0) {
                                for (var i=0; i<self.list.length; i++) {
                                    if (self.list[i].id == id) {
                                        self.list.splice(i, 1);
                                        self.total = self.total - 1;
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
                        this.college ={id:0,headshot:'',name:'',phone:'',email:'',collegeName:'',url:''};
                        this.editFlag = true;
                        return true;
                    } else {
                        Api.get("/school/college/info",{id:id},function (result) {
                            if (result.code == 0) {
                                self.college = result.data;
                            } else {
                                self.$message({showClose: true, message: result.msg, type: 'error'});
                            }
                        });
                        self.editFlag = true;
                    }
                },
                saveCollege:function () {
                    var self = this;
                    Api.post("/school/college/save",self.college,function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: "保存成功", type: 'success'});
                            self.loadCollegeList();
                            self.editFlag = false;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                photoUploadSuccess:function (result, file, fileList) {
                    var self = this;
                    if (result.code == 0){
                        self.college.headshot = result.data;
                    } else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
                },
                openMessageWindow:function (toId) {
                    window.open ('/message?toId='+toId, 'newwindow',
                            'height=600, width=400, top=150,left=500%, toolbar=no, menubar=no, scrollbars= no, resizable=no,location=true,status=no');
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadCollegeList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadCollegeList(this.page, this.size);
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