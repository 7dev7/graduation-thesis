$(function () {
    check_current_spreadsheet();

    function check_current_spreadsheet() {
        $.ajax({
            url: '/spreadsheet/current',
            type: 'POST',
            success: function (data) {
                if (data === "") {
                    $("#table-module").show();
                    $("#load-excel-file-module").show();
                    $("#table-controls-module").hide();
                } else {
                    $("#table-module").show();
                    $("#create-table-module").show();
                    $("#table-controls-module").show();
                    reloadTable();
                }
            }
        });
    }

    $("#createTableBtn").on("click", function (event) {
        event.preventDefault();
        $("#load-excel-file-module").hide();
        $("#create-table-module").show();
        $("#table-controls-module").show();

        loadTable([], []);
    });

    $("#addColumnBtn").on("click", function (event) {
        event.preventDefault();
        $("#addColumnModal").modal();
    });

    $("#createColumnBtn").on("click", function (event) {
        event.preventDefault();

        var columnName = $("#addColumnName").val();
        var columnType = $('#addColumnTypeSelect').find('option:selected').val();

        $("#addColumnModal").modal('hide');

        Pace.track(function () {
            $.ajax({
                type: "POST",
                data: {
                    columnName: columnName,
                    columnType: columnType
                },
                url: '/column/add',
                success: function (data) {
                    reloadTable();
                }
            });
        });
    });

    function reloadTable() {
        $.jgrid.gridUnload('#customTableJQGrid');
        $.ajax({
            url: "/spreadsheet/current",
            type: 'POST',
            success: function (responseData) {
                var cols = responseData.columns;
                var colTypes = responseData.columnTypes;
                var model = [];
                for (var i = 0; i < cols.length; i++) {
                    model.push({label: cols[i], name: cols[i], editable: true});
                }

                $('#inputContinuousColumns option').remove();
                $('#inputCategorialColumns option').remove();
                $('#outputContinuousColumns option').remove();

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

                $("#customTableJQGrid").jqGrid({
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
                    pager: '#customTableJQGridPager',
                    cellsubmit: 'clientArray',
                    editurl: 'clientArray',
                    ondblClickRow: function (rowid, iRow, iCol, e) {
                        var colName = cols[iCol];
                        $("#editColumnName").val(colName);
                        $("#editInitColumnName").val(colName);
                        $("#editColumnId").val(iCol);
                        var template = '[value=' + colTypes[iCol] + ']';
                        $("#editChooseTypeSelect").find(template).attr("selected", "selected");
                        $('.selectpicker').selectpicker('refresh');
                        $("#editColumnModal").modal();
                    }
                }).navGrid('#customTableJQGridPager', {}, {
                    reloadAfterSubmit: true,
                    url: '/row/edit',
                    closeAfterEdit: true,
                    closeOnEscape: true,
                    beforeSubmit: function (postdata, formid) {
                        postdata['___#$RowId$#___'] = postdata['customTableJQGrid_id'];
                        delete postdata['customTableJQGrid_id'];
                        return [true, ""];
                    },
                    afterSubmit: function (postdata, formid) {
                        $.each(cols, function (i, item) {
                            $("#customTableJQGrid").jqGrid('setCell', formid['___#$RowId$#___'], item, formid[item]);
                        });
                    }
                }, {
                    reloadAfterSubmit: false,
                    url: '/row/add',
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
                    url: '/row/remove'
                }, {}, {});
            }
        });
    }

    $("#removeColumnBtn").on("click", function (event) {
        event.preventDefault();
        var columnId = $("#editColumnId").val();
        var columnInitName = $("#editInitColumnName").val();
        $("#editColumnModal").modal('hide');
        $.ajax({
            type: 'POST',
            data: {
                columnId: columnId,
                initName: columnInitName
            },
            url: '/column/remove',
            success: function (data) {
                reloadTable();
            }
        });
    });

    $("#editColumnBtn").on("click", function (event) {
        event.preventDefault();
        var columnName = $("#editColumnName").val();
        var columnInitName = $("#editInitColumnName").val();
        var columnId = $("#editColumnId").val();
        var columnType = $('#editChooseTypeSelect').find('option:selected').val();

        $("#customTableJQGrid").jqGrid('setLabel', columnId, columnName);
        $("#editColumnModal").modal('hide');
        $.ajax({
            type: "POST",
            data: {
                columnId: columnId,
                columnName: columnName,
                columnType: columnType,
                initName: columnInitName
            },
            url: '/column/update',
            success: function (data) {
                reloadTable();
            }
        });
    })
});

function loadTable(cols, rows) {
    $.ajax({
        type: "POST",
        url: '/spreadsheet/create'
    });

    var model = [];
    for (var i = 0; i < cols.length; i++) {
        model.push({label: cols[i], name: cols[i], editable: true});
    }

    $("#customTableJQGrid").jqGrid({
        data: rows,
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
        pager: '#customTableJQGridPager',
        cellsubmit: 'clientArray',
        editurl: 'clientArray'
    }).navGrid('#customTableJQGridPager', {}, {}, {}, {}, {}, {});
}