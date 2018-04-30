<!DOCTYPE HTML>
<html>
<head>
    <title>学生列表</title>
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
        <div class="sticky-header header-section ">
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">学生列表</h2>
            <#--<div class="header-right" style="float: right;margin-right: 50px;line-height: 68px;">
                <el-row>
                    <el-col :xs="0" :sm="4" :md="6" :lg="6" :xl="8">
                        <div style="width: 1px;height: 1px;"></div>
                    </el-col>
                    <el-col :xs="12" :sm="8" :md="8" :lg="6" :xl="8">
                        <a href="/bk?id=${teacher.id}" target="_blank">
                            <img src="${teacher.headshot}" style="width: 50px;height: 50px;border-radius: 50%;margin-top: 10%"></a>
                    </el-col>
                    <el-col :xs="0" :sm="8" :md="6" :lg="6" :xl="3">
                        <a href="/teacher/edit" target="_blank">个人资料</a>
                    </el-col>
                    <el-col :xs="0" :sm="4" :md="4" :lg="3" :xl="3">
                        <a href="/logout">退出</a>
                    </el-col>
                </el-row>
            </div>-->
        </div>
        <div id="page-wrapper" style="width: 90%">
            <div class="main-page">
                <!--grids-->
                <div class="grids">
                  <#--  <div class="progressbar-heading grids-heading">
                        <h2 style="display: inline-block"></h2>
                    </div>-->
                    <el-row v-if="!loading">
                        <div class="panel panel-widget">
                            <template v-if="!loading">
                                <el-table
                                        :data="list"
                                        style="width: 100%"
                                >
                                    <el-table-column
                                            style="max-width: 40px;"
                                            prop="jobId"
                                            label="学号"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="departmentName"
                                            label="系"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="majorName"
                                            label="专业"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="current"
                                            label="届"
                                            width="150">
                                    </el-table-column>
                                    <el-table-column
                                            prop="clazzName"
                                            label="班级"
                                            width="180">
                                    </el-table-column>
                                    <el-table-column
                                            prop="name"
                                            label="姓名"
                                            width="180">
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

        </div>
    </div>


    <script>
        <#if activityId??>
        var activityId = ${activityId?c}
        <#else>
        var activityId = 0;
        </#if>
        <#if collegeId??>
        var collegeId = ${collegeId?c}
        <#else>
        var collegeId = 0;
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
                    loading:false,
                }
            },
            mounted: function () {
                this.loadStudentList();
            },
            methods:{
                detail:function (id) {
                    if (activityId > 0) {
                        window.open('/teacher/student/detail?activityId='+activityId+'&studentId=' + id);
                    } else {
                        window.open('/teacher/student/detail?studentId=' + id);
                    }
                },
                loadStudentList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/teacher/student/listJson',{
                        activityId:activityId,
                        collegeId:collegeId,
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
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
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                openMessageWindow:function (toId) {
                    window.open ('/message?toId='+toId, 'newwindow',
                            'height=600, width=400, top=150,left=500%, toolbar=no, menubar=no, scrollbars= no, resizable=no,location=true,status=no');
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadStudentList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadStudentList(this.page, this.size);
                }
            }
        });
    </script>

</div>
</body>
</html>