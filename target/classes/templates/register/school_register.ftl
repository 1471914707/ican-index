<!DOCTYPE HTML>
<html>
<head>
    <title>学校注册 | Ican毕业设计平台</title>
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
        <h1>学校注册</h1><br>
    </div>
    <template v-if="successFlag"><h3>注册成功！请三天之内等待工作人员联系您，确认身份之后即可登录。如未能收到联系，可点击
        <a href="/register/schoolAppeal" style="color: #409EFF">申议通道</a>进行申诉。</h3></template>
    <div v-if="!successFlag">
        <el-form ref="form" :model="school" label-width="80px">
            <el-form-item label="学校头像">
                <el-upload
                        class="avatar-uploader"
                        action="/photoUpload"
                        :show-file-list="false"
                        :on-success="photoUploadSuccess">
                    <img v-if="school.headshot != ''" :src="school.headshot" class="avatar">
                    <i v-if="school.headshot == ''" class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <div class="tips">建议上传校徽，方便同学教师等辨认</div>
            </el-form-item>
            <el-form-item label="本人姓名"
                          :rules="[
                          { required: true, message: '请输入本人姓名', trigger: 'blur' }]">
                <el-input v-model="school.name"></el-input>
            </el-form-item>
            <el-form-item label="校名" :rules="[
                          { required: true, message: '请输入学校名称', trigger: 'blur' }]">
                <el-input v-model="school.schoolName"></el-input>
            </el-form-item>
            <el-form-item label="官网地址" :rules="[
                          { required: true, message: '请输入官网链接', trigger: 'blur' }]">
                <el-input v-model="school.url"></el-input>
            </el-form-item>
            <el-form-item label="学校电话" :rules="[
                          { required: true, message: '请输入学校电话', trigger: 'blur' }]">
                <el-input v-model="school.schoolPhone"></el-input>
            </el-form-item>
            <el-form-item label="学校邮箱" :rules="[
                          { required: true, message: '请输入学校邮箱', trigger: 'blur' }]">
                <el-input v-model="school.schoolEmail"></el-input>
            </el-form-item>
            <el-form-item label="本人手机" :rules="[
                          { required: true, message: '请输入手机号码', trigger: 'blur' }]">
                <el-input v-model="school.phone"></el-input>
            </el-form-item>
            <el-form-item label="本人邮箱"
                          :rules="[
                          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }]">
                <el-input v-model="school.email"></el-input>
                <div class="tips">注：本人的邮箱将来会作为登录的账号</div>
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
                    <el-input placeholder="请输入验证码" v-model="school.code" class="input-with-select">
                        <el-button slot="append" type="success" @click="getVerCode()" :disabled="verCodeBtnFlag">{{verCodeBtnText}}</el-button>
                    </el-input>
                </el-form-item>
            </template>
            <el-form-item label="密码" :rules="[
                          { required: true, message: '请输入密码', trigger: 'blur' }]">
                <el-input v-model="school.password" type="password"></el-input>
            </el-form-item>
            <el-form-item label="重复密码" :rules="[
                          { required: true, message: '请再次输入密码', trigger: 'blur' }]">
                <el-input v-model="repeatPassword" @blur="checkPassword()" type="password"></el-input>
            </el-form-item>
            <el-form-item label="顶部横幅">
                <el-upload
                        action="/photoUpload"
                        :show-file-list="false"
                        :on-success="photoUploadSuccess3">
                    <img v-if="school.banner != ''" :src="school.banner" style="width: 368px;height: 84px;">
                    <i v-if="school.banner == ''" class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
                <div class="tips">大小为368*84px</div>
            </el-form-item>
            <el-form-item label="国家">
                <el-select v-model="school.country" placeholder="请选择国家" @change="changeCountry()">
                    <el-option
                            v-for="item in countryList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="省份">
                <el-select v-model="school.province" placeholder="请选择省份" @change="changeProvince()">
                    <el-option
                            v-for="item in provinceList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
                </el-select>
            </el-form-item>
            <el-form-item label="城市">
                <el-select v-model="school.city" placeholder="请选择城市">
                    <el-option
                            v-for="item in cityList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="地址">
                <el-input v-model="school.address"></el-input>
            </el-form-item>
            <el-form-item label="认证图片">
                <el-upload
                        class="upload-demo"
                        action="/photoUpload"
                        :file-list="fileList3"
                        :on-success="photoUploadSuccess2">
                    <el-button size="small" type="primary" :disabled="!(fileList3.length < 2)">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">请上传两张学校认证图片，单张大小不超过2M</div>
                </el-upload>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveSchool()" :disabled="saveBtn">注册</el-button>
            </el-form-item>
        </el-form>
    </div>
