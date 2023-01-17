<%@ page import="models.*" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.rmi.MarshalException" %>
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

    <title>Bienvenido Administrador - Dashboard</title>
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
    <script type="text/javascript" src="js/administrador.js"></script>
    <script type="text/javascript" src="js/adminGeneral.js"></script>

</head>


<body id="page-top">

<!-- check if the user is logging-->

<%
    // instance of GestionApp MVC
    GestionAPP app = new GestionAPP();

// instance of usuario
    Admin admin = null;

// check if user is instance of Usuario
    if( session.getAttribute("user") != null && session.getAttribute("user") instanceof Admin){
        // set the user login like Usuario and token validated
        admin = (Admin) session.getAttribute("user");

        // num incidenicas asignadas
        int incidenciasAsignadas = app.incidenciasAbiertasAsignadas();

        // num incidencia por asignar
        int incidenciasPorAsignar = app.incidenciasAbiertas();

        // ArrayList<Message> Admins's message
        ArrayList<Message> message = app.buscaMessageByUserId( admin.getId() );

        // formatter decimal
        DecimalFormat df=new DecimalFormat("#.##");
        // porcentaje de incidencias asignadas
        float porcentaje = (float) incidenciasAsignadas/( incidenciasPorAsignar + incidenciasAsignadas) * 100;

%>

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="Admin.jsp">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-cat"></i>
            </div>
            <div class="sidebar-brand-text mx-3 mt-2 text-light title-main h5 ">FernTickets</div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0 bg-black">

        <!-- Nav Item - Dashboard -->
        <!-- Nav Item - SEARCHING TO ASIGNAR A INCIDENCIA  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="adminIncidentSet()">
                <i class="bi bi-plus-circle-fill text-gray-500"></i>
                <span class="fw-bold" >Asignar una incidencia</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider bg-black">

        <!-- Heading  ORDER TASK BY -->
        <div class="sidebar-heading">
            Consultar las incidencias
        </div>

        <!-- Nav Item - SEARCHING ALL OPEN INCIDENT  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="adminIncidentOpen()">
                <i class="bi bi-card-text"></i>
                <span>Incidencias abiertas</span></a>
        </li>

        <!-- Nav Item - SEARCHING ALL CLOSE INCIDENT  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="adminIncidentClose()">
                <i class="bi bi-card-text"></i>
                <span>Incidencias cerradas</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider bg-black">

        <!-- Heading  TECNICO -->
        <div class="sidebar-heading">
            Consultar tecnicos
        </div>

        <!-- Nav Item - SEARCHING ALL TECNICOS  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="adminTecnicoDetail()">
                <i class="bi bi-card-text"></i>
                <span>Ver todo tecnicos</span></a>
        </li>

        <!-- Nav Item - TO ADD TECNICO  -->
        <li class="nav-item">
            <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#registrar-tecnico-modal">
                <i class="bi bi-card-text"></i>
                <span>Registrar un tecnico</span></a>
        </li>

        <!-- Nav Item - DELETE A TECNICO  -->
        <li class="nav-item">
            <a class="nav-link" type="button"  onclick="adminDeleteTecnico()">
                <i class="bi bi-card-text"></i>
                <span>Borrar un tecnico</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider bg-black">

        <!-- Heading  USUARIO -->
        <div class="sidebar-heading">
            Consultar usuario
        </div>

        <!-- Nav Item - SEARCHING ALL USUARIO  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="adminUsuarioDetail()">
                <i class="bi bi-card-text"></i>
                <span>Ver todo usuarios</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider bg-black">

        <!-- Heading  OTROS MENUS -->
        <div class="sidebar-heading">
            Otras opciones
        </div>

        <!-- Nav Item - ESTADISTICA DE LA APLICACION  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="adminSearchMessage()">
                <i class="bi bi-envelope"></i>
                <span>Mensajería</span></a>
        </li>

        <!-- Nav Item - ESTADISTICA DE LA APLICACION  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="adminEstadistica()">
                <i class="bi bi-card-text"></i>
                <span>Estadística de la aplicación</span></a>
        </li>

        <!-- Nav Item - CONFIGURACION DEL SISTEMA  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="adminSendResumenTecnico()">
                <i class="bi bi-card-text"></i>
                <span>Enviar resumen incidencia por email al tecnico</span></a>
        </li>

        <!-- Nav Item - SESSIONES DE LOS USUARIOS  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="adminLastSession()">
                <i class="bi bi-card-text"></i>
                <span>Ultima sesiones de los usuarios</span></a>
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

                <!-- Topbar Search -->
                <form id="id-form-search-term"
                      class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                    <div class="input-group">
                        <input type="text" class="form-control bg-light border-0 small" placeholder="Buscar incidencia por término..."
                               name="termFilter" aria-label="Search" aria-describedby="basic-addon2" required>
                        <div class="input-group-append">
                            <button class="btn btn-primary" type="submit">
                                <i class="fas fa-search fa-sm"></i>
                            </button>
                        </div>
                    </div>
                </form>

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

                            <%= admin.getNombre() + " " + admin.getApel()%>

                            </span>
                            <img class="img-profile rounded-circle"
                                 src="img/avatar-pig.svg">
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
                    <h1 class="h3 mb-0 text-gray-800">Dashboard de incidencias</h1>
                </div>

                <!-- Content Row -->
                <div class="row py-3">


                    <!-- First Task Card Example -->
                    <div class="col-md-4 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            Incidencias asignadas ...</div>
                                        <div class="h6 mb-0 font-weight-bold text-gray-800">

                                            <%-- el numero de incidecnias abiertas asignada al tecnico --%>
                                            <%= incidenciasAsignadas  %>


                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="bi bi-clipboard2-plus-fill fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- First Task Card Example -->
                    <div class="col-md-4 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            Incidencias Por Asignar ...</div>
                                        <div class="h6 mb-0 font-weight-bold text-gray-800">

                                            <%-- el numero de incidecnias cerradas asignada al tecnico --%>
                                            <%=   incidenciasPorAsignar - incidenciasAsignadas %>


                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="bi bi-clipboard2-check-fill fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Second Task Card Example if is empty -->
                    <div class="col-md-4 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            Porcentaje de incidencias asignadas  ...</div>
                                        <div class="h6 mb-0 font-weight-bold text-gray-800">

                                            <%-- prioridad media de las incidencias --%>
                                            <%= df.format( porcentaje )  %> %

                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="bi bi-clipboard2-data-fill fa-2x"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>



                <!-- Check if there are not Incident -->
                <!-- BEGIN Content Row Incidencias data-->

                <div id="id-incident-board" class="row">

                <%
                    // check if there are incident Open
                    if ( app.buscarTodasIncidenciasAbiertas().isEmpty() ){


                %>

                    <div class="col" >
                        <div class="card border-left-primary shadow h-100 py-5 text-center">
                            <p class="h4">
                                <i class="bi bi-menu-up"></i>
                                <b> NO HAY INCIDENCIAS ASIGNADAS REPORTADAS AUN ..  </b>
                            </p>
                        </div>
                    </div>

                <% }else{ %>

                    <div id="col-lg-12 d-flex align-items-center pb-3 pb-md-4  h-100 ">
                        <img  src="img/bg-dashboard-user.svg" class="img-fluid mx-auto"  alt="DashBoard">
                    </div>

                <%}%>
                </div>
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
                <img class="img-fluid img-thumbnail rounded-circle" src="img/avatar-pig.svg" width="30%">
            </div>

            <div class="text-capitalize">
                <p class="fw-semibold">Nombre: </p>
                <!-- get nombre del tecnico -->
                <p><%= admin.getNombre() + " " + admin.getApel()%></p>
            </div>
            <div class="text-capitalize">
                <p class="fw-semibold text-capitalize">Email: </p>
                <!-- get email del tecnico -->
                <p class="text-lowercase" ><%= admin.getEmail() %></p>
            </div>
            <div class="text-capitalize">
                <p class="fw-semibold text-capitalize">My ID: </p>
                <!-- get id tecnico-->
                <p class="text-lowercase" ><%= admin.getId() %></p>
            </div>
        </div>
    </div>
    <!-- FIN offcanvas para ver el perfil del usuario -->

    <!-- BEGIN modal para registrar un usuario -->

    <div class="modal fade" id="registrar-tecnico-modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title fw-bold text-center ">¿Quieres Registrar Un Tecnico?</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <!-- BEGIN Cuerpo de la incidencia detallada -->
                <div class="modal-body">

                    <div id="id-alert-register-tecnico">
                        <div class=" alert alert-warning text-center" role="alert">
                            <i class="bi bi-exclamation-triangle text-danger "></i>
                            ¡Recuerda solo usar caracteres alfanuméricos!
                        </div>
                    </div>

                    <!-- body of form -->
                    <form id="form-tecnico-register" class="formAddTask"  method="post">

                        <div class="form-group ">
                            <label class="form-label fw-bold" >Ingresa el nombre del técnico</label>
                            <input type="text" class="form-control" name="tecnicoNombre" minlength="1" maxlength="60" placeholder="Nombre" required>
                            <label class="form-label fw-bold" >Ingresa el apellido del técnico</label>
                            <input type="text" class="form-control" name="tecnicoApels" minlength="1" maxlength="60" placeholder="Apellido" required>
                            <label class="form-label fw-bold" >Ingresa el email del técnico</label>
                            <input type="email" class="form-control" name="tecnicoEmail" minlength="1" maxlength="60" placeholder="Email" required>
                            <label class="form-label fw-bold" >Ingresa la contraseña</label>
                            <input type="password" class="form-control" name="tecnicoPass" minlength="1" maxlength="60" placeholder="Contraseña" required>
                            <label class="form-label fw-bold" >Repite la contraseña</label>
                            <input type="password" class="form-control" name="tecnicoPassAgain" minlength="1" maxlength="60" placeholder="Repetir contraseña" required>
                        </div>

                        <hr class="sidebar-divider">

                        <div class="d-flex flex-row-reverse px-2 ">
                            <button type="submit" class="btn btn-primary m-1 shadow-lg"  >Registrar</button>
                            <button type="reset" class="btn btn-secondary m-1 shadow-lg" data-dismiss="modal">Reset</button>
                        </div>
                    </form>
                </div>
                <!-- FIN Cuerpo de la incidencia detallada -->
            </div>
        </div>
        <!-- FIN Incidencia long data Modal -->
    </div>



    <%--responde with a modal here for delete a tecnico--%>
    <div class="modalDeleteTecnico">

    </div>

    <!-- Fin seccion of modal offcanvas and modal -->


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
        // if no user login or no Usuario Resend to index.jsp
        response.sendRedirect( "index.jsp" );
    }

%>





</body>

</html>

