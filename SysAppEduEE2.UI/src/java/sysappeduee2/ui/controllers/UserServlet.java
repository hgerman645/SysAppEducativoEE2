/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package sysappeduee2.ui.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import sysappeduee.dal.UserDAL;
import sysappeduee.dal.RolDAL;
import sysappeduee2.ui.utils.*;
import sysappeduee.el.User;
import sysappeduee.el.Rol;

/**
 *
 * @author cliente
 */
@WebServlet(name = "UserServlet", urlPatterns = {"/User"})
public class UserServlet extends HttpServlet {
    // <editor-fold defaultstate="collapsed" desc="Metodos para procesar las solicitudes get o post del Servlet">
    /**
     * En este método vamos a obtener la información enviada, en una peticion get o post, obteniendo los datos de los parámetros enviados de un formulario o la url del navegador, enviar esa información a una instancia de la entidad Usuario
     *
     * @param request en este parámetro vamos a recibir el request de la peticion get o post enviada al servlet Usuario
     * @return Usuario devolver la instancia de la entidad Usuario con los valores obtenidos del request
     */
    private User getUser(HttpServletRequest request) {
        // Obtener el parámetro accion del request
        String accion = Utilidad.getParameter(request, "accion", "index");
        User usuario = new User();

        usuario.setName(Utilidad.getParameter(request, "name", ""));

        usuario.setLastName(Utilidad.getParameter(request, "lastName", ""));

        usuario.setTelephone(Utilidad.getParameter(request, "telephone", ""));

        usuario.setLogin(Utilidad.getParameter(request, "login", ""));

        usuario.setIdRol(Integer.parseInt(Utilidad.getParameter(request, "idRol", "0")));

        if (accion.equals("index")) {
            // Obtener el parámetro top_aux del request  y asignar ese valor a la propiedad Top_aux de Usuario.
            usuario.setTop_aux(Integer.parseInt(Utilidad.getParameter(request, "top_aux", "10")));
            usuario.setTop_aux(usuario.getTop_aux() == 0 ? Integer.MAX_VALUE : usuario.getTop_aux());
        }
        if (accion.equals("login") || accion.equals("create") || accion.equals("cambiarpass")) {
            // Obtener el parámetro password del request  y asignar ese valor a la propiedad Password de Usuario.
            usuario.setPassword(Utilidad.getParameter(request, "password", ""));

            usuario.setConfirmPassword_aux(Utilidad.getParameter(request, "confirmPassword_aux", ""));
            if (accion.equals("cambiarpass")) {
                usuario.setIdUser(Integer.parseInt(Utilidad.getParameter(request, "idUser", "0")));
            }
        } else {
            // Obtener el parámetro id del request  y asignar ese valor a la propiedad Id de Usuario.
            usuario.setIdUser(Integer.parseInt(Utilidad.getParameter(request, "idUser", "0")));
        }
        // Devolver la instancia de la entidad Usuario con los valores obtenidos del request.
        return usuario;
    }

