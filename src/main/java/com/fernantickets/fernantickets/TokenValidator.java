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

@WebServlet(name = "TokenValidator", value = "/TokenValidator")
public class TokenValidator extends HttpServlet {
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
        String token = request.getParameter("token").trim().toLowerCase();

        // check if the email and pass are validating with this patterns
        Pattern patternToken = Pattern.compile("^[0-9]{6}");


        // validation of token
        if ( !patternToken.matcher( token ).matches() ){
            // Alert!!! values some are valid with each pattern
            data.addProperty("alert",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Fueron ingresados caracteres no numeros .\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );
        } else if (token.isBlank()) {
            // Alert!!! value is blank
            data.addProperty("alert",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  El token fue introducido en blanco .\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );

            // check if token is accept
        } else if ( appLogIn.validandoTokentoObject( session.getAttribute("userTokenValidate"), Integer.parseInt(token))  ) {
            // set accept to objet user
            ((Usuario) session.getAttribute("userTokenValidate")).tokenacept();

            // set atribute user to session
            session.setAttribute("user", session.getAttribute("userTokenValidate") );

            // delete object usuario session.getAttribute("userTokenValidate")
            session.removeAttribute("userTokenValidate");

            // redireccion to usuario page
            data.addProperty("redirect", "/FernanTickets/Usuario.jsp");

            // if no match
        }else
            // Alert!!! value is not validated
            data.addProperty("alert",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Token no validado, intente de nuevo.\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );

        // print data JSON to Ajax client
        out.print(data);


    }
}
