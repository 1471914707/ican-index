<!DOCTYPE HTML>
<html>
<head>
    <title>${college.collegeName} | 专业列表</title>
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
                            <a href="/college/activity/list"><el-collapse-item title="活动列表" name="1">
                            </el-collapse-item></a>
                            <a href="/college/teacher/list"><el-collapse-item title="教师情况" name="3">
                            </el-collapse-item></a>
                            <a href="/college/student/list"><el-collapse-item title="学生情况" name="4">
                            </el-collapse-item></a>
                            <a href="/college/major/list"><el-collapse-item title="专业审核人设置" name="5">
                            </el-collapse-item></a>
                            <el-collapse-item title="个人设置" name="6">
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
                        <h2 style="display: inline-block;text-align: center">${college.collegeName}专业列表</h2>
                    </div>
                    <el-row v-if="!loading">
                        <div class="panel panel-widget">
                            <template v-if="!loading">
                                <template v-for="(item, index) in list">
                                    {{item.name}}----<a style="cursor: pointer;color: #409EFF" @click="majorId=item.id;teacherDialog=true">{{item.teacherId==0?'选择':item.teacherId}}</a>
                                </template>
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

            <el-dialog title="选择教师" :visible.sync="teacherDialog">
                <template v-for="(item, index) in teacherList">
                    <a style="cursor: pointer;color: #409EFF;margin-right: 20px"  @click="chooseTeacher(item.id)">
                        {{item.name}}({{item.degreeName}})</a>
                </template>
            </el-dialog>

        </div>

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/school">首页</a> > 专业列表
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
                    size:100,
                    total:0,
                    list: [],
                    teacherList:[],
                    loading:false,
                    current:'--',
                    infoDialog:false,
                    teacherDialog:false,
                    majorId:0
                }
            },
            mounted: function () {
                this.loadMajorList();
            },
            methods:{
                loadMajorList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/college/major/listJson',{
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                self.loadTeacherList();
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                chooseTeacher:function (teacherId) {
                    var self = this;
                    Api.post("/college/major/teacher",{majorId:self.majorId,teacherId:teacherId},function (result) {
                        if (result.code == 0) {
                            self.loadMajorList();
                            self.$message({showClose: true, message: '设置成功', type: 'success'});
                            self.majorId = 0;
                            self.teacherDialog = false;
                        } else {
                            self.$message({showClose: true, message: '关联失败', type: 'error'});
                        }
                    });
                },
                loadTeacherList:function () {
                    var self = this;
                    Api.get('/teacherListJson',{
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.teacherList = result.data.list;
                                self.teacherTotal = result.data.total;
                                for (var i=0; i<self.teacherList.length; i++) {
                                   self.teacherList[i].degreeName = self.getDegreeName(self.teacherList[i].degree, self.teacherList[i].degreeName);
                                   for (var j=0; j<self.list.length; j++) {
                                       if (self.list[j].teacherId == self.teacherList[i].id) {
                                           self.list[j].teacherId = self.teacherList[i].name;
                                           break;
                                       }
                                   }
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
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