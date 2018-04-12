<!DOCTYPE HTML>
<html>
<head>
    <title>学生项目</title>
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
        .el-form-item__label{
            font-weight: bolder;
        }
        .el-form-item{
            width:33%;
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">学生申报项目</h2>
        </div>
        <div id="page-wrapper" style="width: 90%">
            <div class="main-page">
                <!--grids-->
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <div style="float: right;display: inline-block;">
                            <el-row>
                                <el-col :span="12">
                                    <el-select v-model="followType" placeholder="请选择审核范围" @change="loadProjectList()">
                                        <el-option label="普通审核" value="1"></el-option>
                                        <el-option label="专业审核" value="2"></el-option>
                                    </el-select>
                                </el-col>
                                <el-col :span="12">
                                    <el-select v-model="status" placeholder="请选择状态" @change="loadProjectList()">
                                        <el-option label="全部" value="0"></el-option>
                                        <el-option label="不通过" value="1"></el-option>
                                        <el-option label="通过" value="2"></el-option>
                                    </el-select>
                                </el-col>
                            </el-row>
                        </div><br/><br/><br/>
                    </div>
                    <el-row v-if="!loading">
                        <div class="panel panel-widget">
                            <template v-if="!loading">
                                <el-table
                                        :data="list"
                                        style="width: 100%">
                                    <el-table-column type="expand">
                                        <template slot-scope="props">
                                            <el-form label-position="left" inline class="demo-table-expand">
                                                <el-form-item label="学生姓名">
                                                    <span>{{ props.row.student.name }}({{ props.row.student.jobId }})</span>
                                                </el-form-item>
                                                <el-form-item label="所属专业">
                                                    <span>{{ props.row.collegeName }}-{{ props.row.majorName }}</span>
                                                </el-form-item>
                                                <br/>
                                                <el-form-item label="项目名称">
                                                    <span>{{ props.row.project.title }}</span>
                                                </el-form-item>
                                                <el-form-item label="申报时间">
                                                    <span>{{ props.row.project.gmtCreate }}</span>
                                                </el-form-item>
                                                <el-form-item label="来源课题">
                                                    <span>{{ props.row.paper.title }}</span>
                                                </el-form-item>
                                                <br>
                                                <el-form-item label="指导教师">
                                                    <span>{{ props.row.teacher.name }}</span>
                                                </el-form-item>
                                                <el-form-item label="教师职称">
                                                    <span>{{ props.row.teacher.degreeName }}</span>
                                                </el-form-item>
                                                <el-form-item label="导师联系电话">
                                                    <span>{{ props.row.teacher.phone }}</span>
                                                </el-form-item>
                                                <el-form-item label="导师联系邮箱">
                                                    <span>{{ props.row.teacher.email }}</span>
                                                </el-form-item>

                                            </el-form>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                            label="序号"
                                            prop="project.id">
                                    </el-table-column>
                                    <el-table-column
                                            label="姓名"
                                            prop="student.name">
                                    </el-table-column>
                                    <el-table-column
                                            label="所在系"
                                            prop="departmentName">
                                    </el-table-column>
                                    <el-table-column
                                            label="专业"
                                            prop="majorName">
                                    </el-table-column>
                                    <el-table-column
                                            label="班级"
                                            prop="clazzName">
                                    </el-table-column>
                                    <el-table-column
                                            label="导师"
                                            prop="teacher.name">
                                    </el-table-column>
                                    <el-table-column
                                            label="题目"
                                            prop="project.title">
                                    </el-table-column>
                                    <el-table-column
                                            label="工作量"
                                            prop="project.size">
                                    </el-table-column>
                                    <el-table-column
                                            label="难度"
                                            prop="project.difficulty">
                                    </el-table-column>
                                    <el-table-column
                                            label="预计人数"
                                            prop="project.number">
                                    </el-table-column>
                                    <el-table-column
                                            label="状态">
                                        <template slot-scope="scope">
                                            <span v-if="scope.row.project.status == 1">未通过</span>
                                            <span v-if="scope.row.project.status == 2">通过</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column
                                            fixed="right"
                                            label="操作">
                                        <template slot-scope="scope">
                                            <el-button type="text" size="small" @click="followFun(scope.row.project.id);" v-if="followType=='2'">专业审核</el-button>
                                            <el-button type="text" size="small" @click="followFun(scope.row.project.id);" v-if="followType!='2'">审核</el-button>
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

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/school">首页</a> > 项目列表
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <el-dialog
                title="审核"
                :visible.sync="followDialog"
                width="50%">
            <el-input
                    type="textarea"
                    :rows="2"
                    :cols="10"
                    placeholder="请输入内容"
                    v-model="follow.content">
            </el-input><br><br>
            <el-select v-model="follow.mode" placeholder="请选择审核状态">
                <el-option label="不通过" value="1"></el-option>
                <el-option label="通过" value="2"></el-option>
            </el-select>
            <el-button @click="followDialog = false;followId=0;follow.mode=null;follow.content=''">取 消</el-button>
            <el-button type="primary" @click="saveFollow()">确 定</el-button>
            <br><br>

            <el-table
                    :data="followList"
                    style="width: 100%">
                <el-table-column
                        prop="followUserName"
                        label="审核人"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="content"
                        label="内容"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="mode"
                        label="意见">
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>


    <script>
        <#if activityId??>
        var activityId = ${activityId}
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
                    title:'',
                    current:'--',
                    followType:'1',
                    status:'0',
                    followId:0,
                    followDialog:false,
                    followList:[],
                    follow:{mode:null,content:''}
                }
            },
            mounted: function () {
                this.loadProjectList();
            },
            methods:{
                loadProjectList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/teacher/project/listJson',{
                        activityId:activityId,
                        collegeId:collegeId,
                        type:self.followType,
                        status:self.status,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].project.gmtCreate = self.list[i].project.gmtCreate.split(" ")[0];
                                    self.list[i].teacher.degreeName = self.getDegreeName(self.list[i].teacher.degree, self.list[i].teacher.degreeName);
                                }
                                if (self.list.length > 0) {
                                    self.current = self.list[0].project.current;
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                followFun:function (id) {
                    this.followId = id;
                    var self = this;
                    Api.get("/teacher/project/followList",{id:id},function (result) {
                        if (result.code == 0) {
                            self.followList = result.data;
                            for (var i=0; i<self.followList.length; i++) {
                                if (self.followList[i].mode == 2){
                                    self.followList[i].mode = '通过';
                                } else {
                                    self.followList[i].mode = '不通过';
                                }
                            }
                        }
                    });
                    this.followDialog = true;
                },
                saveFollow:function () {
                    var self = this;
                    Api.get("/teacher/project/followSave",{
                        id:self.followId,
                        content:self.follow.content,
                        mode:self.follow.mode,
                        type:self.followType
                    },function (result) {
                        if (result.code == 0) {
                            self.followFun(self.followId);
                        }
                    });
                    this.followDialog = true;
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
                    this.loadProjectList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadProjectList(this.page, this.size);
                }
            }
        });
    </script>
</div>
</body>
</html>