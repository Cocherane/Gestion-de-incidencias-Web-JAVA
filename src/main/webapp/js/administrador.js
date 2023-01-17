/* All set up for form on request AJAX*/
$(function() {
    /* For the form to restablecer contrasena*/
    $("#form-user-key").submit(function (event) {

        // Stop form from submitting normally
        event.preventDefault();

        var combinedData = $("#form-user-key").serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "RestablecerContrasena",
            data: combinedData,
            dataType: "json",
            error: function (xhr, ajaxOptions, thrownError, exception){
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError );
            },
            success: function(data) {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else if (data.responseHtmlAlert) {
                    // data.responseHtmlAlert contains the HTML for the replacement form reset pass
                    $('#alert-restablecer').html(data.responseHtmlAlert);
                }else {
                    // data.responseHtmlBody contain all message in body
                    $('#page-top').html(data.responseHtmlBody);
                    document.body.style.backgroundImage = "url('img/land_pass.jpg')"
                    document.body.style.backgroundSize = "cover"
                }
            }
        });
    });

});

function setIncidentTecnico( idFormNum ){
    /* For the form to set incident to technical*/
    $(function () {

        var combinedData = $("#form-user-set-incidenia-tecnico-"+ idFormNum).serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "SetTecnicoIncident",
            data: combinedData,
            dataType: "json",
            error: function (xhr, ajaxOptions, thrownError, exception){
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError );
            },
            success: function(data) {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else if (data.responseHtmlAlert) {
                    // data.responseHtmlAlert contains the HTML for the replacement form reset pass
                    $('#alert-set-incidencia').html(data.responseHtmlAlert);
                }else if( data.callType){

                    if ( data.callType === 1 ){
                        $('#tecnicosModal_' + data.idModal ).modal('hide', $(document).ready( function (){
                            $('#id-incident-board').load("adminIncidentSet.jsp");
                        }));
                    }else if ( data.callType === 2){
                        $('#tecnicosModal_' + data.idModal ).modal('hide',$(document).ready( function (){
                            $('#id-incident-board').load("adminIncidentOpen.jsp");
                        }));
                    }else {
                        window.location.href= "/FernanTickets/Admin.jsp"
                    }
                }
            }
        });
    });

}

/* All set up for form on request AJAX*/
$(function() {
    /* For the form to register a tecnico*/
    $("#form-tecnico-register").submit(function (event) {

        // Stop form from submitting normally
        event.preventDefault();

        var combinedData = $("#form-tecnico-register").serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "RegisterTecnico",
            data: combinedData,
            dataType: "json",
            error: function (xhr, ajaxOptions, thrownError, exception){
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError );
            },
            success: function(data) {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else if (data.status ){
                    // recarga la pagina
                    $('#registrar-tecnico-modal').modal('hide',$(document).ready( function (){
                        $('#id-incident-board').load("adminDetailTecnico.jsp");
                    }));
                } else{
                    // data.responseHtmlAlert contains the HTML for the replacement form reset pass
                    $('#id-alert-register-tecnico').html(data.alertHTML);
                }
            }
        });
    });

});


function deleteTecnico( idFormNum ){
    /* For the form to delete a technical*/
    $(function () {

        var combinedData = $("#form-user-delete-tecnico-"+ idFormNum).serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "DeleteTecnico",
            data: combinedData,
            dataType: "json",
            error: function (xhr, ajaxOptions, thrownError, exception){
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError );
            },
            success: function(data) {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else if (data.responseHtmlAlert) {
                    // data.responseHtmlAlert contains the HTML for the replacement form reset pass
                    $('#alert-tecnico-delete').html(data.responseHtmlAlert);
                } else {
                    // recarga la pagina
                    $(document).ready( function (){
                        $('#id-incident-board').load("adminDeleteTecnico.jsp");
                    })
                }
            }
        });
    });

}


function sendResumenTecnico( idFormNum ){
    /* For the form to send all resumen  technical*/
    $(function () {

        var combinedData = $("#form-user-send-tecnico-resumen-"+ idFormNum).serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "SendResumenTecnico",
            data: combinedData,
            dataType: "json",
            error: function (xhr, ajaxOptions, thrownError, exception){
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError );
            },
            success: function(data) {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else if (data.responseHtmlAlert) {
                    // data.responseHtmlAlert contains the HTML for the replacement form reset pass
                    $('#alert-tecnico-send').html(data.responseHtmlAlert);
                }else {
                    window.location.href= "/FernanTickets/Admin.jsp"
                }
            }
        });
    });

}

/* All set up for form on request AJAX*/
$(function() {
    /* For the form to register a tecnico*/
    $("#id-form-search-term").submit(function (event) {

        // Stop form from submitting normally
        event.preventDefault();

        var combinedData = $("#id-form-search-term").serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "BuscarByTerm",
            data: combinedData,
            dataType: "json",
            error: function (xhr, ajaxOptions, thrownError, exception){
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError );
            },
            success: function(data) {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else if (data.responseHtmlAlert){
                    // data.responseHtmlAlert contains the HTML for the replacement form reset pass
                    $('#id-incident-board').html(data.responseHtmlAlert);
                }else {
                    $('#id-incident-board').load("adminBuscarByTerm.jsp");
                }
            }
        });
    });

});

// Incidencias cerradas
function adminIncidentClose(){
    $(document).ready( function (){
        $('#id-incident-board').load("adminIncidentClose.jsp");
    })
}

// Incidencias abiertas sin asignar y asignadas
function adminIncidentOpen(){
    $(document).ready( function (){
        $('#id-incident-board').load("adminIncidentOpen.jsp");
    })
}

// Incidencias abiertas para asignar a un tecnico
function adminIncidentSet(){
    $(document).ready( function (){
        $('#id-incident-board').load("adminIncidentSet.jsp");
    })
}

// Technical details
function adminTecnicoDetail(){
    $(document).ready( function (){
        $('#id-incident-board').load("adminDetailTecnico.jsp");
    })
}

// Usuario details
function adminUsuarioDetail(){
    $(document).ready( function (){
        $('#id-incident-board').load("adminDetailUsuario.jsp");
    })
}

// Delete a Tecnico
function adminDeleteTecnico(){
    $(document).ready( function (){
        $('#id-incident-board').load("adminDeleteTecnico.jsp");
    })
}

// Estadistcica de los usuarios
function adminEstadistica(){
    $(document).ready( function (){
        $('#id-incident-board').load("adminEstadistica.jsp");
    })
}

// LAst session user
function adminLastSession(){
    $(document).ready( function (){
        $('#id-incident-board').load("adminLastSession.jsp");
    })
}

// Send resumen Tecnico
function adminSendResumenTecnico(){
    $(document).ready( function (){
        $('#id-incident-board').load("adminSendTecnicoResumen.jsp");
    })
}

// Send term
function adminSearchTerm(){
    $(document).ready( function (){
        $('#id-incident-board').load("adminBUscarByTerm.jsp");
    })
}

// Send resumen Admin's Messages
function adminSearchMessage(){
    $(document).ready( function (){
        $('#id-incident-board').load("MessagesUser.jsp");
    })
}

$("#myModal").on("hidden.bs.modal", function () {
    // put your default event here
});

// test reach
function myFunction() {
    alert("esta trabajando")
}