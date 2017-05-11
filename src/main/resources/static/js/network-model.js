$(function () {
    $('#removeModelBtn').on('click', function (event) {
        event.preventDefault();
        //TODO add confirmation
        var id = $('#id').val();

        $.ajax({
            url: '/model/remove',
            data: {
                modelId: id
            },
            type: 'POST',
            success: function (data) {
                window.location.replace("/network_models");
            }
        });
    })
});