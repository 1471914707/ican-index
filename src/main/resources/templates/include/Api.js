
var BaseUrl = "/test";

var Api = {
    UserGet: BaseUrl + "/getUser",

    get:function(url,data,successFunction){
        $.ajax({
            url:url,
            dataType:"json",
            data:data,
            type:"GET",
            success:successFunction
        })
    },
    get:function(url,data,successFunction,errorFunction){
        $.ajax({
            url:url,
            dataType:"json",
            data:data,
            type:"GET",
            success:successFunction,
            error:errorFunction
        })
    },

    post:function(url,data,successFunction){
        $.ajax({
            url:url,
            dataType:"json",
            data:data,
            type:"POST",
            success:successFunction
        })
    },
    post:function(url,data,successFunction,errorFunction){
        $.ajax({
            url:url,
            dataType:"json",
            data:data,
            type:"POST",
            success:successFunction,
            error:errorFunction
        })
    },
}