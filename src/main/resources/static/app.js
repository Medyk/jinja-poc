$(document).ready(function() {
    $("#send").on("click", function() {
        $.ajax({
            url: "/jinja",
            data: {
                param1: $("#jinja_param1").val(),
                param2: $("#jinja_param2").val(),
                param3: $("#jinja_param3").val(),
                template: $("#jinja_template").val()
            },
            dataType: "text"
        })
        .done(function(data, textStatus, jqXHR) {
            $("#jinja_response").val(data);
            $("#jinja_result").val(jQuery.parseJSON(data).result);
        })
        .fail(function(jqXHR, textStatus, errorThrown) {
            $("#jinja_response").val("<error>: "+textStatus);
            $("#jinja_result").val("<error>");
        });
    });
});