/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sysappeduee2.ui.utils;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cliente
 */
public class Utilidad {
     public static String getParameter(HttpServletRequest request, String pKey, String pDefault) {
        String result = "";
        
        String value = request.getParameter(pKey); 
        if (value != null && value.trim().length() > 0) { 
            result = value; 
        } else {
            result = pDefault; 
        }
        return result; 
    }

    /**
     * En este método vamos a direccionar al jsp error en el caso que suceda una
     * exception en los servlet
     *
     * @param pError en este parámetro vamos a recibir el error que sucedio en
     * un servlet
     * @param request en este parámetro vamos a recibir el request de un servlet
     * @param response en este parámetro vamos a recibir el response de un
     * servlet o jsp para direccionar al jsp error
     * @throws javax.servlet.ServletException devolver una exception de un
     * servlet
     * @throws java.io.IOException devolver una exception al leer o escribir un
     * archivo
     */
    public static void enviarError(String pError, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", pError); 
        request.getRequestDispatcher("Views/Shared/error.jsp").forward(request, response); // direccionar al jsp error 
    }

    /**
     *
     * @param request en este parámetro vamos a recibir el request de un servlet
     * o jsp
     * @param pStrRuta en este parámetro vamos a recibir la ruta que se va
     * concatenar a la ruta raiz de la aplicacion web
     * @return String va a devolver la ruta completa del archivo css, js, imagen
     * que se desea enviar a un nevegador web
     */
    public static String obtenerRuta(HttpServletRequest request, String pStrRuta) {
        return request.getContextPath() + pStrRuta; // concatenar la ruta raiz de la aplicacion, mas la ruta del archivo css, js o imagen 
    }
}
