<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="models.*" %><%--
  Created by IntelliJ IDEA.
  User: ariel
  Date: 15/06/2022
  Time: 16:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<%
  User user = (User) session.getAttribute("user");

  if( user != null ){

    // instancia de gestion app
    GestionAPP app = new GestionAPP();



%>

<!-- Card Header - Dropdown -->
<div
        class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
  <h6 class="m-0 font-weight-bold text-primary h4">Mensajería...</h6>
</div>

<div class="container text-center my-2">
  <div class="col align-self-end">
    <button class="btn btn-outline-success btn-lg" type="button" data-bs-toggle="modal" data-bs-target="#messageModalAnyUser" ><i class="bi bi-plus-circle-fill"></i> Nuevo mensaje</button>
  </div>
</div>



<!-- Card Body -->
<div class="card-body">

  <%

    ArrayList<Message> messages = app.buscaMessageByUserId(user.getId() );

    if ( !app.buscaMessageByUserId(user.getId() ).isEmpty() ){

  %>


  <%--%%%%%%%% recibido %%%%%%%%%%--%>
  <%--%%%%%%%% recibido %%%%%%%%%%--%>

  <!-- Heading  OTROS MENUS -->
  <div class="m-0 font-weight-bold text-primary h6">
    Mensajes recibidos...
  </div>



  <!-- Las tablas aqui donde muestra  -->
  <table class="table table-sm table-light text-center table-primary table-hover table-striped">
    <thead>
    <tr class="m-2">
      <th class="col-1 text-center" scope="col">&nbsp;Estado</th>
      <th class="col-2 text-center" scope="col">Desde</th>
      <th class="col-2 text-center" scope="col">Fecha</th>
      <th class="col-4 text-center" scope="col">Mensaje</th>
      <th class="col-3 text-center" scope="col">Accciones</th>
    </tr>
    </thead>
    <tbody>

    <!-- primer dato de tareas-->
    <%
      // counter to set form id
      int counterRecibido = 1;

      for ( Message message : messages.stream().filter( st -> st.getReceptor().getId() == user.getId() ).toList() ) {
        // set dynamic id name

        counterRecibido++;



    %>
    <tr>
      <%--marca el estado--%>
      <td><i class="<%= message.isReadMessage()?  "bi bi-envelope-open-fill text-secondary" : "bi bi-envelope-fill text-success " %>"></i></td>
      <%--marca el nombre del que lo envia--%>
      <td class="text-capitalize" ><%= message.getEmitter().getNombre().toLowerCase()+" "+message.getEmitter().getApel().toLowerCase() %></td>
      <%--marca la fecha enviada--%>
      <td><%= message.getDateSent() %></td>
      <%--cuerpo del mensaje--%>
      <td class="text-capitalize"><%= message.getContent().toLowerCase() %></td>
      <td>
        <form id="form-message-dashboard-<%=counterRecibido%>" class="text-center"  method="post">
          <div class="btn-group ">
            <%--value from idMessage--%>
            <input type="hidden" value="<%= message.getId() %>" name="idMessage">
              <%--value from idUser loguin--%>
            <input type="hidden" value="<%= user.getId() %>" name="idUser">
          </div>

          <%--clasificar los botones segun si ya ha sido leido--%>
          <% if ( message.isReadMessage() ){ %>
          <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModalMessageOpen_<%=counterRecibido%>"><i class="bi bi-envelope-open-fill"></i></button>
          <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModalMessageReply_<%=counterRecibido%>" ><i class="bi bi-reply-fill"></i></button>
          <button type="button"  class="btn btn-danger" onclick="removeMessage(<%=counterRecibido%>)" ><i class="fas fa-trash"></i></button>
          <% }else {%>
            <button type="button" class="btn btn-primary"  onclick="openMarkMessage(<%=counterRecibido%>)" ><i class="bi bi-envelope-fill"></i></button>
          <% } %>
        </form>
      </td>
    </tr>

    <%-- MODALS --%>




    <div class="modal fade" id="exampleModalMessageOpen_<%=counterRecibido%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <div class="modal-header bg-primary text-white">
            <h5 class="modal-title fw-bold text-center ">Resumen del mensaje recibido</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <!-- BEGIN Cuerpo de la incidencia detallada -->
          <div class="modal-body">
            
            <!-- body of form -->
            <p class="text-break text-capitalize"> <%= "Enviado desde: "+ message.getEmitter().getNombre()+" " + message.getEmitter().getApel() %> </p>
            <p class="text-break text-capitalize"> <%= message.getContent().toLowerCase() %> </p>
          </div>
          <!-- FIN Cuerpo de la incidencia detallada -->
        </div>
      </div>
      <!-- FIN Incidencia long data Modal -->
    </div>

    <%-- FINAL MODALS --%>

    <%
      }
    %>

    </tbody>
  </table>

  <%-- interaciones de los modales recibido--%>
  <%
    int counterRecibidoModal = 1;
    for ( Message message : messages.stream().filter( st -> st.getReceptor().getId() == user.getId() ).toList() ) {
      // set dynamic id name
      counterRecibidoModal++;



  %>
  <div class="modal fade" id="exampleModalMessageReply_<%=counterRecibidoModal%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header bg-primary text-white">
          <h5 class="modal-title" >¿Quieres responder el mensaje?</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">

          <div id="id-alert-message">
            <div class=" alert alert-warning text-center" role="alert">
              <i class="bi bi-exclamation-triangle text-danger "></i>
              ¡Describe brevemente el mensaje!
            </div>
          </div>

          <form id="form-reply-message-number<%=counterRecibidoModal%>">

            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="floatingInput" name="message" minlength="1" maxlength="200" placeholder="...." required>
              <label for="floatingInput">Descripción del mensaje</label>
            </div>
            <input type="hidden" name="idEmmitter" value="<%= user.getId() %>">
            <input type="hidden" name="idReceptor" value="<%=  message.getEmitter().getId() %>">
            <input type="hidden" name="idIncidenia" value="10000">

            <hr class="sidebar-divider">

            <div class="d-flex flex-row-reverse px-2 ">
              <button type="button" class="btn btn-primary m-1 shadow-lg" onclick="replyMessage(<%=counterRecibidoModal%>)" >Enviar</button>
              <button type="reset" class="btn btn-secondary m-1 shadow-lg" data-dismiss="modal">Reset</button>
            </div>

          </form>

        </div>
      </div>
    </div>
  </div>
  <%
    }
  %>
  <%--%%%%%%%% Recibido END %%%%%%%%%%--%>
  <%--%%%%%%%% Recibido END %%%%%%%%%%--%>


  <%--%%%%%%%% Enviado %%%%%%%%%%--%>
  <%--%%%%%%%% Enviado %%%%%%%%%%--%>

  <!-- Heading  OTROS MENUS -->
  <div class="m-0 font-weight-bold text-primary h6">
    Mensajes enviados...
  </div>



  <!-- Las tablas aqui donde muestra  -->
  <table class="table table-sm table-light text-center table-primary table-hover table-striped">
    <thead>
    <tr class="m-2">
      <th class="col-1 text-center" scope="col">&nbsp;Estado</th>
      <th class="col-2 text-center" scope="col">Para</th>
      <th class="col-2 text-center" scope="col">Fecha</th>
      <th class="col-4 text-center" scope="col">Mensaje</th>
      <th class="col-3 text-center" scope="col">Accciones</th>
    </tr>
    </thead>
    <tbody>

    <!-- primer dato de tareas-->
    <%
      int counterEnviado = 0;

      for ( Message message : messages.stream().filter( st -> st.getEmitter().getId() == user.getId() ).toList() ) {
        // set dynamic id name

        counterEnviado--;



    %>
    <tr>
      <%--marca el estado--%>
      <td><i class="<%= message.isReadMessage()?  "bi bi-envelope-paper-fill text-success" : "bi bi-envelope-fill text-danger " %>"></i></td>
      <%--marca el nombre del que lo envia--%>
      <td class="text-capitalize" ><%= message.getReceptor().getNombre().toLowerCase()+" "+message.getReceptor().getApel().toLowerCase() %></td>
      <%--marca la fecha enviada--%>
      <td><%= message.dateFormatterSent() %></td>
      <%--cuerpo del mensaje--%>
      <td class="text-capitalize"><%= message.getContent().toLowerCase() %></td>
      <td>
        <form id="form-message-dashboard-<%=counterEnviado%>" class="text-center"  method="post">
          <div class="btn-group ">
            <%--value from idMessage--%>
            <input type="hidden" value="<%= message.getId() %>" name="idMessage">
            <%--value from idUser loguin--%>
            <input type="hidden" value="<%= user.getId() %>" name="idUser">
          </div>

          <%--clasificar los botones segun si ya ha sido leido--%>
          <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModalMessageOpen_<%=counterEnviado%>"><i class="bi bi-envelope-open-fill"></i></button>
          <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModalMessageReply_<%=counterEnviado%>" ><i class="bi bi-reply-fill"></i></button>
          <button type="button"  class="btn btn-danger" onclick="removeMessage(<%=counterEnviado%>)" ><i class="fas fa-trash"></i></button>

        </form>
      </td>
    </tr>

    <%-- MODALS --%>




    <div class="modal fade" id="exampleModalMessageOpen_<%=counterEnviado%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content">
          <div class="modal-header bg-primary text-white">
            <h5 class="modal-title fw-bold text-center ">Resumen del mensaje recibido</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <!-- BEGIN Cuerpo de la incidencia detallada -->
          <div class="modal-body">

            <!-- body of form -->
            <p class="text-break text-capitalize"> <%= "Enviado desde: "+ message.getEmitter().getNombre()+" " + message.getEmitter().getApel() %> </p>
            <p class="text-break text-capitalize"> <%= message.getContent().toLowerCase() %> </p>
          </div>
          <!-- FIN Cuerpo de la incidencia detallada -->
        </div>
      </div>
      <!-- FIN Incidencia long data Modal -->
    </div>

    <%-- FINAL MODALS --%>

    <%
      }
    %>

    </tbody>
  </table>

  <%-- interaciones de los modales recibido--%>
  <%
    int counterEnviadoModal = 0;
    for ( Message message : messages.stream().filter( st -> st.getReceptor().getId() == user.getId() ).toList() ) {
      // set dynamic id name
      counterEnviadoModal--;



  %>
  <div class="modal fade" id="exampleModalMessageReply_<%=counterEnviadoModal%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header bg-primary text-white">
          <h5 class="modal-title" >¿Quieres responder el mensaje?</h5>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">

          <div id="id-alert-message2">
            <div class=" alert alert-warning text-center" role="alert">
              <i class="bi bi-exclamation-triangle text-danger "></i>
              ¡Describe brevemente el mensaje!
            </div>
          </div>

          <form id="form-reply-message-number<%=counterEnviadoModal%>">

            <div class="form-floating mb-3">
              <input type="text" class="form-control"  name="message" minlength="1" maxlength="200" placeholder="Hola quiero ..." required>
              <label for="floatingInput">Descripción del mensaje</label>
            </div>
            <input type="hidden" name="idEmmitter" value="<%= user.getId() %>">
            <input type="hidden" name="idReceptor" value="<%=  message.getEmitter().getId() %>">
            <input type="hidden" name="idIncidenia" value="10000">

            <hr class="sidebar-divider">

            <div class="d-flex flex-row-reverse px-2 ">
              <button type="button" class="btn btn-primary m-1 shadow-lg" onclick="replyMessage(<%=counterEnviadoModal%>)">Enviar</button>
              <button type="reset" class="btn btn-secondary m-1 shadow-lg" data-dismiss="modal">Reset</button>
            </div>

          </form>

        </div>
      </div>
    </div>
  </div>
  <%
    }
  %>
  <%--%%%%%%%% Enviado END %%%%%%%%%%--%>
  <%--%%%%%%%% Enviado END %%%%%%%%%%--%>







</div>
  <%
    }else {
  %>

                      <div class="col" >
                        <div class="card border-left-primary shadow h-100 py-5 text-center">
                          <p class="h4">
                            <b> NO TIENE MENSAJES AUN..  </b>
                            <i class="fa fa-calendar-week"></i>
                          </p>
                        </div>
                      </div>



<%
    }
%>

      <%-- %%%%%%%%%%%%%%% Composision de un mensaje %%%%%%%%%%%%%%%%--%>
      <!-- BEGIN Incidencia long data Modal message -->
      <div class="modal fade" id="messageModalAnyUser" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header bg-primary text-white">
              <h5 class="modal-title fw-bold text-center ">Redactando un mensaje nuevo</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <!-- BEGIN Cuerpo de la incidencia detallada -->
            <div class="modal-body">

              <div id="id-alert-new-message">
                <div class=" alert alert-warning text-center" role="alert">
                  <i class="bi bi-exclamation-triangle text-danger "></i>
                  ¡Describe brevemente el mensaje!
                </div>
              </div>

              <!-- body of form -->
              <form id="form-new-compose-message" class="formAddTask">

                <div class="form-group  ">
                  <select class="form-select" name="idReceptor" aria-label="Default select example" required>
                    <option selected>Escoge a quien quieres enviar el mensaje</option>
                    <%
                      // se buscan los user de sistema
                      // USUARIOS
                      for (UsuarioDataClass  usuarioData : app.getUsuarios().stream().filter( st -> st.getId() != user.getId()).toList()  ) {
                    %>
                    <option class="text-capitalize fw-semibold" value="<%=usuarioData.getId()%>" ><%= String.format( "%s %s - %20S",usuarioData.getNombre(), usuarioData.getApel(), "Usuario"  )%></option>
                    <%
                      }
                    %>

                    <%
                      // se buscan los user de sistema
                      // TTECNICOS
                      for (TecnicoDataClass tecnicoData : app.getTecnicos().stream().filter( st -> st.getId() != user.getId()).toList()  ) {
                    %>
                    <option class="text-capitalize fw-semibold" value="<%=tecnicoData.getId()%>" ><%= String.format( "%s %s - %30S",tecnicoData.getNombre(), tecnicoData.getApel(), "Tecnico"  )%></option>
                    <%
                      }
                    %>

                    <%
                      // se buscan los user de sistema
                      // ADMINS
                      for (Admin adminData : app.getAdmins().stream().filter( st -> st.getId() != user.getId()).toList()  ) {
                    %>
                    <option class="text-capitalize fw-semibold" value="<%=adminData.getId()%>" ><%= String.format( "%s %s - %40S",adminData.getNombre(), adminData.getApel(), "Admin"  )%></option>
                    <%
                      }
                    %>

                  </select>
                  <div class="form-floating my-3">
                    <input type="text" class="form-control"  name="message" minlength="1" maxlength="200" placeholder="...." required>
                    <label for="floatingInput">Descripción del mensaje</label>
                  </div>
                  <input type="hidden" name="idEmmitter" value="<%= user.getId() %>">
                  <input type="hidden" name="idIncidenia" value="2000">
                </div>

                <hr class="sidebar-divider">

                <div class="d-flex flex-row-reverse px-2 ">
                  <button type="button" class="btn btn-primary m-1 shadow-lg" onclick="newComposeMessage()" >Enviar</button>
                  <button type="reset" class="btn btn-secondary m-1 shadow-lg" data-dismiss="modal">Reset</button>
                </div>
              </form>
            </div>
            <!-- FIN Cuerpo de la incidencia detallada -->
          </div>
        </div>
        <!-- FIN Incidencia long data Modal -->
      </div>
      <%-- %%%%%%%%%%%%%%% END Composicion de un mensaje %%%%%%%%%%%%%%%%--%>

<%
  // resend to index
  }else{
  response.sendRedirect( "index.jsp" );
  }

%>
