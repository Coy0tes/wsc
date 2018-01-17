<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>提现管理</title>
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
		<form:form id="inputForm" modelAttribute="yjtx" action="${ctx}/yjtx/yjtx/shbtg" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">微信OPENID：</label></td>
					<td class="width-35">
						${yjtx.wxopenid}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">提现金额：</label></td>
					<td class="width-35">
					${yjtx.jine}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">到账金额：</label></td>
					<td class="width-35">
					${yjtx.daozhangjine}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">领取状态</label></td>
					<td class="width-35">
					${yjtx.status}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">申请时间：</label></td>
					<td class="width-35">
					<fmt:formatDate value="${yjtx.sdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">后台处理状态 </label></td>
					<td class="width-35">
						${ yjtx.clzt }
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">处理时间：</label></td>
					<td class="width-35">
					<fmt:formatDate value="${yjtx.cldate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">银行：</label></td>
					<td class="width-35">
					${yjtx.bank}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">卡号：</label></td>
					<td class="width-35">
					${yjtx.cardid}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">户名：</label></td>
					<td class="width-35">
					${yjtx.username}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">手机号：</label></td>
					<td class="width-35">
					${yjtx.mobile}
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">处理备注：</label></td>
					<td class="width-35">
						<form:input path="clremark" htmlEscape="false"    class="form-control "/>
					</td>
				</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>