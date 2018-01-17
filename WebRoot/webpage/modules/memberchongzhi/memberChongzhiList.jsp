<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>充值管理</title>
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
		<h5>充值管理列表 </h5>
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
	<form:form id="searchForm" modelAttribute="memberChongzhi" action="${ctx}/memberchongzhi/memberChongzhi/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>姓名：</span>
				<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>手机号：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="20"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="memberchongzhi:memberChongzhi:add">
				<table:addRow url="${ctx}/memberchongzhi/memberChongzhi/form" title="充值管理" width="550px" height="400px"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="memberchongzhi:memberChongzhi:edit">
			    <table:editRow url="${ctx}/memberchongzhi/memberChongzhi/form" title="充值管理" id="contentTable" width="550px" height="400px"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="memberchongzhi:memberChongzhi:del">
				<table:delRow url="${ctx}/memberchongzhi/memberChongzhi/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="memberchongzhi:memberChongzhi:import">
				<table:importExcel url="${ctx}/memberchongzhi/memberChongzhi/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="memberchongzhi:memberChongzhi:export">
	       		<table:exportExcel url="${ctx}/memberchongzhi/memberChongzhi/export"></table:exportExcel><!-- 导出按钮 -->
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
				<!-- <th  class="sort-column wxopenid">充值人微信Openid</th> -->
				<th  class="headimgurl">头像</th>
				<th  class="nickname">昵称</th>
				<th  class="sort-column name">姓名</th>
				<th  class="sort-column mobile">手机号</th>
				<th  class="sort-column jine">充值金额</th>
				<th  class="sort-column zsjine">赠送金额</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th  class="sort-column remarks">备注信息</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="memberChongzhi">
			<tr>
				<td> <input type="checkbox" id="${memberChongzhi.id}" class="i-checks"></td>
				<%-- <td><a  href="#" onclick="openDialogView('查看充值管理', '${ctx}/memberchongzhi/memberChongzhi/form?id=${memberChongzhi.id}','550px', '400px')">
					${memberChongzhi.wxopenid}
				</a></td> --%>
				<td>
					<div style="text-align:center">
						<img src="${memberChongzhi.headimgurl}" style="width:60px;heigth:60px;"/>
 					</div>
				</td>
				<td>
					${memberChongzhi.nickname}
				</td>
				<td>
					${memberChongzhi.name}
				</td>
				<td>
					${memberChongzhi.mobile}
				</td>
				<td>
					${memberChongzhi.jine}
				</td>
				<td>
					${memberChongzhi.zsjine}
				</td>
				<td>
					<fmt:formatDate value="${memberChongzhi.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${memberChongzhi.remarks}
				</td>
				<td>
					<shiro:hasPermission name="memberchongzhi:memberChongzhi:view">
						<a href="#" onclick="openDialogView('查看充值管理', '${ctx}/memberchongzhi/memberChongzhi/form?id=${memberChongzhi.id}','550px', '380px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<%-- <shiro:hasPermission name="memberchongzhi:memberChongzhi:edit">
    					<a href="#" onclick="openDialog('修改充值管理', '${ctx}/memberchongzhi/memberChongzhi/form?id=${memberChongzhi.id}','550px', '400px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission> --%>
    				<shiro:hasPermission name="memberchongzhi:memberChongzhi:del">
						<a href="${ctx}/memberchongzhi/memberChongzhi/delete?id=${memberChongzhi.id}" onclick="return confirmx('确认要删除该充值管理吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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