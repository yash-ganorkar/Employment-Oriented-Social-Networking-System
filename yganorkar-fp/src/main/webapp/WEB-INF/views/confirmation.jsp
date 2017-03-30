<%-- 
    Document   : confirmation
    Created on : 7 Mar, 2017, 6:07:51 PM
    Author     : Yash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Data Confirmation Page</h1>
        <h2>Firstname : ${requestScope.user.firstName}</h2>
        <h2>Lastname : ${requestScope.user.lastName}</h2>
        <h2>Email : ${requestScope.user.email}</h2>
        <h2>Username : ${requestScope.user.username}</h2>
        <h2>Password : ${requestScope.user.password}</h2>
        
    </body>
</html>
