package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.GestionAPP;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RestablecerContrasena", value = "/RestablecerContrasena")
public class RestablecerContrasena extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Instance GestionApp a MVC
        GestionAPP appRestablecer = new GestionAPP();
        // Instance of Session to set a User Logged
        HttpSession session = request.getSession();
        // Instance Printerout
        PrintWriter out = response.getWriter();
        // Instance user to NULL
        Object user = null;
        // tipo de usuario
        String tipoUser = null;

        // password first data
        String passFirst = request.getParameter("password");
        // password Second data
        String passSecond = request.getParameter("passwordRepetir");

        // set json object response from server
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //create Json Object
        JsonObject data = new JsonObject();


        // control if user is null and pass are empty or no same
        //ocurre cuando no existe un usuario loguiado
        if ( session.getAttribute("user") == null ){
            data.addProperty("redirect", "/FernanTickets/index.jsp");

        //  Ocuerre ecuando son enviados datos en blanco
        } else if ( passFirst.isBlank() || passSecond.isBlank() ) {
            data.addProperty("responseHtmlAlert", "" +
                    "<i class=\"bi bi-exclamation-triangle-fill\"></i>" +
                    "  Algunos campos están en blanco!!!" +
                    "");
        // Ocurre cuando el password y el passwordRepetir no coinciden
        } else if ( !passFirst.equals(passSecond) ) {
            data.addProperty("responseHtmlAlert", "" +
                    "<i class=\"bi bi-exclamation-triangle-fill\"></i>" +
                    "  No coinciden las contraseñas introducidas!!!" +
                    "");

        // reset the password and logOut
        }else {
            // restablecer contrasena
            appRestablecer.setNewPasswordObject( session.getAttribute("user"), passFirst );
            // close session
            session.invalidate();
            // respond an action to in body with a message your password was mod
            data.addProperty("responseHtmlBody",
                    "<div class=\"d-flex justify-content-center\">\n" +
                            "  <div class=\"card shadow-lg \" style=\"width: 20rem;\">\n" +
                            "    <img src=\"img/PasswordGif.gif\" class=\"card-img-top img-fluid mx-auto\" alt=\"Contrasena\" >\n" +
                            "\n" +
                            "    <div class=\"card-body\">\n" +
                            "      <h5 class=\"card-title text-center\">Has restablecido tu contraseña</h5>\n" +
                            "      <p class=\"card-text\">Ahora tienes que volver a hacer login dentro del sistema</p>\n" +
                            "      <a href=\"index.jsp\" class=\"btn btn-primary\">Pagina Principal</a>\n" +
                            "      <a href=\"LogIn.jsp\" class=\"btn btn-primary\">Menu de login</a>\n" +
                            "    </div>\n" +
                            "  </div>\n" +
                            "</div>"
                    );
        }

        // print data JSON to Ajax client
        out.print(data);

    }
}
