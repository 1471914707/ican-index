<!DOCTYPE HTML>
<html>
<head>
    <title>学校申诉 | Ican毕业设计平台</title>
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
        <h1>学校申诉</h1><br>
    </div>
    <template v-if="successFlag"><h3>提交成功！您亦可主动联系客服:18813960106。</h3></template>
    <div v-if="!successFlag">
        <el-form ref="form" :model="schoolAppeal" label-width="80px">
            <el-form-item label="本人姓名"
                          :rules="[
                          { required: true, message: '请输入本人姓名', trigger: 'blur' }]">
                <el-input v-model="schoolAppeal.name"></el-input>
            </el-form-item>
            <el-form-item label="校名" :rules="[
                          { required: true, message: '请输入学校名称', trigger: 'blur' }]">
                <el-input v-model="schoolAppeal.schoolName"></el-input>
            </el-form-item>
            <el-form-item label="本人手机" :rules="[
                          { required: true, message: '请输入手机号码', trigger: 'blur' }]">
                <el-input v-model="schoolAppeal.phone"></el-input>
            </el-form-item>
            <el-form-item label="本人邮箱"
                          :rules="[
                          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
                          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur,change' }]">
                <el-input v-model="schoolAppeal.email"></el-input>
            </el-form-item>
            <el-form-item label="申议内容"
                          :rules="[
                          { required: true, message: '请输入申议内容', trigger: 'blur' }]">
                <el-input
                        type="textarea"
                        :rows="4"
                        placeholder="请输入内容"
                        v-model="schoolAppeal.content">
                </el-input>
            </el-form-item>
            <el-form-item label="滑块验证">
                <div class="block">
                    <span class="demonstration">请将滑块滑动到最右完成验证</span>
                    <el-slider v-model="sliderVal"></el-slider>
                </div>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="saveSchoolAppeal()" :disabled="saveBtn">提交</el-button>
            </el-form-item>
        </el-form>
    </div>
</div>

<script type="text/javascript">
    var app = new Vue({
        el: "#app",
        data: function () {
            return {
                schoolAppeal: {
                    name: '',
                    schoolName: '',
                    phone:'',
                    email:'',
                    content:''
                },
                successFlag:false,
                sliderVal:0
            }
        },
        watch:{
        },
        mounted: function () {
        },
        methods: {
            saveSchoolAppeal:function () {
                var self = this;
                if (this.sliderVal != 100){
                    self.$message({showClose: true, message: '请完成滑块验证', type: 'error'});
                    return false;
                }
                Api.post("/register/schoolAppeal/save",self.schoolAppeal,function (result) {
                    if (result.code == 0) {
                        self.$message({showClose: true, message: '提交成功，请等待通知', type: 'success'});
                        self.successFlag = true;
                    } else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
                })
            }
        }
    });

</script>
</body>
</html>