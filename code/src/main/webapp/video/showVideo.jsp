<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    $(function(){
        initVideoTable();
    });
    function initVideoTable(){
        $('#videoTable').jqGrid({
            //整合bootstrap样式
            styleUI: 'Bootstrap',
            url: '${path}/video/findAll',
            datatype: 'json',
            mtype: 'get',
            colNames: ['标题','用户','描述','视频','封面','类别','状态','创建时间','播放次数','点赞次数'],
            colModel: [
                {
                    name: 'title',
                    editable: true// 开启当前列的可编辑模式， 前提是cellEdit必须为true
                },
                {
                    name: 'user.id',
                    editable: true,
                    edittype:'select',
                    editoptions: {dataUrl: '${path}/user/selectAllUser'},
                    formatter:function (value,options,row) {
                        return row.user.username;
                    }
                },
                {
                    name: 'description',
                    editable: true
                },
                {name : 'videoPath',editable:true,align : "center",edittype:"file",width : 400,align:"center",
                    formatter:function(cellvalue, options, rowObject){
                        return "<video id='media' src='"+cellvalue+"' controls width='400px' heigt='800px' poster='"+rowObject.coverPath+"'/>";
                    }
                },
                {name : 'coverPath',
                    formatter: function (value, options, row) {// value:当前对象 row：当前行
                        if (value != null) {
                            return "<img src= '" +row.coverPath + "' width='100px' />";
                        } else {
                            return value;
                        }
                    }
                },
                {
                    name: 'category.id',
                    editable: true,
                    edittype:'select',
                    editoptions: {dataUrl: '${path}/category/findAll'},
                    formatter:function (value,options,row) {
                        return row.category.cateName;
                    }
                },
                {
                    name: 'status',
                    formatter: function (value, options, row) {// value:当前对象 row：当前行
                        if (value != 1) {
                            return "<button class='btn btn-danger' style='width: 100%;height: 100%' onclick='updateStatus(\""+row.id+"\",\""+value+"\")'>下线</button>";
                        } else {
                            return "<button class='btn btn-success' style='width: 100%;height: 100%' onclick='updateStatus(\""+row.id+"\",\""+value+"\")'>上线</button>";
                        }
                    }
                },
                {
                    name: 'createTime'
                },
                {
                    name: 'playCount'
                },
                {
                    name: 'likeCount'
                }
            ],
            autowidth: true,
            cellEdit: false, //开启数据表格可编辑模式
            rownumbers: true,
            pager: '#pg',// 生成分页工具栏
            rowList: [2, 5, 10, 20, 50], // 指定每页展示信息条数的选项列表
            rowNum: 2, //  指定默认每页展示的条数，这个值必须来自rowList数组的一个元素
            viewrecords: true, // 显示总记录数
            page: 1, // 默认显示第几页
            editurl: '${path}/video/edit',
            //multiselect:true,
        }).navGrid('#pg',
            // 控制功能按钮是否显示，默认都为true
            {'add': true, 'edit': true, 'del': true, 'search': true, 'refresh': false},
            {
                closeAfterEdit:true,// 编辑提交后自动关闭
                afterSubmit:function(data) {  //提交之后触发
                    $.ajaxFileUpload({
                        fileElementId: "videoPath",    //需要上传的文件域的ID，即<input type="file" id="headImg" name="headImg" >的ID。
                        url: "${path}/video/uploadUserVideo", //后台方法的路径
                        type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                        //dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                        data:{"id":data.responseText},
                        success: function() {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                            //刷新表单
                            $("#videoTable").trigger("reloadGrid");
                        }
                    });
                    return "success";
                }
            }, // 配置编辑操作额外参数
            {
                //editData:{'id':''},// 指定添加额外传的参数，会覆盖本次请求同名的请求参数
                closeAfterAdd:true,
                afterSubmit:function(data) {  //提交之后触发
                    $.ajaxFileUpload({
                        fileElementId: "videoPath",    //需要上传的文件域的ID，即<input type="file" id="headImg" name="headImg" >的ID。
                        url: "${path}/video/uploadUserVideo", //后台方法的路径
                        type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                        //dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                        data:{"id":data.responseText},
                        success: function() {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                            //刷新表单
                            $("#videoTable").trigger("reloadGrid");
                        }
                    });
                    return "success";
                }
            }, // 配置添加操作额外参数
            {
                afterSubmit:function(data){
                    //向警告框中追加错误信息
                    $("#showMsg").html(data.responseJSON.message);
                    //展示警告框
                    $("#deleteMsg").show();

                    //自动关闭
                    setTimeout(function(){
                        $("#deleteMsg").hide();
                    },3000);
                    return "success";
                }
            } //执行删除之后的额外操作配置删除操作额外参数
        );
    }

    //修改状态
    function updateStatus(id,status){

        if(status==1){
            $.post("${path}/video/edit",{"id":id,"status":"0","oper":"edit"},function(data){
                //刷新表单
                $("#videoTable").trigger("reloadGrid");
            });
        }else{
            $.post("${path}/video/edit",{"id":id,"status":"1","oper":"edit"},function(data){
                //刷新表单
                $("#videoTable").trigger("reloadGrid");
            });
        }
    }

</script>


<%--创建一个面板--%>
<div class="panel panel-info" >
    <div class="panel-heading">
        <div class="jumbotron">
            <h3 align="center">视频信息</h3>
        </div>
    </div>

    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">视频管理</a></li>
        </ul>
    </div>
    <%--警告提示框--%>
    <div id="deleteMsg" class="alert alert-primary" style="height: 50px;width: 250px;display: none" align="center">
        <span id="showMsg"/>
    </div>
    <table class="table table-striped " id="videoTable">
        <div id="pg"></div>
    </table>

</div>
