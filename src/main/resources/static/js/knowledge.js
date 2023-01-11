$(document).ready(function () {
    $("#searchType-mobile").change(function () {
        $("#searchType").val($(this).val());
    });

    api.getKnowledge();
});

let api = {
    getKnowledge: function () {
        let url = "/knowledge";
        let params = {
            searchType: $("#searchType option:selected").val(),
            search: $("#search").val(),
            page: $("#page").val(),
            sort: $("#sort").val(),
            size: $("#size").val(),
            totalCount: $("#totalCount").val()
        };
        commonMethod.fetchGet(url, params)
            .then(data => callback.getKnowledge(data))
            .then(() => this.getTotalCount())
            .catch(err => false);
    },

    getTotalCount: function () {
        let url = "/knowledge/count";
        let params = {
            searchType: $("#searchType option:selected").val(),
            search: $("#search").val(),
            page: $("#page").val(),
            sort: $("#sort").val(),
            size: $("#size").val(),
            totalCount: $("#totalCount").val()
        };

        commonMethod.fetchGet(url, params)
            .then(data => callback.getTotalCount(data))
            .catch(err => false);
    }
}

let callback = {
    getKnowledge: function (data) {
        $("#posts").empty();
        $(data.content).each(function (index, item) {
            $("#posts").append(genArticle(item));
        });
        setPagination(data);
    },

    getTotalCount: function (data) {
        $("#totalCount").val(data.totalElements);
        setPagination(data);
    }
}

function genArticle(data) {
    let article = document.createElement('article');
    let $article = $(article);
    $article.attr("class", "bottom-line");

    let a = document.createElement('a');
    let $a = $(a);
    $a.attr("href", "/knowledge/" + data.id);
    $a.attr("class", "post-link");

    let rowDiv = document.createElement('div');
    let $rowDiv = $(rowDiv);
    $rowDiv.attr("class", "css-post");

    let preview = document.createElement('img');
    let $preview = $(preview);
    $preview.attr("class", "css-preview");
    $preview.attr("src", data.preView);
    if(!data.preView){
        $preview.addClass("no-image");
    }

    let contentDiv = document.createElement('div');
    let $contentDiv = $(contentDiv);
    $contentDiv.attr("class", "pull-width");

    let writerDiv = document.createElement('div');
    let $writerDiv = $(writerDiv);

    let profile = document.createElement('img');
    let $profile = $(profile);
    $profile.attr("class", "css-profile");
    $profile.attr("src", data.writer.profile);

    let writerName = document.createElement('span');
    let $writerName = $(writerName);
    $writerName.attr("class", "css-writer bold me-2");
    $writerName.text(data.writer.name);

    let updateAt = document.createElement('span');
    let $updateAt = $(updateAt);
    $updateAt.attr("class", "css-writer");
    $updateAt.attr(data.updateAt);

    let title = document.createElement('p');
    let $title = $(title);
    $title.attr("class", "css-title");
    $title.text(data.title);

    let footDiv = document.createElement('div');
    let $footDiv = $(footDiv);

    let tags = document.createElement('div');
    let $tags = $(tags);
    $tags.attr("class", "tags d-inline-block");

    $(data.tags).each(function (index, item) {
        let tag = document.createElement('span');
        let $tag = $(tag);
        $tag.attr("class", "tag");
        $tag.text("#" + item);

        $tags.append($tag);
    });

    let postFoot = document.createElement('div');
    let $postFoot = $(postFoot);
    $postFoot.attr("class", "post-foot d-inline-block css-right");

    let viewImg = document.createElement('img');
    let $viewImg = $(viewImg);
    $viewImg.attr("class", "post-foot-img");
    $viewImg.attr("src", "https://stubox-bucket.s3.ap-northeast-1.amazonaws.com/viewCount.png");

    let viewSpan = document.createElement('span');
    let $viewSpan = $(viewSpan);
    $viewSpan.attr("class", "post-foot-span");
    $viewSpan.text(data.viewCount);

    let commentImg = document.createElement('img');
    let $commentImg = $(commentImg);
    $commentImg.attr("class", "post-foot-img");
    $commentImg.attr("src", "https://stubox-bucket.s3.ap-northeast-1.amazonaws.com/comment.png");

    let commentSpan = document.createElement('span');
    let $commentSpan = $(commentSpan);
    $commentSpan.attr("class", "post-foot-span");
    $commentSpan.text(data.commentCount);

    let likeImg = document.createElement('img');
    let $likeImg = $(likeImg);
    $likeImg.attr("class", "post-foot-img");
    $likeImg.attr("src", "https://stubox-bucket.s3.ap-northeast-1.amazonaws.com/like2.png");

    let likeSpan = document.createElement('span');
    let $likeSpan = $(likeSpan);
    $likeSpan.attr("class", "post-foot-span");
    $likeSpan.text(data.likeCount);

    $postFoot.append($viewImg).append($viewSpan).append($commentImg).append($commentSpan).append($likeImg).append($likeSpan);
    $footDiv.append($tags).append($postFoot);
    $writerDiv.append($profile).append($writerName).append($updateAt);
    $contentDiv.append($writerDiv).append($title).append($footDiv);
    $rowDiv.append($preview).append($contentDiv);
    $a.append($rowDiv);
    $article.append($a);

    return $article;
}

