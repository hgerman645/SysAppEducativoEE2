<%-- 
    Document   : edit
    Created on : 29 ago 2022, 8:36:26
    Author     : Reina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sysappeducationee.el.Week"%>
<% Week week = (Week) request.getAttribute("week");%>
<!DOCTYPE html>
<html>
   <head>
         <jsp:include page="/Views/Shared/title.jsp" />
        <title>Edit Semana</title>
      </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Edit Semana</h5>
            <form action="Week" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="id" value="<%=week.getId()%>">  
                 <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtSemanadelNombre" type="text" name="semanadelnombre" value="<%=week.getNameWeek()%>" required class="validate" maxlength="30">
                        <label for="txtSemanadelNombre">Semana del Nombre</label>
                    </div>             
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Week" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>
