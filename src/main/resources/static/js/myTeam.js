let api = {
    init: function () {
        let url = "/team/my";
        commonMethod.fetchGet(url)
            .then(data => setTeamInfo(data))
            .catch(err => false);
    },

    authorize: function(id){
        let url = "/team/auth";
        let body = {
            memberId: id
        }
        commonMethod.fetch(url, "PUT", body)
                    .then(() => window.location.reload())
                    .catch(err => false);
    },

    expel: function (id) {
        let url = "/team/expel";
        let body = {
            memberId: id
        }
        commonMethod.fetch(url, "PUT", body)
            .then(() => window.location.reload())
            .catch(err => false);
    },

    withdrawal: function(){
        let url = "/team/withdrawal";
        commonMethod.fetch(url, "PUT")
                    .then(() => location.href = "/group")
                    .catch(err => false);
    },

    update: function(){
        let url = "/team/my";
        let body = {
            categories : categories()
        };
        commonMethod.fetch(url, "PUT", body)
                    .then(() => window.location.reload())
                    .catch(err => false);
    },

    remove: function(){
        let url = "/team";
        commonMethod.fetch(url, "DELETE")
                            .then(() => location.href = "/group")
                            .catch(err => false);
    }
}

function setTeamInfo(data) {
    $("#name").val(data.name);
    $(data.categories).each(function (index, item) {
        $("#category" + (index + 1)).val(item).prop("selected", true);
    });
    setTeamManagers(data.teamManagers);
    setTeamMembers(data.teamMembers);
}

function setTeamManagers(data) {
    $(data).each(function (index, item) {
        let div = document.createElement('div');
        let $div = $(div);

        let spanName = document.createElement('span');
        let $spanName = $(spanName);
        $spanName.text(item.name);
        $div.append($spanName);
        $("#teamManagers").append($div);
    });
}

function setTeamMembers(data) {
    $(data).each(function (index, item) {
        let div = document.createElement('div');
        let $div = $(div);

        let spanName = document.createElement('span');
        let $spanName = $(spanName);
        $spanName.text(item.name);
        $div.append($spanName);

        if ($("#mode").val() == "manage") {
            let aBtn = document.createElement('input');
                        let $aBtn = $(aBtn);
                        $aBtn.attr("type", "button");
                        $aBtn.attr("onclick", "api.authorize(" + item.id + ")")
                        $aBtn.val("임원");

            let eBtn = document.createElement('input');
            let $eBtn = $(eBtn);
            $eBtn.attr("type", "button");
            $eBtn.attr("onclick", "api.expel(" + item.id + ")")
            $eBtn.val("추방");

            $div.append($aBtn).append($eBtn);
        }

        $("#teamMembers").append($div);
    });
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
api.init();