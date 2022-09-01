<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sysappeduee.el.Rol"%>
<%@page import="sysappeduee.dal.RolDAL"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<Rol> roles = RolDAL.getAll();
    int id = Integer.parseInt(request.getParameter("idRol"));
%>
<select id="slRol" name="idRol">
    <option <%=(id == 0) ? "selected" : ""%>  value="0">SELECCIONAR</option>
    <% for (Rol rol : roles) {%>
    <option <%=(id == rol.getIdRol()) ? "selected" : ""%>  value="<%=rol.getIdRol()%>"><%= rol.getName()%></option>
    <%}%>
</select>
<label for="idRol">Rol</label>
