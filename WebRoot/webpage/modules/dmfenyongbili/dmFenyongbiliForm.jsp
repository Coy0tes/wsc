<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>分佣比例管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit(); //form的submit方法，请求form的action
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
		<form:form id="inputForm" modelAttribute="dmFenyongbili" action="${ctx}/dmfenyongbili/dmFenyongbili/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}" hideType="1"/>	
		<div style="text-align:center;margin:20px"><h3>系统参数设置</h3></div>
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer" align="center" style="width:60%;">
		   <tbody>
		   		<tr>
					<th colspan="3" align="center"  class="width active "  ><font style="color:blue;font-weight:bold;">分享客</font></th>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>下1级微客分佣比例(%)：</label></td>
					<td class="width-35">
						<form:input path="wk1" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>下2级微客分佣比例(%)：</label></td>
					<td class="width-35">
						<form:input path="wk2" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<tr>
					<th colspan="3" align="center" class="width active"><font style="color:blue;font-weight:bold;">一级分销商</th>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>达到销售额(元)：</label></td>
					<td class="width-35">
						<form:input path="jmf" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>下1级微客分佣比例(%)：</label></td>
					<td class="width-35">
						<form:input path="jm1" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>下2级微客分佣比例(%)：</label></td>
					<td class="width-35">
						<form:input path="jm2" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<tr>
					<th colspan="3" class="width active"><font style="color:blue;font-weight:bold;">二级分销商</th>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>达到销售额(元)：</label></td>
					<td class="width-35">
						<form:input path="jmf11" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>下1级微客分佣比例(%)：</label></td>
					<td class="width-35">
						<form:input path="jm11" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>下2级微客分佣比例(%)：</label></td>
					<td class="width-35">
						<form:input path="jm22" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<%-- <tr>
					<th colspan="3" class="width active"><font style="color:blue;font-weight:bold;">总分销额提成</th>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>达到金额(元)：</label></td>
					<td class="width-35">
						<form:input path="jmtced" htmlEscape="false"    class="form-control required number"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>提成金额(%)：</label></td>
					<td class="width-35">
						<form:input path="jmtcbl" htmlEscape="false"    class="form-control required number"/>
					</td>
		  		</tr> --%>
		  		<tr>
					<th colspan="3" class="width active"><font style="color:blue;font-weight:bold;">提成到账比例</th>
				</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>提现到账(%)：</label></td>
					<td class="width-35">
						<form:input path="txdzbl" htmlEscape="false"    class="form-control required number"/>
					</td>
		  		</tr>
		 	</tbody>
		</table>
		<div style="text-align:center;margin-top:20px;">
		    <a href="javascript:" style="height:35px;width:80px;" onClick="doSubmit();" class="btn btn-success"><i class="fa fa-plus"></i>&nbsp;提交</a>
		</div>
	</form:form>
</body>
</html>