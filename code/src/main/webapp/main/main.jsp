<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>应学后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>
    <script type="text/javascript">
        var onResize = function(){
            $("body").css("margin-top",$(".navbar").height());
        };
        $(window).resize(onResize);
        $(function(){
            onResize();
        });
    </script>
</head>
<body>


    <!--顶部导航-->
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#col">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <span class="navbar-brand">应学app管理系统 </span>
            </div>
            <div class="collapse navbar-collapse" id="col">
                <ul class="nav navbar-nav">
                    <li><a href="">首页</a></li>
                    <li><a href="">关于我们</a></li>
                    <li><a href="">联系我们</a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li> <a href="${path}/admin/logOut">
                        <span class="glyphicon glyphicon-log-in"></span>退出
                    </a></li>
                </ul>
                <p class="navbar-text navbar-right">欢迎：${sessionScope.admin.username}</p>
            </div>
        </div>
    </div>
    <!--栅格系统-->
        <!--左边手风琴部分-->
    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-3">
                <div class="panel-group">
                    <div class="panel panel-info" >
                        <div class="panel-heading" style="text-align: center">
                            <a href="#user"  data-toggle="collapse" >用户管理</a>
                        </div>
                        <div class="collapse in" id="user">
                            <div class="panel-body" style="text-align: center" >
                                <button  class="btn btn-danger" style="margin-top: 10px; width:90%;">
                                    <a href="javascript:$('#mainId').load('${path}/user/showUser.jsp')">用户信息</a>
                                </button>
                                <br/>
                                <button type="button" class="btn btn-danger" style="margin-top: 10px; width:90%;">用户统计</button><br/>
                                <button type="button" class="btn btn-danger" style="margin-top: 10px; width:90%;">用户分布</button>
                            </div>
                        </div>

                    </div>
                    <div class="panel panel-info" >
                        <div class="panel-heading" style="text-align: center">
                            <a href="#category"  data-toggle="collapse" >分类管理</a>
                        </div>
                        <div class="collapse" id="category">
                            <div class="panel-body" style="text-align: center" >
                                <button  class="btn btn-danger" style="margin-top: 10px; width:90%;">
                                    <a href="javascript:$('#mainId').load('${path}/category/showCategory.jsp')">分类信息</a>
                                </button>
                                <br/>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info" >
                        <div class="panel-heading" style="text-align: center">
                            <a href="#video"  data-toggle="collapse" >视频管理</a>
                        </div>
                        <div class="collapse" id="video">
                            <div class="panel-body" style="text-align: center" >
                                <button  class="btn btn-danger" style="margin-top: 10px; width:90%;">
                                    <a href="javascript:$('#mainId').load('${path}/video/showVideo.jsp')">视频信息</a>
                                </button>
                                <br/>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-info" >
                        <div class="panel-heading">
                            日记管理
                        </div>
                        <div class="panel-body">

                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-9" id="mainId">
                <div class="panel panel-success" id="welcomeInfo">
                    <!--巨幕开始-->
                    <div class="panel-heading">
                        <div class="jumbotron">
                            <h3 align="center">欢迎来到应学app管理系统</h3>
                        </div>
                    </div>
                    <div class="panel-body">
                        <!--右边轮播图部分-->
                        <div class="carousel" data-ride="carousel" data-interval="10000" id="cc">
                            <div class="carousel-inner">
                                <div class="item active">
                                    <video src="https://nasa-2021.oss-cn-hangzhou.aliyuncs.com/video/云雾.mp4?OSSAccessKeyId=LTAI4G81me9myzWo6nn57omC&Expires=1612326435&Signature=KE10Hk0WIvy%2BDYuj%2B%2F86N463oDo%3D" width="100%" controls/>
                                </div>
                                <div class="item ">
                                    <video src="https://nasa-2021.oss-cn-hangzhou.aliyuncs.com/video/2020宣传视频.mp4?OSSAccessKeyId=LTAI4G81me9myzWo6nn57omC&Expires=1612326127&Signature=RDtnBsJ4MdAwRsM4LRNtdkAUzLY%3D" controls>
                                    </video>
                                </div>
                                <div class="item ">
                                    <img src="../bootstrap/img/pic3.jpg" width="100%">
                                </div>
                                <div class="item ">
                                    <img src="../bootstrap/img/pic4.jpg" width="100%">
                                </div>
                            </div>
                            <!--前进后退控制按钮-->
                            <a href="#cc" class="carousel-control left" data-slide="prev">
                                <span class="glyphicon glyphicon-chevron-left"></span>
                            </a>
                            <a href="#cc" class="carousel-control right" data-slide="next">
                                <span class="glyphicon glyphicon-chevron-right"></span>
                            </a>
                            <!--图片指示器按钮-->
                            <ul class="carousel-indicators">
                                <li data-target="#cc" data-slide-to="0"  class="active"></li>
                                <li data-target="#cc" data-slide-to="1"></li>
                                <li data-target="#cc" data-slide-to="2"></li>
                                <li data-target="#cc" data-slide-to="3"></li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--页脚-->
    <div class="panel panel-footer" align="center">
        <span>@百知教育 zhangcn@zparkhr.com</span>
    </div>
    <!--栅格系统-->

</body>
</html>
