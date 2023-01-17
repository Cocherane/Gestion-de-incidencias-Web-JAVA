<%@ page import="java.util.ArrayList" %>
<%@ page import="models.*" %><%--
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
  // instance of gestionApp
  GestionAPP app = new GestionAPP();


  // if user isn't logged in resent to index.jsp
  if ( session.getAttribute("user") != null || session.getAttribute("user") instanceof Admin){

    // Prioridada media de la aplicacion
    float prioridaApp = app.prioridadMediaApp();
    // incidencias abiertas
    int numInciAbiertas = app.incidenciasAbiertas();
    // incidencias cerradas
    int numInciCerradas = app.incidenciasCerradas();

%>
<!-- Incidencias cerradas: -->
<div class="col-md-4 mb-4">
  <div class="card border-left-primary shadow h-100 py-2">
    <div class="card-body">
      <div class="row no-gutters align-items-center">
        <div class="col mr-2">
          <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
            Incidencias cerradas...</div>
          <div class="h6 mb-0 font-weight-bold text-gray-800">

            <%-- prioridad media de las incidencias --%>
            <%= numInciCerradas %>

          </div>
        </div>
        <div class="col-auto">
          <i class="bi bi-clipboard2-data-fill fa-2x"></i>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Prioridad media de las incidencias totales: -->
<div class="col-md-4 mb-4">
  <div class="card border-left-primary shadow h-100 py-2">
    <div class="card-body">
      <div class="row no-gutters align-items-center">
        <div class="col mr-2">
          <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
            Prioridad media de las incidencias totales...</div>
          <div class="h6 mb-0 font-weight-bold text-gray-800">

            <%-- prioridad media de las incidencias --%>
            <%= (float)( prioridaApp ) %>

          </div>
        </div>
        <div class="col-auto">
          <i class="bi bi-clipboard2-data-fill fa-2x"></i>
        </div>
      </div>
    </div>
  </div>
</div>



<!-- Procentaje de incidencias resueltas: -->
<div class="col-md-4 mb-4">
  <div class="card border-left-primary shadow h-100 py-2">
    <div class="card-body">
      <div class="row no-gutters align-items-center">
        <div class="col mr-2">
          <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
            Procentaje de incidencias resueltas...</div>
          <div class="h6 mb-0 font-weight-bold text-gray-800">

            <%-- prioridad media de las incidencias --%>
            <%= (float)( ( numInciCerradas*100 )/ ( numInciAbiertas + numInciCerradas ) ) %> %

          </div>
        </div>
        <div class="col-auto">
          <i class="bi bi-clipboard2-data-fill fa-2x"></i>
        </div>
      </div>
    </div>
  </div>
</div>


<%

    // resend to index
  }else{
    response.sendRedirect( "index.jsp" );
  }

%>


