package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.Admin;
import models.GestionAPP;
import models.Tecnico;
import models.Usuario;

import javax.mail.Session;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(name = "LogIn", value = "/LogIn")
public class LogIn extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Instance GestionApp a MVC
        GestionAPP appLogIn = new GestionAPP();
        // Instance of Session to set a User Logged
        HttpSession session = request.getSession();
        // Instance Printerout
        PrintWriter out = response.getWriter();
        // Instance user to NULL
        Object user = null;


        // set json object response from server
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //create Json Object
        JsonObject data = new JsonObject();

        // values email and password from LogIn.jsp, [ remove space blank, email to lowercase ]
        String email = request.getParameter("emailUser").trim().toLowerCase();
        String pass = request.getParameter("passUser").trim();


        // check if the email and pass are validating with this patterns
        Pattern patternEmail = Pattern.compile("^[a-zA-Z0-9_!#$%&*+/?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Pattern patternPass = Pattern.compile("^[a-zA-Z0-9_!#$%&*+/?`{|}~^.-]{1,12}");


        // check Values if values are blanks
        if ( email.isEmpty() || pass.isEmpty() ){

            // Alert!!! values some are blanks [ END 1]
            data.addProperty("form",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  El email o el password fueron introducidos en blanco.\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );

            // check if pattern with email and password are valid
        } else if ( !patternEmail.matcher( email ).matches() || !patternPass.matcher( pass ).matches() ) {

            // Alert!!! values some are valid with each pattern [ END 2]
            data.addProperty("form",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Fueron ingresados caracteres no permitidos .\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );

            // if pass all control and check, procedure to check if the user is valid
        }else {

            // check if the user with that email and password exist
            user = appLogIn.login( email, pass );

            // set record
            //appLogIn.logInSessionLog( user );

            // check if user is null( NO EXIST )
            if ( user != null ){

                // save in session the user
                session.setAttribute("user", user);
                // set max inactive to 15 min
                session.setMaxInactiveInterval( 900 );
                // control logOut secure and loguin
                session.setAttribute("logout", "true");
                
                // check if
               if( user instanceof Usuario){
                   data.addProperty("redirect",  "/FernanTickets/Usuario.jsp");
               } else if ( user instanceof Tecnico ) {
                   data.addProperty("redirect", "/FernanTickets/Tecnico.jsp");
               } else if ( user instanceof Admin) {
                   data.addProperty("redirect", "/FernanTickets/Admin.jsp");
               }else {
                   data.addProperty("redirect", "/FernanTickets/index.jsp");
               }

            }else {
                // Alert!!! there're not any matches in BBDD [ END 3]
                data.addProperty("form",
                        "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                                "                                <i class=\"fa fa-ban\"></i>\n" +
                                "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                                "                                <div class=\"mx-1\">\n" +
                                "                                  El email o password no coinciden intenta de nuevo.\n" +
                                "                                </div>\n" +
                                "                              </div>"
                );
            }


        }
        // print data JSON to Ajax client
        out.print(data);

    // END METHOD POST
    }

}
