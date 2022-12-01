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
        commonMethod.fetchPost(url, body).then(data => addItem(data.id, data.name)).catch(err => false);
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
        $(data.boxes).each(function (index, item) {
            addItem(item.id, item.name, selectBoxId);
        });
    }
}

function addItem(id, name, selectBoxId) {
    // div블럭 생성
    let div = "<div class='mt-2'></div>";
    let $div = $(div);

    // 버튼그룹 생성
    let btnGroup = "<div class='btn-group' style='width: 38.5rem'></div>";
    let $btnGroup = $(btnGroup);
    // 이름버튼 생성
    let nameBtn = "<a href='/card/init/" + id + "' role='button' class='btn btn-outline-success box' style='width: 90%;'>" + name + "</a>";
    if (id === selectBoxId) {
            nameBtn = "<a href='/card/init/" + id + "' role='button' class='btn btn-success box' style='width: 90%;'>" + name + "</a>";

        }
    // 선택버튼 생성
    let selectBtn = "<input type='button' onclick='selectBox(this," + id + ")' class='btn btn-outline-success' value='선택'>";

    // contents
    let $contents = $("#contents");
    $btnGroup.append(nameBtn).append(selectBtn);
    $div.append($btnGroup);
    $contents.append($div);
}

function selectBox(target, id) {
    $(".box").attr("class", "btn btn-outline-success box");
    $box = $(target).prev(".box");
    $box.attr("class", "btn btn-success box");
    api.select(id);
}

api.search();

