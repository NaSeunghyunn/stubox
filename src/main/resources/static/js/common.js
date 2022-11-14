let commonMethod = {
    fetchGet: async function (url) {
        const res = await fetch(getApiUrl(url));
        const data = await res.json();
        if (res.ok) {
            return data;
        } else {
            apiError(data);
        }
    },

    fetchPost: async function (url, body) {
        let option = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body)
        }
        const res = await fetch(getApiUrl(url), option);
        const data = await res.json();
        if (res.ok) {
            return data;
        } else {
            apiError(data);
        }
    },

    fetch: async function (url, method, body) {
        let option = {
            method: method,
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(body)
        }
        const res = await fetch(getApiUrl(url), option);
        const data = await res.json();
        if (res.ok) {
            return data;
        } else {
            apiError(data);
        }
    },

    back: function () {
    console.log($("#referer").val());
    $("#referer").val();
    },

    clearError: function (){
        $(".err").empty();
    },

    drawError: function (error){
         $(".err").text(error.message);
    }
}

function getApiUrl(url){
    return "/api" + url;
}

function apiError(data){
    throw new Error(data.error);
}