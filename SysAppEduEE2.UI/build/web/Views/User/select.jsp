<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sysappeduee.el.User"%>
<%@page import="sysappeduee.dal.UserDAL"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<User> usuarios = UserDAL.getAll();
    int id = Integer.parseInt(request.getParameter("idUser"));
%>
<select id="slRol" name="idUser">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (User usuario : usuarios) {%>
    <option <%=(id == usuario.getIdUser()) ? "selected" : ""%>  value="<%=usuario.getIdUser()%>"><%= usuario.getLogin()%></option>
    <%}%>
</select>
<label for="iduser">User</label>

