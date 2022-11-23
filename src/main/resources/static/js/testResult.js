let api = {
    testResults: function (isNgResult) {
        let testResult = null;
        if (isNgResult) {
            testResult = "NG";
        }

        let params = {
            to: $("#to").val(),
            from: $("#from").val(),
            testResult: testResult
        }

        let url = "/test/result";

        commonMethod.fetchGet(url, params).then(data => drawTestResults(data.testResults)).catch(err => false);
    }
}

function drawTestResults(testResults) {
    $("#container").empty();
    $(testResults).each(function (index, item) {
        let row = document.createElement('div');
        let $row = $(row);
        $row.css("padding", "3%");

        let pTestDate = document.createElement('p');
        let $pTestDate = $(pTestDate);
        $pTestDate.text(item.testDate);

        let table = document.createElement('table');
        let $table = $(table);

        let thRow = document.createElement('tr');
        let $thRow = $(thRow);

        let thTime = document.createElement('th');
        let $thTime = $(thTime);
        $thTime.text("시간");

        let thKeyword = document.createElement('th');
        let $thKeyword = $(thKeyword);
        $thKeyword.text("키워드");

        let thLevel = document.createElement('th');
        let $thLevel = $(thLevel);
        $thLevel.text("레벨");

        let thResult = document.createElement('th');
        let $thResult = $(thResult);
        $thResult.text("테스트 결과");

        $table.append($thRow);
        $thRow.append($thTime).append($thKeyword).append($thLevel).append($thResult);

        $(item.contents).each(function (index, item) {
            let tdRow = document.createElement('tr');
            let $tdRow = $(tdRow);

            let tdTime = document.createElement('td');
            let $tdTime = $(tdTime);
            $tdTime.text(item.time);

            let tdKeyword = document.createElement('td');
            let $tdKeyword = $(tdKeyword);
            $tdKeyword.text(item.keyword);

            let tdLevel = document.createElement('td');
            let $tdLevel = $(tdLevel);
            $tdLevel.text(item.level);

            let tdResult = document.createElement('td');
            let $tdResult = $(tdResult);
            $tdResult.text(item.testResult);

            $table.append($tdRow);
            $tdRow.append($tdTime).append($tdKeyword).append($tdLevel).append($tdResult);
        });
        $("#container").append($row.append($pTestDate).append($table));
    });
}

api.testResults();