</div>

<script type="text/javascript">
     var app = new Vue({
        el: "#app",
        data: function () {
            return {
                role:0,
                school: {
                    headshot: '',
                    name: '',
                    phone:'',
                    email:'',
                    schoolName:'',
                    url:'',
                    banner:'',
                    address:'',
                    auth1:'',
                    auth2:'',
                    country:null,
                    province:null,
                    city:null,
                    schoolPhone:'',
                    schoolEmail:'',
                    password:'',
                    code:''
                },
                allCityList:[],
                countryList:[],
                provinceList:[],
                cityList:[],
                dialogImageUrl: '',
                dialogVisible: false,
                fileList3: [],
                successFlag:false,
                repeatPassword:'',
                saveBtn:false,
                sliderVal:0,
                emailFlag:false,
                verCodeBtnText:'请点击获取邮箱验证码',
                verCodeBtnFlag:false,
                timer:null,
                count:0
            }
        },
        watch:{
        },
        mounted: function () {
            this.loadCityList();
        },
        methods: {
            saveSchool:function () {
                var self = this;
                if (this.fileList3.length < 2 || this.fileList3.length != 2){
                    self.$message({showClose: true, message: '请上传两张认证图片', type: 'error'});
                }
                if (this.school.headshot.trim() == '' || this.school.banner.trim() == ''){
                    self.$message({showClose: true, message: '头像或横幅尚未上传', type: 'error'});
                }
                this.school.auth1 = this.fileList3[0];
                this.school.auth2 = this.fileList3[1];
                Api.post("/register/schoolSave",self.school,function (result) {
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
                if (this.school.email.trim() == '' || !reg.test(this.school.email)){
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
                Api.post("/register/getEmailVerCode",{email:self.school.email,role:3},function (result) {
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
                  if (this.school.email.trim() == ''){
                      this.$message({showClose: true, message: '邮箱尚未填写', type: 'error'});
                      this.sliderVal = 0;
                      return false;
                  }
                  this.emailFlag = true;
              }
            },
            checkPassword:function () {
              if (this.school.password != this.repeatPassword) {
                  this.$message({showClose: true, message: '两次输入的密码不一致', type: 'error'});
                  this.saveBtn = true;
              } else {
                  this.saveBtn = false;
              }
            },
            photoUploadSuccess:function (result, file, fileList) {
                var self = this;
                if (result.code == 0){
                    self.school.headshot = result.data;
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
            },
            photoUploadSuccess3:function (result, file, fileList) {
                if (result.code == 0) {
                    this.school.banner = result.data;
                } else {
                    self.$message({showClose: true, message: '上传失败', type: 'error'});
                }
            },
            loadCityList:function () {
                if (this.allCityList.length > 0) {
                    return false;
                }
                var self = this;
                Api.get("/allCityJson",{},function (result) {
                    if (result.code == 0) {
                        self.allCityList = result.data;
                        for (var i=0; i<self.allCityList.length; i++){
                            if (self.allCityList[i].parentId == 0) {
                                self.countryList.push(self.allCityList[i]);
                            }
                            if (self.allCityList[i].parentId == self.school.country) {
                                self.provinceList.push(self.allCityList[i]);
                            }
                            if (self.allCityList[i].parentId == self.school.province) {
                                self.cityList.push(self.allCityList[i]);
                            }
                        }
                    }
                });
            },
            changeCountry:function () {
                //清空省份和城市
                this.provinceList = [];
                this.cityList = [];
                this.school.province = null;
                this.school.city = null;
                for (var i=0; i<this.allCityList.length; i++) {
                    if (this.allCityList[i].parentId == this.school.country) {
                        this.provinceList.push(this.allCityList[i]);
                    }
                }
            },
            changeProvince:function () {
                //清空省份和城市
                this.cityList = [];
                this.school.city = null;
                for (var i=0; i<this.allCityList.length; i++) {
                    if (this.allCityList[i].parentId == this.school.province) {
                        this.cityList.push(this.allCityList[i]);
                    }
                }
            },
        }
    });

</script>
</body>
</html>