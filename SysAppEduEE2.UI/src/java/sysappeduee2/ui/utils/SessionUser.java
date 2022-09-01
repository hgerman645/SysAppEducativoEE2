/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysappeduee2.ui.utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sysappeduee.el.User;
/**
 *
 * @author cliente
 */
public class SessionUser {
     public static void autenticarUser(HttpServletRequest request, User pUsuario) {
        HttpSession session = (HttpSession) request.getSession();
        session.setMaxInactiveInterval(3600); 
        session.setAttribute("auth", true); 
        session.setAttribute("user", pUsuario.getLogin());
        session.setAttribute("rol", pUsuario.getRol().getName()); 
    }

    /**
     * En este método vamos obtener el login del usuario que inicio session
     *
     * @param request en este parámetro vamos a recibir el request de un servlet
     * o jsp
     * @return boolean true si el usuario ha iniciado session, false si no ha
     * iniciado session
     */
    public static boolean isAuth(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        boolean auth = session.getAttribute("auth") != null ? (boolean) session.getAttribute("auth") : false;
        return auth;
    }

    /**
     * En este método vamos obtener el login del usuario que inicio session.
     *
     * @param request en este parámetro vamos a recibir el request de un servlet
     * o jsp
     * @return String el login del usuario
     */
    public static String getUser(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String user = "";
        if (SessionUser.isAuth(request)) {
            user = session.getAttribute("user") != null ? (String) session.getAttribute("user") : "";
        }
        return user;
    }

    /**
     * En este método vamos obtener el rol del usuario que inicio session
     *
     * @param request en este parámetro vamos a recibir el request de un servlet
     * o jsp
     * @return String el rol del usuario
     */
    public static String getRol(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        String user = "";
        if (SessionUser.isAuth(request)){
            user = session.getAttribute("rol") != null ? (String) session.getAttribute("rol") : "";
        }
        return user;
    }

    /**
     * Este método lo vamos a utilizar, para validar si un usuario esta
     * autorizado, a realizar x accion en un servlet o jsp.
     *
     * @param request en este parámetro vamos a recibir el request de un servlet
     * o jsp
     * @param response en este parámetro vamos a recibir el response de un
     * servlet, para direccionar al servlet de usuario accion login, en el caso
     * que no tenga permisos.
     * @param pIAuthorize en este parámetro se enviará una expresión Lambda, que
     * cumpla con la firma del metodo, que tiene declarado en la interface de
     * IAuthorize.
     * @throws javax.servlet.ServletException devolver una exception de un
     * servlet
     * @throws java.io.IOException devolver una exception al leer o escribir un
     * archivo
     */
    public static void authorize(HttpServletRequest request, HttpServletResponse response, IAuthorize pIAuthorize) throws ServletException, IOException {
        if (SessionUser.isAuth(request)) { // verificar si el usuario ha iniciado session 
            pIAuthorize.authorize(); // ejecutar la expresión Lambda, si el usuario ha sido autorizado en el sistema
        } else {
            response.sendRedirect("User?accion=login"); // direccionar al servlet de Usuario accion login, en el caso que no tenga permiso el usuario
        }
    }

    /**
     * Este método lo vamos utilizar para cerrar la session de un usuario
     * autorizado en el sistema.
     *
     * @param request en este parámetro vamos a recibir el request de un servlet
     * o jsp
     */
    public static void cerrarSession(HttpServletRequest request) {
        HttpSession session = (HttpSession) request.getSession();
        session.invalidate();
    }
}
