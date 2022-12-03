let api = {
    init: function () {
        let url = "/team/my";
        commonMethod.fetchGet(url)
            .then(data => setTeamInfo(data))
            .catch(err => false);
    },

    authorize: function (id) {
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

    approval: function (id) {
        let url = "/team/approval";
        let body = {
            memberId: id
        }
        commonMethod.fetch(url, "PUT", body)
            .then(() => window.location.reload())
            .catch(err => false);
    },

    refuse: function (id) {
        let url = "/team/refuse";
        let body = {
            memberId: id
        }
        commonMethod.fetch(url, "PUT", body)
            .then(() => window.location.reload())
            .catch(err => false);
    },

    withdrawal: function () {
        let url = "/team/withdrawal";
        commonMethod.fetch(url, "PUT")
            .then(() => location.href = "/group")
            .catch(err => false);
    },

    update: function () {
        let url = "/team/my";
        let body = {
            categories: categories()
        };
        commonMethod.fetch(url, "PUT", body)
            .then(() => window.location.reload())
            .catch(err => false);
    },

    remove: function () {
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
    if ($("#mode").val() == "manage") {
        setApplicants(data.applicants);
    }
}

function setTeamManagers(data) {
    $(data).each(function (index, item) {
        let div = document.createElement('div');
        let $div = $(div);
        $div.attr("class", "grey-bottom-line member-profile");

        let imgProfile = document.createElement('img');
        let $imgProfile = $(imgProfile);
        $imgProfile.attr("src",item.profile);

        let spanName = document.createElement('span');
        let $spanName = $(spanName);
        $spanName.text(item.name);

        let div2 = document.createElement('div');
        let $div2 = $(div2);

        $div2.append($imgProfile).append($spanName);
        $div.append($div2);
        $("#teamManagers").append($div);
    });
}

function setTeamMembers(data) {
    $(data).each(function (index, item) {
        let div = document.createElement('div');
        let $div = $(div);
        $div.attr("class", "grey-bottom-line member-profile");

let imgProfile = document.createElement('img');
        let $imgProfile = $(imgProfile);
        $imgProfile.attr("src",item.profile);

        let spanName = document.createElement('span');
        let $spanName = $(spanName);
        $spanName.text(item.name);

        let div2 = document.createElement('div');
                let $div2 = $(div2);

                $div2.append($imgProfile).append($spanName);
                $div.append($div2);

        if ($("#mode").val() == "manage") {
            let divBtn = document.createElement('div');
            let $divBtn = $(divBtn);

            let aBtn = document.createElement('input');
            let $aBtn = $(aBtn);
            $aBtn.attr("type", "button");
            $aBtn.attr("class", "btn btn-outline-secondary mx-1");
            $aBtn.attr("onclick", "api.authorize(" + item.id + ")")
            $aBtn.val("임원");

            let eBtn = document.createElement('input');
            let $eBtn = $(eBtn);
            $eBtn.attr("type", "button");
            $eBtn.attr("class", "btn btn-outline-secondary mx-1");
            $eBtn.attr("onclick", "api.expel(" + item.id + ")")
            $eBtn.val("추방");

            $divBtn.append($aBtn).append($eBtn);
            $div.append($divBtn)
        }

        $("#teamMembers").append($div);
    });
}

function setApplicants(data) {
    $(data).each(function (index, item) {
        let div = document.createElement('div');
        let $div = $(div);
        $div.attr("class", "grey-bottom-line member-profile");

let imgProfile = document.createElement('img');
        let $imgProfile = $(imgProfile);
        $imgProfile.attr("src",item.profile);

        let spanName = document.createElement('span');
        let $spanName = $(spanName);
        $spanName.text(item.name);

        let div2 = document.createElement('div');
                let $div2 = $(div2);

                $div2.append($imgProfile).append($spanName);
                $div.append($div2);

        let divBtn = document.createElement('div');
        let $divBtn = $(divBtn);

        let aBtn = document.createElement('input');
        let $aBtn = $(aBtn);
        $aBtn.attr("type", "button");
        $aBtn.attr("class", "btn btn-outline-secondary mx-1");
        $aBtn.attr("onclick", "api.approval(" + item.id + ")")
        $aBtn.val("승인");

        let eBtn = document.createElement('input');
        let $eBtn = $(eBtn);
        $eBtn.attr("type", "button");
        $eBtn.attr("class", "btn btn-outline-secondary mx-1");
        $eBtn.attr("onclick", "api.refuse(" + item.id + ")")
        $eBtn.val("거절");

        $divBtn.append($aBtn).append($eBtn);
        $div.append($divBtn)

        $("#applicants").append($div);
    });
}

function categories() {
    let category1 = blankToNull($("#category1 option:selected").val());
    let category2 = blankToNull($("#category2 option:selected").val());
    let category3 = blankToNull($("#category3 option:selected").val());
    return [category1, category2, category3];
}

function blankToNull(str) {
    if (str == "") return null;
    return str;
}
api.init();