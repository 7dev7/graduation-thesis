$(function () {
    //TODO show start page

    var countOfInputs = 1;
    $('#addInputParameter').on('click', function (event) {
        event.preventDefault();
        var className = 'paramItem' + countOfInputs;

        var block = $("<div class='" + className + "'></div>");
        var input = $("<div class='col-md-11'><input type='number' required='required' class='form-control inParam' id='id" + countOfInputs + "'/></div>");
        var btnBlock = $("<div class='col-md-1'></div>");
        var btn = $("<button type='button' value='" + countOfInputs + "' class='btn btn-danger inParam removeParamBtn' id='deleteParameter" + countOfInputs + "'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></button>");

        btn.on('click', function (event) {
            event.preventDefault();
            var index = $(this).val();
            removeItemById(index);
        });

        btn.appendTo(btnBlock);
        input.appendTo(block);
        btnBlock.appendTo(block);
        block.appendTo('#inputsGroup');

        countOfInputs += 1;
    });

    $('#chooseModel').on('click', function (event) {
        event.preventDefault();

        if (!validate()) {
            return;
        }

        $("#inputsErrorBlock").hide();

        $('#input-values-module').hide();
        $('#choose-model-module').show();
    });

    $('#backToInputValuesBtn').on('click', function (event) {
        event.preventDefault();

        $('#input-values-module').show();
        $('#choose-model-module').hide();
    });
});

function validate() {
    var msg = $("#inputsErrorMessage");
    var errorBlock = $("#inputsErrorBlock");

    var flag = true;

    $('.inParam').each(function (i, item) {
        var value = item.value;
        if (value === "") {
            msg.html("Заполните все входные значения");
            errorBlock.show();
            flag = false;
        }
    });
    return flag;
}

function removeItemById(id) {
    var className = ".paramItem" + id;
    $(className).remove();
}