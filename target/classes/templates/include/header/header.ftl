<div id="header">
<div class="sticky-header header-section ">
    <div class="header-left">
        <!--logo -->
        <div class="logo">
            <a href="index.html">
                <ul>
                    <li><img src="http://cdn.ican.com/public/images/logo.png" alt="" /></li>
                    <li><h1>Ican</h1></li>
                    <div class="clearfix"> </div>
                </ul>
            </a>
        </div>
        <!--//logo-->
    </div>
    <div class="header-center header-right-grid" style="60%;">
        <div class="profile_details_left">
            <h3>
                <marquee style="line-height: 55px;"><span v-html="slogan"></span></marquee>
            </h3>
            <#--<el-row>
                <el-col :span="2">
                    <el-menu-item index="1"><a href="/login?role=3">学校登录</a></el-menu-item>
                </el-col>
                <el-col :span="2">
                    <el-menu-item index="2"><a href="/login?role=4">学院登录</a></el-menu-item>
                </el-col>
                <el-col :span="2">
                    <el-menu-item index="3"><a href="/login?role=5">导师登录</a></el-menu-item>
                </el-col>
                <el-col :span="2">
                    <el-menu-item index="4"><a href="/login?role=6">学生登录</a></el-menu-item>
                </el-col>
                <el-col :span="2">
                    <el-menu-item index="5"><a href="https://www.ele.me">博客园</a></el-menu-item>
                </el-col>
                <el-col :span="3">
                </el-col>

            </el-row>-->
        </div>
        <div class="clearfix"> </div>
    </div>

    <!--//end-search-box-->
    <div class="header-right">
        <div style="line-height: 55px;text-align: center;">
            <el-dropdown>
              <span class="el-dropdown-link" style="cursor:pointer;">
                <h2>登录</h2>
              </span>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item><a href="/login?role=2">管理员</a></el-dropdown-item>
                    <el-dropdown-item><a href="/login?role=3">学校登录</a></el-dropdown-item>
                    <el-dropdown-item><a href="/login?role=4">学院登录</a></el-dropdown-item>
                    <el-dropdown-item><a href="/login?role=5">导师登录</a></el-dropdown-item>
                    <el-dropdown-item><a href="/login?role=6">学生登录</a></el-dropdown-item>
                    <el-dropdown-item>博客园</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
           <#-- <h2>登录</h2>-->
        </div>

        <div class="clearfix"> </div>
    </div>
    <div class="clearfix"> </div>
</div>

</div>
<script>
    var app = new Vue({
        el: "#header",
        data: function () {
            return {
                slogan:'全站最热门广告位招租！！！有意者电话联系18813960106！！！'
            }
        },
        mounted: function () {
            var R = Math.floor(Math.random() * 255);
            var G = Math.floor(Math.random() * 255);
            var B = Math.floor(Math.random() * 255);
            this.slogan = "<font color='rgb("+R+","+G+","+B+")'>"+this.slogan+"</font>";
            console.log(this.slogan);
        }
    });
</script>