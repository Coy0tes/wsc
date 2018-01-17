<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>提现管理</title>
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
		<h5>提现列表 </h5>
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
	<sys:message content="${message}" hideType="1"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="yjtx" action="${ctx}/yjtx/yjtx/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>后台处理状态</span>
				<form:select path="clzt" class="form-control">
					<form:option value=""></form:option>
					<form:option value="0">未处理</form:option>
					<form:option value="1">审核通过</form:option>
					<form:option value="2">审核不通过</form:option>
				</form:select>
			&nbsp;&nbsp;
			<span>处理日期：</span>
				<form:input path="cldate" htmlEscape="false" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  class=" form-control input-sm"/>
			&nbsp;&nbsp;
			<span>领取状态</span>
				<form:select path="status" class="form-control">
					<form:option value=""></form:option>
					<form:option value="未领取">未领取</form:option>
					<form:option value="已领取">已领取</form:option>
				</form:select>
			&nbsp;&nbsp;
			<span>申请日期：</span>
				<form:input path="sdate" htmlEscape="false" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  class=" form-control input-sm"/>
			
		</div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<%-- <shiro:hasPermission name="yjtx:yjtx:add">
				<table:addRow url="${ctx}/yjtx/yjtx/form" title="提现"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="yjtx:yjtx:edit">
			    <table:editRow url="${ctx}/yjtx/yjtx/form" title="提现" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="yjtx:yjtx:del">
				<table:delRow url="${ctx}/yjtx/yjtx/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="yjtx:yjtx:import">
				<table:importExcel url="${ctx}/yjtx/yjtx/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="yjtx:yjtx:export">
	       		<table:exportExcel url="${ctx}/yjtx/yjtx/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column jine">提现金额</th>
				<th  class="sort-column daozhangjine">到账金额</th>
				<th  class="sort-column status">领取状态</th>
				<th  class="sort-column sdate">申请时间</th>
				<th  class="sort-column clzt">后台处理状态</th>
				<th  class="sort-column clremark">处理备注</th>
				<th  class="sort-column cldate">处理日期</th>
				<th  class="sort-column bank">银行</th>
				<th  class="sort-column cardid">卡号</th>
				<th  class="sort-column username">户名</th>
				<th  class="sort-column mobile">手机号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="yjtx">
			<tr>
				<td> <input type="checkbox" id="${yjtx.id}" class="i-checks"></td>
				<td>
					<div style="text-align:center">
						<img src="${yjtx.headimgurl}" style="width:60px;heigth:60px;"/>
 					</div>
				</td>
				<td>
					${yjtx.nickname}
				</td>
				<td>
					${yjtx.jine}
				</td>
				<td>
					${yjtx.daozhangjine}
				</td>
				<td>
					${yjtx.status}
				</td>
				<td>
					<fmt:formatDate value="${yjtx.sdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${yjtx.clzt}
				</td>
				<td>
					${yjtx.clremark}
				</td>
				<td>
					<fmt:formatDate value="${yjtx.cldate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${yjtx.bank}
				</td>
				<td>
					${yjtx.cardid}
				</td>
				<td>
					${yjtx.username}
				</td>
				<td>
					${yjtx.mobile}
				</td>
				<td>
					<shiro:hasPermission name="yjtx:yjtx:view">
						<a href="#" onclick="openDialogView('查看提现', '${ctx}/yjtx/yjtx/form?id=${yjtx.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					
					<c:if test="${yjtx.clzt == '未处理'}">
						<a href="${ctx}/yjtx/yjtx/shtg?id=${yjtx.id}" onclick="return confirmx('是否确认已打款？', this.href)"   class="btn btn-success btn-xs"><i class="fa fa-check"></i>审核通过</a>
						<a href="#" onclick="openDialog('审核不通过', '${ctx}/yjtx/yjtx/form?id=${yjtx.id}','800px', '500px')" class="btn btn-danger btn-xs" ><i class="fa fa-trash"></i> 审核不通过</a>
					</c:if>
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