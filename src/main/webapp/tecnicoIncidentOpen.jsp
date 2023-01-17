<%@ page import="models.GestionAPP" %>
<%@ page import="models.IncidenciaDataClass" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="models.Tecnico" %><%--
  Created by IntelliJ IDEA.
  User: ariel
  Date: 26/07/2022
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- Custom scriptss for all Form -->
<script type="text/javascript" src="js/tecnico.js"></script>
<script type="text/javascript" src="js/adminGeneral.js"></script>
<%
  // instance of gestionApp
  GestionAPP app = new GestionAPP();

  // if user isn't logged in resent to index.jsp
  if ( session.getAttribute("user") != null || session.getAttribute("user") instanceof Tecnico){
    // Instance user
    Tecnico tecnico = ((Tecnico) session.getAttribute("user"));

    //get incidencias abiertas no asignadas y asignadas
    ArrayList<IncidenciaDataClass> incidenciasAbiertas = app.buscaIncidenciasAbiertasbyTecnico( tecnico.getId() );

    // check if Arraylist<IncidenciaDataClass> is no empty
    if ( !incidenciasAbiertas.isEmpty() ){
%>
        <%-- titulo de las incidencias --%>
        <div class="col-lg-12 pb-3 pb-md-4">
          <h1 class="h5 fst-italic mb-0 text-gray-600 fw-bolder text-capitalize">Incidencias asignadas</h1>
        </div>

<%
      // contador para marcar el contador del modal para abrir el correcto
      int counterSetOffcanvas = 0;
      // interacion de las incidencias
      for (IncidenciaDataClass incidencia : incidenciasAbiertas) {
        counterSetOffcanvas++;

%>


<!-- BEGIN Incidencia short data -->
<div class="col-lg-6 pb-3 pb-md-4">
  <div class="card shadow-lg border-primary">
    <div class="card-header text-center bg-primary text-light fs-5 fw-bold">
      Incidencia Asignada
    </div>
    <div class="card-body text-light">
      <ul class="list-group list-group-flush">
        <li class="list-group-item ">Incidencia con ID : <%=incidencia.getId()%></li>
        <li class="list-group-item ">Comentario : <%= incidencia.getDescripcion()%></li>
        <li class="list-group-item ">Prioridad : <%= incidencia.getPrioridad()%></li>
        <li class="list-group-item ">Fecha de la creacion : <%= incidencia.getDateFormatteFechaInicio()%></li>
        <li class="list-group-item ">Estado :
          <div class="progress">
            <div class="progress-bar bg-danger bg-primary progress-bar-striped progress-bar-animated text-center" role="progressbar" aria-label="Animated striped example" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: 70%">
              Asignada
            </div>
          </div>
        </li>
      </ul>
      <div class="d-flex justify-content-center mx-auto py-1">
        <button class="btn btn-primary mx-1" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal_<%=counterSetOffcanvas%>" >Cerrar Incidencia</button>
        <button class="btn btn-primary mx-1" type="button" data-bs-toggle="modal" data-bs-target="#messageModal_<%=counterSetOffcanvas%>" >Enviar un mensaje</button>
      </div>

    </div>
    <div class="card-footer text-center text-muted">
      Hace <%= incidencia.getDias() %> días
    </div>
  </div>
</div>

<!-- END Incidencia short data -->

<!-- BEGIN Incidencia long data Modal -->
<div class="modal fade" id="exampleModal_<%=counterSetOffcanvas%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header bg-primary text-white">
        <h5 class="modal-title fw-bold text-center ">¿Quieres Cerrar la incidencia?</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <!-- BEGIN Cuerpo de la incidencia detallada -->
      <div class="modal-body">

        <div id="id-alert-report-tecnico">
          <div class=" alert alert-warning text-center" role="alert">
            <i class="bi bi-exclamation-triangle text-danger "></i>
            ¡Describe brevemente la resolución!
          </div>
        </div>

        <!-- body of form -->
        <form id="form-tecnico-close-<%=counterSetOffcanvas%>" class="formAddTask"  method="post">

          <div class="form-group ">
            <label class="form-label fw-bold" >Descripción de la resolución del reporte</label>
            <input type="text" class="form-control" name="description" minlength="1" maxlength="200" placeholder="Descripción de la resolucion" required>
            <input type="hidden" name="idIncidenia" value="<%=incidencia.getId()%>">
          </div>

          <hr class="sidebar-divider">

          <div class="d-flex flex-row-reverse px-2 ">
            <button type="button" class="btn btn-primary m-1 shadow-lg" onclick="setCloseIncidenctByTecnico(<%=counterSetOffcanvas%>, false)"  >Reportar</button>
            <button type="reset" class="btn btn-secondary m-1 shadow-lg" data-dismiss="modal">Reset</button>
          </div>
        </form>
      </div>
      <!-- FIN Cuerpo de la incidencia detallada -->
    </div>
  </div>
  <!-- FIN Incidencia long data Modal -->
</div>

<!-- BEGIN Incidencia long data Modal -->
<div class="modal fade" id="messageModal_<%=counterSetOffcanvas%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header bg-primary text-white">
        <h5 class="modal-title fw-bold text-center ">¿Quieres enviarle un mensaje al usuario que creo la incidencia?</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <!-- BEGIN Cuerpo de la incidencia detallada -->
      <div class="modal-body">

        <div id="id-alert-message">
          <div class=" alert alert-warning text-center" role="alert">
            <i class="bi bi-exclamation-triangle text-danger "></i>
            ¡Describe brevemente el mensaje!
          </div>
        </div>

        <!-- body of form -->
        <form id="form-send-message-<%=counterSetOffcanvas%>"  >

          <div class="form-group ">
            <label class="form-label fw-bold" >Descripción el mensaje</label>
            <input type="text" class="form-control" name="message" minlength="1" maxlength="200" placeholder="Descripción del mensaje" required>
            <input type="hidden" name="idEmmitter" value="<%=tecnico.getId()%>">
            <input type="hidden" name="idReceptor" value="<%=incidencia.getIdUsuario()%>">
            <input type="hidden" name="idIncidenia" value="<%=incidencia.getId()%>">
          </div>

          <hr class="sidebar-divider">

          <div class="d-flex flex-row-reverse px-2 ">
            <button type="button" class="btn btn-primary m-1 shadow-lg" onclick="sendMessage(<%=counterSetOffcanvas%>)" >Enviar</button>
            <button type="reset" class="btn btn-secondary m-1 shadow-lg" data-dismiss="modal">Reset</button>
          </div>
        </form>
      </div>
      <!-- FIN Cuerpo de la incidencia detallada -->
    </div>
  </div>
  <!-- FIN Incidencia long data Modal -->
</div>

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
      <b> NO HAY INCIDENCIAS ASIGNADAS A TI AUN ...  </b>
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
