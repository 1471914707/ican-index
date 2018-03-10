<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Ican | 毕业设计平台</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <#include 'include/cssjs_commonv2.ftl'>
    <#include 'include/header/header-index.ftl'>
<#--    <script src="https://cdn.bootcss.com/echarts/4.0.2/echarts.min.js"></script>
    <script src="http://data-visual.cn/datav/src/js/echarts/extension/echarts-wordcloud.min.js"></script>
 -->
    <script src="http://cdn.ican.com/public/echarts/echarts4.0.2.min.js"></script>
    <script src="http://cdn.ican.com/public/echarts/echarts-wordcloud.min.js"></script>
    <style>
        .main{margin-left:auto;margin-right:auto;width: 1200px;height:600px;max-width:1800px;max-height:1600px;margin-top: 20px;}
        @media screen and (max-width: 768px) {
            .main{margin-left:auto;margin-right:auto;width: 400px;height:1400px;margin-top: 20px;}
        }
    </style>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="app">
<div id="main" class="main"></div>
</div>

<script type="text/javascript">
    /*var myChart = echarts.init(document.getElementById('main'));*/
    /*var option = {
        title: {
            link: 'https://www.baidu.com/s?wd=' + encodeURIComponent('ECharts'),
            x: 'center',
            textStyle: {
                fontSize: 23
            }

        },
        backgroundColor: '#FFFFFF',
        tooltip: {
            show: true
        },
        toolbox: {
            feature: {
                /!*saveAsImage: {
                    iconStyle: {
                        normal: {
                            color: '#FFFFFF'
                        }
                    }
                }*!/
            }
        },
        series: [{
            name: '学校热点分析',
            type: 'wordCloud',
            //size: ['9%', '99%'],
            sizeRange: [20, 100],
            //textRotation: [0, 45, 90, -45],
            rotationRange: [-45, 90],
            //shape: 'circle',
            textPadding: 0,
            autoSize: {
                enable: true,
                minSize: 20
            },
            order:false,
            textStyle: {
                normal: {
                    color: function() {
                        return 'rgb(' + [
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160)
                        ].join(',') + ')';
                    }
                },
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            data: []
        }]
    };*/

    var app = new Vue({
        el: "#app",
        data: function () {
            return {
                flag:0,
                option:{
                    title: {
                        link: 'https://www.baidu.com/s?wd=' + encodeURIComponent('ECharts'),
                        x: 'center',
                        textStyle: {
                            fontSize: 23
                        }

                    },
                    backgroundColor: '#FFFFFF',
                    tooltip: {
                        show: true
                    },
                    toolbox: {
                        feature: {
                            /*saveAsImage: {
                                iconStyle: {
                                    normal: {
                                        color: '#FFFFFF'
                                    }
                                }
                            }*/
                        }
                    },
                    series: [{
                        name: '学校热点分析',
                        type: 'wordCloud',
                        //size: ['9%', '99%'],
                        sizeRange: [1, 100],
                        //textRotation: [0, 45, 90, -45],
                        rotationRange: [-45, 90],
                        //shape: 'circle',
                        textPadding: 0,
                        autoSize: {
                            enable: true,
                            minSize: 1
                        },
                        order:false,
                        textStyle: {
                            normal: {
                                color: function() {
                                    return 'rgb(' + [
                                        Math.round(Math.random() * 160),
                                        Math.round(Math.random() * 160),
                                        Math.round(Math.random() * 160)
                                    ].join(',') + ')';
                                }
                            },
                            emphasis: {
                                shadowBlur: 10,
                                shadowColor: '#333'
                            }
                        },
                        data: []
                    }]
                }
            }
        },
        watch:{
            /*flag:function (newVal,oldVal) {
                if (newVal == 1) {
                    if (myChart != null) {
                        console.log("jjjjj")
                        myChart.setOption(option);
                    }else{
                        console.log(newVal)
                        this.flag = 1;
                    }
                }
                this.flag = -1000;
            }*/
        },
        mounted: function () {
            this.loadSchoolActiveData();
        },
        methods: {
            loadSchoolActiveData:function () {
                var self = this;
                Api.get('/schoolActiveData',{},function (result) {
                    if (result.code == 0) {
                        var myChart = echarts.init(document.getElementById('main'));
                        self.option.series[0].data = result.data;
                        myChart.setOption(self.option);
                        window.onresize = function () {
                            myChart.resize(); //使第一个图表适应
                        }
                        //self.JsonList = result.data;
                        // 基于准备好的dom，初始化echarts实例
                        /*option.series[0].data = result.data;
                        self.flag = 1;*/
                        //myChart.setOption(option);
                        /*var datas = new Array();
                        for (var i=0; i<result.data.length; i++) {
                            datas.push({name:result.data[i].name,value:result.data[i].value})
                        }
                        var myChart = echarts.init(document.getElementById('main'));
                        option.series[0].data = datas;
                        //console.log("option.series[0].data:"+option.series[0].data)
                        for (var i=0; i<option.series[0].data.length; i++) {
                            console.log(option.series[0].data[i].name+":"+option.series[0].data.value)
                        }
                        myChart.setOption(option);*/
                    }
                });
            }
        }
    });

    /*window.onresize = function () {
        myChart.resize(); //使第一个图表适应
    }*/

</script>
</body>
</html>