<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>分销额管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
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
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="zfxelq" action="${ctx}/zfxelq/zfxelq/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">微信Openid：</label></td>
					<td class="width-35">
						<form:input path="wxopenid" htmlEscape="false"    class="form-control "/>
					</td>
					<td class="width-15 active"><label class="pull-right">累计金额：</label></td>
					<td class="width-35">
						${zfxelq.ljjine}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">奖励比例：</label></td>
					<td class="width-35">
						${zfxelq.bili}
					</td>
					<td class="width-15 active"><label class="pull-right">实际奖励金额：</label></td>
					<td class="width-35">
						${zfxelq.jine}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">领取时间：</label></td>
					<td class="width-35">
						<fmt:formatDate value="${zfxelq.sdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>