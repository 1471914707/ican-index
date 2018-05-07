<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Ican | 毕业设计平台</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <#include 'include/cssjs_common_new.ftl'>
    <#--<#include 'include/header/header.ftl'>-->
    <style>
        html, body{
           /* background: #ffffff;*/
        }
        .ticket-grid{
            padding-right: 20px;
        }
        .el-radio+.el-radio {
            margin-left: 0px !important;
            margin-right: 20px;
        }
        .el-radio__label {
            padding-right: 15px;
        }
    </style>
    <style>
    .img_avatar {
        border: 1px solid #ccc;
        padding: 2px;
        width: 180px;
        height: 180px;
    }
    .display_name {
        font-size: 20px;
        font-weight: bold;
        color: #454545;
    }
    .text_gray {
        color: Gray;
    }
    .face-img {
        width: 50px;
        height: 50px;
        float: right;
        margin-right: 10px;
    }
    .small-icon{
        width:auto;
        height: 20px;1
    }
    textarea{
        resize:none !important;
        height:180px;
    }
    .uploadActive{
        display: none;
    }
    .icon-embed {
        width: 20px;
        height:20px;
        cursor: pointer;
    }
    a {
        cursor: pointer;
    }
    img{
        cursor: pointer;
    }
    .el-radio.is-bordered{
        margin-bottom: 10px;
    }
