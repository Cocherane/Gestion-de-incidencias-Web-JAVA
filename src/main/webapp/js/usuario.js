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

$ (function (){
    /* Form to add incident */
    $("#form-user-add").submit(function (event) {

        // Stop form from submitting normally
        event.preventDefault();

        var combinedData = $("#form-user-add").serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "InsertarIncidencia",
            data: combinedData,
            dataType: "json",
            error: function (xhr, ajaxOptions, thrownError, exception) {
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError);
            },
            success: function (data) {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else {
                    // data.responseHtmlBody contain all message in body
                    $('#id-alert-report').html(data.alertHtml);
                }
            }
        });
    });

});


function usuarioIncidentOpen(){
    $(document).ready( function (){
        $('#id-incident-board').load("usuarioIncidentOpen.jsp");
    })
}

function usuarioIncidentClose(){
    $(document).ready( function (){
        $('#id-incident-board').load("usuarioIncidentClose.jsp");
    })
}


// Send resumen Admin's Messages
function usuarioSearchMessage(){
    $(document).ready( function (){
        $('#id-incident-board').load("MessagesUser.jsp");
    })
}