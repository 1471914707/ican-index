<!DOCTYPE HTML>
<html>
<head>
    <title>系部-专业-班级设置</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
    <style>
        .el-radio__label{
            font-size: 16px;
            color: #000;
        }
        i {
           cursor: pointer;
        }
        .card-title{
            font-weight: bolder;
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">${college.collegeName}--系部-专业-班级设置</h2>
        </div>
        <div id="page-wrapper" style="width: 90%">
            <div class="main-page">
                <div class="grids">
                    <div class="panel panel-widget">
                        <template v-if="!loading">
                            <el-row>
                                <el-col :span="7">
                                    <el-card class="box-card">
                                        <div slot="header" class="clearfix">
                                            <span class="card-title">系部列表</span>
                                            <el-button style="float: right; padding: 3px 0" type="text">
                                                <span @click="departmentAdd(-1)">新增</span>&nbsp;&nbsp;
                                                <span @click="departmentEditFlag=!departmentEditFlag">编辑</span></el-button>
                                        </div>
                                        <template v-for="(item, index) in departmentList">
                                            <el-radio v-model="departmentId" :label="item.id">{{item.name}}</el-radio>
                                            <template v-if="departmentEditFlag">
                                                <i class="el-icon-edit-outline" @click="departmentAdd(index)"></i>
                                                <i class="el-icon-close" @click="departmentDelete(item.id)"></i>
                                            </template>
                                            <br><br>
                                        </template>
                                    </el-card>
                                </el-col>
                                <el-col :span="7">
                                    <el-card class="box-card">
                                        <div slot="header" class="clearfix">
                                            <span class="card-title">专业列表</span>
                                            <el-button style="float: right; padding: 3px 0" type="text">
                                                <span @click="majorAdd(-1)">新增&nbsp;&nbsp;
                                                <span @click="majorEditFlag=!majorEditFlag">编辑</span></el-button>
                                        </div>
                                        <template v-for="(item, index) in changeMajorList">
                                            <el-radio v-model="majorId" :label="item.id">{{item.name}}</el-radio>
                                            <template v-if="majorEditFlag">
                                                <i class="el-icon-edit-outline" @click="majorAdd(index)"></i>
                                                <i class="el-icon-close" @click="majorDelete(item.id)"></i>
                                            </template>
                                            <br><br>
                                        </template>
                                    </el-card>
                                </el-col>
                                <#--<el-col :span="3">
                                    <el-card class="box-card">
                                        <div slot="header" class="clearfix">
                                            <span class="card-title">年级（届）</span>
                                        </div>
                                        <template v-for="item in 5">
                                            <el-radio v-model="current" :label="item+2017">{{item+2017}}</el-radio>
                                            <br><br>
                                        </template>
                                    </el-card>
                                </el-col>-->
                                <el-col :span="7">
                                    <el-card class="box-card">
                                        <div slot="header" class="clearfix">
                                            <span class="card-title">班级列表</span>
                                            <el-button style="float: right; padding: 3px 0" type="text">
                                                <span @click="clazzAdd(-1)">新增</span>&nbsp;&nbsp;
                                                <span @click="clazzEditFlag=!clazzEditFlag">编辑</span></el-button>
                                        </div>
                                        <el-select v-model="current" placeholder="请选择届" @change="changeCurrent()">
                                            <el-option
                                                    v-for="item in 10"
                                                    :key="item+2017"
                                                    :label="item+2017"
                                                    :value="item+2017">
                                            </el-option>
                                        </el-select><br><br>
                                        <template v-for="(item, index) in changeClazzList">
                                            <el-radio v-model="clazzId" :label="item.id">{{item.current}}届--{{item.name}}</el-radio>
                                            <template v-if="clazzEditFlag">
                                                <i class="el-icon-edit-outline" @click="clazzAdd(index)"></i>
                                                <i class="el-icon-close" @click="clazzDelete(item.id)"></i>
                                            </template>
                                            <br><br>
                                        </template>
                                    </el-card>
                                </el-col>
                            </el-row>
                        </template>
                        <template v-if="loading">
                        <#include '/include/common/loading.ftl'>
                        </template>
                    </div>
                </div>
            </div>

        </div>

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/">首页</a> > 系部-专业-班级设置
                        </p>
                    </div>
                </div>
            </div>
        </div>

        <el-dialog
                title="新增/编辑--系部"
                :visible.sync="departmentAddFlag"
                width="30%">
            <el-input v-model="department.name" placeholder="请输入名称"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="departmentAddFlag = false">取 消</el-button>
                <el-button type="primary" @click="saveDepartment()">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog
                title="新增/编辑--专业"
                :visible.sync="majorAddFlag"
                width="30%">
            <el-input v-model="major.name" placeholder="请输入名称"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="majorAddFlag = false">取 消</el-button>
                <el-button type="primary" @click="saveMajor()">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog
                title="新增/编辑--班级"
                :visible.sync="clazzAddFlag"
                width="30%">
            <el-select v-model="clazz.current" placeholder="请选择届">
                <el-option
                        v-for="item in 10"
                        :key="item+2017"
                        :label="item+2017"
                        :value="item+2017">
                </el-option>
            </el-select><br><br>
            <el-input v-model="clazz.name" placeholder="请输入名称"></el-input>
            <span slot="footer" class="dialog-footer">
                <el-button @click="clazzAddFlag = false">取 消</el-button>
                <el-button type="primary" @click="saveClazz()">确 定</el-button>
            </span>
        </el-dialog>
    </div>

    <script>
        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    list: [],
                    departmentList:[],
                    departmentTotal:0,
                    majorList:[],
                    changeMajorList:[],
                    majorTotal:[],
                    clazzList:[],
                    changeClazzList:[],
                    clazzTotal:0,
                    departmentEditFlag:false,
                    majorEditFlag:false,
                    clazzEditFlag:false,
                    loading:false,
                    departmentId:0,
                    majorId:0,
                    clazzId:0,
                    current:2018,
                    department:{id:0,schoolId:0,collegeId:0,name:''},
                    major:{id:0,schoolId:0,collegeId:0,departmentId:0,teacherId:0,name:''},
                    clazz:{id:0,schoolId:0,collegeId:0,departmentId:0,majorId:0,current:null,name:''},
                    departmentAddFlag:false,
                    majorAddFlag:false,
                    clazzAddFlag:false
                }
            },
            watch:{
                departmentId:function (newVal, oldVal) {
                    /*alert("该表了系部ID")*/
                    this.changeMajorList = [];
                    if (this.departmentList.length > 1){
                        for (var j=0; j<this.majorList.length; j++){
                            if (this.majorList[j].departmentId == this.departmentId){
                                this.changeMajorList.push(JSON.parse(JSON.stringify(this.majorList[j])));
                            }
                        }
                    }
                    this.changeClazzList = [];
                    for (var j=0; j<this.clazzList.length; j++){
                        if (this.clazzList[j].departmentId == this.departmentId){
                            this.changeClazzList.push(JSON.parse(JSON.stringify(this.clazzList[j])));
                        }
                    }
                    this.current = null;
                },
                majorId:function (newVal, oldVal) {
                   /* alert("该表了专业ID")*/
                    this.changeClazzList = [];
                    if (this.majorList.length > 1){
                        for (var j=0; j<this.clazzList.length; j++){
                            if (this.clazzList[j].majorId == this.majorId){
                                this.changeClazzList.push(JSON.parse(JSON.stringify(this.clazzList[j])));
                            }
                        }
                    }
                    this.current = null;
                }
            },
            mounted: function () {
                this.loadAllList();
            },
            methods:{
                loadAllList:function () {
                    var self = this;
                    self.loading = true;
                    Api.get('/college/department/allListJson',{},function (result) {
                        if (result.code == 0) {
                            self.departmentList = result.data.departmentList;
                            self.departmentTotal = result.data.departmentTotal;
                            self.majorList = result.data.majorList;
                            self.majorTotal = result.data.majorTotal;
                            self.clazzList = result.data.clazzList;
                            self.clazzTotal = result.data.clazzTotal;
                            if (self.departmentList.length > 1){
                                self.departmentId = self.departmentList[0].id;
                                self.changeMajorList = [];
                                for (var j=0; j<self.majorList.length; j++){
                                    if (self.majorList[j].departmentId == self.departmentId){
                                        self.changeMajorList.push(JSON.parse(JSON.stringify(self.majorList[j])));
                                    }
                                }
                                if (self.changeMajorList.length > 1){
                                    self.changeClazzList = [];
                                    self.majorId = self.changeMajorList[0].id;
                                    for (var j=0; j<self.clazzList.length; j++){
                                        if (self.majorId == self.clazzList[j].majorId){
                                            self.changeClazzList.push(JSON.parse(JSON.stringify(self.clazzList[j])));
                                        }
                                    }
                                }
                            }
                            self.loading = false;
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                changeCurrent:function () {
                    this.changeClazzList = [];
                    if (this.clazzList.length > 1){
                        for (var j=0; j<this.clazzList.length; j++){
                            if (this.clazzList[j].current == this.current && this.clazzList[j].majorId == this.majorId){
                                this.changeClazzList.push(JSON.parse(JSON.stringify(this.clazzList[j])));
                            }
                        }
                    }
                },
                departmentAdd:function (index) {
                    if (index < 0){
                        this.department = {id:0,schoolId:0,collegeId:0,name:''};
                    } else {
                        this.department = JSON.parse(JSON.stringify(this.departmentList[index]));
                    }
                    this.departmentAddFlag = true;
                },
                majorAdd:function (index) {
                    if (index < 0){
                        this.major = {id:0,schoolId:0,collegeId:0,departmentId:this.departmentId,teacherId:0,name:''};
                    } else {
                        this.major = JSON.parse(JSON.stringify(this.changeMajorList[index]));
                    }
                    this.majorAddFlag = true;
                },
                clazzAdd:function (index) {
                    if (index < 0){
                        this.clazz = {id:0,schoolId:0,collegeId:0,departmentId:this.departmentId,majorId:this.majorId,current:null,name:''};
                    } else {
                        this.clazz = JSON.parse(JSON.stringify(this.changeClazzList[index]));
                    }
                    this.clazzAddFlag = true;
                },
                departmentDelete:function (id) {
                    var self = this;
                    this.$confirm('此操作将永久删除该系部, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/college/department/departmentDelete",{id:id},function (result) {
                            if (result.code == 0) {
                                for (var i=0; i<self.departmentList.length; i++) {
                                    if (self.departmentList[i].id == id) {
                                        self.departmentList.splice(i, 1);
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
                majorDelete:function (id) {
                    var self = this;
                    this.$confirm('此操作将永久删除该专业, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/college/department/majorDelete",{id:id},function (result) {
                            if (result.code == 0) {
                                for (var i=0; i<self.majorList.length; i++) {
                                    if (self.majorList[i].id == id) {
                                        self.majorList.splice(i, 1);
                                    }
                                }
                                for (var i=0; i<self.changeMajorList.length; i++) {
                                    if (self.changeMajorList[i].id == id) {
                                        self.changeMajorList.splice(i, 1);
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
                clazzDelete:function (id) {
                    var self = this;
                    this.$confirm('此操作将永久删除该班级, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post("/college/department/clazzDelete",{id:id},function (result) {
                            if (result.code == 0) {
                                for (var i=0; i<self.clazzList.length; i++) {
                                    if (self.clazzList[i].id == id) {
                                        self.clazzList.splice(i, 1);
                                    }
                                }
                                for (var i=0; i<self.changeClazzList.length; i++) {
                                    if (self.changeClazzList[i].id == id) {
                                        self.changeClazzList.splice(i, 1);
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
                saveDepartment:function () {
                    var self = this;
                    var add = false;
                    if (self.department.id <= 0){
                        add = true;
                    }
                    Api.post("/college/department/departmentSave",self.department,function (result) {
                        if (result.code == 0){
                            //是增加
                            var departmentNew = result.data;
                            if (add) {
                                self.departmentList.push(departmentNew);
                            } else {
                                //修改成功
                                for (var i=0; i<self.departmentList.length; i++){
                                    if (departmentNew.id == self.departmentList[i].id){
                                        self.departmentList[i].name = departmentNew.name;
                                        break;
                                    }
                                }
                            }
                            self.$message({showClose: true, message: "保存成功", type: 'success'});
                            self.departmentAddFlag = false;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                saveMajor:function () {
                    var self = this;
                    var add = false;
                    if (self.department.id <= 0){
                        add = true;
                    }
                    Api.post("/college/department/majorSave",self.major,function (result) {
                        if (result.code == 0){
                            //是增加
                            var majorNew = result.data;
                            if (add) {
                                self.majorList.push(majorNew);
                                self.changeMajorList.push(majorNew);
                            } else {
                                //修改成功
                                for (var i=0; i<self.majorList.length; i++){
                                    if (majorNew.id == self.majorList[i].id){
                                        self.majorList[i].name = majorNew.name;
                                        break;
                                    }
                                }
                                for (var i=0; i<self.changeMajorList.length; i++){
                                    if (majorNew.id == self.changeMajorList[i].id){
                                        self.changeMajorList[i].name = majorNew.name;
                                    }
                                }
                            }
                            self.$message({showClose: true, message: "保存成功", type: 'success'});
                            self.majorAddFlag = false;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                saveClazz:function () {
                    var self = this;
                    /*if (self.clazz.current || self.clazz.current <= 0 || self.clazz.name.trim() == ''){
                        return false;
                    }*/
                    var add = false;
                    if (self.department.id <= 0){
                        add = true;
                    }
                    Api.post("/college/department/clazzSave",self.clazz,function (result) {
                        if (result.code == 0){
                            //是增加
                            var clazzNew = result.data;
                            if (add) {
                                self.clazzList.push(clazzNew);
                                self.changeClazzList.push(clazzNew);
                            } else {
                                //修改成功
                                for (var i=0; i<self.clazzList.length; i++){
                                    if (clazzNew.id == self.majorList[i].id){
                                        self.majorList[i].name = majorNew.name;
                                        break;
                                    }
                                }
                                for (var i=0; i<self.changeClazzList.length; i++){
                                    if (clazzNew.id == self.changeClazzList[i].id){
                                        self.changeClazzList[i].name = clazzNew.name;
                                    }
                                }
                            }
                            self.$message({showClose: true, message: "保存成功", type: 'success'});
                            self.clazzAddFlag = false;
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
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
                }
            }
        });
    </script>
</div>
</body>
</html>