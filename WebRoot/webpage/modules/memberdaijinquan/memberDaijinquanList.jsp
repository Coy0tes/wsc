<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>代金券管理</title>
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
		<h5>代金券管理 </h5>
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
	<form:form id="searchForm" modelAttribute="memberDaijinquan" action="${ctx}/memberdaijinquan/memberDaijinquan/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>姓名：</span>
				<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>手机号：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
			<span>代金券来源：</span>
				<form:select path="laiyuan"  class="form-control m-b">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('laiyuan')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="memberdaijinquan:memberDaijinquan:add">
				<table:addRow url="${ctx}/memberdaijinquan/memberDaijinquan/form" title="代金券管理" width="433px" height="500px"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="memberdaijinquan:memberDaijinquan:edit">
			    <table:editRow url="${ctx}/memberdaijinquan/memberDaijinquan/form" title="代金券管理" id="contentTable" width="433px" height="500px"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="memberdaijinquan:memberDaijinquan:del">
				<table:delRow url="${ctx}/memberdaijinquan/memberDaijinquan/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="memberdaijinquan:memberDaijinquan:import">
				<table:importExcel url="${ctx}/memberdaijinquan/memberDaijinquan/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="memberdaijinquan:memberDaijinquan:export">
	       		<table:exportExcel url="${ctx}/memberdaijinquan/memberDaijinquan/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-primary btn-outline btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		   
		   <button class="btn btn-primary btn-outline btn-sm " data-toggle="tooltip" data-placement="left" onclick="openDialog('修改代金券管理', '${ctx}/memberdaijinquan/memberDaijinquan/sendAllform','433px', '500px')" title="批量发放代金券"><i class="glyphicon glyphicon-edit"></i> 批量发放代金券</button>
		
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
				<th  class="sort-column wxopenid">微信Openid</th>
				<th  class="sort-column name">姓名</th>
				<th  class="sort-column mobile">手机号</th>
				<th  class="sort-column laiyuan">代金券来源</th>
				<th  class="sort-column orderid">来源订单</th>
				<th  class="sort-column jine">代金券金额</th>
				<th  class="sort-column syzt">使用状态</th>
				<th  class="sort-column createDate">发放时间</th>
				<th  class="sort-column updateDate">使用时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberDaijinquan">
			<tr>
				<td> <input type="checkbox" id="${memberDaijinquan.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看代金券管理', '${ctx}/memberdaijinquan/memberDaijinquan/form?id=${memberDaijinquan.id}','433px', '500px')">
					${memberDaijinquan.wxopenid}
				</a></td>
				<td>
					${memberDaijinquan.name}
				</td>
				<td>
					${memberDaijinquan.mobile}
				</td>
				<td>
					${fns:getDictLabel(memberDaijinquan.laiyuan, 'laiyuan', '')}
				</td>
				<td>
					${memberDaijinquan.orderid}
				</td>
				<td>
					${memberDaijinquan.jine}
				</td>
				<td>
					${fns:getDictLabel(memberDaijinquan.syzt, 'djqsyzt', '')}
				</td>
				<td>
					<fmt:formatDate value="${memberDaijinquan.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${memberDaijinquan.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="memberdaijinquan:memberDaijinquan:view">
						<a href="#" onclick="openDialogView('查看代金券管理', '${ctx}/memberdaijinquan/memberDaijinquan/form?id=${memberDaijinquan.id}','433px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="memberdaijinquan:memberDaijinquan:edit">
    					<a href="#" onclick="openDialog('修改代金券管理', '${ctx}/memberdaijinquan/memberDaijinquan/form?id=${memberDaijinquan.id}','433px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="memberdaijinquan:memberDaijinquan:del">
						<a href="${ctx}/memberdaijinquan/memberDaijinquan/delete?id=${memberDaijinquan.id}" onclick="return confirmx('确认要删除该代金券管理吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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