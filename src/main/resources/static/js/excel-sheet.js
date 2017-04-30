$(function () {
    var options = {
        beforeSend: function () {
        },
        uploadProgress: function (event, position, total, percentComplete) {
        },
        success: function (data) {

        },
        complete: function (response) {
            if (response.status !== 201) {
                $("#message").show();
                $("#message").text(response.responseText);
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
        },
        error: function (data) {
        }
    };

    $("#load-file-form").ajaxForm(options);

    $("#config-btn").on("click", function (event) {
        event.preventDefault();

        $("#show-excel-file-module").hide();
        $("#config-model-module").show();
    });

    $("#autoModeBtn").on("click", function (event) {
        event.preventDefault();

        $("#config-model-module").hide();
        $("#automatic-mode-module").show();
    });

    $("#mlpCheckbox").on("change", function (event) {
        event.preventDefault();
        var minNum = $("#mlpMinNumOfNeuron");
        var maxNum = $("#mlpMaxNumOfNeuron");

        minNum.prop('disabled', !minNum.prop('disabled'));
        maxNum.prop('disabled', !maxNum.prop('disabled'));

        if (minNum.val() === "") {
            minNum.val(3);
        }
        if (maxNum.val() === "") {
            maxNum.val(10);
        }
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
});
