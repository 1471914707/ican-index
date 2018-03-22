<div class="sidebar" role="navigation">
    <div class="navbar-collapse">
        <nav class="cbp-spmenu cbp-spmenu-vertical cbp-spmenu-right dev-page-sidebar mCustomScrollbar _mCS_1 mCS-autoHide mCS_no_scrollbar" id="cbp-spmenu-s1">
            <div>
                <el-collapse>
                    <a href="www.baidu.com">
                        <el-collapse-item title="一致性 Consistency" name="1"></el-collapse-item>
                    </a>
                </el-collapse>
            </div>
        </nav>
    </div>
</div>
<div class="sticky-header header-section ">
    <div class="header-left">
        <div class="logo">
            <a href="index.html">
                <ul>
                    <li><img src="http://cdn.ican.com/public/images/logo.png" alt="" /></li>
                    <li><h1>Ican</h1></li>
                    <div class="clearfix"></div>
                </ul>
            </a>
        </div>
        <div class="header-right header-right-grid">
            <div class="profile_details_left">
                <div class="clearfix"></div>
            </div>
        </div>

        <div class="clearfix"> </div>
    </div>
    <div class="header-right" style="float: right;">
        <div class="profile_details"></div>
        <button id="showLeftPush"><img  src="http://cdn.ican.com/public/images/bars.png" style="max-width:18.003px;max-height:23.333px;"></button>
        <div class="clearfix"> </div>
    </div>
    <div class="clearfix"> </div>
</div>
<script>
    var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
            showLeftPush = document.getElementById( 'showLeftPush' ),
            body = document.body;

    showLeftPush.onclick = function() {
        classie.toggle( this, 'active' );
        classie.toggle( body, 'cbp-spmenu-push-toright' );
        classie.toggle( menuLeft, 'cbp-spmenu-open' );
        disableOther( 'showLeftPush' );
    };

    function disableOther( button ) {
        if( button !== 'showLeftPush' ) {
            classie.toggle( showLeftPush, 'disabled' );
        }
    }
</script>
