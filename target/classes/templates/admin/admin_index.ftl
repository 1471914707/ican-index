<!DOCTYPE HTML>
<html>
<head>
    <title>学校申请列表</title>
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
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <!--left-fixed -navigation-->
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
                <!--grids-->
                <div class="grids">
                    <div class="progressbar-heading grids-heading">
                        <h2>学校申请列表</h2>
                    </div>
                    <div class="panel panel-widget">
                        <template v-if="!loading">
                            <el-table
                                    :data="list"
                                    style="width: 100%"
                            >
                                <el-table-column
                                        style="max-width: 40px;"
                                        prop="id"
                                        label="id"
                                        width="80">
                                </el-table-column>
                                <el-table-column
                                        prop="schoolName"
                                        label="学校"
                                        width="180">
                                </el-table-column>
                                <el-table-column
                                        prop="name"
                                        label="负责人"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        prop="phone"
                                        label="电话"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        prop="email"
                                        label="邮箱"
                                        width="180">
                                </el-table-column>
                                <el-table-column
                                        prop="gmtCreate"
                                        label="申请时间"
                                        width="100">
                                </el-table-column>
                                <el-table-column
                                        prop="gmtModified"
                                        label="最后修改时间"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        label="状态"
                                        width="100">
                                    <template slot-scope="scope">
                                        <select class="form-control1" v-model="scope.row.status" @change="selectStatus(scope.row.status)">
                                            <option v-for="option in statusList" v-bind:value="option.value">
                                                {{ option.text }}
                                            </option>
                                        </select>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                        prop="gmtModified"
                                        label="最后跟进人"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        fixed="right"
                                        label="操作"
                                        width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="small">查看</el-button>
                                        <el-button type="text" size="small">官网</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </template>
                        <template v-if="loading">
                            <#include '/include/common/loading.ftl'>
                        </template>
                    </div>
                </div>
            </div>

            <div class="block-pagination">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-sizes="[2, 4, 6, 8]"
                        :page-size="size"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </div>

        </div>

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/">首页</a> > 学校申请列表
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <script>
        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    slogan:'全站最热门广告位招租！！！有意者电话联系18813960106！！！',
                    page:1,
                    size:2,
                    total:0,
                    list: [],
                    resultStatusList:[],
                    statusList:[{text:'初始化',value:0},{text:'有效',value:1},{text:'禁停',value:2}],
                    loading:false
                }
            },
            mounted: function () {
                this.localSchoolList();
            },
            methods:{
                localSchoolList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 5;
                    Api.get('/admin/school/schoolList',{
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].gmtCreate = self.getDate(self.list[i].gmtCreate);
                                    self.list[i].gmtModified = self.getDate(self.list[i].gmtModified);
                                    self.resultStatusList.push(self.list[i].status);
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
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
                    var self = this;
                    for (var i=0; i<self.list.length; i++) {
                        if (self.list[i].status != self.resultStatusList[i]) {
                            //改变的列
                            Api.post('/admin/school/schoolAuth',{
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