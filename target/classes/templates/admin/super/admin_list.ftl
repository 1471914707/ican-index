<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>管理员列表</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <#include '/include/cssjs_common.ftl'>
    <style>
        .box4{
            margin: 20px auto;
            min-height: 100px;
            width: 300px;
            padding: 10px;
            position:relative;
            background: -webkit-gradient(linear, 0% 20%, 0% 100%, from(#fff), to(#fff), color-stop(.2, #f2f2f2));
            border: 1px solid #ccc;
            -webkit-box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.3);
        }
    </style>
</head>
<body>
<#include '/include/common/menu_left.ftl'>
<el-container>
    <el-container>
        <el-header>Header</el-header>
        <el-main>
            <div id="app"><br>
                <el-row :gutter="20">
                    <el-col :span="4"><div class="grid-content bg-purple" style="max-width:100px;height: 1px;"></div></el-col>
                    <el-col :span="8" align="center"><div class="grid-content bg-purple" class="box4">
                        <div>
                            <h1>操作</h1>
                        </div>
                        <br>
                        <el-form ref="form" :model="form" label-width="80px">
                            <el-form-item label="邮箱/手机">
                                <el-input v-model="form.account"></el-input>
                            </el-form-item>
                            <el-form-item label="密码">
                                <el-input v-model="form.password"></el-input>
                            </el-form-item>
                            <div v-show="sliderShow">
                                <div class="block">
                                    <span class="demonstration">请将滑块移动到<span style="color: red;">{{resultValue}}</span>以通过校验</span><br>
                                    <el-slider v-model="sliderValue"></el-slider>
                                </div>
                            </div><br />
                            <el-form-item style="margin-left: -80px;">
                                <el-button type="primary" @click="login()" :disabled="disabled">登陆</el-button>
                            </el-form-item>
                            <el-form-item style="margin-left: -80px;">
                                <a @click="reset()">重置</a>
                            </el-form-item>
                        </el-form>
                    </div></el-col>
                    <el-col :span="4" class="hidden-sm-and-down"><div class="grid-content bg-purple"></div></el-col>
                </el-row>
            </div>
        </el-main>
    </el-container>
</el-container>

<script type="text/javascript">
    <#if role??>
    var role = ${role}
    <#else>
    var role = 0;
    </#if>
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
                loginNum:0,
                sliderValue:0,
                resultValue:-1,
                sliderShow:false,
                disabled:false
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
            this.role = role;
        },
        methods: {
            getLoginRole:function (role) {
                var welcome = '欢迎登录';
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
                Api.post('/login',{
                    account:self.form.account,
                    password:self.form.password,
                    role:role
                },function (result) {
                    if (result.code == 0) {
                        self.$message({showClose: true, message: '点了登录', type: 'success'});
                        //window.location.href = '/success?role='+role
                    }else {
                        self.$message({showClose: true, message: '登录异常', type: 'error'});
                    }
                });
            },
            reset:function () {
                this.form.account = '';
                this.form.password = '';
            }
        }
    });

</script>
</body>
</html>