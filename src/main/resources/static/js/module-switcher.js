$(function () {
    $("#inputNewTableBtn").on('click', function (event) {
        event.preventDefault();
        $('#create-table-module').hide();
        $('#show-excel-file-module').hide();
        $("#table-controls-module").hide();
        $('#load-excel-file-module').show();
    });

    $("#autoModeBtn").on("click", function (event) {
        event.preventDefault();
        if (!isValidState()) {
            return;
        }
        $("#inOutErrorBlock").hide();

        $("#table-module").hide();
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
        // $("#show-excel-file-module").show();
        $("#table-module").show();
    });
});

function isValidState() {
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
        return false;
    }

    var intersect2 = intersection(inputCategorialColumnIndexes, outputContinuousColumnIndexes);
    if (intersect2.length > 0) {
        msg.html("Одинаковый признак во входных категориальных и выходных значениях");
        errorBlock.show();
        return false;
    }

    var intersect3 = intersection(inputContinuousColumnIndexes, outputContinuousColumnIndexes);
    if (intersect3.length > 0) {
        msg.html("Одинаковый признак во входных непрерывных и выходных значениях");
        errorBlock.show();
        return false;
    }

    if (inputContinuousColumnIndexes.length === 0) {
        msg.html("Выберите входные значения");
        errorBlock.show();
        return false;
    }

    if (outputContinuousColumnIndexes.length === 0) {
        msg.html("Выберите выходные значения");
        errorBlock.show();
        return false;
    }
    return true;
}

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