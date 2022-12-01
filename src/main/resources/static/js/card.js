let api = {
    init: function () {
        let url = "/card/" + $("#boxId").val();
        commonMethod.fetchGet(url)
            .then(data => this.initCallback(data))
            .catch(err => false);
    },

    save: function () {
        let url = "/card";

        let $addRow = $("#addRow");
        let body = {
            boxId: $("#boxId").val(),
            keyword: $addRow.find(".keyword").val(),
            concept: $addRow.find(".concept").val()
        };

        commonMethod.fetchPost(url, body)
            .then(data => addItem(data.id, data.keyword, data.concept))
            .then(() => clearItem($addRow))
            .catch(err => false);
    },

    changeBoxName: function () {
        let url = "/card";
        let body = {
            boxId: $("#boxId").val(),
            boxName: $("#boxName").val()
        };
        commonMethod.fetch(url, "PUT", body)
            .catch(err => false);
    },

    delCard: function (id) {
        let url = "/card";
        let body = {
            id: id
        };
        commonMethod.fetch(url, "DELETE", body)
            .then(() => $("#card" + id).remove())
            .catch(err => false);
    },

    initCallback: function (data) {
        if (data == null) return;

        $("#boxName").val(data.boxName);
        $(data.cards).each(function (index, item) {
            addItem(item.id, item.keyword, item.concept);
        });
    }
}

function addItem(id, keyword, concept) {
    // 이름
    let inputKeyword = "<input type='text' class='keyword form-control mt-2' style='width: 30%; cursor: auto;' value='" + keyword.replace('\'', '') + "' readonly>";
    // 선택버튼 생성
    let inputConcept = "<input type='text' class='concept form-control mt-2' style='cursor: auto;' value='" + concept + "' readonly>";
    let delBtn = "<input type='button' class='btn btn-success mt-2 add-btn' onclick='api.delCard(" + id + ");' value='-'>";
    // div블럭 생성
    let div = "<div id='card" + id + "' class='d-flex'></div>";
    let $div = $(div);
    // contents
    $div.append(inputKeyword).append(inputConcept).append(delBtn);
    $("#wrap-contents").append($div);
}

function clearItem(row) {
    let $row = $(row);
    $row.find(".keyword").val("");
    $row.find(".concept").val("");
}

api.init();