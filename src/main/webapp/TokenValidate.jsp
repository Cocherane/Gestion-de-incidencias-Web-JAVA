<%@ page import="models.Usuario" %><%--
  Created by IntelliJ IDEA.
  User: ariel
  Date: 20/07/2022
  Time: 10:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% if( session.getAttribute("userTokenValidate") != null && session.getAttribute("userTokenValidate") instanceof Usuario
    && !((Usuario) session.getAttribute("userTokenValidate")).isTokenValidated()
){ %>

<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Validación del token - FernTickets</title>
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

    <!-- Custom styles for this template-->
    <link href="css/sb-admin-2.min.css" rel="stylesheet">
    <!-- backgroung set -->
    <link href="css/generalAllPages.css" rel="stylesheet" type="text/css">

    <!-- JavaScript to set config of AJAX-->
    <script type="text/javascript" src="js/adminGeneral.js"></script>


</head>

<body class="bg-gradient-primary" id="bg-imgen-token">

<div class="container">

    <!-- Outer Row -->
    <div class="row justify-content-center">

        <div class="col-xl-10 col-lg-12 col-md-9">

            <div class="card o-hidden border-0 shadow-lg my-5">
                <div class="card-body p-0">
                    <!-- Nested Row within Card Body -->
                    <div class="row">
                        <div class="col-lg-6 d-none d-lg-block bg-password-image"></div>
                        <div class="col-lg-6">
                            <div class="p-5">
                                <div class="text-center">
                                    <h1 class="h4 text-gray-900 mb-2">Validación de tu token</h1>
                                    <p class="mb-4">Un email con tu token fue enviado a</p>
                                </div>
                                <div id="alertMessage" ></div>

                                <form id="form-validate-token" class="user">
                                    <div class="form-group">
                                        <input type="text" class="form-control form-control-user"
                                               name="token" id="exampleInputEmail" aria-describedby="emailHelp"
                                               placeholder="Introduce el token de 6 dígitos..." pattern="[0-9]{6}">
                                    </div>
                                    <button id="buttonSubmit" type="submit" class="btn btn-primary btn-user btn-block">
                                        Validar
                                    </button>
                                </form>
                                <hr>
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

<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>

</body>

</html>

<%
    }else {
        // if no user login or no Usuario Resend to index.jsp
        response.sendRedirect( "index.jsp" );
    }
%>