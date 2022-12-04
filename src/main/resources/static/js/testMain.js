let api = {
    init: function(){
        let url = "/test/"+$("#boxId").val();
        commonMethod.fetchGet(url)
                    .then(data => setCounts(data))
                    .catch(err => false);
    }
}

function setCounts(data){
    $("#level1").text("레벨1\r\n("+data.countLevel1+" 개)");
    $("#level2").text("레벨2\r\n("+data.countLevel2+" 개)");
    $("#level3").text("레벨3\r\n("+data.countLevel3+" 개)");
    $("#level4").text("레벨4\r\n("+data.countLevel4+" 개)");
    $("#level5").text("레벨5\r\n("+data.countLevel5+" 개)");

}

api.init();