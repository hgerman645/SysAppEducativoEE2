<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="sysappeducationee.el.Unit"%>
<% Unit unite =(Unit) request.getAttribute("unite");%>

<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Rol</title>

    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Unit</h5>
            <form action="Unit" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>">   
                <input type="hidden" name="id" value="<%=unite.getId()%>">   
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="NameUnit" value="<%=unite.getNameUnit()%>" required class="validate" maxlength="30">
                        <label for="txtNombre">Nombre</label>
                    </div>                                       
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="Role" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>
