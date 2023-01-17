package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.GestionAPP;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(name = "CerrarIncidencia", value = "/CerrarIncidencia")
public class CerrarIncidencia extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Instance GestionApp a MVC
        GestionAPP app = new GestionAPP();
        // Instance of Session to set a User Logged
        HttpSession session = request.getSession();
        // Instance Printerout
        PrintWriter out = response.getWriter();
        // Instance user to NULL
        Object user = null;


        // password first data
        String description = request.getParameter("description").toLowerCase();
        // password Second data
        String idIncidenia = request.getParameter("idIncidenia");

        // check if the email and pass are validating with this patterns
        Pattern patternDescription = Pattern.compile("^[\\w\\-\\s]+${1,200}");

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
        } else if ( description.isBlank() || idIncidenia.isBlank() ) {
            data.addProperty("responseHtmlAlert", "" +
                    "<i class=\"bi bi-exclamation-triangle-fill\"></i>" +
                    "  Algunos campos están en blanco!!!" +
                    "");

            // set the resolution in an incidenct by tecnico if
        }else if ( app.cierraIncidencia(1234, Integer.parseInt(idIncidenia), description) ){

            // set description of incident by tecnico resent to tecnico.jsp
            data.addProperty("load", "true");
        }else {
            // mesaje de que ha ocurrido un error fatal
            data.addProperty("responseHtmlAlert", "" +
                    "<i class=\"bi bi-exclamation-triangle-fill\"></i>" +
                    "  HA OCURRIDO UN ERROR FATAL!!!" +
                    "");
        }

        // print data JSON to Ajax client
        out.print(data);

    }
}
