<!DOCTYPE HTML>
<html>
<head>
    <title>普通管理员列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
<#include '/include/cssjs_common_new.ftl'>
    <script>
        /*     new WOW().init();*/
    </script>
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
        <!--left-fixed -navigation-->
        <div class="sidebar" role="navigation">
            <div class="navbar-collapse">
                <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right dev-page-sidebar mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar" id="cbp-spmenu-s1">
                    <div>
                        <el-collapse>
                            <a href="www.baidu.com"><el-collapse-item title="一致性 Consistency" name="1">
                            </el-collapse-item></a>
                        </el-collapse>
                    </div>
                </nav>
            </div>
        </div>
        <div class="sticky-header header-section ">
            <div class="header-left">
                <div class="logo">
                    <a href="index.html">
                        <ul>
                            <li><img src="http://cdn.ican.com/public/images/logo.png" alt="" /></li>
                            <li><h1>Ican</h1></li>
                            <div class="clearfix"> </div>
                        </ul>
                    </a>
                </div>
                <div class="header-right header-right-grid">
                    <div class="profile_details_left">
                        <div class="clearfix"></div>
                    </div>
                </div>

                <div class="clearfix"></div>
            </div>
            <div class="header-right" style="float: right;">
                <div class="profile_details">
                </div>
                <button id="showLeftPush"><img  src="http://cdn.ican.com/public/images/bars.png" style="max-width:18.003px;max-height:23.333px;"></button>
                <div class="clearfix"></div>
            </div>
            <div class="clearfix"></div>
        </div>
        <div id="page-wrapper">
            <div class="main-page">
                <!--grids-->
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <h2>普通管理员列表&nbsp;&nbsp;<i class="el-icon-circle-plus" @click="edit(0)" style="cursor: pointer;"></i></h2>
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
                                        label="id"
                                        width="80">
                                </el-table-column>
                                <el-table-column
                                        prop="name"
                                        label="姓名"
                                        width="180">
                                </el-table-column>
                                <el-table-column
                                        prop="sex"
                                        label="性别"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        prop="phone"
                                        label="电话"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        prop="email"
                                        label="邮箱"
                                        width="180">
                                </el-table-column>
                                <el-table-column
                                        prop="gmtCreate"
                                        label="创建时间"
                                        width="100">
                                </el-table-column>
                                <el-table-column
                                        fixed="right"
                                        label="操作"
                                        min-width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="small" @click="edit(scope.row.id)">修改</el-button>
                                        <el-button type="text" size="small" @click="delete(scope.row.id)">删除</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </template>
                        <template v-if="loading">
                        <#include '/include/common/loading.ftl'>
                        </template>

                        <el-dialog
                                :title="admin.id>0?'修改管理员信息':'创建管理员信息'"
                                :visible.sync="editFlag"
                                width="30%">
                            <div>
                                <el-form :model="admin" label-width="80px" :rules="rules" ref="adminForm">
                                    <el-form-item label="名字" prop="name">
                                        <el-input v-model="admin.name"></el-input>
                                    </el-form-item>
                                    <el-form-item label="电话" prop="phone">
                                        <el-input v-model="admin.phone"></el-input>
                                    </el-form-item>
                                    <el-form-item label="邮箱" prop="email">
                                        <el-input v-model="admin.email"></el-input>
                                    </el-form-item>
                                    <el-form-item label="性别" prop="sex">
                                        <el-select v-model="admin.sex" placeholder="请选择性别">
                                            <el-option label="男" :value="1"></el-option>
                                            <el-option label="女" :value="2"></el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="角色" prop="role">
                                        <el-select v-model="admin.role" placeholder="请选择角色">
                                            <el-option label="普通管理员" :value="2"></el-option>
                                            <el-option label="超级管理员" :value="1"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="saveAdmin('adminForm')">确 定</el-button>
                                <el-button @click="editFlag = false; admin={}">取 消</el-button>
                            </span>
                        </el-dialog>

                    </div>
                </div>
            </div>

            <div class="block-pagination">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-sizes="[2, 4, 6, 8]"
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
                            <a href="/">首页</a> > 普通管理员列表
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
                    admin:{},
                    page:1,
                    size:2,
                    total:0,
                    list: [],
                    editFlag:false,
                    loading:false,
                    rules: {
                        name: [
                            {required: true, message: '请输入该项', trigger: 'blur'}
                        ],
                        phone: [
                            {required: true, message: '请输入该项', trigger: 'blur'}
                        ],
                        email: [
                            {required: true, message: '请输入该项', trigger: 'blur'}
                        ],
                        sex: [
                            {required: true, message: '请输入该项', trigger: 'blur'}
                        ],
                        role: [
                            {required: true, message: '请输入该项', trigger: 'blur'}
                        ]
                    }
                }
            },
            mounted: function () {
                this.loadAdminList();
            },
            methods:{
                loadAdminList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 5;
                    Api.get('/admin/super/adminList',{
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                for (var i=0; i<self.list.length; i++){
                                    self.list[i].gmtCreate = self.getDate(self.list[i].gmtCreate);
                                    if (self.list[i].sex == 1){
                                        self.list[i].sex = '男';
                                    } else if (self.list[i].sex == 2){
                                        self.list[i].sex = '女';
                                    } else {
                                        self.list[i].sex = '';
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
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                edit:function (id) {
                    this.editFlag = true;
                    if (id <= 0) {
                        this.admin = {id:0,headshot:"",name:"",sex:"",role:"",phone:"",email:"",password:""};
                        return true;
                    }
                    var self = this;
                    Api.get("/admin/super/adminInfo",{id:id},function (result) {
                        if (result.code == 0) {
                            self.admin = result.data;
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.editFlag = false;
                        }
                    });
                },
                saveAdmin:function (formName) {
                    var self = this;
                    self.$refs[formName].validate(function (valid) {
                        if (valid) {
                            Api.post("/admin/super/save", self.admin, function (result) {
                                if (result.code == 0) {
                                    self.$message({showClose: true, message: '保存成功', type: 'success'});
                                    self.loadAdminList();
                                    self.editFlag = false;
                                }else {
                                    self.$message({showClose: true, message: result.msg, type: 'error'});
                                }
                            });
                        } else {
                            console.log(valid);
                            return false;
                        }
                    });
                },
                delete:function (id) {
                    this.$confirm('此操作将永久删除此管理员, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/admin/super/delete", {id:id}, function (result) {
                            if (result.code == 0) {
                                self.$message({showClose: true, message: '删除成功', type: 'success'});
                            }else {
                                self.$message({showClose: true, message: result.msg, type: 'error'});
                            }
                        });
                    }).catch(function () {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadAdminList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadAdminList(this.page, this.size);
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
        
        function disableOther( button ) {
            if( button !== 'showLeftPush' ) {
                classie.toggle( showLeftPush, 'disabled' );
            }
        }
    </script>

</div>
</body>
</html>