</style>
</head>
<body>
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
           <#-- <h2 style="display: inline-block;line-height: 80px;margin-left: 3%;">毕业设计（论文）平台</h2>-->
            <img src="http://cdn.ican.com/public/images/ican-banner.png" style="height: 78px;width: auto;margin-left: 15%;">
        </div><br>
        <div id="page-wrapper" style="width: 95%;">
            <div class="main-page">
                <el-row class="four-grids">

                    <el-col :span="14" class="ticket-grid">
                        <div v-for="(item, index) in list">
                            <el-row>
                                <el-col :span="7"><img :src="item.headshot" class="face-img" @click="openBk(item.userId)"></el-col>
                                <el-col :span="17">
                                    <span @click="openBk(item.userId)" style="cursor:pointer;">{{item.name}}</span><br>
                                    <span style="color: #808080;font-size: 12px">{{getTime(item.gmtCreate)}}</span><br><br>
                                    {{item.content}}<br><br>
                                    <el-row style="width: 65%">
                                        <template v-for="(photo,i) in item.image" >
                                            <el-col :span="7">
                                                <img :src="photo.url" style="width: auto;height: auto;max-width: 90%; max-height: 90%; cursor:pointer;" @click="photoUrl=photo.url;photoVisible=true;">
                                            </el-col>
                                        </template>
                                    </el-row>

                                    <#--<div v-for="(photo,i) in item.image" style="display: inline-block">
                                        <img :src="photo.url" style="width:125px;height: 125px;cursor:pointer " @click="photoUrl=photo.url;photoVisible=true;">
                                        <template v-if="i % 2 == 0">
                                            <br/>
                                        </template>
                                    </div>--><br>
                                    <br />
                                    <div>
                                        <el-row class="blog-bottom">
                                            <el-col :span="20">
                                                <div style="width: 1px;height: 1px;"></div>
                                            </el-col>
                                            <el-col :span="4">
                                                <el-row>
                                                    <el-col :span="24">
                                                        <embed src="http://cdn.ican.com/public/svg/comment.svg"
                                                               type="image/svg+xml" class="icon-embed"/>
                                                        <div style="display: inline-block;vertical-align: top;" @click="openMessageWindow2(item.userId)">
                                                            私信</div>
                                                    </el-col>
                                                </el-row>
                                            </el-col>
                                        </el-row>
                                    </div>
                                    <div style="background-color:#dddddd;height: 1px;"></div>
                                    <br>
                                </el-col>
                            </el-row>
                        </div>
                        <div class="block-pagination">
                            <el-pagination
                                    @size-change="handleSizeChange"
                                    @current-change="handleCurrentChange"
                                    :current-page="page"
                                    :page-size="size"
                                    layout="prev, next"
                                    :total="total">
                            </el-pagination>
                        </div>
                    </el-col>

                    <el-col :span="8" class="ticket-grid" style="padding: 5%;padding-top: 0% !important;padding-left: 0% !important;">
                        <div class="login-heading">
                            <h1>{{welcomeRole}}</h1>
                        </div>
                        <div class="login-info">
                            <form>
                                <input v-model="form.account" type="text" class="user" name="email" placeholder="手机/邮箱" required="">
                                <input v-model="form.password" type="password" name="password" class="lock" placeholder="密码">
                                <el-radio-group v-model="role" @change="changeRole()">
                                    <el-radio :label="3">学校</el-radio>
                                    <el-radio :label="4">二级学院</el-radio>
                                    <el-radio :label="5">教师</el-radio>
                                    <el-radio :label="6">学生</el-radio>
                                </el-radio-group>
                                <div v-show="sliderShow">
                                    <div class="block">
                                        <span class="demonstration">请将滑块移动到<span style="color: red;">{{resultValue}}</span>以通过校验</span><br>
                                        <el-slider v-model="sliderValue"></el-slider>
                                    </div>
                                </div>
                                <div class="forgot-top-grids">
                                    <div class="forgot-grid">
                                        <ul>
                                            <li>
                                                <input type="checkbox" id="brand1" value="1" v-model="remember" name="remember">
                                                <label for="brand1"><span></span>记住登录</label>
                                            </li>
                                        </ul>
                                    </div>
                                    <div class="forgot">
                                        <a href="#">忘记密码?</a>
                                    </div>
                                    <div class="clearfix"> </div>
                                </div>
                                <el-button type="primary" @click="login()" style="width:100%;margin-top: 5px;" :disabled="disabled">登录</el-button>
                                <div class="signup-text">
                                    <a @click="dialogVisible=true">还没有账号？注册一个！</a>
                                </div>
                                <hr>
                            </form>
                        </div>
                        <br><br>
                        <div>
                            <el-card class="box-card">
                                <div slot="header" class="clearfix">
                                    <span style="font-weight: bolder">联系我们</span>
                                </div>
                                <div>
                                    <el-row>
                                        <el-col :span="12">
                                            <img src="http://cdn.ican.com/public/images/wechat.jpg"
                                                 style="width: auto;height: auto;max-width: 100%; max-height: 100%; ">
                                        </el-col>
                                        <el-col :span="12">
                                            <div><br>请扫描二维码联系客服<br><br>
                                                <i class="el-icon-message">1471914707@qq.com</i><br><br>
                                                <i class="el-icon-phone">18813960106</i><br><br>
                                                <i class="el-icon-service">QQ1471914707</i><br><br>
                                                <span><a href="/register/schoolAppeal">学校审议通道</a></span>
                                            </div>
                                        </el-col>
                                    </el-row>
                                </div>
                            </el-card>
                        </div>
                    </el-col>
                </el-row>

            </div>
        </div>

        <el-dialog
                title="选择注册角色"
                :visible.sync="dialogVisible"
                width="15%">
            <div>
                <el-radio-group v-model="roleVal" @change="changeRoleVal()">
                    <el-radio  v-model="roleVal" label="3" border>学校</el-radio>
                    <el-radio  v-model="roleVal" label="5" border>教师</el-radio>
                    <el-radio  v-model="roleVal" label="6" border>学生</el-radio>
                </el-radio-group>
            </div>
        </el-dialog>
        <el-dialog
                title=""
                :visible.sync="photoVisible"
                width="80%">
            <center>
                <img :src="photoUrl" style="max-width: 90%;height: auto">
            </center>
        </el-dialog>
    </div>

</div>
<script type="text/javascript">
    var root=document.getElementsByClassName("root")[0];
    root.style.cssText="background-color: #f77;";
