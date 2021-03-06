<!DOCTYPE HTML>
<html>
<head>
    <title>${school.schoolName} | 教师列表</title>
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
        a img{
            cursor: pointer;
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sidebar" role="navigation">
            <div class="navbar-collapse">
                <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right dev-page-sidebar mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar" id="cbp-spmenu-s1">
                    <div>
                        <el-collapse>
                            <a href="/college/activity/list" target="_blank"><el-collapse-item title="活动列表" name="1">
                            </el-collapse-item></a>
                            <a href="/college/teacher/list" target="_blank"><el-collapse-item title="教师情况" name="3">
                            </el-collapse-item></a>
                            <a href="/college/student/list" target="_blank"><el-collapse-item title="学生情况" name="4">
                            </el-collapse-item></a>
                            <a href="/college/major/list" target="_blank"><el-collapse-item title="专业审核人设置" name="5">
                            </el-collapse-item></a>
                            <a href="/message/my" target="_blank"><el-collapse-item title="我的私信" name="6">
                            </el-collapse-item></a>
                            <el-collapse-item title="个人设置" name="7">
                                <div style="color: #409EFF;cursor: pointer">
                                    <div onclick="javascript:window.location.href='/school/edit'">个人资料</div>
                                    <div onclick="javascript:window.location.href='/resetPassword'">密码修改</div>
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
                <img src="${school.banner}" style="height: 84px;width: auto">
                <div class="clearfix"> </div>
            </div>
            <div class="header-right" style="float: right;margin-right: 50px;">
                <el-row>
                    <el-col :span="10">
                        <div style="width: 1px;height: 1px;"></div>
                    </el-col>
                    <el-col :span="4">
                        <a href="/bk?id=${schoolId}" target="_blank">
                            <img src="${school.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 18%"></a>
                    </el-col>
                    <el-col :span="4">
                        <a href="/bk?id=${college.id?c}" target="_blank">
                            <img src="${college.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 18%"></a>
                    </el-col>
                    <el-col :span="6">
                        <button id="showLeftPush" style="padding-top: 30px;float:right;">
                            <img  src="http://cdn.ican.com/public/images/bars.png" style="max-width:18.003px;max-height:23.333px;"></button>
                        <div class="clearfix"></div>
                    </el-col>
                </el-row>
            </div>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper">
            <div class="main-page">
                <!--grids-->
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <h2 style="display: inline-block">教师列表</h2>
                        <div style="float: right;display: inline-block;margin-right: 1.5%;margin-top: 1%">
                            <el-row>
                                <el-col :span="12" style="margin-right: 15px;"><el-input v-model="jobId" placeholder="请输入教师证号"></el-input></el-col>
                                <el-col :span="4"><el-button type="success" icon="el-icon-search" @click="loadTeacherList()"></el-button></el-col>
                                <el-col :span="4" style="padding-left: 5px;"><el-button type="success" icon="el-icon-plus" circle @click="addTeacherFlag=true"></el-button>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                    <el-row v-if="!loading">
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
                                            prop="departmentName"
                                            label="系部"
                                            width="200">
                                    </el-table-column>
                                    <el-table-column
                                            label="姓名"
                                            width="150">
                                        <template slot-scope="scope">
                                            <el-button type="text" @click="openBk(scope.row.id)">{{scope.row.name}}</el-button>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                            prop="phone"
                                            label="电话"
                                            width="150">
                                    </el-table-column>
                                    <el-table-column
                                            prop="email"
                                            label="邮箱"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="jobId"
                                            label="教师证号"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="degreeName"
                                            label="职称"
                                            width="150"
                                            :filters="[{text: '助教', value: '1'}, {text: '讲师', value: '2'}, {text: '副教授', value: '3'},{text: '教授', value: '4'},{text: '高级工程师', value: '5'},{text: '其他', value: '6'}]"
                                            :filter-method="filterHandler"
                                            :filter-multiple="false">
                                    </el-table-column>
                                    <el-table-column
                                            prop="gmtCreate"
                                            label="注册时间"
                                            width="150">
                                    </el-table-column>
                                    <el-table-column
                                            fixed="right"
                                            label="操作"
                                            min-width="150">
                                        <template slot-scope="scope">
                                            <el-button type="text" size="small" @click="detail(scope.row.id)">查看</el-button>
                                            <el-button type="text" size="small" @click="openMessageWindow(scope.row.id)">私信</el-button>
                                            <el-button type="text" size="small" @click="deleteTeacher(scope.row.id)">删除</el-button>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </template>
                            <template v-if="loading">
                            <#include '/include/common/loading.ftl'>
                            </template>
                        </div>
                    </el-row>
                   <#-- <template v-if="loading">
                    <#include '/include/common/loading.ftl'>
                    </template>-->
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
                            <a href="/">首页</a> > 教师列表
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <template v-if="addTeacherFlag">
            <el-dialog
                    title="增加教师与系部关联"
                    :visible.sync="addTeacherFlag"
                    width="60%"
                    :before-close="handleClose">
                <el-row style="height: 500px;">
                    <el-col :span="8">
                        <#--选择教师-->
                        <div>
                        <#--搜索框-->
                            <el-input placeholder="请输入教师编号" v-model="searchJobId" class="input-with-select">
                                <el-button slot="append" icon="el-icon-search" @click="searchTeacher(1)"></el-button>
                            </el-input>
                        </div>
                        <div style="overflow-y:scroll;overflow-x: hidden;height: 450px;">
                        <#--搜索结果--><br>
                            <template v-for="(item, index) in searchTeacherList">
                                <el-row>
                                    <el-col :span="6">
                                        <img :src="item.headshot" style="height: 30px;width: 30px;" @click="selectedTeacher.id=item.id;selectedTeacher.name=item.name">
                                    </el-col>
                                    <el-col :span="16">
                                        <a @click="selectedTeacher.id=item.id;selectedTeacher.name=item.name" style="cursor: pointer">{{item.name}}({{item.degreeName}})</a>
                                    </el-col>
                                </el-row>
                            </template>
                            <template v-if="searchTeacherList.length==0">
                                未搜索到相关数据
                            </template>
                            <template v-if="searchTeacherTotal > searchTeacherList.length">
                                <a style="color: #409EFF;cursor: pointer" @click="searchTeacherPage+=1;searchTeacher(2);">查看更多...</a>
                            </template>
                        </div>
                    </el-col>
                    <el-col :span="16" style="padding-left: 20px;">
                        <div style="height: 450px">
                        <#--可选系-->
                            <div>请选择关联系部：</div>
                            <template v-for="(item, index) in departmentList">
                                <a style="margin-right: 20px;cursor: pointer;color: #409EFF" @click="selectedDepartment.id=item.id;selectedDepartment.name=item.name">{{item.name}}</a>
                            </template>
                        </div>
                        <div style="float: right;padding-right: 25px;">
                        <#--选择结果-->
                            已选择：{{selectedTeacher.name}} / {{selectedDepartment.name}}&nbsp;&nbsp;
                            <el-button type="success" @click="addDepartmentTeacher">保存</el-button>
                        </div>
                    </el-col>
                </el-row>
                <#--<div>
                    <div style="width:25%; height:500px;padding-bottom: 20px;">

                    </div>
                    <div style="padding-left: 20px;border-left: 1px solid #000000;float: right;width: 75%">

                    </div>
                </div>-->
            </el-dialog>

        </template>
    </div>


    <script>
        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    slogan: '全站最热门广告位招租！！！有意者电话联系18813960106！！！',
                    page: 1,
                    size: 20,
                    total: 0,
                    list: [],
                    loading: false,
                    degree: 0,
                    addTeacherFlag: false,
                    jobId: '',
                    searchJobId:'',
                    selectedTeacher: {id: 0, name: ''},
                    searchTeacherList:[],
                    searchTeacherTotal:0,
                    searchTeacherPage:1,
                    departmentList:[],
                    selectedDepartment:{id:0,name:''}
                }
            },
            mounted: function () {
                this.loadTeacherList();
                this.loadDepartmentList();
            },
            methods:{
                detail:function (id) {
                    window.open('/college/teacher/detail?teacherId=' + id);
                },
                loadTeacherList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/college/teacher/listJson',{
                        degree:self.degree,
                        jobId:self.jobId,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].gmtCreate = self.getDate(self.list[i].gmtCreate);
                                    self.list[i].degreeName = self.getDegreeName(self.list[i].degree,self.list[i].degreeName);
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                searchTeacher:function (type) {
                    var self = this;
                    if (type == 1){
                        self.searchTeacherPage = 1;
                    }
                    if (self.searchJobId == '') {
                        return false;
                    }
                    Api.get("/college/teacher/searchTeacher",{jobId:self.searchJobId,page:self.searchTeacherPage},function (result) {
                        if (result.code == 0) {
                            if (self.searchTeacherPage == 1) {
                                self.searchTeacherList = result.data.list;
                            } else {
                                for (var i=0; i<result.data.list.length; i++) {
                                    self.searchTeacherList.push(result.data.list[i]);
                                }
                            }
                            for (var i=0; i<self.searchTeacherList.length; i++) {
                                self.searchTeacherList[i].degreeName = self.getDegreeName(self.searchTeacherList[i].degree, self.searchTeacherList[i].degreeName);
                            }
                            self.searchTeacherTotal = result.data.total;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                loadDepartmentList:function () {
                    var self = this;
                    Api.get("/departmentListJson3",{},function (result) {
                       if (result.code == 0){
                           self.departmentList = result.data;
                       }
                    });
                },
                addDepartmentTeacher:function () {
                    var self = this;
                    Api.post("/college/teacher/addDepartmentTeacher",{teacherId:self.selectedTeacher.id,departmentId:self.selectedDepartment.id},function (result) {
                        if (result.code == 0) {
                            self.loadTeacherList();
                            self.$message({showClose: true, message: '关联成功', type: 'success'});
                            self.selectedTeacher = {id: 0, name: ''};
                            self.selectedDepartment = {id: 0, name: ''};
                            self.addTeacherFlag = false;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                deleteTeacher:function (id) {
                    var self = this;
                    this.$confirm('此操作将删除教师和我院的关联, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/college/teacher/deleteTeacher",{teacherId:id},function (result) {
                            if (result.code == 0) {
                                for (var i=0 ; i<self.list.length; i++) {
                                    if (id == self.list[i].id){
                                        self.list.splice(i, 1);
                                        break;
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
                openMessageWindow:function (toId) {
                    window.open ('/message?toId='+toId, 'newwindow',
                            'height=600, width=400, top=150,left=500%, toolbar=no, menubar=no, scrollbars= no, resizable=no,location=true,status=no');
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                getDegreeName:function (degree,degreeName) {
                    switch (degree){
                        case 1:
                            return '助教';
                        case 2:
                            return '讲师';
                        case 3:
                            return '副教授';
                        case 4:
                            return '教授';
                        case 5:
                            return '高级工程师';
                        case 6:
                            return degreeName;
                    }
                },
                filterHandler:function (value, row, column) {
                    this.degree = value;
                    this.loadTeacherList();
                },
                openBk:function (id) {
                    window.open('/bk?id=' + id);
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadTeacherList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadTeacherList(this.page, this.size);
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