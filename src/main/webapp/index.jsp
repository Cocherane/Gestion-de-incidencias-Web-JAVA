<%@ page import="DAO.DAOManager" %>
<%@ page import="DAO.DAOIncidenciaSQL" %>
<%@ page import="models.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Bienvenido a FernanTicket - Inicio</title>
    <%--favicon--%>
    <link rel="icon" type=”image/svg+xml” href="${pageContext.request.contextPath}/img/cat-solid.svg" >

    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>
    <!-- jQuery import -->
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>


    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">


    <!-- Icons -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.1/font/bootstrap-icons.css">

    <!-- own CSS -->
    <link rel="stylesheet" href="css/index.css">

    <!-- JavaScript to set config of AJAX-->
    <!-- Custom scriptss for all Form -->


</head>
<body style="background-image: url('img/index_bg.svg');
background-size: 100% 100%;
background-size: cover;
background-repeat: no-repeat;
min-height: 100%;
height: 100%;
">

<%
    // instance of GestionApp MVC
    GestionAPP app = new GestionAPP();

    // usuario loguiado
    User user = (User) session.getAttribute("user");

    // el id del icono
    String pathIconUser = null;
    // set icono user
    if ( session.getAttribute("user") instanceof Usuario){
        pathIconUser = "img/avatar-cat.svg";
    } else if ( session.getAttribute("user") instanceof Tecnico) {
        pathIconUser = "img/avatar-fox.svg";
    }else {
        pathIconUser = "img/avatar-pig.svg";
    }


%>

<div id="content">

    <div id="navbar">
        <nav class="navbar navbar-expand-lg bg-primary shadow-lg">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <a class="navbar-brand d-flex align-items-center justify-content-center " href="#">
                    <div class="sidebar-brand-icon rotate-n-15">
                        <i class="fas fa-cat text-light" style="font-size: 3rem;"></i>
                    </div>
                    <div class="sidebar-brand-text mx-3 mt-2 text-light title-main h1 fw-bolde">FernTickets</div>
                </a>

                <div class="collapse navbar-collapse " id="navbarTogglerDemo03">
                    <%
                     if ( user == null ){
                    %>
                    <div class="col-lg-12 d-lg-flex justify-content-end" >
                        <a href="register.jsp" class="btn btn-primary" role="button" >Registrarse</a>
                        <a href="LogIn.jsp" class="btn btn-primary" role="button" >Iniciar sesión</a>
                    </div>

                    <%
                        }else {
                    %>


                    <a class=" col-lg-12 d-flex align-self-center justify-content-end " href="LogIn.jsp"  role="button" >
                        <div class="my-auto text-light fw-bold fs-5 name-user text-capitalize">
                            <%= user.getNombre()+" "+user.getApel() %>
                        </div>
                        <img class="rounded-circle align-content-center m-3" style="height: 50px;" src="<%=pathIconUser%>">
                    </a>
                    <%
                        }
                    %>


                </div>
            </div>
        </nav>
    </div>

    <div id="main"  >

        <div class="container-fluid px-4 mt-5 h-100">
            <div class="row gx-5 h-75 ">
                <div class="col-lg-5 p-3 d-flex align-items-center" >
                    <div class="p-3  text-light ">
                        <div class="slogan-main display-2 fw-bold fst-italic">El servicio está en todas partes</div>
                        <div class="slogan fs-4 fw-bolder mt-4">Una aplicación diferente y didáctica,
                            que gestiona los reporte de tareas de los clientes
                            a su personal calificado, sin frustraciones, llevando
                            la tecnología de gestión al nivel máximo de la eficacia.</div>
                    </div>
                </div>
                <div class="col-lg-7 p-3">
                    <img src="img/index_img.svg" class="img-fluid p-3" alt="...">
                </div>
            </div>
        </div>
    </div>


</div>

</div>

</body>
</html>