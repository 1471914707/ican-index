<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Content-Type" content="text/css; charset=utf-8"/>
    <title>首页</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
    <meta name="format-detection" content="telephone=no" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <style>
        @media screen and (max-width: 850px) {
            .el-menu-header-long {
                display: none;
            }
        }
        @media screen and (min-width: 850px) {
            .el-menu-header-short {
                display: none;
            }
        }
        @media screen and (max-width: 400px) {
            .el-menu-header-mini {
                display: none;
            }
        }
        a{
            text-decoration:none;
            font-weight:bolder;
            color: black;
            font-size: 16px;
        }
    </style>
</head>
<body>
<div id="header">
    <el-menu class="el-menu-header-long" mode="horizontal">
        <el-row>
            <el-col :span="3">
                <div style="width: 10px;height: 10px;"></div>
            </el-col>
            <el-col :span="5" style="text-align: center" >
                <el-menu-item index="1"><img src="http://www.linjiayu.cn/img/logo.png" height="56px"></el-menu-item>
            </el-col>
            <el-col :span="2">
                <el-menu-item index="1"><a href="/login?role=3">学校登录</a></el-menu-item>
            </el-col>
            <el-col :span="2">
                <el-menu-item index="2"><a href="/login?role=4">学院登录</a></el-menu-item>
            </el-col>
            <el-col :span="2">
                <el-menu-item index="3"><a href="/login?role=5">导师登录</a></el-menu-item>
            </el-col>
            <el-col :span="2">
                <el-menu-item index="4"><a href="/login?role=6">学生登录</a></el-menu-item>
            </el-col>
            <el-col :span="2">
                <el-menu-item index="5"><a href="https://www.ele.me">博客园</a></el-menu-item>
            </el-col>
            <el-col :span="3">
            </el-col>

        </el-row>
    </el-menu>
    <el-menu class="el-menu-header-short" mode="horizontal">
        <el-row>
            <el-col :span="6">
                <div style="width: 10px;height: 10px;"></div>
            </el-col>
            <el-col :span="12">
                <div align="center">
                  <el-menu-item index="1"><img src="http://www.linjiayu.cn/img/logo.png" style="max-height: 56px;max-width: 155px"></el-menu-item>
                </div>
            </el-col>
            <el-col :span="6" style="text-align: right">
            </el-col>

        </el-row>
    </el-menu>
</div>
<script type="text/javascript">
    var app = new Vue({
        el: "#header",
        data: function () {
            return {
                page: 1,
                size: 20,
                total: 0,
                name:'',
                list: []
            }
        },
        mounted: function () {
            this.loadList();
        },
        methods: {
            edit: function (id) {
                /*if (id) {
                    location.href = Api.speakerEdit+"?activityId=" + activityId + "&speakerId=" + id;
                } else {
                    location.href = Api.speakerList;
                }*/
            },
            detail:function (id) {
                /*if (id) {
                    location.href = Api.speakerDetail+"?activityId=" + activityId + "&speakerId=" + id;
                } else {
                    location.href = Api.speakerList;
                }*/
            },
            loadList: function (page, size) {
                /*page = page || this.page || 1;
                this.page = page;
                size = size || this.size || 20;
                var self = this;
                Api.get(Api.speakerListJson,{
                    activityId: activityId,
                    name:self.name,
                    page: page,
                    size: size
                },function (data) {
                    self.list = data.data.list;
                    self.total = data.data.total;
                });*/
            },
            handleSizeChange: function (size) {
                /*this.size = size;
                this.loadList();*/
            },
            handleCurrentChange: function (page) {/*
                this.page = page;
                this.loadList();*/
            }
        }
    });
</script>
</body>
