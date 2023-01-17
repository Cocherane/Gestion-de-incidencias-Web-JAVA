<%@ page import="models.Usuario" %><%--
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

    <title>Schedule - Dashboard</title>

    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">

</head>


<body id="page-top">

<%-- check if the user is logging--%>
<%
    // instance of usuario
    Usuario usuario = null;

    // check if user is instance of Usuario
    if( session.getAttribute("user") != null && session.getAttribute("user") instanceof Usuario ){
        // set the user login like Usuario and token validated
        usuario = (Usuario) session.getAttribute("user");

        // check if user is null 2 time an check if token has been validated
        if ( usuario != null  && usuario.isTokenValidated() ){


%>


<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="dashboard.jsp">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-cat"></i>
            </div>
            <div class="sidebar-brand-text mx-3">Schedule <sup>1</sup></div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">

        <!-- Nav Item - Dashboard -->
        <li class="nav-item active text-center ali my-3">
            <!-- Button trigger modal -->
            <button type="button" class="btn btn-primary " data-toggle="modal" data-target="#exampleModalCenter">
                <i class="fas fa-solid fa-calendar-circle-plus"></i>
                <span> Add a task</span>
            </button>

            <!-- Modal pop out add task-->
            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header bg-primary">
                            <h5 class="modal-title text-white " id="exampleModalLongTitle"><b>ADD A TASK</b></h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <!-- body of form -->
                            <form class="formAddTask" id="IdFormAddTask" action="#" novalidate>
                                <div class="form-group ">
                                    <label>Description</label>
                                    <input type="text" class="form-control" name="description" minlength="1" maxlength="40" placeholder="Add a description" pattern="[A-Za-z]" required>
                                </div>
                                <div class="form-group form-row justify-content-center">
                                    <div class="col-auto " >
                                        <label>Date </label>
                                        <input type="date" class="form-control" name="date"  required >
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="addTask('IdFormAddTask')" >Add task</button>
                        </div>
                    </div>
                </div>
            </div>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider">

        <!-- Heading  ORDER TASK BY -->
        <div class="sidebar-heading">
            Order Tasks by
        </div>

        <!-- Nav Item - SEARCHING CURRENT DAY  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="todayTasks()">
                <i class="fas fa-duotone fa-calendar-day"></i>
                <span>Today</span></a>
        </li>

        <!-- Nav Item - SEARCHING CURRENT MONTH  -->
        <li class="nav-item">
            <a class="nav-link" href="#" onclick="monthTasks()">
                <i class="fas fa-duotone fa-calendar-day"></i>
                <span>Current month</span></a>
        </li>

        <!-- Nav Item - Pages Collapse SEARCHING BY DATE -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseOne"
               aria-expanded="true" aria-controls="collapseOne">
                <i class="fas fa-duotone fa-calendar-day"></i>
                <span>Search by date</span>
            </a>
            <div id="collapseOne" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Select your date :</h6>
                    <!-- Form to get date -->
                    <form  id="formDate" method="POST" action="datetasks.jsp" >
                        <div class="form-group px-2">
                            <label>
                                <input class="form-control" type="date" name="dateFilter"  required>
                            </label>
                            <hr>
                        </div>
                    </form>
                    <div class="form-group px-2">
                        <button class="form-control btn btn-primary btn-sm" type="button"  onclick="dateTask('formDate')">Ok!!</button>
                    </div>
                </div>
            </div>
        </li>


        <!-- Nav Item - Pages Collapse SEARCHING BY MONTH -->
        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
               aria-expanded="true" aria-controls="collapseTwo">
                <i class="fas fa-duotone fa-calendar-day"></i>
                <span>Search by month</span>
            </a>
            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <h6 class="collapse-header">Select your month:</h6>

                    <form id="formGroupMonth" class="form-group" action="#" method="post">


                        <div class="btn-toolbar custom-control custom-checkbox m-0 px-2 form-check  d-flex " role="toolbar" aria-label="Toolbar with button groups">
                            <!-- meses de enero febrero -->
                            <div class="btn-group-sm my-1 btn-group-toggle " role="group" aria-label="First group">
                                <label class="btn btn-primary active ">
                                    <input  type="radio"  onclick="TasksByMonth('1')">Enero
                                </label>
                                <label class="btn btn-primary ">
                                    <input  type="radio" onclick="TasksByMonth('2')" > Febrero
                                </label>
                            </div>

                            <!-- meses de marzo abril -->
                            <div class="btn-group-sm my-1  btn-group-toggle flex-fill" role="group" aria-label="First group">
                                <label class="btn btn-primary ">
                                    <input type="radio" onclick="TasksByMonth('3')">Marzo
                                </label>
                                <label class="btn btn-primary ">
                                    <input type="radio" onclick="TasksByMonth('4')" >Abril
                                </label>
                            </div>

                            <!-- meses de mayo junio -->
                            <div class="btn-group-sm my-1  btn-group-toggle " role="group" aria-label="First group">
                                <label class="btn btn-primary ">
                                    <input type="radio" onclick="TasksByMonth('5')">mayo
                                </label>
                                <label class="btn btn-primary ">
                                    <input type="radio" onclick="TasksByMonth('6')">Junio
                                </label>
                            </div>

                            <!-- meses de julio agosto -->
                            <div class="btn-group-sm my-1  btn-group-toggle " role="group" aria-label="First group">
                                <label class="btn btn-primary ">
                                    <input type="radio" onclick="TasksByMonth('7')">julio
                                </label>
                                <label class="btn btn-primary ">
                                    <input type="radio" onclick="TasksByMonth('8')">agosto
                                </label>
                            </div>


                            <!-- meses de septiembre octubre -->
                            <div class="btn-group-sm my-1  btn-group-toggle " role="group" aria-label="First group">
                                <label class="btn btn-primary ">
                                    <input type="radio" onclick="TasksByMonth('9')">Septiembre
                                </label>
                                <label class="btn btn-primary ">
                                    <input type="radio" onclick="TasksByMonth('10')">Octubre
                                </label>
                            </div>
                            <!-- meses de noviembre diciembre -->
                            <div class="btn-group-sm my-2  btn-group-toggle " role="group" aria-label="First group">
                                <label class="btn btn-primary ">
                                    <input type="radio" onclick="TasksByMonth('11')">Noviembre
                                </label>
                                <label class="btn btn-primary">
                                    <input type="radio" onclick="TasksByMonth('12')">Diciembre
                                </label>
                            </div>

                        </div>

                    </form>
                </div>
            </div>
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
                <form id="formterm"
                      class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                    <div class="input-group">
                        <input type="text" class="form-control bg-light border-0 small" placeholder="Search for term..."
                               name="termFilter" aria-label="Search" aria-describedby="basic-addon2">
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

                    <!-- Nav Item - Alerts -->
                    <li class="nav-item dropdown no-arrow mx-1">
                        <a class="nav-link dropdown-toggle" href="#" id="alertsDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-bell fa-fw"></i>
                            <!-- Counter - Alerts -->
                            <span class="badge badge-danger badge-counter">3+</span>
                        </a>
                        <!-- Dropdown - Alerts -->
                        <div class="dropdown-list dropdown-menu dropdown-menu-right shadow animated--grow-in"
                             aria-labelledby="alertsDropdown">
                            <h6 class="dropdown-header">
                                Alerts Center
                            </h6>
                            <a class="dropdown-item d-flex align-items-center" href="#">
                                <div class="mr-3">
                                    <div class="icon-circle bg-primary">
                                        <i class="fas fa-file-alt text-white"></i>
                                    </div>
                                </div>
                                <div>
                                    <div class="small text-gray-500">December 12, 2019</div>
                                    <span class="font-weight-bold">A new monthly report is ready to download!</span>
                                </div>
                            </a>
                            <a class="dropdown-item d-flex align-items-center" href="#">
                                <div class="mr-3">
                                    <div class="icon-circle bg-success">
                                        <i class="fas fa-donate text-white"></i>
                                    </div>
                                </div>
                                <div>
                                    <div class="small text-gray-500">December 7, 2019</div>
                                    $290.29 has been deposited into your account!
                                </div>
                            </a>
                            <a class="dropdown-item d-flex align-items-center" href="#">
                                <div class="mr-3">
                                    <div class="icon-circle bg-warning">
                                        <i class="fas fa-exclamation-triangle text-white"></i>
                                    </div>
                                </div>
                                <div>
                                    <div class="small text-gray-500">December 2, 2019</div>
                                    Spending Alert: We've noticed unusually high spending for your account.
                                </div>
                            </a>
                            <a class="dropdown-item text-center small text-gray-500" href="#">Show All Alerts</a>
                        </div>
                    </li>



                    <div class="topbar-divider d-none d-sm-block"></div>

                    <!-- Nav Item - User Information -->
                    <li class="nav-item dropdown no-arrow">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">

                            <span class="mr-2 d-none d-lg-inline text-gray-600 small">
<%-- Show out the name of user --%>
                            <% out.println( usuario.getNombre()+" "+usuario.getApel() );%>
                            </span>
                            <img class="img-profile rounded-circle"
                                 src="img/undraw_profile.svg">
                        </a>
                        <!-- Dropdown - User Information -->
                        <div class="dropdown-menu dropdown-menu-right shadow animated--grow-in"
                             aria-labelledby="userDropdown">
                            <div class="dropdown-divider"></div>
                            <form action="/schedule/LogOut" method="post">
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
                    <h1 class="h3 mb-0 text-gray-800">Next Assignments</h1>
                </div>

                <!-- Content Row -->
                <div class="row py-3">

                    <%-- Check if there are not Incident  BEGIN --%>
                    <%
                        if ( usuario.getTasks().stream().anyMatch(st -> !st.isDone()) ){
                            // contador para solo imprimir 2 tareas


                            for (Task task : usuario.getTasks().stream().filter(st -> !st.isDone()).limit(2).toList()) {
                    %>
                    <!-- First Task Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            Next task ...</div>
                                        <div class="h6 mb-0 font-weight-bold text-gray-800">
                                            <%-- First Task --%>
                                            <%= task.getDescription()  %>
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                        if ( usuario.getTasks().stream().filter(st -> !st.isDone()).limit(2).count() == 1 ){

                    %>
                    <!-- Second Task Card Example if is empty -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-primary shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                            Next task ...</div>
                                        <div class="h6 mb-0 font-weight-bold text-gray-800">
                                            <%-- First Task --%>
                                            <%= "No Task"  %>
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                        }
                    %>


                    <!-- Earnings (Monthly) Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-info shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-info text-uppercase mb-1">Tasks done today
                                        </div>
                                        <div class="row no-gutters align-items-center">
                                            <div class="col-auto">
                                                <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">
                                                    <%-- task done on % today--%>
                                                    <%
                                                        int percentage = 100;
                                                        if (usuario.getTasks().stream().anyMatch( st -> st.getDate().isEqual(LocalDate.now()) ) ){

                                                            // set % following [num peding/ num done]
                                                            percentage = (int) (
                                                                    usuario.getTasks().stream().filter( st -> st.getDate().isEqual(LocalDate.now()) && st.isDone() ).count()
                                                                            / usuario.getTasks().stream().filter( st -> st.getDate().isEqual(LocalDate.now()) ).count()
                                                            ) * 100 ;

//                                                            out.println(usuario.getTasks().stream().filter( st -> st.getDate().isEqual(LocalDate.now()) && !st.isDone() ).count() );
//                                                            out.println( usuario.getTasks().stream().filter( st -> st.getDate().isEqual(LocalDate.now())  ).count());
                                                            out.println( percentage+ "%");

                                                        }else {
                                                            // set 100% if no task today
                                                            out.println( percentage+ "%");
                                                        }
                                                    %>
                                                </div>
                                            </div>
                                            <div class="col">
                                                <div class="progress progress-sm mr-2">
                                                    <div class="progress-bar bg-info" role="progressbar"
                                                         style="width: <%= percentage %>%" aria-valuenow="50" aria-valuemin="0"
                                                         aria-valuemax="100"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Pending Requests Card Example -->
                    <div class="col-xl-3 col-md-6 mb-4">
                        <div class="card border-left-info shadow h-100 py-2">
                            <div class="card-body">
                                <div class="row no-gutters align-items-center">
                                    <div class="col mr-2">
                                        <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                            All the tasks for today</div>
                                        <div class="h5 mb-0 font-weight-bold text-gray-800">
                                            <%-- Count all task for tomorrow--%>
                                            <%= usuario.getTasks().stream().filter( st -> st.getDate().isEqual( LocalDate.now() ) ).count() %>
                                        </div>
                                    </div>
                                    <div class="col-auto">
                                        <i class="fas fa-comments fa-2x text-gray-300"></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>



                    <% }else{
                        out.println(
                                "<div class=\"col\" >\n" +
                                        "                            <div class=\"card border-left-primary shadow h-100 py-5 text-center\">\n" +
                                        "                                <p class=\"h4\">\n" +
                                        "                                    <b> There are no Tasks..  </b>\n" +
                                        "                                    <i class=\"fa fa-calendar-week\"></i>\n" +
                                        "                                </p>\n" +
                                        "                            </div>\n" +
                                        "                        </div>"
                        );

                    }
                    %>

                </div>




                <!-- Content Row -->

                <div class="row">

                    <!-- Area Table Tasks -->
                    <div class="col-xl-8 col-lg-7">
                        <div id="showTasks" class="card shadow mb-4">
                            <!-- Card Header - Dropdown -->
                            <div id="tasksGroup">
                                <div
                                        class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                    <h6 class="m-0 font-weight-bold text-primary h4">The next tasks ...</h6>
                                </div>
                                <!-- Card Body -->
                                <div class="card-body">
                                    <!-- Las tablas aqui donde muestra  -->
                                    <table class="table table-sm table-light text-center table-primary table-hover table-striped">
                                        <thead>
                                        <tr class="m-2">
                                            <th class="col-2 text-center" scope="col"><i class="fas fa-calendar-check"></i>&nbsp;Date</th>
                                            <th class="col-8 text-center" scope="col">Description</th>
                                            <th class="col-2 text-center" scope="col">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <!-- primer dato de tareas-->
                                        <%
                                            int i = 1;
                                            for (Task task : usuario.getTasks()) {
                                                // solo pintamos las tareas que esten no realizadas
                                                if ( !task.isDone() ){
                                                    String formTag = "form_"+ i++;


                                        %>
                                        <tr>
                                            <td><%= GestionApp.localDateToStringSql( task.getDate() ) %></td>
                                            <td><%= task.getDescription() %></td>
                                            <td>
                                                <form id="<%= formTag %>" class="text-center"  method="post">
                                                    <div class="btn-group ">
                                                        <input type="hidden" value="<%= task.getIdTask() %>" name="idTask">
                                                    </div>
                                                    <button type="button" class="btn btn-primary" onclick="check('<%= formTag %>')" ><i class="fas fa-check"></i></button>
                                                    <button type="button"  class="btn btn-danger" onclick="remove('<%= formTag %>')" ><i class="fas fa-trash"></i></button>
                                                </form>
                                            </td>
                                        </tr>
                                        <% }}%>
                                        <!-- segundo dato de tareas-->
                                        <%--                                    <tr>--%>
                                        <%--                                        <td>01/01/2022</td>--%>
                                        <%--                                        <td>LLevar comida al poligono</td>--%>
                                        <%--                                        <td>--%>
                                        <%--                                            <form id="form_2" class="text-center formtask" action="#" method="post">--%>
                                        <%--                                                <div class="btn-group ">--%>
                                        <%--                                                    <input type="hidden" value="12345" name="idTask">--%>
                                        <%--                                                </div>--%>
                                        <%--                                                <button type="button" class="btn btn-primary" onclick="check('form_2')" ><i class="fas fa-check" ></i></button>--%>
                                        <%--                                                <button type="button" class="btn btn-danger " onclick="remove('form_2')" ><i class="fas fa-trash"></i></button>--%>
                                        <%--                                            </form>--%>
                                        <%--                                        </td>--%>
                                        <%--                                    </tr>--%>

                                        </tbody>
                                    </table>

                                </div>
                            </div>
                        </div>
                    </div>


                </div>



            </div>
            <!-- /.container-fluid -->

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


<script src="https://code.jquery.com/jquery-3.5.0.js"></script>



<%-- check if the user is logging--%>
<%



        }else {
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

