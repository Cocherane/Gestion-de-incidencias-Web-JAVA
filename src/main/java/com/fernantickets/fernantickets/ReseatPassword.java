package com.fernantickets.fernantickets;

import com.google.gson.JsonObject;
import models.GestionAPP;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ReseatPassword", value = "/ReseatPassword")
public class ReseatPassword extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        // Instance GestionApp a MVC
        GestionAPP appLogIn = new GestionAPP();
        // Instance of Session to set a User Logged
        HttpSession session = request.getSession();
        // Instance Printerout
        PrintWriter out = response.getWriter();



        // set json object response from server
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        //create Json Object
        JsonObject data = new JsonObject();

        // values email [ remove space blank, email to lowercase ]
        String emailReseat = request.getParameter("emailReseat").trim().toLowerCase();

        //  check if the user exists
        Object user = appLogIn.buscaUserAllByEmail( emailReseat );
        if ( user != null ){
            // set new pass
            int passReseat = genNumLen(6,0,9);
            // restablecer contrasena
            appLogIn.setNewPasswordObject(
                    user ,
                    String.valueOf(passReseat));
            // close session
            session.invalidate();
            // respond an action to in body with a message your password was mod
            data.addProperty("redirect", "/FernanTickets/LogIn.jsp");


            appLogIn.sentEmailReseatPass(
                emailReseat,
                passReseat
            );


            // no user exists
        }else {
            data.addProperty("alert",
                    "<div class=\"alert alert-warning  d-flex align-items-center \" role=\"alert\">\n" +
                            "                                <i class=\"fa fa-ban\"></i>\n" +
                            "                                <i class=\"fa-solid fa-triangle-exclamation\"></i>\n" +
                            "                                <div class=\"mx-1\">\n" +
                            "                                  El usuario no existe.\n" +
                            "                                </div>\n" +
                            "                              </div>"
                    );
        }

        // print data JSON to Ajax client
        out.print(data);
    }

    /*generation clave*/

    private static int genNumLen(int len, int nuMin, int nuMax){
        int numOut = 0;
        for (int i = 0; i < len; i++) {
            int num ;
            // do es para controlar que si sale un numero 0 al final principio del numero random sea 0 lo vuelva a intentar
            do {
                num = genNum(nuMin,nuMax);
            }while(num == 0 && i == (len -1));
            // una vez pase la condicion se asigna a la posicion
            numOut += Math.pow(10,i) * num ;
        }
        return numOut;
    }

    private static int genNum(int nuMin, int nuMax){
        return (int)(Math.random() * (nuMax-nuMin + 1)) + nuMin;
    }

}
