<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>左边菜单</title>
    <link rel="stylesheet" type="text/css" href="http://cdn.ican.com/public/css/header/menu_left.css" />
</head>
<body>

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
</script>
</body>
</html>