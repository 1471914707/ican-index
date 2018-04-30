<!DOCTYPE HTML>
<html>
<head>
    <title>统计情况</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
    <script type="text/javascript" src="http://echarts.baidu.com/gallery/vendors/echarts/echarts.min.js"></script>
    <style>

    </style>
</head>
<body class="cbp-spmenu-push">
<div class="main-content">
    <div id="app">
        <div class="sticky-header header-section ">
            <h2 style="display: inline-block;line-height: 68px;margin-left: 3%;">{{activity.name}}--统计情况</h2>
            <div class="clearfix"> </div>
        </div>
        <div id="page-wrapper" style="width:90%">
            <div class="main-page">
                <div class="grids">
                    <template v-if="!loading">
                        <#--<div class="panel panel-widget">-->
                            <#--<h3>每周进度情况<h3>-->
                            <template>
                                <el-select v-model="gmtCreate" placeholder="请选择时间" @change="changeGmtCreate()">
                                    <el-option
                                            v-for="item in list"
                                            :key="item.gmtCreate"
                                            :label="item.gmtCreate"
                                            :value="item.gmtCreate">
                                    </el-option>
                                </el-select>
                            </template>
                                <div id="container" style="height: 450px;width: auto"></div>
                        <#--</div>-->
                    </template>
                    </template>
                    <template v-if="loading">
                    <#include '/include/common/loading.ftl'>
                    </template>
                </div>

            </div>

           <#-- <div class="block-pagination">
                <el-pagination
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page="page"
                        :page-sizes="[10, 20, 30, 40]"
                        :page-size="size"
                        layout="total, sizes, prev, pager, next, jumper"
                        :total="total">
                </el-pagination>
            </div>-->

        </div>

        <div class="dev-page">
            <div class="dev-page-footer dev-page-footer-fixed">
                <div class="container">
                    <div class="copyright">
                        <p>
                            <a href="/">首页</a> > 统计情况
                        </p>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <script>
        <#if activityId??>
        var activityId = ${activityId}
        <#else>
        var activityId = 0;
        </#if>

        var app = new Vue({
            el: "#app",
            data: function () {
                return {
                    activity:{},
                    ratingList:[],
                    projectList:[],
                    list:[],
                    loading:false,
                    gmtCreate:null,
                    counts:{}
                }
            },
            mounted: function () {
                this.loadRatingList();
            },
            methods:{
                loadRatingList:function () {
                    var self = this;
                    Api.get("/counts/task/listJson",{activityId:activityId},function (result) {
                        if (result.code == 0) {
                            self.list = result.data.list;
                            self.total = result.data.total;
                            self.activity = result.data.activity;
                            for (var i=0; i<self.list.length; i++) {
                                self.list[i].gmtCreate = self.list[i].gmtCreate.split(" ")[0];
                            }
                            if (self.list.length > 1) {
                                self.gmtCreate = self.list[0].gmtCreate;
                                self.changeGmtCreate();
                            }
                        } else {
                            self.$message({showClose: true, message: '系统出错，请重新刷新', type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                changeGmtCreate:function () {
                    for (var i=0; i<this.list.length; i++) {
                        if (this.gmtCreate == this.list[i].gmtCreate) {
                            this.counts = JSON.parse(JSON.stringify(this.list[i]));
                            break;
                        }
                    }
                    var datas = [];
                    var content = JSON.parse(this.counts.content);
                    for (var i=1; i<=10; i++){
                        datas.push(content[i+'']);
                    }
                    var dom = document.getElementById("container");
                    var myChart = echarts.init(dom);
                    var app1 = {};
                    var option = null;
                    app1.title = '项目进度统计 - 条形图';

                    option = {
                        title: {
                            text: '每周进度统计/统计人数'+content[11+'']+'人/已做完：'+content[12+'']+'人',
                            subtext: '数据来自ICAN'
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        legend: {
                            data: ['比例']
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis: {
                            type: 'value',
                            boundaryGap: [0, 0.2]
                        },
                        yAxis: {
                            type: 'category',
                            data: ['0-10%','10-20%','20-30%','30-40%','40-50%','50-60%','60-70%','70-80%','80-90%','90-100%']
                        },
                        series: [
                            {
                                name: '达成人数',
                                type: 'bar',
                                data: datas
                            }
                        ]
                    };
                    ;
                    if (option && typeof option === "object") {
                        myChart.setOption(option, true);
                    }
                }
            }
        });
    </script>
</div>
</body>
</html>