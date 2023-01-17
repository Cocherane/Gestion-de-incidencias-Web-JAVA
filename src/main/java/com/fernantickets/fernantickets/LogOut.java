package com.fernantickets.fernantickets;

import models.GestionAPP;
import models.Tecnico;
import models.Usuario;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogOut", value = "/LogOut")
public class LogOut extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Instance GestionApp a MVC
        GestionAPP appLogOut = new GestionAPP();
        // instance of HttpSession to delete the session
        HttpSession session = request.getSession();

        //  get value logout check user just could active this with dashboard
        String  valueLogout = request.getParameter("logout");
        // check if el user is not null
        Object usuario = session.getAttribute( "user" );

        // check if query of logout if fron dashboard o link direct
        if ( valueLogout != null && valueLogout.equalsIgnoreCase("true") && usuario != null){
            // se dataRecord logOut
            appLogOut.logOutSessionLog( usuario );
            // close session if query is from dashboard
            session.invalidate();
            // redirest to index.jsp
            response.sendRedirect("index.jsp");
        }else if (usuario != null){
            // redireciona al usuario al usuario al dashboard

            if( usuario instanceof Usuario){
                response.sendRedirect("Usuario.jsp");
            } else if ( usuario instanceof Tecnico) {
                response.sendRedirect("Tecnico.jsp");
            }else {
                response.sendRedirect("Admin.jsp");
            }
        }else{
            // redireciona al index si no cumple con ningun parametro arriba
            response.sendRedirect("index.jsp");
        }
    }
}
