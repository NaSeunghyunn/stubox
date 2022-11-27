let api ={
    search: function(){
        let url="/posts";

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

function doSearch(){
    $("#container ul").empty();
    $("#lastPostId").val("");
    api.search();
}

function drawPosts(data){
    $(data.posts).each(function(index, item){
        let row = document.createElement('li');
        let $row = $(row);

        let divTeam = document.createElement('div');
        let $divTeam = $(divTeam);

        let spanTeamName = document.createElement('span');
        let $spanTeamName = $(spanTeamName);
        $spanTeamName.css("font-size","0.7rem");
        $spanTeamName.text(item.teamName);

        let spanCategories = document.createElement('span');
        let $spanCategories = $(spanCategories);
        $spanCategories.css("font-size","0.7rem");
        $spanCategories.css("padding", "0.5rem")
        $spanCategories.text(item.categories.join("\r\n"));

        $divTeam.append($spanTeamName).append($spanCategories);

        let divPost = document.createElement('div');
        let $divPost = $(divPost);

        let spanTitle = document.createElement('span');
        let $spanTitle = $(spanTitle);
        $spanTitle.css("font-size","1.7rem");
        $spanTitle.text(item.title);

        let signUpBtn = document.createElement('input');
        let $signUpBtn = $(signUpBtn);
        $signUpBtn.attr("type","button");
        $signUpBtn.val("신청");

        $divPost.append($spanTitle).append($signUpBtn);

        $row.append($divTeam).append($divPost);
        $("#container ul").append($row);
        $("#lastPostId").val(item.postId);
    });
    return data;
}

function moreBtn(data){
    if(data.more){
            $("#moreBtn").show();
        } else{
        $("#moreBtn").hide();
        }
}

doSearch();