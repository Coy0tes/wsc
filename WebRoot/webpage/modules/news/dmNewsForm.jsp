<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>新闻信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  var imgurl = $("#imgurl").val();
		  if(!imgurl){
			  top.layer.msg('新闻主图不能为空',{icon:2});
			  return false;
		  }
		
			//取到富文本的值
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
		<form:form id="inputForm" modelAttribute="dmNews" action="${ctx}/news/dmNews/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
		   		
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>所属分类：</label></td>
					<td class="width-35">
						<sys:gridselect url="${ctx}/news/dmNews/selectcategory" id="category" name="category.id"  value="${dmNews.category.id}"  title="选择所属分类" labelName="category.name" 
						 labelValue="${dmNews.category.name}" cssClass="form-control required" fieldLabels="分类名称" fieldKeys="name" searchLabel="分类名称" searchKey="name" ></sys:gridselect>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>新闻标题：</label></td>
					<td class="width-35">
						<form:input path="title" htmlEscape="false"    class="form-control required"/>
					</td>
				</tr>
				<tr >
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>新闻主图：</label></td>
					<td id="mainpic" class="width-35">
						<form:hidden id="imgurl" path="imgurl" htmlEscape="false" maxlength="200" class="form-control"/>
						<sys:ckfinder input="imgurl" type="images" uploadPath="/news/dmNews" selectMultiple="false" maxHeight="100" maxWidth="100"  />
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>新闻内容：</label></td>
					<td class="width-35">
						<form:hidden path="contents"/>
						<div id="hiddendiv">渲染umeditor元素</div>
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
				<tr>
		 	</tbody>
		</table>
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
		$("#mainpic").find("img").addClass("zoomify");
		$('.zoomify').zoomify();
	});
 </script>
</body>
</html>