<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sysappeduee.el.User"%>
<% User usuario = (User) request.getAttribute("usuario");%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Editar Usuario</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Editar Usuario</h5>
            <form action="User" method="post" onsubmit="return  validarFormulario()">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <input type="hidden" name="idUser" value="<%=usuario.getIdUser()%>">  
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="name" value="<%=usuario.getName()%>" required class="validate" maxlength="50">
                        <label for="txtNombre">Nombre</label>
                    </div>                       
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" name="lastName" value="<%=usuario.getLastName()%>" required class="validate" maxlength="50">
                        <label for="txtApellido">Apellido</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input  id="txtTelephone" type="text" name="telephone" value="<%=usuario.getTelephone()%>" required class="validate" maxlength="10">
                        <label for="txtTelephone">Telefono</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtLogin" type="text" name="login" value="<%=usuario.getLogin()%>" required  class="validate" maxlength="60">
                        <label for="txtLogin">Login</label>
                    </div>                     
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Rol/select.jsp">                           
                            <jsp:param name="idRol" value="<%=usuario.getIdRol() %>" />  
                        </jsp:include>  
                        <span id="slRol_error" style="color:red" class="helper-text"></span>
                    </div>
                </div>

                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn blue"><i class="material-icons right">save</i>Guardar</button>
                        <a href="User" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>                          
                    </div>
                </div>
            </form>          
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />   
        <script>
            function validarFormulario() {
                var result = true;
                var slRol = document.getElementById("slRol");
                var slRol_error = document.getElementById("slRol_error");
                if (slRol.value == 0) {
                    slRol_error.innerHTML = "El Rol es obligatorio";
                    result = false;
                } else {
                    slRol_error.innerHTML = "";
                }
                return result;
            }
        </script>
    </body>
</html>
