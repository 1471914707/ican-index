<!DOCTYPE HTML>
<html>
<head>
    <title>学校申请列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <#include '/include/header/admin_header.ftl'>
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
                                        prop="follow.followUserName"
                                        label="最后跟进人"
                                        width="120">
                                </el-table-column>
                                <el-table-column
                                        fixed="right"
                                        label="操作"
                                        min-width="120">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="small" @click="detail(scope.row.id)">查看</el-button>
                                        <el-button type="text" size="small" @click="goWebSite(scope.row.url)">官网</el-button>
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
                        :page-sizes="[10, 20, 30, 40]"
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
                    size:10,
                    total:0,
                    list: [],
                    resultStatusList:[],
                    statusList:[{text:'初始化',value:0},{text:'有效',value:1},{text:'禁停',value:2}],
                    loading:false
                }
            },
            mounted: function () {
                this.loadSchoolList();
            },
            methods:{
                detail:function (id) {
                    window.location.href = '/admin/school/detail?schoolId=' + id;
                },
                loadSchoolList:function (page, size) {
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
                goWebSite:function (url) {
                    window.open(url);
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadSchoolList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadSchoolList(this.page, this.size);
                }
            }
        });
    </script>
</div>
</body>
</html>