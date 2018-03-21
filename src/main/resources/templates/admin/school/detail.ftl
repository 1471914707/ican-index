<!DOCTYPE HTML>
<html>
<head>
    <title>详情 | 　</title>
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
                        <span>{{schoolName}}</span>
                        <el-button style="float: right; padding: 3px 0" type="text" @click="editFlag=true">编辑</el-button>
                    </div>
                    <#--<div v-for="o in 4" :key="o" class="panel panel-widget">
                        {{'列表内容 ' + o }}
                    </div>-->
                    <template v-if="!editFlag">
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>头像：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="8"><div class="grid-content bg-purple-light">
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
                            <el-col :span="8" :xs="8"><div class="grid-content bg-purple-light">
                                {{school.name}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>电话：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="8"><div class="grid-content bg-purple-light">
                                {{school.phone}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>邮箱：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="8"><div class="grid-content bg-purple-light">
                                {{school.email}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>官网：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="8"><div class="grid-content bg-purple-light">
                                {{school.url}}
                            </div></el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="4" :xs="0"><div class="grid-content bg-purple">
                                <h3>所在城市：</h3>
                            </div></el-col>
                            <el-col :span="8" :xs="8"><div class="grid-content bg-purple-light">
                                {{getCityName(school.country, school.province, school.city)}}
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
                        </el-form>
                    </template>
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
                school:{},
                schoolName:'',
                allCityList:[],
                editFlag:false,
                countryList:[],
                provinceList:[],
                cityList:[]
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
                            self.schoolName = self.school.schoolName;
                        }
                    }else {
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
              for (var i=0; i<this.allCityList.length; i++) {
                  if (this.allCityList[i].parentId == this.school.country) {
                      this.provinceList.push(this.allCityList[i]);
                  }
              }
            },
            changeCountry:function () {
                //清空省份和城市
                this.provinceList = [];
                this.cityList = [];
                for (var i=0; i<this.allCityList.length; i++) {
                    if (this.allCityList[i].parentId == this.school.country) {
                        this.provinceList.push(this.allCityList[i]);
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