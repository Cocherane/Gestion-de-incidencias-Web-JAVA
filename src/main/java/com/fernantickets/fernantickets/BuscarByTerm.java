package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.GestionAPP;
import models.IncidenciaDataClass;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

@WebServlet(name = "BuscarByTerm", value = "/BuscarByTerm")
public class BuscarByTerm extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

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


        // data by term
        String termSeach = request.getParameter("termFilter");

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
        } else if ( termSeach.isBlank() ) {
            data.addProperty("responseHtmlAlert", "<div class=\"alert alert-warning\" role=\"alert\">\n" +
                    "    <i class=\"bi bi-exclamation-triangle-fill\"></i> \n" +
                    "    El campo está en blanco!!!\n" +
                    "</div>");

            // set the resolution in an incidenct by tecnico if
        }else {

            // buscar incidencias
            ArrayList<IncidenciaDataClass> incidenciaData = app.buscarIncidenciasbyTerm( termSeach );

            // check if there are match
            if ( !incidenciaData.isEmpty() ){
                session.setAttribute("incidenciaByTerm", incidenciaData);
                data.addProperty("success", true);
            }else {
                data.addProperty( "responseHtmlAlert","<div class=\"alert alert-warning\" role=\"alert\">\n" +
                        "    <i class=\"bi bi-exclamation-triangle-fill\"></i>\n" +
                        "    HA OCURRIDO UN ERROR INTENTE MÁS TARDE!!!\n" +
                        "</div>");
            }

        }

        // print data JSON to Ajax client
        out.print(data);
    }
}
