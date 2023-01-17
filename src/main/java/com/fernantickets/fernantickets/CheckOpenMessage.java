package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.GestionAPP;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(name = "CheckOpenMessage", value = "/CheckOpenMessage")
public class CheckOpenMessage extends HttpServlet {


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


        // first data from idMessage
        int idMessage = Integer.parseInt(request.getParameter("idMessage").trim());
        // Second data from user using
        int idUser = Integer.parseInt(request.getParameter("idUser").trim());


        // set json object response from server
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //create Json Object
        JsonObject data = new JsonObject();


        // process to check email open
        if ( app.markMessageOpenByidMessage( idMessage ) ){
            data.addProperty("Process", "True");
        }else {
            data.addProperty("Process", "False");
        }

        out.println(data);

    }
}
