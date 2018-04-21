<!DOCTYPE HTML>
<html>
<head>
    <title>学生注册 | Ican毕业设计平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
<#include '/include/cssjs_common_new.ftl'>
    <style>
        .register-body{margin:0 auto;width: 450px;}
        .avatar-uploader .el-upload {
            border: 1px dashed #d9d9d9;
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            border-radius: 50%;
        }
        .avatar-uploader .el-upload:hover {
            border-color: #409EFF;
        }
        .avatar-uploader-icon {
            font-size: 28px;
            color: #8c939d;
            width: 100px;
            height: 100px;
            line-height: 100px;
            text-align: center;
        }
        .avatar {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            display: block;
        }
        .tips{
            font-size: 12px;
            color: #606266;
        }
        .el-form-item__label{
            font-weight: bolder;
        }
        html, body{
            background: #fff;
        }
        .el-icon-close{
            display: none !important;
        }
    </style>
</head>
<body>
<div id="app" class="register-body">
    <div style="text-align: center">
        <h1>学生注册</h1><br>
    </div>
    <template v-if="successFlag"><h3 style="text-align: center">注册成功！您现在可以选择<a href="/login?role=6" style="color: #409EFF">登录</a></h3></template>
    <div v-if="!successFlag">

        <el-form ref="form" :model="student" label-width="80px">
            <el-form-item label="注意：">
                <div class="tips" style="color: red">*注册之前请先确定您有二级学院未过期的邀请码</div>
            </el-form-item>
            <el-form-item label="邀请码">
                <el-input placeholder="请输入邀请码" v-model="student.keyt" class="input-with-select">
                    <el-button slot="append" type="success" @click="verKeyt()" :loading="loading">点击校验</el-button>
                </el-input>
                <template v-if="!keytRightFlag">
                    <div class="tips">请输入邀请码，点击校验</div>
                </template>
            </el-form-item>
            <template v-if="keytRightFlag">
                <el-form-item label="系部">
                    <el-select v-model="student.departmentId" placeholder="请选择所在系部" @change="changeDepartment()">
                        <el-option
                                v-for="item in departmentList"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="专业">
                    <el-select v-model="student.majorId" placeholder="请选择专业" @change="changeMajor()">
                        <el-option
                                v-for="item in changeMajorList"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="届">
                    <el-select v-model="student.current" placeholder="请选择届" @change="changeCurrent()">
                        <el-option
                                v-for="item in 20"
                                :key="item+2010"
                                :label="item+2010"
                                :value="item+2010">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="班级">
                    <el-select v-model="student.clazzId" placeholder="请选择班级">
                        <el-option
                                v-for="item in changeClazzList"
                                :key="item.id"
                                :label="item.name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
            </template>
            <el-form-item label="个人头像">
                <el-upload
                        class="avatar-uploader"
                        action="/photoUpload"
                        :show-file-list="false"
                        :on-success="photoUploadSuccess">
                    <img v-if="student.headshot != ''" :src="student.headshot" class="avatar">
                    <i v-if="student.headshot == ''" class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <div class="tips">建议上传真实个人头像，方便同学同事等辨认</div>
            </el-form-item>
            <el-form-item label="本人姓名"
                          :rules="[
                          { required: true, message: '请输入本人姓名', trigger: 'blur' }]">
                <el-input v-model="student.name"></el-input>
            </el-form-item>
            <el-form-item label="在校学号" :rules="[
                          { required: true, message: '请输入在校学号', trigger: 'blur' }]">
                <el-input v-model="student.jobId"></el-input>
            </el-form-item>
            <el-form-item label="本人手机" :rules="[
                          { required: true, message: '请输入手机号码', trigger: 'blur' }]">
                <el-input v-model="student.phone"></el-input>
            </el-form-item>
            <el-form-item label="本人邮箱"
                          :rules="[
                          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }]">
                <el-input v-model="student.email"></el-input>
                <div class="tips">注：本人的邮箱和账号将来会作为登录的账号</div>
            </el-form-item>
            <template v-if="!emailFlag">
                <el-form-item label="滑块验证">
                    <div class="block">
                        <span class="demonstration">请将滑块滑动到最右完成验证</span>
                        <el-slider v-model="sliderVal" @change="checkSliderVal()"></el-slider>
                    </div>
                </el-form-item>
            </template>
            <template v-if="emailFlag">
                <el-form-item label="验证码">
                    <el-input placeholder="请输入验证码" v-model="student.code" class="input-with-select">
                        <el-button slot="append" type="success" @click="getVerCode()" :disabled="verCodeBtnFlag">{{verCodeBtnText}}</el-button>
                    </el-input>
                </el-form-item>
            </template>
            <el-form-item label="密码" :rules="[
                          { required: true, message: '请输入密码', trigger: 'blur' }]">
                <el-input v-model="student.password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="重复密码" :rules="[
                          { required: true, message: '请再次输入密码', trigger: 'blur' }]">
                <el-input v-model="repeatPassword" @blur="checkPassword()" type="password"></el-input>
            </el-form-item>
            <el-form-item label="认证图片">
                <el-upload
                        class="upload-demo"
                        action="/photoUpload"
                        :file-list="fileList3"
                        :on-success="photoUploadSuccess2">
                    <el-button size="small" type="primary" :disabled="!(fileList3.length < 2)">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">请上传两张个人认证图片，单张大小不超过2M</div>
                </el-upload>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveStudent()" :disabled="saveBtn">注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</div>

