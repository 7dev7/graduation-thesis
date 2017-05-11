$(function () {
    var countOfInputs = 1;
    $('#addInputParameter').on('click', function (event) {
        event.preventDefault();
        var className = 'paramItem' + countOfInputs;

        var block = $("<div class='" + className + "'></div>");
        var input = $("<div class='col-md-11'><input type='number' class='form-control inParam' id='id" + countOfInputs + "'/></div>");
        var btnBlock = $("<div class='col-md-1'></div>");
        var btn = $("<button type='button' value='" + countOfInputs + "' class='btn btn-danger inParam removeParamBtn' id='deleteParameter" + countOfInputs + "'><span class='glyphicon glyphicon-remove' aria-hidden='true'></span></button>");


        btn.on('click', function (event) {
            event.preventDefault();
            var index = $(this).val();
            //TODO handle remove item
        });

        btn.appendTo(btnBlock);
        input.appendTo(block);
        btnBlock.appendTo(block);
        block.appendTo('#inputsGroup');

        countOfInputs += 1;
    });
});