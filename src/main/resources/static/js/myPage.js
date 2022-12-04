let api= {
    modName: function(){
        let url="/member";
        let body={
            memberName: $("#memberName").val()
        }
        commonMethod.fetch(url, "PUT", body)
                    .then(() => window.location.reload())
                    .catch(err => false);
    },

    cancel: function () {
            let url = "/team/cancel";

            commonMethod.fetch(url, "PUT")
                .then(() => window.location.reload())
                .catch(err => false);
        }
}

function goMyGroup(){
location.href = "/team/my";
}

function goGroup(){
location.href = "/group";
}

function doCancelReq(){
api.cancel();
}

