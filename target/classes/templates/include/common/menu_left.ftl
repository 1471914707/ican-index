<div id="header" style="margin-left: 10px;display: inline;float: left;width: 200px">

        <el-menu
                default-active="2"
                class="el-menu-vertical-demo"
                @open="handleOpen"
                @close="handleClose">
            <el-submenu index="1">
                <template slot="title">
                    <i class="el-icon-location"></i>
                    <span>导航一</span>
                </template>
                <el-menu-item-group>
                    <template slot="title">分组一</template>
                    <el-menu-item index="1-1">选项1</el-menu-item>
                    <el-menu-item index="1-2">选项2</el-menu-item>
                </el-menu-item-group>
                <el-menu-item-group title="分组2">
                    <el-menu-item index="1-3">选项3</el-menu-item>
                </el-menu-item-group>
                <el-submenu index="1-4">
                    <template slot="title">选项4</template>
                    <el-menu-item index="1-4-1">选项1</el-menu-item>
                </el-submenu>
            </el-submenu>
            <el-menu-item index="2">
                <i class="el-icon-menu"></i>
                <span slot="title">导航二</span>
            </el-menu-item>
            <el-menu-item index="3" disabled>
                <i class="el-icon-document"></i>
                <span slot="title">导航三</span>
            </el-menu-item>
            <el-menu-item index="4">
                <i class="el-icon-setting"></i>
                <span slot="title">导航四</span>
            </el-menu-item>
        </el-menu>

<script type="text/javascript">

    var app = new Vue({
        el: "#header",
        data: function () {
            return {
            }
        },
        methods: {
            handleOpen:function(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose:function(key, keyPath) {
                console.log(key, keyPath);
            }
        }
    });

</script>
</div>


<#--
<!DOCTYPE html>

<div class="wmenu">
    <dl>
        <dt class="user"><a href="#">用户管理</a></dt>
        <dd><a href="http://xtian.me">基本资料</a></dd>
        <dd><a href="http://xtian.me">邮箱管理</a></dd>
        <dd><a href="http://xtian.me">密码管理</a></dd>
    </dl>
    <dl>
        <dt class="edit"><a href="#">编辑管理</a></dt>
        <dd><a href="http://xtian.me">发布广告管理</a></dd>
    </dl>
</div>
<script type="text/javascript">
    $('.wmenu dl dt').click(function(){
        $(this).toggleClass('icotop');
        $(this).siblings('dd').toggleClass('hidden');
    });
</script>-->
