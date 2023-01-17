<%@ page import="models.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %><%--
  Created by IntelliJ IDEA.
  User: ariel
  Date: 26/07/2022
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Custom scriptss for all Form -->
<script type="text/javascript" src="js/administrador.js"></script>
<%


    // if user isn't logged in resent to index.jsp
    if ( session.getAttribute("user") != null || session.getAttribute("user") instanceof Admin){

        // instance of gestionApp
        GestionAPP app = new GestionAPP();
        // get all usuarios
        HashMap<Object, Date> usuarios = app.getLastSession();


%>
<!-- Card Header - Dropdown -->
<div
        class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
    <h6 class="m-0 font-weight-bold text-primary h4"> LAS ÚLTIMAS SESIONES DE LOS USUARIOS ...</h6>
</div>
<!-- Card Body -->
<div class="card-body">
    <%


        // check if Arraylist<TecnicoDataClass> is no empty
        if ( !usuarios.isEmpty() ){


    %>



    <!-- BEGIN LIST USER -->

    <table class="table table-sm table-light text-center table-primary table-hover table-striped">
        <thead>
        <tr class="m-2">
            <th class="col-1 text-center" scope="col">Numero</th>
            <th class="col-1 text-center" scope="col">ID</th>
            <th class="col-3 text-center" scope="col">Tipo Usuario</th>
            <th class="col-4 text-center" scope="col">Nombre</th>
            <th class="col-3 text-center" scope="col">Última sesión</th>


        </tr>
        </thead>
        <tbody>
        <%
            SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );

            int index = 1;
            // interamos sobre el  hashmap de objetos usuario y date
            for ( Map.Entry<Object, Date> configSet : usuarios.entrySet() ) {

                // pasamos el valor del objeto para obtener la informacion del usuario
                String[] dataUser = app.getResumeUser( configSet.getKey() );
                // Obtenemos el objeto Date
                Date dateLastSession =  configSet.getValue();


        %>


        <!-- primer dato de tecnicos-->
        <tr>
            <%--num index--%>
            <td><%= index++ %></td>
            <%--Id user--%>
            <td><%= dataUser[0] %></td>
            <%--tipo user--%>
            <td class="text-capitalize"><%= dataUser[1]%></td>
            <%--Num incidencias asignadas--%>
            <td class="text-capitalize" ><%= dataUser[2]%></td>
            <%--las session date --%>
            <td><%= dateFormat.format( dateLastSession )%></td>
        </tr>


        <%
                // close interacion tecnico
            }
        %>

        </tbody>
    </table>

    <%
            // if there aree no tecnico show this message
        }else {
            out.println(
                    "<div class=\"col\" >\n" +
                            "                            <div class=\"card border-left-primary shadow h-100 py-5 text-center\">\n" +
                            "                                <p class=\"h4\">\n" +
                            "                                    <b> USUARIOS AUN REGISTRADOS..  </b>\n" +
                            "                                    <i class=\"fa fa-calendar-week\"></i>\n" +
                            "                                </p>\n" +
                            "                            </div>\n" +
                            "                        </div>"
            );
        }

    %>


</div>


<%

        // resend to index
    }else{
        response.sendRedirect( "index.jsp" );
    }

%>

