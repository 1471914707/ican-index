<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <script src="https://cdn.bootcss.com/echarts/4.0.2/echarts.min.js"></script>
    <script src="http://data-visual.cn/datav/src/js/echarts/extension/echarts-wordcloud.min.js"></script>
    <#include 'include/cssjs_common.ftl'>
    <#include 'include/header/header-index.ftl'>
    <style>
        .main{margin-left:auto;margin-right:auto;width: 600px;height:400px;max-width:800px;max-height:600px;margin-top: 20px;}
        @media screen and (max-width: 768px) {
            .main{margin-left:auto;margin-right:auto;width: 300px;height:400px;margin-top: 20px;}
        }
    </style>
</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" class="main"></div>
<script type="text/javascript">

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
    var option = {
        title: {
            link: 'https://www.baidu.com/s?wd=' + encodeURIComponent('ECharts'),
            x: 'center',
            textStyle: {
                fontSize: 23
            }

        },
        backgroundColor: '#FFFFFF',
        tooltip: {
            show: false
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
            sizeRange: [6, 66],
            //textRotation: [0, 45, 90, -45],
            rotationRange: [-45, 90],
            //shape: 'circle',
            textPadding: 0,
            autoSize: {
                enable: true,
                minSize: 6
            },
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
            data: [{
                name: "Jayfee",
                value: 666
            }, {
                name: "Nancy",
                value: 520
            }]
        }]
    };

    var app = new Vue({
        el: "#app",
        data: function () {
            return {
                JosnList : []
            }
        },
        mounted: function () {
            this.JosnList.push({
                name: "广东技术师范学院",
                value: 666
            },{
                name: "广东技术师范学院",
                value: 666
            },{
                name: "广东技术师范学院",
                value: 666
            },{
                name: "广东技术师范学院",
                value: 666
            },{
                name: "广东技术师范学院",
                value: 666
            },{
                name: "广东技术师范学院",
                value: 666
            },{
                name: "广东技术师范学院",
                value: 666
            })
            option.series[0].data = this.JosnList;
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
            window.onresize = function () {
                myChart.resize(); //使第一个图表适应
            }
        },
        methods: {

        }
    });



</script>
</body>
</html>