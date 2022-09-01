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

import java.util.*;
import sysappeduee.dal.*;
import sysappeduee.el.*;
import sysappeduee2.ui.utils.*;

/**
 *
 * @author mg200
 */
@WebServlet(name = "MatterServlet", urlPatterns = {"/MatterServlet"})
public class MatterServlet extends HttpServlet {

    private Matter GetMatter(HttpServletRequest request){
        String accion = Utilidad.getParameter(request, "accion", "index");
        Matter matter = new Matter();
        if(accion.equals("create")==false){
            matter.setId(Integer.parseInt(Utilidad.getParameter(request, "Id", "0")));
        }
        matter.setNameMatter(Utilidad.getParameter(request, "NameMatter", ""));
        if(accion.equals("index")){
            matter.setTopAux(Integer.parseInt(Utilidad.getParameter(request, "TopAux", "10")));
            matter.setTopAux(matter.getTopAux()==0? Integer.MAX_VALUE : matter.getTopAux());
        }
        return matter;
    }
    
    private void DoGetRequestIndex(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        try {
            Matter matter = new Matter();
            matter.setTopAux(10);
            ArrayList<Matter> matters = MatterDAL.buscar(matter);
            request.setAttribute("matters", matters);
            request.setAttribute("TopAux", matter.getTopAux());
            request.getRequestDispatcher("Views/Matter/index.jsp").forward(request, response);
        }catch(Exception ex){
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void DoPostRequestIndex(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        try {
            Matter matter = new Matter();
            ArrayList<Matter> matters= MatterDAL.buscar(matter);
            request.setAttribute("matters", matters);
            request.setAttribute("TopAux", matter.getTopAux());
            request.getRequestDispatcher("Views/Matter/index.jsp").forward(request, response);
        }catch(Exception ex){
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void DoGetRequestCreate(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        request.getRequestDispatcher("Views/Matter/create.jsp").forward(request, response);
    }
    
    private void DoPostRequestCreate(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        try {
             Matter matter = GetMatter(request);
             int result = MatterDAL.create(matter);
             if(result !=0){
                 request.setAttribute("acccion", "index");
                 DoGetRequestIndex(request, response);
             }else{
                 Utilidad.enviarError("No se logro resgistrar un nuevo registro", request, response);
             }
        }catch (Exception ex){
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void RequestGetById(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        try {
            Matter matter = GetMatter(request);
            Matter Matter_Result = MatterDAL.getById(matter);
            if(Matter_Result.getId()>0){
                request.setAttribute("matter", Matter_Result);
            }else{
                Utilidad.enviarError("El Id: " + matter.getId() + " No existe en la Tabla Materia. ", request, response);
            }
        }catch(Exception ex){
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void DoGetRequestEdit(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        RequestGetById(request, response);
        request.getRequestDispatcher("Views/Matter/edit.jsp").forward(request, response);
    }
    
    private void DoPostRequestEdit(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        try{
            Matter matter = GetMatter(request);
            int Result = MatterDAL.Update(matter);
            if(Result !=0){
                request.setAttribute("accion", "index");
                DoGetRequestIndex(request, response);
            }else{
                Utilidad.enviarError("No se logro actualixar el registro. ", request, response);
            }
        }catch (Exception ex){
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    private void DoGetRequestDetails(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        RequestGetById(request, response);
        request.getRequestDispatcher("Views/Matter/details.jsp").forward(request, response);
    }
    
    private void DoGetRequestDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException,IOException{
        RequestGetById(request, response);
        request.getRequestDispatcher("Views/Matter/delete.jsp").forward(request, response);
    }
    
    private void DoPostRequestDelete(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
        try{
            Matter matter = GetMatter(request);
            int Result = MatterDAL.delete(matter);
            if (Result !=0){
                request.setAttribute("accion", "index");
                DoGetRequestIndex(request,response);
            }else{
                Utilidad.enviarError("No se Logro ELiminar el Registro", request, response);
            }
        }catch(Exception ex){
            Utilidad.enviarError(ex.getMessage(), request, response);
        }
    }
    
    protected void DoGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        SessionUser.authorize(request, response,() -> {//..........
            String accion= Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    DoGetRequestIndex(request, response); 
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    DoGetRequestCreate(request, response);
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    DoGetRequestEdit(request, response);
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    DoGetRequestDelete(request, response); 
                    break;
                case "details":
                    request.setAttribute("accion", accion);
                    DoGetRequestDetails(request, response); 
                    break;
                default:
                    request.setAttribute("accion", accion);
                    DoGetRequestIndex(request, response);
            }
        });
    }
    
    protected void DoPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        SessionUser.authorize(request, response, () -> {
            String accion = Utilidad.getParameter(request, "accion", "index");
            switch (accion) {
                case "index":
                    request.setAttribute("accion", accion);
                    DoPostRequestIndex(request, response); 
                    break;
                case "create":
                    request.setAttribute("accion", accion);
                    DoPostRequestCreate(request, response); 
                    break;
                case "edit":
                    request.setAttribute("accion", accion);
                    DoPostRequestEdit(request, response); 
                    break;
                case "delete":
                    request.setAttribute("accion", accion);
                    DoPostRequestDelete(request, response); 
                    break;
                default:
                    request.setAttribute("accion", accion);
                    DoGetRequestIndex(request, response); 
            }

        });
    }
}
