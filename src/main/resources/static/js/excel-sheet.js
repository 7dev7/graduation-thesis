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

            loadSpreadsheetData();
        }
    };

    function loadSpreadsheetData() {
        $.ajax({
            url: "/current_spreadsheet",
            type: 'POST',
            success: function (responseData) {
                var cols = responseData.columns;
                var model = [];
                for (var i = 0; i < cols.length; i++) {
                    model.push({label: cols[i], name: cols[i], editable: true});
                }

                $.each(cols, function (i, item) {
                    $('#inputContinuousColumns').append($('<option>', {
                        value: i,
                        text: item
                    }));
                    $('#inputCategorialColumns').append($('<option>', {
                        value: i,
                        text: item
                    }));
                    $('#outputContinuousColumns').append($('<option>', {
                        value: i,
                        text: item
                    }));
                });

                $('#inputContinuousColumns').selectpicker('refresh');
                $('#inputCategorialColumns').selectpicker('refresh');
                $('#outputContinuousColumns').selectpicker('refresh');

                $("#jqGrid").jqGrid({
                    data: responseData.rows,
                    datatype: "local",
                    colModel: model,
                    localReader: {
                        id: "___#$RowId$#___"
                    },
                    autowidth: true,
                    shrinkToFit: true,
                    width: null,
                    scroll: true,
                    viewrecords: true,
                    height: 500,
                    rowNum: 25,
                    pager: '#jqGridPager',
                    cellsubmit: 'clientArray',
                    editurl: 'clientArray'
                }).navGrid('#jqGridPager', {}, {
                    reloadAfterSubmit: true,
                    url: '/analysis/edit',
                    closeAfterEdit: true,
                    closeOnEscape: true,
                    beforeSubmit: function (postdata, formid) {
                        postdata['___#$RowId$#___'] = postdata['jqGrid_id'];
                        delete postdata['jqGrid_id'];
                        return [true, ""];
                    },
                    afterSubmit: function (postdata, formid) {
                        $.each(cols, function (i, item) {
                            $("#jqGrid").jqGrid('setCell', formid['___#$RowId$#___'], item, formid[item]);
                        });
                    }
                }, {
                    reloadAfterSubmit: false,
                    url: '/analysis/add',
                    position: "last",
                    closeAfterAdd: true,
                    closeOnEscape: true,
                    afterSubmit: function (response, postdata) {
                        return [true, "", $.parseJSON(response.responseText)];
                    }
                }, {
                    reloadAfterSubmit: false,
                    closeAfterDelete: true,
                    closeOnEscape: true,
                    url: '/analysis/delete'
                }, {}, {});
            }
        });
    }

    $("#load-file-form").ajaxForm(options);

    $("#autoModeBtn").on("click", function (event) {
        event.preventDefault();

        var inputContinuousColumnIndexes = [];
        $('#inputContinuousColumns').find('option:selected').each(function (i, item) {
            inputContinuousColumnIndexes.push(item.value);
        });

        var inputCategorialColumnIndexes = [];
        $('#inputCategorialColumns').find('option:selected').each(function (i, item) {
            inputCategorialColumnIndexes.push(item.value);
        });

        var outputContinuousColumnIndexes = [];
        $('#outputContinuousColumns').find('option:selected').each(function (i, item) {
            outputContinuousColumnIndexes.push(item.value);
        });

        var msg = $("#inOutErrorMessage");
        var errorBlock = $("#inOutErrorBlock");

        var intersect1 = intersection(inputContinuousColumnIndexes, inputCategorialColumnIndexes);
        if (intersect1.length > 0) {
            msg.html("Одинаковый признак во входных непрерывных и категориальных значениях");
            errorBlock.show();
            return;
        }

        var intersect2 = intersection(inputCategorialColumnIndexes, outputContinuousColumnIndexes);
        if (intersect2.length > 0) {
            msg.html("Одинаковый признак во входных категориальных и выходных значениях");
            errorBlock.show();
            return;
        }

        var intersect3 = intersection(inputContinuousColumnIndexes, outputContinuousColumnIndexes);
        if (intersect3.length > 0) {
            msg.html("Одинаковый признак во входных непрерывных и выходных значениях");
            errorBlock.show();
            return;
        }

        if (inputContinuousColumnIndexes.length === 0) {
            msg.html("Выберите входные значения");
            errorBlock.show();
            return;
        }

        if (outputContinuousColumnIndexes.length === 0) {
            msg.html("Выберите выходные значения");
            errorBlock.show();
            return;
        }

        $("#inOutErrorBlock").hide();

        $("#show-excel-file-module").hide();
        $("#automatic-mode-module").show();
    });

    function intersection(a, b) {
        var m = a.length, n = b.length, c = 0, res = [];
        for (var i = 0; i < m; i++) {
            var j = 0, k = 0;
            while (b[j] !== a[i] && j < n) j++;
            while (res[k] !== a[i] && k < c) k++;
            if (j !== n && k === c) res[c++] = a[i];
        }
        return res;
    }

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
        var rbfMaxNum = $("#rbfMaxNumOfNeuron");

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

        if (parseInt(mlpMinNum.val()) > parseInt(mlpMaxNum.val())) {
            msg.html("Минимальное число нейронов больше максимального числа нейронов");
            errorBlock.show();
            return false;
        }

        if (rbf.prop("checked") && rbfMinNum.val() === "") {
            msg.html("Минимальное число нейронов для сети радиальных базисных функций не заполнено");
            errorBlock.show();
            return false;
        }

        if (rbf.prop("checked") && rbfMaxNum.val() === "") {
            msg.html("Максимальное число нейронов для сети радиальных базисных функций не заполнено");
            errorBlock.show();
            return false;
        }

        //TODO implement int number validation

        if (parseInt(rbfMinNum.val()) > parseInt(rbfMaxNum.val())) {
            msg.html("Минимальное число нейронов больше максимального числа нейронов");
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

        var inputContinuousColumnIndexes = [];
        $('#inputContinuousColumns').find('option:selected').each(function (i, item) {
            inputContinuousColumnIndexes.push(item.value);
        });
        data['inputContinuousColumnIndexes'] = inputContinuousColumnIndexes;


        var inputCategorialColumnIndexes = [];
        $('#inputCategorialColumns').find('option:selected').each(function (i, item) {
            inputCategorialColumnIndexes.push(item.value);
        });
        data['inputCategorialColumnIndexes'] = inputCategorialColumnIndexes;


        var outputContinuousColumnIndexes = [];
        $('#outputContinuousColumns').find('option:selected').each(function (i, item) {
            outputContinuousColumnIndexes.push(item.value);
        });
        data['outputContinuousColumnIndexes'] = outputContinuousColumnIndexes;


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
