package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.GestionAPP;
import models.Usuario;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(name = "InsertarIncidencia", value = "/InsertarIncidencia")
public class InsertarIncidencia extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Instance GestionApp a MVC
        GestionAPP appInsert = new GestionAPP();
        // Instance of Session to set a User Logged
        HttpSession session = request.getSession();
        // Instance Printerout
        PrintWriter out = response.getWriter();

        // description incident data
        String description = request.getParameter("description");
        // prioridad incident data
        String prioridad = request.getParameter("prioridad");

        // check if the email and pass are validating with this patterns
        Pattern patternDescription = Pattern.compile("^[\\w\\-\\s]+${1,200}");
        Pattern patternPrioridad = Pattern.compile("^[0-9]{1,2}");

        // set json object response from server
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //create Json Object
        JsonObject data = new JsonObject();

        // check if field are blank
        // check if user is logged in
        if ( session.getAttribute("user") == null ){
            // resent to index.jsp
            data.addProperty("redirect", "/FernanTickets/index.jsp");

        // check if field are blank
        } else if ( prioridad.isBlank() || description.isBlank() ) {
            data.addProperty("alertHtml",
                    "<div class=\" alert alert-warning\" role=\"alert\">\n" +
                            "                                    <i class=\"bi bi-exclamation-triangle text-danger\"></i>\n" +
                            "                                    ¡Los campos estan en blanco!\n" +
                            "                                </div>"
                    );

        // check patter input in field
        } else if ( !patternDescription.matcher( description ).matches() || !patternPrioridad.matcher( prioridad ).matches() ) {
            data.addProperty("alertHtml",
                    "<div class=\" alert alert-danger\" role=\"alert\">\n" +
                            "                                    <i class=\"bi bi-exclamation-triangle text-warning\"></i>\n" +
                            "                                    ¡Has introducidos caracteres especiales! <br> Solo caracteres Alphanumerico\n" +
                            "                                </div>"
            );

        // if all field are valited
        }else if ( session.getAttribute("user") instanceof Usuario ){
            // insert into BD
            appInsert.insertaIncidencia(
                    // email de la incidecnia
                    ((Usuario) session.getAttribute("user")).getEmail(),
                    // descripcion de la incidecnia
                    description.toLowerCase(),
                    // prioridad de la incidecnia
                    Integer.parseInt( prioridad )
            );

            data.addProperty("redirect", "/FernanTickets/Usuario.jsp");



        // enviar un mensaje de error fatal
        }else {
            data.addProperty("alertHtml",
                    "<div class=\" alert alert-danger\" role=\"alert\">\n" +
                            "                                    <i class=\"bi bi-exclamation-triangle text-warning\"></i>\n" +
                            "                                    ¡Ha ocurrido un error fatal intenta de nuevo!\n" +
                            "                                </div>"
            );
        }

        // salida de el envio del json
        out.println(data);
    }
}
