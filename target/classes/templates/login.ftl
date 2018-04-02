<!DOCTYPE HTML>
<html>
<head>
    <title>登录 | Ican毕业设计平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <#include 'include/cssjs_common_new.ftl'>
    <style>
        .forgot-grid ul li input[type="checkbox"]+label{
            line-height: 2;
        }
    </style>
</head>
<body class="login-bg">
<div style="margin-top: 300px;float: right">
    <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=&site=qq&menu=yes">
    <img border="0" src="http://wpa.qq.com/pa?p=2::53" alt="你好" title="你好"/></a>
</div>
<div class="login-body" id="app">
    <div class="login-heading">
        <h1>{{getLoginRole(role)}}</h1>
    </div>
    <div class="login-info">
        <form>
            <input v-model="form.account" type="text" class="user" name="email" placeholder="手机/邮箱" required="">
            <input v-model="form.password" type="password" name="password" class="lock" placeholder="密码">
            <div v-show="sliderShow">
                <div class="block">
                    <span class="demonstration">请将滑块移动到<span style="color: red;">{{resultValue}}</span>以通过校验</span><br>
                    <el-slider v-model="sliderValue"></el-slider>
                </div>
            </div><br />
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
                <a href="signup.html">还没有账号？注册一个！</a>
            </div>
            <hr>
        </form>
    </div>
</div>
<div class="go-back login-go-back">
    <a href="/">主页</a>
</div>
<div class="copyright login-copyright">
    <p>Copyright &copy; 2018.这个星球第一款毕业设计平台<a href="#" target="_blank" title="ican">Ican</a></p>
</div>
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
                remember:[],
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
                var remember = self.remember[0];
                Api.post('/login',{
                    account:self.form.account,
                    password:self.form.password,
                    role:role,
                    remember:remember
                },function (result) {
                    if (result.code == 0) {
                        self.$message({showClose: true, message: '点了登录', type: 'success'});
                        /*window.location.href = '/success?role='+role;*/
                        /*alert(result.data);*/
                        window.location.href = result.data;
                    }else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
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