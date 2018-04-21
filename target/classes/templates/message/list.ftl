<!DOCTYPE HTML>
<html>
<head>
    <title>消息列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
    <style>
        img{
            display: inline-block;
            width:50px;height: 50px;border-radius: 50%
        }
        .item1 {
            margin-top: 10px;
            margin-right: 40px;
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
            <h2 style="display: inline-block;line-height: 68px;margin-left: 5%;">我的消息<i class="el-icon-refresh" @click="loadMessageList()" style="cursor:pointer;"></i></h2>
        </div>
       
        <div style="padding-top: 75px;width: 90%;margin: 0 auto;">
            <template v-if="loading">
            <#include '/include/common/loading.ftl'>
            </template>
            <template v-if="!loading">
                <template v-for="(item, index) in list">
                    <div @click="openMessageWindow(userId==item.fromId?item.toId:item.fromId);" style="cursor: pointer;">
                    <template v-if="item.fromId == userId">
                        <template v-if="item.hasRead == 1">
                            <el-badge value="new" class="item1">
                                <img :src="item.toUser.headshot" style="">
                            </el-badge>
                        </template>
                        <template v-if="item.hasRead == 2" class="item1">
                            <el-badge class="item1">
                                <img :src="item.toUser.headshot" style="">
                            </el-badge>
                        </template>
                    </template>
                    <template v-if="item.toId == userId">
                        <template v-if="item.hasRead == 1">
                            <el-badge value="new" class="item1">
                                <img :src="item.fromUser.headshot">
                            </el-badge>
                        </template>
                        <template v-if="item.hasRead == 2" class="item1">
                            <el-badge class="item1">
                                <img :src="item.fromUser.headshot">
                            </el-badge>
                        </template>
                    </template>
                        <span style="margin-right: 3%;" v-if="userId==item.fromId">{{item.toUser.name}}</span>
                        <span style="margin-right: 3%;" v-if="userId==item.toId">{{item.fromUser.name}}</span>
                        <span style="margin-right: 3%;font-weight: bolder">{{item.content.length>45?item.content.substring(0,45):item.content}}</span>
                        <span>{{item.gmtCreate}}</span>
                    </div>
                    <br>
                </template>
            </template>
        </div>
        
        
        <div class="block-pagination">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="page"
                    :page-sizes="[20, 30, 40, 50]"
                    :page-size="size"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>

    </div>

    <script>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    slogan:'全站最热门广告位招租！！！有意者电话联系18813960106！！！',
                    page:1,
                    size:20,
                    total:0,
                    list: [],
                    loading:true,
                    userList:[],
                    userId:0,
                }
            },
            mounted: function () {
                this.loadMessageList();
            },
            methods:{
                loadMessageList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/message/listMy',{
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            self.list = result.data.list;
                            self.total = result.data.total;
                            self.userList = result.data.userList;
                            self.userId = result.data.userId;

                            for (var j=0; j<self.list.length; j++) {
                                self.list[j].gmtCreate = self.getDate(self.list[j].gmtCreate);
                                for (var i=0; i<self.userList.length; i++) {
                                    if (self.userList[i].id == self.list[j].fromId) {
                                        self.list[j].fromUser = self.userList[i];
                                    }
                                    if (self.userList[i].id == self.list[j].toId) {
                                        self.list[j].toUser = self.userList[i];
                                    }
                                }
                            }
                            self.loading = false;
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(".")[0];
                    }
                    return '';
                },
                openMessageWindow:function (id) {
                    window.open ('/message?toId='+id, 'newwindow',
                            'height=600, width=400, top=150,left=500%, toolbar=no, menubar=no, scrollbars= no, resizable=no,location=true,status=no');
                },
                handleClose:function () {
                    this.studentList = [];
                    this.infoDialog = false;
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadMessageList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadMessageList(this.page, this.size);
                }
            }
        });
    </script>
</div>
</body>
</html>