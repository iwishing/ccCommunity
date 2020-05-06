$(function(){
    $('#replyButton').click(function () {
        var postId = $('#PostIdHidden').val().trim();
        var parent_id = $('#PostIdHidden').val().trim();
        var content = $('#replyContent').val().trim();

        var replyContext = document.getElementById("replyContext");
        var repleyMessage = document.getElementById("repleyMessage");
        // var repleyMessageContext = document.getElementById("repleyMessageContext");


        repleyMessage.style.display="none";
        $('#messageButton').removeClass("btn-success").addClass("btn-danger");
        if (content == ''){
            repleyMessage.style.display="block";
            repleyMessageContext.innerHTML="回复不能为空！";
            return false;
        }

        var param = {
            postId: postId,
            parent_id: parent_id,
            type: 1,
            content: content
        }

        $.ajax({
            type: 'post',
            url: '/comment',
            contentType: 'application/json',
            dataType: 'json',
            data: JSON.stringify(param),
            success:function (data) {
                if (data == "success"){
                    repleyMessage.style.display="block";
                    $('#messageButton').removeClass("btn-danger").addClass("btn-success");
                    $('#replyContent').val("");
                    repleyMessageContext.innerHTML="发送成功！";
                    setTimeout(function(){repleyMessage.style.display="none";}, 2000);
                }else {
                    repleyMessage.style.display="block";
                    repleyMessageContext.innerHTML=data;
                    setTimeout(function(){repleyMessage.style.display="none";}, 2000);
                    return false;
                }
                console.log(data);
            },
            error:function (err) {
                console.log(err);
                return false;
            }
        })
        return false;
    })
    return false;
})
