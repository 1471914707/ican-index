<!DOCTYPE HTML>
<html>
<head>
    <title>重置密码</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
    <#include 'include/cssjs_common_new.ftl'>
    <style>
    </style>
</head>
<body class="login-bg">
<div class="login-body" id="app">
    <div style="margin-bottom: 20px;">
        <h1>重置密码</h1>
    </div>
    <div>
        <el-input v-model="password" placeholder="请输入旧密码" style="margin-bottom: 20px;" type="password"></el-input>
        <el-input v-model="new_password" placeholder="请输入新密码" style="margin-bottom: 20px;" type="password"></el-input>
        <el-button type="primary" @click="resetPassword()">保存</el-button>
    </div>
</div>
<script type="text/javascript">

    var app = new Vue({
        el: "#app",
        data: function () {
            return {
                password:'',
                new_password:''
            }
        },
        watch:{
        },
        mounted: function () {
        },
        methods: {
            resetPassword:function () {
                var self = this;
                if (this.password.trim() == '' | this.new_password.trim() == '') {
                    self.$message({showClose: true, message: '请输入旧密码或新密码', type: 'success'});
                }
                Api.post("/reset_password",{password:this.password,newPassword:this.new_password},function (result) {
                    if (result.code == 0) {
                        self.$message({showClose: true, message: '修改成功', type: 'success'});
                    } else {
                        self.$message({showClose: true, message: '修改失败', type: 'success'});
                    }
                });
            }
        }
    });

</script>
</body>
</html>