$(function () {
    // var options = {
    //     complete: function (response) {
    //         alert(response);
    //     }
    // };
    //
    // $("#uploadModelForm").ajaxForm(options);

    $("#remove-model-confirm").dialog({
        autoOpen: false,
        resizable: false,
        modal: true,
        height: "auto",
        width: 400
    });

    $('.removeModelBtn').on('click', function (event) {
        event.preventDefault();
        var id = $(this).val();
        var confirmDialog = $("#remove-model-confirm");
        confirmDialog.dialog({
            buttons: {
                "Удалить модель": function () {
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
                    $(this).dialog("close");
                },
                'Отмена': function () {
                    $(this).dialog("close");
                }
            }
        });
        confirmDialog.dialog("open");
    });
});