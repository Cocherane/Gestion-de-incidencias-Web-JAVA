/* submit from form LOGIN.JSP */
$(function() {
    $("#form-login-user").submit(function (event) {

        // Stop form from submitting normally
        event.preventDefault();

        var combinedData = $("#form-login-user").serialize();
        console.log(combinedData);
        $.ajax({
            type: "POST",
            url: "LogIn",
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
                    $('#alertMessage').html(data.form);
                }
            }
        })
    });
});




