<%-- 
    Document   : datails
    Created on : 29 ago 2022, 8:36:52
    Author     : Alumno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sysappeducationee.el.Week"%>
<% Week week = (Week) request.getAttribute("week");%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Materia</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Detalle de Materia</h5>
            <div class="row">
                <div class="input-field col l4 s12">
                    <input disabled  id="txtNombre" type="text" value="<%=week.getNameWeek()%>">
                    <label for="txtNombre">Nombre</label>
                </div>                                         
            </div>
            <div class="row">
                <div class="col l12 s12">
                    <a href="Week?accion=edit&id=<%=week.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>                        
                    <a href="Week" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                </div>
            </div>         
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>

