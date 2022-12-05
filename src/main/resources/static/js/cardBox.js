let api = {
    search: function () {
        let url = "/box";
        commonMethod.fetchGet(url).then(data => this.searchCallback(data)).catch(err => false);
    },

    searchMyBox: function () {
        let url = "/box/myBox";
        commonMethod.fetchGet(url).then(data => this.searchCallback(data)).catch(err => false);
    },

    createBox: function () {
        let url = "/box";
        let body = {
            name: $("#boxName").val()
        };
        commonMethod.fetchPost(url, body).then(data => addItem(data)).catch(err => false);
    },

    select: function (id) {
        let url = "/box/" + id;
        commonMethod.fetch(url, "PUT", null).catch(err => false);
    },

    searchCallback: function (data) {
        if (data == null) return;

        let $contents = $("#contents");
        $contents.empty();
        let selectBoxId = data.selectBoxId;
        $(data).each(function (index, item) {
            addItem(item);
        });
    },

    remove: function (id) {
        let url = "/box/" + id;
        commonMethod.fetch(url, "DELETE")
            .then(() => window.location.reload())
            .catch(err => false);
    }
}

function addItem(data) {
    // div블럭 생성
    let div = "<div class='mt-2'></div>";
    let $div = $(div);

    // 버튼그룹 생성
    let btnGroup = "<div class='btn-group'></div>";
    let $btnGroup = $(btnGroup);
    //테스트영역
    let testArea = "<div class='test-area'></div>";
    let $testArea = $(testArea);
    // 박스영역
    let boxArea = "<div class='box-area'></div>";
    let $boxArea = $(boxArea);
    // 작성자 표시용 div블럭
    let divAuthor = "<div></div>";
    let $divAuthor = $(divAuthor);
    $divAuthor.css("text-align", "left");
    // 작성자 프로필사진
    let imgProfile = document.createElement('img');
    let $imgProfile = $(imgProfile);
    $imgProfile.attr("class", "img-profile")
    $imgProfile.attr("src", data.profile);
    // 작성자 이름
    let author = document.createElement('span');
    $author = $(author);
    $author.text(data.author);
    $divAuthor.append($imgProfile).append(author);

    // 박스이름영역 생성
    let divBoxName = "<div style='float: left;'></div>";
    let $divBoxName = $(divBoxName);
    // 이름버튼 생성
    let nameBtn = "<a href='/card/init/" + data.id + "' role='button' class='box-title'>" + data.name + "</a>";
    $divBoxName.append(nameBtn);
    // 박스영역 조합
    $boxArea.append($divAuthor).append($divBoxName);
    // 테스트버튼 생성
    let testBtn = "<button type='button' class='sel-btn' onclick='selectBox(" + data.id + ")'><img src='/img/test_icon.png'></btn>";
    // 삭제버튼 생성
    let delBtn = "<button type='button' class='del-btn' onclick='api.remove(" + data.id + ")'><img src='/img/delete_icon.png'></btn>";

    let btnArea = "<div></div>";
    let $btnArea = $(btnArea);
    $btnArea.css("align-self", "center");
    $btnArea.append(testBtn).append(delBtn);

    //테스트영역 조합
    $testArea.append($boxArea).append($btnArea);
    // contents
    $btnGroup.append($testArea);
    $div.append($btnGroup);
    $("#contents").append($div);
}

function selectBox(id) {
    location.href = "/test/" + id;
}

api.search();

