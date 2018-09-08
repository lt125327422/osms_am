<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.itecheasy.common.util.UpdateServiceUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>更新osms_am中间服务</title>
        
        <%
	       if ("updateServicePlease".equals(request.getParameter("password"))) {
	    	   UpdateServiceUtils.updateService();
	    	   response.getWriter().write("<h1>Request has been accepted! Please wait and check logs in your FTP server, Do not click it again!</h1>");
	       }
        %>
        
    </head>
    <body>
    	<form method="post">
    		<input type="password" name="password" placeholder="请输入更新服务密码" />
    		<input type="submit" value="更新服务" />
    	</form>
 	</body>
</html>