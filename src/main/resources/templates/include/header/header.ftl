<div id="header">
<div class="sticky-header header-section" style="line-height: 68px;">
        <div class="profile_details_left" style="margin-left: 25%;">
            <h1 style="display: inline-block;margin-right: 20%;"> 毕业设计（论文）平台</h1>
            <div style="display: inline-block;font-size: 20px">
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
                <el-dropdown>
              <span class="el-dropdown-link" style="cursor:pointer;">
                <h2>注册</h2>
              </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item><a href="/register/school">学校注册</a></el-dropdown-item>
                        <el-dropdown-item><a href="/login?role=4">学院注册</a></el-dropdown-item>
                        <el-dropdown-item><a href="/login?role=5">导师注册</a></el-dropdown-item>
                        <el-dropdown-item><a href="/login?role=6">学生注册</a></el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
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