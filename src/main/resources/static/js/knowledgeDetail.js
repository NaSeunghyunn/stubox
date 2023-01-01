$(document).ready(function () {
    api.findPost();
    api.findPostLike();
    api.findPostTags();
    api.findComments();
});

let api = {
    findPost: function () {
        let url = "/knowledge/" + $("#postId").val();
        commonMethod.fetchGet(url)
            .then(data => callback.findPost(data))
            .catch(err => false);
    },

    findPostLike: function () {
        let url = "/like/" + $("#postId").val();
        commonMethod.fetchGet(url)
            .then(data => callback.findPostLike(data))
            .catch(err => false);
    },

    doLike: function () {
        let url = "/like";
        let body = {
            postId: $("#postId").val()
        }
        commonMethod.fetchPost(url, body)
            .then(data => callback.doLike(data))
            .catch(err => false);
    },

    cancelLike: function () {
        let url = "/like";
        let body = {
            postId: $("#postId").val()
        };
        commonMethod.fetch(url, "DELETE", body)
            .then(data => callback.cancelLike(data))
            .catch(err => false);
    },

    findPostTags: function () {
        let url = "/postTag/" + $("#postId").val();
        commonMethod.fetchGet(url)
            .then(data => callback.findPostTags(data))
            .catch(err => false);
    },

    saveComment: function () {
        let url = "/comment";
        let body = {
            postId: $("#postId").val(),
            content: $("#comment").val()
        }
        commonMethod.fetchPost(url, body)
            .then(() => $("#comment").val(""))
            .then(() => this.findComments())
            .catch(err => false);
    },

    findComments: function () {
        let url = "/comment/" + $("#postId").val();
        commonMethod.fetchGet(url)
            .then(data => callback.findComments(data))
            .catch(err => false);
    },

    saveCommentChild: function () {
        let url = "/comment/child";
        console.log($("#commentId").val());
        let body = {
            id: $("#commentId").val(),
            content: $("#comment2").val(),
            receiverName: $("#receiverName").val()
        };
        commonMethod.fetchPost(url, body)
            .then(() => $("#comment2").val(""))
            .then(() => this.findComments())
            .catch(err => false);
    }

};

let callback = {
    findPost: function (data) {
        $("#title").text(data.title);
        $("#writer-profile").attr("src", data.writer.profile);
        $("#writer-name").text(data.writer.name);
        $("#updateAt").text(data.updateAt);
        $("#view-count").text(data.viewCount);
        $(".content-area").append(data.content);
    },

    findPostLike: function (data) {
        if (data.receiveMyLike) {
            $("#like-ok").show();
            $("#like-no").hide();
        } else {
            $("#like-no").show();
            $("#like-ok").hide();
        }
        $("#like-count").text(data.likeCount);
    },

    doLike: function () {
        $("#like-ok").show();
        $("#like-no").hide();
        let likeCount = Number($("#like-count").text()) + 1;
        $("#like-count").text(likeCount);
    },

    cancelLike: function () {
        $("#like-no").show();
        $("#like-ok").hide();
        let likeCount = Number($("#like-count").text()) - 1;
        $("#like-count").text(likeCount);
    },

    findPostTags: function (data) {
        $(data).each(function (index, item) {
            let tagDiv = document.createElement('div');
            let $tagDiv = $(tagDiv);
            $tagDiv.attr("class", "tag");

            let tag = document.createElement('a');
            let $tag = $(tag);
            $tag.attr("href", "#");
            $tag.text("#" + item.name);

            $tagDiv.append($tag);
            $(".tag-area").append($tagDiv);
        });
    },

    findComments: function (data) {
        $(".comment-area").empty();
        $(data).each(function (index, item) {
            $(".comment-area").append(genComment(item));
        });

        $(".comment-child-input").click(function (e) {
            $("#commentChildInput").remove();
            $(".comment-child-input").not(this).attr("aria-expanded", "false");
            var menuItem = $(e.currentTarget);
            if (menuItem.attr("aria-expanded") === "true") {
                $(this).attr("aria-expanded", "false");
            } else {
                $(this).attr("aria-expanded", "true");
                let commentWrap = $(this).closest(".comment-wrap");
                let id = commentWrap.find(".comment-id").val();
                let receiverName = commentWrap.find(".receiver-name").text();
                commentWrap.append(genCommentInput(id, receiverName));
            }
        });
    }
}

