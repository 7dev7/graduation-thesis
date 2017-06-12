$(function () {
    $('#analyzeBtn').on('click', function (event) {
        event.preventDefault();
        if (!validate()) {
            return;
        }
        $("#inputsErrorBlock").hide();
        var id = $('#modelId').val();
        compute(id);
        $('#input-values-module').hide();
        $('#show-result-module').show();
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

function compute(modelId) {
    var inputData = loadInputData();
    $.ajax({
        url: '/model/compute',
        type: 'POST',
        contentType: "application/json",
        data: JSON.stringify({
            inputs: inputData,
            modelId: modelId
        }),
        success: function (data) {
            $('#resultModelName').attr('href', '/model?id=' + data.modelId);
            $('#resultModelName').html(data.modelName);

            for (var i = 0; i < data.inputValues.length; i++) {
                $('#inputValues').append("<li class='list-group-item'>" + data.inColumns[i].name + ":&nbsp;&nbsp;&nbsp;&nbsp;" + data.inputValues[i] + "</li>");
            }

            for (var i = 0; i < data.values.length; i++) {
                var val = data.values[i];
                var mearType = data.outColumns[i].measurementType;
                if (mearType === 'INTEGER') {
                    val = Math.round(val);
                }
                $('#outputValues').append("<li class='list-group-item'>" + data.outColumns[i].name + " :&nbsp;&nbsp;&nbsp;&nbsp;" + val + "</li>");
            }
        }
    });
}

function loadInputData() {
    var values = [];
    $('.inParam').each(function (i, item) {
        values.push(item.value);
    });
    return values;
}