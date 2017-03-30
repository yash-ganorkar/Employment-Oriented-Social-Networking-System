<%-- 
    Document   : error
    Created on : 7 Mar, 2017, 7:14:06 PM
    Author     : Yash
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style>
            h1,h2{
                color: red;
            }
        </style>
    </head>
    <body>
        <h1>Error</h1>
        <h2>${requestScope.error}</h2>
    </body>
</html>
