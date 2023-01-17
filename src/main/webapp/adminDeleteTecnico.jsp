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
  // get all tecnico
  ArrayList<TecnicoDataClass> tecnicos = app.getTecnicos();

  // if user isn't logged in resent to index.jsp
  if ( session.getAttribute("user") != null || session.getAttribute("user") instanceof Admin){

%>
<!-- Card Header - Dropdown -->
<div
  class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
  <h6 class="m-0 font-weight-bold text-primary h4"> ¿Quieres borrar un técnico? ...</h6>
</div>
<!-- Card Body -->
<div class="card-body">
<%

    // check if Arraylist<TecnicoDataClass> is no empty
    if ( !tecnicos.isEmpty() ){


%>



<!-- BEGIN LIST Tecnicos -->

      <!-- set alert from server here -->
      <div id="alert-tecnico-delete">
        <div class=" alert alert-warning" role="alert">
          <i class="bi bi-exclamation-triangle "></i>
          ¡Recuerda, solo puedes borrar técnicos que no tienen incidencias abiertas o cerradas, en caso de que tenga una incidencia asignada se puede pasar a otro técnico en el menú de INCIDENCIAS ABIERTAS!
        </div>
      </div>

        <table class="table table-sm table-light text-center table-primary table-hover table-striped">
          <thead>
          <tr class="m-2">
            <th class="col-1 text-center" scope="col">Numero</th>
            <th class="col-1 text-center" scope="col">ID</th>
            <th class="col-5 text-center" scope="col">Name</th>
            <th class="col-1 text-center" scope="col">Incidencias asignadas</th>
            <th class="col-1 text-center" scope="col">Incidencias cerradas</th>
            <th class="col-3 text-center" scope="col"></th>

          </tr>
          </thead>
          <tbody>
          <%
              // index de la tabla de los tecnicos
              int indexTecnico = 1;
              // interacion tecnico
              for (TecnicoDataClass tecnicoData : tecnicos) {


          %>


          <!-- primer dato de tecnicos-->
          <tr>
            <%--num index--%>
            <td><%= indexTecnico++ %></td>
            <%--Id tecnico--%>
            <td><%= tecnicoData.getId() %></td>
            <%--Nombre del tecnico--%>
            <td class="text-capitalize"><%= tecnicoData.getNombre()+" "+ tecnicoData.getApel()%></td>
            <%--Num incidencias asignadas--%>
            <td><%= tecnicoData.getIncidenciasAbiertas()%></td>
            <%--Prioridad media --%>
            <td><%= tecnicoData.getIncidenciasCerradas()%></td>
            <%--Acciones de asignar--%>
            <td>
              <%--set to admin tecnico cant delete it have closed incidents--%>
              <%
                /*check if tecnico have closed incident*/
                if ( tecnicoData.getIncidenciasCerradas() == 0 ){


              %>
              <form id="form-user-delete-tecnico-<%=indexTecnico%>" class="text-center"  >
                <div class="btn-group ">
                  <input type="hidden" value="<%=tecnicoData.getId()%>" name="idTecnicoDelete">
                </div>
                <button type="button" class="btn btn-danger" onclick="deleteTecnico(<%=indexTecnico%>)" <%= tecnicoData.getIncidenciasAbiertas()==0?"":"disabled"%> ><i class="fas fa-trash"></i> </button>
              </form>

              <%
              /*set a message*/
                }else {

                  out.print("NO SE PUEDE BORRAR");
              }
              %>

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
            out.println(
                    "<div class=\"col\" >\n" +
                            "                            <div class=\"card border-left-primary shadow h-100 py-5 text-center\">\n" +
                            "                                <p class=\"h4\">\n" +
                            "                                    <b> NO HAY TECNICOS REGISTRADOS AUN..  </b>\n" +
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