</script>
<script type="text/javascript">
    <#--<#if role??>
    var role = ${role}
    <#else>
    var role = 0;
    </#if>-->
    <#if loginMsg??>
    var loginMsg = ${loginMsg}
    <#else>
    var loginMsg = 0;
    </#if>

    var app = new Vue({
        el: "#app",
        data: function () {
            return {
                role:0,
                form: {
                    account: '',
                    password: ''
                },
                remember:[],
                loginNum:0,
                sliderValue:0,
                resultValue:-1,
                sliderShow:false,
                disabled:false,
                welcomeRole:'请选择角色',
                page:1,
                size:2,
                total:0,
                list: [],
                dialogVisible:false,
                roleVal:null,
                photoVisible:false,
                photoUrl:''
            }
        },
        watch:{
            loginNum:function (newVal,oldVal) {
                if (newVal > 2) {
                    this.sliderShow = true;
                    var randomVal = Math.floor(Math.random() * 100);
                    while (randomVal == this.resultValue) {
                        randomVal = Math.floor(Math.random() * 100);
                    }
                    this.resultValue = randomVal;
                    this.disabled = true;
                }
            },
            sliderValue:function (newVal,oldVal) {
                if (newVal == this.resultValue) {
                    this.disabled = false;
                } else {
                    this.disabled = true;
                }
            }
        },
        mounted: function () {
            if (loginMsg != 0) {
                self.$message({showClose: true, message: loginMsg, type: 'success'});
            }
            this.role = 0;
            this.loadBlogList();
        },
        methods: {
            loadBlogList:function (page, size) {
                var self = this;
                self.loading = true;
                var page = page || this.page || 1;
                var size = size || this.size || 2;
                Api.get('/bk/listJson',{
                    id:100000,
                    type:1,
                    page:page,
                    size:size
                },function (result) {
                    if (result.code == 0) {
                        if (result.data.list) {
                            self.list = result.data.list;
                            self.total = result.data.total;
                            for (var i=0; i<self.list.length; i++) {
                                self.list[i].image = JSON.parse(self.list[i].image);
                                self.list[i].commentFlag = false;
                                self.list[i].loadingComment = false;
                            }
                            self.loading = false;
                        }
                    }else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                        self.loading = false;
                    }
                });
            },
            changeRole:function () {
                this.welcomeRole = this.getLoginRole(this.role);
            },
            getLoginRole:function (role) {
                var welcome = '请选择角色';
                switch (role){
                    case 1:
                    case 2:
                        return '管理员登陆';
                    case 3:
                        return '学校登陆';
                    case 4:
                        return '学院登陆';
                    case 5:
                        return '导师登陆';
                    case 6:
                        return '学生登陆';
                    default:
                        return welcome;
                }
            },
            login:function () {
                var self = this;
                var rule_phone = /^1\d{10}$/;
                var rule_email = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
                self.loginNum ++;
                if (!rule_email.test(self.form.account) && !rule_phone.test(self.form.account)){
                    self.$message({showClose: true, message: '请输入合法的手机或邮箱', type: 'error'});
                    return false;
                }
                //var rule_password = /^[a-zA-z]\w{6,15}$/;
                if (self.form.password == null || self.form.password.length < 5){
                    self.$message({showClose: true, message: '密码多于或等于六位的字母数字下划线组成', type: 'error'});
                    return false;
                }
                var remember = self.remember[0];
                Api.post('/login',{
                    account:self.form.account,
                    password:self.form.password,
                    role:self.role,
                    remember:remember
                },function (result) {
                    if (result.code == 0) {
                        self.$message({showClose: true, message: '登录成功', type: 'success'});
                        /*window.location.href = '/success?role='+role;*/
                        /*alert(result.data);*/
                        window.location.href = result.data;
                    }else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
                });
            },
            changeRoleVal:function () {
                if (this.roleVal == 3){
                    window.location.href = "/register/schoolRegister";
                }
                if (this.roleVal == 4){
                    window.location.href = "/register/teacherRegister";
                }
                if (this.roleVal == 5){
                    window.location.href = "/register/studentRegister";
                }
            },
            reset:function () {
                this.form.account = '';
                this.form.password = '';
            },
            getTime:function (time) {
                return DateFun.getTimeFormatText(time);
            },
            getDate:function (dateTime) {
                if (dateTime.trim() != '') {
                    return dateTime.split(" ")[0];
                }
                return '';
            },
            handleSizeChange:function (size) {
                this.size = size;
                this.loadBlogList(this.page, this.size);
            },
            handleCurrentChange:function (page) {
                this.page = page;
                this.loadBlogList(this.page, this.size);
            },
        }
    });


/*    var app = new Vue({
        el: "#app",
        data: function () {
            return {
                show3:false,
                time:'00:00:00',
                timer:null
            }
        },
        watch:{
        },
        mounted: function () {
            window.setInterval(this.loadSchoolActiveData,1000);
        },
        methods: {
            loadSchoolActiveData:function () {
                this.time = new Date().Format("HH:mm:ss");
                //this.show3 = !this.show3;
            }
        }
    });*/

</script>
</body>
</html>