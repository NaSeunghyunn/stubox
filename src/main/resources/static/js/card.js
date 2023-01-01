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
            keyword: $("#add-keyword").val(),
            concept: $("#add-concept").val()
        };

        commonMethod.fetchPost(url, body)
            .then(data => addItem(data.id, data.keyword, data.concept))
            .then(() => clearItem($addRow))
            .catch(err => false);
    },

    changeBoxName: function () {
        let url = "/card/boxName";
        let body = {
            boxId: $("#boxId").val(),
            boxName: $("#boxName").val()
        };
        commonMethod.fetch(url, "PUT", body)
            .catch(err => false);
    },

    delCard: function () {
        let url = "/card";
        let body = {
            id: $("#cardId").val()
        };
        commonMethod.fetch(url, "DELETE", body)
            .then(() => $("#card" + $("#cardId").val()).remove())
            .catch(err => false);
    },

    updateCard: function () {
        let url = "/card";
        let body = {
            id: $("#cardId").val(),
            keyword: $("#keyword").val(),
            concept: $("#concept").val()
        };
        commonMethod.fetch(url, "PUT", body)
            .then(() => this.updateCallback())
            .catch(err => false);
    },

    initCallback: function (data) {
        if (data == null) return;

        $("#boxName").val(data.boxName);
        $(data.cards).each(function (index, item) {
            addItem(item.id, item.keyword, item.concept);
        });
    },

    updateCallback: function () {
        let $card = $("#card" + $("#cardId").val());
        $card.find(".keyword").val($("#keyword").val());
        $card.find(".concept").val($("#concept").val());
        $card.find(".card").val($("#keyword").val());
    }
}

function addItem(id, keyword, concept) {
    // 이름
    let keywordBtn = document.createElement('input');
    let $keywordBtn = $(keywordBtn);
    $keywordBtn.attr("type", "button");
    $keywordBtn.attr("class", "btn btn-outline-success mt-2 card");
    $keywordBtn.attr("onclick", "showModal(this)");
    $keywordBtn.css("width", "100%");
    $keywordBtn.css("text-align", "left");
    $keywordBtn.val(keyword.replace('\'', ''));

    //    let inputKeyword = "<input type='button' class='keyword form-control mt-2' onclick=\'showModal(" + id + ",'"+keyword+"','"+concept+"');\' value='" + keyword.replace('\'', '') + "'>";
    // 선택버튼 생성
    let inputKeyword = "<input type='hidden' class='keyword' value='" + keyword + "'>";
    let inputConcept = "<input type='hidden' class='concept' value='" + concept + "'>";
    let inputCardId = "<input type='hidden' class='cardId' value='" + id + "'>";
    // div블럭 생성
    let div = "<div id='card" + id + "' class='d-flex'></div>";
    let $div = $(div);
    // contents
    $div.append($keywordBtn).append(inputKeyword).append(inputConcept).append(inputCardId);
    $("#wrap-contents").append($div);
}

function clearItem(row) {
    let $row = $(row);
    $row.find(".keyword").val("");
    $row.find(".concept").val("");
    $("#add-keyword").val("");
    $("#add-concept").val("");
}

function showModal(target) {
    let $card = $(target).parent("div");
    let keyword = $card.find(".keyword").val();
    let concept = $card.find(".concept").val();
    let cardId = $card.find(".cardId").val();
    $("#keyword").val(keyword);
    $("#concept").val(concept);
    $("#cardId").val(cardId);
    $("#btn-mod-modal").click();
}

api.init();