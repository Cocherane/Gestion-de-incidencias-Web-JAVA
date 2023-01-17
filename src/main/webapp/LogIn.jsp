<%@ page import="models.Usuario" %>
<%@ page import="models.Tecnico" %>
<%@ page import="models.Admin" %><%--
  Created by IntelliJ IDEA.
  User: ariel
  Date: 28/06/2022
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  if ( session.getAttribute("user") == null ){ %>
<!DOCTYPE html>
<head>
    <title>LogIn - FernTickets</title>
    <%--favicon--%>
    <link rel="icon" type=”image/svg+xml” href="${pageContext.request.contextPath}/img/cat-solid.svg" >
    <!-- Custom fonts for this template-->
    <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor" crossorigin="anonymous">
    <!-- JavaScript Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-pprn3073KE6tl6bjs2QrFaJGz5/SUsLqktiwsUTF55Jfv3qYSDhgCecCxMW52nD2" crossorigin="anonymous"></script>

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
    <!-- backgroung set -->
    <link href="css/generalAllPages.css" rel="stylesheet" type="text/css">
    <!-- jQuery import -->
    <script src="https://code.jquery.com/jquery-3.5.0.js"></script>
    <!-- JavaScript to set config of AJAX-->
    <script type="text/javascript" src="js/login.js"></script>



</head>
<body class="bg-gradient-primary" id="bg-imgen-loguin">

<div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

        <div class="col-xl-10 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-1">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-4">Bienvenido a FernanTickets!!!</h1>
                                </div>

                                <!--START div of Alert  -->
                                <div id="alertMessage"></div>
                                <!--END div of Alert  -->

                                <form id="form-login-user" class="user" method="post" >
                                    <div class="form-group">
                                        <input type="email" name="emailUser" class="form-control form-control-user"
                                               id="exampleInputEmail" aria-describedby="emailHelp"
                                               placeholder="Ingresa tu email...">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" name="passUser" class="form-control form-control-user"
                                               id="exampleInputPassword" placeholder="Ingresa tu contraseña...">
                                    </div>
                                    <div class="form-group">

                                    </div>
                                    <hr>
                                    <button id="buttonSubmit" type="submit" class="btn btn-primary btn-user btn-block">
                                        Login
                                    </button>

                                </form>
                                <hr>
                                <div class="text-center">
                                    <a class="small" href="forgotPassword.html">Olvide la contraseña?</a>
                                </div>
                                <div class="text-center">
                                    <a class="small" href="register.jsp">Crear una cuenta</a>
                                </div>
                                <div class="text-center">
                                    <a class="small" href="index.jsp">Volver a la página principal? Inicio!!!</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>
</body>
</html>

<%
        // redirect to Usuario.jsp
    }else if ( session.getAttribute("user") instanceof Usuario){
        response.sendRedirect("Usuario.jsp");
        // redirect to Tecnico.jsp
    }else if ( session.getAttribute("user") instanceof Tecnico){
            response.sendRedirect("Tecnico.jsp");
        // redirect to Admin.jsp
    }else if ( session.getAttribute("user") instanceof Admin){
        response.sendRedirect("Admin.jsp");
        // redirect to index.jsp
    }else {
        response.sendRedirect("index.jsp");
    }
%>
