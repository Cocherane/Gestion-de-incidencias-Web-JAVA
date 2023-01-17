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

        //get incidencias abiertas no asignadas y asignadas
        ArrayList<TecnicoDataClass> tecnicoData = app.getTecnicos();

        // check if Arraylist<IncidenciaDataClass> is no empty
        if ( !tecnicoData.isEmpty() ){
%>
<%-- titulo de las incidencias --%>
<div class="col-lg-12 pb-3 pb-md-4">
    <h1 class="h5 fst-italic mb-0 text-gray-600 fw-bolder text-capitalize">Información de los técnicos registrados</h1>
</div>

<%
    // contador para marcar el contador del modal para abrir el correcto
    int counterSetOffcanvas = 0;
    // interacion de las incidencias
    for (TecnicoDataClass tecnico : tecnicoData) {
        //
        counterSetOffcanvas++;
%>

<!-- BEGIN Incidencia short data -->
<div class="col-lg-6 pb-3 pb-md-4">
    <div class="card shadow-lg border-primary">
        <div class="card-header text-center bg-primary text-light fs-5 fw-bold">
            Detalles del técnico
        </div>
        <div class="card-body text-light">
            <ul class="list-group list-group-flush">
                <li class="list-group-item ">Id del tecnico : <%= tecnico.getId()%></li>
                <li class="list-group-item text-capitalize">Nombre : <%= tecnico.getNombre()+" "+ tecnico.getApel()%></li>
                <li class="list-group-item text-lowercase">Email : <%= tecnico.getEmail()%></li>
                <li class="list-group-item ">Numero de incidecias abierta : <%= tecnico.getIncidenciasAbiertas()%></li>
                <li class="list-group-item ">Numero de incidencia que ha cerrado : <%= tecnico.getIncidenciasCerradas()%></li>
                <li class="list-group-item ">Prioridad media de las incidencias : <%= tecnico.getPrioridadMediaIncidenciaTecnico()%></li>
            </ul>
        </div>
        <div class="card-footer text-center text-muted">
            Detalles del técnico
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
            <b> NO HAY TECNICOS REGISTRADOS AUN ...  </b>
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

