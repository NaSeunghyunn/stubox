window.addEventListener("keydown", function (e) {
    if ([38, 40].indexOf(e.keyCode) > -1) {
        e.preventDefault();
    }
}, false);

$(document).ready(function () {
    var timeout = null;
    $("#tag-input").keyup(function (e) {
        var key = e.keyCode;
        $(".tag-sel-div").show(200);

        if (key == 38) {
            var $selectedTag = $(".tag-selected");
            var $start = $(".tag-sel-item").first();
            $(".tag-sel-item").not($start).css("background", "#ffffff");
            $selectedTag.prev().css("background", "#cee6ff");
            $(".tag-sel-item").not($start).removeClass("tag-selected");
            $selectedTag.prev().addClass("tag-selected");

            let selectedIdx = $(".tag-sel-item").index($(".tag-selected"));
            if ($(".tag-sel-div").scrollTop() > 0) {
                if ((selectedIdx - 3) * 45 > $(".tag-sel-div").scrollTop() || selectedIdx * 45 < $(".tag-sel-div").scrollTop()) {
                    $(".tag-sel-div").scrollTop(selectedIdx * 45);
                }
            }
            return;
        }

        if (key == 40) {
            var $selectedTag = $(".tag-selected");
            var $last = $(".tag-sel-item").last();
            $(".tag-sel-item").not($last).css("background", "#ffffff");
            $selectedTag.next().css("background", "#cee6ff");
            $(".tag-sel-item").not($last).removeClass("tag-selected");
            $selectedTag.next().addClass("tag-selected");

            let selectedIdx = $(".tag-sel-item").index($(".tag-selected"));
            if (selectedIdx > 3) {
                if ((selectedIdx - 3) * 45 > $(".tag-sel-div").scrollTop()) {
                    $(".tag-sel-div").scrollTop((selectedIdx - 3) * 45);
                }
            }
            return;
        }

        if (key == 13) {
            $(".tag-selected").click();
        }
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            if (!$("#tag-input").val()) {
                clearSearchTag();
                return;
            }
            api.getTags();
        }, 500);
    });

    let tagSelFlg = false;
    $(".tag-sel-div").on("mouseenter", function () {
        tagSelFlg = true;
    });
    $(".tag-sel-div").on("mouseleave", function () {
        tagSelFlg = false;
    });

    $("#tag-input").focusout(function () {
        if (!tagSelFlg) {
            clearSearchTag();
        };
    });
});

let api = {
    getTags: function () {
        let url = "/tag";
        let params = {
            name: $("#tag-input").val()
        }
        commonMethod.fetchGet(url, params)
            .then(data => this.getTagsCallback(data))
            .then(() => initSelTag())
            .catch(err => false);
    },

    getTagsCallback: function (data) {

        $(".tag-sel-div").empty();
        $(data).each(function (index, item) {
            let tagDiv = document.createElement('div');
            let $tagDiv = $(tagDiv);
            $tagDiv.attr("class", "tag-sel-item");
            $tagDiv.text(item.name);

            let id = item.id == null ? "" : item.id;
            let tagId = document.createElement('input');
            let $tagId = $(tagId);
            $tagId.attr("type", "hidden");
            $tagId.attr("class", "tag-id2");
            $tagId.val(id);

            $tagDiv.append($tagId);
            $(".tag-sel-div").append($tagDiv);
        });


    },

    saveTag: function () {
        let tagId = $(".tag-selected").find(".tag-id2").val();
        if (tagId) {
            addTag();
            clearSearchTag();
            return;
        }

        let url = "/tag";
        let body = {
            name: $(".tag-selected").text()
        }

        commonMethod.fetchPost(url, body)
            .then(data => addTag(data))
            .then(() => clearSearchTag())
            .catch(err => false);
    },

    saveKnowledge: function () {
        let url = "/knowledge";
        let $tags = $(".tag-id");
        let tagIds = [];
        $tags.each(function (index, item) {
            tagIds.push($(item).val());
        });
        let body = {
            title: $("#title").val(),
            content: $("#contents").val(),
            previewURL: $(".note-editable").find("img").attr("src"),
            tagIds: tagIds
        }
        commonMethod.fetchPost(url, body)
            .then(() => location.href = "/knowledge")
            .catch(err => false);
    }
}

$('.summernote').summernote({
    height: 300,
    lang: "ko-KR",
    callbacks: {
        onImageUpload: function (files, editor, welEditable) {
            // 파일 업로드(다중업로드를 위해 반복문 사용)
            for (var i = files.length - 1; i >= 0; i--) {
                uploadSummernoteImageFile(files[i], this);
            }
        }
    }
});

function uploadSummernoteImageFile(file, el) {
    let formData = new FormData();
    formData.append("file", file);
    let url = "/knowledge/image";
    $(".loading").show();
    commonMethod.fetchFormData(url, "POST", formData)
        .then((data) => $(el).summernote('editor.insertImage', data.url))
        .catch(err => false)
        .finally(() => $(".loading").hide());
}

function removeTag(tag) {
    let $tagDiv = $(tag).parent(".tag");
    $tagDiv.remove();
}

function addTag(id) {
    let tagDiv = document.createElement('div');
    let $tagDiv = $(tagDiv);
    $tagDiv.attr("class", "tag m-1");

    let tagName = document.createElement('div');
    let $tagName = $(tagName);
    $tagName.attr("class", "tag-name");
    $tagName.text($(".tag-selected").text());

    let tagId = document.createElement('input');
    let $tagId = $(tagId);
    $tagId.attr("type", "hidden");
    $tagId.attr("class", "tag-id");
    let tagIdValue = id ? id : $(".tag-selected").find(".tag-id2").val();
    $tagId.val(tagIdValue);

    let button = document.createElement('button');
    let $button = $(button);
    $button.attr("onclick", "removeTag(this)");
    $button.text("×");

    $tagDiv.append($tagName).append($tagId).append($button);
    $(".tags").append($tagDiv);
}

function addTagFlag() {
    let flg = true;
    $(".tag-name").each(function (index, item) {
        if ($(".tag-selected").text() === $(item).text()) {
            flg = false;
            return false;
        }
    });

    return flg;
}

function clearSearchTag() {
    $("#tag-input").val("");
    $(".tag-sel-div").hide();
    $(".tag-sel-div").empty();
}

function initSelTag() {
    $(".tag-sel-item").first().addClass("tag-selected");
    $(".tag-sel-item").first().css("background", "#cee6ff");
    $(".tag-sel-item").hover(function () {
        $(".tag-sel-item").not(this).css("background", "#ffffff");
        $(".tag-sel-item").not(this).removeClass("tag-selected");
        $(this).css("background", "#cee6ff").addClass("tag-selected");
    });


    $(".tag-sel-item").click(function () {
        let addFlag = addTagFlag();

        if (addFlag) {
            api.saveTag();
        } else {
            clearSearchTag();
        }
    });
}