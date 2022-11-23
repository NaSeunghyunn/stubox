let testData;

let api = {
    init: function () {
        let url = "/test/" + $("#level").val();
        commonMethod.fetchGet(url)
            .then(data => testData = data)
            .then(() => selectCard())
            .catch(err => false);
    },

    save: function (body) {
        let url = "/test";
        commonMethod.fetchPost(url, body)
            .then(() => selectCard())
            .catch(err => false);
    },

    update: function (body) {
        let url = "/test";
        commonMethod.fetch(url, "PUT", body)
            .then(() => selectCard())
            .catch(err => false);
    }
}

function test(answer) {
    let id = $("#id").val();
    if (id == null || id == "") {
        let body = {
            cardId: $("#cardId").val(),
            testResult: answer
        }
        api.save(body);
    } else {
        let body = {
            id: id,
            testResult: answer
        }
        api.update(body);
    }
}

function showConcept() {
    $("#result").show(300);
}

function selectCard() {
    if (testData.length == 0) {
        $("#result").remove();
        $("#keyword").remove();
        $("#message").text("더이상 테스트 할 카드가 없습니다.")
    }

    $("#result").hide(300);
    const random = Math.floor(Math.random() * testData.length);
    const card = testData.splice(random, 1);
    $("#id").val(card[0].id);
    $("#cardId").val(card[0].cardId);
    $("#keyword").val(card[0].keyword);
    $("#concept").text(card[0].concept);
}

api.init();