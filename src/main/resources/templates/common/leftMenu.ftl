<div class="layui-side layui-side-menu">
    <div class="layui-side-scroll">
        <div class="layui-logo" >

                    <span style="">
                       <strong>ZQMaster</strong>
                        <!--<img  src="/static/zzpic/jiujiufu-left-max.png">-->
                    </span>
        </div>

        <ul class="layui-nav layui-nav-tree" lay-shrink="all" id="LAY-system-side-menu" lay-filter="layadmin-system-side-menu">
            <#if menuList ??>
                <#list menuList as item>
                    <#if item.lever=='1'>
                        <li data-name="${item.menu_name}" class="layui-nav-item">
                            <a href="javascript:;" lay-tips="${item.menu_name}" lay-direction="2">
                                <i class="layui-icon ${(item.css)!''}"></i>
                                <cite>${item.menu_name}</cite>
                            </a>

                        <#elseif item.lever=='2'>
                                <dl class="layui-nav-child">
                                    <dd >
                                        <a lay-href="${item.href}">${item.menu_name}<i class="layui-icon ${(item.css)!''}"></i></a>
                                    </dd>
                                </dl>
                    </#if>
                </#list>
            </#if>

                        </li>
        </ul>
    </div>
</div>