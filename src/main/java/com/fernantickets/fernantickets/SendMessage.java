package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.Admin;
import models.GestionAPP;
import models.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SendMessage", value = "/SendMessage")
public class SendMessage extends HttpServlet {
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


        // data from message
        User emitter = (User) session.getAttribute("user");
        User receptor = (User) app.buscaUserById(
                Integer.parseInt( request.getParameter("idReceptor") )
        );

        int idEmmitter = Integer.parseInt(request.getParameter("idEmmitter"));
        int idReceptor = Integer.parseInt(request.getParameter("idReceptor"));
        int idIncidenia = Integer.parseInt(request.getParameter("idIncidenia"));
        String content = request.getParameter("message");


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
        }else if (  content.isBlank() ){

            data.addProperty("alert",
                    "<div class=\" alert alert-danger\" role=\"alert\">\n" +
                            "                                    <i class=\"bi bi-exclamation-triangle text-warning\"></i>\n" +
                            "                                    ¡No ha enviado ningún mensaje, ingrese uno!\n" +
                            "                                </div>"
            );

        }else if (  app.insertaMessage( emitter, receptor, content  ) ){

            data.addProperty("send", "true");

            // enviar un mensaje de error fatal
        }else {
            data.addProperty("alert",
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
