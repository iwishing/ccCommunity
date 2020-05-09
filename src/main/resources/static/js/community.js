function subscription() {
        var community_id=$('#communityHidden').val().trim();
         var subscriptionButton = document.getElementById("subscriptionButton");
          var cancelSubscription = document.getElementById("cancelSubscription");

        $.ajax({
            type: "get",
            url: "/community/subscription/"+community_id,
            success:function (data) {
                console.log(data)
                if(data == "not_login"){
                    //用户未登录
                    var isAccepted = confirm(data);
                    if (isAccepted) {
                        $('#loginModel').modal({show: true});
                    }else{
                        return false;
                    }
                }else if(data == "subscription"){
                    window.location.reload();
                    //用户订阅
                }else if (data == "cancel"){
                    window.location.reload();
                }else {
                    //其他
                }
            },
            error:function (err) {
                console.log(err)
            }
        })
}