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
        Pace.track(function () {
            $.ajax({
                url: "/current_spreadsheet",
                type: 'POST',
                success: function (responseData) {
                    var cols = responseData.columns;
                    var colTypes = responseData.columnTypes;
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
                        editurl: 'clientArray',
                        ondblClickRow: function (rowid, iRow, iCol, e) {
                            var colName = cols[iCol];
                            $("#columnName").val(colName);
                            $("#columnId").val(iCol);
                            var template = '[value=' + colTypes[iCol] + ']';
                            $("#chooseTypeSelect").find(template).attr("selected", "selected");
                            $('.selectpicker').selectpicker('refresh');
                            $("#myModal").modal();
                        }
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
        });
    }

    $("#load-file-form").ajaxForm(options);

    $("#trainBtn").on("click", function (event) {
        event.preventDefault();
        if (!validate()) {
            return;
        }
        hideErrors();

        var data = loadData();
        Pace.track(function () {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
                url: '/train',
                success: function (data) {
                    window.location.replace("/network_models");
                }
            });
        });
    });

    $("#saveColumnBtn").on("click", function (event) {
        event.preventDefault();
        var columnName = $("#columnName").val();
        var columnId = $("#columnId").val();
        var columnType = $('#chooseTypeSelect').find('option:selected').val();

        $("#jqGrid").jqGrid('setLabel', columnId, columnName);
        $("#myModal").modal('hide');
        $.ajax({
            type: "POST",
            data: {
                columnId: columnId,
                columnName: columnName,
                columnType: columnType
            },
            url: '/analysis/update_column'
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