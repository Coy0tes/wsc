<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
			//图片非空验证
		var mainpicurl = $("#mainpicurl").val();
		  if(!mainpicurl){
			  top.layer.msg('图片不能为空',{icon:2});
			  return false;
		  }
		  
		//取到富文本的值
			//富文本非空验证
		  var content = UM.getEditor('myEditor').getContent();
		  if(!content){
			  top.layer.msg('内容不能为空',{icon:2});
			  return false;
		  }
		  
		  if(validateForm.form()){
			  $("#contents").val(UM.getEditor('myEditor').getContent());//取富文本的值
			  $("#inputForm").submit();
			  return true;
		  }

		
		
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
	
	<link rel="stylesheet" href="${ctxStatic}/zoomify/css/zoomify.min.css">
	<!-- 富文本编辑器样式和函数 -->
	<link href="${ctxStatic}/umeditor1.2.3/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" src="${ctxStatic}/umeditor1.2.3/third-party/template.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/umeditor1.2.3/umeditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${ctxStatic}/umeditor1.2.3/umeditor.min.js"></script>
    <script type="text/javascript" src="${ctxStatic}/umeditor1.2.3/lang/zh-cn/zh-cn.js"></script>
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="goods" action="${ctx}/goods/goods/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>商品名称：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>商品价格：</label></td>
					<td class="width-35">
						<form:input path="price" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>商品分类：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/goods/goods/selectcategory" id="category" name="category.id"  value="${goods.category.id}"  title="选择商品分类" labelName="category.name" 
						 labelValue="${goods.category.name}" cssClass="form-control required" fieldLabels="商品分类" fieldKeys="name" searchLabel="商品分类" searchKey="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>库存数量：</label></td>
					<td class="width-35">
						<form:input path="kcsl" htmlEscape="false"    class="form-control required digits"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>限购数量(不限购)：</label></td>
					<td class="width-35">
						<form:input path="xgnum" htmlEscape="false"    class="form-control required digits"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>下架日期：</label></td>
					<td class="width-35">
						<form:input path="xjrq" htmlEscape="false"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  class="form-control required" readonly="true"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>运费设置：</label></td>
					<td class="width-35">
						<form:radiobuttons path="yunfei" items="${fns:getDictList('sfby')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否上架：</label></td>
					<td class="width-35">
						<form:radiobuttons path="ison" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否使用余额：</label></td>
					<td class="width-35">
						<form:radiobuttons path="isyue" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否返还佣金：</label></td>
					<td class="width-35">
						<form:radiobuttons path="sffhyj" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否可用代金券：</label></td>
					<td class="width-35">
						<form:radiobuttons path="sfkydjq" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>是否永久上架：</label></td>
					<td class="width-35">
						<form:radiobuttons path="sfyj" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="i-checks required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>商品主图(建议640*640)：</label></td>
					<td class="width-35">
						<form:hidden id="mainpicurl" path="mainpicurl" htmlEscape="false" maxlength="200" class="form-control" />
						<sys:ckfinder input="mainpicurl" type="images" uploadPath="/goods/goods" selectMultiple="false" maxHeight="100" maxWidth="100"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">商品图片展示(可多选，建议640*640)：</label></td>
					<td class="width-35">
						<form:hidden id="pictures" path="pictures" htmlEscape="false" maxlength="200" class="form-control" />
						<sys:ckfinder input="pictures" type="images" uploadPath="/goods/goods" selectMultiple="true" maxHeight="100" maxWidth="100"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>商品图片&描述：</label></td>
					<td class="width-35">
					<!-- <form:input path="contents" htmlEscape="false"    class="form-control required"/> -->
						<form:hidden path="contents"/>
						<div id="hiddendiv" style="display: none;">渲染umeditor元素</div>
						<!--style给定宽度可以影响编辑器的最终宽度-->
						<!-- 富文本编辑器 -->
						<script type="text/plain" id="myEditor" style="width:100%;height:200px;">
    						
						</script>
						
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>排序：</label></td>
					<td class="width-35">
						<form:input path="sort" htmlEscape="false"    class="form-control required digits"/>
					</td>
				</tr>
		 	</tbody>
		</table>
		
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">商品规格：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-primary btn-outline btn-sm" onclick="addRow('#goodsGuigeList', goodsGuigeRowIdx, goodsGuigeTpl);goodsGuigeRowIdx = goodsGuigeRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
						<tr>
							<th class="hide"></th>
							<th>规格名</th>
							<th>库存</th>
							<th>价格</th>
							<th>促销价格</th>
							<th>规格图片</th>
							<th width="10">&nbsp;</th>
						</tr>
					</thead>
				<tbody id="goodsGuigeList">
				</tbody>
			</table>
			<script type="text/template" id="goodsGuigeTpl">//<!--
				<tr id="goodsGuigeList{{idx}}">
					<td class="hide">
						<input id="goodsGuigeList{{idx}}_id" name="goodsGuigeList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="goodsGuigeList{{idx}}_delFlag" name="goodsGuigeList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					<td>
						<input id="goodsGuigeList{{idx}}_name" name="goodsGuigeList[{{idx}}].name" type="text" value="{{row.name}}"    class="form-control required"/>
					</td>
					<td>
						<input id="goodsGuigeList{{idx}}_kcsl" name="goodsGuigeList[{{idx}}].kcsl" type="text" value="{{row.kcsl}}"    class="form-control required digits"/>
					</td>
					<td>
						<input id="goodsGuigeList{{idx}}_price" name="goodsGuigeList[{{idx}}].price" type="text" value="{{row.price}}"    class="form-control required number"/>
					</td>
					<td>
						<input id="goodsGuigeList{{idx}}_csprice" name="goodsGuigeList[{{idx}}].csprice" type="text" value="{{row.csprice}}"    class="form-control  number"/>
					</td>
					<td>
						<input type="hidden" id="goodsGuigeList{{idx}}_imgurl" name="goodsGuigeList[{{idx}}].imgurl"  value="{{row.imgurl}}" />
						<sys:ckfinder input="goodsGuigeList{{idx}}_imgurl" type="images" uploadPath="/goods/goods" selectMultiple="false" maxHeight="100" maxWidth="100"/>
					</td>
					<td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#goodsGuigeList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td>
				</tr>//-->
			</script>
			<script type="text/javascript">
				var goodsGuigeRowIdx = 0, goodsGuigeTpl = $("#goodsGuigeTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(goods.goodsGuigeList)};
					for (var i=0; i<data.length; i++){
						addRow('#goodsGuigeList', goodsGuigeRowIdx, goodsGuigeTpl, data[i]);
						goodsGuigeRowIdx = goodsGuigeRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</form:form>
	<script type="text/javascript">
	//实例化编辑器
    var um = UM.getEditor('myEditor');
     um.ready(function() {//编辑器初始化完成再赋值 
        $('#hiddendiv').html($("#contents").val());
        um.setContent($('#hiddendiv').text());
    });  
</script>
	<script src="${ctxStatic}/zoomify/js/zoomify.min.js"></script>
	<script>
		$(function() {
			$("#mainpicurl").find("img").addClass("zoomify");
			$('.zoomify').zoomify();
		});
	 </script>
</body>
</html>