<%@page import="sysappeduee2.ui.utils.*"%>
<% if (SessionUser.isAuth(request) == false) {
         response.sendRedirect("User?accion=login");
    }
%>
<!DOCTYPE html>
<html>
    <head>        
        <jsp:include page="/Views/Shared/title.jsp" />
        <title>Home</title>
        <link rel="stylesheet" href="wwwroot/css/darkmode.css">
          <link rel="stylesheet" href="css/style.css">

    </head>
    <body>
        <jsp:include page="/Views/Shared/headerBody.jsp" />  
        <jsp:include page="/Views/Shared/footerBody.jsp" />      
    </body>
</html>
