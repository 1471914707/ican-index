<!DOCTYPE HTML>
<html>
<head>
    <title>对话信息 | Ican毕业设计平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="keywords" content="毕业设计平台" />
    <#include '/include/cssjs_common_new.ftl'>
    <style>
        .dialog-header{
            background: #409EFF;
            display: flex;
        }
        .dialog-header img{
            width: 50px;height: 50px;border-radius: 50px;
            margin-top:5px;margin-left: 10px;
        }
        body {
            margin: auto;
            height:600px;
            width:400px;
        }
        .dialog-add{
            height: 60px;
            width: 400px;
            background-color: #ddd;
            position: fixed;
            bottom: 0;
        }
        .dialog-body{
            overflow:scroll; width:400px; height:480px;
            overflow-x: hidden;
        }
        .dialog-content{
            border-radius:7px;
            overflow: auto;
            padding: 5px;
        }
        .dialog-content-div-left{
            margin-top: 20px;margin-bottom: 20px;
            margin-right: 40px;margin-left: 10px;
        }
        .dialog-content-div-right{
            margin-top: 15px;margin-bottom: 15px;
            margin-left: 40px;margin-right: 10px;
        }
    </style>
</head>
<body style="background: #f4f7f9;">
<div id="app">
    <div class="dialog-header">
        <el-row style="width: 400px">
            <el-col :span="5">
                <img :src="toHeadshot">
            </el-col>
            <el-col :span="19" style="line-height: 60px">
                {{toName.length>10?toName.substring(0,10)+'...':toName}}&nbsp;&nbsp;&nbsp;
                <span style="color: gray">(共{{total>1000?'1000+':total}}条信息)</span>
            </el-col>
        </el-row>
    </div>
    <div class="dialog-body" id="testDiv">
        <template v-for="(item, index) in list">
            <div v-if="toId==item.fromId" class="dialog-content-div-left">
                <#--对方的信息-->
                <div style="background-color:#ffffff; padding: 8px 8px 8px 8px;" class="dialog-content">
                    {{item.content}}</div>
                    <span style="color: #999;font-size: 10px">{{getDateTime(item.gmtCreate)}}--{{getStatus(item.hasRead)}}</span>
            </div>
            <div v-if="toId!=item.fromId" class="dialog-content-div-right">
                <#--本方的信息-->
                <div style="background-color:lawngreen; padding: 8px 8px 8px 8px;" class="dialog-content">
                    {{item.content}}</div><#--<div class="triangle"></div>-->
            </div>
        </template>
        <#--<div name="msg_end"></div>-->
    </div>
    <div class="dialog-add">
        <el-row style="width: 400px">
            <el-col :span="19" style="line-height: 60px;margin-left: 10px">
                <el-input v-model="content" placeholder="请输入内容"></el-input>
            </el-col>
            <el-col :span="4" style="line-height: 60px;margin-left: 5px;">
                <el-button size="small" type="primary" @Click="addMessage()">发送</el-button>
            </el-col>
        </el-row>
    </div>
</div>
<script type="text/javascript">
    <#if toId??>
    var toId = ${toId}
    <#else>
    var toId = 0;
    </#if>

    var app = new Vue({
        el: "#app",
        data: function () {
            return {
                total:0,
                list:[],
                fromName:'',
                fromHeadshot:'',
                toName:'',
                toHeadshot:'',
                content:'',
                page:1,
                size:20,
                toId:toId,
                scroll:''
            }
        },
        mounted: function () {
            this.loadMessageList();
            this.scroll = document.getElementById('testDiv');
            this.scroll.onscroll = this.divScroll;
            /*window.location.hash = "#msg_end";*/
        },
        methods: {
            loadMessageList:function () {
                var self = this;
                var page = page || this.page || 1;
                var size = size || this.size || 20;
                Api.get("/message/list",{toId:toId,page:page,size:size},function (result) {
                    if (result.code == 0) {
                        if (result.data.list && result.data.list.length == 0 && page > 1) {
                            self.$message({showClose: true, message: '没有更多信息了', type: 'success'});
                            return true;
                        }
                        if (page == 1) {
                            self.list = result.data.list.reverse();
                        } else {
                            var newList = result.data.list.reverse();
                            for (var i=0; i<self.list.length; i++) {
                                newList.push(self.list[i]);
                            }
                            self.list = newList;
                        }
                        self.fromName = result.data.fromName;
                        self.fromHeadshot = result.data.fromHeadshot;
                        self.toName = result.data.toName;
                        self.toHeadshot = result.data.toHeadshot;
                        self.total = result.data.total;
                    } else {
                        self.$message({showClose: true, message: '获取会话信息异常', type: 'error'});
                    }
                });
            },
            addMessage:function () {
                var self = this;
                Api.post("/message/add",{toId:toId,content:self.content},function (result) {
                    if (result.code == 0) {
                        self.page = 1;
                        self.loadMessageList();
                        self.content = '';
                    } else{
                        self.$message({showClose: true, message: '发送信息失败', type: 'error'});
                    }
                })
            },
            divScroll:function () {
                var wholeHeight = this.scroll.scrollHeight;
                var scrollTop = this.scroll.scrollTop;
                var divHeight = this.scroll.clientHeight;
                if(scrollTop + divHeight >= wholeHeight){
                    console.log('滚动到底部了！');
                    this.page = 1;
                    this.loadMessageList();
                    //这里写动态加载的逻辑，比如Ajax请求后端返回下一个页面的内容
                }
                if(scrollTop == 0){
                    console.log('滚动到头部了！');
                    this.page += 1;
                    this.loadMessageList();
                }
            },
            getDateTime:function (date) {
                var minute = 60 * 1000;// 1分钟
                var hour = 60 * minute;// 1小时
                var day= 24 * hour;// 1天
                var month = 31 * day;// 月
                if (date == null) {
                    return null;
                }
                var diff = new Date().getTime() - (new Date(date)).getTime();
                var r = 0;
                if (diff > day) {
                    var d = (new Date(date));

                    return d.Format("yyyy-MM-dd HH:mm:ss");
                }
                if (diff > hour) {
                    r = Math.round(diff / hour);
                    return r + "个小时前";
                }
                if (diff > minute) {
                    r = Math.round(diff / minute);
                    return r + "分钟前";
                }
                return "刚刚";
            },
            getStatus:function (read) {
                switch (read){
                    case 1:
                        return '未读';
                    case 2:
                        return '已读';
                    default:
                        return '未知';
                }
            }
        }
    });

</script>
</body>
</html>