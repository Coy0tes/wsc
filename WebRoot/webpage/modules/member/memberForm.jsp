<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
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
	<script type="text/javascript" src="${ctxStatic}/area/jsAddress.js"></script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="member" action="${ctx}/member/member/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" >
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">微信Openid：</label></td>
					<td class="width-35">
						<form:input path="wxopenid" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">姓名：</label></td>
					<td class="width-35">
						<form:input path="name" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">手机号：</label></td>
					<td class="width-35">
						<form:input path="mobile" htmlEscape="false"    class="form-control  digits"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">省：</label></td>
					<td class="width-35">
						<form:select path="province" class="form-control ">
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">市：</label></td>
					<td class="width-35">
						<form:select path="city" class="form-control ">
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">区：</label></td>
					<td class="width-35">
						<form:select path="county" class="form-control ">
						</form:select>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">收货人电话：</label></td>
					<td class="width-35">
						<form:input path="shrdh" htmlEscape="false"    class="form-control digits required"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">收货地址：</label></td>
					<td class="width-35">
						<form:input path="address" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">推荐人微信Openid：</label></td>
					<td class="width-35">
						<form:input path="tjrwxopenid" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">余额：</label></td>
					<td class="width-35">
						<form:input path="yue" htmlEscape="false"    class="form-control  number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">佣金：</label></td>
					<td class="width-35">
						<form:input path="yongjin" htmlEscape="false"    class="form-control  number"/>
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">海报URL：</label></td>
					<td class="width-35">
						<form:input path="haibaourl" htmlEscape="false"    class="form-control "/>
					</td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
<script type="text/javascript">
	addressInit("province","city","county","${member.province}","${member.city}","${member.county}");
</script>
</body>
</html>