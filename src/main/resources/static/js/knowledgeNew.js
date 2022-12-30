$(document).ready(function(){
    var timeout = null;
    $("#tag-input").keyup(function(){
        clearTimeout(timeout);
        timeout = setTimeout(function () {
            if(!$("#tag-input").val()){
                        clearSearchTag();
                        return;
                    }
            api.getTags();
        }, 500);
    });

    $("#tag-sel").change(function(){
        let addFlag = addTagFlag();

        if(addFlag){
            addTag();
            api.saveTag();
        }

        clearSearchTag();
    });
});

let api = {
    getTags: function(){
        let url = "/tag";
        let params = {
            name: $("#tag-input").val()
        }
        commonMethod.fetchGet(url, params)
        .then(data => this.getTagsCallback(data))
        .catch(err => false);
    },

    getTagsCallback: function(data){
        let size = data.length < 5? data.length : 4 ;
        if(size == 1) size++;
        $("#tag-sel").attr("size", size);
        $("#tag-sel").empty();
        $("#tag-sel").show(300);
        $(data).each(function(index, item){
            let id = item.id == null ? "" : item.id;
            let option = "<option value='"+id+"'>"+item.name+"</option>"
            $("#tag-sel").append(option);
        });
    },

    saveTag: function(){
        let tagId = $("#tag-sel option:selected").val();
        if(tagId){
            return;
        }

        let url = "/tag";
        let body = {
            name: $("#tag-sel option:selected").text()
        }

        commonMethod.fetchPost(url, body)
        .catch(err => false);
    },

    saveKnowledge: function(){
        let url= "/knowledge";
        let $tags = $(".tag-id");
        let tagIds = [];
        $tags.each(function(index, item){
            tagIds.push($(item).val());
        });
        let body = {
            title: $("#title").val(),
            content: $("#contents").val(),
            previewURL: $(".note-editable").find("img").attr("src"),
            tagIds: tagIds
        }
        commonMethod.fetchPost(url, body)
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
        .finally(()=>$(".loading").hide());
}

function removeTag(tag){
    let $tagDiv = $(tag).parent(".tag");
    $tagDiv.remove();
}

function addTag(){
    let tagDiv = document.createElement('div');
            let $tagDiv = $(tagDiv);
            $tagDiv.attr("class","tag m-1");

            let tagName = document.createElement('div');
            let $tagName = $(tagName);
            $tagName.attr("class", "tag-name");
            $tagName.text($("#tag-sel option:selected").text());

            let tagId = document.createElement('input');
            let $tagId = $(tagId);
            $tagId.attr("type", "hidden");
            $tagId.attr("class", "tag-id");
            $tagId.val($("#tag-sel option:selected").val());

            let button = document.createElement('button');
            let $button = $(button);
            $button.attr("onclick", "removeTag(this)");
            $button.text("×");

            $tagDiv.append($tagName).append($tagId).append($button);
            $(".tags").append($tagDiv);
}

function addTagFlag(){
    let flg = true;
    $(".tag-name").each(function(index, item){
                if($("#tag-sel option:selected").text() === $(item).text()){
                flg = false;
                    return false;
                }
            });
            return flg;
}

function clearSearchTag(){
        $("#tag-input").val("");
        $("#tag-sel").hide();
        $("#tag-sel").empty();
}