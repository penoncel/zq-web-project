<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>锁屏</title>
    <meta name="keywords" content="PoweredByJ2eeFast"/><meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <link rel="stylesheet" href="/static/lock/bootstrap.min.css">
    <link rel="stylesheet" href="/static/lock/font-awesome.min.css">
    <link rel="stylesheet" href="/static/lock/ionicons.css">
    <link rel="stylesheet" href="/static/lock/AdminLTE.css">
    <link rel="stylesheet" href="/static/lock/jquery.toast.css">
    <link rel="stylesheet" href="/static/lock/jquery.flipcountdown.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!-- Google Font -->
    <style>
        * {
            margin: 0;
            padding: 0;
        }
        body {
            overflow: hidden;
        }
        #time {
            width: 100%;
            color: #fff;
            font-size: 60px;
            display: inline-block;
            text-align: center;
            font-family: 'Open Sans', sans-serif;
            font-weight: 300;
        }
    </style>
</head>
<script>
    var baseURL = "/" ;
    function imgUserError(){
        var img=event.srcElement;
        img.src=baseURL+"static/img/user2-160x160.jpg";
        img.onerror=null;
    }
</script>
<body class="hold-transition lockscreen">
<!-- Automatic element centering -->
<div class="lockscreen-wrapper">
    <div id="time"></div>
    <div class="lockscreen-logo">
        <#--        <a href="../../index2.html"><b>Admin</b>LTE</a>-->
        <a href="#"><small>V</small></a>
    </div>
    <!-- User name -->
    <div class="lockscreen-name"></div>

    <!-- START LOCK SCREEN ITEM -->
    <div class="lockscreen-item">
        <!-- lockscreen image -->
        <div class="lockscreen-image">
            <img src="/statics/img/user2-160x160.jpg" onerror="imgUserError();"  alt="User Image">
        </div>
        <!-- /.lockscreen-image -->

        <!-- lockscreen credentials (contains the form) -->
        <form class="lockscreen-credentials" id="form-lock" method="post" action="#" onsubmit="return false;">
            <input type="hidden" name="username" value="<@shiro.principal property="username"/>"/>
            <div class="input-group">
                <input type="password" name="password"  class="form-control" placeholder="密码">
                <div class="input-group-btn">
                    <button type="button" class="btn" onclick="submitLock()" onkeydown="if(event.keyCode==13){submitLock();}"><i class="fa fa-arrow-right text-muted"></i></button>
                </div>
            </div>
        </form>
        <!-- /.lockscreen credentials -->

    </div>
    <!-- /.lockscreen-item -->
    <div class="help-block text-center" style="font-size: 12px; margin-top: 50px;">
        系统锁屏,请输入密码登陆!
    </div>
    <div class="text-center">
        <a href="/logout" style="font-size: 13px">退出重新登陆</a>
    </div>
    <div class="lockscreen-footer text-center">
        Copyright &copy; 2018- <a href="http://www.lixinfintech.com"></a><br>

    </div>
</div>
<!-- /.center -->

