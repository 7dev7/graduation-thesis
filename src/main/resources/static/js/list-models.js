$(function () {
    $("#loadModelErrorMsg").hide();

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

    var options = {
        complete: function (data) {
            if (data.status !== 201) {
                var msg = $("#loadModelErrorMsg");
                msg.text(data.responseText);
                msg.show();
                return;
            }
            $("#loadModelErrorMsg").hide();
            window.location.replace("/model?id=" + data.responseJSON.id);
        }
    };

    $("#uploadModelForm").ajaxForm(options);
});