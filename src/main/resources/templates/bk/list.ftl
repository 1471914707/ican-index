<!DOCTYPE HTML>
<html>
<head>
    <title>${bk.name} | 交流中心</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
    <style>
        .main-content{
            margin:0 auto;
            width:800px;
            margin-top: 25px;
        }
        .img_avatar {
            border: 1px solid #ccc;
            padding: 2px;
            width: 180px;
            height: 180px;
        }
        .display_name {
            font-size: 20px;
            font-weight: bold;
            color: #454545;
        }
        .text_gray {
            color: Gray;
        }
        .face-img {
            width: 50px;
            height: 50px;
        }
        .small-icon{
            width:auto;
            height: 20px;1
        }
        textarea{
            resize:none !important;
            height:180px;
        }
        .uploadActive{
            display: none;
        }
        .icon-embed {
            width: 20px;
            height:20px;
            cursor: pointer;
        }
        /*.blog-bottom{
            cursor: pointer;
        }*/
        img{
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="main-content">
    <div id="app">
        <div class="person-info">
            <el-row>
                <el-col :span="6" style="margin-right: 15px;">
                    <img src="${bk.headshot}"
                         alt="${bk.name}的头像" class="img_avatar">
                </el-col>
                <el-col :span="17" style="line-height: 20x;">
                    <div style="float:right;">
                        <img src="http://cdn.ican.com/public/images/blogAdd.png" style="width: 28px;height: 28px;cursor: pointer" @click="addVisible=true"></div>
                    <h1 class="display_name">${bk.name}</h1>
                    <br>
                    <span class="text_gray">角色：</span>${bk.roleName}<br>
                    <#if bk.phone??>
                        <span class="text_gray">电话：</span>${bk.phone}<br>
                    </#if>
                    <#if bk.email??>
                        <span class="text_gray">邮箱：</span>${bk.email}<br>
                    </#if>
                    <#if bk.url??>
                        <span class="text_gray">官网：</span>${bk.url}<br>
                    </#if>
                    <#if bk.gmtCreate??>
                        <span class="text_gray">创建时间：</span>${bk.gmtCreate}<br>
                    </#if>
                    <span class="text_gray">发布：</span>${bk.total}条<br>
                    <span @click="openMessageWindow(${bk.id})" style="cursor: pointer;">私信</span>
                </el-col>
            </el-row>
        </div>
        <div class="bk-body">
            <br>
            <el-tabs type="border-card">
                <el-tab-pane label="账号发布">
                    <div v-for="(item, index) in list" style="padding: 20px 50px 10px 70px">
                        <el-row>
                            <el-col :span="3"><img :src="item.headshot" class="face-img" @click="openBk(item.userId)"></el-col>
                            <el-col :span="17">
                                <span @click="openBk(item.userId)" style="cursor:pointer;">{{item.name}}</span><br>
                                <div style="float: right;margin-top: 0px;cursor: pointer" @click="deleteBlog(item.id)"><i class="el-icon-close"></i></div>

                                <#--<div style="display: inline-block; "> <img class="small-icon" src="http://cdn.ican.com/public/images/teacher.png">
                                <#if bk.role ?? && bk.role == 3>
                                    <img src="http://cdn.ican.com/public/images/school.png">
                                </#if>
                                <#if bk.role ?? && bk.role == 4>
                                    <img src="http://cdn.ican.com/public/images/college.png">
                                </#if>
                                <#if bk.role ?? && bk.role == 5>
                                    <img src="http://cdn.ican.com/public/images/teacher.png">
                                </#if>
                                <#if bk.role ?? && bk.role == 6>
                                    <img src="http://cdn.ican.com/public/images/student.png">
                                </#if></div>-->
                                <span style="color: #808080;font-size: 12px">{{getTime(item.gmtCreate)}}</span><br><br>
                                {{item.content}}<br><br>
                                <div v-for="(photo,i) in item.image" style="display: inline-block">
                                    <img :src="photo.url" style="width:125px;height: 125px;cursor:pointer " @click="photoUrl=photo.url;photoVisible=true;">
                                    <template v-if="i % 2 == 0">
                                        <br/>
                                    </template>
                                </div><br>
                                <#--<div style="text-align: center;color: #808080;">
                                    <el-row>
                                        <el-col :span="6" style="border: solid 1px #ddd;font-size: 12px;line-height: 25px;">
                                            <i class="el-icon-circle-close">删除</i>
                                        </el-col>
                                        <el-col :span="6" style="border: solid 1px #ddd;font-size: 12px;line-height: 25px;">
                                            删除
                                        </el-col>
                                        <el-col :span="6" style="border: solid 1px #ddd;font-size: 12px;line-height: 25px;">
                                            删除
                                        </el-col>
                                        <el-col :span="6" style="border: solid 1px #ddd;font-size: 12px;line-height: 25px;">
                                            删除
                                        </el-col>
                                    </el-row>
                                </div>-->
                                <br />
                                <div>
                                    <el-row class="blog-bottom">
                                        <el-col :span="20">
                                            <div style="width: 1px;height: 1px;"></div>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-row>
                                                <el-col :span="24" style="cursor: pointer;">
                                                    <embed src="http://cdn.ican.com/public/svg/comment.svg"
                                                           type="image/svg+xml" class="icon-embed"/>
                                                    <div style="display: inline-block;vertical-align: top;" @click="openMessageWindow2(item.userId)">
                                                        私信</div>
                                                </el-col>
                                                <el-col :span="0">
                                                    <#--<embed src="http://cdn.ican.com/public/svg/like.svg"
                                                           type="image/svg+xml" class="icon-embed"/>
                                                    <div style="display: inline-block;vertical-align: top;">{{item.likeCount}}</div>-->
                                                </el-col>
                                            </el-row>
                                        </el-col>
                                    </el-row>
                                </div>
                                <template v-if="item.commentFlag">
                                    <div  v-if="item.loadingComment">

                                    </div>
                                    <div  v-if="!item.loadingComment">
                                        <#include '/include/common/loading.ftl'>
                                    </div>
                                </template>
                                <div style="background-color:#dddddd;height: 1px;"></div>
                            </el-col>
                        </el-row>
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
                </el-tab-pane>
                <el-tab-pane label="全校发布">
                    <div v-for="(item, index) in list2" style="padding: 20px 50px 10px 70px">
                        <el-row>
                            <el-col :span="3"><img :src="item.headshot" class="face-img" @click="openBk(item.userId)"></el-col>
                            <el-col :span="17">
                                <span @click="openBk(item.userId)" style="cursor:pointer;">{{item.name}}</span><br>
                                <div style="float: right;margin-top: 0px;cursor: pointer" @click="deleteBlog(item.id)"><i class="el-icon-close"></i></div>

                            <#--<div style="display: inline-block; "> <img class="small-icon" src="http://cdn.ican.com/public/images/teacher.png">
                            <#if bk.role ?? && bk.role == 3>
                                <img src="http://cdn.ican.com/public/images/school.png">
                            </#if>
                            <#if bk.role ?? && bk.role == 4>
                                <img src="http://cdn.ican.com/public/images/college.png">
                            </#if>
                            <#if bk.role ?? && bk.role == 5>
                                <img src="http://cdn.ican.com/public/images/teacher.png">
                            </#if>
                            <#if bk.role ?? && bk.role == 6>
                                <img src="http://cdn.ican.com/public/images/student.png">
                            </#if></div>-->
                                <span style="color: #808080;font-size: 12px">{{getTime(item.gmtCreate)}}</span><br><br>
                                {{item.content}}<br><br>
                                <div v-for="(photo,i) in item.image" style="display: inline-block">
                                    <img :src="photo.url" style="width:125px;height: 125px;cursor:pointer " @click="photoUrl=photo.url;photoVisible=true;">
                                    <template v-if="i % 2 == 0">
                                        <br/>
                                    </template>
                                </div><br>
                            <#--<div style="text-align: center;color: #808080;">
                                <el-row>
                                    <el-col :span="6" style="border: solid 1px #ddd;font-size: 12px;line-height: 25px;">
                                        <i class="el-icon-circle-close">删除</i>
                                    </el-col>
                                    <el-col :span="6" style="border: solid 1px #ddd;font-size: 12px;line-height: 25px;">
                                        删除
                                    </el-col>
                                    <el-col :span="6" style="border: solid 1px #ddd;font-size: 12px;line-height: 25px;">
                                        删除
                                    </el-col>
                                    <el-col :span="6" style="border: solid 1px #ddd;font-size: 12px;line-height: 25px;">
                                        删除
                                    </el-col>
                                </el-row>
                            </div>-->
                                <br />
                            <div>
                                <el-row class="blog-bottom">
                                    <el-col :span="20">
                                        <div style="width: 1px;height: 1px;"></div>
                                    </el-col>
                                    <el-col :span="4">
                                        <el-row>
                                            <el-col :span="24">
                                                <embed src="http://cdn.ican.com/public/svg/comment.svg"
                                                       type="image/svg+xml" class="icon-embed"/>
                                                <#--<div style="display: inline-block;vertical-align: top;" @click="item.commentFlag=true;openComment(index);">
                                                    {{item.commentCount>1000?'1000+':item.commentCount}}</div>-->
                                                <div style="display: inline-block;vertical-align: top;" @click="openMessageWindow2(item.userId)">
                                                    私信</div>
                                            </el-col>
                                            <el-col :span="0">
                                                <#--<embed src="http://cdn.ican.com/public/svg/like.svg"
                                                       type="image/svg+xml" class="icon-embed"/>
                                                <div style="display: inline-block;vertical-align: top;">{{item.likeCount}}</div>-->
                                            </el-col>
                                        </el-row>
                                    </el-col>
                                </el-row>
                            </div>
                                <template v-if="item.commentFlag">
                                    <div  v-if="item.loadingComment">

                                    </div>
                                    <div  v-if="!item.loadingComment">
                                    <#include '/include/common/loading.ftl'>
                                    </div>
                                </template>
                                <div style="background-color:#dddddd;height: 1px;"></div>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="block-pagination">
                        <el-pagination
                                @size-change="handleSizeChange2"
                                @current-change="handleCurrentChange2"
                                :current-page="page2"
                                :page-sizes="[20, 30, 40, 50]"
                                :page-size="size2"
                                layout="total, sizes, prev, pager, next, jumper"
                                :total="total2">
                        </el-pagination>
                    </div>
                </el-tab-pane>
                <#--<el-tab-pane label="角色管理">角色管理</el-tab-pane>
                <el-tab-pane label="定时任务补偿">定时任务补偿</el-tab-pane-->
            </el-tabs>

        </div>

        <el-dialog
                title="发布"
                :visible.sync="addVisible"
                width="50%">
                <div style="margin-bottom: 10px;"><el-input type="textarea" v-model="content"></el-input></div>
                <div style="float:right;"><el-button type="success" @click="addBlog()">提交</el-button></div><br><br>
                <el-upload
                        action="/photoUpload"
                        list-type="picture-card"
                        :on-remove="handleRemove"
                        :on-success="photoUploadSuccess">
                    <i class="el-icon-plus"></i>
                </el-upload>
          </el-dialog>

        <el-dialog
                title=""
                :visible.sync="photoVisible"
                width="80%">
            <center>
                <img :src="photoUrl" style="max-width: 90%;height: auto">
            </center>
        </el-dialog>
    </div>

    <script>
        <#if id??>
        var id = ${id}
        <#else>
        var id = 0;
        </#if>
        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    user:{},
                    page:1,
                    size:20,
                    total:0,
                    list: [],
                    page2:1,
                    size2:20,
                    total2:0,
                    list2: [],
                    loading:false,
                    editFlag:false,
                    content:'',
                    imageJson:'',
                    photoUrl:'',
                    photoVisible:false,
                    addVisible:false,
                    imageList:[],
                    commentList:[]
                }
            },
            mounted: function () {
                this.loadBlogList();
                this.loadBlogList2();
            },
            methods:{
                loadBlogList:function (page, size) {
                    var self = this;
                    self.loading = true;
                    var page = page || this.page || 1;
                    var size = size || this.size || 20;
                    Api.get('/bk/listJson',{
                        id:id,
                        type:1,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list = result.data.list;
                                self.total = result.data.total;
                                for (var i=0; i<self.list.length; i++) {
                                    self.list[i].image = JSON.parse(self.list[i].image);
                                    self.list[i].commentFlag = false;
                                    self.list[i].loadingComment = false;
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                loadBlogList2:function (page2, size2) {
                    var self = this;
                    self.loading = true;
                    var page = page2 || this.page2 || 1;
                    var size = size2 || this.size2 || 20;
                    Api.get('/bk/listJson',{
                        id:id,
                        type:2,
                        page:page,
                        size:size
                    },function (result) {
                        if (result.code == 0) {
                            if (result.data.list) {
                                self.list2 = result.data.list;
                                self.total2 = result.data.total;
                                for (var i=0; i<self.list2.length; i++) {
                                    self.list2[i].image = JSON.parse(self.list2[i].image);
                                    self.list2[i].commentFlag = false;
                                    self.list2[i].loadingComment = false;
                                }
                                self.loading = false;
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                /*openComment:function (index) {
                  var self = this;
                  if (self.list[index].id){
                      if (self.list[i].loadingComment == true){
                          self.list[i].loadingComment = false;
                          return true;
                      }
                      Api.get("bk/commentListJson",{id:self.list[index].id},function (result) {
                          if (result.code == 0) {
                              self.user = result.data.user;
                              self.list[index].commentList = result.data.list;
                              self.list[index].commentTotal = result.data.total;
                          }
                      });
                  }
                },*/
                addBlog:function () {
                    var self = this;
                    self.imageJson = JSON.stringify(self.imageList);
                    console.log(self.imageJson);
                    Api.post('/bk/add',{
                        content:self.content,
                        image:self.imageJson
                    },function (result) {
                        if (result.code == 0) {
                            self.$message({showClose: true, message: '发布成功', type: 'success'});
                            self.imageList = [];
                            self.content = '';
                            self.addVisible = false;
                            if (self.page == 1) {
                                self.loadBlogList();
                            }
                        }else {
                            self.$message({showClose: true, message: result.msg, type: 'error'});
                        }
                    });
                },
                deleteBlog:function (id) {
                    var self = this;
                    this.$confirm('此操作将永久删除该内容, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        Api.post('/bk/delete',{
                            id:id
                        },function (result) {
                            if (result.code == 0) {
                                self.$message({showClose: true, message: '删除成功', type: 'success'});
                                for (var i=0; i<self.list.length; i++) {
                                    if (self.list[i].id == id){
                                        self.list.splice(i, 1);
                                    }
                                }
                            }else {
                                self.$message({showClose: true, message: result.msg, type: 'error'});
                            }
                        });
                    }).catch(function () {
                        this.$message({type: 'info', message: '已取消删除'});
                    });
                },
                openBk:function (id) {
                    window.open('/bk?id=' + id);
                },
                photoUploadSuccess:function (result, file, fileList) {
                    var self = this;
                    if (result.code == 0){
                        if (self.imageList.length > 8) {
                            self.$message({showClose: true, message: '最多上传9张图片，多于只取前9张', type: 'error'});
                            return true;
                        }
                        self.imageList.push({url: result.data});
                    } else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
                },
                handleRemove:function (file, fileList) {
                    for (var i=0; i<this.imageList.length; i++) {
                        if (this.imageList[i].url == file.url){
                            this.imageList.split(i, 1);
                        }
                    }
                },
                openMessageWindow:function () {
                    window.open ('/message?toId='+id, 'newwindow',
                            'height=600, width=400, top=150,left=500%, toolbar=no, menubar=no, scrollbars= no, resizable=no,location=true,status=no');
                },
                openMessageWindow2:function (id) {
                    window.open ('/message?toId='+id, 'newwindow',
                            'height=600, width=400, top=150,left=500%, toolbar=no, menubar=no, scrollbars= no, resizable=no,location=true,status=no');
                },
                getTime:function (time) {
                    return DateFun.getTimeFormatText(time);
                },
                getDate:function (dateTime) {
                    if (dateTime.trim() != '') {
                        return dateTime.split(" ")[0];
                    }
                    return '';
                },
                handleSizeChange:function (size) {
                    this.size = size;
                    this.loadBlogList(this.page, this.size);
                },
                handleCurrentChange:function (page) {
                    this.page = page;
                    this.loadBlogList(this.page, this.size);
                },
                handleSizeChange2:function (size) {
                    this.size2 = size;
                    this.loadBlogList2(this.page, this.size);
                },
                handleCurrentChange2:function (page) {
                    this.page2 = page;
                    this.loadBlogList2(this.page, this.size);
                }
            }
        });
    </script>
</div>
</body>
</html>