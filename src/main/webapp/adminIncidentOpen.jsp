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
<script type="text/javascript" src="js/adminGeneral.js"></script>
<%
  // instance of gestionApp
  GestionAPP app = new GestionAPP();

  // if user isn't logged in resent to index.jsp
  if ( session.getAttribute("user") != null || session.getAttribute("user") instanceof Admin){


    //get incidencias abiertas no asignadas y asignadas
    ArrayList<IncidenciaDataClass> incidenciasAbiertas = app.buscarTodasIncidenciasAbiertas();
    // get all Tecnicos
    ArrayList<TecnicoDataClass> tecnicos = app.getTecnicos();

    // check if Arraylist<IncidenciaDataClass> is no empty
    if ( !incidenciasAbiertas.isEmpty() ){
%>
<%-- titulo de las incidencias --%>
<div class="col-lg-12 pb-3 pb-md-4">
  <h1 class="h5 fst-italic mb-0 text-gray-600 fw-bolder text-capitalize">Incidencias abiertas sin asignar y asignadas a un tecnico</h1>
</div>

<%
  // contador para marcar el contador del modal para abrir el correcto
  int counterSetOffcanvas = 0;
  // index de la tabla de los tecnicos
  int indexTecnico = 1;
  // interacion de las incidencias
  for (IncidenciaDataClass incidencia : incidenciasAbiertas) {
    // contador para cada incidencia
    counterSetOffcanvas++;

%>


<!-- BEGIN Incidencia short data -->
<div class="col-lg-6 pb-3 pb-md-4">
  <div class="card shadow-lg border-primary">
    <div class="card-header text-center bg-primary text-light fs-5 fw-bold">
      Incidencia
    </div>
    <div class="card-body text-light">
      <ul class="list-group list-group-flush">
        <li class="list-group-item ">Incidencia con ID : <%= incidencia.getId()%></li>
        <li class="list-group-item ">Comentario : <%= incidencia.getDescripcion()%></li>
        <li class="list-group-item ">Prioridad : <%= incidencia.getPrioridad()%></li>
        <li class="list-group-item ">Fecha de la creacion : <%= incidencia.getDateFormatteFechaInicio()%></li>
        <li class="list-group-item ">Estado :
          <div class="progress">
            <div class="progress-bar <%= (incidencia.getIdTecnico() == 0 )? "bg-danger" : "bg-primary "%> bg-primary progress-bar-striped progress-bar-animated text-center" role="progressbar" aria-label="Animated striped example" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: <%= (incidencia.getIdTecnico() == 0 )? 33 : 66 %>%">
              <%= (incidencia.getIdTecnico() == 0 )?"POR ASIGNAR": "ASIGNADA"%>
            </div>
          </div>
        </li>
      </ul>
      <div class="d-flex justify-content-center mx-auto py-1">
        <button class="btn <%=(incidencia.getIdTecnico() == 0 )?"btn-success": "btn-danger"%> mx-1" type="button" data-bs-toggle="modal" data-bs-target="#tecnicosModal_<%=counterSetOffcanvas%>" ><%=(incidencia.getIdTecnico() == 0 )?"Asignar a un tecnico": "Modificar el tecnico"%></button>
        <button class="btn btn-primary mx-1" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal_<%=counterSetOffcanvas%>" <%= (incidencia.getIdTecnico() == 0 )?"disabled": ""%>>Mas informacion ...</button>
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
        <h5 class="modal-title fw-bold text-center ">Incidencia detallada</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
      <!-- BEGIN Cuerpo de la incidencia detallada -->
      <div class="modal-body">
        <div class="card-body text-light">
          <ul class="list-group list-group-flush">
            <li class="list-group-item ">Incidencia con ID : <%= incidencia.getId()%></li>
            <li class="list-group-item ">Comentario : <%= incidencia.getDescripcion()%></li>
            <li class="list-group-item ">Prioridad : <%= incidencia.getPrioridad()%></li>
            <li class="list-group-item ">Fecha de la creacion : <%= incidencia.getDateFormatteFechaInicio()%></li>
            <li class="list-group-item ">Dias desde que se abrio : <%= incidencia.getDias()%></li>
            <li class="list-group-item ">Estado :
              <div class="progress">
                <div class="progress-bar <%= (incidencia.getIdTecnico() == 0 )? "bg-danger" : "bg-primary "%> bg-primary progress-bar-striped progress-bar-animated text-center" role="progressbar" aria-label="Animated striped example" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100" style="width: <%= (incidencia.getIdTecnico() == 0 )? 33 : 66 %>%">
                  <%= (incidencia.getIdTecnico() == 0 )?"POR ASIGNAR": "ASIGNADA"%>
                </div>
              </div>
            </li>
            <!-- BEGIN datos de la incidencia por el tecnico -->

            <li class="list-group-item ">Tecnico asignado : <%= incidencia.getNombreTecnico() %></li>
            <li class="list-group-item ">Id del tecnico asignado : <%= incidencia.getIdTecnico() %></li>

            <!-- FIN datos de la incidencia por el tecnico -->

          </ul>
        </div>
      </div>
      <!-- FIN Cuerpo de la incidencia detallada -->
      <div class="modal-footer bg-transparent">
        <button type="button" class="btn btn-danger fw-bold" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
  <!-- FIN Incidencia long data Modal -->
</div>

<!-- BEGIN LIST Tecnicos -->
<div class="modal fade" id="tecnicosModal_<%=counterSetOffcanvas%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header bg-primary text-white">
        <h5 class="modal-title fw-bold text-center ">Asigna un tecnico a la incidencia</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>

      <div id="alert-set-incidencia"></div>
      <!-- BEGIN Cuerpo de la incidencia detallada -->
      <div class="modal-body">
        <%-- check if there are tecnico--%>
          <%-- TAbles --%>
          <table class="table table-sm table-light text-center table-primary table-hover table-striped">
            <thead>
            <tr class="m-2">
              <th class="col-1 text-center" scope="col">Numero</th>
              <th class="col-2 text-center" scope="col">ID</th>
              <th class="col-8 text-center" scope="col">Name</th>
              <th class="col-1 text-center" scope="col">Incidencias asignada</th>
              <th class="col-2 text-center" scope="col">Prioridad media</th>
              <th class="col-2 text-center" scope="col"></th>

            </tr>
            </thead>
            <tbody>
        <%
          /*check is there are tecnico*/
          if ( !tecnicos.isEmpty() ){
            // solo es la id de mostrar los detalles del tecnico en fila
            int idRowTecnico = 1;
            // interacion tecnico
            for (TecnicoDataClass tecnicoData : tecnicos) {

              // contador para cada tecnico
              indexTecnico++;

              // si el tecnico ya esta en la incidenia no se muestra para asignarle
              if ( incidencia.getIdTecnico() == tecnicoData.getId()  ){
                continue;
              }


        %>


            <!-- primer dato de tecnicos-->
            <tr>
                <%--num index--%>
                <td><%= idRowTecnico++ %></td>
                <%--Id tecnico--%>
                <td><%= tecnicoData.getId() %></td>
                <%--Nombre del tecnico--%>
                <td class="text-capitalize"><%= tecnicoData.getNombre()+" "+ tecnicoData.getApel()%></td>
                <%--Num incidencias asignadas--%>
                <td><%= tecnicoData.getIncidenciasAbiertas()%></td>
                <%--Prioridad media --%>
                <td><%= tecnicoData.getPrioridadMediaIncidenciaTecnico()%></td>
                <%--Acciones de asignar--%>
                <td>
                  <form id="form-user-set-incidenia-tecnico-<%=indexTecnico%>" class="text-center"  >
                    <div class="btn-group ">
                      <input type="hidden" value="<%=incidencia.getId()%>" name="idIncidencia">
                      <input type="hidden" value="<%=tecnicoData.getId()%>" name="idTecnico">
                      <input type="hidden" value="2" name="idType">
                      <input type="hidden" value="<%=counterSetOffcanvas%>" name="idModal">
                    </div>
                    <button type="button" class="btn btn-primary" onclick="setIncidentTecnico(<%=indexTecnico%>)" ><i class="fas fa-check"></i></button>
                  </form>
                </td>
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
        %>
          <div class="col" >
            <div class="card border-left-primary shadow h-100 py-5 text-center">
              <p class="h4">
                <i class="bi bi-menu-up"></i>
                <b> NO HAY TECNICO AUN REGISTRADOS PARA AGIGNARLE LA INCIDENCIA ...  </b>
              </p>
            </div>
          </div>
        <%
          }
        %>

      </div>
      <!-- FIN Cuerpo de la incidencia detallada -->
      <div class="modal-footer bg-transparent">
        <button type="button" class="btn btn-danger fw-bold" data-bs-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
  <!-- FIN Incidencia long data Modal -->
</div>

<!-- BEGIN Incidencia long data Modal message -->
<div class="modal fade" id="messageModal_<%=counterSetOffcanvas%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header bg-primary text-white">
        <h5 class="modal-title fw-bold text-center ">¿Quieres enviar un mensaje al usuario que creo la incidencia?</h5>
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
        <form id="form-send-message-<%=counterSetOffcanvas%>" class="formAddTask">

          <div class="form-group ">
            <label class="form-label fw-bold" >Descripción el mensaje</label>
            <input type="text" class="form-control" name="message" minlength="1" maxlength="200" placeholder="Descripción del mensaje" required>
            <input type="hidden" name="idEmmitter" value="<%= ((Admin)session.getAttribute("user")).getId() %>">
            <input type="hidden" name="idReceptor" value="<%=  incidencia.getIdUsuario() %>">
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
      <b> NO HAY INCIDENCIAS CREADAS POR LOS USUARIOS ...  </b>
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
