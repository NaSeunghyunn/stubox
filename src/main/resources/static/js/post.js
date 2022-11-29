let api = {
    save: function () {
        let url = "/posts";
        let body = {
            title: $("#title").val(),
            content: $("#content").val()
        }
        commonMethod.fetchPost(url, body)
            .then(() => location.href = "/group")
            .catch(err => false);
    }
}