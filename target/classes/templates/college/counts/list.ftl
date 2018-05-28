<!DOCTYPE HTML>
<html>
<head>
    <title>统计情况</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
    <script src="http://cdn.ican.com/public/echarts/echarts4.0.2.min.js"></script>
    <style>
        .el-input__inner{
            height:33px !important;
        }
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
                            <h2 style="display: inline-block;margin-right: 15px;">每周进度统计</h2>
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
                <br><br><br>
                <div class="grids">
                    <h2 style="display: inline-block;margin-right: 15px;">成绩分析</h2>
                    <template>
                            <el-select v-model="answerId" placeholder="请选择答辩安排" @change="changeAnswer()">
                                <el-option
                                        v-for="item in answerList"
                                        :key="item.name"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        <template v-if="loadingGrade">
                            <div>
                                <br>正在分析...
                            <#include '/include/common/loading.ftl'>
                            </div>
                        </template>
                        <template>
                            <div id="container2" style="height: 450px;width: auto"></div>
                            <div id="container1"></div>
                        </template>
                    <#--</div>-->
                    </template>
                    </template>
                </div>
            </div>
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
                    counts:{},
                    
                    answerList:[],
                    loadingGrade:false,
                    answerId:null,
                    ratingList:[],
                    projectList:[],
                    gradeList:[],
                    gradePerList:[{value:0,name:'0-10'},{value:0,name:'10-20'},{value:0,name:'20-30'},{value:0,name:'30-40'},
                        {value:0,name:'40-50'},{value:0,name:'50-60'},{value:0,name:'60-70'},{value:0,name:'70-80'},{value:0,name:'80-90'},
                        {value:0,name:'90-100'}],
                    gradeMax:0,
                    gradeMin:100,
                    gradeAvg:0
                }
            },
            mounted: function () {
                this.loadList();
                this.loadAnswerList();
            },
            methods:{
                loadAnswerList:function () {
                    var self = this;
                    Api.get("/answerArrange/listJson",{activityId:activityId},function (result) {
                        if (result.code == 0) {
                            self.answerList = result.data.list;
                        } else {
                            self.$message({showClose: true, message: '系统出错，请重新刷新', type: 'error'});
                            self.loading = false;
                        }
                    });
                },
                loadList:function () {
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
                changeAnswer:function () {
                    var self = this;
                    self.loadingGrade = true;
                    Api.get("/counts/rating/listJson",{activityId:activityId,answerId:self.answerId},function (result) {
                        if (result.code == 0) {
                            self.projectList = result.data.projectList;
                            self.ratingList = result.data.ratingList;
                            self.answerArrange = result.data.answerArrange;
                            //处理一下成绩相关,项目区分
                            self.gradeList = [];
                            var teacherRatio = self.answerArrange.ratio1 / 100;
                            var groupsRatio = self.answerArrange.ratio2 / 100;
                            for (var i=0; i<self.projectList.length; i++) {
                                var info = {};
                                var groupsNumTotal = 0;
                                var groupsScoreTotal = 0;
                                for (var j=0; j<self.ratingList.length; j++) {
                                    if (self.ratingList[j].teacherId == self.projectList[i].teacherId && self.ratingList[j].projectId == self.projectList[i].id){
                                        //项目的指导教师评分
                                        info.teacherRating = self.ratingList[j].score;
                                        continue;
                                    }
                                    if (self.ratingList[j].projectId == self.projectList[i].id){
                                        groupsNumTotal ++;
                                        groupsScoreTotal += self.ratingList[j].score;
                                    }
                                }
                                info.groupsRating = groupsScoreTotal / groupsNumTotal;
                                info.totalRating = info.teacherRating * teacherRatio + info.groupsRating * groupsRatio;
                                self.gradeList.push(info);
                            }
                            var num = 0;
                            var gradeTotal = 0;
                            self.gradeMax = 0;
                            self.gradeMin = 100;
                            for (var i=0; i<self.gradePerList.length; i++) {
                                self.gradePerList[i].value = 0;
                            }
                            for (var i=0; i<self.gradeList.length; i++) {
                                if (self.gradeList[i].totalRating) {
                                    gradeTotal += self.gradeList[i].totalRating;
                                    console.log(Math.floor(self.gradeList[i].totalRating / 10))
                                    self.gradePerList[Math.floor(self.gradeList[i].totalRating / 10)].value ++;
                                    if (self.gradeList[i].totalRating > self.gradeMax) {
                                        self.gradeMax = self.gradeList[i].totalRating.toFixed(2);
                                    }
                                    if (self.gradeList[i].totalRating < self.gradeMin) {
                                        self.gradeMin = self.gradeList[i].totalRating.toFixed(2);
                                    }
                                } else {
                                    num ++;
                                }
                            }
                            if (gradeTotal > 0) {
                                self.gradeAvg = (gradeTotal / self.projectList.length).toFixed(2);
                            }
                            if (self.gradeList.length == num) {
                                self.gradeMin = 0;
                                self.gradeMax = 0;
                                self.gradeAvg = 0;
                                self.gradePerList = [{value:0,name:'0-10'},{value:0,name:'10-20'},{value:0,name:'20-30'},{value:0,name:'30-40'},
                                    {value:0,name:'40-50'},{value:0,name:'50-60'},{value:0,name:'60-70'},{value:0,name:'70-80'},{value:0,name:'80-90'},
                                    {value:0,name:'90-100'}];
                            }
                            var echartsPie;
                            var option = {
                                title : {
                                    text: '',
                                    subtext: '',
                                    x:'center'
                                },
                                tooltip : {
                                    trigger: 'item',
                                    formatter: "{a} <br/>{b} : {c} 人"
                                },
                                legend: {
                                    orient : 'vertical',
                                    x : 'left',
                                    data:[]
                                },
                                toolbox: {
                                    show : true,
                                    feature : {
                                        mark : {show: true},
                                        dataView : {show: true, readOnly: false},
                                        magicType : {
                                            show: true,
                                            type: ['pie', 'funnel'],
                                            option: {
                                                funnel: {
                                                    x: '25%',
                                                    width: '50%',
                                                    funnelAlign: 'left',
                                                    max: 1548
                                                }
                                            }
                                        },
                                        restore : {show: true},
                                        saveAsImage : {show: true}
                                    }
                                },
                                calculable : true,
                                series : [
                                    {
                                        name:'人数',
                                        type:'pie',
                                        radius : '55%',//饼图的半径大小
                                        center: ['50%', '60%'],//饼图的位置
                                        data:self.gradePerList
                                    }
                                ]
                            };

                            echartsPie = echarts.init(document.getElementById('container1'));
                            $(function(){
                                echartsPie.setOption(option);
                            });
                            var datas = [];
                            for (var i=0; i<self.gradePerList.length; i++) {
                                datas.push(self.gradePerList[i].value);
                            }
                            var dom1 = document.getElementById("container2");
                            var myChart1 = echarts.init(dom1);
                            var app2 = {};
                            var option1 = null;
                            app2.title = '成绩分析统计 - 条形图';

                            option1 = {
                                title: {
                                    text: '统计项目'+self.projectList.length+'个/最高分：'+self.gradeMax+'分/最低分：'+self.gradeMin+'分/平均分：'+self.gradeAvg+'分',
                                    subtext: '数据来自ICAN'
                                },
                                tooltip: {
                                    trigger: 'axis',
                                    axisPointer: {
                                        type: 'shadow'
                                    }
                                },
                                legend: {
                                    data: ['区间']
                                },
                                grid: {
                                    left: '3%',
                                    right: '4%',
                                    bottom: '3%',
                                    containLabel: true
                                },
                                xAxis: {
                                    type: 'value',
                                    boundaryGap: [0, 1]
                                },
                                yAxis: {
                                    type: 'category',
                                    data: ['0-10','10-20','20-30','30-40','40-50','50-60','60-70','70-80','80-90','90-100']
                                },
                                series: [
                                    {
                                        name: '所占人数',
                                        type: 'bar',
                                        data: datas
                                    }
                                ]
                            };
                            ;
                            if (option1 && typeof option1 === "object") {
                                myChart1.setOption(option1, true);
                            }
                            self.loadingGrade = false;
                        } else {
                            self.$message({showClose: true, message: '系统出错，请重新刷新', type: 'error'});
                            self.loadingGrade = false;
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
                            text: '统计人数'+content[11+'']+'人/已做完：'+content[12+'']+'人',
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