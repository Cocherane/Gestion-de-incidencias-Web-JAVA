<%@ page import="models.Usuario" %>
<%@ page import="models.Tecnico" %><%--
  Created by IntelliJ IDEA.
  User: ariel
  Date: 18/07/2022
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // set user value session
    Object user = session.getAttribute("user");
    if( user instanceof Usuario){
        response.sendRedirect("Usuario.jsp");

    } else if ( user instanceof Tecnico) {
        response.sendRedirect("Tecnico.jsp");

    }else {
        response.sendRedirect("Admin.jsp");

    }
%>
