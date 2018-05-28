<!DOCTYPE HTML>
<html>
<head>
    <title>${college.collegeName}|活动列表</title>
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
        .el-button--text {
            margin-right: 10px;
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
                            <a href="/college/department/list" target="_blank"><el-collapse-item title="系部-专业-班级设置" name="5">
                            </el-collapse-item></a>
                            <a href="/college/major/list" target="_blank"><el-collapse-item title="专业审核人设置" name="6">
                            </el-collapse-item></a>
                            <a href="/message/my" target="_blank"><el-collapse-item title="我的私信" name="7">
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
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <h2>活动列表<i class="el-icon-circle-plus" @click="edit(0)" style="cursor: pointer;"></i></h2>
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
                                        label="活动名称"
                                        width="200">
                                </el-table-column>
                                <el-table-column
                                        prop="current"
                                        label="对象(届)"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        prop="startTime"
                                        label="开始时间"
                                        width="150">
                                </el-table-column>
                                <el-table-column
                                        prop="endTime"
                                        label="结束时间"
                                        width="150">
                                </el-table-column>
                                <el-table-column
                                        fixed="right"
                                        label="操作"
                                        min-width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="small" @click="student(scope.row.id)">学生情况</el-button>
                                        <el-button type="text" size="small" @click="paper(scope.row.id)">选题情况</el-button>
                                        <el-button type="text" size="small" @click="project(scope.row.id)">学生项目</el-button>
                                        <el-button type="text" size="small" @click="fileArrange(scope.row.id)">共享文档</el-button>
                                        <el-button type="text" size="small" @click="arrange(scope.row.id)">流程设置</el-button>
                                        <el-button type="text" size="small" @click="rating(scope.row.id)">评审答辩</el-button>
                                        <el-button type="text" size="small" @click="counts(scope.row.id)">统计情况</el-button>
                                        <el-button type="text" size="small" @click="edit(scope.row.id)">编辑</el-button>
                                        <el-button type="text" size="small" @click="activityDelete(scope.row.id)">删除</el-button>
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


            <template v-if="editFlag">
                <el-dialog
                        :title="activity.id==0?'新增活动':'编辑活动'"
                        :visible.sync="editFlag"
                        width="35%">
                    <el-form ref="activity" :model="activity" label-width="80px">
                        <el-form-item label="活动名称" style="width: 68%">
                            <el-input v-model="activity.name"></el-input>
                        </el-form-item>
                        <el-form-item label="活动目标">
                            <el-select v-model="activity.current" placeholder="请选择某届">
                                <el-option
                                        v-for="item in 100"
                                        :key="item+1970"
                                        :label="item+1970"
                                        :value="item+1970">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="开始时间">
                            <el-date-picker
                                    v-model="activity.startTime"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    @change="startTimeChange"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束时间">
                            <el-date-picker
                                    v-model="activity.endTime"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    @change="endTimeChange"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                    <el-button @click="editFlag = false">取 消</el-button>
                    <el-button type="primary" @click="saveActivity()">确 定</el-button>
                  </span>
                </el-dialog>

            </template>

            <div class="block-pagination">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-sizes="[10, 20, 30, 40]"
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
                            <a href="/">首页</a> > 活动列表
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        <#if college.id??>
        var collegeId = ${college.id?c}
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
                    activity:{id:0,name:'',current:2018,startTime:'',endTime:''}
                }
            },
            mounted: function () {
                this.loadActivityList();
            },
            methods:{
                loadActivityList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/college/activity/listJson',{
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
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
                activityDelete:function (id) {
                    var self = this;
                    this.$confirm('此操作将永久删除该活动, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/college/activity/delete",{id:id},function (result) {
                            if (result.code == 0) {
                                for (var i=0; i<self.list.length; i++) {
                                    if (self.list[i].id == id) {
                                        self.list.splice(i, 1);
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
                        this.activity = {id:0,name:'',current:2018,startTime:'',endTime:''};
                        this.editFlag = true;
                        return true;
                    } else {
                        for (var i=0; i<this.list.length; i++) {
                            if (id == this.list[i].id) {
                                self.activity = JSON.parse(JSON.stringify(this.list[i]));
                            }
                        }
                        self.editFlag = true;
                    }
                },
                saveActivity:function () {
                    var self = this;
                    Api.post("/college/activity/save",self.activity,function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: "保存成功", type: 'success'});
                            self.loadActivityList();
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
                student:function (id) {
                    window.open('/college/student/list?activityId=' + id);
                },
                paper:function (id) {
                    window.open('/college/paper/list?activityId=' + id);
                },
                project:function (id) {
                    window.open('/college/project/list?activityId=' + id);
                },
                arrange:function (id) {
                    window.open('/arrange/list?activityId=' + id + '&collegeId=' + collegeId);
                },
                fileArrange:function (id) {
                    window.open('/file/list?activityId=' + id);
                },
                rating:function (id) {
                    window.open('/answerArrange/list?activityId=' + id);
                },
                counts:function (id) {
                    window.open('/counts/list?activityId=' + id);
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