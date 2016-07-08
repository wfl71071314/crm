<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" pageEncoding="UTF-8"%>

<%@ include file="/commons/common.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>添加角色</title>
</head>

<body class="main">
	
	<span class="page_title">系统角色添加</span>
	<div class="button_bar">
		<button class="common_button" onclick="javascript:history.go(-1);">
			返回
		</button>
		<button class="common_button" onclick="document.forms[1].submit();">
			保存
		</button>
	</div>
	
	<form:form action="${ctp}/role/create" method="POST" modelAttribute="role">
		<c:if test="${role.id!=null }">
			<form:hidden path="id"/>
		</c:if>
		<table class="query_form_table" border="0" cellPadding="3"
			cellSpacing="0">
			<tr>
				<th>
					角色名称
				</th>
				<td>
					<form:input path="name"/>
				</td>
				<th>
					角色描述
				</th>
				<td>
					<form:input path="description"/>
				</td>
			</tr>
			<tr>
				<th>
					状态
				</th>
				<td>
					<% 
						Map<String, String> map = new HashMap<String, String>();
						map.put("1", "有效");
						map.put("0", "无效");
						request.setAttribute("enabled", map);
					%>
					<form:select path="enabled" items="${enabled}"></form:select>
				</td>
			</tr>	
		</table>
	</form:form>
</body>
</html>
