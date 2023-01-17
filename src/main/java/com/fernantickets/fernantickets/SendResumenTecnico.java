package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.Admin;
import models.GestionAPP;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SendResumenTecnico", value = "/SendResumenTecnico")
public class SendResumenTecnico extends HttpServlet {
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

        // idTecnico
        int idTecnico = Integer.parseInt(request.getParameter("idTecnicoSend"));


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


            // if all field are valited
        }else if ( session.getAttribute("user") instanceof Admin ){
                // num idTecnico to send all resummen
            app.enviarResumenIncidenciaTecnico( idTecnico, app.getPathDataConfig() );


            data.addProperty("redirect", "/FernanTickets/Admin.jsp");



            // enviar un mensaje de error fatal
        }else {
            data.addProperty("responseHtmlAlert",
                    "<div class=\" alert alert-danger\" role=\"alert\">\n" +
                            "                                    <i class=\"bi bi-exclamation-triangle text-warning\"></i>\n" +
                            "                                    ¡Ha ocurrido un error fatal, intenta de nuevo más tarde!\n" +
                            "                                </div>"
            );
        }

        // salida de el envio del json
        out.println(data);

    }
}
