<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport"
              content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>Document</title>
        <script src="${path}/js/echarts.js"></script>
        <script src="${path}/bootstrap/js/jquery.min.js"></script>
        <script type="text/javascript">
            $(function(){
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));

                $.get("${path}/json/echarts.json",function(data){
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '用户月注册数量统计',  //标题
                            show:true,  //是否展示标题
                            link:"${path}/main/main.jsp",  //标题链接
                            target:"self",//设置打开窗口方式
                            subtext:"纯属虚构",
                        },
                        tooltip: {}, //鼠标提示
                        legend: {
                            data:['小男孩','小姑娘']  //选项卡
                        },
                        xAxis: {
                            data: data.month  //横坐标
                        },
                        yAxis: {},  //纵坐标  自适应
                        series: [{
                            name: '小男孩',  //指定选项卡
                            type: 'bar', //设置图标类型  bar:主状态  line:折线图
                            data: data.boys  //数据
                        },{
                            name: '小姑娘',  //指定选项卡
                            type: 'bar', //设置图标类型  bar:主状态  line:折线图
                            data: data.girls  //数据
                        }]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                },"json");
            });
        </script>
    </head>
    <body>
        <div align="center">
            <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
            <div id="main" style="width: 600px;height:400px;" ></div>
        </div>
    </body>
</html>