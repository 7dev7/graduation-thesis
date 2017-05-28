$(function () {
    //TODO show start page

    var countOfInputs = 1;
    $('#addInputParameter').on('click', function (event) {
        event.preventDefault();
        var className = 'paramItem' + countOfInputs;

        var block = $("<div class='" + className + "'></div>");
        var input = $("<div class='col-md-11'><input type='number' required='required' class='form-control inParam' id='id" + countOfInputs + "'/></div>");
        var btnBlock = $("<div class='col-md-1'></div>");
        var btn = $("<button type='button' value='" + countOfInputs + "' class='btn btn-danger removeParamBtn' id='deleteParameter" + countOfInputs + "'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></button>");

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

        var numOfInputs = $('.inParam').length;

        $.ajax({
            url: '/model/appropriate',
            type: 'POST',
            data: {
                numOfInputs: numOfInputs
            },
            success: function (data) {
                fillModels(data);
                $('#choose-model-module').show();
            }
        });
    });

    $('#backToInputValuesBtn').on('click', function (event) {
        event.preventDefault();

        $('#input-values-module').show();
        $('#choose-model-module').hide();
    });

    function fillModels(data) {
        var modelsListBlock = $('#modelsList');
        modelsListBlock.empty();

        if (data.length === 0) {
            var msg = $("<h4>У вас нет моделей с количеством входных параметров равным " + $('.inParam').length + "</h4>");
            msg.appendTo(modelsListBlock);
            return
        }

        for (var i = 0; i < data.length; i++) {
            var item = data[i];

            var parent = $("<div class='modelItem'></div>");
            var name = $("<h3 value='" + item.id + "'><a href='#'>" + item.name + "</a></h3>");

            name.on('click', function (event) {
                event.preventDefault();
                var id = $(this).attr('value');
                compute(id);
            });

            var dt = new Date(item.dateOfCreation);
            var curr_date = dt.getDate();
            var curr_month = dt.getMonth();
            var curr_year = dt.getFullYear();
            var d = curr_year + "-" + curr_month + "-" + curr_date;

            var creationDate = $("<h4>Дата создания: <span>" + d + "</span></h4>");
            var error = $("<h5>Ошибка: <span>" + item.error + "</span></h5>");
            var hiddenFunc = null;
            var outFunc = null;
            if (item.hiddenFuncFormatted !== null) {
                hiddenFunc = $("<h5>Функция активации скрытого слоя: <span>" + item.hiddenFuncFormatted + "</span></h5>");
            }
            if (item.outFuncFormatted !== null) {
                outFunc = $("<h5>Функция активации выходного слоя: <span>" + item.outFuncFormatted + "</span></h5>");
            }
            var description = $("<h5>Описание: <span>" + item.description + "</span></h5>");


            var inValsBlock = $('<div></div>');
            var inTitle = $('<h4>Входные колонки:</h4>');

            inTitle.appendTo(inValsBlock);

            var inVals = $('<ul class="list-group"></ul>');
            for (var j = 0; j < item.inputColumns.length; j++) {
                inVals.append("<li class='list-group-item'>" + item.inputColumns[j] + "</li>");
            }
            inVals.appendTo(inValsBlock);


            var outValsBlock = $('<div></div>');
            var outTitle = $('<h4>Выходные колонки:</h4>');

            outTitle.appendTo(outValsBlock);

            var outVals = $('<ul class="list-group"></ul>');
            for (var j = 0; j < item.outColumns.length; j++) {
                outVals.append("<li class='list-group-item'>" + item.outColumns[j] + "</li>");
            }
            outVals.appendTo(outValsBlock);


            name.appendTo(parent);
            creationDate.appendTo(parent);
            error.appendTo(parent);
            if (hiddenFunc !== null) {
                hiddenFunc.appendTo(parent);
            }
            if (outFunc !== null) {
                outFunc.appendTo(parent);
            }
            description.appendTo(parent);

            inValsBlock.appendTo(parent);
            outValsBlock.appendTo(parent);
            $('<hr/>').appendTo(parent);

            parent.appendTo(modelsListBlock);
        }
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
                $('#choose-model-module').hide();

                $('#resultModelName').attr('href', '/model?id=' + data.modelId);
                $('#resultModelName').html(data.modelName);

                for (var i = 0; i < data.inputValues.length; i++) {
                    $('#inputValues').append("<li class='list-group-item'>" + data.inColumns[i].name + ":    " + data.inputValues[i] + "</li>");
                }

                for (var i = 0; i < data.values.length; i++) {
                    var val = data.values[i];
                    var mearType = data.outColumns[i].measurementType;
                    if (mearType === 'INTEGER') {
                        val = Math.round(val);
                    }
                    $('#outputValues').append("<li class='list-group-item'>" + data.outColumns[i].name + " :    " + val + "</li>");
                }

                $('#show-result-module').show();
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