<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ include file="/commons/common.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<script type="text/javascript">
		$(function(){
			$("img[id^='delete-']").click(function(){
				var id=$(this).attr("id").split("-")[1];
			 	var lable=$(this).next(":hidden").val();
				var flag=confirm("确定要删除"+lable+"吗？");
				if(!flag){
					return;
				}
				var url="${ctp}/user/delete-ajax";
				var args={"id":id,"time":new Date()};
				$.post(url,args,function(data){
					alert("删除成功！");
					$("#user-"+id).remove();
				},"text"); 
			});
		});
	</script>
</head>

<body class="main">
	<div class="button_bar">
		<button class="common_button" id="new" onclick="window.location.href='${ctp}/user/create'">新建</button>
		<button class="common_button" onclick="document.forms[1].submit();">
			查询
		</button>
	</div>
	<form action="${ctp}/user/list2">
		<div class="page_title">
			用户管理
		</div>
		
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th class="input_title">
					用户名
				</th>
				<td class="input_content">
					<input type="text" name="search_LIKES_name" />
				</td>
				<th class="input_title">
					状态
				</th>
				<td class="input_content">
					<select name="search_EQS_enabled">
						<option value="">
							全部
						</option>
						<option value="1">
							正常
						</option>
						<option value="0">
							已删除
						</option>
					</select>
				</td>
			</tr>
		</table>
		<!-- 列表数据 -->
		<br />
		
		<c:if test="${page != null && page.totalElements > 0 }">
			<table class="data_list_table" border="0" cellPadding="3"
				cellSpacing="0">
				<tr>
					<th class="data_title" style="width: 40px;">
						编号
					</th>
					<th class="data_title" style="width: 50%;">
						用户名
					</th>
					<th class="data_title" style="width: 20%;">
						状态
					</th>
					<th class="data_title">
						操作
					</th>
				</tr>
				<c:forEach var="user" items="${page.content }">
					<tr  id="user-${user.id}">
						<td class="data_cell" style="text-align: right; padding: 0 10px;">
						${user.id}
						</td>
						<td class="data_cell" style="text-align: center;">
						${user.name}
						</td>
						<td class="data_cell" >
						${user.enabled == 1 ? "有效" : "无效"}
						</td>
						<td class="data_cell">
							<img onclick="window.location.href='create?id=${user.id}'" 
							class="op_button" src="${ctp}/static/images/bt_edit.gif" title="编辑" />
							<img id="delete-${user.id}"
							title="删除" src="${ctp}/static/images/bt_del.gif" class="op_button" />
							<input type="hidden" value="${user.name}"/>
					
						</td>
					</tr>
				</c:forEach>
			</table>
			<tags:pageTag page="${pageNo}" queryString="${queryString}"></tags:pageTag>
		</c:if>
		<c:if test="${page == null || page.totalElements == 0 }">
			没有任何数据
		</c:if>
	</form>
</body>
</html>
