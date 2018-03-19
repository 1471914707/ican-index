<!DOCTYPE HTML>
<html>
<head>
    <title>详情 | 　</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
    <script>
        /*     new WOW().init();*/
    </script>
    <style>
        ul{
            list-style-type:none;
        }
        .el-carousel__mask, .el-cascader-menu, .el-cascader-menu__item.is-disabled:hover, .el-collapse-item__header, .el-collapse-item__wrap{
            background-color: #2a2f43;
            color: #fff;
            font-weight: 600;
            padding-left: 5%;
        }
        .clearfix:before,
        .clearfix:after {
            display: table;
            content: "";
        }
        .clearfix:after {
            clear: both
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sidebar" role="navigation">
            <div class="navbar-collapse">
                <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right dev-page-sidebar mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar" id="cbp-spmenu-s1">
                    <div>
                        <el-collapse>
                            <a href="www.baidu.com"><el-collapse-item title="一致性 Consistency" name="1">
                            </el-collapse-item></a>
                        </el-collapse>
                    </div>
                </nav>
            </div>
        </div>
        <div class="sticky-header header-section ">
            <div class="header-left">
                <div class="logo">
                    <a href="index.html">
                        <ul>
                            <li><img src="http://cdn.ican.com/public/images/logo.png" alt="" /></li>
                            <li><h1>Ican</h1></li>
                            <div class="clearfix"> </div>
                        </ul>
                    </a>
                </div>
                <div class="header-right header-right-grid">
                    <div class="profile_details_left">
                        <div class="clearfix"> </div>
                    </div>
                </div>

                <div class="clearfix"> </div>
            </div>
            <div class="header-right" style="float: right;">
                <div class="profile_details">
                </div>
                <button id="showLeftPush"><img  src="http://cdn.ican.com/public/images/bars.png" style="max-width:18.003px;max-height:23.333px;"></button>
                <div class="clearfix"> </div>
            </div>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper">
            <div class="main-page">
                <el-card class="grids">
                    <div slot="header" class="clearfix progressbar-heading grids-heading">
                        <span>卡片名称</span>
                        <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
                    </div>
                    <div v-for="o in 4" :key="o" class="panel panel-widget">
                        {{'列表内容 ' + o }}
                    </div>
                </el-card>
            </div>

        </div>

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/">首页</a> > <a href="/admin">学校申请列表</a> > {{school.schoolName}}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>


<script type="text/javascript">
    <#if schoolId??>
    var schoolId = ${schoolId}
    <#else>
    var schoolId = 0;
    </#if>

    var app = new Vue({
        el: "#app",
        data: function () {
            return {
                school:{}
            }
        },
        mounted: function () {
            this.localSchool();
        },
        methods:{
            localSchool:function () {
                var self = this;
                Api.get('/admin/school/detailJson',{
                    schoolId:schoolId
                },function (result) {
                    if (result.code == 0) {
                        if (result.data) {
                            self.school = result.data;
                        }
                    }else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
                });
            },
            getDate:function (dateTime) {
                if (dateTime.trim() != '') {
                    return dateTime.split(" ")[0];
                }
                return '';
            },
            getStatusName:function (status) {
                switch (status) {
                    case 0:
                        return '初始化';
                    case 1:
                        return '有效';
                    case 2:
                        return '禁停';
                    default:
                        return '未知';
                }
            },
            selectStatus:function (newStatus) {
                console.log(newStatus);
                var self = this;
                for (var i=0; i<self.list.length; i++) {
                    if (self.list[i].status != self.resultStatusList[i]) {
                        //改变的列
                        Api.post('/admin/schoolAuth',{
                            schoolId:self.list[i].id,
                            status:newStatus
                        },function (result) {
                            if (result.code == 0) {
                                self.$message({showClose: true, message: '更改成功', type: 'true'});
                                self.resultStatusList[i] = newStatus;
                            }else {
                                self.$message({showClose: true, message: result.msg, type: 'error'});
                                self.list[i].status = self.resultStatusList[i];
                            }
                        });
                        break;
                    }
                }
            },
            handleSizeChange:function (size) {
                this.size = size;
                this.localSchoolList(this.page, this.size);
            },
            handleCurrentChange:function (page) {
                this.page = page;
                this.localSchoolList(this.page, this.size);
            }
        }
    });
</script>
    <script>
        var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
                showLeftPush = document.getElementById( 'showLeftPush' ),
                body = document.body;

        showLeftPush.onclick = function() {
            classie.toggle( this, 'active' );
            classie.toggle( body, 'cbp-spmenu-push-toright' );
            classie.toggle( menuLeft, 'cbp-spmenu-open' );
            disableOther( 'showLeftPush' );
        };


        function disableOther( button ) {
            if( button !== 'showLeftPush' ) {
                classie.toggle( showLeftPush, 'disabled' );
            }
        }
    </script>

</div>
</body>
</html>