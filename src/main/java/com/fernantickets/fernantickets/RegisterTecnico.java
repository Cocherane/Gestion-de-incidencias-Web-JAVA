package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.Admin;
import models.GestionAPP;
import models.Tecnico;
import models.Usuario;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

@WebServlet(name = "RegisterTecnico", value = "/RegisterTecnico")
public class RegisterTecnico extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Instance GestionApp a MVC
        GestionAPP app = new GestionAPP();
        // Instance of Session to set a User Logged
        HttpSession session = request.getSession();
        // Instance Printerout
        PrintWriter out = response.getWriter();



        // set json object response from server
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //create Json Object
        JsonObject data = new JsonObject();

        // values email and password from LogIn.jsp, [ remove space blank, email to lowercase ]
        String tecnicoName = request.getParameter("tecnicoNombre").trim().toLowerCase();
        String tecnicoApels = request.getParameter("tecnicoApels").trim().toLowerCase();
        String tecnicoEmail = request.getParameter("tecnicoEmail").trim().toLowerCase();
        String tecnicoPass = request.getParameter("tecnicoPass").trim();
        String tecnicoPassAgain = request.getParameter("tecnicoPassAgain").trim();


        // check if the email and pass are validating with this patterns
        Pattern patternEmail = Pattern.compile("^[a-zA-Z0-9_!#$%&*+/?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
        Pattern patternPass = Pattern.compile("^[a-zA-Z0-9_!#$%&*+/?`{|}~^.-]{1,12}");
        Pattern patternWord = Pattern.compile("^[a-zA-Z0-9_!#$%&*+/?`{|}~^.-]{1,50}");

        // control if user is null and pass are empty or no same
        //ocurre cuando no existe un usuario loguiado
        if ( session.getAttribute("user") == null ){
            data.addProperty("redirect", "/FernanTickets/index.jsp");

        // check Values if values are blanks
        }else if (  tecnicoName.isBlank() || tecnicoApels.isBlank() || tecnicoPass.isBlank() || tecnicoEmail.isBlank()  ){

            // Alert!!! values some are blanks [ END 1]
            data.addProperty("alertHTML",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Algún dato fue esta en blanco.\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );

            // check if pattern with name and surname are valid
        } else if ( !patternWord.matcher( tecnicoName ).matches() || !patternWord.matcher( tecnicoApels ).matches()  ) {

            // Alert!!! values some are valid with each pattern [ END 2]
            data.addProperty("alertHTML",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Fueron ingresados caracteres no permitidos en el Nombre o Apellido.\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );

            // check if pattern with  password is valid
        } else if (  !patternPass.matcher( tecnicoPass ).matches() ) {

            // Alert!!! values some are valid with each pattern [ END 2]
            data.addProperty("alertHTML",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Fueron ingresados caracteres no permitidos en la Contrasena.\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );

            // check if pattern with email is valid
        } else if ( !patternEmail.matcher( tecnicoEmail ).matches() || !patternPass.matcher( tecnicoPass ).matches() ) {

            // Alert!!! values some are valid with each pattern [ END 2]
            data.addProperty("alertHTML",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Fueron ingresados caracteres no permitidos en le Email .\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );

            // check if the password are same
        } else if ( !tecnicoPass.equals( tecnicoPassAgain ) ) {

            // Alert!!! values some are valid with each pattern [ END 2]
            data.addProperty("alertHTML",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  Las contraseñas no concuerdan, ingrésalas de nuevo.\n" +
                            "                                </div>\n" +
                            "                              </div>"
            );

            // if pass all control and check, procedure to check if the tecnico is valid
        }else {


            // register the tecnico
            if ( app.insertaTecnico( tecnicoName, tecnicoApels, tecnicoPass, tecnicoEmail ) ){

                // check if
                data.addProperty("status", "true");

            }else {
                // Alert!!! there're not any matches in BBDD [ END 3]
                data.addProperty("alertHTML",
                        "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                                "                                <i class=\"fa fa-ban\"></i>\n" +
                                "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                                "                                <div class=\"mx-1\">\n" +
                                "                                  Ya existe un usuario con este email .\n" +
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
