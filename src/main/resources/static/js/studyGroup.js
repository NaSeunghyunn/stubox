let api = {
    search: function () {
        let url = "/posts";

        let params = {
            postId: $("#lastPostId").val(),
            category: $("#category option:selected").val(),
            searchContents: $("#searchContents").val()
        }
        commonMethod.fetchGet(url, params)
            .then(data => drawPosts(data))
            .then(data => moreBtn(data))
            .catch(err => false);
    }
}

function doSearch() {
    $("#contents ul").empty();
    $("#lastPostId").val("");
    api.search();
}

function drawPosts(data) {
    $(data.posts).each(function (index, item) {
        let row = document.createElement('li');
        let $row = $(row);

        let divGroupRow = document.createElement('div');
        let $divGroupRow = $(divGroupRow);
        $divGroupRow.attr("class", "group-row d-flex justify-content-between");

        let divContent = document.createElement('div');
        let $divContent = $(divContent);
        $divContent.attr("id", "content-div");

        let divTeam = document.createElement('div');
        let $divTeam = $(divTeam);
        $divTeam.css("float", "left");

        let spanTeamName = document.createElement('span');
        let $spanTeamName = $(spanTeamName);
        $spanTeamName.css("font-size", "0.7rem");
        $spanTeamName.text(item.teamName);

        let spanCategories = document.createElement('span');
        let $spanCategories = $(spanCategories);
        $spanCategories.css("font-size", "0.7rem");
        $spanCategories.css("padding", "0.5rem")
        $spanCategories.text(item.categories.join("\r\n"));

        $divTeam.append($spanTeamName).append($spanCategories);

        let divPost = document.createElement('div');
        let $divPost = $(divPost);
        $divPost.css("text-align", "left");

        let spanTitle = document.createElement('span');
        let $spanTitle = $(spanTitle);
        $spanTitle.css("font-size", "1.7rem");
        let title = item.title.length >13 ? item.title.slice(0,13)+"...":item.title
        $spanTitle.text(title);

        $divPost.append($spanTitle);
        $divContent.append($divTeam).append($divPost);

        let divBtn = document.createElement('div');
        let $divBtn = $(divBtn);
        $divBtn.attr("class", "req-btn-div");

        let reqBtn = document.createElement('input');
        let $reqBtn = $(reqBtn);
        $reqBtn.attr("type", "button");
        $reqBtn.attr("class", "btn btn-outline-success");
        $reqBtn.attr("id", "req-btn");
        $reqBtn.val("신청");

        $divBtn.append($reqBtn);
        $divGroupRow.append($divContent).append($divBtn);
        $row.append($divGroupRow);
        $("#contents ul").append($row);
        $("#lastPostId").val(item.postId);
    });
    return data;
}

function moreBtn(data) {
    if (data.more) {
        $("#moreBtn").show();
    } else {
        $("#moreBtn").hide();
    }
}

doSearch();