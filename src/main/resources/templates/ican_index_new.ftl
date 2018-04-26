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
            background: #ffffff;
        }
        .carousel-info {
            padding-top: 90px;
            width: 90%;
            margin: 0px auto;
            overflow-y: hidden;
        }
         .transition-box {
             margin-bottom: 10px;
             width: 200px;
             height: 100px;
             border-radius: 4px;
             background-color: #409EFF;
             text-align: center;
             color: #fff;
             padding: 40px 20px;
             box-sizing: border-box;
             margin-right: 20px;
         }
    </style>
</head>
<body>
<div id="app">
    <div id="header">
        <div class="sticky-header header-section" style="height: 84px;">
            jj
        </div>
    </div>
    <div class="carousel-info">
        <el-row>
            <el-col :span="18">
                {{time}}
            </el-col>
            <el-col :span="6" style="background:#000;">
88
            </el-col>
        </el-row>
        <div>

        </div>
    </div>
</div>

<script type="text/javascript">

    var app = new Vue({
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
    });

</script>
</body>
</html>