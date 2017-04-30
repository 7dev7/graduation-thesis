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

            $("#jqGrid").jqGrid({
                url: 'http://trirand.com/blog/phpjqgrid/examples/jsonp/getjsonp.php?callback=?&qwery=longorders',
                mtype: "GET",
                datatype: "jsonp",
                colModel: [
                    {label: 'OrderID', name: 'OrderID', key: true},
                    {label: 'Customer ID', name: 'CustomerID'},
                    {label: 'Order Date', name: 'OrderDate'},
                    {label: 'Freight', name: 'Freight'},
                    {label: 'Ship Name', name: 'ShipName'}
                ],
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
