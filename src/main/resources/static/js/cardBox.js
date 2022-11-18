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

    select: function(id){
        let url = "/box/"+id;
                commonMethod.fetch(url,"PUT",null).catch(err => false);
    },

    searchCallback: function(data){
        if (data == null) return;

        let $container = $(".container");
        $container.empty();
        let selectBoxId = data.selectBoxId;
        $(data.boxes).each(function(index, item){
            addItem(item.id, item.name, selectBoxId);
        });
    }
}

function addItem(id, name, selectBoxId){
    // 이름버튼 생성
            let nameBtn = "<a href='/card/init/"+id+"' role='button'>"+name+"</a>";
            // 선택버튼 생성
            let selectBtn = "<a href='#' role='button' onclick='selectBox(this,"+id+")'>선택</a>";
            // div블럭 생성
            let div = "<div class='box'></div>";
            let $div = $(div);
            if(id === selectBoxId){
                $div.css("background","#c6dcfc");
            }
            // container
            let $container = $(".container");
            $container.append($div.append(nameBtn).append(selectBtn));
}

function selectBox(target, id){
    $(".box").css("background","");
    $boxDiv = $(target).parents(".box");
    $boxDiv.css("background","#c6dcfc");
    api.select(id);
}

api.search();