<script type="text/javascript">
    var app = new Vue({
        el: "#app",
        data: function () {
            return {
                role:6,
                student: {
                    headshot: '',
                    name: '',
                    phone:'',
                    email:'',
                    schoolId:'',
                    collegeId:'',
                    departmentId:'',
                    majorId:'',
                    current:'',
                    clazzId:'',
                    jobId:'',
                    password:'',
                    auth1:'',
                    auth2:'',
                    keyt:'',
                    code:''
                },
                keytRightFlag:false,
                fileList3: [],
                successFlag:false,
                repeatPassword:'',
                saveBtn:true,
                departmentList:[],
                majorList:[],
                changeMajorList:[],
                clazzList:[],
                changeClazzList:[],
                loading:false,
                sliderVal:0,
                emailFlag:false,
                verCodeBtnText:'请点击获取邮箱验证码',
                verCodeBtnFlag:false,
                timer:null,
                count:0,
                emailFlag:false
            }
        },
        watch:{
        },
        mounted: function () {
        },
        methods: {
            saveStudent:function () {
                var self = this;
                if (self.student.departmentId == '' || self.student.majorId == '' || self.student.current == ''
                        || self.student.clazzId == '') {
                    self.$message({showClose: true, message: '请确保系，专业和班级已经选择', type: 'error'});
                    return false;
                }
                if (self.student.degree != 6){
                    self.student.degreeName = '';
                }
                if (this.fileList3.length < 2 || this.fileList3.length != 2){
                    self.$message({showClose: true, message: '请上传两张认证图片', type: 'error'});
                }
                this.student.auth1 = this.fileList3[0];
                this.student.auth2 = this.fileList3[1];
                Api.post("/register/studentSave",self.student,function (result) {
                    if (result.code == 0) {
                        self.$message({showClose: true, message: '注册成功', type: 'success'});
                        self.successFlag = true;
                    } else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
                })
            },
            getVerCode:function () {
                var self = this;
                var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");
                if (this.student.email.trim() == '' || !reg.test(this.student.email)){
                    self.$message({showClose: true, message: '邮箱输入有误', type: 'error'});
                    return false;
                }
                this.verCodeBtnFlag = true;
                //倒计时
                const TIME_COUNT = 60;
                if (!self.timer) {
                    self.count = TIME_COUNT;
                    self.show = false;
                    self.timer = setInterval(function () {
                        if (self.count > 0 && self.count <= TIME_COUNT) {
                            self.count--;
                            self.verCodeBtnText = self.count + 's';
                        } else {
                            clearInterval(self.timer);
                            self.timer = null;
                            self.verCodeBtnText = '请点击获取邮箱验证码';
                            self.verCodeBtnFlag = false;
                        }
                    }, 1000)
                }
                Api.post("/register/getEmailVerCode",{email:self.student.email,role:4},function (result) {
                    if (result.code == 0){
                        if (result.data.num){
                            if (result.data.num > 3){
                                self.$message({showClose: true, message: result.msg, type: 'error'});
                                this.saveBtn = true;
                                return false;
                            }
                        }
                        self.$message({showClose: true, message: '邮箱验证码发送成功', type: 'success'});
                    } else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                        self.verCodeBtnText = '请点击获取邮箱验证码';
                        self.verCodeBtnFlag = false;
                        self.timer = null;
                        return false;
                    }
                });
            },
            checkSliderVal:function () {
                if (this.sliderVal == 100) {
                    if (this.student.email.trim() == ''){
                        this.$message({showClose: true, message: '邮箱尚未填写', type: 'error'});
                        this.sliderVal = 0;
                        return false;
                    }
                    this.emailFlag = true;
                }
            },
            verKeyt:function () {
                var self = this;
                self.loading = true;
                if (this.student.keyt.trim() == '') {
                    self.$message({showClose: true, message: '请输入邀请码', type: 'error'});
                }
                Api.post("/register/studentVerKeyt",{keyt:self.student.keyt},function (result) {
                    if (result.code == 0) {
                        self.saveBtn = false;
                        self.keytRightFlag = true;
                        self.student.schoolId = result.data.schoolId;
                        self.student.collegeId = result.data.collegeId;
                        self.departmentList = result.data.departmentList;
                        self.majorList = result.data.majorList;
                        self.clazzList = result.data.clazzList;
                        self.$message({showClose: true, message: '校验成功', type: 'success'});
                        self.loading = false;
                    } else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                        self.loading = false;
                    }
                });
            },
            changeDepartment:function () {
                this.changeMajorList = [];
                this.changeClazzList = [];
                this.student.majorId = null;
                this.student.clazzId = null;
                this.student.current = null;
                for (var i=0; i<this.majorList.length; i++) {
                    if (this.majorList[i].departmentId == this.student.departmentId){
                        this.changeMajorList.push(JSON.parse(JSON.stringify(this.majorList[i])));
                    }
                }
            },
            changeMajor:function () {
                this.changeClazzList = [];
                this.student.clazzId = null;
                this.student.current = null;
            },
            changeCurrent:function () {
                this.changeClazzList = [];
                this.student.clazzId = null;
                for (var i=0; i<this.clazzList.length; i++) {
                    if (this.clazzList[i].current == this.student.current){
                        this.changeClazzList.push(JSON.parse(JSON.stringify(this.clazzList[i])));
                    }
                }
            },
            checkPassword:function () {
                if (this.student.password != this.repeatPassword) {
                    this.$message({showClose: true, message: '两次输入的密码不一致', type: 'error'});
                    this.saveBtn = true;
                } else {
                    this.saveBtn = false;
                }
            },
            photoUploadSuccess:function (result, file, fileList) {
                var self = this;
                if (result.code == 0){
                    self.student.headshot = result.data;
                } else {
                    self.$message({showClose: true, message: '上传失败', type: 'error'});
                }
            },
            photoUploadSuccess2:function (result, file, fileList) {
                if (result.code == 0) {
                    this.fileList3.push(result.data);
                } else {
                    self.$message({showClose: true, message: '上传失败', type: 'error'});
                }
            }
        }
    });

</script>
</body>
</html>