function genCommentInput(id, receiverName) {
    let collapse = document.createElement('div');
    let $collapse = $(collapse);
    $collapse.attr("id", "commentChildInput");
    $collapse.css("padding", "0 60px");

    let commentId = document.createElement('input');
    let $commentId = $(commentId);
    $commentId.attr("type", "hidden");
    $commentId.attr("id", "commentId");
    $commentId.val(id);

    let receiverNameTag = document.createElement('input');
    let $receiverName = $(receiverNameTag);
    $receiverName.attr("type", "hidden");
    $receiverName.attr("id", "receiverName");
    $receiverName.val(receiverName);

    let $commentInput = $(".comment-input-area").clone();
    $commentInput.append($commentId).append($receiverName);
    $commentInput.find("textarea").attr("id", "comment2");
    $commentInput.find("textarea").val("");
    $commentInput.find("button").attr("onclick", "api.saveCommentChild()");

    $collapse.append($commentInput);
    return $collapse;
}

function genComment(data) {
    let row = document.createElement('div');
    let $row = $(row);
    $row.attr("class", "bottom-line");

    let commentWrap = document.createElement('div');
    let $commentWrap = $(commentWrap);
    $commentWrap.attr("class", "comment-wrap");

    let comment = document.createElement('div');
    let $comment = $(comment);
    $comment.attr("class", "comment d-flex p-3");
    $comment.css("white-space", "pre-line");

    let profileDiv = document.createElement('div');
    let $profileDiv = $(profileDiv);

    let profile = document.createElement('img');
    let $profile = $(profile);
    $profile.attr("class", "profile-img mx-2");
    $profile.attr("src", data.writer.profile);

    let contentDiv = document.createElement('div');
    let $contentDiv = $(contentDiv);

    let nickName = document.createElement('div');
    let $nickName = $(nickName);
    $nickName.css("font-weight", "bold");
    $nickName.attr("class", "receiver-name");
    $nickName.text(data.writer.name);

    let id = document.createElement('input');
    let $id = $(id);
    $id.attr("type", "hidden");
    $id.attr("class", "comment-id");
    $id.val(data.id);

    let content = document.createElement('div');
    let $content = $(content);
    $content.text(data.content);



    let contentInputDiv = document.createElement('div');
    let $contentInputDiv = $(contentInputDiv);
    $contentInputDiv.attr("class", "comment-child-input-container");

    let contentInput = document.createElement('a');
    let $contentInput = $(contentInput);
    $contentInput.attr("class", "comment-child-input text-muted");
    $contentInput.attr("aria-expanded", "false");
    $contentInput.text("답글쓰기");

    $contentInputDiv.append($contentInput);

    $contentDiv.append($nickName).append($id).append($content).append($contentInputDiv)
    $profileDiv.append($profile);
    $comment.append($profileDiv).append($contentDiv);
    $commentWrap.append($comment);
    $row.append($commentWrap);

    $(data.children).each(function (index, item) {
        $row.append(genCommentChild(item, data.id));
    });

    return $row;
}

function genCommentChild(data, commentId) {
    let commentWrap = document.createElement('div');
    let $commentWrap = $(commentWrap);
    $commentWrap.attr("class", "comment-wrap");

    let comment = document.createElement('div');
    let $comment = $(comment);
    $comment.attr("class", "comment-child d-flex");


    let profileDiv = document.createElement('div');
    let $profileDiv = $(profileDiv);

    let profile = document.createElement('img');
    let $profile = $(profile);
    $profile.attr("class", "profile-img mx-2");
    $profile.attr("src", data.writer.profile);

    let contentDiv = document.createElement('div');
    let $contentDiv = $(contentDiv);

    let nickName = document.createElement('div');
    let $nickName = $(nickName);
    $nickName.css("font-weight", "bold");
    $nickName.attr("class", "receiver-name")
    $nickName.text(data.writer.name);

    let id = document.createElement('input');
    let $id = $(id);
    $id.attr("type", "hidden");
    $id.attr("class", "comment-id");
    $id.val(commentId);

    let contentWrap = document.createElement('div');
    let $contentWrap = $(contentWrap);
    $contentWrap.attr("class", "d-flex");

    let receiver = document.createElement('span');
    let $receiver = $(receiver);
    $receiver.text(data.receiver);
    $receiver.attr("class", "receiver");

    let content = document.createElement('div');
    let $content = $(content);
    $content.text(data.content);

    let contentInputDiv = document.createElement('div');
    let $contentInputDiv = $(contentInputDiv);
    $contentInputDiv.attr("class", "comment-child-input-container");

    let contentInput = document.createElement('a');
    let $contentInput = $(contentInput);
    $contentInput.attr("class", "comment-child-input text-muted");
    $contentInput.attr("aria-expanded", "false");
    $contentInput.text("답글쓰기");

    $contentInputDiv.append($contentInput);
    $contentWrap.append($receiver).append($content);
    $contentDiv.append($nickName).append($id).append($contentWrap).append($contentInputDiv)
    $profileDiv.append($profile);
    $comment.append($profileDiv).append($contentDiv);
    $commentWrap.append($comment);

    return $commentWrap;
}
