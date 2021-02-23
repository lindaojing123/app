<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script>
    $(function () {
        $('#categoryTable').jqGrid({
            //整合bootstrap样式
            styleUI: 'Bootstrap',
            url: '${path}/category/getCategory',
            datatype: 'json',
            mtype: 'get',
            colNames:['名称', '类别', '父类别id'],
            colModel: [
                {
                    name: 'cateName',
                    editable: true,// 开启当前列的可编辑模式， 前提是cellEdit必须为true
                },
                {name: 'levels', editable: false,},
                {name: 'parentId', editable: false,},
            ],
            autowidth: true,
            cellEdit: false, //开启数据表格可编辑模式
            rownumbers: true,
            pager: '#pg',// 生成分页工具栏
            rowList: [ 2 , 5, 10, 20, 50], // 指定每页展示信息条数的选项列表
            rowNum: 2, //  指定默认每页展示的条数，这个值必须来自rowList数组的一个元素
            viewrecords: true, // 显示总记录数
            page: 1, // 默认显示第几页
            editurl: '${path}/category/edit',
            //multiselect:true,
            subGrid : true,  //是否开启子表格
            /*
            subgrid_id：是在创建表数据时创建的div标签的ID
            row_id：    是该行的ID
            * */
            subGridRowExpanded : function(subgrid_id, row_id) {  //设置展开子表格的参数
            addSubGird(subgrid_id, row_id);
        }
        }).navGrid('#pg',
            // 控制功能按钮是否显示，默认都为true
            {'add': true, 'edit': true, 'del': true, 'search': true, 'refresh': false},
            {
                closeAfterEdit:true,// 编辑提交后自动关闭
            }, // 配置编辑操作额外参数
            {
                closeAfterAdd:true,
                editData:{'levels':'1'} ,// 指定添加额外传的参数，会覆盖本次请求同名的请求参数
            }, // 配置添加操作额外参数
            {
                closeAfterDel: true,
            }// 配置删除操作额外参数
        );
    })
    function addSubGird(subgrid_id, row_id){
        var tableId = subgrid_id+"Table";
        var pg = subgrid_id+"Page";

        //在子表格的div中创建一个table和一个div
        $("#"+subgrid_id).html("<table id="+tableId+" /><div id="+pg+" />");
        $('#'+tableId).jqGrid({
            //整合bootstrap样式
            styleUI: 'Bootstrap',
            url: '${path}/category/get2Category?parentId='+row_id,
            datatype: 'json',
            mtype: 'get',
            colNames:['名称', '类别', '父类别id'],
            colModel: [
                {
                    name: 'cateName',
                    editable: true,// 开启当前列的可编辑模式， 前提是cellEdit必须为true
                },
                {name: 'levels', editable: false,},
                {name: 'parentId', editable: true,},
            ],
            autowidth: true,
            cellEdit: false, //开启数据表格可编辑模式
            rownumbers: true,
            pager: '#'+pg,// 生成分页工具栏
            rowList: [ 2 , 5, 10, 20, 50], // 指定每页展示信息条数的选项列表
            rowNum: 2, //  指定默认每页展示的条数，这个值必须来自rowList数组的一个元素
            viewrecords: true, // 显示总记录数
            page: 1, // 默认显示第几页
            editurl: '${path}/category/edit',
        }).navGrid('#'+pg,
            // 控制功能按钮是否显示，默认都为true
            {'add': true, 'edit': true, 'del': true, 'search': false, 'refresh': false},
            {
                closeAfterEdit:true,// 编辑提交后自动关闭
            }, // 配置编辑操作额外参数
            {
                closeAfterAdd:true,
                editData:{'levels':'2'}// 指定添加额外传的参数，会覆盖本次请求同名的请求参数

            }, // 配置添加操作额外参数
            {
                closeAfterDel: true,
            }
        );
    }
</script>

<%--创建一个面板--%>
<div class="panel panel-info" >
    <div class="panel-heading">
        <div class="jumbotron">
            <h3 align="center">分类信息</h3>
        </div>
    </div>
    <div class="panel-body">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="#">分类管理</a></li>
        </ul>
    </div>
    <table class="table table-striped " id="categoryTable">
        <div id="pg"></div>
    </table>

</div>
