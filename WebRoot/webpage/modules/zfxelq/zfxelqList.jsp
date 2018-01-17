<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>分销额管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>分销额列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="zfxelq" action="${ctx}/zfxelq/zfxelq/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>领取时间：</span>
				<form:input path="sdate" htmlEscape="false" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="zfxelq:zfxelq:add">
				<table:addRow url="${ctx}/zfxelq/zfxelq/form" title="分销额"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zfxelq:zfxelq:edit">
			    <table:editRow url="${ctx}/zfxelq/zfxelq/form" title="分销额" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zfxelq:zfxelq:del">
				<table:delRow url="${ctx}/zfxelq/zfxelq/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zfxelq:zfxelq:import">
				<table:importExcel url="${ctx}/zfxelq/zfxelq/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="zfxelq:zfxelq:export">
	       		<table:exportExcel url="${ctx}/zfxelq/zfxelq/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-primary btn-outline btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="headimgurl">头像</th>
				<th  class="nickname">昵称</th>
				<th  class="sort-column ljjine">累计金额</th>
				<th  class="sort-column bili">奖励比例</th>
				<th  class="sort-column jine">实际奖励金额</th>
				<th  class="sort-column sdate">领取时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="zfxelq">
			<tr>
				<td> <input type="checkbox" id="${zfxelq.id}" class="i-checks"></td>
				<td>
					<div style="text-align:center">
						<img src="${zfxelq.headimgurl}" style="width:60px;heigth:60px;"/>
 					</div>
				</td>
				<td>
					${zfxelq.nickname}
				</td>
				<td>
					${zfxelq.ljjine}
				</td>
				<td>
					${zfxelq.bili}
				</td>
				<td>
					${zfxelq.jine}
				</td>
				<td>
					<fmt:formatDate value="${zfxelq.sdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="zfxelq:zfxelq:view">
						<a href="#" onclick="openDialogView('查看分销额', '${ctx}/zfxelq/zfxelq/form?id=${zfxelq.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="zfxelq:zfxelq:edit">
    					<a href="#" onclick="openDialog('修改分销额', '${ctx}/zfxelq/zfxelq/form?id=${zfxelq.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="zfxelq:zfxelq:del">
						<a href="${ctx}/zfxelq/zfxelq/delete?id=${zfxelq.id}" onclick="return confirmx('确认要删除该分销额吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>
</body>
</html>