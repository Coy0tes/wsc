<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理管理</title>
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
</head>
<body class="hideScroll">
	<form:form id="inputForm" modelAttribute="goodsOrder" action="${ctx}/goodsorder/goodsOrder/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right">微信Openid：</label></td>
					<td class="width-35">
						${goodsOrder.wxopenid }
					</td>
					<td class="width-15 active"><label class="pull-right">客户姓名：</label></td>
					<td class="width-35">
						<!-- <form:input path="khname" htmlEscape="false"  readonly="true"  class="form-control "/> -->
						${goodsOrder.khname }
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">订单总价：</label></td>
					<td class="width-35">
						${goodsOrder.ddjg }
					</td>
					<td class="width-15 active"><label class="pull-right">是否使用代金券：</label></td>
					<td class="width-35">
				   		${fns:getDictLabel(goodsOrder.sfsydjq,'yes_no','')}
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">代金券ID：</label></td>
					<td class="width-35">
						${goodsOrder.djqid }
					</td>
					<td class="width-15 active"><label class="pull-right">代金券金额：</label></td>
					<td class="width-35">
						${goodsOrder.djqjine }
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">实际支付金额：</label></td>
					<td class="width-35">
						${goodsOrder.xfje }
					</td>
					<td class="width-15 active"><label class="pull-right">运费：</label></td>
					<td class="width-35">
						${goodsOrder.yunfei }
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">领取方式：</label></td>
					<td class="width-35">
						${goodsOrder.lqfs }
					</td>
					<td class="width-15 active"><label class="pull-right">领取/邮寄时间：</label></td>
					<td class="width-35">
						${goodsOrder.lqyjsj }
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">联系方式：</label></td>
					<td class="width-35">
						${goodsOrder.mobile }
					</td>
					<td class="width-15 active"><label class="pull-right">联系地址：</label></td>
					<td class="width-35">
						${goodsOrder.address }
					</td>
				<tr>
				</tr>
					<td class="width-15 active"><label class="pull-right">订单状态：</label></td>
					<td class="width-35">
						${goodsOrder.status }
					</td>
					<td class="width-15 active"><label class="pull-right">物流单号：</label></td>
					<td class="width-35">
						${goodsOrder.wldh }
					</td>
		  		</tr>
		  		<tr>
					<td class="width-15 active"><label class="pull-right">快递公司：</label></td>
					<td class="width-35">
						${goodsOrder.kdgs}
					</td>
		  		</tr>
		 	</tbody>
		</table>
		
		<div class="tabs-container">
            <ul class="nav nav-tabs">
				<li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true">订单明细：</a>
                </li>
            </ul>
            <div class="tab-content">
				<div id="tab-1" class="tab-pane active">
			<a class="btn btn-primary btn-outline btn-sm " onclick="addRow('#goodsOrderDetailList', goodsOrderDetailRowIdx, goodsOrderDetailTpl);goodsOrderDetailRowIdx = goodsOrderDetailRowIdx + 1;" title="新增"><i class="fa fa-plus"></i> 新增</a>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th class="hide"></th>
						<th>商品ID</th>
						<th>商品名称</th>
						<th>商品规格ID</th>
						<th>商品规格</th>
						<th>购买数量</th>
						<th>商品单价</th>
						<!-- <th width="10">&nbsp;</th> -->
					</tr>
				</thead>
				<tbody id="goodsOrderDetailList">
				</tbody>
			</table>
			<script type="text/template" id="goodsOrderDetailTpl">//<!--
				<tr id="goodsOrderDetailList{{idx}}">
					<td class="hide">
						<input id="goodsOrderDetailList{{idx}}_id" name="goodsOrderDetailList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
						<input id="goodsOrderDetailList{{idx}}_delFlag" name="goodsOrderDetailList[{{idx}}].delFlag" type="hidden" value="0"/>
					</td>
					
					<td>
						<input id="goodsOrderDetailList{{idx}}_goodsid" name="goodsOrderDetailList[{{idx}}].goodsid" type="text" value="{{row.goodsid}}" readonly="true"   class="form-control "/>
					</td>
					
					
					<td>
						<input id="goodsOrderDetailList{{idx}}_goodsname" name="goodsOrderDetailList[{{idx}}].goodsname" type="text" value="{{row.goodsname}}" readonly="true"   class="form-control "/>
					</td>
					
					
					<td>
						<input id="goodsOrderDetailList{{idx}}_goodsguigeid" name="goodsOrderDetailList[{{idx}}].goodsguigeid" type="text" value="{{row.goodsguigeid}}" readonly="true"   class="form-control "/>
					</td>
					
					
					<td>
						<input id="goodsOrderDetailList{{idx}}_goodsguigename" name="goodsOrderDetailList[{{idx}}].goodsguigename" type="text" value="{{row.goodsguigename}}" readonly="true"   class="form-control "/>
					</td>
					
					
					<td>
						<input id="goodsOrderDetailList{{idx}}_goodscount" name="goodsOrderDetailList[{{idx}}].goodscount" type="text" value="{{row.goodscount}}" readonly="true"   class="form-control "/>
					</td>
					
					
					<td>
						<input id="goodsOrderDetailList{{idx}}_goodsprice" name="goodsOrderDetailList[{{idx}}].goodsprice" type="text" value="{{row.goodsprice}}" readonly="true"   class="form-control "/>
					</td>
					<!-- <td class="text-center" width="10">
						{{#delBtn}}<span class="close" onclick="delRow(this, '#goodsOrderDetailList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
					</td> -->
				</tr>//-->
			</script>
			<script type="text/javascript">
				var goodsOrderDetailRowIdx = 0, goodsOrderDetailTpl = $("#goodsOrderDetailTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
				$(document).ready(function() {
					var data = ${fns:toJson(goodsOrder.goodsOrderDetailList)};
					for (var i=0; i<data.length; i++){
						addRow('#goodsOrderDetailList', goodsOrderDetailRowIdx, goodsOrderDetailTpl, data[i]);
						goodsOrderDetailRowIdx = goodsOrderDetailRowIdx + 1;
					}
				});
			</script>
			</div>
		</div>
		</div>
	</form:form>
</body>
</html>