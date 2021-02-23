<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    $(function(){
        initUserTable();
    });
    function initUserTable(){
        $('#userTable').jqGrid({
            //整合bootstrap样式
            styleUI: 'Bootstrap',
            url: '${path}/user/findAll',
            datatype: 'json',
            mtype: 'get',
            colNames: ['用户名','手机号','微信','头像','签名','状态','创建时间'],
            colModel: [
                {
                    name: 'username',
                    editable: true// 开启当前列的可编辑模式， 前提是cellEdit必须为true
                },
                {
                    name: 'phone',
                    editable: true
                },
                {
                    name: 'weChat',
                    editable: false
                },
                {
                    name: 'headImg',
                    edittype: "file",
                    editable: true,
                    width: 100,
                    //value:列值,options:操作,row:行对象数据(所有列的数据)
                    formatter: function (value, options, row) {// value:当前对象 row：当前行
                        if (value != null) {
                            <%--return "<img src= ${path}/bootstrap/img/" +value + " width='100px' />";--%>
                            return "<img src= https://nasa-2021.oss-cn-hangzhou.aliyuncs.com/photo/" +row.headImg + " width='100px' />";
                        } else {
                            return value;
                        }
                    }
                },
                {
                    name: 'sign',
                    editable: true
                },
                {
                    name: 'status',
                    formatter: function (value, options, row) {// value:当前对象 row：当前行
                        if (value != 1) {
                            return "<button class='btn btn-danger' style='width: 100%;height: 100%' onclick='updateStatus(\""+row.id+"\",\""+value+"\")'>冻结</button>";
                        } else {
                            return "<button class='btn btn-success' style='width: 100%;height: 100%' onclick='updateStatus(\""+row.id+"\",\""+value+"\")'>正常</button>";
                        }
                    }
                },
                {
                    name: 'createTime'
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
            editurl: '${path}/user/edit',
            //multiselect:true,
        }).navGrid('#pg',
            // 控制功能按钮是否显示，默认都为true
            {'add': true, 'edit': true, 'del': true, 'search': true, 'refresh': false},
            {
                closeAfterEdit:true,// 编辑提交后自动关闭
                afterSubmit:function(data) {  //提交之后触发
                    $.ajaxFileUpload({
                        fileElementId: "headImg",    //需要上传的文件域的ID，即<input type="file" id="headImg" name="headImg" >的ID。
                        url: "${path}/user/uploadUserHeadImg", //后台方法的路径
                        type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                        //dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                        data:{"id":data.responseText},
                        success: function() {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                            //刷新表单
                            $("#userTable").trigger("reloadGrid");
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
                        fileElementId: "headImg",    //需要上传的文件域的ID，即<input type="file" id="headImg" name="headImg" >的ID。
                        url: "${path}/user/uploadUserHeadImg", //后台方法的路径
                        type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                        //dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                        data:{"id":data.responseText},
                        success: function() {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                            //刷新表单
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    return "success";
                }
            }, // 配置添加操作额外参数
            {
                closeAfterDel:true,
            }// 配置删除操作额外参数
        );
    }

    function updateStatus(id,status) {
        if(status==1){
            $.post("${path}/user/updateStatus",{"id":id,"status":"0"},function(data){
                //刷新表单
                $("#userTable").trigger("reloadGrid");
            });
        }else{
            $.post("${path}/user/updateStatus",{"id":id,"status":"1"},function(data){
                //刷新表单
                $("#userTable").trigger("reloadGrid");
            });
        }
    }
</script>


<%--创建一个面板--%>
<div class="panel panel-info" >
    <div class="panel-heading">
        <div class="jumbotron">
            <h3 align="center">用户信息</h3>
        </div>
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">用户管理</a></li>
        </ul>
        <div class="btn-group" role="group" aria-label="...">
            <button class="btn btn-primary">
                <a href="${path}/easyPoi/import">导入用户信息</a>
            </button>
            <button class="btn btn-success">
                <a href="${path}/easyPoi/export">导出用户信息</a>
            </button>
            <button type="button" class="btn btn-danger">测试</button>
        </div>
    </div>
    <table class="table table-striped " id="userTable">
        <div id="pg"></div>
    </table>

</div>
