let api = {
    search: function(){
        let url = "/box";
        commonMethod.fetchGet(url).then(data => this.searchCallback(data)).catch(err => false);
    },

    searchMyBox: function(){
            let url = "/box/myBox";
            commonMethod.fetchGet(url).then(data => this.searchCallback(data)).catch(err => false);
        },

    createBox: function(){
        let url = "/box";
        let body = {
            name: $("#boxName").val()
        };
        commonMethod.fetchPost(url,body).then(data => addItem(data.id, data.name)).catch(err => false);
    },

    searchCallback: function(data){
        if (data == null) return;

        let $container = $(".container");
        $container.empty();
        $(data).each(function(index, item){
            addItem(item.id, item.name);
        });
    }
}

function addItem(id, name){
    // 이름버튼 생성
            let nameBtn = "<a href='/card/init/"+id+"' role='button'>"+name+"</a>";
            // 선택버튼 생성
            let selectBtn = "<a href='#' role='button'>선택</a>";
            // div블럭 생성
            let div = "<div></div>";
            let $div = $(div);
            // container
            let $container = $(".container");
            $container.append($div.append(nameBtn).append(selectBtn));
}

api.search();

