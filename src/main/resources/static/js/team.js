let api = {
    save: function () {
        let url = "/team";
        let body = {
            name: $("#name").val(),
            categories: categories()
        }
        commonMethod.fetchPost(url, body)
            .then(() => location.href = "/group")
            .catch(err => false);
    }
}

function categories() {
    let category1 = blankToNull($("#category1 option:selected").val());
    let category2 = blankToNull($("#category2 option:selected").val());
    let category3 = blankToNull($("#category3 option:selected").val());
    return [category1, category2, category3];
}

function blankToNull(str){
    if(str == "") return null;
    return str;
}