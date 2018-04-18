<!DOCTYPE HTML>
<html>
<head>
    <title>答辩安排列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">{{activity.name}}--答辩安排</h2>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper" style="width:90%">
            <div class="main-page">
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <h2>安排列表<i class="el-icon-circle-plus" @click="edit(0)" style="cursor: pointer;"></i></h2>
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
                                        label="安排名称"
                                        width="200">
                                    <template slot-scope="scope">
                                        <a @click="openGroups(scope.row.id)" style="cursor: pointer;color: #409EFF">{{scope.row.name}}</a>
                                    </template>
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
                                        prop="ratio1"
                                        label="指导教师评分比例(%)"
                                        width="150">
                                </el-table-column>
                                <el-table-column
                                        prop="ratio2"
                                        label="小组教师评分比例(%)"
                                        width="150">
                                </el-table-column>
                                <el-table-column
                                        prop="type"
                                        label="类型"
                                        width="120">
                                </el-table-column>
                                <template v-if="role == 4">
                                <el-table-column
                                        fixed="right"
                                        label="操作"
                                        min-width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" v-if="role==4" @click="openRating(scope.row.id)">成绩</el-button>
                                        <el-button type="text" size="small" @click="edit(scope.row.id)">编辑</el-button>
                                        <el-button type="text" size="small" @click="answerArrangeDelete(scope.row.id)">删除</el-button>
                                    </template>
                                </el-table-column>
                                </template>
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
                        :title="answerArrange.id==0?'新增答辩安排':'编辑答辩安排'"
                        :visible.sync="editFlag"
                        width="35%">
                    <el-form ref="" :model="answerArrange" label-width="150px">
                        <el-form-item label="答辩名称" style="width: 68%">
                            <el-input v-model="answerArrange.name"></el-input>
                        </el-form-item>
                        <el-form-item label="开始时间">
                            <el-date-picker
                                    v-model="answerArrange.startTime"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    @change="startTimeChange"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="结束时间">
                            <el-date-picker
                                    v-model="answerArrange.endTime"
                                    type="date"
                                    value-format="yyyy-MM-dd"
                                    @change="endTimeChange"
                                    placeholder="选择日期">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="指导教师评分比例(%)">
                            <el-input-number v-model="answerArrange.ratio1" :min="1" :max="100"
                                             @change="handleRatio1Change"></el-input-number>
                        </el-form-item>
                        <el-form-item label="小组教师评分比例(%)">
                            <el-input-number v-model="answerArrange.ratio2" step="10" :min="1" :max="100" disabled="true"></el-input-number>
                        </el-form-item>
                        <el-form-item label="答辩类型">
                            <el-select v-model="answerArrange.type" placeholder="请选择答辩类型">
                                <el-option label="非正式" value="1"></el-option>
                                <el-option label="正式" value="2"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-form>
                    <span slot="footer" class="dialog-footer">
                    <el-button @click="editFlag = false">取 消</el-button>
                    <el-button type="primary" @click="saveAnswerArrange()">确 定</el-button>
                  </span>
                </el-dialog>

            </template>
        </div>

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/">首页</a> > 答辩安排列表
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
                    activity:{},
                    list: [],
                    loading:false,
                    editFlag:false,
                    role:0,
                    answerArrange:{id:0,name:'',activityId:activityId,startTime:'',endTime:'',ratio1:0,ratio2:0,type:'0'}
                }
            },
            mounted: function () {
                this.loadAnswerArrangeList();
            },
            methods:{
                loadAnswerArrangeList:function () {
                    var self = this;
                    self.loading = true;
                    Api.get('/answerArrange/listJson',{
                        activityId:activityId
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.activity = result.data.activity;
                                self.role = result.data.role;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].startTime = self.getDate(self.list[i].startTime);
                                    self.list[i].endTime = self.getDate(self.list[i].endTime);
                                    self.list[i].type = self.list[i].type == 2 ? '正式' : '非正式';
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                answerArrangeDelete:function (id) {
                    var self = this;
                    this.$confirm('此操作将永久删除该答辩安排, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/answerArrange/delete",{id:id},function (result) {
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
                        this.answerArrange = {id:0,name:'',activityId:activityId,startTime:'',endTime:'',ratio1:0,ratio2:0,type:'1'};
                    } else {
                        for (var i=0; i<this.list.length; i++) {
                            if (this.list[i].id == id) {
                                this.answerArrange = JSON.parse(JSON.stringify(this.list[i]));
                                this.answerArrange.type = this.answerArrange.type == '正式'?'2':'1';
                            }
                        }
                    }
                    this.editFlag = true;
                    return true;
                },
                saveAnswerArrange:function () {
                    var self = this;
                    Api.post("/answerArrange/save",self.answerArrange,function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: "保存成功", type: 'success'});
                            self.loadAnswerArrangeList();
                            self.editFlag = false;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                handleRatio1Change:function (ratio1) {
                    this.answerArrange.ratio2 = 100 - ratio1;
                },
                openGroups:function (id) {
                    window.location.href = '/answerArrange/groups?activityId='+activityId+'&answerId=' + id;
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                openRating:function (id) {
                    window.open("/college/rating?activityId=" + activityId  + "&answerId=" + id);
                }
            }
        });
    </script>
</div>
</body>
</html>