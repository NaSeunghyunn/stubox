let api = {
    init: function () {
        let url = "/posts/" + $("#id").val();
        commonMethod.fetchGet(url)
            .then((data) => drawPost(data))
            .catch(err => false);
    },

    req: function () {
        let url = "/team/req";
        let body = {
            teamName: $("#teamName").val()
        };

        commonMethod.fetchPost(url, body)
            .then(() => location.href = "/group")
            .catch(err => false);
    }
}

function drawPost(data) {
    $("#title").val(data.title);
    $("#content").val(data.content);
}

api.init();