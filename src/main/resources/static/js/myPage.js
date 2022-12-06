let api = {
    modName: function () {
        let url = "/member";
        let body = {
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

function goMyGroup() {
    location.href = "/team/my";
}

function goGroup() {
    location.href = "/group";
}

function doCancelReq() {
    api.cancel();
}

function profileUpload(){
    $("#profileImageInput").click();
    $("#profileImageInput").change(function(e){
        let imgFile = e.target.files[0];

        if(!imgFile.type.match("image.*")){
            alert("이미지 파일이 아닙니다.");
            return;
        }

        let formData = new FormData($("#profileImageForm")[0]);
        let url = "/member/image";
        commonMethod.fetchFormData(url, "PUT", formData)
        .then(() => window.location.reload())
        .catch(err => false);
    })
}

