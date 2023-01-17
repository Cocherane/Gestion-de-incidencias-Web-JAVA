<%--
  Created by IntelliJ IDEA.
  User: ariel
  Date: 19/06/2022
  Time: 18:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- comprobamos si el usuario esta loguiado no presentar la pnatalla de regitro--%>
<% if ( session.getAttribute("usuario") == null ) { %>
<!DOCTYPE html>
<html lang="en">

<head>

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Registrando - FernTickets</title>
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


</head>

<body class="bg-gradient-primary" id="bg-imgen-register">

<div class="container">

  <div class="card o-hidden border-0 shadow-lg my-5">
    <div class="card-body p-0">
      <!-- Nested Row within Card Body -->
      <div class="row">
        <div class="col-lg-5 d-none d-lg-block bg-register-image"></div>
        <div class="col-lg-7">
          <div class="p-5">
            <div class="text-center">
              <h1 class="h4 text-gray-900 mb-4">Creando una cuenta!!!</h1>
            </div>
            <%-- alert--%>
            <div id="alert-wrapper"></div>

            <form id="formRegister" class="user needs-validation"   >
              <div class="form-group row">
                <div class="col-sm-6 mb-3 mb-sm-0">
                  <input type="text" class="form-control form-control-user" id="FirstName" name="FirstName"
                         placeholder="Nombre" required>

                  <div class="valid-feedback">
                    Looks good!
                  </div>


                </div>
                <div class="col-sm-6">
                  <input type="text" class="form-control form-control-user" id="LastName" name="LastName"
                         placeholder="Apellido" required>

                  <div class="valid-feedback">
                    Looks good!
                  </div>

                </div>

              </div>
              <div class="form-group">
                <input type="email" class="form-control form-control-user" id="InputEmail" name="InputEmail"
                       placeholder="Email" required>

                <div class="valid-feedback">
                  Looks good!
                </div>

              </div>
              <div class="form-group row">
                <div class="col-sm-6 mb-3 mb-sm-0">
                  <input type="password" class="form-control form-control-user"
                         id="InputPassword" name="InputPassword" placeholder="Contraseña" required>
                  <div class="invalid-feedback">
                    The keys are not the same.
                  </div>

                </div>
                <div class="col-sm-6">
                  <input type="password" class="form-control form-control-user"
                         id="RepeatPassword" name="RepeatPassword" placeholder="Repite la contraseña" required>
                </div>
              </div>
              <button type="submit" class="btn btn-primary btn-user btn-block">
                Register Account
              </button>

            </form>
            <hr>

            <div class="text-center">
              <a class="small" href="LogIn.jsp">Ya tengo una cuenta? Login!!!</a>
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

<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>

<script>
  $("#formRegister").submit( function (event) {

    // Stop form from submitting normally
    event.preventDefault();

    var combinedData = $("#formRegister").serialize();
    console.log(combinedData);
    $.ajax({
      type: "POST",
      url: "RegisterUser",
      data: combinedData,
      error: function (xhr, ajaxOptions, thrownError, exception){
        console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError );
      },
      success: function(data) {
        if (data.redirect) {
          // data.redirect contains the string URL to redirect to
          window.location.href = data.redirect;
        } else {
          // data.responseHtmlAlert contains the HTML for the replacement form reset pass
          $('#alert-wrapper').html(data.alertHTML);
        }
      }



    });
  } );

</script>

</body>

</html>

<%-- redirecionamos a el menu principal si ya esta registrado el usuario--%>
<%
  } else {
    response.sendRedirect("index.jsp");
  }
%>


<script src="./js/jquery.bs.alert.min.js"></script>
