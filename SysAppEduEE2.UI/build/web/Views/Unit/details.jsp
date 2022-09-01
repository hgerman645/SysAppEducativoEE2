<%@page import="sysappeducationee.el.Unit"%>
<% Unit unite = (Unit) request.getAttribute("unite");%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Detalle de Unidad</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/title.jsp"/>
        <<main class ="container">
            <h5> Detalles de Unidad</h5>
            <div class="row">
                <div class="input-field col l4 s12">
                    <input disabled  id="txtNombre" type="text" value="<%=unite.getNameUnit()%>">
                    <label for="txtNombre">Nombre</label>
                </div>
            </div>
            <div class="row">
                <div class="col l12 s12">
                    <a href="Role?accion=edit&id=<%=unite.getId()%>" class="waves-effect waves-light btn blue"><i class="material-icons right">edit</i>Ir modificar</a>                        
                    <a href="Role" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                </div>
            </div> 
        </main>
        <jsp:include page="/Views/Shared/FooterBody.jsp"/>
    </body>
</html>