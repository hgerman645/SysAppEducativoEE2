<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="sysappeduee.el.User"%>
<%@page import="sysappeduee.el.Rol"%>
<%@page import="java.util.ArrayList"%>
<% ArrayList<User> usuarios = (ArrayList<User>) request.getAttribute("usuarios");
    int numPage = 1;
    int numReg = 10;
    int countReg = 0;
    if (usuarios == null) {
        usuarios = new ArrayList();
    } else if (usuarios.size() > numReg) {
        double divNumPage = (double) usuarios.size() / (double) numReg;
        numPage = (int) Math.ceil(divNumPage);
    }
    String strTop_aux = request.getParameter("top_aux");
    int top_aux = 10;
    if (strTop_aux != null && strTop_aux.trim().length() > 0) {
        top_aux = Integer.parseInt(strTop_aux);
    }
%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Buscar Usuario</title>

    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <main class="container">   
            <h5>Buscar Usuario</h5>
            <form action="User" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>"> 
                <div class="row">
                    <div class="input-field col l4 s12">
                        <input  id="txtNombre" type="text" name="name">
                        <label for="txtNombre">Nombre</label>
                    </div>  
                    <div class="input-field col l4 s12">
                        <input  id="txtApellido" type="text" name="lastName">
                        <label for="txtApellido">Apellido</label>
                    </div> 
                    <div class="input-field col l4 s12">
                        <input id="txtTelephone" type="text" name="telephone">
                        <label for="txtTelephone">Telefono</label>
                    </div>
                    <div class="input-field col l4 s12">
                        <input  id="txtLogin" type="text" name="login">
                        <label for="txtLogin">Login</label>
                    </div>                    
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Rol/select.jsp">                           
                            <jsp:param name="idRol" value="0" />  
                        </jsp:include>                        
                    </div>
                    <div class="input-field col l4 s12">   
                        <jsp:include page="/Views/Shared/selectTop.jsp">
                            <jsp:param name="top_aux" value="<%=top_aux%>" />                        
                        </jsp:include>                        
                    </div> 
                </div>
                <div class="row">
                    <div class="col l12 s12">
                        <button type="sutmit" class="waves-effect waves-light btn teal darken-4"><i class="material-icons right">search</i>Buscar</button>
                        <a href="User?accion=create" title="Crear" class="btn-floating waves-effect waves-light btn blue"><i class="material-icons right">add</i>Crear</a>                          
                    </div>
                </div>
            </form>

            <div class="row">
                <div class="col l12 s12">
                    <div style="overflow: auto">
                        <table class="paginationjs">
                            <thead>
                                <tr>                                     
                                    <th>Nombre</th>  
                                    <th>Apellido</th> 
                                    <th>Telefono</th>
                                    <th>Login</th>  
                                    <th>Rol</th>   
                                    <th>Acciones</th>
                                </tr>
                            </thead>                       
                            <tbody>                           
                                <% for (User usuario : usuarios) {
                                        int tempNumPage = numPage;
                                        if (numPage > 1) {
                                            countReg++;
                                            double divTempNumPage = (double) countReg / (double) numReg;
                                            tempNumPage = (int) Math.ceil(divTempNumPage);
                                        }
                                %>
                                <tr data-page="<%= tempNumPage%>">                                    
                                    <td><%=usuario.getName()%></td>  
                                    <td><%=usuario.getLastName()%></td>
                                    <td><%=usuario.getTelephone()%></td>
                                    <td><%=usuario.getLogin()%></td>  
                                    <td><%=usuario.getRol().getName()%></td> 
                                    <td>
                                        <div style="display:flex">
                                             <a href="User?accion=edit&idUser=<%=usuario.getIdUser()%>" title="Modificar" class="btn-floating waves-effect waves-light btn green">
                                            <i class="material-icons">edit</i>
                                        </a>
                                        <a href="User?accion=details&idUser=<%=usuario.getIdUser()%>" title="Ver" class="btn-floating waves-effect waves-light btn blue">
                                            <i class="material-icons">description</i>
                                        </a>
                                        <a href="User?accion=delete&idUser=<%=usuario.getIdUser()%>" title="Eliminar" class="btn-floating waves-effect waves-light btn red">
                                            <i class="material-icons">delete</i>
                                        </a>    
                                        </div>                                                                    
                                    </td>                                   
                                </tr>
                                <%}%>                                                       
                            </tbody>
                        </table>
                    </div>                  
                </div>
            </div>             
            <div class="row">
                <div class="col l12 s12">
                    <jsp:include page="/Views/Shared/paginacion.jsp">
                        <jsp:param name="numPage" value="<%= numPage%>" />                        
                    </jsp:include>
                </div>
            </div>
        </main>
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>
