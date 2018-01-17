<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
	<!-- 放大镜 -->
	<link rel="stylesheet" href="${ctxStatic}/zoomify/css/zoomify.min.css">
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>商品列表 </h5>
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
	<form:form id="searchForm" modelAttribute="goods" action="${ctx}/goods/goods/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>商品名称：</span>
				<form:input path="name" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
			&nbsp;&nbsp;
			<span>商品分类：</span>
				<sys:gridselect url="${ctx}/goods/goods/selectcategory" id="category" name="category"  value="${goods.category.id}"  title="选择商品分类" labelName="category.name" 
					labelValue="${goods.category.name}" cssClass="form-control required" fieldLabels="商品分类" fieldKeys="name" searchLabel="商品分类" searchKey="name" ></sys:gridselect>
			&nbsp;&nbsp;
			<span>是否上架：</span>
			    <form:select path="ison" class="form-control">
			    	<form:option value=""></form:option>
			    	<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"></form:options>
			    </form:select>
			&nbsp;&nbsp;
			<span>是否永久上架：</span>
				<form:select path="sfyj" class="form-control">
			    	<form:option value=""></form:option>
			    	<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"></form:options>
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
			<shiro:hasPermission name="goods:goods:add">
				<table:addRow url="${ctx}/goods/goods/form" title="商品" width="60%" height="70%"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="goods:goods:edit">
			    <table:editRow url="${ctx}/goods/goods/form" title="商品" id="contentTable" width="900px" height="70%"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="goods:goods:del">
				<table:delRow url="${ctx}/goods/goods/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="goods:goods:import">
				<table:importExcel url="${ctx}/goods/goods/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="goods:goods:export">
	       		<table:exportExcel url="${ctx}/goods/goods/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column name">商品名称</th>
				<th  class="sort-column price">商品价格</th>
				<th  class="sort-column category.id">商品分类</th>
				<th  class="sort-column kcsl">库存数量</th>
				<th  class="sort-column xgnum">限购数量</th>
				<th  class="sort-column xjrq">下架日期</th>
				<th  class="sort-column yunfei">运费设置</th>
				<th  class="sort-column ison">是否上架</th>
				<th  class="sort-column isyue">是否使用余额</th>
				<th  class="sort-column sffhyj">是否返还佣金</th>
				<th  class="sort-column sfkydjq">是否可用代金券</th>
				<th  class="sort-column sfyj">是否永久上架</th>
				<th  class="sort-column mainpicurl">商品主图(建议640*640)</th>
				<th  class="sort-column sort">排序</th>
				<!-- <th  class="sort-column contents">商品图片&描述</th> -->
				<!-- <th  class="sort-column updateDate">更新时间</th> -->
				<!-- <th  class="sort-column remarks">备注信息</th> -->
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="goods">
			<tr>
				<td> <input type="checkbox" id="${goods.id}" class="i-checks"></td>
				<td>
					${goods.name}
				</td>
				<td>
					${goods.price}
				</td>
				<td>
					${goods.category.name}
				</td>
				<td>
					${goods.kcsl}
				</td>
				<td>
					${goods.xgnum}
				</td>
				<td>
					${goods.xjrq}
				</td>
				<td>
					${fns:getDictLabel(goods.yunfei, 'sfby', '')}
				</td>
				<td>
					${fns:getDictLabel(goods.ison, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(goods.isyue, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(goods.sffhyj, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(goods.sfkydjq, 'yes_no', '')}
				</td>
				<td>
					${fns:getDictLabel(goods.sfyj, 'yes_no', '')}
				</td>
				<td>
					<div style="text-align:center;">
					    	<img src="${goods.mainpicurl}" class="zoomify" style="max-height: 100px;max-width: 100px;">
					</div>
				</td>
				<td>
					${goods.sort}
				</td>
				<td>
					<shiro:hasPermission name="goods:goods:view">
						<a href="#" onclick="openDialogView('查看商品', '${ctx}/goods/goods/form?id=${goods.id}','60%', '70%')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="goods:goods:edit">
    					<a href="#" onclick="openDialog('修改商品', '${ctx}/goods/goods/form?id=${goods.id}','60%', '70%')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="goods:goods:del">
						<a href="${ctx}/goods/goods/delete?id=${goods.id}" onclick="return confirmx('确认要删除该商品吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
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
<script src="${ctxStatic}/zoomify/js/zoomify.min.js"></script>
<script>
	$(function() {
		$('.zoomify').zoomify();
	});
 </script>
</body>
</html>