<%@ page import="models.Usuario" %>
<%@ page import="models.GestionAPP" %>
<%@ page import="models.IncidenciaDataClass" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: ariel
  Date: 29/06/2022
  Time: 10:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Bienvenido Usuario - Dashboard</title>
    <%--favicon--%>
    <link rel="icon" type=”image/svg+xml” href="${pageContext.request.contextPath}/img/cat-solid.svg" >

    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <!-- jQuery import -->
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>

    <%--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa" crossorigin="anonymous"></script>--%>


    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
    <!-- backgroung set -->
    <link href="css/generalAllPages.css" rel="stylesheet" type="text/css">

    <!-- Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

    <!-- JavaScript to set config of AJAX-->
    <!-- Custom scriptss for all Form -->
    <script type="text/javascript" src="js/usuario.js"></script>
    <script type="text/javascript" src="js/adminGeneral.js"></script>

</head>


<body id="page-top">

<!-- check if the user is logging-->

<%
// instance of GestionApp MVC
GestionAPP app = new GestionAPP();

// instance of usuario
Usuario usuario = null;

// check if user is instance of Usuario
if( session.getAttribute("user") != null && session.getAttribute("user") instanceof Usuario ){
    // set the user login like Usuario and token validated
    usuario = (Usuario) session.getAttribute("user");

    // check if user is null 2 time an check if token has been validated
    if ( usuario != null  && usuario.isTokenValidated() ){

        //get incidencias abiertas no asignadas y asignadas
        ArrayList<IncidenciaDataClass> incidenciasAbiertas = app.buscaIncidenciasAbiertasbyUsuario(usuario.getId() );


%>

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="Usuario.jsp">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-cat"></i>
            </div>
            <div class="sidebar-brand-text mx-3 mt-2 text-light title-main h5">FernTickets</div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">

        <!-- Nav Item - Dashboard -->
        <li class="nav-item active text-center ali my-3">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary " data-toggle="modal" data-target="#exampleModalCenter">
                <i class="bi bi-plus-circle-fill text-gray-500"></i>
                <span>Reportar una incidencia</span>
            </button>

            <!-- Modal pop out add task-->
            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header bg-primary">
                            <h5 class="modal-title text-white" id="exampleModalLongTitle"><b>Reportar una incidencia</b></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">

                            <div id="id-alert-report">
                                <div class=" alert alert-warning" role="alert">
                                    <i class="bi bi-exclamation-triangle text-danger"></i>
                                    ¡Describe brevemente el reporte!
                                </div>
                            </div>

                            <!-- body of form -->
                            <form id="form-user-add" class="formAddTask"  method="post" >
                                <div class="form-group ">
                                    <label class="form-label fw-bold" >Description del reporte</label>
                                    <input type="text" class="form-control" name="description" minlength="1" maxlength="200" placeholder="Descripción de la incidencia" required>
                                </div>
                                <label class="form-label fw-bold" for="customRange2">Prioridad del reporte</label>
                                <div class="range">
                                    <input type="range" name="prioridad" class="form-range"  min="1" max="10" id="customRange2" />
                                </div>
                                <!-- BEGIN Number para el range scale -->
                                <div class="d-flex justify-content-between">
                                    <div >1</div>
                                    <div >2</div>
                                    <div >3</div>
                                    <div >4</div>
                                    <div >5</div>
                                    <div >6</div>
                                    <div >7</div>
                                    <div >8</div>
                                    <div >9</div>
                                    <div >10</div>
                                </div>
                                <!-- END Number para el range scale -->

                                <div class="d-flex justify-content-between">
                                    <div class=" fw-bold">Minima</div>
                                    <div class=" fw-bold">Maxima</div>
                                </div>

                                <hr class="sidebar-divider">

                                <div class="d-flex flex-row-reverse px-2">
                                    <button type="submit" class="btn btn-primary m-1 shadow-lg"  >Reportar</button>
                                    <button type="button" class="btn btn-secondary m-1 shadow-lg" data-dismiss="modal">Close</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider">

        <!-- Heading  ORDER TASK BY -->
        <div class="sidebar-heading">
            Consultar mis incidencias
        </div>

        <!-- Nav Item - SEARCHING OPEN INCIDENT  -->
        <li class="nav-item">
            <a class="nav-link"  onclick="usuarioIncidentOpen()">
                <i class="fas fa-duotone fa-calendar-day"></i>
                <span>Incidencias abiertas</span></a>
        </li>

        <!-- Nav Item - SEARCHING CLOSE INCIDENT  -->
        <li class="nav-item">
            <a class="nav-link"  onclick="usuarioIncidentClose()">
                <i class="fas fa-duotone fa-calendar-day"></i>
                <span>Incidencias cerradas</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider bg-black">

        <!-- Heading  OTROS MENUS -->
        <div class="sidebar-heading">
            Otras opciones
        </div>

        <!-- Nav Item - ESTADISTICA DE LA APLICACION  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="usuarioSearchMessage()">
                <i class="bi bi-envelope"></i>
                <span>Mensajería</span></a>
        </li>







        <!-- Divider -->
        <hr class="sidebar-divider d-none d-md-block">

        <!-- Sidebar Toggler (Sidebar) -->
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
        </div>



    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                <!-- Sidebar Toggle (Topbar) -->
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>


                <!-- Topbar Navbar -->
                <ul class="navbar-nav ml-auto">

                    <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                    <li class="nav-item dropdown no-arrow d-sm-none">
                        <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-search fa-fw"></i>
                        </a>
                        <!-- Dropdown - Messages -->
                        <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in"
                             aria-labelledby="searchDropdown">
                            <form class="form-inline mr-auto w-100 navbar-search">
                                <div class="input-group">
                                    <input type="text" class="form-control bg-light border-0 small"
                                           placeholder="Search for..." aria-label="Search"
                                           aria-describedby="basic-addon2">
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="button">
                                            <i class="fas fa-search fa-sm"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </li>





                    <div class="topbar-divider d-none d-sm-block"></div>

                    <!-- Nav Item - User Information -->
                    <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">

                            <span class="mr-2 d-none d-lg-inline text-gray-600 small">

                                <!-- Show out the name of user -->

                            <% out.println( usuario.getNombre()+" "+usuario.getApel() );%>

                            </span>
                            <img class="img-profile rounded-circle"
                                 src="img/avatar-cat.svg">
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                             aria-labelledby="userDropdown">

                            <!-- button para ver perfil del usuario -->
                            <button class="dropdown-item" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasPerfil" aria-controls="offcanvasRight">
                                <i class="fas fa-frog fa-sm fa-fw mr-2 text-gray-400"></i>
                                Ver perfil
                            </button>

                            <div class="dropdown-divider"></div>

                            <!-- button para restablecer la clave mediante un offcanvas -->
                            <button class="dropdown-item" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
                                <i class="fas fa-solid fa-unlock fa-sm fa-fw mr-2 text-gray-400"></i>
                                Cambiar contraseña
                            </button>

                            <div class="dropdown-divider"></div>

                            <!-- button para hecer logout -->
                            <form action="/FernanTickets/LogOut" method="post">
                                <input type="hidden" name="logout" value="true">
                                <button type="submit" class="dropdown-item" >
                                    <i class="fas fa-sign-out-alt fa-sm fa-fw mr-2 text-gray-400"></i>
                                    LogOut
                                </button>
                            </form>

                        </div>
                    </li>

                </ul>

            </nav>
            <!-- End of Topbar -->

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-0 text-gray-800">Panel de sus incidencias reportadas</h1>
                </div>

                <!-- Content Row -->
                <div class="row py-3">


                    <!-- First Task Card Example -->
                    <div class="col-md-6 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            Incidencias sin asignar ...</div>
                                        <div class="h6 mb-0 font-weight-bold text-gray-800">

                                            <%-- el numero de incidecnias abiertas sin asignar --%>
                                            <%= app.incidenciasNoAsignadasbyUsuario( usuario.getId() )  %>


                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="bi bi-clipboard2-plus-fill fa-2x"></i>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Second Task Card Example if is empty -->
                    <div class="col-md-6 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            Incidencias asignadas ...</div>
                                        <div class="h6 mb-0 font-weight-bold text-gray-800">

                                            <%-- el numero de incidecnias abiertas asignadas --%>
                                            <%= app.incidenciasAsignadasbyUsuario( usuario.getId() )  %>

                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="bi bi-clipboard2-check-fill fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>



                <!-- Check if there are not Incident -->
                <div id="id-incident-board" class="row">
                <%
                    // check if there are incident Open
                    if ( !incidenciasAbiertas.isEmpty() ){

                %>

                    <%-- titulo de las incidencias --%>
                    <div class="col-lg-12 pb-3 pb-md-4">
                        <h1 class="h5 fst-italic mb-0 text-gray-600 fw-bolder text-capitalize">Resumen corto de las incidencias reportadas</h1>
                    </div>

                <%
                            // contador para marcar el contador del modal para abrir el correcto
                            int counterSetOffcanvas = 0;
                            // interador para las incidencias abiertas usuario
                            for (IncidenciaDataClass incidencia : incidenciasAbiertas) {
                                //
                                counterSetOffcanvas++;



                %>

                <!-- BEGIN Content Row Incidencias data-->



                    <!-- BEGIN Incidencia short data -->
                    <div class="col-lg-6 pb-3 pb-md-4">
                        <div class="card shadow-lg border-primary">
                            <div class="card-header text-center bg-primary text-light fs-5 fw-bold ">
                                Resumen corto Incidencia
                            </div>
                            <div class="card-body text-light">
                                <ul class="list-group list-group-flush">
                                    <li class="list-group-item ">Comentario : <%= incidencia.getDescripcion()%></li>
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
                                    <button class="btn btn-primary" type="button" data-bs-toggle="modal" data-bs-target="#exampleModal_<%=counterSetOffcanvas%>" >Mas informacion ...</button>
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

                                            <li class="list-group-item ">Tecnico asignado : <%= (incidencia.getIdTecnico() == 0 )? "NO ASIGNADO AUN" : incidencia.getNombreTecnico() %></li>
                                            <li class="list-group-item ">Id del tecnico asignado : <%= (incidencia.getIdTecnico() == 0 )? "NO ASIGNADO AUN" : incidencia.getIdTecnico() %></li>

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


                    <!-- BEGIN Incidencia long data Modal message -->
                    <div class="modal fade" id="messageModal_<%=counterSetOffcanvas%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                            <div class="modal-content">
                                <div class="modal-header bg-primary text-white">
                                    <h5 class="modal-title fw-bold text-center ">¿Quieres enviar un mensaje en relación con tu incidencia?</h5>
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
                                            <input type="hidden" name="idEmmitter" value="<%= incidencia.getIdUsuario() %>">
                                            <input type="hidden" name="idReceptor" value="<%= ( incidencia.getIdTecnico() == 0 )?  182789292 : incidencia.getIdTecnico()  %>">
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

                <!-- FIN Content Row Incidencias data-->
                <%
                        // close colon of fori
                    }
                 %>

                </div>

                <!-- FIN Check if there are not Incident -->

                <%
                        // close colon from check if incident is empty
                    }else{
                %>
                        <div class="col" >
                           <div class="card border-left-primary shadow h-100 py-5 text-center">
                              <p class="h4">
                                  <i class="bi bi-menu-up"></i>
                                  <b> NO HAY INCIDENCIAS REPORTADAS ABIERTAS ..  </b>
                              </p>
                            </div>
                        </div>
                <% } %>


            <!-- FIN Page Content -->
            </div>


        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Forest Production by Ariel 2021</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->


    <!-- seccion of modal offcanvas -->
    <!-- offcanvas para restablecer la clave -->
    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
        <div class="offcanvas-header bg-primary text-light rounded-bottom shadow-sm">
            <h5 class="offcanvas-title" id="offcanvasRightLabel">Quieres restablecer la contraseña?</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>

        <!-- alert restablecer contrasena -->
        <div id="alert-restablecer" class="alert alert-warning " role="alert">
        </div>
        <!-- FIN alert restablecer contrasena -->

        <div class="offcanvas-body fw-bold m-4 border  rounded-2 shadow-lg">
            <form id="form-user-key" method="post" >

                <div class="mb-3">
                    <label for="formGroupExampleInput2" class="form-label">Nueva contraseña</label>
                    <input type="password" class="form-control" name="password" id="formGroupExampleInput2" placeholder="Nueva contraseña">
                </div>
                <div class="mb-3">
                    <label for="formGroupExampleInput2" class="form-label">Confirmar contraseña</label>
                    <input type="password" class="form-control" name="passwordRepetir" id="formGroupExampleInput3" placeholder="Repite contraseña">
                </div>

                <div class="d-flex justify-content-center mx-auto pt-4 btn-group " style="width: 4em;">
                    <button type="reset" class="btn btn-outline-danger" style="min-width: 8em;">Borrar</button>
                    <button type="submit" class="btn btn-primary" style="min-width: 8em;">Restablecer</button>
                </div>

            </form>
        </div>
    </div>
    <!-- FIN offcanvas para restablecer la clave -->



    <!-- offcanvas para ver el perfil del usuario -->
    <div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasPerfil" aria-labelledby="offcanvasRightLabel">
        <div class="offcanvas-header bg-primary text-light rounded-bottom shadow-sm">
            <h5 class="offcanvas-title" id="offcanvasRightLabelPerfil">Perfil del usuario</h5>
            <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
        </div>
        <div class="offcanvas-body fw-bold m-4 border  rounded-2 shadow-lg">

            <div class="text-center">
                <img class="img-fluid  rounded-circle" src="img/avatar-cat.svg" width="30%">
            </div>

            <div class="text-capitalize">
                <p class="fw-semibold">Nombre: </p>
                <!-- get nombre del usuario -->
                <p><%= usuario.getNombre() + " " + usuario.getApel()%></p>
            </div>
            <div class="text-capitalize">
                <p class="fw-semibold text-capitalize">Email: </p>
                <!-- get email del usuario -->
                <p class="text-lowercase" ><%= usuario.getEmail() %></p>

            </div>
        </div>
    </div>
    <!-- FIN offcanvas para ver el perfil del usuario -->

    <!-- Fin seccion of modal offcanvas -->


</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>



<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>




<!-- check if the user is logging-->

<%

        }else {

            // asigno el usuario a una variable temporal para procesarla en el TokenValidate.jsp
            session.setAttribute("userTokenValidate", usuario );
            // anulo la session del usuario guardado en session.getAtribute("user");
            session.removeAttribute("user");
            // take to usuario to validate the token
            response.sendRedirect( "TokenValidate.jsp" );
    }
    }else {
        // if no user login or no Usuario Resend to index.jsp
        response.sendRedirect( "index.jsp" );
    }

%>





</body>

</html>
