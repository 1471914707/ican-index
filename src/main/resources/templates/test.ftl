<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>测试</title>
    <#include 'include/cssjs_common.ftl'>
</head>

<body>
<el-container>
    <el-header><#include '/include/header/header-index.ftl'></el-header>
    <el-container>
        <el-aside width="200px">
            <#include '/include/common/menu_left.ftl'>
        </el-aside>
        <el-main>
            <section>
                <h1>Ican-inde x主页</h1>
            </section>
        </el-main>
    </el-container>
</el-container>

</body>
</html>