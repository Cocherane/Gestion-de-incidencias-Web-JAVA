/* submit from form tokenValidation */
$(function() {
    $("#form-validate-token").submit(function (event) {

        // Stop form from submitting normally
        event.preventDefault();

        var combinedData = $("#form-validate-token").serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "TokenValidator",
            data: combinedData,
            dataType: "json",
            error: function (xhr, ajaxOptions, thrownError, exception){
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError );
            },
            success: function(data) {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else {
                    // data.form contains the HTML for the replacement form
                    $('#alertMessage').html(data.alert);
                }
            }
        });
    });
});


/* submit from form reseat password */
$(function() {
    $("#form-reseat-pass").submit(function (event) {

        // Stop form from submitting normally
        event.preventDefault();

        var combinedData = $("#form-reseat-pass").serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "ReseatPassword",
            data: combinedData,
            dataType: "json",
            async: true,
            error: function (xhr, ajaxOptions, thrownError, exception){
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError );
            },
            success: function(data) {
                // to disable modal #spinner-loading


                        if (data.redirect) {
                            // data.redirect contains the string URL to redirect to
                            window.location.href = data.redirect;

                        } else {
                            // data.form contains the HTML for the replacement form
                            $('#alertMessage').html(data.alert);
                        }


            }
        });
    });
});


/* submit from form send message */
function sendMessage( idFormNum) {
    $(function () {

            var combinedData = $("#form-send-message-"+ idFormNum).serialize();
            console.log(combinedData);
            $.ajax({
                type: "POST",
                url: "SendMessage",
                data: combinedData,
                dataType: "json",
                error: function (xhr, ajaxOptions, thrownError, exception) {
                    console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError);
                },
                success: function (data) {

                    if (data.redirect) {
                        // data.redirect contains the string URL to redirect to
                        window.location.href = data.redirect;
                    } else if (data.alert) {
                        // data.form contains the HTML for the replacement form
                        $('#id-alert-message').html(data.alert);
                    } else if (data.send) {
                        window.location.reload();
                    }
                }
            });
    });
}

/* submit from form send message */
function replyMessage( idFormNum) {

    $(document).ready( function () {

        var combinedData = $("#form-reply-message-number"+ idFormNum).serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "SendMessage",
            data: combinedData,
            dataType: "json",
            error: function (xhr, ajaxOptions, thrownError, exception) {
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError);
            },
            success: function (data) {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else if (data.alert) {
                    // data.form contains the HTML for the replacement form
                    $('#id-alert-message').html(data.alert);
                } else if (data.send) {
                    $('#exampleModalMessageReply_' + idFormNum).modal('hide', $(document).ready(function (){
                        $('#id-incident-board').load("MessagesUser.jsp");
                    }));

                }
            }
        });
    });
}

/* submit from form send message */
function newComposeMessage( ) {

    $(document).ready( function () {

        var combinedData = $("#form-new-compose-message").serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "SendMessage",
            data: combinedData,
            dataType: "json",
            error: function (xhr, ajaxOptions, thrownError, exception) {
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError);
            },
            success: function (data) {
                if (data.redirect) {
                    // data.redirect contains the string URL to redirect to
                    window.location.href = data.redirect;
                } else if (data.alert) {
                    // data.form contains the HTML for the replacement form
                    $('#id-alert-new-message').html(data.alert);
                } else if (data.send) {
                    $('#messageModalAnyUser').modal('hide', $(document).ready(function (){
                        $('#id-incident-board').load("MessagesUser.jsp");
                    }));

                }
            }
        });
    });
}

function openMarkMessage( Id ){

    $(document).ready( function () {

            var combinedData = $("#form-message-dashboard-" + Id).serialize();
            console.log(combinedData);
            $.ajax({
                type: "POST",
                url: "CheckOpenMessage",
                data: combinedData,
                error: function (xhr, ajaxOptions, thrownError, exception) {
                    console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError);
                },
                success: function () {
                    $('#id-incident-board').load("MessagesUser.jsp", function () {
                        $('#exampleModalMessageOpen_' + Id).modal('show');
                    } );

                }
            });
    });


}

function removeMessage( Id ){
    $(document).ready( function (){
        var combinedData = $("#form-message-dashboard-" + Id).serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "DeleteMessage",
            data: combinedData,
            error: function (xhr, ajaxOptions, thrownError, exception) {
                console.log('ERROR: jqXHR, exception', exception, xhr, ajaxOptions, thrownError);
            },
            success: function(){
                $('#id-incident-board').load("MessagesUser.jsp");
            }
        })
    })
}