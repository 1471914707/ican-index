<!DOCTYPE HTML>
<html>
<head>
    <title>${college.collegeName} | 选题列表</title>
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
        <!--left-fixed -navigation-->
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
                        <div style="float: right;display: inline-block;margin-right: 1.5%;margin-top: 1%">
                            <el-row>
                                <el-col :span="10" style="margin-right: 15px;"><el-input v-model="title" placeholder="请输入标题"></el-input></el-col>
                                <el-col :span="4"><el-button type="success" icon="el-icon-search" @click="loadPaperList()"></el-button></el-col>
                                <el-col :span="3" style="margin-right: 15px;"><el-button type="primary" class="el-icon-download" @click="excel()">导出</el-button></el-col>
                                <el-col :span="4" style="margin-left: 20px;margin-top: 10px;"><el-switch v-model="paperFlag" @change="paperStudent()"></el-switch></el-col>
                            </el-row>
                        </div><br/>
                        <h2 style="display: inline-block;text-align: center">${school.schoolName}-${college.collegeName}毕业设计（论文）选题汇总</h2>
                        <h2 style="display: inline-block;text-align: center">（{{current}}届）</h2>
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
                                            prop="paper.id"
                                            label="序号"
                                            width="100">
                                    </el-table-column>
                                    <el-table-column
                                            label="指导教师"
                                            width="120">
                                        <template slot-scope="scope">
                                            <el-button type="text" size="small" @click="openBk(scope.row.teacher.id)">{{scope.row.teacher.name}}</el-button>
                                        </template>
                                    </el-table-column>
                                    <#--<el-table-column
                                            prop="teacher.name"
                                            label="指导教师"
                                            width="120">
                                    </el-table-column>-->
                                    <el-table-column
                                            prop="teacher.degreeName"
                                            label="职称"
                                            width="120">
                                    </el-table-column>
                                    <el-table-column
                                            prop="college.collegeName"
                                            label="所在学院"
                                            width="150">
                                    </el-table-column>
                                    <el-table-column
                                            prop="department.name"
                                            label="所在系（部门）"
                                            width="150">
                                    </el-table-column>
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
                                            prop="paper.minNumber"
                                            label="需要人数"
                                            width="100">
                                    </el-table-column>
                                    <el-table-column
                                            prop="paper.remark"
                                            label="备注"
                                            width="250">
                                    </el-table-column>
                                    <el-table-column
                                            label="已选人数">
                                        <template slot-scope="scope">
                                            <el-button type="text" size="small" @click="getPaperStudent(scope.row.paper.id);infoDialog=true">{{scope.row.num}}</el-button>
                                         </template>
                                    </el-table-column>
                                    <el-table-column
                                            fixed="right"
                                            label="状态">
                                        <template slot-scope="scope">
                                            <el-button type="text" size="small" @click="statusChange(scope.row.paper.id);" v-if="scope.row.paper.status==1">无效</el-button>
                                            <el-button type="text" size="small" @click="statusChange(scope.row.paper.id);" v-if="scope.row.paper.status==2">有效</el-button>
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

            <el-dialog title="学生情况" :visible.sync="infoDialog" :before-close="handleClose">
                <el-table :data="studentList">
                    <el-table-column property="current" label="届" width="100"></el-table-column>
                    <el-table-column property="majorName" label="专业"></el-table-column>
                    <el-table-column property="clazzName" label="班级"></el-table-column>
                    <el-table-column
                            label="姓名">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" @click="openStudentDetail(scope.row.id)">{{scope.row.name}}</el-button>
                        </template>
                    </el-table-column>
                    <el-table-column property="phone" label="电话"></el-table-column>
                    <el-table-column property="email" label="邮箱"></el-table-column>
                </el-table>
            </el-dialog>

        </div>

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/school">首页</a> > 选题列表
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
                    slogan:'全站最热门广告位招租！！！有意者电话联系18813960106！！！',
                    page:1,
                    size:20,
                    total:0,
                    list: [],
                    teacherList:[],
                    loading:false,
                    studentList:[],
                    current:'--',
                    title:'',
                    infoDialog:false,
                    paperFlag:false,
                }
            },
            mounted: function () {
                this.loadPaperList();
            },
            methods:{
                loadPaperList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/college/paper/listJson',{
                        activityId:activityId,
                        title:self.title,
                        departmentId:self.departmentId,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list.length > 0) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].teacher.degreeName = self.getDegreeName(self.list[i].teacher.degree, self.list[i].teacher.degreeName);
                                    if (self.list[i].paper.minNumber != self.list[i].paper.maxNumber) {
                                        self.list[i].paper.minNumber = self.list[i].paper.minNumber + '-' + self.list[i].paper.maxNumber;
                                    }
                                }
                                self.current = self.list[0].paper.current;
                            }
                            self.paperFlag = result.data.activity.paper == 2?true:false;
                            self.current = result.data.activity.current;
                            self.loading = false;
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                getPaperStudent:function (id) {
                    var self = this;
                    Api.get("/college/paper/student",{id:id},function (result) {
                       if (result.code == 0) {
                            self.studentList = result.data;
                       } else {
                           self.$message({showClose: true, message: result.msg, type: 'error'});
                       }
                    });
                },
                paperStudent:function () {
                    var self = this;
                    Api.post("/college/activity/paper",{id:activityId},function (result) {
                        if (result.code == 0) {
                            self.paperFlag = result.data ==2?true:false;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                statusChange:function (id) {
                    var self = this;
                    Api.post("/college/paper/status",{id:id},function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: '修改成功', type: 'success'});
                            for (var i=0; i<self.list.length; i++) {
                                if (id == self.list[i].paper.id){
                                    self.list[i].paper.status = result.data;
                                }
                            }
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                openStudentDetail:function (id) {
                    window.open("/college/student/detail?studentId=" + id + "&activityId=" + activityId);
                },
                excel:function () {
                    location.href = "/college/paper/excel?activityId=" + activityId;
                },
                openBk:function (id) {
                    window.open('/bk?id=' + id);
                },
                handleClose:function () {
                    this.studentList = [];
                    this.infoDialog = false;
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
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
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