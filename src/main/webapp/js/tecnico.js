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

function setCloseIncidenctByTecnico( idFormNum, status ) {
    $(function () {
            var combinedData = $("#form-tecnico-close-"+ idFormNum).serialize();
            console.log(combinedData);
            $.ajax({
                type: "POST",
                url: "CerrarIncidencia",
                data: combinedData,
                dataType: "json",
                error: function (xhr, ajaxOptions, thrownError, exception) {
                    console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError);
                },
                success: function (data) {
                    if (data.redirect) {
                        // data.redirect contains the string URL to redirect to
                            window.location.href = data.redirect;
                    }else if (data.load){

                        if (status === true) {
                            console.log("entre 1")
                            $('#exampleModal_' + idFormNum).modal('hide', $(document).ready(function (){
                                $('#id-incident-board').load("tecnicoIncidenctOpenShort.jsp");
                            }));
                        } else  {
                            $('#exampleModal_' + idFormNum).modal('hide' , $(document).ready(function (){
                                $('#id-incident-board').load("tecnicoIncidentOpen.jsp");
                            }));

                        }

                    } else {
                        // data.responseHtmlAlert contains the HTML for the replacement form reset pass
                        $('#id-alert-report-tecnico').html(data.responseHtmlAlert);
                    }
                }
            });
    });
}



function tecnicoIncidentOpen(){
    $(document).ready( function (){
        $('#id-incident-board').load("tecnicoIncidentOpen.jsp");
    })
}

function tecnicoIncidentClose(){
    $(document).ready( function (){
        $('#id-incident-board').load("tecnicoIncidentClose.jsp");
    })
}

function tecnicoIncidentOpenShort(){
    $(document).ready( function (){
        $('#id-incident-board').load("tecnicoIncidenctOpenShort.jsp");
    })
}

// Send resumen Admin's Messages
function tecnicoSearchMessage(){
    $(document).ready( function (){
        $('#id-incident-board').load("MessagesUser.jsp");
    })
}

