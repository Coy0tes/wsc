<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理管理</title>
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
		<h5>订单管理列表 </h5>
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
	<form:form id="searchForm" modelAttribute="goodsOrder" action="${ctx}/goodsorder/goodsOrder/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>微信Openid：</span>
				<form:input path="wxopenid" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>客户姓名：</span>
				<form:input path="khname" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>联系方式：</span>
				<form:input path="mobile" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			<span>物流单号：</span>
				<form:input path="wldh" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="goodsorder:goodsOrder:add">
				<table:addRow url="${ctx}/goodsorder/goodsOrder/form" title="订单管理"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<%-- <shiro:hasPermission name="goodsorder:goodsOrder:edit">
			    <table:editRow url="${ctx}/goodsorder/goodsOrder/form" title="订单管理" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission> --%>
			<shiro:hasPermission name="goodsorder:goodsOrder:del">
				<table:delRow url="${ctx}/goodsorder/goodsOrder/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="goodsorder:goodsOrder:import">
				<table:importExcel url="${ctx}/goodsorder/goodsOrder/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="goodsorder:goodsOrder:export">
	       		<table:exportExcel url="${ctx}/goodsorder/goodsOrder/export"></table:exportExcel><!-- 导出按钮 -->
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
				<!-- <th  class="sort-column wxopenid">微信Openid</th> -->
				<th  class="headimgurl">头像</th>
				<th  class="nickname">昵称</th>
				<th  class="sort-column khname">客户姓名</th>
				<th  class="sort-column ddjg">订单总价(元)</th>
				<!-- <th  class="sort-column sfsydjq">是否使用代金券</th> -->
				<th  class="sort-column djqjine">代金券金额(元)</th>
				<th  class="sort-column xfje">实际支付金额(元)</th>
				<th  class="sort-column yunfei">运费(元)</th>
				<th  class="sort-column mobile">联系方式</th>
				<th  class="sort-column address">联系地址</th>
				<th  class="sort-column status">订单状态</th>
				<th  class="sort-column wldh">物流单号</th>
				<th  class="sort-column kdgs">快递公司</th>
				<th  class="sort-column updateDate">更新时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goodsOrder">
			<tr>
				<td> <input type="checkbox" id="${goodsOrder.id}" class="i-checks"></td>
				<%-- <td><a  href="#" onclick="openDialogView('查看订单管理', '${ctx}/goodsorder/goodsOrder/form?id=${goodsOrder.id}','800px', '500px')">
					${goodsOrder.wxopenid}
				</a></td> --%>
				<td>
					<div style="text-align:center">
						<img src="${goodsOrder.headimgurl}" style="width:60px;heigth:60px;"/>
 					</div>
				</td>
				<td>
					${goodsOrder.nickname}
				</td>
				<td>
					${goodsOrder.khname}
				</td>
				<td>
					${goodsOrder.ddjg}
				</td>
				<%-- <td>
				    ${fns:getDictLabel(goodsOrder.sfsydjq,'yes_no','')}
				</td> --%>
				<td>
					${goodsOrder.djqjine}
				</td>
				<td>
					${goodsOrder.xfje}
				</td>
				<td>
					${goodsOrder.yunfei}
				</td>
				<td>
					${goodsOrder.mobile}
				</td>
				<td>
					${goodsOrder.address}
				</td>
				<td>
					${goodsOrder.status}
				</td>
				<td>
					${goodsOrder.wldh}
				</td>
				<td>
					${goodsOrder.kdgs}
				</td>
				<td>
					<fmt:formatDate value="${goodsOrder.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<shiro:hasPermission name="goodsorder:goodsOrder:view">
						<a href="#" onclick="openDialogView('查看订单管理', '${ctx}/goodsorder/goodsOrder/form?id=${goodsOrder.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<!-- <shiro:hasPermission name="goodsorder:goodsOrder:edit">
    					<a href="#" onclick="openDialog('修改订单管理', '${ctx}/goodsorder/goodsOrder/form?id=${goodsOrder.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				 -->
    				<shiro:hasPermission name="goodsorder:goodsOrder:del">
						<a href="${ctx}/goodsorder/goodsOrder/delete?id=${goodsOrder.id}" onclick="return confirmx('确认要删除该订单管理吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
						<a href="#" onclick="openDialog('添加物流单号', '${ctx}/goodsorder/goodsOrder/formWuliudanhao?id=${goodsOrder.id}', '480px', '285px' )" class="btn btn-success btn-xs"> 物流单号</a>
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