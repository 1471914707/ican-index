<!DOCTYPE HTML>
<html>
<head>
    <title>学校申议列表</title>
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
                        <h2>学校申议列表</h2>
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
                                        prop="name"
                                        label="姓名"
                                        width="100">
                                </el-table-column>
                                <el-table-column
                                        prop="schoolName"
                                        label="学校"
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
                                        prop="content"
                                        label="内容"
                                        width="400">
                                    <template slot-scope="scope">
                                        <#--{{scope.row.content.length>20?scope.row.content.substring(0,20):scope.row.content}}-->
                                        {{scope.row.content}}
                                    </template>
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
                                        fixed="right"
                                        label="操作"
                                        min-width="200">
                                    <template slot-scope="scope">
                                        <el-button type="text" size="small" @click="followAddFlag=true;followId=scope.row.id">添加跟进</el-button>
                                        <el-button type="text" size="small"
                                                   @click="followListFlag=true;loadFollowList(scope.row.id)">跟进情况</el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </template>
                        <template v-if="loading">
                        <#include '/include/common/loading.ftl'>
                        </template>

                        <el-dialog
                                title="跟进信息"
                                :visible.sync="followListFlag"
                                width="50%">
                            <template>
                                <el-table
                                        :data="followList"
                                        style="width: 100%">
                                    <el-table-column
                                            prop="gmtCreate"
                                            label="时间"
                                            width="100">
                                    </el-table-column>
                                    <el-table-column
                                            prop="followUserName"
                                            label="姓名"
                                            width="100">
                                    </el-table-column>
                                    <el-table-column
                                            prop="mode"
                                            label="方式"
                                            width="100">
                                    </el-table-column>
                                    <el-table-column
                                            prop="content"
                                            label="内容">
                                    </el-table-column>
                                </el-table>
                                <div class="block-pagination">
                                    <el-pagination
                                            @current-change="handleFollowCurrentChange"
                                            :page-size="followSize"
                                            layout="total, prev, pager, next"
                                            :total="followTotal">
                                    </el-pagination>
                                </div>
                            </template>
                            <span slot="footer" class="dialog-footer">
                               <el-button type="primary" @click="followListFlag = false;">关 闭</el-button>
                           </span>
                        </el-dialog>


                      <el-dialog
                              title="添加跟进信息"
                              :visible.sync="followAddFlag"
                              width="30%">
                          <div>
                              <el-form ref="form" :model="follow" label-width="80px">
                                  <el-form-item label="跟进内容">
                                      <el-input type="textarea" v-model="follow.content"></el-input>
                                  </el-form-item>
                                  <el-form-item label="跟进方式">
                                      <el-radio-group v-model="follow.mode">
                                          <el-radio label="1">手机</el-radio>
                                          <el-radio label="2">QQ</el-radio>
                                          <el-radio label="3">微信</el-radio>
                                          <el-radio label="4">邮箱</el-radio>
                                      </el-radio-group>
                                  </el-form-item>
                                  <el-form-item>
                                      <el-button type="primary" @click="saveFollow();followAddFlag=false;">提交</el-button>
                                      <el-button @click="followAddFlag=false">取消</el-button>
                                  </el-form-item>
                              </el-form>
                          </div>
                      </el-dialog>

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
                            <a href="/">首页</a> > 学校申议列表
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
                    admin:{},
                    page:1,
                    size:2,
                    total:0,
                    list: [],
                    resultStatusList:[],
                    statusList:[{text:'初始化',value:0},{text:'待处理',value:1},{text:'处理中',value:2}
                        ,{text:'通过',value:3},{text:'驳回',value:4}],
                    follow:{content:'',mode:null,followId:0,followType:2},
                    followListFlag:false,
                    followAddFlag:false,
                    followList:[],
                    followPage:1,
                    followTotal:0,
                    followSize:10,
                    modeList:[{"id":1,"name":'电话'},{"id":2,"name":'QQ'},{"id":3,"name":'微信'},{"id":4,"name":'邮箱'}],
                    loading:false
                }
            },
            mounted: function () {
                this.loadSchoolAppealList();
            },
            methods:{
                loadSchoolAppealList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 5;
                    Api.get('/admin/schoolAppeal/list',{
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                for (var i=0; i<self.list.length; i++) {
                                    self.resultStatusList.push(self.list[i].status);
                                    self.list[i].gmtCreate = self.list[i].gmtCreate.split(" ")[0];
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                saveFollow:function () {
                    var self = this;
                    self.follow.followId = self.followId;
                    Api.post("/admin/follow/save",self.follow,function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: '保存成功', type: 'success'});
                            self.loadFollowList();
                        } else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                selectStatus:function (newStatus) {
                    var self = this;
                    for (var i=0; i<self.list.length; i++) {
                        if (self.list[i].status != self.resultStatusList[i]) {
                            //改变的列
                            Api.post('/admin/schoolAppeal/statusSave',{
                                id:self.list[i].id,
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
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                loadFollowList:function (id, page, size) {
                    var self = this;
                    var page = page || this.followPage || 1;
                    var size = size || this.followSize || 10;
                    Api.get('/admin/follow/listJson',{
                        followId:id,
                        followType:2,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            self.followList = result.data.list;
                            self.followTotal = result.data.total;
                            for (var i=0; i<self.followList.length; i++) {
                                self.followList[i].gmtCreate = self.followList[i].gmtCreate.split(" ")[0];
                                self.followList[i].mode = self.getModeName(self.followList[i].mode);
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                getModeName:function (mode) {
                    switch (mode){
                        case 1:
                            return '电话';
                        case 2:
                            return 'QQ';
                        case 3:
                            return '微信';
                        case 4:
                            return '邮箱';
                        default:
                            return '未知';
                    }
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadSchoolAppealList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadSchoolAppealList(this.page, this.size);
                },
                handleFollowCurrentChange:function (page) {
                    this.page = page;
                    this.loadFollowList(this.followId,this.page, this.size);
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
        showLeftPush.click();
        function disableOther( button ) {
            if( button !== 'showLeftPush' ) {
                classie.toggle( showLeftPush, 'disabled' );
            }
        }
    </script>

</div>
</body>
</html>