    /**
     * En este método se ejecutara cuando se envie una peticion get al servlet Usuario, y el parametro accion sea igual index. Este método se encargara de enviar los datos de los usuarios al jsp de index de Usuario.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion get enviada al servlet Usuario
     * @param response en este parámetro vamos a recibir el response de la peticion get enviada al servlet Usuario que utlizaremos para enviar el jsp
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doGetRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User usuario = new User(); 
            usuario.setTop_aux(10); 
            ArrayList<User> usuarios = UserDAL.searchIncludeRol(usuario);
            
            request.setAttribute("usuarios", usuarios);
            request.setAttribute("top_aux", usuario.getTop_aux());
            request.getRequestDispatcher("Views/User/index.jsp").forward(request, response);
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response); 
        }
    }

    /**
     * En este método se ejecutara cuando se envie una peticion post, al servlet Usuario , y el parámetro accion sea igual index. Este método se encargara de enviar los datos de los usuarios al jsp de index de Usuario
     *
     * @param request en este parámetro vamos a recibir el request de la peticion post enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doPostRequestIndex(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User usuario = getUser(request); // Llenar la instancia de Usuario con los parámetros enviados en el request.
            // Ir a la capa de acceso a datos y buscar los registros de Usuario y asociar Rol.
            ArrayList<User> usuarios = UserDAL.searchIncludeRol(usuario);
            // Enviar los usuarios al jsp utilizando el request.setAttribute con el nombre del atributo usuario.
            request.setAttribute("usuarios", usuarios);
            // Enviar el Top_aux de Usuario al jsp utilizando el request.setAttribute con el nombre del atributo top_aux.
            request.setAttribute("top_aux", usuario.getTop_aux());
            request.getRequestDispatcher("Views/User/index.jsp").forward(request, response); // Direccionar al jsp index de Usuario.
        } catch (Exception ex) {
            Utilidad.enviarError(ex.getMessage(), request, response); // Enviar al jsp de error si hay un Exception.
        }
    }

    /**
     * En este método se ejecutara cuando se envie una peticion get al servlet Usuario, y el parámetro accion sea igual create.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion get enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doGetRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // direccionar al jsp create de Usuario
        request.getRequestDispatcher("Views/User/create.jsp").forward(request, response);
    }

    /**
     * En este método se ejecutara cuando se envie una peticion post al servlet Usuario , y el parámetro accion sea igual create.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion post enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doPostRequestCreate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User usuario = getUser(request); // Llenar la instancia de Usuario con los parámetros enviados en el request
            // Enviar los datos de Usuario a la capa de accesoa a datos para que lo almacene en la base de datos el registro.
            int result = UserDAL.create(usuario);
            if (result != 0) { // Si el result es diferente a cero significa que los datos fueron ingresados correctamente.
                // Enviar el atributo accion con el valor index al jsp de index
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response); // Ir al metodo doGetRequestIndex para que nos direcciones al jsp index
            } else {
                // Enviar al jsp de error el siguiente mensaje. No se logro registrar un nuevo registro
                Utilidad.enviarError("No se logro registrar un nuevo registro", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception
            Utilidad.enviarError(ex.getMessage(), request, response);
        }

    }

    /**
     * En este método obtiene por Id un Usuario desde la capa de acceso a datos el Id se captura del request que se envio al servlet de Usuario
     *
     * @param request en este parámetro vamos a recibir el request de la peticion get o post enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void requestGetById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User usuario = getUser(request); // Llenar la instancia de Usuario con los parámetros enviados en el request.
            User usuario_result = UserDAL.getById(usuario); // Obtener desde la capa de acceso a datos el usuario por Id.
            if (usuario_result.getIdUser() > 0) { // Si el Id es mayor a cero.
                Rol rol = new Rol();
                rol.setIdRol(usuario_result.getIdRol());
                // Obtener desde la capa de acceso a datos el rol por Id y asignarlo al usuario.
                usuario_result.setRol(RolDAL.getById(rol));
                // Enviar el atributo usuario con el valor de los datos del usuario de nuestra base de datos a un jsp
                request.setAttribute("usuario", usuario_result);
            } else {
                // Enviar al jsp de error el siguiente mensaje. El Id: ? no existe en la tabla de Usuario
                Utilidad.enviarError("El Id:" + usuario_result.getIdUser() + " no existe en la tabla de Usuario", request, response);
            }
        } catch (Exception ex) {
            // enviar al jsp de error si hay un Exception
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    /**
     * En este método se ejecutara cuando se envie una peticion get al servlet Usuario , y el parámetro accion sea igual edit.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion get enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doGetRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Enviar el usuario al jsp de edit que se obtiene por Id
        requestGetById(request, response);
        // Direccionar al jsp edit de Usuario
        request.getRequestDispatcher("Views/User/edit.jsp").forward(request, response);
    }

    /**
     * En este método se ejecutara cuando se envie una peticion post al servlet Usuario , y el parámetro accion sea igual edit.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion post enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doPostRequestEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User usuario = getUser(request); // Llenar la instancia de Usuario con los parámetros enviados en el request.
            // Enviar los datos de Usuario a la capa de accesoa a datos para modificar el registro.
            int result = UserDAL.update(usuario);
            if (result != 0) {
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response); // Ir al metodo doGetRequestIndex para que nos direcciones al jsp index.
            } else {
                // Enviar al jsp de error el siguiente mensaje. No se logro actualizar el registro.
                Utilidad.enviarError("No se logro actualizar el registro", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    /**
     * En este método se ejecutara cuando se envie una peticion get al servlet Usuario , y el parámetro accion sea igual details.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion get enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doGetRequestDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Enviar el usuario al jsp de details que se obtiene por Id.
        requestGetById(request, response);
        // Direccionar al jsp details de Usuario.
        request.getRequestDispatcher("Views/User/details.jsp").forward(request, response);
    }

    /**
     * En este método se ejecutara cuando se envie una peticion get al servlet Usuario , y el parámetro accion sea igual delete.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion get enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doGetRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Enviar el usuario al jsp de delete que se obtiene por Id.
        requestGetById(request, response);
        // Direccionar al jsp delete de Usuario.
        request.getRequestDispatcher("Views/User/delete.jsp").forward(request, response);
    }

    /**
     * En este método se ejecutara cuando se envie una peticion post al servlet Usuario , y el parámetro accion sea igual delete.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion post enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doPostRequestDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User usuario = getUser(request); // Llenar la instancia de Usuario con los parámetros enviados en el request.
            // Enviar los datos de Usuario a la capa de accesoa a datos para que elimine el registro.
            int result = UserDAL.delete(usuario);
            if (result != 0) { // Si el result es diferente a cero significa que los datos fueron eliminados correctamente.
                // Enviar el atributo accion con el valor index al jsp de index.
                request.setAttribute("accion", "index");
                doGetRequestIndex(request, response);  // Ir al método doGetRequestIndex para que nos direccione al jsp index.
            } else {
                // Enviar al jsp de error el siguiente mensaje. No se logro eliminar el registro.
                Utilidad.enviarError("No se logro eliminar el registro", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception.
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    /**
     * En este método se ejecutara cuando se envie una peticion get al servlet Usuario , y el parámetro accion sea igual login.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion get enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doGetRequestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SessionUser.cerrarSession(request); // Cerrar la session del usuario autenticado en el sistema 
        // Direccionar al jsp login de Usuario.
        request.getRequestDispatcher("Views/User/login.jsp").forward(request, response);
    }

    /**
     * En este método se ejecutara cuando se envie una peticion post al servlet Usuario , y el parámetro accion sea igual login.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion post enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doPostRequestLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User usuario = getUser(request); // Llenar la instancia de Usuario con los parámetros enviados en el request.
            // Ir a la capa de accesoa a datos para que autorizar el usuario.
            User usuario_auth = UserDAL.login(usuario);
            // Confirmar que el usuario cumple con la autorizacion para entrar al sistema.
            if (usuario_auth.getIdUser() != 0 && usuario_auth.getLogin().equals(usuario.getLogin())) {
                Rol rol = new Rol();
                rol.setIdRol(usuario_auth.getIdRol());
                // Obtener desde la capa de acceso a datos el rol por Id y asignarlo al usuario.
                usuario_auth.setRol(RolDAL.getById(rol));
                // Autenticar el usuario en la aplicacion web, mediante variables de session.
                SessionUser.autenticarUser(request, usuario_auth);
                response.sendRedirect("Home"); // Direccionar al Servlet de Home para ir al jsp index en la carpeta principal 
            } else {
                // Enviar el atributo error con el valor Credenciales incorrectas al jsp de login.
                request.setAttribute("error", "Credenciales incorrectas");
                // Enviar el atributo accion con el valor login al jsp de login.
                request.setAttribute("accion", "login");
                doGetRequestLogin(request, response); // Ir al método doGetRequestLogin para que nos direccione al jsp login.
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception.
            request.setAttribute("error", ex.getMessage());
        }
    }

    /**
     * En este método se ejecutara cuando se envie una peticion get al servlet Usuario , y el parámetro accion sea igual cambiarpass.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion get enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doGetRequestCambiarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User usuario = new User();
            // Obtener el login de la variable de session y asignarlo a la instancia de Usuario.
            usuario.setLogin(SessionUser.getUser(request));
            // Buscar el  Usuario por Login en la capa de acceso a datos  
            User usuario_result = UserDAL.search(usuario).get(0);
            if (usuario_result.getIdUser() > 0) { // Si el Id de Usuario es mayor a cero
                // Enviar el atributo usuario con el valor del usuario que se obtuvo por Login
                request.setAttribute("usuario", usuario_result);
                // Direccionar al jsp cambiarPassword de Usuario.
                request.getRequestDispatcher("Views/User/cambiarPassword.jsp").forward(request, response);
            } else {
                // Enviar al jsp de error el siguiente mensaje.El Id [Id] no existe en la tabla de Usuario.
                Utilidad.enviarError("El Id:" + usuario_result.getIdUser() + " no existe en la tabla de Usuario", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception.
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }

    /**
     * En este método se ejecutara cuando se envie una peticion post al servlet Usuario , y el parámetro accion sea igual cambiarpass.
     *
     * @param request en este parámetro vamos a recibir el request de la peticion post enviada al servlet Usuario
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    private void doPostRequestCambiarPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User usuario = getUser(request); // Llenar la instancia de Usuario con los parámetros enviados en el request.
            String passActual = Utilidad.getParameter(request, "passwordActual", ""); // Obtener el parámetro passwordActual del request
            // Llamamos el método cambiarPassword de la capa de acceso a datos para cambiar el password actual del usuario que inicio session.
            int result = UserDAL.changePassword(usuario, passActual);
            if (result != 0) { // Si el result es diferente a cero significa que se cambio el password correctamente.
                // Direccionar al Servlet de Usuario enviando el parámetro accion igual login. Esto hará que nos pida el sistema
                // volver iniciar session con el nuevo password.
                response.sendRedirect("User?accion=login");
            } else {
                // Enviar al jsp de error el siguiente mensaje.No se logro cambiar el password.
                Utilidad.enviarError("No se logro cambiar el password", request, response);
            }
        } catch (Exception ex) {
            // Enviar al jsp de error si hay un Exception.
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Este método es un override al método de la clase HttpServlet para recibir todas las peticiones get que se realice al Servlet Usuario
     *
     * @param request en este parámetro vamos a recibir el request de la peticion get enviada al servlet Usuario
     * @param response en este parámetro vamos a recibir el response de la peticion get enviada al servlet Usuario que utilizaremos para enviar el jsp al navegador web
     * @throws ServletException devolver una exception de un servlet
     * @throws IOException devolver una exception al leer o escribir un archivo
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener el parámetro accion del request
        String accion = Utilidad.getParameter(request, "accion", "index");
        if (accion.equals("login")) { // Si accion es igual a login.
            // Enviar el atributo accion al jsp de login.
            request.setAttribute("accion", accion);
            doGetRequestLogin(request, response); // Ir al método doGetRequestLogin.
        } else {
            SessionUser.authorize(request, response, () -> {
                switch (accion) {
                    case "index":
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response); // Ir al método doGetRequestIndex.
                        break;
                    case "create":
                        request.setAttribute("accion", accion);
                        doGetRequestCreate(request, response); // Ir al método doGetRequestCreate.
                        break;
                    case "edit":
                        request.setAttribute("accion", accion);
                        doGetRequestEdit(request, response); // Ir al método doGetRequestEdit.
                        break;
                    case "delete":
                        request.setAttribute("accion", accion);
                        doGetRequestDelete(request, response); // Ir al método doGetRequestDelete.
                        break;
                    case "details":
                        request.setAttribute("accion", accion);
                        doGetRequestDetails(request, response); // Ir al método doGetRequestDetails.
                        break;
                    case "cambiarpass":
                        // Enviar el atributo accion al jsp de cambiarPassword.
                        request.setAttribute("accion", accion);
                        doGetRequestCambiarPassword(request, response); // Ir al método doGetRequestCambiarPassword.
                        break;
                    default:
                        // Enviar el atributo accion al jsp de index.
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response); // Ir al método doGetRequestIndex.
                }
            });
        }
    }

    /**
     * Este método es un override al método de la clase HttpServlet para recibir todas las peticiones post que se realice al Servlet Usuario
     *
     * @param request en este parámetro vamos a recibir el request de la peticion post enviada al servlet Usuario
     * @param response en este parámetro vamos a recibir el response de la peticion get enviada al servlet Usuario que utlizaremos para enviar el jsp al navegador web
     * @throws ServletException devolver una exception de un servlet
     * @throws IOException devolver una exception al leer o escribir un archivo
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = Utilidad.getParameter(request, "accion", "index");
        if (accion.equals("login")) { 
            request.setAttribute("accion", accion);
            doPostRequestLogin(request, response);  // Ir al método doGetRequestLogin.
        } else {
            SessionUser.authorize(request, response, () -> {
                switch (accion) {
                    case "index":
                        request.setAttribute("accion", accion);
                        doPostRequestIndex(request, response);  // Ir al metodo doPostRequestIndex.
                        break;
                    case "create":
                        request.setAttribute("accion", accion);
                        doPostRequestCreate(request, response);  // Ir al metodo doPostRequestCreate.
                        break;
                    case "edit":
                        request.setAttribute("accion", accion);
                        doPostRequestEdit(request, response);  // Ir al metodo doPostRequestEdit.
                        break;
                    case "delete":
                        request.setAttribute("accion", accion);
                        doPostRequestDelete(request, response);  // Ir al metodo doPostRequestDelete.
                        break;
                    case "cambiarpass":
                        request.setAttribute("accion", accion);
                        doPostRequestCambiarPassword(request, response);  // Ir al metodo doPostRequestCambiarPassword.
                        break;
                    default:
                        request.setAttribute("accion", accion);
                        doGetRequestIndex(request, response);  // Ir al metodo doGetRequestIndex.
                }
            });
        }
    }
    // </editor-fold>
}
