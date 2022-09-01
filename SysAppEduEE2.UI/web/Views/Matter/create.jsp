<%-- 
    Document   : create
    Created on : 29 ago 2022, 10:43:43
    Author     : Alumno
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="/Views/Shared/title.jsp" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Materia</title>
    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />
        <main class="container">
            <h5>Crear Materia</h5>
            <form action="Matter" method="post">
                <input type="hidden" name="accion" value="<%=request.getAttribute("accion")%>" >
                <div class="row">
                    <div class="input-field col l4 s12">
                        <imput id="txtNombre" type="text" name="NameMatter" required class="vlaidate" maxlengeth="30" >
                            <<label for="txtNombre">Nombre</label>
                    <div>
                <div/>
                <div class="row">
                    <div class="col l12 s12" >
                        <button type="sutmit" class="wawes-effect wawes-light btn blue"> <i class="material-icons right"> save <i/> Guardar </button>
                        <a href="Matter" class="waves-effect waves-light btn blue"><i class="material-icons right">list</i>Cancelar</a>
                    </div>
                </div>
            </form>
        </main>
          <jsp:include page="/Views/Shared/footerBody.jsp" />       
    </body>
</html>
