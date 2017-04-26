$(document).ready(function () {
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
});