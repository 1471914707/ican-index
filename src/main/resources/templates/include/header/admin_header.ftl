<div class="sticky-header header-section" style="height: 60px;">
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
    <div style="float: right;line-height: 68px;padding-right: 50px;width: 25%" >
        管理员：${admin.name}
        <el-dropdown style="margin-left: 20px;font-size: 16px;">
                <span class="el-dropdown-link" style="color: black">
                菜单<i class="el-icon-arrow-down el-icon--right"></i>
                </span>
            <el-dropdown-menu slot="dropdown">
                <el-dropdown-item><a href="/admin/school/list">学校申请</a></el-dropdown-item>
                <el-dropdown-item><a href="/admin/schoolAppeal">学校申议</a></el-dropdown-item>
            </el-dropdown-menu>
        </el-dropdown>
    </div>
    <div class="clearfix"> </div>
</div>