function setPagination(data) {
    $(".pagination").empty();

    let totalPage = data.totalPages;
    let page = $("#page").val();
    let pageNumber = parseInt(page / 10);

    if (pageNumber > 0) {
        $(".pagination").append(genPageLink(pageNumber * 10 - 1, "이전"));
    }

    for (let i = pageNumber * 10 + 1; i <= Math.min(pageNumber * 10 + 10, totalPage); i++) {
        let $pageLink = genPageLink(i - 1, i);
        if (i - 1 == page) {
            $pageLink.attr("class", "page-item active");
            $pageLink.find("a").removeAttr("href");
            $pageLink.find("a").css("cursor", "default");
        }
        $(".pagination").append($pageLink);
    }

    if (pageNumber * 10 + 10 < totalPage) {
        $(".pagination").append(genPageLink(pageNumber * 10 + 10, "다음"));
    }
}

function genPageLink(page, name) {
    let previous = document.createElement('li');
    let $previous = $(previous);
    $previous.attr("class", "page-item");

    let previousLink = document.createElement('a');
    let $previousLink = $(previousLink);
    $previousLink.attr("class", "page-link");
    $previousLink.attr("href", getUrl(page));
    $previousLink.text(name);

    $previous.append($previousLink);
    return $previous;
}

function getUrl(page) {
    let pageVal = page == null ? $("#page").val() : page
    let params = {
        searchType: $("#searchType option:selected").val(),
        search: $("#search").val(),
        page: pageVal,
        sort: $("#sort").val(),
        totalCount: $("#totalCount").val()
    };
    let query = Object.keys(params)
        .filter(k => params[k] != null)
        .map(k => encodeURIComponent(k) + '=' + encodeURIComponent(params[k]))
        .join('&');
    return "/knowledge?" + query;
}

function sortLatest() {
    $("#sort").val("LATEST");
    $("#sortType").text("최신순");
    $(".css-sort").click();
    $("#totalCount").val("0");
    location.href = getUrl(0);
}

function sortRecommend() {
    $("#sort").val("RECOMMEND");
    $("#sortType").text("추천순");
    $(".css-sort").click();
    $("#totalCount").val("0");
    location.href = getUrl(0);
}

function sortComment() {
    $("#sort").val("COMMENT");
    $("#sortType").text("댓글순");
    $(".css-sort").click();
    $("#totalCount").val("0");
    location.href = getUrl(0);
}

function sortView() {
    $("#sort").val("VIEW");
    $("#sortType").text("조회순");
    $(".css-sort").click();
    $("#totalCount").val("0");
    location.href = getUrl(0);
}

function searchMobile() {
    $("#search").val($("#search-mobile").val());
    $("#totalCount").val("0");
    location.href = getUrl(0);
}

function searchPost() {
    $("#totalCount").val("0");
    location.href = getUrl(0);
}