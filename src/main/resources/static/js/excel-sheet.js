$(function () {
    var options = {
        complete: function (response) {
            if (response.status !== 201) {
                var msgField = $("#message");
                msgField.show();
                msgField.text(response.responseText);
                return;
            }

            $("#load-excel-file-module").hide();
            $("#show-excel-file-module").show();

            var responseData = JSON.parse(response.responseText);
            var cols = responseData.columns;
            var model = [];
            for (var i = 0; i < cols.length; i++) {
                model.push({label: cols[i], name: cols[i], editable: true});
            }

            $.each(cols, function (i, item) {
                $('#inputColumns').append($('<option>', {
                    value: i,
                    text: item
                }));
                $('#outputColumns').append($('<option>', {
                    value: i,
                    text: item
                }));
            });

            $('#inputColumns').selectpicker('refresh');
            $('#outputColumns').selectpicker('refresh');

            $("#jqGrid").jqGrid({
                data: responseData.rows,
                datatype: "local",
                colModel: model,
                autowidth: true,
                cellEdit: true,
                shrinkToFit: true,
                width: null,
                scroll: true,
                viewrecords: true,
                height: 500,
                rowNum: 25,
                pager: "#jqGridPager",
                cellsubmit: 'clientArray',
                editurl: 'clientArray',
                editoptions: {
                    dataInit: function (elem) {
                        $(elem).focus(function () {
                            this.select();
                        })
                    },
                    dataEvents: [
                        {
                            type: 'keydown',
                            fn: function (e) {
                                var key = e.charCode || e.keyCode;
                                if (key === 9)//tab
                                {
                                    var grid = $('#jqGrid');
                                    //Save editing for current row
                                    grid.jqGrid('saveRow', selIRow, false, 'clientArray');
                                    //If at bottom of grid, create new row
                                    if (selIRow++ === grid.getDataIDs().length) {
                                        grid.addRowData(selIRow, {});
                                    }
                                    //Enter edit row for next row in grid
                                    grid.jqGrid('editRow', selIRow, false, 'clientArray');
                                }
                            }
                        }
                    ]
                }
            });
        }
    };

    $("#load-file-form").ajaxForm(options);

    $("#autoModeBtn").on("click", function (event) {
        event.preventDefault();

        $("#show-excel-file-module").hide();
        $("#automatic-mode-module").show();
    });

    $("#mlpCheckbox").on("change", function (event) {
        event.preventDefault();
        var minNum = $("#mlpMinNumOfNeuron");
        var maxNum = $("#mlpMaxNumOfNeuron");

        var hiddenLogisticFunc = $("#hiddenLogisticFunc");
        var hiddenHyperbolicFunc = $("#hiddenHyperbolicFunc");
        var hiddenExpFunc = $("#hiddenExpFunc");
        var hiddenSinFunc = $("#hiddenSinFunc");


        var outLogisticFunc = $("#outLogisticFunc");
        var outHyperbolicFunc = $("#outHyperbolicFunc");
        var outExpFunc = $("#outExpFunc");
        var outSinFunc = $("#outSinFunc");

        minNum.prop('disabled', !minNum.prop('disabled'));
        maxNum.prop('disabled', !maxNum.prop('disabled'));

        if (minNum.val() === "") {
            minNum.val(3);
        }
        if (maxNum.val() === "") {
            maxNum.val(10);
        }
        hiddenLogisticFunc.prop('disabled', !hiddenLogisticFunc.prop('disabled'));
        hiddenHyperbolicFunc.prop('disabled', !hiddenHyperbolicFunc.prop('disabled'));
        hiddenExpFunc.prop('disabled', !hiddenExpFunc.prop('disabled'));
        hiddenSinFunc.prop('disabled', !hiddenSinFunc.prop('disabled'));

        outLogisticFunc.prop('disabled', !outLogisticFunc.prop('disabled'));
        outHyperbolicFunc.prop('disabled', !outHyperbolicFunc.prop('disabled'));
        outExpFunc.prop('disabled', !outExpFunc.prop('disabled'));
        outSinFunc.prop('disabled', !outSinFunc.prop('disabled'));
    });

    $("#rbfCheckbox").on("change", function (event) {
        event.preventDefault();
        var minNum = $("#rbfMinNumOfNeuron");
        var maxNum = $("#rbfMaxNumOfNeuron");

        minNum.prop('disabled', !minNum.prop('disabled'));
        maxNum.prop('disabled', !maxNum.prop('disabled'));

        if (minNum.val() === "") {
            minNum.val(3);
        }
        if (maxNum.val() === "") {
            maxNum.val(10);
        }
    });

    $("#backBtnInAutoModule").on("click", function (event) {
        event.preventDefault();
        $("#automatic-mode-module").hide();
        $("#show-excel-file-module").show();
    });

    $("#trainBtn").on("click", function (event) {
        event.preventDefault();
        if (!validate()) {
            return;
        }
        hideErrors();

        var data = loadData();
        $.ajax({
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(data),
            url: '/train'
        });
    });

    $('.alert .close').on('click', function (e) {
        $(this).parent().hide();
    });

    function validate() {
        var msg = $("#autoModeErrorMessage");
        var errorBlock = $("#autoModeErrorBlock");

        var mlp = $("#mlpCheckbox");
        var rbf = $("#rbfCheckbox");

        var mlpMinNum = $("#mlpMinNumOfNeuron");
        var mlpMaxNum = $("#mlpMaxNumOfNeuron");

        var rbfMinNum = $("#rbfMinNumOfNeuron");
        var rfbMaxNum = $("#rbfMaxNumOfNeuron");

        if (!mlp.prop("checked") && !rbf.prop("checked")) {
            msg.html("Выберите хотя бы один тип сети");
            errorBlock.show();
            return false;
        }

        if (mlp.prop("checked") && mlpMinNum.val() === "") {
            msg.html("Минимальное число нейронов скрытого слоя для многослойного перцептрона не заполнено");
            errorBlock.show();
            return false;
        }

        if (mlp.prop("checked") && mlpMaxNum.val() === "") {
            msg.html("Максимальное число нейронов скрытого слоя для многослойного перцептрона не заполнено");
            errorBlock.show();
            return false;
        }

        if (rbf.prop("checked") && rbfMinNum.val() === "") {
            msg.html("Минимальное число нейронов для сети радиальных базисных функций не заполнено");
            errorBlock.show();
            return false;
        }

        if (rbf.prop("checked") && rfbMaxNum.val() === "") {
            msg.html("Максимальное число нейронов для сети радиальных базисных функций не заполнено");
            errorBlock.show();
            return false;
        }

        if (mlp.prop("checked") && $('#hiddenFuncs').find('input:checked').length === 0) {
            msg.html("Выберите хотя бы одну функцию активации для скрытого слоя");
            errorBlock.show();
            return false;
        }

        if (mlp.prop("checked") && $('#outFuncs').find('input:checked').length === 0) {
            msg.html("Выберите хотя бы одну функцию активации для выходного слоя");
            errorBlock.show();
            return false;
        }
        return true;
    }

    function hideErrors() {
        $("#autoModeErrorBlock").hide();
    }

    function loadData() {
        var data = {};
        data['isMLPNeeded'] = $("#mlpCheckbox").prop("checked");
        data['isRBFNeeded'] = $("#rbfCheckbox").prop("checked");

        data['mlpMinNumOfNeuron'] = $("#mlpMinNumOfNeuron").val();
        data['mlpMaxNumOfNeuron'] = $("#mlpMaxNumOfNeuron").val();

        data['rbfMinNumOfNeuron'] = $("#rbfMinNumOfNeuron").val();
        data['rbfMaxNumOfNeuron'] = $("#rbfMaxNumOfNeuron").val();

        var inputColumnIndexes = [];
        $('#inputColumns').find('option:selected').each(function (i, item) {
            inputColumnIndexes.push(item.value);
        });
        data['inputColumnIndexes'] = inputColumnIndexes;

        var outputColumnIndexes = [];
        $('#outputColumns').find('option:selected').each(function (i, item) {
            outputColumnIndexes.push(item.value);
        });
        data['outputColumnIndexes'] = outputColumnIndexes;

        var hiddenNeuronsFuncs = [];
        $('#hiddenFuncs').find('input:checked').each(function (i, item) {
            hiddenNeuronsFuncs.push(item.value);
        });
        data['hiddenNeuronsFuncs'] = hiddenNeuronsFuncs;

        var outNeuronsFuncs = [];
        $('#outFuncs').find('input:checked').each(function (i, item) {
            outNeuronsFuncs.push(item.value);
        });
        data['outNeuronsFuncs'] = outNeuronsFuncs;

        return data;
    }
});
