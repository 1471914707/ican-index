<!DOCTYPE HTML>
<html>
<head>
    <title>我的选题</title>
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
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">{{activity.name}} -- 我发布的选题</h2>
        </div>
        <div id="page-wrapper" style="width: 90%">
            <el-button type="success" @click="edit(0)">增加</el-button><br><br>
            <div class="main-page">
                <!--grids-->
                <div class="grids">
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
                                            label="需要人数"
                                            width="100">
                                        <template slot-scope="scope">
                                            <span v-if="scope.row.paper.minNumber == scope.row.paper.maxNumber">{{scope.row.paper.minNumber}}</span>
                                            <span v-if="scope.row.paper.minNumber != scope.row.paper.maxNumber">{{scope.row.paper.minNumber + '-' + scope.row.paper.maxNumber}}</span>
                                        </template>
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
                                            label="操作"
                                            width="120">
                                        <template slot-scope="scope">
                                            <el-button type="text" size="small" @click="edit(scope.row.paper.id);">编辑</el-button>
                                            <el-button type="text" size="small" @click="deletePaper(scope.row.paper.id);">删除</el-button>
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


            <template v-if="editDialog">
                <el-dialog
                        title=""
                        :visible.sync="editDialog"
                        width="70%">
                    <el-form ref="" :model="paper" label-width="80px" style="width: 80%">
                        <el-form-item label="题目">
                            <el-input type="textarea" v-model="paper.title"></el-input>
                        </el-form-item>
                        <el-form-item label="要求">
                            <el-input type="textarea" v-model="paper.requires"></el-input>
                        </el-form-item>
                        <el-form-item label="备注">
                            <el-input type="textarea" v-model="paper.remark"></el-input>
                        </el-form-item>
                        <el-form-item label="最小人数">
                            <el-input-number v-model="paper.minNumber" :min="1" :max="100"></el-input-number>
                        </el-form-item>
                        <el-form-item label="最大人数">
                            <el-input-number v-model="paper.maxNumber" :min="1" :max="100"></el-input-number>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                    <el-button @click="editDialog = false">取 消</el-button>
                    <el-button type="primary" @click="savePaper()">确 定</el-button>
                  </span>
                </el-dialog>

            </template>

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
                    loading:false,
                    current:'--',
                    editDialog:false,
                    infoDialog:false,
                    activity:{},
                    studentList:[],
                    paper:{id:0,activityId:activityId,title:'',requires:'',maxNumber:1,minNumber:1,remark:''}
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
                    Api.get('/teacher/paper/listJson',{
                        activityId:activityId,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list.length > 0) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                            }
                            for (var i=0; i<self.list[i].length; i++) {
                                if (self.list[i].paper.minNumber != self.list[i].paper.maxNumber) {
                                    self.list[i].paper.minNumber = self.list[i].paper.minNumber + '-' + self.list[i].paper.maxNumber;
                                }
                            }
                            self.activity = result.data.activity;
                            self.loading = false;
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                getPaperStudent:function (id) {
                    var self = this;
                    Api.get("/teacher/paper/student",{id:id,activityId:activityId},function (result) {
                        if (result.code == 0) {
                            self.studentList = result.data;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                savePaper:function () {
                    var self = this;
                    Api.post("/teacher/paper/save",self.paper,function (result) {
                        if (result.code == 0){
                            self.$message({showClose: true, message: '保存成功', type: 'success'});
                            self.loadPaperList();
                            self.paper = {id:0,activityId:activityId,title:'',requires:'',maxNumber:1,minNumber:1,remark:''};
                            self.editDialog = false;
                        } else {
                            self.$message({showClose: true, message: '保存失败', type: 'error'});
                        }
                    });
                },
                edit:function (id) {
                    var self = this;
                    if (id > 0){
                        Api.get("/teacher/paper/info",{id:id},function (result) {
                            if (result.code == 0) {
                                self.paper = result.data;
                                self.editDialog = true;
                            }else{
                                self.paper = {id:0,activityId:activityId,title:'',requires:'',maxNumber:1,minNumber:1,remark:''};
                            }
                        });
                    } else {
                        self.editDialog = true;
                    }
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                handleClose:function () {
                    this.studentList = [];
                    this.infoDialog = false;
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
</div>
</body>
</html>