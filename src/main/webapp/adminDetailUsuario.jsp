<%@ page import="java.util.ArrayList" %>
<%@ page import="models.*" %><%--
  Created by IntelliJ IDEA.
  User: ariel
  Date: 26/07/2022
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  // instance of gestionApp
  GestionAPP app = new GestionAPP();

  // if user isn't logged in resent to index.jsp
  if ( session.getAttribute("user") != null || session.getAttribute("user") instanceof Admin){

    //get all usuario
    ArrayList<UsuarioDataClass> usuarioData = app.getUsuarios();

    // check if Arraylist<IncidenciaDataClass> is no empty
    if ( !usuarioData.isEmpty() ){
%>
<%-- titulo de las incidencias --%>
<div class="col-lg-12 pb-3 pb-md-4">
  <h1 class="h5 fst-italic mb-0 text-gray-600 fw-bolder text-capitalize">Información de los usuarios registrados</h1>
</div>

<%
  // contador para marcar el contador del modal para abrir el correcto
  int counterSetOffcanvas = 0;
  // interacion de las incidencias
  for ( UsuarioDataClass usuario : usuarioData) {
    //
    counterSetOffcanvas++;
%>

<!-- BEGIN Incidencia short data -->
<div class="col-lg-6 pb-3 pb-md-4">
  <div class="card shadow-lg border-primary">
    <div class="card-header text-center bg-primary text-light fs-5 fw-bold">
      Detalles del usuario
    </div>
    <div class="card-body text-light">
      <ul class="list-group list-group-flush">
        <li class="list-group-item text-capitalize ">Id del usuario : <%= usuario.getId()%></li>
        <li class="list-group-item text-capitalize">Nombre : <%= usuario.getNombre()+" "+ usuario.getApel()%></li>
        <li class="list-group-item text-lowercase">Email : <%= usuario.getEmail()%></li>
        <li class="list-group-item ">Número de incidencias abiertas por asignar : <%= usuario.getIncidenciasAbiertas()%></li>
      </ul>
    </div>
    <div class="card-footer text-center text-muted">
      Detalles del usuario
    </div>
  </div>
</div>

<!-- END Incidencia short data -->

<%-- if the Arraylist<IncidenciaDataClass> is empty --%>
<%
    // llave de cerrar el for
  }
  // llave de cerrar para si ista empty el arraylist
}else{
%>
<div class="col" >
  <div class="card border-left-primary shadow h-100 py-5 text-center">
    <p class="h4">
      <i class="bi bi-menu-up"></i>
      <b> NO HAY USUARIO REGISTRADOS AUN ...  </b>
    </p>
  </div>
</div>
<%
    }


    // resend to index
  }else{
    response.sendRedirect( "index.jsp" );
  }

%>

