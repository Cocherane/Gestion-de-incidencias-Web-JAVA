package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.GestionAPP;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "RegisterUser", value = "/RegisterUser")
public class RegisterUser extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // instance of MVC
        GestionAPP app = new GestionAPP();
        // printer
        PrintWriter out = response.getWriter();

        // set json object response from server
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //create Json Object
        JsonObject data = new JsonObject();

        // get parameter form
        String name = request.getParameter("FirstName");
        String surmane = request.getParameter("LastName");
        String email = request.getParameter("InputEmail");
        String passwr = request.getParameter("InputPassword");
        String passwrRepet = request.getParameter("RepeatPassword");

//        System.out.println(name);
//        System.out.println(surmane);
//        System.out.println(email);
//        System.out.println(passwr);
//        System.out.println(passwrRepet);

        if ( name.isEmpty() && surmane.isEmpty() && email.isEmpty() && passwr.isEmpty() && passwrRepet.isEmpty() ){
            // no puede contener un valor vacio
            data.addProperty("alertHTML",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Algunos campos están vacíos.\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );
        }else if( !email.contains("@") ){
            // check if contains a @
            data.addProperty("alertHTML",
                    "<div class=\"alert alert-danger d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Es un inválido email.\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );

        } else if (!passwr.equals(passwrRepet)) {
            // comprobar si el los password coincide
            data.addProperty("alertHTML",
                    "<div class=\"alert alert-danger d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Las contraseñas no coinciden.\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );
        }else {
            // check if insert a user was success
            if ( app.insertaUsuario(name, surmane, passwr, email) ){

                data.addProperty("redirect", "/FernanTickets/LogIn.jsp");

            }else {
                // el email ya existe
                data.addProperty("alertHTML",
                        "<div class=\"alert alert-danger d-flex align-items-center \" role=\"alert\">\n" +
                                "                                <i class=\"fa fa-ban\"></i>\n" +
                                "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                                "                                <div class=\"mx-1\">\n" +
                                "                                  Ya existe un usuario registrado con este email..\n" +
                                "                                </div>\n" +
                                "                              </div>"
                );
            }

        }

        // print data JSON to Ajax client
        out.print(data);
    }
}
