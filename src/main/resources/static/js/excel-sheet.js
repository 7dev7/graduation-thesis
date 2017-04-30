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
                model.push({label: cols[i], name: cols[i]});
            }

            $("#jqGrid").jqGrid({
                data: responseData.rows,
                datatype: "local",
                colModel: model,
                autowidth: true,
                shrinkToFit: true,
                width: null,
                scroll: true,
                viewrecords: true,
                height: 500,
                rowNum: 25,
                pager: "#jqGridPager"
            });
        },
        error: function (data) {
        }
    };

    $("#load-file-form").ajaxForm(options);
});