<#--jQuery 3 -->
<script src="/static/libs/jquery.min.js"></script>
<#-- Bootstrap 3.3.7 -->
<script src="/static/libs/bootstrap.min.js"></script>
<script src="/static/plugins/three/three.min.js"></script>
<script src="/static/plugins/three/hk.js"></script>
<#-- jQuery toast 提示框  -->
<script src="/static/libs/jquery.toast.min.js"></script>
<#-- 全局遮罩-->
<script type="text/javascript" src="/static/plugins/blockUI/jquery.blockUI.min.js"></script>
<script type="text/javascript" src="/static/plugins/flipcountdown/jquery.flipcountdown.js"></script>
<script type="text/javascript" src="/static/plugins/fullscreen/jquery.fullscreen.js"></script>
<script type="text/javascript" src="/static/libs/SM4.min.js"></script>
</body>
<script type="text/javascript">
    (function ($) {

        /*扩展Date*/
        if (!$.isFunction(Date.prototype.format)) {
            Date.prototype.format = function (format) {
                var o = {
                    "M+": this.getMonth() + 1,
                    "d+": this.getDate(),
                    "h+": this.getHours() % 12 === 0 ? 12 : this.getHours() % 12,
                    "H+": this.getHours(),
                    "m+": this.getMinutes(),
                    "s+": this.getSeconds(),
                    "q+": Math.floor((this.getMonth() + 3) / 3),
                    "S": this.getMilliseconds()
                };
                var week = {
                    0: "日",
                    1: "一",
                    2: "二",
                    3: "三",
                    4: "四",
                    5: "五",
                    6: "六"
                };

                if (/(y+)/.test(format))
                    format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));

                if (/(E+)/.test(format))
                    format = format.replace(RegExp.$1, (RegExp.$1.length > 1 ? RegExp.$1.length > 2 ? "星期" : "周" : "") + week[this.getDay()]);

                for (var k in o){
                    if (new RegExp("(" + k + ")").test(format))
                        format = format.replace(RegExp.$1, RegExp.$1.length === 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
                }

                return format;
            };
        }

    })(jQuery);

    var can,w,h,words,index;
    $(function () {


        $('#time').text((new Date()).format('HH:mm:ss'));

        setInterval(function () {
            $('#time').text((new Date()).format('HH:mm:ss'));
        }, 500);
        /*$('#time').flipcountdown({speedFlip:50});*/

        if(Math.floor(Math.random() * 10) + 1 > 5){
            $(".lockscreen-footer a").css("color","#000");
            $(".lockscreen-footer").css("color","#000");
            init();
            animate();
        }else{

            $(".lockscreen-logo a").css("color","#FAFAFA");
            $(".lockscreen-name").css("color","#FAFAFA");
            $(".lockscreen-footer a").css("color","#FAFAFA");
            $(".lockscreen-footer").css("color","#FAFAFA");
            $(".help-block").css("color","#FAFAFA");
            container = document.createElement( 'canvas' );
            container.id = 'canvas';
            container.style.position = 'absolute';
            container.style.zIndex = '0';
            container.style.top = '0px';
            $(".lockscreen-wrapper").before(container);
            var canvas=document.getElementById("canvas");
            can=canvas.getContext("2d");
            /*var s=window.screen;*/
            w=canvas.width=window.innerWidth;
            h=canvas.height=window.innerHeight;

            can.fillStyle=color2();

            words = Array(256).join("1").split("");

            index = setInterval(draw,50);

            window.addEventListener( 'resize', onWindowResize0, false );
        }

    });

    function onWindowResize0() {
        clearInterval(index);
        var canvas=document.getElementById("canvas");
        w=canvas.width=window.innerWidth;
        h=canvas.height=window.innerHeight;
        can.fillStyle=color2();
        words = Array(256).join("1").split("");
        index = setInterval(draw,50);
    }

    function draw(){
        can.fillStyle='rgba(0,0,0,0.05)';
        can.fillRect(0,0,w,h);
        can.fillStyle=color2();
        words.map(function(y,n){
            text=String.fromCharCode(Math.ceil(65+Math.random()*57));
            x = n*10;
            can.fillText(text,x,y);
            words[n]=( y > 758 + Math.random()*484 ? 0:y+10 );
        });
    }

    function color1(){
        var colors=[0,1,2,3,4,5,6,7,8,9,'a','b','c','d','e','f'];
        var color="";
        for( var i=0; i<6; i++){
            var n=Math.ceil(Math.random()*15);
            color += "" + colors[n];
        }
        return '#'+color;
    }

    function color2(){
        var color = Math.ceil(Math.random()*16777215).toString(16);
        while(color.length<6){
            color = '0'+color;
        }
        return '#'+color;
    }
    function color3(){
        return "#" + (function(color){
            return new Array(7-color.length).join("0")+color;
        })((Math.random()*0x1000000 << 0).toString(16))
    }



    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            submitLock();
        }
    });



    function trim(value) {
        if (value == null) {
            return "";
        }
        return value.toString().replace(/(^\s*)|(\s*$)|\r|\n/g, "");
    };


    var container;
    var camera, scene, projector, renderer;

    var PI2 = Math.PI * 2;

    var programFill = function ( context ) {

        context.beginPath();
        context.arc( 0, 0, 1, 0, PI2, true );
        context.closePath();
        context.fill();

    };

    var programStroke = function ( context ) {

        context.lineWidth = 0.05;
        context.beginPath();
        context.arc( 0, 0, 1, 0, PI2, true );
        context.closePath();
        context.stroke();

    };

    var mouse = { x: 0, y: 0 }, INTERSECTED;



    function init() {

        container = document.createElement( 'div' );
        container.id = 'bgc';
        container.style.position = 'absolute';
        container.style.zIndex = '0';
        container.style.top = '0px';

        $(".lockscreen-wrapper").before(container);


        camera = new THREE.PerspectiveCamera( 70, window.innerWidth / window.innerHeight, 1, 10000 );
        camera.position.set( 0, 300, 500 );

        scene = new THREE.Scene();

        for ( var i = 0; i < 100; i ++ ) {

            var particle = new THREE.Particle( new THREE.ParticleCanvasMaterial( { color: Math.random() * 0x808080 + 0x808080, program: programStroke } ) );
            particle.position.x = Math.random() * 800 - 400;
            particle.position.y = Math.random() * 800 - 400;
            particle.position.z = Math.random() * 800 - 400;
            particle.scale.x = particle.scale.y = Math.random() * 10 + 10;
            scene.add( particle );

        }

        projector = new THREE.Projector();

        renderer = new THREE.CanvasRenderer();
        renderer.setSize( window.innerWidth, window.innerHeight -10);
        container.appendChild( renderer.domElement );
        document.addEventListener( 'mousemove', onDocumentMouseMove, false );
        window.addEventListener( 'resize', onWindowResize, false );

    };

    function onWindowResize() {

        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();

        renderer.setSize( window.innerWidth, window.innerHeight-10 );

    };

    function onDocumentMouseMove( event ) {

        event.preventDefault();

        mouse.x = ( event.clientX / window.innerWidth ) * 2 - 1;
        mouse.y = - ( event.clientY / window.innerHeight ) * 2 + 1;

    };


    function animate() {

        requestAnimationFrame( animate );

        render();

    };

    var radius = 600;
    var theta = 0;

    function render() {

        /*rotate camera*/

        theta += 0.2;

        camera.position.x = radius * Math.sin( theta * Math.PI / 360 );
        camera.position.y = radius * Math.sin( theta * Math.PI / 360 );
        camera.position.z = radius * Math.cos( theta * Math.PI / 360 );
        camera.lookAt( scene.position );

        /*find intersections*/

        camera.updateMatrixWorld();

        var vector = new THREE.Vector3( mouse.x, mouse.y, 0.5 );
        projector.unprojectVector( vector, camera );

        var ray = new THREE.Ray( camera.position, vector.subSelf( camera.position ).normalize() );

        var intersects = ray.intersectObjects( scene.children );

        if ( intersects.length > 0 ) {

            if ( INTERSECTED != intersects[ 0 ].object ) {

                if ( INTERSECTED ) INTERSECTED.material.program = programStroke;

                INTERSECTED = intersects[ 0 ].object;
                INTERSECTED.material.program = programFill;

            }

        } else {

            if ( INTERSECTED ) INTERSECTED.material.program = programStroke;

            INTERSECTED = null;

        }

        renderer.render( scene, camera );

    }

</script>
</html>