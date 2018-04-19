<!DOCTYPE HTML>
<html>
<head>
    <title>详情 | 学校</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
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
        .el-row {
            padding: 10px;
        }
        .avatar-uploader .el-upload {
            border-radius: 6px;
            cursor: pointer;
            position: relative;
            overflow: hidden;
        }
        .avatar-uploader .el-upload:hover {
            border-color: #409EFF;
        }
        .avatar {
            width: 178px;
            height: 178px;
            display: block;
            border-radius: 50%;
        }
        .radio label, .checkbox label, label{
            font-weight: 900;
            color:#000;
        }
    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
    <#include '/include/header/admin_header.ftl'>
        <div id="page-wrapper">
            <div class="main-page">
                <el-card class="grids">
                    <div slot="header" class="clearfix progressbar-heading grids-heading">
                        <span>{{schoolName}}</span>
                        <el-button style="float: right; padding: 3px 0" type="text" @click="editFlag=true">编辑</el-button>
                    </div>
                    <template v-if="!editFlag">
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>头像：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                <div class="profile_img">
                                    <span class="prfil-img"><img :src="school.headshot" alt=""> </span>
                                    <div class="clearfix"></div>
                                </div>
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>负责人：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{school.name}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>电话：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{school.phone}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>邮箱：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{school.email}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>官网：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{school.url}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>所在城市：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{getCityName(school.country, school.province, school.city)}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>地址：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="16"><div class="grid-content bg-purple-light">
                                {{school.address}}
                            </div></el-col>
                        </el-row>
                    </template>

                    <template v-if="editFlag">
                        <el-form label-width="80px">
                            <el-form-item label="头像">
                                <el-upload
                                        class="avatar-uploader"
                                        action="/photoUpload"
                                        :show-file-list="false"
                                        :on-success="photoUploadSuccess">
                                    <img v-if="school.headshot.trim().length > 0" :src="school.headshot" class="avatar">
                                    重新上传
                                </el-upload>
                            </el-form-item>
                            <el-form-item label="学校">
                                <el-input v-model="school.schoolName"></el-input>
                            </el-form-item>
                            <el-form-item label="电话">
                                <el-input v-model="school.phone"></el-input>
                            </el-form-item>
                            <el-form-item label="邮箱">
                                <el-input v-model="school.email"></el-input>
                            </el-form-item>
                            <el-form-item label="官网">
                                <el-input v-model="school.url"></el-input>
                            </el-form-item>
                            <el-form-item label="国家">
                                <el-select v-model="school.country" placeholder="请选择国家" @change="changeCountry()">
                                    <el-option
                                            v-for="item in countryList"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="省份">
                                <el-select v-model="school.province" placeholder="请选择省份" @change="changeProvince()">
                                    <el-option
                                            v-for="item in provinceList"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="城市">
                                <el-select v-model="school.city" placeholder="请选择城市">
                                    <el-option
                                            v-for="item in cityList"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                            <el-form-item label="地址">
                                <el-input v-model="school.address"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="onSubmit()">保存</el-button>
                                <el-button @click="loadSchool();editFlag=false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </template>
                </el-card>
            </div>
            <br><br>

            <el-card class="grids">
                <el-row>
                    <el-col :span="8" v-for="(item, index) in authPhotoList" :key="o" :offset="index > 0 ? 2 : 0">
                        <el-card :body-style="{ padding: '0px' }">
                            <img :src="item.url" class="image">
                            <#--<div style="padding: 14px;">
                                <span>{{item.remark}}</span>
                                <div class="bottom clearfix">
                                    <el-button type="text" class="button">操作按钮</el-button>
                                </div>
                            </div>-->
                        </el-card>
                    </el-col>
                </el-row>
            </el-card>

            <br><br>
            <el-card class="grids">
                <div slot="header" class="clearfix progressbar-heading grids-heading">
                    <span>跟进记录</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="followAddFlag=true">添加</el-button>
                </div>
                <div>
                    <template v-if="followAddFlag == true">
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
                    </template>
                    <template>
                        <el-table
                                :data="followList"
                                style="width: 100%">
                            <el-table-column
                                    prop="gmtCreate"
                                    label="跟进时间"
                                    width="180">
                            </el-table-column>
                            <el-table-column
                                    prop="followUserName"
                                    label="跟进人"
                                    width="180">
                            </el-table-column>
                            <el-table-column
                                    prop="mode"
                                    label="跟进方式">
                            </el-table-column>
                            <el-table-column
                                    prop="content"
                                    label="跟进情况">
                            </el-table-column>
                        </el-table>
                    </template>
                    <div class="block-pagination">
                        <el-pagination
                                @current-change="handleCurrentChange"
                                :page-size="followSize"
                                layout="total, prev, pager, next"
                                :total="followTotal">
                        </el-pagination>
                    </div>
                </div>
            </el-card>
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
                school:{},
                schoolName:'',
                allCityList:[],
                editFlag:false,
                countryList:[],
                provinceList:[],
                cityList:[],
                follow:{content:'',mode:null,followId:schoolId,followType:1},
                followAddFlag:false,
                followList:[],
                followPage:1,
                followTotal:0,
                followSize:10,
                modeList:[{"id":1,"name":'电话'},{"id":2,"name":'QQ'},{"id":3,"name":'微信'},{"id":4,"name":'邮箱'}],
                authPhotoList:[]
            }
        },
        watch:{
            school:function (newVal, oldVal) {
                if (this.school.country && this.school.province && this.school.city) {
                    this.loadCityList();
                }
            }
        },
        mounted: function () {
            this.loadSchool();
            this.loadFollowList();
        },
        methods:{
            loadSchool:function () {
                var self = this;
                Api.get('/admin/school/detailJson',{
                    schoolId:schoolId
                },function (result) {
                    if (result.code == 0) {
                        if (result.data) {
                            self.school = result.data.school;
                            self.authPhotoList = result.data.authPhotoList;
                            self.schoolName = self.school.schoolName;
                        }
                    }else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
                });
            },
            loadFollowList:function (page, size) {
                var self = this;
                var page = page || this.followPage || 1;
                var size = size || this.followSize || 10;
                Api.get('/admin/follow/listJson',{
                    followId:schoolId,
                    followType:1,
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
            onSubmit:function () {
                var self = this;
                Api.post("/admin/school/save",self.school,function (result) {
                    if (result.code == 0) {
                        self.$message({showClose: true, message: '保存成功', type: 'success'});
                    } else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
                });
            },
            saveFollow:function () {
                var self = this;
                Api.post("/admin/follow/save",self.follow,function (result) {
                    if (result.code == 0) {
                        self.$message({showClose: true, message: '保存成功', type: 'success'});
                        self.loadFollowList();
                    } else {
                        self.$message({showClose: true, message: result.msg, type: 'error'});
                    }
                });
            },
            loadCityList:function () {
                if (this.allCityList.length > 0) {
                    return false;
                }
                var self = this;
                Api.get("/allCityJson",{},function (result) {
                    if (result.code == 0) {
                        self.allCityList = result.data;
                        for (var i=0; i<self.allCityList.length; i++){
                            if (self.allCityList[i].parentId == 0) {
                                self.countryList.push(self.allCityList[i]);
                            }
                            if (self.allCityList[i].parentId == self.school.country) {
                                self.provinceList.push(self.allCityList[i]);
                            }
                            if (self.allCityList[i].parentId == self.school.province) {
                                self.cityList.push(self.allCityList[i]);
                            }
                        }
                    }
                });
            },
            changeCountry:function () {
              //清空省份和城市
              this.provinceList = [];
              this.cityList = [];
              this.school.province = null;
              this.school.city = null;
              for (var i=0; i<this.allCityList.length; i++) {
                  if (this.allCityList[i].parentId == this.school.country) {
                      this.provinceList.push(this.allCityList[i]);
                  }
              }
            },
            changeProvince:function () {
                //清空省份和城市
                this.cityList = [];
                this.school.city = null;
                for (var i=0; i<this.allCityList.length; i++) {
                    if (this.allCityList[i].parentId == this.school.province) {
                        this.cityList.push(this.allCityList[i]);
                    }
                }
            },
            photoUploadSuccess:function (result, file, fileList) {
                var self = this;
                if (result.code == 0){
                    self.school.headshot = result.data;
                } else {
                    self.$message({showClose: true, message: result.msg, type: 'error'});
                }
            },
            getCityName:function (country, province, city) {
                var cityName = {country:'',province:'',city:''};
                var self = this;
                for (var i=0; i<self.allCityList.length; i++) {
                    if (country == self.allCityList[i].id) {
                        cityName['country'] = self.allCityList[i].name;
                        continue;
                    }
                    if (province == self.allCityList[i].id) {
                        cityName['province'] = self.allCityList[i].name;
                        continue;
                    }
                    if (city == self.allCityList[i].id) {
                        cityName['city'] = self.allCityList[i].name;
                    }
                }
                return cityName['country'] + " / " + cityName['province'] + " / " + cityName['city'];
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
            handleCurrentChange:function (page) {
                this.page = page;
                this.localFollowList(this.page, this.size);
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